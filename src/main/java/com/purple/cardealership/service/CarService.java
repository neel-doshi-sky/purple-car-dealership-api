package com.purple.cardealership.service;

import com.purple.cardealership.Constants;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Method to save a car entity to the database
     *
     * @param car the car entity
     * @return the id of the newly created car
     * @throws IllegalArgumentException the IllegalArgumentException if the entity is null
     */
    public Long createCar(Car car) throws IllegalArgumentException {
        log.info("saving car " + car.toString());
        return carRepository.save(car).getId();
    }

    /**
     * Method to get a list of all cars in the database
     *
     * @return list of cars
     */
    public List<Car> readCars() {
        return carRepository.findAll();
    }
}
