package io.github.lianjishu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

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
}
