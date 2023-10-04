package com.example.ejob.application.interceptors;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ServicesOutboundExceptionHandler {

    ResponseEntity<Map<String,Object>> handle(Exception exception);
}
