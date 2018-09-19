package com.matrix.cache.redis.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.map.MDataMap;

/**
 * @descriptions 针对缓存提供基本的增删改查操作 
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年12月12日 下午10:50:56
 * @version 1.0.1
 */
public class RedisFactory implements ICacheFactory{  

	private String baseKey = "";
	private String load = "";  // ILoadCache		
	public RedisFactory(String sKey , String load) {
		if(StringUtils.isBlank(load)) {
			load = "";
		}
		baseKey = sKey;
		this.load = "com.matrix.load." + load;
	}
	
	/////////////////////////////////////////////////////////////////// 基础json //////////////////////////////////////////////////////////////////////
	/**
	 * @description: 返回 key 所关联的字符串值。
	 *
	 * @param key 如果 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
	 * @author Yangcl
	 * @date 2018年9月18日 下午5:24:33 
	 * @version 1.0.0.1
	 */
	public String get(String key) {
		String value = RedisTemplate.getInstance().get(baseKey + key);
		if(StringUtils.isBlank(value)) {
			synchronized (CacheLaunch.class) {
				value = RedisTemplate.getInstance().get(baseKey + key);
				if(StringUtils.isBlank(value)) {
					if(this.load.length() == 16) {
						return "";
					}
					try {
						Class<?> clazz = Class.forName(load);   
						if (clazz != null && clazz.getDeclaredMethods() != null){
							ILoadCache cache = (ILoadCache) clazz.newInstance();
							return cache.load(key , "");
						}else {
							return "";
						}
					}catch (Exception e) {
						e.printStackTrace();
						return "";
					}    
				}else {
					return value;
				}
			}
		}else {
			return value;
		}
	}

	/**
	 * @description: 将字符串值 value 关联到 key 。
	 *		对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 *
	 * @param key	如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param value
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午5:26:44 
	 * @version 1.0.0.1
	 */
	public void set(String key, String value) {
		RedisTemplate.getInstance().set(key, value);
	}

	/**
	 * @description: 将字符串值 value 关联到 key 。带自定义超时时间
	 *
	 * @param key
	 * @param value
	 * @param timeout 
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:19:51 
	 * @version 1.0.0.1
	 */
	public void set(String key, String value, long timeout) {
		RedisTemplate.getInstance().set(key, value, timeout);
	}

	/**
	 * @description: 将字符串值 value 关联到 key 。带默认超时时间30天
	 *
	 * @param key
	 * @param value
	 * @param timeout 
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:19:51 
	 * @version 1.0.0.1
	 */
	public void setDefalutTimeout(String key, String value) {
		RedisTemplate.getInstance().setDefalutTimeout(key, value); 
	}

	/**
	 * @description: 在原有的值基础上新增字符串到末尾。
	 *
	 * @param key
	 * @param value 
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:42:07 
	 * @version 1.0.0.1
	 */
	public Integer appendStringInEnd(String key, String value) {
		return RedisTemplate.getInstance().appendStringInEnd(key, value);
	}

	/**
	 * @description: 获取原来key键对应的值并重新赋新值。
	 *
	 * @param key
	 * @param value
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:46:33 
	 * @version 1.0.0.1
	 */
	public String getAndSet(String key, String value) {
		return RedisTemplate.getInstance().getAndSet(key, value);
	}

	/**
	 * @description: 获取指定字符串的长度。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午7:05:22 
	 * @version 1.0.0.1
	 */
	public Long size(String key) {
		return RedisTemplate.getInstance().size(key); 
	}

	/**
	 * @description: 以增量的方式将long值存储在变量中。
	 *
	 * @param key
	 * @param delta
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:06:42 
	 * @version 1.0.0.1
	 */
	public Long increment(String key, Long delta) {
		return RedisTemplate.getInstance().increment(key, delta); 
	}

	/**
	 * @description: 删除缓存信息
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:15:00 
	 * @version 1.0.0.1
	 */
	public void del(String key) {
		RedisTemplate.getInstance().del(key); 
	}
	
	
	/////////////////////////////////////////////////////////////////// 哈希存储 //////////////////////////////////////////////////////////////////////	
	/**
	 * @description: 返回哈希表 key 中给定域 field 的值。
	 *
	 * @param key
	 * @param field
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:53:28 
	 * @version 1.0.0.1
	 */
	public Object hget(String key, String field) {
		return RedisTemplate.getInstance().hget(key, field); 
	}

