package com.guava;

import java.awt.Color;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * http://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained
 * @author root
 */
public class ImmutableTest {
	public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
			"red", "orange", "yellow", "green", "blue", "purple");

	public static final ImmutableSet<Color> GOOGLE_COLORS = ImmutableSet
			.<Color> builder()
			.add(new Color(0, 191, 255))
			.build();

	public void testA(){
		ImmutableSet.of("a", "b", "c", "a", "d", "b");

		ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz");
		thingamajig(foobar);

	}
	
	public void thingamajig(Collection<String> collection) {
		ImmutableList<String> defensiveCopy = ImmutableList.copyOf(collection);
	}		
	
	class Bar {

	}

	class Foo {
		private Set<Bar> bars;

		Foo(Set<Bar> bars) {
			this.bars = ImmutableSet.copyOf(bars); // defensive copy!
		}

		public Set<Bar> getBars() {
			return bars;
		}

		public void setBars(Set<Bar> bars) {
			this.bars = bars;
		}

	}
}
