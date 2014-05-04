package com.slieer.guava;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.primitives.Ints;

public class NewUtility {
	@Test
	public void testUtility() {
		Iterable<Integer> concatenated = Iterables.concat(Ints.asList(1, 2, 3),
				Ints.asList(4, 5, 6));
		// concatenated has elements 1, 2, 3, 4, 5, 6

		List<String> list = Lists.newArrayList();
		List<String> theseElements = Lists.newArrayList("alpha", "beta",
				"gamma");
		List<String> exactly100 = Lists.newArrayListWithCapacity(100);
		List<String> approx100 = Lists.newArrayListWithExpectedSize(100);

		Set<String> approx100Set = Sets.newHashSetWithExpectedSize(100);
		Set<String> copySet = Sets.newHashSet();
		
		//Multisets
		//Multimaps
		
		Map<String, Integer> map = Maps.newLinkedHashMap();
		
		//Tables.newCustomTable
		
		Table<Integer, Integer, Double> weightedGraph = HashBasedTable.create();
		weightedGraph.put(1, 2, 4.0);
		weightedGraph.put(1, 3, 20.0);
		weightedGraph.put(2, 3, 5.0);

		weightedGraph.row(1); // returns a Map mapping v2 to 4, v3 to 20
		weightedGraph.column(3);
		
		ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(
				theseElements, new Function<String, Integer>() {
					public Integer apply(String string) {
						return string.length();
					}
				});
		
		Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
		Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
		MapDifference<String, Integer> diff = Maps.difference(left, right);
		
	}
}
