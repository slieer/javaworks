package slieer.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dev
 *
 *         调用了concurrencyLevel()方法，设置了我们允许在map中并发修改的数量，
 *         可以指定softValues()方法，这样map中的value值都包裹在一个SoftReference(软引用)对象中，可以在内存过低
 *         的时候被当作垃圾回收。
 */
public class CacheTest {
	static Logger logger = LogManager.getLogger(CacheTest.class.getName());	
		
	@Test
	public void cacheTest() {
		ConcurrentMap<String, Book> books = new MapMaker().concurrencyLevel(2)
				.makeMap();

		books.put("plus", new Book());
		books.put("plus", new Book());
		books.put("plus", new Book());
		
		logger.info("this value is {}", books.get("plus"));
	}

	private static LoadingCache<String, String> cahceBuilder = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {
					String strProValue = "hello " + key + "!";
					return strProValue;
				}
				
			});
	
	@BeforeEach
	public void testLoadingCache() throws Exception {		
		logger.info("jerry value: {}", cahceBuilder.apply("jerry"));
		logger.info("jerry value: {}", cahceBuilder.get("jerry"));
		logger.info("peida value: {}", cahceBuilder.get("peida"));
		logger.info("peida value: {}", cahceBuilder.apply("peida"));
		logger.info("lisa value: {}", cahceBuilder.apply("lisa"));
		
		cahceBuilder.put("harry", "ssdded");
		logger.info("harry value: {}", cahceBuilder.get("harry"));
	}

	@Test
    public void testcallableCache()throws Exception{
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();  
        String resultVal = cache.get("jerry", new Callable<String>() {  
            public String call() {  
                String strProValue="hello "+"jerry"+"!";                
                return strProValue;
            }
        });  
        logger.info("jerry value : {}", resultVal);
        
        resultVal = cache.get("peida", new Callable<String>() {  
            public String call() {  
                String strProValue="hello "+"peida"+"!";                
                return strProValue;
            }  
        });  
        logger.info("peida value : {}", resultVal);  
    }

	public static class Book {
		int i = 0;
		{
			i = RandomUtils.nextInt(0, 100);
		}

		@Override
		public String toString() {
			return "Book [i=" + i + "]";
		}
	}
}
