package org.slieer.pattern.oops.designPatterns.factory.websites;



import java.util.ArrayList;
import java.util.List;

import org.slieer.pattern.oops.designPatterns.factory.pages.Page;

public abstract class Website {

	protected List<Page> pages = new ArrayList<>();
	
	public List<Page> getPages() {
		return pages;
	}

	public Website() {
		System.out.println("yoloyolyoylo!!");
		this.createWebsite();
	}
	
	public abstract void createWebsite();
	
}
