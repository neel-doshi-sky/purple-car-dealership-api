package com.purple.cardealership.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
public class CustomResponse {

    private String code;
    private HttpStatus httpStatus;
    private String message;

}
