package com.purple.cardealership.service;

import com.purple.cardealership.Constants;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
// @Slf4j
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Long createCar(Car car) throws IllegalArgumentException {
        //return carRepository.save(car).getId();
        return null;
    }
}
