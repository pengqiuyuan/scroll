package com.ruoyi.common.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum RedisUtil {
	
    INSTANCE;

    private final JedisPool pool;

    RedisUtil() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(500);
		poolConfig.setMaxIdle(10);
		poolConfig.setMaxWaitMillis(100 * 1000);
		poolConfig.setTestOnBorrow(true);
		pool = new JedisPool(poolConfig, "101.201.227.186", 6009, 60000,"Joke123098");  
    }
    
    public void sadd(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.setex(key, 60 * 60 * 24, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void srem(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.del(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.exists(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    public String sget(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    public Long sincr(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.incr(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    /**
     * 忘记密码记录token使用，1小时内重置密码
     * @param key
     * @param value
     */
    public void saddByPw(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.setex(key, 60 * 60 * 1, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    /**
     * 查询key的剩余生存时间
     * @param key
     * @return
     */
    public Long sttl(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.ttl(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}