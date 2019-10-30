package com.zk.sms.common.controller;

import com.zk.sms.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理.
 *
 * @author guoying
 * @since 2019 -10-27 21:08:32
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    /**
     * Handle http message not readable exception result body.
     *
     * @param e the e
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultBody handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e.getMessage());
        return ResultBody.failure(400, "请求参数不正确", e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResultBody handleValidationException(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return ResultBody.failure(400, "请求类型不支持！", e.getMessage());
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBody handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return ResultBody.failure(405, "不被支持的访问方法！", e.getMessage());
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResultBody handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return ResultBody.failure(415, "不支持的媒体类型！", e.getMessage());
    }

    /**
     * 捕捉其他所有异常.
     *
     * @param request the request
     * @param e       the e
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBody globalException(HttpServletRequest request, Exception e) {
        return ResultBody.failure(500, e.getMessage(), e);
    }
}
