package org.slieer.pattern.oops.designPatterns.observer.subscribers;

import org.slieer.pattern.oops.designPatterns.observer.Subject;

public abstract class Observer {
	public Subject subject;
	public abstract void update();
}
