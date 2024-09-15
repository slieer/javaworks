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

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Below are links to APIs that may be helpful during these exercises.
 *
 * {@link MutableList#groupBy(Function)}
 * {@link MutableList#groupByEach(Function)}
 * {@link ArrayIterate#groupByEach(Object[], Function)}
 * {@link MutableBag#addOccurrences(Object, int)}
 *
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/19">Exercise 7 Slides</a>
 */
public class Exercise7Test extends CompanyDomainForKata
{
    /**
     * Create a Multimap where the keys are the names of cities and the values are the Customers from those cities.
     * A Customer is only associated to one city.
     */
    @Test
    @Tag("SOLUTION")
    public void customersByCity()
    {
        MutableListMultimap<String, Customer> multimap =
                this.company.getCustomers().groupBy(Customer::getCity);

        var expectedLiphookList =
                Lists.mutable.with("Mary").collect(this.company::getCustomerNamed);
        var expectedLondonList =
                Lists.mutable.with("Fred", "Bill").collect(this.company::getCustomerNamed);

        Assertions.assertEquals(expectedLiphookList, multimap.get("Liphook"));
        Assertions.assertEquals(expectedLondonList, multimap.get("London"));
    }

    /**
     * Create a Multimap where the keys are the names of items and the values are the Suppliers that supply them.
     * A Supplier is associated to many item names.
     */
    @Test
    @Tag("SOLUTION")
    public void itemsBySuppliers()
    {
        MutableMultimap<String, Supplier> itemsToSuppliers = this.company.getSuppliers()
                .groupByEach(supplier -> ArrayAdapter.adapt(supplier.getItemNames()));

        Verify.assertIterableSize("should be 2 suppliers for sofa", 2, itemsToSuppliers.get("sofa"));
    }
}