	/**
	 * @description: 返回哈希表 key 中，所有的域和值。
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:52:39 
	 * @version 1.0.0.1
	 */
	public Map<Object, Object> hgetAll(String key) {
		return RedisTemplate.getInstance().hgetAll(key); 
	}

	/**
	 * @description: 将哈希表key中的域 field 的值设为value。如果 key 不存在，
	 * 		一个新的哈希表被创建并进行 HSET 操作。如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 *	
	 * 例如：	
	 *			redisTemplate.opsForHash().put("hashValue","map1","map1-1");  
	 *			redisTemplate.opsForHash().put("hashValue","map2","map2-2"); 
	 *
	 * @param key
	 * @param field
	 * @param value 
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:26:13 
	 * @version 1.0.0.1
	 */
	public void hset(String key, String field, String value) {
		RedisTemplate.getInstance().hset(key, field, value);
	}

	/**
	 * @description: 以map集合的形式添加键值对。
	 * 例如：
	 			Map newMap = new HashMap();  
				newMap.put("map3","map3-3");  
				newMap.put("map5","map5-5");  
				redisTemplate.opsForHash().putAll("hashValue",newMap);
				Map map = redisTemplate.opsForHash().entries("hashValue");
	 *			
	 * @param key
	 * @param map 
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:34:31 
	 * @version 1.0.0.1
	 */
	public void hsetAll(String key, Map<String, Object> map) {
		RedisTemplate.getInstance().hsetAll(key, map);
	}

	/**
	 * @description: 删除变量中的键值对，可以传入多个参数，删除多个键值对
	 * 例如：	
	 *			redisTemplate.opsForHash().delete("hashValue","map1","map2");
	 *
	 * @param key
	 * @param field  建议使用字符串
	 * @return 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:39:20 
	 * @version 1.0.0.1
	 */
	public Long hdel(String key, Object... field) {
		return RedisTemplate.getInstance().hdel(key, field); 
	}

	/**
	 * @description: 判断变量中是否有指定的map键。
	 *
	 * @param key
	 * @param hashKey
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:46:24 
	 * @version 1.0.0.1
	 */
	public Boolean hkeyExist(String key, Object hashKey) {
		return RedisTemplate.getInstance().hkeyExist(key, hashKey); 
	}

	
	/////////////////////////////////////////////////////////////////// 无序Set存储|交叉并操作暂未提供 //////////////////////////////////////////////////////////////////////
	
	/**
	 * @description: 添加一个set集合到redis中
	 *	例如：
	 *			redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");  
	 * @param key
	 * @param values 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:22:26 
	 * @version 1.0.0.1
	 */
	public Long addSet(String key, String... values) {
		return RedisTemplate.getInstance().addSet(key, values);
	}

	/**
	 * @description: 获取变量中的值。
	 *
	 * @param key 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:27:10 
	 * @version 1.0.0.1
	 */
	public Set<String> sget(String key) {
		return RedisTemplate.getInstance().sget(key);
	}

	/**
	 * @description: 获取set集合的长度
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:30:26 
	 * @version 1.0.0.1
	 */
	public Long setLength(String key) {
		return RedisTemplate.getInstance().setLength(key);
	}

	/**
	 * @description: 随机获取set集合中的元素。
	 *
	 * @param key 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:32:22 
	 * @version 1.0.0.1
	 */
	public String setRandomMember(String key) {
		return RedisTemplate.getInstance().setRandomMember(key); 
	}

	/**
	 * @description: 随机获取set集合中指定个数的元素。
	 *
	 * @param key 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:32:22 
	 * @version 1.0.0.1
	 */
	public List<String> setRandomMembers(String key, long count) {
		return RedisTemplate.getInstance().setRandomMembers(key, count);
	}

