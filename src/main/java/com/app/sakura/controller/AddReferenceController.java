package com.app.sakura.controller;

import com.app.sakura.entity.Brand;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.ProductReference;
import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.repository.BrandRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.ProductReferenceRepository;
import com.app.sakura.repository.ProductRepository;
import com.app.sakura.service.DataValidator;
import com.app.sakura.util.AlertUtil;
import com.app.sakura.util.ScreenUtils;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddReferenceController {

    @Autowired
    private ScreenUtils screen;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ProductReferenceRepository productReferenceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataValidator dataValidator;

    @FXML
    private ComboBox<Manufacturer> brandType;

    @FXML
    private TextField fujiNo;

    @FXML
    private TextField refNo;

    @FXML
    public void initialize(){
        fujiNo.setText(ProductSearchController.sakuraId);
        loadBrand();
    }

    @FXML
    public void exit(Event event){
    	((Stage) ((Node) fujiNo).getScene().getWindow()).close();
    }

    @FXML
    public void addProductReference(){
        if(validateProductReference()){
            ProductReference productReference = new ProductReference();
            productReference.setManufacturer(brandType.getSelectionModel().getSelectedItem());
            productReference.setReference(refNo.getText());
            productReference.setProduct(productRepository.findBySakuraNo(fujiNo.getText()));
            if(productReferenceRepository.save(productReference).getId() != null){
                AlertUtil.showInfo(String.format("Reference Added for fuji no : %s",fujiNo.getText()));
                exit(null);
            }
        }
    }

    private boolean validateProductReference(){
        boolean validate = true;
        if(brandType.getSelectionModel().getSelectedItem() == null){
            AlertUtil.showError("Select brand type");
            validate = false;
        }else if(refNo.getText().isEmpty() || refNo.getText() == null){
            AlertUtil.showError("Enter reference no");
            validate = false;
        }
        return validate;
    }

    public void loadBrand(){
        brandType.setItems(FXCollections.observableList(manufacturerRepository.findAll()));
    }
}
