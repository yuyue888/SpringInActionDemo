/**
 * @CoypRight:nd.com (c) 2017 All Rights Reserved
 * @date 2017年4月1日 上午11:10:28
 * @version V1.0
 */
package config.support.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * <p>
 * 单实例Redis缓存，用于存储公共数据（如：分布式锁、字典数据）
 * </p>
 *
 * @author Yangjs
 */
@Repository
public class SingletonJedis {

    @Qualifier("jedisPool")
    @Autowired
    private JedisPool jedisPool;
    private Logger logger = LoggerFactory.getLogger(SingletonJedis.class);

    /**
     * 获取资源
     *
     * @return
     * @throws JedisException
     */
    public synchronized Jedis getResource() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            logger.warn("getResource.", e);
            returnBrokenResource(jedis);
            throw e;
        }
        return jedis;
    }

    /**
     * 归还资源
     *
     * @param ShardedJedises
     * @param isBroken
     */
    @SuppressWarnings("deprecation")
    public void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     *
     * @param ShardedJedises
     * @param isBroken
     */
    @SuppressWarnings("deprecation")
    public void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
