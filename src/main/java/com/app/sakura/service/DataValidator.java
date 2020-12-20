package com.app.sakura.service;

import com.app.sakura.controller.AddGenericeController;
import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Product;
public interface DataValidator{

    boolean validateAddProduct(Product product);
    boolean validateGenericAdd(String fieldData, AddGenericeController.AddWindowType windowType, Filter selectedItem);
}
