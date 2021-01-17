package com.app.sakura.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import com.app.sakura.entity.*;
import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.repository.*;
import com.app.sakura.service.DataValidator;
import com.app.sakura.util.AlertUtil;
import com.app.sakura.util.FileUtil;
import com.app.sakura.util.ScreenUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

@Component
public class ProductAddController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label logout;

	@FXML
	private TextField sakuraId;

	@FXML
	private TextField refId;

	@FXML
	private ComboBox<Filter> filterType;

	@FXML
	private ComboBox<Manufacturer> manufacturer;

	@FXML
	private ComboBox<TypeDetail> typeDetails;

	@FXML
	private Button chooseImage;

	@FXML
	private Button addProduct;

	@FXML
	private Spinner<Double> innerD;

	@FXML
	private Spinner<Double> height;

	@FXML
	private Spinner<String> thread;

	@FXML
	private Spinner<Double> contains;

	@FXML
	private Spinner<Double> outerD;

	@FXML
	private TextField note;

	@FXML
	private ComboBox<String> heightM;

	@FXML
	private ComboBox<String> innerM;

	@FXML
	private ComboBox<String> outerM;


	@Autowired
	private ScreenUtils screen;

	@FXML
	private VBox imagePanel;
	
	@FXML
	private Tab productSearch;

	@FXML
	private Label addFilter;

	@FXML
	private Label addMan;

	@FXML
	private Label addType;


	private FileChooser fileChooser = new FileChooser();

	@Autowired
	private FilterRepository filterRepository;
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	@Autowired
	private TypeDetailRepository typeDetailRepository;
	@Autowired
	private DataValidator dataValidator;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	@Autowired
	private ProductImageRepository productImageRepository;

	private List<File> filePath;

	@FXML
	void exit(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void hover(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
			logout.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #e0e0e0;");
		else if (event.getEventType() == MouseEvent.MOUSE_EXITED)
			logout.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

	}

	@FXML
	public void logout(Event event) throws IOException {
		screen.switchScreen(event.getSource(), SakuraScreen.LOGIN);
	}

	@FXML
	void initialize() {
		loadAddButton();
		loadFilterType();
		loadManfacturers();
		loadTypeDetails();
		filePath = new ArrayList<>();
	}
	private ImageView getAddButtonView(){
		Image img = new Image("add.png");
		ImageView view = new ImageView(img);
		view.setFitHeight(20);
		view.setPreserveRatio(true);
		return view;
	}
	private void loadAddButton() {
		ImageView view = getAddButtonView();
		addFilter.setGraphic(view);
		addMan.setGraphic(getAddButtonView());
		addType.setGraphic(getAddButtonView());
	}

	@FXML
	public void openAddFilter(){
		openAddWindow(AddGenericeController.AddWindowType.FILTER);
		loadFilterType();
	}

	@FXML
	public void openAddMan(){
		openAddWindow(AddGenericeController.AddWindowType.MANUFACTURER);
		loadManfacturers();
	}

	@FXML
	public void openAddType() {
		openAddWindow(AddGenericeController.AddWindowType.TYPE_DETAIL);
		loadTypeDetails();
	}


	private void openAddWindow(AddGenericeController.AddWindowType windowType) {
		AddGenericeController.windowType = windowType;
		screen.switchScreen(addFilter, SakuraScreen.ADD_GENERIC_WINDOW, true);
	}

	private void loadManfacturers() {
		manufacturer.setItems(FXCollections.observableList(manufacturerRepository.findAll()));
	}

	@FXML
	private void loadTypeDetails() {
		Filter selectedFilter = filterType.getSelectionModel().getSelectedItem();
		if(selectedFilter == null){
			typeDetails.setItems(FXCollections.emptyObservableList());
		}else {
			typeDetails.setItems(FXCollections.observableList(typeDetailRepository.findByFilterId(selectedFilter.getId())));
		}
	}

	private void loadFilterType() {
		filterType.setItems(FXCollections.observableList(filterRepository.findAll()));
	}
	//TODO: add primary application field
	//TODO: clear screen after adding
	//TODO: alert error (if possible)

	@FXML
	void addProduct(ActionEvent event) {
		Product productModal = composeAddProductModal();
		if(dataValidator.validateAddProduct(productModal)){
			ProductDetail productDetailSaved = productDetailRepository.save(productModal.getProductDetail());
			productModal.setProductDetail(productDetailSaved);
			productModal = productRepository.save(productModal);
			saveProductImage(productModal);
			if(productModal != null){
				AlertUtil.showInfo("Product Added Successfully");
				clearScreen();
			}
		}else{
			AlertUtil.showError("Invalid Data");
		}

	}
	private void clearScreen(){
		sakuraId.setText("");
		filterType.getSelectionModel().clearSelection();
		manufacturer.getSelectionModel().clearSelection();
		typeDetails.getSelectionModel().clearSelection();
		height.getEditor().setText("");
		innerD.getEditor().setText("");
		outerD.getEditor().setText("");
		contains.getEditor().setText("");
		heightM.getSelectionModel().selectFirst();
		innerM.getSelectionModel().selectFirst();
		outerM.getSelectionModel().selectFirst();
		thread.getEditor().setText("");
		note.setText("");

	}
	private void saveProductImage(Product product) {
		if (filePath != null && filePath.size() > 0) {
			for(File file : filePath) {
				ProductImage image = new ProductImage();
				image.setImageUrl(FileUtil.copyFile(file.getAbsolutePath()));
				image.setProduct(product);
				productImageRepository.save(image);
			}
		}
	}

	private Product composeAddProductModal(){
		Product product = new Product();
		product.setSakuraNo(sakuraId.getText());
		product.setRefrenceNo(refId.getText());
		product.setTypeDetail(typeDetails.getSelectionModel().getSelectedItem());
		product.setManufacturer(manufacturer.getSelectionModel().getSelectedItem());
		product.setFilter(filterType.getSelectionModel().getSelectedItem());
		ProductDetail productDetail = new ProductDetail();
		productDetail.setSakuraNo(sakuraId.getText());
		productDetail.setHeight(height.getEditor().getText());
		productDetail.setOutDiameter(outerD.getEditor().getText());
		productDetail.setInnerDiameter(innerD.getEditor().getText());
		productDetail.setContains(contains.getEditor().getText());
		productDetail.setThread(thread.getEditor().getText());
		productDetail.setHeightMeasurement(heightM.getSelectionModel().getSelectedItem());
		productDetail.setOuterMeasurement(outerM.getSelectionModel().getSelectedItem());
		productDetail.setInnerMeasurement(innerM.getSelectionModel().getSelectedItem());
		productDetail.setNote(note.getText());

		product.setProductDetail(productDetail);
		return product;
	}
	@FXML
	void openImage(ActionEvent event) throws FileNotFoundException {
		
		List<File> tempFilePath = fileChooser.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());
		tempFilePath.forEach(file -> filePath.add(file));

		for(File file : filePath) {
			Image image = new Image(new FileInputStream(file));
			ImageView view = new ImageView(image);
			view.setPreserveRatio(true);
			view.setFitWidth(imagePanel.getWidth());
			view.setFitHeight(171);
			imagePanel.getChildren().add(view);
		}
	}
}
