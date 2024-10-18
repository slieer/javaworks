package org.slieer.pattern.oops.designPatterns.observer.subscribers;

import org.slieer.pattern.oops.designPatterns.observer.Subject;
import org.slieer.pattern.oops.designPatterns.observer.Subject;

public class TabletClient extends Observer {

	public TabletClient (Subject subject) {
		this.subject = subject;
		subject.attach(this);
	}
	
	public void addMessage(String message) {
		subject.setState(message + " - sent from tablet");
	}
	
	@Override
	public void update() {
		System.out.println("Tablet Stream: " + subject.getState());
	}

}
