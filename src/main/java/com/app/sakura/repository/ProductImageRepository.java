package com.app.sakura.repository;

import com.app.sakura.entity.ProductImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	
	List<ProductImage> findByProductSakuraNo(String sakuraNum);
	
	
}
