package com.app.sakura.repository;

import com.app.sakura.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,String> {
}
