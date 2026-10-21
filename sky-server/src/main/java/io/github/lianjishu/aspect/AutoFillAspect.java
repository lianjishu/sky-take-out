package io.github.lianjishu.aspect;

import io.github.lianjishu.annotation.AutoFill;
import io.github.lianjishu.context.BaseContext;
import io.github.lianjishu.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* io.github.lianjishu.mapper.*.*(..)) && @annotation(io.github.lianjishu.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint 切点
     * @return 处理结果
     * @throws Throwable 异常
     */
    @Before("autoFillPointCut()")
    public Object around(JoinPoint joinPoint) throws Throwable {
        log.info("开始进行公共字段的自动填充...");

        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //获取到当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) { //方法没参数
            return null;
        }
        Object entity = args[0]; //实体对象
        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long operatedUserId = BaseContext.getCurrentId();

        //根据当前不同的操作类型，为对应的属性通过反射来赋值
        if (operationType == OperationType.INSERT) { //插入操作
            try {
                //为4个公共字段赋值
                entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class).invoke(entity, now);
                entity.getClass().getDeclaredMethod("setCreateUser", Long.class).invoke(entity, operatedUserId);
                entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(entity, now);
                entity.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(entity, operatedUserId);
            } catch (Exception e) {
                log.error("反射赋值公共字段出错...", e);
            }
        } else if (operationType == OperationType.UPDATE) { //更新操作
            try {
                //为2个公共字段赋值
                entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(entity, now);
                entity.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(entity, operatedUserId);
            } catch (Exception e) {
                log.error("反射赋值公共字段出错...", e);
            }
        }

        return new Object();
    }
}
