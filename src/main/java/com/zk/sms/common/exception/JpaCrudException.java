package com.zk.sms.common.exception;

/**
 * The type Jpa crud exception.
 *
 * @author guoying
 * @since 2019 -10-27 20:39:07
 */
public class JpaCrudException extends RuntimeException {
    /**
     * Instantiates a new Jpa crud exception.
     *
     * @param message the message
     */
    public JpaCrudException(String message) {
        super(message);
    }
}