	/**
	 * @description: 检查给定的元素是否在set集合中。
	 *	例如：
	 *			boolean isMember = redisTemplate.opsForSet().isMember("setValue","A"); 
	 * @param key
	 * @param value 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:37:15 
	 * @version 1.0.0.1
	 */
	public Boolean isInSet(String key, String value) {
		return RedisTemplate.getInstance().isInSet(key, value); 
	}

	/**
	 * @description: 批量移除set集合中的元素。
	 *
	 * @param key
	 * @param objects 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:41:34 
	 * @version 1.0.0.1
	 */
	public Long setElementRemove(String key, Object... objects) {
		return RedisTemplate.getInstance().setElementRemove(key, objects);
	}

	/////////////////////////////////////////////////////////////////// 有序Set存储|交叉并操作暂未提供 //////////////////////////////////////////////////////////////////////
	/**
	 * @description: 添加一个元素到指定的有序set集合中
	 *	例如：
	 *			redisTemplate.opsForZSet().add("zSetValue","A",1); 
	 *			redisTemplate.opsForZSet().add("zSetValue","B",3);
	 *			redisTemplate.opsForZSet().add("zSetValue","C",2);
	 *			redisTemplate.opsForZSet().add("zSetValue","D",5);
	 *
	 * @param key
	 * @param values 
	 * @param sort 顺序集合中的排序权重
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:22:26 
	 * @version 1.0.0.1
	 */
	public Boolean addZset(String key, String value, double sort) {
		return RedisTemplate.getInstance().addZset(key, value, sort);
	}

	/**
	 * @description: 获取有序set集合中指定区间(set游标为依据)的元素。
	 *	例如：
	 *			Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
	 *
	 * @param key
	 * @param start
	 * @param end 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:59:46 
	 * @version 1.0.0.1
	 */
	public Set<String> zsetRangeIndex(String key, long start, long end) {
		return RedisTemplate.getInstance().zsetRangeIndex(key, start, end);
	}

	/**
	 * @description: 根据设置的score获取序set集合中的区间值。
	 *
	 * @param key
	 * @param min
	 * @param max
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:04:08 
	 * @version 1.0.0.1
	 */
	public Set<String> zsetRangeByScore(String key, double min, double max) {
		return RedisTemplate.getInstance().zsetRangeByScore(key, min, max);
	}

	/**
	 * @description: 获取区间值的个数。
	 *
	 * @param key
	 * @param min
	 * @param max 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:08:35 
	 * @version 1.0.0.1
	 */
	public Long zsetCount(String key, double min, double max) {
		return RedisTemplate.getInstance().zsetCount(key, min, max); 
	}

	/**
	 * @description: 获取元素的分值。
	 *
	 * @param key
	 * @param value 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:10:31 
	 * @version 1.0.0.1
	 */
	public Double zsetScore(String key, String value) {
		return RedisTemplate.getInstance().zsetScore(key, value);
	}

	/**
	 * @description: 获取变量中元素的个数。
	 *
	 * @param key 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:12:19 
	 * @version 1.0.0.1
	 */
	public Long zsetSize(String key) {
		return RedisTemplate.getInstance().zsetSize(key);
	}

	/**
	 * @description: 批量移除元素根据元素值。
	 *
	 * @param key
	 * @param values 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:13:51 
	 * @version 1.0.0.1
	 */
	public Long zsetRemove(String key, Object... values) {
		return RedisTemplate.getInstance().zsetRemove(key, values);
	}

	/**
	 * @description: 根据分值移除区间元素。
	 *
	 * @param key
	 * @param min
	 * @param max
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:15:57 
	 * @version 1.0.0.1
	 */
	public Long zsetRemoveRangeByScor(String key, double min, double max) {
		return RedisTemplate.getInstance().zsetRemoveRangeByScor(key, min, max);
	}

	/**
	 * @description: 根据索引值移除区间元素。
	 *
	 * @param key
	 * @param start 有序set集合索引
	 * @param end	有序set集合索引
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:17:30 
	 * @version 1.0.0.1
	 */
	public Long zsetRemoveRange(String key, long start, long end) {
		return RedisTemplate.getInstance().zsetRemoveRange(key, start, end);
	}
	

}



















































