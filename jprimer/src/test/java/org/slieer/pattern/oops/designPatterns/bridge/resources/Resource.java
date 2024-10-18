package org.slieer.pattern.oops.designPatterns.bridge.resources;


import java.util.List;

import org.slieer.pattern.oops.designPatterns.bridge.Detail;
import org.slieer.pattern.oops.designPatterns.bridge.formatters.Formatter;
import org.slieer.pattern.oops.designPatterns.bridge.Detail;

public abstract class Resource {

	public String print(Formatter formatter) {
		return formatter.format(getHeader(), getDetails());
	}
	
	public abstract List<Detail> getDetails();
	
	abstract public String getHeader();
}
