package com.purple.cardealership.service;

import java.sql.SQLException;

import com.purple.cardealership.entity.Car;
import com.purple.cardealership.repository.CarRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
// @Slf4j
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Long createCar(Car car) throws IllegalArgumentException {
        return carRepository.save(car).getId();
    }
}
