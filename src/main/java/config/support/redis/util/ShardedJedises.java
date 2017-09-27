/**
 * @CoypRight:nd.com (c) 2017 All Rights Reserved
 * @date 2017年4月1日 上午11:13:17
 * @version V1.0
 */
package config.support.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.*;
import java.util.*;

/**
 * <p>
 * 分片实例缓存，基于hash分布式缓存方案，存储业务数据（如：商品详细，排行榜）
 * </p>
 *
 * @author Yangjs
 */
@Repository
public class ShardedJedises {
    private static final int THREE_MINUTS = 60 * 3;
    @Qualifier("shardedJedisPool")
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    private Logger logger = LoggerFactory.getLogger(ShardedJedises.class);

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        String value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.get(key);

                value = !StringUtils.isEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                logger.debug("get {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("get {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 返回key剩余的过期时间
     *
     * @param key 键
     * @return
     */
    public long getTTL(String key) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.ttl(key);
            logger.debug("exists {}", key);
        } catch (Exception e) {
            logger.warn("exists {}", key, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object getObject(String key) {
        Object value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = toObject(shardedJedis.get(getBytesKey(key)));
                logger.debug("getObject {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObject {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 设置缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public String set(String key, String value, int cacheSeconds) {
        String result = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.set(key, value);
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("set {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("set {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 设置缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.set(getBytesKey(key), toBytes(value));
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setObject {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObject {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public List<String> getList(String key) {
        List<String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.lrange(key, 0, -1);
                logger.debug("getList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getList {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public List<Object> getObjectList(String key) {
        List<Object> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                List<byte[]> list = shardedJedis.lrange(getBytesKey(key), 0, -1);
                value = new ArrayList<Object>();
                for (byte[] bs : list) {
                    value.add(toObject(bs));
                }
                logger.debug("getObjectList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObjectList {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 设置List缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                shardedJedis.del(key);
            }
            result = shardedJedis.rpush(key, (String[]) value.toArray(new String[value.size()]));
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setList {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setList {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 设置List缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                shardedJedis.del(key);
            }
            List<byte[]> list = new ArrayList<byte[]>();
            for (Object o : value) {
                list.add(toBytes(o));
            }
            result = shardedJedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setObjectList {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObjectList {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long listAdd(String key, String... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.rpush(key, value);
            logger.debug("listAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("listAdd {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long listObjectAdd(String key, Object... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            List<byte[]> list = new ArrayList<byte[]>();
            for (Object o : value) {
                list.add(toBytes(o));
            }
            result = shardedJedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
            logger.debug("listObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("listObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Set<String> getSet(String key) {
        Set<String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.smembers(key);
                logger.debug("getSet {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getSet {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> getObjectSet(String key) {
        Set<Object> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = new HashSet<Object>();
                Set<byte[]> set = shardedJedis.smembers(getBytesKey(key));
                for (byte[] bs : set) {
                    value.add(toObject(bs));
                }
                logger.debug("getObjectSet {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObjectSet {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 设置Set缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setSet(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                shardedJedis.del(key);
            }
            result = shardedJedis.sadd(key, (String[]) value.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setSet {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSet {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 设置Set缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                shardedJedis.del(key);
            }
            Set<byte[]> set = new HashSet<byte[]>();
            for (Object o : value) {
                set.add(toBytes(o));
            }
            result = shardedJedis.sadd(getBytesKey(key), (byte[][]) set.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setObjectSet {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObjectSet {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long setSetAdd(String key, String... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.sadd(key, value);
            logger.debug("setSetAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSetAdd {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            Set<byte[]> set = new HashSet<byte[]>();
            for (Object o : value) {
                set.add(toBytes(o));
            }
            result = shardedJedis.rpush(getBytesKey(key), (byte[][]) set.toArray());
            logger.debug("setSetObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSetObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向Dict缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long setDict(String key, String filed, String value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.hset(key, filed, value);
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setSetObjectAdd {}.{} = {}", key, filed, value);
        } catch (Exception e) {
            logger.warn("setSetObjectAdd {}.{} = {}", key, filed, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 获取dict缓存
     *
     * @param key 键
     * @return 值
     */
    public String getDict(String key, String filed) {
        String value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.hget(key, filed);

                value = !StringUtils.isEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                logger.debug("get {}.{} = {}", key, filed, value);
            }
        } catch (Exception e) {
            logger.warn("get {}.{} = {}", key, filed, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    public Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.hgetAll(key);
                logger.debug("getMap {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getMap {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    public Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = new HashMap<String, Object>();
                Map<byte[], byte[]> map = shardedJedis.hgetAll(getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
                    value.put(e.getKey().toString(), toObject(e.getValue()));
                }
                logger.debug("getObjectMap {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObjectMap {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return value;
    }

    /**
     * 设置Map缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public String setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                shardedJedis.del(key);
            }
            result = shardedJedis.hmset(key, value);
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setMap {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setMap {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 设置Map缓存
     *
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                shardedJedis.del(key);
            }
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = shardedJedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, getRandom(cacheSeconds));
            }
            logger.debug("setObjectMap {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObjectMap {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public String mapPut(String key, Map<String, String> value) {
        String result = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.hmset(key, value);
            logger.debug("mapPut {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("mapPut {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public String mapObjectPut(String key, Map<String, Object> value) {
        String result = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = shardedJedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
            logger.debug("mapObjectPut {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("mapObjectPut {} = {}", key, value, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long mapRemove(String key, String mapKey) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.hdel(key, mapKey);
            logger.debug("mapRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapRemove {}  {}", key, mapKey, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.hdel(getBytesKey(key), getBytesKey(mapKey));
            logger.debug("mapObjectRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean mapExists(String key, String mapKey) {
        boolean result = false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.hexists(key, mapKey);
            logger.debug("mapExists {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapExists {}  {}", key, mapKey, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean mapObjectExists(String key, String mapKey) {
        boolean result = false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.hexists(getBytesKey(key), getBytesKey(mapKey));
            logger.debug("mapObjectExists {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapObjectExists {}  {}", key, mapKey, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    public long del(String key) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(key)) {
                result = shardedJedis.del(key);
                logger.debug("del {}", key);
            } else {
                logger.debug("del {} not exists", key);
            }
        } catch (Exception e) {
            logger.warn("del {}", key, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    public long delObject(String key) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                result = shardedJedis.del(getBytesKey(key));
                logger.debug("delObject {}", key);
            } else {
                logger.debug("delObject {} not exists", key);
            }
        } catch (Exception e) {
            logger.warn("delObject {}", key, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public boolean exists(String key) {
        boolean result = false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.exists(key);
            logger.debug("exists {}", key);
        } catch (Exception e) {
            logger.warn("exists {}", key, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public boolean existsObject(String key) {
        boolean result = false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            result = shardedJedis.exists(getBytesKey(key));
            logger.debug("existsObject {}", key);
        } catch (Exception e) {
            logger.warn("existsObject {}", key, e);
        } finally {
            returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 获取资源
     *
     * @return
     * @throws JedisException
     */
    public ShardedJedis getResource() throws JedisException {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();

            // logger.debug("getResource.", ShardedJedis);
        } catch (JedisException e) {
            logger.warn("getResource.", e);
            returnBrokenResource(shardedJedis);
            throw e;
        }
        return shardedJedis;
    }

    /**
     * 归还资源
     *
     * @param ShardedJedis
     * @param isBroken
     */
    @SuppressWarnings("deprecation")
    public void returnBrokenResource(ShardedJedis shardedJedis) {
        if (shardedJedis != null) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        }
    }

    /**
     * 释放资源
     *
     * @param ShardedJedis
     * @param isBroken
     */
    @SuppressWarnings("deprecation")
    public void returnResource(ShardedJedis shardedJedis) {
        if (shardedJedis != null) {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    /**
     * 获取缓存过期时间带随机性（防缓存雪崩）
     *
     * @param 秒 seconds
     * @return
     */
    public int getRandom(int seconds) {
        java.util.Random r = new java.util.Random();
        return seconds + r.nextInt(THREE_MINUTS);
    }

    /**
     * 获取byte[]类型Key
     *
     * @param key
     * @return
     */
    public byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            return ((String) object).getBytes();

        } else {
            return toBytes(object);

        }
    }

    /**
     * Object转换byte[]类型
     *
     * @param key
     * @return
     */
    public byte[] toBytes(Object object) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * byte[]型转换Object
     *
     * @param key
     * @return
     */
    public Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

}
