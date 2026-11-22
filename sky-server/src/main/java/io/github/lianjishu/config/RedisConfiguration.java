package io.github.lianjishu.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模板对象...");
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置redis的连接工厂对象
        template.setConnectionFactory(redisConnectionFactory);

        //统一声明要用到的序列化器
        //StringRedisSerializer序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //GenericJackson2JsonRedisSerializer序列化器
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(jsonMapper());

        //设置redis key的序列化器
        template.setKeySerializer(stringRedisSerializer);
        //设置redis hash key的序列化器
        template.setHashKeySerializer(stringRedisSerializer);
        //设置redis value的序列化器
        template.setValueSerializer(jsonSerializer);
        //设置redis hash value的序列化器
        template.setHashValueSerializer(jsonSerializer);

        log.info("redis模板对象创建完成...");
        return template;
    }

    /**
     * 自定义 CacheManager，让 @Cacheable/@CachePut 也走 JSON 序列化
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))              // 缓存30分钟
                .disableCachingNullValues()                       // 不缓存null
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(jsonMapper())));

        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }

    /**
     * 缓存专用的 JSON 序列化器所依赖的 ObjectMapper。
     * <p>
     * 1. 必须注册 JavaTimeModule：否则 LocalDateTime 会被当成普通 POJO 序列化，
     *    在开启类型信息（default typing）后抛出
     *    "Type id handling not implemented for type java.lang.Object"，
     *    导致 @Cacheable 写缓存直接失败（DTO 中含 createTime/updateTime 等字段）。
     * 2. 开启类型信息并写入 {@code @class}，反序列化时才能还原成具体的 Result / Category 等类型，
     *    而不是 LinkedHashMap。
     */
    private ObjectMapper jsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 处理 LocalDateTime / LocalDate / LocalTime
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
