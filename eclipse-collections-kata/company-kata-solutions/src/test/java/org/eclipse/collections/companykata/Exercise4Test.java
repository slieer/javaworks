/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.companykata;

import java.util.List;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.Iterate;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Below are links to APIs that may be helpful during these exercises.
 * <p>
 * {@link ArrayIterate#collect(Object[], Function)}
 * {@link ArrayIterate#count(Object[], Predicate)}
 * {@link ArrayIterate#detect(Object[], Predicate)}
 * {@link ListIterate#collect(List, Function)}
 * {@link ListIterate#collectDouble(List, DoubleFunction)}
 * {@link ListIterate#select(List, Predicate)}
 *
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/12">Exercise 4 Slides</a>
 */
public class Exercise4Test extends CompanyDomainForKata
{
    private static final String SANDWICH_TOASTER = "sandwich toaster";

    /**
     * Solve this without changing the return type of {@link Company#getSuppliers()}. Find the appropriate method on
     * {@link ArrayIterate}.
     */
    @Test
    @Tag("SOLUTION")
    public void findSupplierNames()
    {
        // The solution for this exercise is (the return type of getSuppliers is changed in exercise 7):
        // MutableList<String> supplierNames = ArrayIterate.collect(this.company.getSuppliers(), Supplier::getName);
        MutableList<String> supplierNames = this.company.getSuppliers().collect(Supplier::getName);

        var expectedSupplierNames = Lists.mutable.with(
                "Shedtastic",
                "Splendid Crocks",
                "Annoying Pets",
                "Gnomes 'R' Us",
                "Furniture Hamlet",
                "SFD",
                "Doxins");
        Assertions.assertEquals(expectedSupplierNames, supplierNames);
    }

    /**
     * Create a {@link Predicate} for Suppliers that supply more than 2 items. Find the number of suppliers that
     * satisfy that Predicate.
     */
    @Test
    @Tag("SOLUTION")
    public void countSuppliersWithMoreThanTwoItems()
    {
        // The solution for this exercise is (the return type of getSuppliers is changed in exercise 7):
        // int suppliersWithMoreThanTwoItems = ArrayIterate.count(this.company.getSuppliers(), s -> s.getItemNames().length > 2);
        int suppliersWithMoreThanTwoItems =
                this.company.getSuppliers().count(supplier -> supplier.getItemNames().length > 2);

        Assertions.assertEquals(5, suppliersWithMoreThanTwoItems, "suppliers with more than 2 items");
    }

    /**
     * Try to solve this without changing the return type of {@link Supplier#getItemNames()}.
     */
    @Test
    @Tag("SOLUTION")
    public void whoSuppliesSandwichToaster()
    {
        // Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".
        Predicate<Supplier> suppliesToaster =
                supplier -> ArrayIterate.contains(supplier.getItemNames(), SANDWICH_TOASTER);

        // Find one supplier that supplies toasters.
        // The solution for this exercise is (the return type of getSuppliers is changed in exercise 7):
        // Supplier toasterSupplier = ArrayIterate.detect(this.company.getSuppliers(), suppliesToaster);
        Supplier toasterSupplier = this.company.getSuppliers().detect(suppliesToaster);

        Assertions.assertNotNull(toasterSupplier, "toaster supplier");
        Assertions.assertEquals("Doxins", toasterSupplier.getName());
    }

    /**
     * Get the order values that are greater than 1.5.
     */
    @Test
    @Tag("SOLUTION")
    public void filterOrderValues()
    {
        MutableList<Double> orderValues =
                ListIterate.collect(this.company.getMostRecentCustomer().getOrders(), Order::getValue);
        MutableList<Double> filtered = orderValues.select(value -> value > 1.5);

        var expectedValues = Lists.mutable.with(372.5, 1.75);
        Assertions.assertEquals(expectedValues, filtered);
    }

    /**
     * Get the order values that are greater than 1.5 using double instead of Double.
     */
    @Test
    @Tag("SOLUTION")
    public void filterOrderValuesUsingPrimitives()
    {
        MutableDoubleList orderValues =
                ListIterate.collectDouble(this.company.getMostRecentCustomer().getOrders(), Order::getValue);
        MutableDoubleList filtered = orderValues.select(value -> value > 1.5);

        var expectedValues = DoubleLists.mutable.with(372.5, 1.75);
        Assertions.assertEquals(expectedValues, filtered);
    }

    /**
     * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
     */
    @Test
    @Tag("SOLUTION")
    public void filterOrders()
    {
        MutableList<Order> filtered =
                ListIterate.select(this.company.getMostRecentCustomer().getOrders(), o -> o.getValue() > 2.0);

        var expectedOrders =
                Lists.mutable.with(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders()));
        Assertions.assertEquals(expectedOrders, filtered);
    }
}
