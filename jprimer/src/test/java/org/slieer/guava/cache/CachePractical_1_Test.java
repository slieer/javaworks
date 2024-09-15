package org.slieer.guava.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 
　说明：Callable只有在缓存值不存在时，才会调用，比如第二次调用getCallableCache(u1name)直接返回缓存中的值

　　guava Cache数据移除：

　　guava做cache时候数据的移除方式，在guava中数据的移除分为被动移除和主动移除两种。
　　被动移除数据的方式，guava默认提供了三种方式：
　　1.基于大小的移除:看字面意思就知道就是按照缓存的大小来移除，如果即将到达指定的大小，那就会把不常用的键值对从cache中移除。
　　定义的方式一般为 CacheBuilder.maximumSize(long)，还有一种一种可以算权重的方法，个人认为实际使用中不太用到。就这个常用的来看有几个注意点，
　　　　其一，这个size指的是cache中的条目数，不是内存大小或是其他；
　　　　其二，并不是完全到了指定的size系统才开始移除不常用的数据的，而是接近这个size的时候系统就会开始做移除的动作；
　　　　其三，如果一个键值对已经从缓存中被移除了，你再次请求访问的时候，如果cachebuild是使用cacheloader方式的，那依然还是会从cacheloader中再取一次值，如果这样还没有，就会抛出异常
　　2.基于时间的移除：guava提供了两个基于时间移除的方法
　　　　expireAfterAccess(long, TimeUnit)  这个方法是根据某个键值对最后一次访问之后多少时间后移除
　　　　expireAfterWrite(long, TimeUnit)  这个方法是根据某个键值对被创建或值被替换后多少时间移除
　　3.基于引用的移除：
　　这种移除方式主要是基于java的垃圾回收机制，根据键或者值的引用关系决定移除
　　主动移除数据方式，主动移除有三种方法：
　　1.单独移除用 Cache.invalidate(key)
　　2.批量移除用 Cache.invalidateAll(keys)
　　3.移除所有用 Cache.invalidateAll()
　　如果需要在移除数据的时候有所动作还可以定义Removal Listener，但是有点需要注意的是默认Removal Listener中的行为是和移除动作同步执行的，如果需要改成异步形式，可以考虑使用RemovalListeners.asynchronous(RemovalListener, Executor) *
 */
public class CachePractical_1_Test{
private static Cache<String, String> cacheFormCallable = null; 

    /**
     * 对需要延迟处理的可以采用这个机制；(泛型的方式封装)
     * @param <K>
     * @param <V>
     * @param key
     * @param callable
     * @return V
     * @throws Exception
     */
    private static <K,V> Cache<K , V> callableCached() throws Exception {
          Cache<K, V> cache = CacheBuilder
          .newBuilder()
          .maximumSize(10000)
          .expireAfterWrite(10, TimeUnit.MINUTES)
          .build();
          return cache;
    }

    
    private String getCallableCache(final String userName) {
           try {
             //Callable只有在缓存值不存在时，才会调用
             return cacheFormCallable.get(userName, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(userName+" from db");
                    return "hello "+userName+"!";
               }
              });
           } catch (ExecutionException e) {
              e.printStackTrace();
              return null;
            } 
    }
    
    @Test
    public void testCallableCache() throws Exception{
         final String u1name = "peida";
         final String u2name = "jerry"; 
         final String u3name = "lisa"; 
         cacheFormCallable=callableCached();
         cacheFormCallable.put("peida", "a");
         cacheFormCallable.put("harry", "a");
         cacheFormCallable.put("peida", "a");
         cacheFormCallable.put("lisa", "a");

         
         System.out.println("peida:"+getCallableCache(u1name));
         System.out.println("jerry:"+getCallableCache(u2name));
         System.out.println("lisa:"+getCallableCache(u3name));
         System.out.println("peida:"+getCallableCache(u1name));
         
    }
}
