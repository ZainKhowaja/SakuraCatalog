package com.app.sakura.repository;

import com.app.sakura.entity.ProductReference;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReferenceRepository extends JpaRepository<ProductReference, Integer> {
	
	List<ProductReference> findByProduct_sakuraNoAndActiveTrue(String sakuraNo);
	List<ProductReference> findByProductActiveAndActiveTrue(int active);
	
	ProductReference findFirstByReferenceAndManufacturer_name(String referenceNo, String name);
}
