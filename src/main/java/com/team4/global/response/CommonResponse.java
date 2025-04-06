package com.team4.global.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommonResponse<T> (@Schema(example = "data status") String message, T data){

    public static <T> CommonResponse<T> ok() {
        return new CommonResponse<>("ok", null);
    }

    public static <T> CommonResponse<T> ok(T result) {
        return new CommonResponse<>("ok", result);
    }

    public static <T> CommonResponse<T> created(T result) {
        return new CommonResponse<>("created", result);
    }

    public static <T> CommonResponse<T> updated(T result) {
        return new CommonResponse<>("update", result);
    }

    public static <T> CommonResponse<T> delete(T result) {
        return new CommonResponse<>("delete", result);
    }
}
