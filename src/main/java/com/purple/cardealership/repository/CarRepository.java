package com.purple.cardealership.repository;

import java.util.List;

import com.purple.cardealership.entity.Car;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	@Query(value = "SELECT * FROM Car c WHERE " +
			"(:brand is null or c.brand = :brand) and " +
			"(:ageStart is null or c.age >= :ageStart) and " +
			"(:ageEnd is null or c.age <= :ageEnd) and  " +
			"(:mileageStart is null or c.mileage >= :mileageStart) and " +
			"(:mileageEnd is null or c.mileage <= :mileageEnd) and  " +
			"(:engineSizeStart is null or c.engineSize >= :engineSizeStart) and " +
			"(:engineSizeEnd is null or c.engineSize <= :engineSizeEnd)")
	List<Car> findCar(@Param("brand") String brand, @Param("ageStart") Integer ageStart,
			@Param("ageEnd") Integer ageEnd, @Param("mileageStart") Integer mileageStart,
			@Param("mileageEnd") Integer mileageEnd, @Param("engineSizeStart") Double engineSizeStart,
			@Param("engineSizeEnd") Double engineSizeEnd, Sort Sort);
}
