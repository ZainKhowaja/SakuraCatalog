package com.app.sakura.repository;

import com.app.sakura.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer> {
}
