package com.app.sakura.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.sakura.model.SearchProduct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

@Component
public class ProductSearchController {

	@FXML
	private Button dataBTN;

	@FXML
	private TableColumn<?, ?> columnOne;

	@FXML
	private TableColumn<?, ?> columnTwo;

	@FXML
	private TableColumn<?, ?> columnThree;

	@FXML
	private TableColumn<?, ?> columnFour;

	@FXML
	private ComboBox<String> searchByCmbBox;

	@FXML
	private TextField searchText;

	@FXML
	private Label sakuraNumber;

	@FXML
	private Label volume;

	@FXML
	private Label containPeices;

	@FXML
	private Label innerDiameter;

	@FXML
	private Label outer;

	@FXML
	private Label height;

	@FXML
	private Label primaryApp;

	@FXML
	private Label typeDetail;

	@FXML
	private Label filterType;

	@FXML
	private Label status;

	@FXML
	private VBox imagePanel;

	@FXML
	private TableView<SearchProduct> tableView;
	
	ObservableList<SearchProduct> dummyData = getDummyData();

	@FXML
	void clicked(ActionEvent event) {
		System.out.println(dataBTN.getText());
		System.out.println("dataBTN");
	}

	@FXML
	void initialize() {
		searchByCmbBox.getItems().addAll("Sakura Number", "Reference Number");
		searchByCmbBox.getSelectionModel().selectFirst();
		searchByCmbBox.getSelectionModel().selectedItemProperty()
				.addListener((option, oldvalue, newvalue) -> searchByChanged());

		// Load Table
		columnOne.setCellValueFactory(new PropertyValueFactory<>("ColumnOne"));
		columnTwo.setCellValueFactory(new PropertyValueFactory<>("ColumnTwo"));
		columnThree.setCellValueFactory(new PropertyValueFactory<>("ColumnThree"));
		columnFour.setCellValueFactory(new PropertyValueFactory<>("ColumnFour"));
		
		tableView.setItems(dummyData);
		FilteredList<SearchProduct> filteredData = new FilteredList<>(dummyData, p -> true);
		searchText.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(dummyFilter -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				return dummyFilter.getColumnOne().contains(newValue.toLowerCase());

			});
		});
		List<String> data= new ArrayList<String>();
		data.forEach(p -> {});
		SortedList<SearchProduct> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);
	}

	private ObservableList<SearchProduct> getDummyData() {
		
		ObservableList<SearchProduct> response = FXCollections.observableArrayList();
		
		SearchProduct product = new SearchProduct();
		product.setColumnOne("A-1002");
		product.setColumnTwo("Air Filter");
		product.setColumnThree("Round Metal End Cap Element");
		product.setColumnFour("DAIHATSU 17801-87201");
		
		SearchProduct product2 = new SearchProduct();
		product2.setColumnOne("O-1001");
		product2.setColumnTwo("Oil Filter");
		product2.setColumnThree("Full Flow Element");
		product2.setColumnFour("MITSUBISHI 31340-12030");
		
		
		response.add(product);
		response.add(product2);
		
		return response;
		
	}

	@FXML
	void search(KeyEvent event) {

	}

	private void searchByChanged() {

	}
}
