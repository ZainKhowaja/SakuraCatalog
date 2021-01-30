package com.app.sakura.controller;

import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.TypeDetail;
import com.app.sakura.repository.FilterRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.TypeDetailRepository;
import com.app.sakura.service.DataValidator;
import com.app.sakura.util.AlertUtil;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddGenericeController {

    public static AddWindowType windowType;
    public static Boolean deleteData = false;
    
    @FXML
    private Label labelValue;
    @FXML
    private TextField textValue;
    @FXML
    private Label windowText;
    @FXML
    private Button btnValue;
    @FXML
    private Label filterLabel;
    @FXML
    private ComboBox<Object> filterCombo;
    @FXML
    private GridPane grid;

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
    	if(deleteData) {
    		loadForDeleteWindow();
    	}else {
    		loadWindow(windowType);
    	}
    }

    private void loadForDeleteWindow() {
    	grid.getChildren().removeIf(node ->  GridPane.getRowIndex(node) == 0);
    	
    	ObservableList<Object> data = FXCollections.observableArrayList();
    	
    	windowText.setText(windowType.getWindowText());
    	btnValue.setText(windowType.getWindowText());
    	filterLabel.setText(windowType.getWindowText());
//    	filterType.setItems(FXCollections.observableList(filterRepository.findAll()));
    	if(windowType == AddWindowType.DEL_TYPE_DETAIL){	
        	data.addAll(typeDetailRepository.findByActiveTrue());
    		filterCombo.setItems(data);
    	}else if(windowType == AddWindowType.DEL_FILTER){	
        	data.addAll(filterRepository.findByActiveTrue());
    		filterCombo.setItems(data);
    	}else if(windowType == AddWindowType.DEL_MANUFACTURER){	
        	data.addAll(manufacturerRepository.findByActiveTrue());
    		filterCombo.setItems(data);
    	}
    	
	}

	private void loadWindow(AddWindowType windowType) {
        windowText.setText(windowType.getWindowText());
        labelValue.setText(windowType.getLabelText());
        btnValue.setText(windowType.getWindowText());

        //remove combobox
        if(windowType != AddWindowType.TYPE_DETAIL){
            grid.getChildren().removeIf(node ->  GridPane.getRowIndex(node) == 1);
        }else{
        	ObservableList<Object> data = FXCollections.observableArrayList();
        	data.addAll(filterRepository.findByActiveTrue());
            filterCombo.setItems(data);
        }

    }

    @FXML
    public void addData() {
        String filedData = textValue.getText().trim();
        Integer filterId = null;
        
        Filter selectedFilter = null;
        if(filterCombo.getSelectionModel().getSelectedItem() instanceof Filter) {
        	selectedFilter = (Filter) filterCombo.getSelectionModel().getSelectedItem();
        }
      
       if(deleteData) {
    	   if(windowType == AddWindowType.DEL_TYPE_DETAIL){	
	           	TypeDetail detail = (TypeDetail) filterCombo.getSelectionModel().getSelectedItem();
	           	detail.setActive(false);
	           	typeDetailRepository.save(detail);
	       	} else if(windowType == AddWindowType.DEL_FILTER){	
	       		Filter filter = (Filter) filterCombo.getSelectionModel().getSelectedItem();
	       		filter.setActive(false);
	       		filterRepository.save(filter);
	       	} else if(windowType == AddWindowType.DEL_MANUFACTURER){	
	           	Manufacturer manf = (Manufacturer) filterCombo.getSelectionModel().getSelectedItem();
	           	manf.setActive(false);
	       		manufacturerRepository.save(manf);
	       	}
       }else {
	        if(windowType == AddWindowType.TYPE_DETAIL){
	            filterId = selectedFilter.getId();
	        }
	        if(!dataValidator.validateGenericAdd(filedData,windowType,selectedFilter,filterId)){
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
	        }
	        if (windowType == AddWindowType.TYPE_DETAIL) {
	            TypeDetail typeDetail = new TypeDetail();
	            typeDetail.setName(filedData);
	            typeDetail.setFilter((Filter) filterCombo.getSelectionModel().getSelectedItem());
	            typeDetailRepository.save(typeDetail);
	        } 
       }
        AlertUtil.showInfo(deleteData ? "Record Deleted" : "Record added");
        exit();
    }

    public enum AddWindowType {
        FILTER("Add Filter", "Filter Name:"),
        MANUFACTURER("Add Brand", "Brand Name"),
        TYPE_DETAIL("Add Type", "Type Name:"),
        DEL_FILTER("Delete Filter", "Filter Name:"),
        DEL_MANUFACTURER("Delete Brand", "Brand Name"),
        DEL_TYPE_DETAIL("Delete Type", "Type Name:");

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
