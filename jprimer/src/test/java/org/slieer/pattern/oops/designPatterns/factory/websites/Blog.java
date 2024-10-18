package org.slieer.pattern.oops.designPatterns.factory.websites;


import org.slieer.pattern.oops.designPatterns.factory.pages.AboutPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.CommentPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.ContactPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.PostPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.CommentPage;
import org.slieer.pattern.oops.designPatterns.factory.pages.ContactPage;

public class Blog extends Website {

	@Override
	public void createWebsite() {
		pages.add(new PostPage());
		pages.add(new AboutPage());
		pages.add(new CommentPage());
		pages.add(new ContactPage());
	}

}
