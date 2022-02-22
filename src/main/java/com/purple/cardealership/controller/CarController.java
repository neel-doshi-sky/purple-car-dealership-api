package com.purple.cardealership.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import com.purple.cardealership.Constants;
import com.purple.cardealership.MissingArgsException;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.response.CustomResponseEntity;
import com.purple.cardealership.service.CarService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

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
     * @param body containing details of the car e.g
     *             {"brand":"asd","model":"asd2","age":"123","mileage":"123456","engineSize":"1.4"}
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
            if (Stream.of(brand, model, age, mileage, engineSize).anyMatch(Objects::isNull)) {
                throw new MissingArgsException("Missing arg(s)");
            }
            Car car = new Car(brand, model, age, mileage, engineSize);
            Car newCar = carService.createCar(car);

            if (newCar == null) {
                log.error(Constants.SERVER_ERROR);
                return new CustomResponseEntity(Constants.SERVER_ERROR, "CUSTOM_ERROR_SERVER_ERROR",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new CustomResponseEntity("The car was created", HttpStatus.CREATED);

        } catch (NullPointerException | NumberFormatException | ClassCastException | MissingArgsException
                | EntityNotFoundException e) {
            log.error(e.getMessage());
            return new CustomResponseEntity(Constants.INVALID_BODY, "CUSTOM_ERROR_BAD_REQUEST", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new CustomResponseEntity(Constants.SERVER_ERROR, "CUSTOM_ERROR_SERVER_ERROR",
                    HttpStatus.INTERNAL_SERVER_ERROR);
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

    /**
     * Endpoint to update a car or create a new car if no id is provided in the body
     *
     * @param body the body containing details of the car e.g
     *             * {"id": "1",
     *             "brand":"asd","model":"asd2","age":"123","mileage":"123456","engineSize":"1.4"}
     * @return CustomResponseEntity with updated/created car returned
     */
    @PutMapping("/update")
    public CustomResponseEntity update(@RequestBody HashMap<String, String> body) {
        try {
            Long id = body.containsKey("id") ? Long.parseLong(body.get("id")) : null;
            String brand = body.getOrDefault("brand", null);
            String model = body.getOrDefault("model", null);
            Integer age = body.containsKey("age") ? Integer.parseInt(body.get("age")) : null;
            Integer mileage = body.containsKey("mileage") ? Integer.parseInt(body.get("mileage")) : null;
            Double engineSize = body.containsKey("engineSize") ? Double.parseDouble(body.get("engineSize")) : null;

            Car updatedCar = carService.updateOrCreateCar(id, brand, model, age, mileage, engineSize);

            if (updatedCar == null) {
                log.error(Constants.SERVER_ERROR);
                return new CustomResponseEntity(Constants.SERVER_ERROR, "CUSTOM_ERROR_SERVER_ERROR",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            boolean isNewCar = id == null;
            return new CustomResponseEntity(
                    (isNewCar ? "Created new car with ID: " : "Updated car with ID: ") + updatedCar.getId(),
                    isNewCar ? HttpStatus.CREATED : HttpStatus.OK, updatedCar);
        } catch (NumberFormatException | ClassCastException | MissingArgsException | EntityNotFoundException e) {
            log.error(e.getMessage());
            return new CustomResponseEntity(Constants.INVALID_BODY, "CUSTOM_ERROR_BAD_REQUEST", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new CustomResponseEntity(Constants.SERVER_ERROR, "CUSTOM_ERROR_SERVER_ERROR",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete a car by id
     *
     * @param id the id of the car
     * @return CustomResponseEntity with the result of the operation
     */
    @DeleteMapping("/delete/{id}")
    public CustomResponseEntity delete(@PathVariable(value = "id") Long id) {
        try {
            carService.deleteCar(id);
            return new CustomResponseEntity("Successfully deleted car with ID: " + id, HttpStatus.OK);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return new CustomResponseEntity(Constants.INVALID_PATH_VARIABLE, "CUSTOM_ERROR_BAD_REQUEST",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
