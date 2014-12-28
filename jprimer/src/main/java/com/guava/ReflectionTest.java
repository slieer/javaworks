package com.guava;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Test;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

//http://code.google.com/p/guava-libraries/wiki/ReflectionExplained
public class ReflectionTest {
	@Test
	public void test() {
		TypeToken<String> stringTok = TypeToken.of(String.class);
		
		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		
		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
		};
		
		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {
		};

		TypeToken<Map<String, BigInteger>> mapToken = mapToken(
				TypeToken.of(String.class), 
				TypeToken.of(BigInteger.class)
		);
		
		TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(
				TypeToken.of(Integer.class), 
				new TypeToken<Queue<String>>() {
				}
		);
		
		
	}

	static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken,
			TypeToken<V> valueToken) {
		return new TypeToken<Map<K, V>>() {
			
		}.where(new TypeParameter<K>() {
			
		}, keyToken).where(new TypeParameter<V>() {
			
		}, valueToken);
	}

}
