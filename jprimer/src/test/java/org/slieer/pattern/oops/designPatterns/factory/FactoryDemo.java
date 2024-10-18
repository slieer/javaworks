package org.slieer.pattern.oops.designPatterns.factory;

import org.slieer.pattern.oops.designPatterns.factory.websites.Blog;
import org.slieer.pattern.oops.designPatterns.factory.websites.Website;
import org.slieer.pattern.oops.designPatterns.factory.websites.Blog;

public class FactoryDemo {

	public static void main(String[] args) {
		Blog site = (Blog) WebsiteFactory.getWebsite(WebsiteType.BLOG);
		
		System.out.println(site.getPages());
		
		//site = WebsiteFactory.getWebsite(WebsiteType.SHOP);
		
		System.out.println(site.getPages());
	}

}
