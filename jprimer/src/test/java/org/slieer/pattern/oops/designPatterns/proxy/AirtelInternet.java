package org.slieer.pattern.oops.designPatterns.proxy;

public class AirtelInternet implements Internet {

	@Override
	public String getResource(String site) {
		return "Openeing " + site;
	}

}
