package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private String url;
    private final ErrorType type;
    private String[] detail;

    public ErrorInfo(CharSequence url, ErrorType type, String... detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public ErrorInfo(ErrorType type, String... detail) {
        this.type = type;
        this.detail = detail;
    }
}