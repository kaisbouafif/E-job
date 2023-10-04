package com.example.ejob.application.interceptors;


import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final  ServicesOutboundExceptionHandler servicesOutboundExceptionHandler;

    @Autowired
    ControllerExceptionHandler(
            ServicesOutboundExceptionHandler servicesOutboundExceptionHandler
    ){
        this.servicesOutboundExceptionHandler = servicesOutboundExceptionHandler;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            RuntimeException.class, ServletException.class
    })
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        return servicesOutboundExceptionHandler.handle(ex);
    }
}



