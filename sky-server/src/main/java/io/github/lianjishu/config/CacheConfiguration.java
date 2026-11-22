package io.github.lianjishu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存相关配置
 */
@Configuration
@Slf4j
public class CacheConfiguration implements CachingConfigurer {

    private final CacheManager cacheManager;

    public CacheConfiguration(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public CacheManager cacheManager() {
        return cacheManager;
    }

    /**
     * 缓存出错时的降级处理。
     * <p>
     * 默认的 SimpleCacheErrorHandler 会把异常继续抛出，导致接口直接 500。
     * 这里改成"记录日志 + 忽略该条缓存"：例如 Redis 中残留了旧序列化格式的脏数据
     * （之前用 JDK 序列化写入、现在用 JSON 序列化读取），此时按缓存未命中处理，
     * 由业务方法重新查库并把新格式的数据覆盖写回缓存，接口不会因此报错。
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.warn("读取缓存失败，按缓存未命中处理。cache={}, key={}, 原因: {}",
                        cache.getName(), key, exception.getMessage());
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.warn("写入缓存失败，本次不缓存。cache={}, key={}, 原因: {}",
                        cache.getName(), key, exception.getMessage());
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.warn("清理缓存失败。cache={}, key={}, 原因: {}",
                        cache.getName(), key, exception.getMessage());
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.warn("清空缓存失败。cache={}, 原因: {}", cache.getName(), exception.getMessage());
            }
        };
    }
}
