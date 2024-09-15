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

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.SortedBags;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Has a number, a {@link Customer}, a {@link List} of {@link LineItem}s, and a boolean that states whether or not the order
 * has been delivered. There is a class variable that contains the next order number.
 */
public class Order
{
    private static final AtomicInteger NEXT_ORDER_NUMBER = new AtomicInteger(1);

    private final int orderNumber;
    private final MutableSortedBag<LineItem> lineItems = SortedBags.mutable.empty(Comparators.byFunction(LineItem::getName));
    private boolean delivered;

    public Order()
    {
        this.orderNumber = NEXT_ORDER_NUMBER.getAndIncrement();
    }

    public static void resetNextOrderNumber()
    {
        NEXT_ORDER_NUMBER.set(1);
    }

    public void deliver()
    {
        this.delivered = true;
    }

    public boolean isDelivered()
    {
        return this.delivered;
    }

    public void addLineItem(LineItem aLineItem)
    {
        this.lineItems.add(aLineItem);
    }

    public void addLineItems( LineItem item, Integer count)
    {
        this.lineItems.addOccurrences(item, count);
    }

    public Bag<LineItem> getLineItems()
    {
        return this.lineItems;
    }

    @Override
    public String toString()
    {
        return "order " + this.orderNumber + " items: " + this.lineItems.size();
    }

    /**
     * Refactor to use {@link org.eclipse.collections.api.RichIterable#sumOfDouble(DoubleFunction)}.
     */
    public double getValue()
    {
        return this.lineItems.sumOfDouble(LineItem::getValue);
    }

    public boolean containsItemNamed(String itemName)
    {
        return this.lineItems.containsBy(LineItem::getName, itemName);
    }
}
