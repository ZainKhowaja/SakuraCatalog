package com.app.sakura.service;

import com.app.sakura.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductBySakuraNo(String sakuraNo);
    List<Product> getProductBySakuraNoLike(String sakuraNo);
    List<Product> getProductByRefrenceNoLike(String refrenceNo);


}
