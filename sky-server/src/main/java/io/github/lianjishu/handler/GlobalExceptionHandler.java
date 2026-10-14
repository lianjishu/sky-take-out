package io.github.lianjishu.handler;

import io.github.lianjishu.constant.MessageConstant;
import io.github.lianjishu.exception.BaseException;
import io.github.lianjishu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex 异常对象
     * @return 处理结果
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL完整性约束异常
     *
     * @param ex 异常对象
     * @return 处理结果
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String exMsg = ex.getMessage();
        if (exMsg.contains("Duplicate entry")) {
            String entry = exMsg.split(" ")[2];
            String msg = entry + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
