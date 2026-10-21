package io.github.lianjishu.annotation;

import io.github.lianjishu.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动填充注解 | 用于标识需要进行公共字段填充的方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoFill {
    /**
     * 操作类型
     * @return 操作类型
     */
    OperationType value();
}
