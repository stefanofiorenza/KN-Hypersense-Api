package com.knits.coreplatform.message;

/**
 * Response entity for Excel upload/download operations.
 *
 * @author Vassili Moskaljov
 * @version 1.0
 * */
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
