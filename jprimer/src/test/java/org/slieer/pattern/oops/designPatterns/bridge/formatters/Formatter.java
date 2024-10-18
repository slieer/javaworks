package org.slieer.pattern.oops.designPatterns.bridge.formatters;


import java.util.List;

import org.slieer.pattern.oops.designPatterns.bridge.Detail;
import org.slieer.pattern.oops.designPatterns.bridge.resources.Resource;
import org.slieer.pattern.oops.designPatterns.bridge.Detail;
import org.slieer.pattern.oops.designPatterns.bridge.resources.Resource;

public abstract class Formatter {
	Resource resource;
	
	public Formatter(Resource resource) {
		this.resource = resource;
	}
	public abstract String format(String header, List<Detail> details);
	public abstract String render();
}
