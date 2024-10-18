package org.slieer.pattern.oops.designPatterns.factory.websites;

import org.slieer.pattern.oops.designPatterns.factory.pages.CartPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.ItemPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.SearchPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.SearchPage;

public class Shop extends Website {

	@Override
	public void createWebsite() {
		pages.add(new CartPage());
		pages.add(new ItemPage());
		pages.add(new SearchPage());
	}

}
