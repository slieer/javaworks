package org.slieer.pattern.oops.designPatterns.decorator;

import org.slieer.pattern.oops.designPatterns.decorator.beverages.Beverage;
import org.slieer.pattern.oops.designPatterns.decorator.beverages.Cappuccino;
import org.slieer.pattern.oops.designPatterns.decorator.beverages.decorators.ChocolateDecorator;
import org.slieer.pattern.oops.designPatterns.decorator.visualComponents.TextView;
import org.slieer.pattern.oops.designPatterns.decorator.visualComponents.VisualComponent;
import oops.designPatterns.decorator.visualComponents.decorators.BorderDecorator;
import oops.designPatterns.decorator.visualComponents.decorators.ScrollDecorator;
import org.slieer.pattern.oops.designPatterns.decorator.beverages.Beverage;
import org.slieer.pattern.oops.designPatterns.decorator.beverages.Cappuccino;
import org.slieer.pattern.oops.designPatterns.decorator.beverages.decorators.ChocolateDecorator;

public class Client {

	public static void main(String[] args) {
		
		/*
		VisualComponent textBox = new TextView("This is the text of my text-box");
		VisualComponent textBoxWithBorder = new BorderDecorator(textBox);
		VisualComponent textBoxWithScroll = new ScrollDecorator(textBoxWithBorder);
		
		
		textBoxWithScroll.draw();
		
		*/
		
		Beverage coffee = new Cappuccino();
		System.out.println(coffee.cost());
		
		Beverage coffeeWithCoholate = new ChocolateDecorator(new ChocolateDecorator(coffee));
		System.out.println(coffeeWithCoholate.cost());
		


	}

}
