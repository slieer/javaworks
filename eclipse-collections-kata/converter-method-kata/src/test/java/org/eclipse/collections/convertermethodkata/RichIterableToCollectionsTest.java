/*
 * Copyright (c) 2022 The Bank of New York Mellon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.convertermethodkata;

import java.util.Comparator;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.SortedBags;
import org.eclipse.collections.api.factory.SortedMaps;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.factory.Stacks;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Convert the types in the following tests to the missing type using APIs on the RichIterable interface.
 */
public class RichIterableToCollectionsTest
{
    public static final Person MARY_SMITH = new Person("Mary", "Smith", 25);
    public static final Person TED_FIELDS = new Person("Ted", "Fields", 30);
    public static final Person SALLY_GOLD = new Person("Sally", "Gold", 42);
    private final ImmutableList<Person> people = Lists.immutable.with(MARY_SMITH, TED_FIELDS, SALLY_GOLD);

    @Test
    @Tag("KATA")
    public void toList()
    {
        Interval interval = Interval.oneTo(5);
        // Convert interval to a MutableList<Integer>
        MutableList<Integer> list = null;
        Assertions.assertEquals(Lists.mutable.with(1, 2, 3, 4, 5), list);
    }

    @Test
    @Tag("KATA")
    public void toSet()
    {
        MutableList<Integer> list = Lists.mutable.with(1, 2, 2, 3, 3);
        // Convert list to a MutableSet<Integer>
        MutableSet<Integer> set = null;
        Assertions.assertEquals(Sets.mutable.with(1, 2, 3), set);
    }

    @Test
    @Tag("KATA")
    public void toBag()
    {
        MutableList<Integer> list = Lists.mutable.with(1, 2, 2, 3, 3);
        // Convert list to a MutableBag<Integer>
        MutableBag<Integer> bag = null;
        Assertions.assertEquals(Bags.mutable.with(1, 2, 2, 3, 3), bag);
    }

    @Test
    @Tag("KATA")
    public void toStack()
    {
        MutableList<Integer> list = Lists.mutable.with(1, 2, 3);
        // Convert list to a MutableStack<Integer>
        MutableStack<Integer> stack = null;
        Assertions.assertEquals(Stacks.mutable.with(1, 2, 3), stack);
        // Pop 3 elements off the stack
        Assertions.assertEquals(list.toReversed(), null);
    }

    @Test
    @Tag("KATA")
    public void toMap()
    {
        MutableList<Integer> list = Lists.mutable.with(1, 2, 3);
        // Convert list to a MutableMap<String, Integer> where the keys are the String value of the element, and the
        // values are the Integer value
        MutableMap<String, Integer> map = null;
        Assertions.assertEquals(Maps.mutable.with("1", 1, "2", 2, "3", 3), map);
    }

    @Test
    @Tag("KATA")
    public void toSortedList()
    {
        MutableList<Integer> list = Lists.mutable.with(5, 3, 1, 4, 2);
        // Convert list to a sorted MutableList<Integer>
        MutableList<Integer> forward = null;
        // Convert list to a MutableList<Integer> sorted in reverse order
        MutableList<Integer> reverse = null;
        Assertions.assertEquals(Lists.mutable.with(1, 2, 3, 4, 5), forward);
        Assertions.assertEquals(Lists.mutable.with(5, 4, 3, 2, 1), reverse);
    }

    @Test
    @Tag("KATA")
    public void toSortedListByLastName()
    {
        // Convert this.people to a MutableList<Person> sorted by last name
        MutableList<Person> sorted = null;
        Assertions.assertEquals(Lists.mutable.with(TED_FIELDS, SALLY_GOLD, MARY_SMITH), sorted);
    }

    @Test
    @Tag("KATA")
    public void toSortedSet()
    {
        MutableList<Integer> list = Lists.mutable.with(5, 3, 1, 4, 2);
        // Convert list to a sorted MutableSortedSet<Integer>
        MutableSortedSet<Integer> forward = null;
        // Convert list to a MutableSortedSet<Integer> sorted in reverse order
        MutableSortedSet<Integer> reverse = null;
        Assertions.assertEquals(SortedSets.mutable.with(1, 2, 3, 4, 5), forward);
        Assertions.assertEquals(SortedSets.mutable.with(5, 4, 3, 2, 1), reverse);
    }

    @Test
    @Tag("KATA")
    public void toSortedSetByFirstName()
    {
        // Convert this.people to a MutableSortedSet<Person> sorted by first name
        MutableSortedSet<Person> sorted = null;
        Assertions.assertEquals(SortedSets.mutable.with(
                Comparator.comparing(Person::getFirstName),
                MARY_SMITH, SALLY_GOLD, TED_FIELDS), sorted);
    }

    @Test
    @Tag("KATA")
    public void toSortedBag()
    {
        MutableList<Integer> list = Lists.mutable.with(5, 3, 1, 4, 2);
        // Convert list to a sorted MutableSortedBag<Integer>
        MutableSortedBag<Integer> forward = null;
        // Convert list to a MutableSortedBag<Integer> sorted in reverse order
        MutableSortedBag<Integer> reverse = null;
        Assertions.assertEquals(SortedBags.mutable.with(1, 2, 3, 4, 5), forward);
        Assertions.assertEquals(SortedBags.mutable.with(5, 4, 3, 2, 1), reverse);
    }

    @Test
    @Tag("KATA")
    public void toSortedBagByAge()
    {
        // Convert this.people to a MutableSortedBag<Person> sorted by age
        MutableSortedBag<Person> sorted = null;
        Assertions.assertEquals(SortedBags.mutable.with(
                Comparator.comparing(Person::getAge),
                MARY_SMITH, TED_FIELDS, SALLY_GOLD), sorted);
    }

    @Test
    @Tag("KATA")
    public void toSortedMap()
    {
        MutableList<Integer> list = Lists.mutable.with(3, 1, 2);
        // Convert list to a MutableSortedMap<String, Integer> where the keys are the String value of the Integer
        // and the values are the Integer values
        MutableSortedMap<String, Integer> map = null;
        Assertions.assertEquals(SortedMaps.mutable.with("1", 1, "2", 2, "3", 3), map);
    }

    @Test
    @Tag("KATA")
    public void toSortedMapByLastName()
    {
        // Convert this.people to MutableSortedMap<String, Person> where the keys are the last name of the person
        // and the values are the person, and the keys are sorted on their uppercase String value
        MutableSortedMap<String, Person> map = null;
        Assertions.assertEquals(SortedMaps.mutable.with("FIELDS", TED_FIELDS, "GOLD", SALLY_GOLD, "SMITH", MARY_SMITH), map);
    }

    @Test
    @Tag("KATA")
    public void toArray()
    {
        MutableList<Integer> list = Lists.mutable.with(1, 2, 3);
        // Convert the list to an Integer array
        Integer[] array = null;
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3}, array);
    }

    @Test
    @Tag("KATA")
    public void toStringTest()
    {
        MutableList<Integer> list = Lists.mutable.with(1, 2, 3);
        // Convert the list to a String
        String toString = null;
        // Convert the list to a String with "[", "," "]" as separators using makeString
        String makeString = null;
        Assertions.assertEquals("[1, 2, 3]", toString);
        Assertions.assertEquals("[1, 2, 3]", makeString);
    }
}