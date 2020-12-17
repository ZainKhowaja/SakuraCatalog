package com.app.sakura.service.impl;

import com.app.sakura.entity.Product;
import com.app.sakura.repository.ProductRepository;
import com.app.sakura.service.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataValidatorImpl implements DataValidator {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean validateAddProduct(Product product) {
        boolean isValidated = true;
        if (product.getSakuraNo() == null || product.getSakuraNo().isEmpty()) {
            isValidated = false;
        } else if (productRepository.findById(product.getSakuraNo()).isPresent()) {
            isValidated = false;
        } else if (product.getRefrenceNo() == null || product.getRefrenceNo().isEmpty()) {
            isValidated = true; // make it false if want to enable reference check enbale while adding new product
        } else if (product.getProductDetail() == null) {
            isValidated = false;
        } else if (product.getFilter() == null || product.getManufacturer() == null || product.getTypeDetail() == null){
            isValidated = false;
        }
        return isValidated;
    }
}
