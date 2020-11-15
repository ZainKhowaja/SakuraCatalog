package com.app.sakura.repository;

import com.app.sakura.entity.ProductReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReferenceRepository extends JpaRepository<ProductReference, Integer> {
}
