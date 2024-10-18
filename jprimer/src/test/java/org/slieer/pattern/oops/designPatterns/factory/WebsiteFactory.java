package org.slieer.pattern.oops.designPatterns.factory;

import org.slieer.pattern.oops.designPatterns.factory.websites.Blog;
import org.slieer.pattern.oops.designPatterns.factory.websites.Shop;
import org.slieer.pattern.oops.designPatterns.factory.websites.Website;
import org.slieer.pattern.oops.designPatterns.factory.websites.Blog;
import org.slieer.pattern.oops.designPatterns.factory.websites.Shop;
import org.slieer.pattern.oops.designPatterns.factory.websites.Website;

public class WebsiteFactory {

	public static Website getWebsite(WebsiteType siteType) {
		switch(siteType) {
			case BLOG : {
				return new Blog();
			}
		
			case SHOP : {
				return new Shop();
			}
			
			default : {
				return null;
			}
 		}
	}
	
}
