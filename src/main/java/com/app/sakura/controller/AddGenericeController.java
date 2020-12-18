package com.app.sakura.controller;

import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.TypeDetail;
import com.app.sakura.repository.FilterRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.TypeDetailRepository;
import com.app.sakura.service.DataValidator;
import com.app.sakura.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddGenericeController {

    public static AddWindowType windowType;

    @FXML
    private Label labelValue;
    @FXML
    private TextField textValue;
    @FXML
    private Label windowText;
    @FXML
    private Button btnValue;

    @Autowired
    private FilterRepository filterRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private TypeDetailRepository typeDetailRepository;

    @Autowired
    private DataValidator dataValidator;

    @FXML
    public void exit() {
        ((Stage) ((Node) windowText).getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        loadWindow(windowType);
    }

    private void loadWindow(AddWindowType windowType) {
        windowText.setText(windowType.getWindowText());
        labelValue.setText(windowType.getLabelText());
        btnValue.setText(windowType.getWindowText());

    }

    @FXML
    public void addData() {
        String filedData = textValue.getText().trim();
        if(!dataValidator.validateGenericAdd(filedData,windowType)){
            return;
        }
        if (windowType == AddWindowType.FILTER) {
            Filter filter = new Filter();
            filter.setName(filedData);
            filterRepository.save(filter);
        } else if (windowType == AddWindowType.MANUFACTURER) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(filedData);
            manufacturerRepository.save(manufacturer);
        } else if (windowType == AddWindowType.TYPE_DETAIL) {
            TypeDetail typeDetail = new TypeDetail();
            typeDetail.setName(filedData);
            typeDetailRepository.save(typeDetail);
        }
        AlertUtil.showInfo("Record added");
        exit();
    }

    public enum AddWindowType {
        FILTER("Add Filter", "Filter Name:"),
        MANUFACTURER("Add Manufacturer", "Manufacturer Name"),
        TYPE_DETAIL("Add Type", "Type Name:");

        private String windowText;
        private String labelText;

        AddWindowType(String windowText, String labelText) {
            this.windowText = windowText;
            this.labelText = labelText;
        }

        public String getWindowText() {
            return windowText;
        }

        public String getLabelText() {
            return labelText;
        }
    }


}
