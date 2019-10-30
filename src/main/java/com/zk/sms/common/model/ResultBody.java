package com.zk.sms.common.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The type Result body.
 *
 * @author guoying
 * @since 2019 -10-27 22:01:13
 */
@Getter
@Setter
@ApiModel(description = "返回结果")
public final class ResultBody<T> implements Serializable {

    /**
     * The result.
     */
    @ApiModelProperty("是否成功: true or false")
    private boolean result = true;

    /**
     * The Code.
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * The Message.
     */
    @ApiModelProperty("描述性原因")
    private String message;

    /**
     * The Data.
     */
    @ApiModelProperty("业务数据")
    private T data;

    /**
     * Success result body.
     *
     * @param message the message
     * @param data    the data
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody<T> success(String message, T data) {
        ResultBody<T> resultBody = new ResultBody<>();
        resultBody.setResult(true);
        resultBody.setCode(200);
        resultBody.setMessage(message);
        resultBody.setData(data);
        return resultBody;
    }

    /**
     * Success result body.
     *
     * @param data the data
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody<T> success(T data) {
        return success("OK", data);
    }

    /**
     * Success result body.
     *
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody<T> success() {
        return success(null);
    }

    /**
     * Failure result body.
     *
     * @param code    the code
     * @param message the message
     * @param error   the error
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody<T> failure(Integer code, String message, T error) {
        ResultBody<T> resultBody = new ResultBody<>();
        resultBody.setResult(false);
        resultBody.setCode(code);
        resultBody.setMessage(message);
        resultBody.setData(error);
        return resultBody;
    }

    /**
     * Failure result body.
     *
     * @param code    the code
     * @param message the message
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody failure(Integer code, String message) {
        return failure(code, message, null);
    }

    /**
     * Failure result body.
     *
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody failure() {
        return failure(-1, null, null);
    }

    /**
     * Failure result body.
     *
     * @param message the message
     * @param data    the data
     * @return the result body
     * @author guoying
     * @since 2019 /10/27
     */
    public static<T> ResultBody failure(String message,Object data) {
        return failure(-1, message, data);
    }
}
