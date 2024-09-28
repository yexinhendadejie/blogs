package com.blogs.utils.heartbeat;

public class HeartBeatException extends RuntimeException {

    public HeartBeatException() {
    }

    public HeartBeatException(String message) {
        super(message);
    }

    public HeartBeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeartBeatException(Throwable cause) {
        super(cause);
    }

    public HeartBeatException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
