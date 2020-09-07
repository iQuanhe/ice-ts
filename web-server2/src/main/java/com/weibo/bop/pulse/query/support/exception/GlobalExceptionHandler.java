package main.java.com.weibo.bop.pulse.query.support.exception;

import com.weibo.common.exception.BaseException;
import com.weibo.common.pojo.ErrorCode;
import com.weibo.common.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截处理类
 *
 * @author shaowei3
 * @create 2019-01-13
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        logger.error("GlobalExceptionHandler exceptionHandler Exception {}", e);
        return Result.error(ErrorCode.API_SERVICE_ERROR);
    }

    @ExceptionHandler(value = BaseException.class)
    public Result baseExceptionHandler(BaseException e) {
        logger.error("GlobalExceptionHandler exceptionHandler Exception {}", e);
        return Result.error(e.getCode(), e.getMsg());
    }
}
