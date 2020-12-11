package com.app.sakura.controller;

import com.app.sakura.entity.Brand;
import com.app.sakura.repository.BrandRepository;
import com.app.sakura.util.AlertUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddBrandController {


    @Autowired
    private BrandRepository brandRepository;

    @FXML
    private TextField brandName;

    @FXML
    public void exit(Event event){
        ((Stage) ((Node) brandName).getScene().getWindow()).close();
    }

    @FXML
    public void addBrand(){
        if(brandName.getText().equals("") || brandName.getText().isEmpty() || brandName.getText().trim().equals("")){
            AlertUtil.showError("Enter correct brand name");
            return;
        }
        if(doesBrandExist()){
            AlertUtil.showError("Brand with the same name already exists");
            return;
        }
        Brand brand = new Brand();
        brand.setName(brandName.getText());
        if(brandRepository.save(brand).getId() != null){
            AlertUtil.showInfo("Brand added");
        }
    }

    private boolean doesBrandExist() {
        return brandRepository.findAll().stream()
                .filter(brand -> brand.getName().equalsIgnoreCase(brandName.getText()))
                .count() > 0;
    }
}
