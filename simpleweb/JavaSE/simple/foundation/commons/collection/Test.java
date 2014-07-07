package simple.foundation.commons.collection;

import org.apache.commons.collections.map.MultiValueMap;

public class Test {
	public static void main(String[] args) {
		MultiValueMap map = new MultiValueMap();
		
		map.put("a", "b");
		map.put("a", "b1");
		map.put("a", "b2");
		map.put("b", "zhai");
		map.put("b", "zhai");
		map.put("b", "zhai");
		
		System.out.println(map);
	}
}
