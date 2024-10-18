package org.slieer.pattern.oops.designPatterns.prototype;

import org.slieer.pattern.oops.designPatterns.prototype.items.Movie;
import org.slieer.pattern.oops.designPatterns.prototype.items.Movie;

public class PrototypeDemo {

	public static void main(String[] args) {
		Registry registry = new Registry();
		Movie movie = (Movie) registry.createItem("Movie");
		movie.setTitle("The Prestige");
		
		System.out.println(movie);
		System.out.println(movie.getRuntime());
		System.out.println(movie.getTitle());
		System.out.println(movie.getUrl());
		
		Movie anotherMovie =  (Movie) registry.createItem("Movie");
		anotherMovie.setTitle("Gang of Wassepur");
		
		System.out.println(anotherMovie);
		System.out.println(anotherMovie.getRuntime());
		System.out.println(anotherMovie.getTitle());
		System.out.println(anotherMovie.getUrl());
	}

}
