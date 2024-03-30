//package com.example.webcinema.Payloads.Responses;
//
//import lombok.Data;
//
//@Data
//public class ResponseObject<T> {
//    private int status;
//    private String message;
//    private T data;
//
//    public ResponseObject(int status, String message, T data) {
//        this.status = status;
//        this.message = message;
//        this.data = data;
//    }
//
//    public ResponseObject<T> responseSuccess(String message, T data) {
//        return new ResponseObject<>(200, message, data);
//    }
//
//    public ResponseObject<T> responseError(int status, String message, T data) {
//        return new ResponseObject<>(status, message, data);
//    }
//}
//
