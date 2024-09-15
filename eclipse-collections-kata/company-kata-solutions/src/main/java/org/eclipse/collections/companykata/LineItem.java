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

/**
 * An Item has a name and a value.
 */
public class LineItem
{
    private final String name;
    private final double value;

    public String getName()
    {
        return this.name;
    }

    public double getValue()
    {
        return this.value;
    }

    public LineItem(String name, double value)
    {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return this.name + " $ " + this.getValue();
    }
}
