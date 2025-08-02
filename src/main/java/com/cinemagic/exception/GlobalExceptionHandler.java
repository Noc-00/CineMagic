package com.cinemagic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//Manejo de excepciones para toda la app
//Captura todas las RuntimeExceptions y regresa respuesta HTTP y 400 Bad Request
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> errorBody = new HashMap<>();

        // Información detallada sobre el error y que enviar respuesta
        errorBody.put("timestamp", LocalDateTime.now()); // Momento en que ocurrio el error
        errorBody.put("status", HttpStatus.BAD_REQUEST.value()); // Codigo HTTP 400
        errorBody.put("error", "Bad Request"); //Texto explicativo
        errorBody.put("message", ex.getMessage()); //Especificacion de la excepcion
        errorBody.put("path", request.getDescription(false).replace("uri=", "")); //Url q causa el error

        //Devuelve la respuesta
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}