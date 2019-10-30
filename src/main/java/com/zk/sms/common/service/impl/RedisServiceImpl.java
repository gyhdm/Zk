package com.zk.sms.common.service.impl;

import com.zk.sms.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The type Redis service.
 *
 * @author guoying
 * @since 2019 -10-29 15:19:21
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * The Redis template.
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void scan(final String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<String> keys(final String pattern) {
        List<String> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            //符合条件的key
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }

    @Override
    public List<String> getAllKeys() {
        return this.keys("*");
    }

    @Override
    public boolean existsKey(String key) {
        Boolean aBoolean = redisTemplate.hasKey(key);
        return aBoolean != null && aBoolean;
    }

    @Override
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    @Override
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        Boolean aBoolean = redisTemplate.renameIfAbsent(oldKey, newKey);
        return aBoolean != null && aBoolean;
    }

    @Override
    public boolean setExpireTime(String key, long time, TimeUnit timeUnit) {
        Boolean expire = redisTemplate.expire(key, time, timeUnit);
        return expire != null && expire;
    }

    @Override
    public boolean remove(String key) {
        Boolean delete = redisTemplate.delete(key);
        return delete != null && delete;
    }

    @Override
    public Long remove(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public boolean setExpireTimeAt(String key, Date date) {
        Boolean aBoolean = redisTemplate.expireAt(key, date);
        return aBoolean != null && aBoolean;
    }

    @Override
    public long getExpireTime(String key, TimeUnit timeUnit) {
        Long expire = redisTemplate.getExpire(key, timeUnit);
        return expire == null ? -1 : expire;
    }

    @Override
    public boolean persistKey(String key) {
        Boolean persist = redisTemplate.persist(key);
        return persist != null && persist;
    }

    @Override
    public ValueOperations<String, Object> valueOperations() {
        return redisTemplate.opsForValue();
    }

    @Override
    public ListOperations<String,Object> listOperations() {
        return redisTemplate.opsForList();
    }

    @Override
    public HashOperations<String, Object, Object> hashOperations() {
        return redisTemplate.opsForHash();
    }

    @Override
    public SetOperations<String, Object> setOperations() {
        return redisTemplate.opsForSet();
    }

    @Override
    public ZSetOperations<String, Object> zSetOperations() {
        return redisTemplate.opsForZSet();
    }
}
