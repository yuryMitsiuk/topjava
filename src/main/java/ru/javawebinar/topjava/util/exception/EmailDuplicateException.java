package ru.javawebinar.topjava.util.exception;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String s) {
        super(s);
    }
}
