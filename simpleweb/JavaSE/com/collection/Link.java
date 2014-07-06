package com.collection;

import javax.enterprise.inject.New;

class UnidirectionLin{
		String value;
		UnidirectionLin next;
		
		public UnidirectionLin(String value, UnidirectionLin next) {
			super();
			this.value = value;
			this.next = next;
		}

		@Override
		public String toString() {
			return "UnidirectionLin [value=" + value + ", next=" + next + "]";
		}
}

public class Link {
	static UnidirectionLin head = new UnidirectionLin("head", null);
	
	static void init(){
		UnidirectionLin _1st = new UnidirectionLin("1", null);
		UnidirectionLin _2nd = new UnidirectionLin("2", null);
		UnidirectionLin _3rd = new UnidirectionLin("3", null);
		UnidirectionLin _4th = new UnidirectionLin("4", null);
		
		head.next = _1st;
		_1st.next = _2nd;
		_2nd.next = _3rd;
		_3rd.next = _4th;
	}
	
	static void ergodic(){
		UnidirectionLin p = head;
		while(p.next != null){
			System.out.println(p);
			p = p.next;
		}
	}
	public static void main(String[] args) {
		init();
		ergodic();
	}
}
