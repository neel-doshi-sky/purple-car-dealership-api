package com.purple.cardealership.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import com.purple.cardealership.MissingArgsException;
import com.purple.cardealership.entity.Car;
import com.purple.cardealership.repository.CarRepository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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
    public Car createCar(@NonNull final Car car) throws IllegalArgumentException {
        log.info("saving car");
        return carRepository.save(car);
    }

    /**
     * Method to get a list of all cars in the database
     * 
     * @param sortBy
     *
     * @return list of cars
     */
    public List<Car> readCars(String brand, Integer ageStart, Integer ageEnd, Integer mileageStart,
            Integer mileageEnd, Double engineSizeStart, Double engineSizeStop, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        return carRepository.findCar(brand, ageStart, ageEnd, mileageStart, mileageEnd, engineSizeStart,
                engineSizeStop, sort);

    }

    /**
     * Method to update a car if provided an id else create a new car
     *
     * @param id         the id of the car to update
     * @param brand      the brand of the car
     * @param model      the model of the car
     * @param age        the age of the car
     * @param mileage    the mileage of the car
     * @param engineSize the engine size of the car
     * @return the newly updated/created car
     * @throws IllegalArgumentException the IllegalArgumentException if invalid
     *                                  arguments provided
     * @throws MissingArgsException     the MissingArgsException if arguments are
     *                                  missing
     * @throws EntityNotFoundException  the EntityNotFoundException if entity not
     *                                  found
     */
    public Car updateOrCreateCar(final Long id, final String brand, final String model, final Integer age,
            final Integer mileage,
            Double engineSize) throws IllegalArgumentException, MissingArgsException, EntityNotFoundException {
        if (id == null) {
            return createCarFromUpdate(brand, model, age, mileage, engineSize);
        } else {
            return updateCar(id, brand, model, age, mileage, engineSize);
        }
    }

    /**
     * Method to create a new car from the update endpoint if no id provided
     *
     * @param brand      the brand of the car
     * @param model      the model of the car
     * @param age        the age of the car
     * @param mileage    the mileage of the car
     * @param engineSize the engine size of the car
     * @return the newly updated/created car
     * @throws IllegalArgumentException the IllegalArgumentException if invalid
     *                                  arguments provided
     * @throws MissingArgsException     the MissingArgsException if arguments are
     *                                  missing
     */
    private Car createCarFromUpdate(final String brand, final String model, final Integer age, final Integer mileage,
            final Double engineSize)
            throws MissingArgsException, IllegalArgumentException {
        if (Stream.of(brand, model, age, mileage, engineSize).allMatch(Objects::nonNull)) {
            Car car = new Car(brand, model, age, mileage, engineSize);
            return createCar(car);
        } else {
            log.info("update car without id, and missing param");
            throw new MissingArgsException("Missing arg(s)");
        }
    }

    /**
     * Method to update an existing car in the database
     *
     * @param id         the id of the car to update
     * @param brand      the brand of the car
     * @param model      the model of the car
     * @param age        the age of the car
     * @param mileage    the mileage of the car
     * @param engineSize the engine size of the car
     * @return the newly updated/created car
     * @throws EntityNotFoundException the EntityNotFoundException if entity not
     *                                 found
     */
    private Car updateCar(@NonNull final Long id, final String brand, final String model, final Integer age,
            final Integer mileage, final Double engineSize) throws EntityNotFoundException {
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
        log.info("updating car with ID: " + car.getId());
        return carRepository.save(car);
    }

    /**
     * Method to delete a car in the database by id
     *
     * @param id the id of the car to delete
     * @throws EmptyResultDataAccessException the EmptyResultDataAccessException if
     *                                        unable to delete car
     */
    public void deleteCar(@NonNull final Long id) throws EmptyResultDataAccessException {
        carRepository.deleteById(id);
    }
}
