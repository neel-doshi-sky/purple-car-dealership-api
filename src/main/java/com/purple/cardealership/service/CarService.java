package com.purple.cardealership.service;

import com.purple.cardealership.Constants;
import com.purple.cardealership.MissingArgsException;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

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
     * @throws IllegalArgumentException the IllegalArgumentException if the entity
     *                                  is null
     */
    public Car createCar(Car car) throws IllegalArgumentException {
        log.info("saving car " + car.toString());
        return carRepository.save(car);
    }

    /**
     * Method to get a list of all cars in the database
     *
     * @return list of cars
     */
    public List<Car> readCars() {
        return carRepository.findAll();
    }

    public Car updateOrCreateCar(Long id, String brand, String model, Integer age, Integer mileage,
            Double engineSize) throws IllegalArgumentException, MissingArgsException, EntityNotFoundException {
        if (id == null) {
            return createCarFromUpdate(brand, model, age, mileage, engineSize);
        } else {
            return updateCar(id, brand, model, age, mileage, engineSize);
        }
    }

    private Car createCarFromUpdate(String brand, String model, Integer age, Integer mileage, Double engineSize)
            throws MissingArgsException, IllegalArgumentException {
        if (Stream.of(brand, model, age, mileage, engineSize).allMatch(value -> value != null)) {
            Car car = new Car(brand, model, age, mileage, engineSize);
            return createCar(car);
        } else {
            log.info("update car without id, and missing param");
            throw new MissingArgsException("Missing arg(s)");
        }
    }

    private Car updateCar(Long id, String brand, String model, Integer age, Integer mileage, Double engineSize)
            throws EntityNotFoundException {
        Car car = carRepository.getById(id);
        if (brand != null) {
            car.setBrand(brand);
        }
        if (model != null) {
            car.setModel(model);
        }
        if (age != null) {
            car.setAge(age);
        }
        if (mileage != null) {
            car.setMileage(mileage);
        }
        if (engineSize != null) {
            car.setEngineSize(engineSize);
        }
        log.info("updating car " + car.toString());
        return carRepository.save(car);
    }
}
