package com.purple.cardealership.response;

import com.purple.cardealership.entity.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.List;

public class CustomResponseEntity extends ResponseEntity<LinkedHashMap<String, Object>> {

    /**
     * Used for confirming success to the user with no additional data
     * 
     * @param message    Message to return to the user
     * @param httpStatus The http status code to be returned
     */
    public CustomResponseEntity(String message, HttpStatus httpStatus) {
        super(LinkedHashMapBuilderSuccess(message, httpStatus), httpStatus);
    }

    /**
     * Used for returning error messages to the user
     * 
     * @param errorMessage      A message describing what went wrong
     * @param internalErrorCode An internal error code to better describe the error
     * @param httpStatus        The http status code to be returned
     */
    public CustomResponseEntity(String errorMessage, String internalErrorCode, HttpStatus httpStatus) {
        super(LinkedHashMapBuilderError(errorMessage, internalErrorCode, httpStatus), httpStatus);
    }

    /**
     * Used for returning Car data to the user
     * 
     * @param message    Message to return to the user
     * @param httpStatus The http status code to be returned
     * @param results    One or more objects that are returned as data to the user
     */
    public CustomResponseEntity(String message, HttpStatus httpStatus, Object results) {
        super(LinkedHashMapBuilderResults(message, httpStatus, results), httpStatus);
    }

    private static LinkedHashMap<String, Object> LinkedHashMapBuilderSuccess(String message, HttpStatus httpStatus) {
        LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        return responseMap;
    }

    private static LinkedHashMap<String, Object> LinkedHashMapBuilderError(String errorMessage,
            String internalErrorCode,
            HttpStatus httpStatus) {
        LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("error", errorMessage);
        responseMap.put("internal-error-code", internalErrorCode);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        return responseMap;
    }

    private static LinkedHashMap<String, Object> LinkedHashMapBuilderResults(String message, HttpStatus httpStatus,
            Object result) {
        LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", String.valueOf(httpStatus.value()));
        responseMap.put("results", result);
        return responseMap;
    }

}
