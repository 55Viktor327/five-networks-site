package com.fivenetworks.site.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid current password");
    }
}
