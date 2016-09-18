package com.wed.moudles.memcached;

import java.util.Map;

import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * 
 * MemcachedSample.java
 * @author wed
 * @date 2016年9月14日  上午10:28:20
 * @description
 * memcache本身并不是分布式的缓存系统，它的分布式是由客户端来实现的。一种比较简单的实现是根据缓存的key来进行Hash，当后端有N台缓存服务器时，访问的服务器为hash(key)%N，
 * 这样可以将前端的请求均衡地映射到后端的缓存服务器。问题：一旦某台缓存服务器宕机，或者由于集群压力过大，需要新增缓存服务器时，大部分的key将会重新发布。对于高并发系统来说，这可能
 * 会演变成一场灾难，所有的请求将如洪水般涌向后端的数据库服务器，而数据库的不可用，将会导致整个应用的不可用，形成所谓的“雪崩效应”。
 * consistent Hash算法可以改善上述问题，它能够在移除/添加一台缓存服务器时，尽可能小地改变已存在的key映射关系，避免大量key的重新映射。
 * 	原理：值域空间组织成一个圆环，顺时针方向进行组织，对相应的服务器节点进行Hash，映射到Hash环上。虚拟节点机制。。。
 *  启动服务：memcached -d -m 100 -u root -l 127.0.0.1 -p 11211 -c 256 -P /tmp/memcached.pid
 * 说明：
 * 	1、
 */
public class MemcachedSample {

	public static void main(String[] args) {
		init();
		setData();
		getData();
	}
	
	public static void init(){
		String[] servers = {"192.168.137.10:11211"};
	
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);	//设置服务器
		pool.setFailover(true);		//设置容错
		pool.setInitConn(10);		//设置初始连接数
		pool.setMinConn(5);			//设置最小连接数
		pool.setMaxConn(25);		//设置最大连接数
		pool.setMaintSleep(30);		//设置连接池维护线程的睡眠时间
		pool.setNagle(false);		//设置是否使用Nagle算法
		pool.setSocketTO(3000);		//设置socket的读取等待超时时间
		pool.setAliveCheck(true);	//设置连接心跳监测开关
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);	//设置Hash算法
		pool.initialize();
	}
	
	private static MemCachedClient memCachedClient = new MemCachedClient();
	
	/**
	 * 存值
	 */
	public static void setData(){
		memCachedClient.add("name", "aa");		//新增缓存，如果缓存服务器存在同样的key，返回false
		memCachedClient.set("name", "bb");		//保存，如果缓存服务器存在同样的key，将其替换
		memCachedClient.replace("name","cc");	//替换，替换相同key的值，如果不存在这样的key，返回false
		
		memCachedClient.prepend("name", "myname is ");	//前缀
		memCachedClient.append("name", ",age is 10.");	//后缀

		//cas操作
		MemcachedItem item = memCachedClient.gets("name");	//gets获取key对应的值和值的版本号
		memCachedClient.cas("name", item.getValue()+"to be continue...", item.getCasUnique());	//当key对应的版本号和通过gets取到的版本号相同时，则将key对应的值修改为item.getValue()+"to be continue..."，这样可以防止并发修改带来的问题
		
		memCachedClient.incr("name", 1);	//增量
		memCachedClient.decr("name", 1);	//减量
	}
	
	/**
	 * 取值
	 */
	public static void getData(){
		//获取单个数据
		Object value = memCachedClient.get("name");
		System.out.println(value);
		//获取一组数据
		String[] keys = {"key1","key2"};
		Map<String,Object> values = memCachedClient.getMulti(keys);		
		System.out.println(values);
	}
}

