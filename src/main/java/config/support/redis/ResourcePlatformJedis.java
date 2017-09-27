/**
 * @CoypRight:nd.com (c) 2017 All Rights Reserved
 * @date 2017年4月1日 上午11:25:34
 * @version V1.0
 */
package config.support.redis;

import config.support.redis.util.ShardedJedises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 资源分发平台-通用缓存服务（实现：String、List类型，后续根据业务补充）
 * </p>
 *
 * @author Yangjs
 */
@Repository
public class ResourcePlatformJedis {
    private static final int TEN_MINUTS = 60 * 10;
    private static final int THREE_MINUTS = 60 * 3;
    @Autowired
    ShardedJedises shardedJedises;
    // 定义物理过期时间（秒为单位，默认10分钟）
    private int physicsCacheSeconds = 60 * 10;

    // 定义逻辑过期时间（秒为单位，默认3分钟）
    private int logicCacheSeconds = 60 * 3;

    /**
     * 初始化构造函数（默认物理过期时间10分钟，逻辑过期时间3分钟）
     */
    public ResourcePlatformJedis() {
        this(TEN_MINUTS, THREE_MINUTS);
    }

    /**
     * 初始化构造函数,设置物理过期实现（默认10分钟）
     *
     * @param physicsCacheSeconds 物理超时时间，0为不超时（单位秒）
     * @return
     */
    public ResourcePlatformJedis(int physicsCacheSeconds) {
        this(physicsCacheSeconds, THREE_MINUTS);
    }

    /**
     * 初始化构造函数,设置逻辑过期实现（默认30分钟）
     *
     * @param physicsCacheSeconds 物理超时时间，0为不超时（单位秒）
     * @param logicCacheSeconds 逻辑超时时间，-2为不超时（单位秒）
     * @return
     */
    public ResourcePlatformJedis(int physicsCacheSeconds, int logicCacheSeconds) {
        this.physicsCacheSeconds = physicsCacheSeconds;
        this.logicCacheSeconds = logicCacheSeconds;
    }

    /**
     * 获取单一字符串缓存，返回Object类型
     *
     * @param identifyKey 唯一缓存键值
     * @return
     */
    public Object get(String identifyKey) {
        return shardedJedises.getObject(identifyKey);
    }

    /**
     * 获取单一字符串缓存是否过期判断
     *
     * @param identifyKey 唯一缓存键值
     * @return
     */
    public boolean isExpire(String identifyKey) {
        if (shardedJedises.getTTL(identifyKey) < logicCacheSeconds) {
            return true;
        }
        return false;
    }

    /**
     * 获取单一字符串缓存，返回Object类型
     *
     * @param identifyKey 唯一缓存键值
     * @param Object 缓存值对象
     * @return
     */
    public Object set(String identifyKey, Object result) {
        shardedJedises.setObject(identifyKey, result, physicsCacheSeconds);
        return result;
    }
}
