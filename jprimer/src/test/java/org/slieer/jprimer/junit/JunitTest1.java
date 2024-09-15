package org.slieer.jprimer.junit;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author mkyong
 * 
 */
public class JunitTest1 {

	private Collection<String> collection;

	@BeforeAll
	public static void oneTimeSetUp() {
		// one-time initialization code
		System.out.println("@BeforeClass - oneTimeSetUp");
	}

	@AfterAll
	public static void oneTimeTearDown() {
		// one-time cleanup code
		System.out.println("@AfterClass - oneTimeTearDown");
	}

	@BeforeEach
	public void setUp() {
		collection = new ArrayList<String>();
		System.out.println("@BeforeEach - setUp");
	}

	@AfterEach
	public void tearDown() {
		collection.clear();
		System.out.println("@AfterEach - tearDown");
	}

	@Test
	public void testEmptyCollection() {
		assertTrue(collection.isEmpty());
		System.out.println("@Test - testEmptyCollection");
	}

	@Test
	public void testOneItemCollection() {
		collection.add("itemA");
		assertEquals(1, collection.size());
		System.out.println("@Test - testOneItemCollection");
	}
}