package com.purple.cardealership.controller;

import java.util.HashMap;

import com.purple.cardealership.entity.Car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

    /**
     * @param body {"brand":"asd","model":"asd2","age":"123","mileage":"123456","engineSize":"1.4"}
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<String> createCar(@RequestBody HashMap<String, String> body) {
        try {
            // Long id = Long.parseLong(body.get("id"));
            String brand = body.get("brand");
            String model = body.get("model");
            Integer age = Integer.parseInt(body.get("age"));
            Integer mileage = Integer.parseInt(body.get("mileage"));
            Double engineSize = Double.parseDouble(body.get("engineSize"));
            System.out.println(engineSize);
            Car car = new Car(brand, model, age, mileage, engineSize);
            // return new ResponseEntity<>("Success", HttpStatus.OK);
            return null;
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw e;
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw e;
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
