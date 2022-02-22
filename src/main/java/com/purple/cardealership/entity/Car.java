package com.purple.cardealership.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private Integer age;
    private Integer mileage;
    private Double engineSize;

    public Car(String brand, String model, Integer age, Integer mileage, Double engineSize) {
        this.brand = brand;
        this.model = model;
        this.age = age;
        this.mileage = mileage;
        this.engineSize = engineSize;
    }
}
