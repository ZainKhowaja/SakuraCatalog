package com.app.sakura.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import com.app.sakura.entity.*;
import com.app.sakura.repository.*;
import com.app.sakura.service.DataValidator;
import com.app.sakura.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.util.ScreenUtils;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private Spinner<Double> volumes;

	@FXML
	private Spinner<Double> contains;

	@FXML
	private Spinner<Double> outerD;

	@FXML
	private Spinner<Double> note;

	@Autowired
	private ScreenUtils screen;

	@FXML
	private VBox imagePanel;
	
	@FXML
	private Tab productSearch;
	
	@Autowired
	private ConfigurableApplicationContext context;

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
		loadFilterType();
		loadManfacturers();
		loadTypeDetails();
	}

	private void loadManfacturers() {
		manufacturer.setItems(FXCollections.observableList(manufacturerRepository.findAll()));
	}

	private void loadTypeDetails() {
		typeDetails.setItems(FXCollections.observableList(typeDetailRepository.findAll()));
	}

	private void loadFilterType() {
		filterType.setItems(FXCollections.observableList(filterRepository.findAll()));
	}

	@FXML
	void addProduct(ActionEvent event) {
		Product productModal = composeAddProductModal();
		if(dataValidator.validateAddProduct(productModal)){
			ProductDetail productDetailSaved = productDetailRepository.save(productModal.getProductDetail());
			productModal.setProductDetail(productDetailSaved);
			productModal = productRepository.save(productModal);

			if(productModal != null){
				AlertUtil.showError("Product Added Successfully");
			}
		}else{
			AlertUtil.showError("Invalid Data");
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
		productDetail.setHeight(String.valueOf(height.getValue()));
		productDetail.setOutDiameter(String.valueOf(outerD.getValue()));
		productDetail.setInnerDiameter(String.valueOf(innerD.getValue()));
		productDetail.setContains(String.valueOf(contains.getValue()));
		productDetail.setVolume(String.valueOf(volumes.getValue()));
		product.setProductDetail(productDetail);
		if (filePath.size() > 0) {
			product.setImagePath(filePath.get(0).getAbsolutePath());
		} else {
			product.setImagePath(null);
		}
		return product;
	}

	@FXML
	void openImage(ActionEvent event) throws FileNotFoundException {
		
		filePath = fileChooser.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());
		
		for(File file : filePath) {
			Image image = new Image(new FileInputStream(file));
			ImageView view = new ImageView(image);
			view.setPreserveRatio(true);
			view.setFitWidth(imagePanel.getWidth());
			view.setFitHeight(171);
			imagePanel.getChildren().add(view);
		}
	}
	
	public void switchScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/sakura/view/ProductSearch.fxml"));
		loader.setControllerFactory(context::getBean);
		Node rootNode = loader.load();
		productSearch.setContent(rootNode);
	}
}
