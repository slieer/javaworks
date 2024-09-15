package slieer.commons.Digester.guide.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Foo {
	private String name;
	private List<Bar> bars = new ArrayList<Bar>();;

	@Override
	public String toString() {
		return "Foo [name=" + name + ", bars=" + bars + "]";
	}

	public void addBar(Bar bar) {
		bars.add(bar);
	}

	public Bar findBar(int id) {
		for (Bar bar : bars) {
			if (bar.getId() == id) {
				return bar;
			}
		}
		return null;
	}

	public Iterator<Bar> getBars() {
		return bars.iterator();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
