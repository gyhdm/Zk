package com.zk.sms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * The type Redis cache config.
 *
 * @author guoying
 * @since 2019 -10-29 23:44:26
 */
@Slf4j
@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisCacheConfig {

    /**
     * The Time to live.
     */
    private Duration timeToLive = Duration.ZERO;

    /**
     * Cache manager redis cache manager.
     *
     * @param connectionFactory the connection factory
     * @return the redis cache manager
     * @author guoying
     * @since 2019 /10/29
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues();
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
        log.debug("自定义RedisCacheManager加载完成");
        return redisCacheManager;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        log.debug("自定义RedisTemplate加载完成");
        return redisTemplate;
    }

    /**
     * Value serializer redis serializer.
     *
     * @return the redis serializer
     * @author guoying
     * @since 2019 /10/29
     */
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * Key serializer redis serializer.
     *
     * @return the redis serializer
     * @author guoying
     * @since 2019 /10/29
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }
}
