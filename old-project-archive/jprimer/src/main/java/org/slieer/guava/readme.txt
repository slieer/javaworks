    Guava Collections Feature http://www.ibm.com/developerworks/cn/java/j-lo-googlecollection/
    
    
    Immutable Collections: 还在使用 Collections.unmodifiableXXX() ？ Immutable Collections 这才是真正的不可修改的集合
    Multiset: 看看如何把重复的元素放入一个集合
    Multimaps: 需要在一个 key 对应多个 value 的时候 , 自己写一个实现比较繁琐 - 让 Multimaps 来帮忙
    BiMap: java.util.Map 只能保证 key 的不重复，BiMap 保证 value 也不重复
    MapMaker: 超级强大的 Map 构造类
    Ordering class: 大家知道用 Comparator 作为比较器来对集合排序，但是对于多关键字排序 Ordering class 可以简化很多的代码
    其他特性

    
    Multiset: 把重复的元素放入集合

		你可能会说这和 Set 接口的契约冲突，因为 Set 接口的 JavaDoc 里面规定不能放入重复元素。
		事实上，Multiset 并没有实现 java.util.Set 接口，它更像是一个 Bag。
		普通的 Set 就像这样 :[car, ship, bike]，而 Multiset 会是这样 : [car x 2, ship x 6, bike x 3]。
		
	Multimap: 在 Map 的 value 里面放多个元素

		Muitimap 就是一个 key 对应多个 value 的数据结构。看上去它很像 java.util.Map 的结构，但是 Muitimap 不是 Map，没有实现 Map 的接口。
		设想你对 Map 调了 2 次参数 key 一样的 put 方法，结果就是第 2 次的 value 覆盖了第 1 次的 value。
		但是对 Muitimap 来说这个 key 同时对应了 2 个 value。
		所以 Map 看上去是 : {k1=v1, k2=v2,...}，而 Muitimap 是 :{k1=[v1, v2, v3], k2=[v7, v8],....}。
		
	BiMap: 双向 Map

		BiMap 实现了 java.util.Map 接口。
		它的特点是它的 value 和它 key 一样也是不可重复的，换句话说它的 key 和 value 是等价的。
		如果你往 BiMap 的 value 里面放了重复的元素，就会得到 IllegalArgumentException。
		
	MapMaker: 超级强大的 Map 构造工具

		MapMaker 是用来构造 ConcurrentMap 的工具类。为什么可以把 MapMaker 叫做超级强大？看了下面的例子你就知道了。首先，它可以用来构造 ConcurrentHashMap:
		
		 //ConcurrentHashMap with concurrency level 8 
		 ConcurrentMap<String, Object> map1 = new MapMaker() 
		    .concurrencyLevel(8) 
		     .makeMap();
		
		或者构造用各种不同 reference 作为 key 和 value 的 Map:
		
		 //ConcurrentMap with soft reference key and weak reference value 
		 ConcurrentMap<String, Object> map2 = new MapMaker() 
		    .softKeys() 
		    .weakValues() 
		    .makeMap();
		
		或者构造有自动移除时间过期项的 Map:
		
		 //Automatically removed entries from map after 30 seconds since they are created 
		 ConcurrentMap<String, Object> map3 = new MapMaker() 
		    .expireAfterWrite(30, TimeUnit.SECONDS) 
		    .makeMap();
		
		或者构造有最大限制数目的 Map：
		
		 //Map size grows close to the 100, the map will evict 
		 //entries that are less likely to be used again 
		 ConcurrentMap<String, Object> map4 = new MapMaker() 
		    .maximumSize(100) 
		    .makeMap();
		
		或者提供当 Map 里面不包含所 get 的项，而需要自动加入到 Map 的功能。这个功能当 Map 作为缓存的时候很有用 :
		
		 //Create an Object to the map, when get() is missing in map 
		 ConcurrentMap<String, Object> map5 = new MapMaker() 
		    .makeComputingMap( 
		      new Function<String, Object>() { 
		        public Object apply(String key) { 
		          return createObject(key); 
		    }});
		
		这些还不是最强大的特性，最厉害的是 MapMaker 可以提供拥有以上所有特性的 Map:
		
		 //Put all features together! 
		 ConcurrentMap<String, Object> mapAll = new MapMaker() 
		    .concurrencyLevel(8) 
		    .softKeys() 
		    .weakValues() 
		    .expireAfterWrite(30, TimeUnit.SECONDS) 
		    .maximumSize(100) 
		    .makeComputingMap( 
		      new Function<String, Object>() { 
		        public Object apply(String key) { 
		          return createObject(key); 
		     }});
		     
		Ordering class: 灵活的多字段排序比较器
			