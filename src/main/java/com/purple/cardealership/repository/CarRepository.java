package com.purple.cardealership.repository;

import javax.transaction.Transactional;

import com.purple.cardealership.entity.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
