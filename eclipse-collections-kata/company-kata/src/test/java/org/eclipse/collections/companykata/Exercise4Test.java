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
import org.eclipse.collections.impl.utility.ListIterate;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Below are links to APIs that may be helpful during these exercises.
 *
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
    /**
     * Solve this without changing the return type of {@link Company#getSuppliers()}. Find the appropriate method on
     * {@link ArrayIterate}.
     */
    @Test
    @Tag("KATA")
    public void findSupplierNames()
    {
        MutableList<String> supplierNames = null;

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
    @Tag("KATA")
    public void countSuppliersWithMoreThanTwoItems()
    {
        Predicate<Supplier> moreThanTwoItems = null;
        int suppliersWithMoreThanTwoItems = 0;

        Assertions.assertEquals(5, suppliersWithMoreThanTwoItems, "suppliers with more than 2 items");
    }

    /**
     * Try to solve this without changing the return type of {@link Supplier#getItemNames()}.
     */
    @Test
    @Tag("KATA")
    public void whoSuppliesSandwichToaster()
    {
        // Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".
        Predicate<Supplier> suppliesToaster = null;

        // Find one supplier that supplies toasters.
        Supplier toasterSupplier = null;

        Assertions.assertNotNull(toasterSupplier, "toaster supplier");
        Assertions.assertEquals("Doxins", toasterSupplier.getName());
    }

    /**
     * Get the order values that are greater than 1.5.
     */
    @Test
    @Tag("KATA")
    public void filterOrderValues()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        MutableList<Double> orderValues = null;
        MutableList<Double> filtered = null;

        var expectedValues = Lists.mutable.with(372.5, 1.75);
        Assertions.assertEquals(expectedValues, filtered);
    }

    /**
     * Get the order values that are greater than 1.5 using double instead of Double.
     */
    @Test
    @Tag("KATA")
    public void filterOrderValuesUsingPrimitives()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        MutableDoubleList orderValues = null;
        MutableDoubleList filtered = null;

        var expectedValues = DoubleLists.mutable.with(372.5, 1.75);
        Assertions.assertEquals(expectedValues, filtered);
    }

    /**
     * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
     */
    @Test
    @Tag("KATA")
    public void filterOrders()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        MutableList<Order> filtered = null;

        var expectedOrders = Lists.mutable.with(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders()));
        Assertions.assertEquals(expectedOrders, filtered);
    }
}
