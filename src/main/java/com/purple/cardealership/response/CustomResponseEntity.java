package com.purple.cardealership.response;

import com.purple.cardealership.entity.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.List;

public class CustomResponseEntity extends ResponseEntity<LinkedHashMap<String, Object>> {

    public CustomResponseEntity(String message, HttpStatus httpStatus) {
        super(LinkedHashMapBuilder(message, httpStatus), httpStatus);
    }

    public CustomResponseEntity(String errorMessage, String internalErrorCode, HttpStatus httpStatus) {
        super(LinkedHashMapBuilder(errorMessage, internalErrorCode, httpStatus), httpStatus);
    }

    public CustomResponseEntity(String message, HttpStatus httpStatus, List<Car> resultList) {
        super(LinkedHashMapBuilder(message, httpStatus, resultList), httpStatus);
    }

    private static LinkedHashMap<String, Object> LinkedHashMapBuilder(String message, HttpStatus httpStatus) {
        LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        return responseMap;
    }

    private static LinkedHashMap<String, Object> LinkedHashMapBuilder(String errorMessage, String internalErrorCode,
                                                                      HttpStatus httpStatus) {
        LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("error", errorMessage);
        responseMap.put("internal-error-code", internalErrorCode);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        return responseMap;
    }

    private static LinkedHashMap<String, Object> LinkedHashMapBuilder(String message, HttpStatus httpStatus, List<Car> resultList) {
        LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        responseMap.put("resultList", resultList);
        return responseMap;
    }

}
