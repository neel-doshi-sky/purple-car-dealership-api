package com.purple.cardealership.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;

public class CustomResponseEntity extends ResponseEntity<LinkedHashMap<String, String>> {

    public CustomResponseEntity(String message, HttpStatus httpStatus) {
        super(LinkedHashMapBuilder(message, httpStatus), httpStatus);
    }

    public CustomResponseEntity(String errorMessage, String internalErrorCode, HttpStatus httpStatus) {
        super(LinkedHashMapBuilder(errorMessage, internalErrorCode, httpStatus), httpStatus);
    }

    private static LinkedHashMap<String, String> LinkedHashMapBuilder(String message, HttpStatus httpStatus) {
        LinkedHashMap<String, String> responseMap = new LinkedHashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        return responseMap;
    }

    private static LinkedHashMap<String, String> LinkedHashMapBuilder(String errorMessage, String internalErrorCode,
                                                                      HttpStatus httpStatus) {
        LinkedHashMap<String, String> responseMap = new LinkedHashMap<>();
        responseMap.put("error", errorMessage);
        responseMap.put("internal-error-code", internalErrorCode);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        return responseMap;
    }
}
