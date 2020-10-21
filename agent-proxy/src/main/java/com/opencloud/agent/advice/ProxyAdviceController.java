package com.opencloud.agent.advice;

import com.opencloud.agent.Result;
import com.opencloud.agent.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;


@RestControllerAdvice(annotations = RestController.class)
public class ProxyAdviceController {

    private final static Logger logger = LoggerFactory.getLogger(ProxyAdviceController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result processException(NativeWebRequest request, Exception e) {
        if (e instanceof BusinessException) {
            logger.error(e.getMessage(), e);
            return Result.error(((BusinessException) e).getMsg());
        } else {
            logger.error(e.getMessage(), e);
            return Result.error();
        }

    }

}
