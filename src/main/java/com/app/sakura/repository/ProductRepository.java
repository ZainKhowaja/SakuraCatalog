package com.app.sakura.repository;

import com.app.sakura.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    List<Product> findBySakuraNoLike(String sakuraNo);
    List<Product> findByRefrenceNoLike(String refrenceNo);
}
