package com.primer.lang;


/**
 * 继承和多态练习
 * 
 * @author slieer
 * Create Date2011-12-22
 * version 1.0
 */
public class PolymorphismTest {
	int i = 11;
	String str = "A class Variable";
	
	PolymorphismTest(){
		System.out.println("constructor A");
	}
	
	void f(String arg){
		System.out.println("class A");
	}
	
	static void sta(){
		System.out.println("class A static method");
	}
	
	public void  process(PolymorphismTest a){
		if(a instanceof B){
			System.out.println("B Type");
		}else if(a instanceof C){
			System.out.println("C Type");
		}
	}
}

class B extends PolymorphismTest{
	int i;
	String str;
	
	B(){
		System.out.println("constructor B");
	}
	
	void f(String arg){
		System.out.println("class B");
	}

	static void sta(){
		System.out.println("class B static method");
	}
	
	public static void main(String[] args) {
		PolymorphismTest a = new B();
		
		System.out.println(((B)a).i);
		a.f("");
		//a.sta();
		
		
		a.process(a);
		a.process(new C());
		
		//List<String> l = new ArrayList<String>();
		//Class<? extends List<>> c = l.getClass();
		
		//System.out.println(c);
		
		Long totalLong = 39291L;
	   System.out.println(totalLong.intValue()/100+1);
      System.out.println(totalLong.intValue()%100+1);
		
	}
}

class C extends PolymorphismTest{
	
}
