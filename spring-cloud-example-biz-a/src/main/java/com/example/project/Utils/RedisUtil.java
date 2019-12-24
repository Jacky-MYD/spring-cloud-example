/**
 * @authour Jacky
 * @data Dec 21, 2019
 */
package com.example.project.Utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Jacky
 *
 */
public class RedisUtil {
	private JedisPool jedisPool;

	/**
	 * @param jedisPool the jedisPool to set
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	// 添加
	public void set(String key, String value) {
		Jedis jedis = this.jedisPool.getResource();
		jedis.set(key, value);
		this.jedisPool.close();
	}
	
	// 添加，带超时时间
	public void setex(String key, int seconds, String value) {
		Jedis jedis = this.jedisPool.getResource();
		jedis.setex(key, seconds, value);
		this.jedisPool.close();
	}
	
	// 获取
	public String get(String key) {
		Jedis jedis = this.jedisPool.getResource();
		String value = jedis.get(key);
		this.jedisPool.close();
		return value;
	}
	
	// 查看某个键是否存在
	public boolean exists(String key) {
		Jedis jedis = this.jedisPool.getResource();
		Boolean exists = jedis.exists(key);
		this.jedisPool.close();
		return exists;
	}
	
	// 查看超时时间
	public Long ttl(String key) {
		Jedis jedis = this.jedisPool.getResource();
		Long ttl = jedis.ttl(key);
		this.jedisPool.close();
		return ttl;
	}
	
	// 删除
	public void del(String key) {
		Jedis jedis = this.jedisPool.getResource();
		jedis.del(key);
		this.jedisPool.close();
	}
}
