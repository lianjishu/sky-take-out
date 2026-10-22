package io.github.lianjishu.config;

import io.github.lianjishu.properties.AliOssProperties;
import io.github.lianjishu.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS工具类配置类，用于创建AliOssUtil的Bean，方便在其他类中注入使用，实现文件上传功能
 */
@Slf4j
@Configuration
@Component
public class AliOssUtilConfiguration {

    /**
     * 创建AliOssUtil实例
     * @param aliOssProperties 阿里云OSS属性
     * @return 阿里云OSS工具类实例
     */
    @Bean
    @ConditionalOnMissingBean
    AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("创建AliOssUtil实例...");
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }
}
