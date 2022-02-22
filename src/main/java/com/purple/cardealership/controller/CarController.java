package com.purple.cardealership.controller;

import java.util.HashMap;

import com.purple.cardealership.Constants;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.response.CustomResponseEntity;
import com.purple.cardealership.service.CarService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * @param body {"brand":"asd","model":"asd2","age":"123","mileage":"123456","engineSize":"1.4"}
     * @return
     */
    @PostMapping("/create")
    public CustomResponseEntity createCar(@RequestBody HashMap<String, String> body) {

        try {
            String brand = body.get("brand");
            String model = body.get("model");
            Integer age = Integer.parseInt(body.get("age"));
            Integer mileage = Integer.parseInt(body.get("mileage"));
            Double engineSize = Double.parseDouble(body.get("engineSize"));
            Car car = new Car(brand, model, age, mileage, engineSize);
            Long idOfNewCar = carService.createCar(car);
            if (idOfNewCar == null) {
                log.error(Constants.SERVER_ERROR);
                return new CustomResponseEntity("CUSTOM_ERROR_SERVER_ERROR", Constants.SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // return new ResponseEntity<>("The car was created", HttpStatus.CREATED);
            return null;
        } catch (NullPointerException | NumberFormatException | ClassCastException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_BODY, e);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.SERVER_ERROR, e);
        }
    }
}
