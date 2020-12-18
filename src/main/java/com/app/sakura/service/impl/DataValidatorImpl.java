package com.app.sakura.service.impl;

import com.app.sakura.controller.AddGenericeController;
import com.app.sakura.entity.Product;
import com.app.sakura.repository.FilterRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.ProductRepository;
import com.app.sakura.repository.TypeDetailRepository;
import com.app.sakura.service.DataValidator;
import com.app.sakura.util.AlertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DataValidatorImpl implements DataValidator {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FilterRepository filterRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private TypeDetailRepository typeDetailRepository;


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

    @Override
    public boolean validateGenericAdd(String fieldData , AddGenericeController.AddWindowType windowType) {
        boolean isValidated = true;
        String msg = "";
        if(fieldData.isEmpty() || fieldData.equalsIgnoreCase("")){
            msg = "Empty Field";
            isValidated = false;
        }else {
            isValidated = checkDuplicateEntry(fieldData, windowType);
            msg = isValidated ? "": "Already Exists";
        }
        if(!isValidated){
            AlertUtil.showError(msg);
        }

        return isValidated;

    }

    private boolean checkDuplicateEntry(String fieldData ,AddGenericeController.AddWindowType windowType) {
        switch (windowType){
            case FILTER: return filterRepository.findAll().stream().filter(x -> x.getName().equalsIgnoreCase(fieldData)).count() == 0;
            case MANUFACTURER: return manufacturerRepository.findAll().stream().filter(x -> x.getName().equalsIgnoreCase(fieldData)).count() == 0;
            case TYPE_DETAIL: return typeDetailRepository.findAll().stream().filter(x -> x.getName().equalsIgnoreCase(fieldData)).count() == 0;
        }
        return false;
    }
}
