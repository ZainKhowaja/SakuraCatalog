package com.app.sakura.repository;

import com.app.sakura.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    int active = 0;
//    List<Product> findBySakuraNoLike(String sakuraNo);
//    List<Product> findByRefrenceNoLike(String refrenceNo);
//Product findBySakuraNo(String sakuraNo);
    List<Product> findBySakuraNoLikeAndActive(String sakuraNo, int active);
    List<Product> findByRefrenceNoLikeAndActive(String refrenceNo, int active);
    Product findBySakuraNoAndActive(String sakuraNo,int active);
    List<Product> findByActive(int active);

    @Transactional
    @Modifying
    @Query(value = "update product set sakura_no = ?1 || '-DEL' , active = 1 where sakura_no = ?1" ,nativeQuery = true)
    void deleteProduct(String sakuraNo);
    Product findByRefrenceNo(String refrenceNo);
    
}
