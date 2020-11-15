package com.app.sakura.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.sakura.entity.Brand;
import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.Product;
import com.app.sakura.entity.ProductImage;
import com.app.sakura.entity.ProductReference;
import com.app.sakura.entity.TypeDetail;
import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.model.SearchProduct;
import com.app.sakura.repository.BrandRepository;
import com.app.sakura.repository.FilterRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.ProductImageRepository;
import com.app.sakura.repository.ProductReferenceRepository;
import com.app.sakura.repository.ProductRepository;
import com.app.sakura.repository.TypeDetailRepository;
import com.app.sakura.util.ScreenUtils;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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
	private HBox imagePanel;

	@FXML
	private TableView<SearchProduct> refTableView;

	@FXML
	private TableColumn<?, ?> refColumnOne;

	@FXML
	private TableColumn<?, ?> refColumnTwo;

	@FXML
	private TableView<SearchProduct> tableView;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private FilterRepository filterRepository;

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Autowired
	private TypeDetailRepository typeDetailRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductReferenceRepository productReferenceRepository;

	@Autowired
	private BrandRepository BrandRepository;

	@Autowired
	private ScreenUtils screen;

	public static String sakuraId;

	@FXML
	void clicked(ActionEvent event) {
		System.out.println(dataBTN.getText());
		System.out.println("Addin Data");
		addDummyData();
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

		updateTable();
	}

	private void updateTable() {
		ObservableList<SearchProduct> dummyData = getDummyData();
		tableView.setItems(dummyData);
		FilteredList<SearchProduct> filteredData = new FilteredList<>(dummyData, p -> true);
		searchText.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(dummyFilter -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				return dummyFilter.getColumnOne().toLowerCase().contains(newValue.toLowerCase());

			});
		});
		SortedList<SearchProduct> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			newSelection.getColumnOne();
			String sakuraId = isReferenceSelected() ? newSelection.getColumnThree() : newSelection.getColumnOne();
			loadDetails(sakuraId);
		});

	}

	private ObservableList<SearchProduct> getDummyData() {

		ObservableList<SearchProduct> response = FXCollections.observableArrayList();

//		SearchProduct product1 = new SearchProduct();
//		product1.setColumnOne("A-1002");
//		product1.setColumnTwo("Air Filter");
//		product1.setColumnThree("Round Metal End Cap Element");
//		product1.setColumnFour("DAIHATSU 17801-87201");
//		
//		SearchProduct product2 = new SearchProduct();
//		product2.setColumnOne("O-1001");
//		product2.setColumnTwo("Oil Filter");
//		product2.setColumnThree("Full Flow Element");
//		product2.setColumnFour("MITSUBISHI 31340-12030");
//		
//		response.add(product1);
//		response.add(product2);
		if (isReferenceSelected()) {
			for (ProductReference product : productReferenceRepository.findAll()) {
				SearchProduct searchProduct = new SearchProduct();
				
				searchProduct.setColumnOne(product.getReference());
				searchProduct.setColumnTwo(product.getBrand().getName());
				searchProduct.setColumnThree(product.getProduct().getSakuraNo());
				
				response.add(searchProduct);
			}
		} else {
			for (Product product : productRepository.findAll()) {
				SearchProduct searchProduct = new SearchProduct();

				searchProduct.setColumnOne(product.getSakuraNo());
				searchProduct.setColumnTwo(product.getFilter().getName());
				searchProduct.setColumnThree(product.getTypeDetail().getName());
				searchProduct.setColumnFour(product.getPrimaryApplication());
				
				response.add(searchProduct);
			}
		}

		return response;
	}

	@FXML
	void search(KeyEvent event) {

	}

	private void searchByChanged() {
		updateTable();
		if (isReferenceSelected()) {
			System.out.println("changed");
			// Adjust Size
//			System.out.println(columnThree.getWidth() + " Coloumn three");
//			System.out.println(columnFour.getWidth() + " Coloumn four");
			columnFour.setPrefWidth(0);
			columnFour.setVisible(false);

			columnThree.setPrefWidth(155.2 + 176.5);

			columnOne.setText("Reference #");
			columnTwo.setText("Manufacture");
			columnThree.setText("Sakura #");
		} else {
			// Adjust Size
			columnFour.setVisible(true);
			columnFour.setPrefWidth(176.5);
			columnThree.setPrefWidth(155.2);

			columnOne.setText("Sakura #");
			columnTwo.setText("Filter Type");
			columnThree.setText("Type Detail");

		}

	}

	private void loadDetails(String sakuraId) {
		Product product = productRepository.findBySakuraNo(sakuraId);
		sakuraNumber.setText(product.getSakuraNo());
//		status.setText(product.get);
		filterType.setText(product.getFilter().getName());
		typeDetail.setText(product.getTypeDetail().getName());
		primaryApp.setText(product.getPrimaryApplication());
		height.setText(product.getProductDetail().getHeight());
		outer.setText(product.getProductDetail().getOutDiameter());
		innerDiameter.setText(product.getProductDetail().getInnerDiameter());

		containPeices.setText(product.getProductDetail().getContains());
		volume.setText(product.getProductDetail().getVolume());

		imagePanel.getChildren().clear();
		List<ProductImage> images = productImageRepository.findByProductSakuraNo(sakuraId);
		for (ProductImage path : images) {
			Image image = null;
			try {
				image = new Image(new FileInputStream(new File(path.getImageUrl())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ImageView view = new ImageView(image);
			view.setPreserveRatio(true);
			view.setFitWidth(imagePanel.getWidth());
			view.setFitHeight(171);
			imagePanel.getChildren().add(view);
		}
		loadReference(sakuraId);
	}

	private void loadReference(String sakuraId) {
		refColumnOne.setCellValueFactory(new PropertyValueFactory<>("ColumnOne"));
		refColumnTwo.setCellValueFactory(new PropertyValueFactory<>("ColumnTwo"));

		ObservableList<SearchProduct> allRef = FXCollections.observableArrayList();

		productReferenceRepository.findByProduct_sakuraNo(sakuraId).forEach(ref -> {
			SearchProduct row = new SearchProduct();
			row.setColumnOne(ref.getReference());
			row.setColumnTwo(ref.getBrand().getName());
			allRef.add(row);
		});

		refTableView.setItems(allRef);

		this.sakuraId = sakuraId;

	}

	private void addDummyData() {

		System.out.println("dummy");

		Filter filter = new Filter();
		filter.setName("Air Filter");
		filterRepository.save(filter);

		Filter filter1 = new Filter();
		filter1.setName("Oil Filter");
		filterRepository.save(filter1);

		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Toyota");
		manufacturerRepository.save(manufacturer);

		Manufacturer manufacturer1 = new Manufacturer();
		manufacturer1.setName("Tesla");
		manufacturerRepository.save(manufacturer1);

		TypeDetail typeDetail = new TypeDetail();
		typeDetail.setName("Round Metal End Cap Element");
		typeDetailRepository.save(typeDetail);

		TypeDetail typeDetail1 = new TypeDetail();
		typeDetail1.setName("Metal End Cap Element");
		typeDetailRepository.save(typeDetail1);

	}

	public boolean isReferenceSelected() {
		return searchByCmbBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("Reference Number");
	}

	public void addReference() {
//		Brand brand = new Brand();
//		brand.setName("Tesla");
//		BrandRepository.save(brand);
		screen.switchScreen(refTableView, SakuraScreen.ADD_REFERENCE, true);
//		if(BrandRepository.count()<=0) {

//		}

	}
}
