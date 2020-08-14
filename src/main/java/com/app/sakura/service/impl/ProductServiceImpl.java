package com.app.sakura.service.impl;

import com.app.sakura.entity.Product;
import com.app.sakura.repository.ProductRepository;
import com.app.sakura.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProductBySakuraNo(String sakuraNo) {
        return productRepository.findById(sakuraNo);
    }

    @Override
    public List<Product> getProductBySakuraNoLike(String sakuraNo) {
        return productRepository.findBySakuraNoLike(sakuraNo);
    }

    @Override
    public List<Product> getProductByRefrenceNoLike(String refrenceNo) {
        return productRepository.findByRefrenceNoLike(refrenceNo);
    }
}
