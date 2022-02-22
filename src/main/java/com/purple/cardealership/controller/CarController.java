package com.purple.cardealership.controller;

import java.util.HashMap;
import java.util.List;

import com.purple.cardealership.Constants;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.response.CustomResponseEntity;
import com.purple.cardealership.service.CarService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Endpoint to add a new car to the database
     *
     * @param body containing details of the car e.g {"brand":"asd","model":"asd2","age":"123","mileage":"123456","engineSize":"1.4"}
     * @return CustomResponseEntity which will contain the response of the request
     */
    @PostMapping("/create")
    public CustomResponseEntity create(@RequestBody HashMap<String, String> body) {

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
                return new CustomResponseEntity("CUSTOM_ERROR_SERVER_ERROR", Constants.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new CustomResponseEntity("The car was created", HttpStatus.CREATED);

        } catch (NullPointerException | NumberFormatException | ClassCastException e) {
            log.error(e.getMessage());
            return new CustomResponseEntity("CUSTOM_ERROR_BAD_REQUEST", Constants.INVALID_BODY, HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new CustomResponseEntity("CUSTOM_ERROR_SERVER_ERROR", Constants.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to return all available cars
     *
     * @return CustomResponseEntity with result list
     */
    @GetMapping("/read")
    public CustomResponseEntity read() {
        List<Car> resultsList = carService.readCars();
        return new CustomResponseEntity("Successfully fetched cars", HttpStatus.OK, resultsList);
    }
}
