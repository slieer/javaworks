package org.slieer.pattern.oops.designPatterns.decorator.beverages.decorators;

import org.slieer.pattern.oops.designPatterns.decorator.beverages.Beverage;

public class EspressoShotDecorator extends BeverageDecorator {

	public EspressoShotDecorator(Beverage beverage) {
		super(beverage);
	}

	@Override
	public int cost() {
		return this.beverage.cost() + 6;
	}
	

}
