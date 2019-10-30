package com.zk.sms.common.service;

import org.springframework.data.redis.core.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The type Redis service.
 *
 * @author guoying
 * @since 2019 -10-29 15:03:54
 */
public interface RedisService {

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;


    /**
     * Scan.
     * keys * 这个命令千万别在生产环境乱用。特别是数据庞大的情况下。因为Keys会引发Redis锁，并且增加Redis的CPU占用。很多公司的运维都是禁止了这个命令的
     * 当需要扫描key，匹配出自己需要的key时，可以使用 scan 命令
     *
     * @param pattern  the pattern
     * @param consumer 对迭代到的key进行操作
     * @author guoying
     * @since 2019 /10/29
     */
    void scan(String pattern, Consumer<byte[]> consumer);

    /**
     * 根据key pattern 查询所有key.
     *
     * @param pattern the pattern
     * @return the list
     * @author guoying
     * @since 2019 /10/29
     */
    List<String> keys(String pattern);

    /**
     * 获取所有key.
     *
     * @return the all keys
     */
    List<String> getAllKeys();

    /**
     * 判断是否有key对应的值.
     *
     * @param key the key
     * @return the boolean
     * @author guoying
     * @since 2019 /10/29
     */
    boolean existsKey(final String key);

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    void renameKey(String oldKey, String newKey);

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    boolean renameKeyNotExist(String oldKey, String newKey);


    /**
     * 设置key的生命周期.
     *
     * @param key      the key
     * @param time     the time
     * @param timeUnit the time unit
     * @return the expire time
     */
    boolean setExpireTime(String key, long time, TimeUnit timeUnit);

    /**
     * 删除key.
     *
     * @param key the key
     * @return the boolean
     * @author guoying
     * @since 2019 /10/29
     */
    boolean remove(String key);

    /**
     * 删除key的集合.
     *
     * @param keys the keys
     * @return the long
     * @author guoying
     * @since 2019 /10/29
     */
    Long remove(Collection<String> keys);


    /**
     * 设置key在指定时间过期.
     *
     * @param key  the key
     * @param date the date
     * @return the expire time at
     */
    boolean setExpireTimeAt(String key, Date date);

    /**
     * 查询key的生命周期.
     *
     * @param key      the key
     * @param timeUnit the time unit
     * @return the expire time
     */
    long getExpireTime(String key, TimeUnit timeUnit);

    /**
     * 将key设置为永久有效
     *
     * @param key
     * @return 是否设置成功
     */
    boolean persistKey(String key);

    /**
     * Value operations value operations.
     *
     * @return the value operations
     * @author guoying
     * @since 2019 /10/30
     */
    ValueOperations<String, Object> valueOperations();

    /**
     * List operations list operations.
     *
     * @return the list operations
     * @author guoying
     * @since 2019 /10/30
     */
    ListOperations<String,Object> listOperations();

    /**
     * Hash operations hash operations.
     *
     * @return the hash operations
     * @author guoying
     * @since 2019 /10/30
     */
    HashOperations<String, Object, Object> hashOperations();

    /**
     * Sets operations.
     *
     * @return the operations
     */
    SetOperations<String, Object> setOperations();

    /**
     * Z set operations z set operations.
     *
     * @return the z set operations
     * @author guoying
     * @since 2019 /10/30
     */
    ZSetOperations<String, Object> zSetOperations();
}
