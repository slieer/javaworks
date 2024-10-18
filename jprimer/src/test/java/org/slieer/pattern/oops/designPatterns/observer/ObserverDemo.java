package org.slieer.pattern.oops.designPatterns.observer;

import org.slieer.pattern.oops.designPatterns.observer.subscribers.PhoneClient;
import org.slieer.pattern.oops.designPatterns.observer.subscribers.TabletClient;
import org.slieer.pattern.oops.designPatterns.observer.subscribers.PhoneClient;
import org.slieer.pattern.oops.designPatterns.observer.subscribers.TabletClient;

public class ObserverDemo {

	public static void main(String args[]) {
		Subject subject = new MessageStream();
		
		PhoneClient phoneClient = new PhoneClient(subject);
		TabletClient tabletClient = new TabletClient(subject);
		
		phoneClient.addMessage("Here is a new message!");
		tabletClient.addMessage("Another new message!");
	}
	
}
