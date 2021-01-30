package com.app.sakura.repository;

import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer> {
	
	List<Manufacturer> findByActiveTrue();
}
