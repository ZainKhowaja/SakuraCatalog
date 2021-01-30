package com.app.sakura.controller;

import com.app.sakura.entity.*;
import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.model.PrintProductDetails.PrintProductDetailsBuilder;
import com.app.sakura.model.SearchProduct;
import com.app.sakura.repository.*;
import com.app.sakura.service.impl.PrinterService;
import com.app.sakura.util.AlertUtil;
import com.app.sakura.util.ScreenUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class ProductSearchController {

    public static String sakuraId;
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
    private Label note;
    @FXML
    private Label containPeices;
    @FXML
    private Label innerDiameter;
    @FXML
    private Label outer;
    @FXML
    private Label outerDSec;
    @FXML
    private Label innerDSec;
    @FXML
    private Label height;
    @FXML
    private Label thread;
    @FXML
    private Label typeDetail;
    @FXML
    private Label filterType;
    @FXML
    private Label brand;
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
    @Autowired
    private PrinterService printerService;

    @FXML
    void clicked(ActionEvent event) {
        System.out.println(dataBTN.getText());
        System.out.println("Addin Data");
        addDummyData();
    }

    @FXML
    void initialize() {
        searchByCmbBox.getItems().addAll("Fuji Number", "Reference Number");
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
            if (newSelection != null) {
                newSelection.getColumnOne();
                String sakuraId = isReferenceSelected() ? newSelection.getColumnThree() : newSelection.getColumnOne();
                loadDetails(sakuraId);
            }
        });

    }
    @Autowired
    private ProductDetailRepository productDetailRepository;
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
            for (ProductReference product : productReferenceRepository.findByProductActive(0)) {
                SearchProduct searchProduct = new SearchProduct();

                searchProduct.setColumnOne(product.getReference());
                searchProduct.setColumnTwo(product.getManufacturer().getName());
                searchProduct.setColumnThree(product.getProduct().getSakuraNo());

                response.add(searchProduct);
            }
        } else {
            for (Product product : productRepository.findByActive(0)) {
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
            columnThree.setText("Fuji No #");
        } else {
            // Adjust Size
            columnFour.setVisible(true);
            columnFour.setPrefWidth(176.5);
            columnThree.setPrefWidth(155.2);

            columnOne.setText("Fuji No #");
            columnTwo.setText("Filter Type");
            columnThree.setText("Type Detail");

        }

    }

    private void loadDetails(String sakuraId) {
        Product product = productRepository.findBySakuraNoAndActive(sakuraId, 0);
        sakuraNumber.setText(product.getSakuraNo());
        brand.setText(product.getManufacturer().getName());
        filterType.setText(product.getFilter().getName());
        typeDetail.setText(product.getTypeDetail().getName());
        thread.setText(product.getProductDetail().getThread());
        height.setText(product.getProductDetail().getHeight() + " " + product.getProductDetail().getHeightMeasurement());
        outer.setText(product.getProductDetail().getOutDiameter() + " " + product.getProductDetail().getOuterMeasurement());
        innerDiameter.setText(product.getProductDetail().getInnerDiameter() + " " + product.getProductDetail().getInnerMeasurement());
        outerDSec.setText(product.getProductDetail().getOutSecDiameter() + " " + product.getProductDetail().getOuterSecMeasurement());
        innerDSec.setText(product.getProductDetail().getInnerSecDiameter() + " " + product.getProductDetail().getInnerSecMeasurement());

        containPeices.setText(product.getProductDetail().getContains());
        note.setText(product.getProductDetail().getNote());

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
            row.setColumnTwo(ref.getManufacturer().getName());
            allRef.add(row);
        });

        refTableView.setItems(allRef);

        ProductSearchController.sakuraId = sakuraId;

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
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            screen.switchScreen(refTableView, SakuraScreen.ADD_REFERENCE, true);
            loadReference(sakuraId);
        } else {
            AlertUtil.showError("Select any item");
        }
    }

    public void addBrand() {
        screen.switchScreen(refTableView, SakuraScreen.ADD_BRAND, true);
    }

    public void printData() {


        if (tableView.getSelectionModel().getSelectedItem() != null) {

            SearchProduct selection = tableView.getSelectionModel().getSelectedItem();
            String sakuraId = isReferenceSelected() ? selection.getColumnThree() : selection.getColumnOne();

            HashMap<String, String> reference = new HashMap<String, String>();

            productReferenceRepository
                    .findByProduct_sakuraNo(sakuraId)
                    .forEach(product -> reference.put(product.getReference(), product.getManufacturer().getName()));

            String imageUrl = "";
            Optional<ProductImage> productImage = productImageRepository.findByProductSakuraNo(sakuraId).stream().findFirst();
            if (productImage.isPresent()) {
                imageUrl = productImage.get().getImageUrl();
            }

            Product product = productRepository.findBySakuraNoAndActive(sakuraId, 0);

            PrintProductDetailsBuilder builder = PrintProductDetailsBuilder.newBuilder();
            builder
                    .setFilter(product.getFilter().getName())
                    .setFujiNo(product.getSakuraNo())
                    .setHeight(product.getProductDetail().getHeight())
                    .setInnerD(product.getProductDetail().getInnerDiameter() + " " + product.getProductDetail().getInnerMeasurement())
                    .setManufacturer(product.getManufacturer().getName())
                    .setNote(product.getProductDetail().getNote())
                    .setOuterD(product.getProductDetail().getOutDiameter() + " " + product.getProductDetail().getOuterMeasurement())
                    .setOuterSecD(product.getProductDetail().getOutSecDiameter() + " " + product.getProductDetail().getOuterSecMeasurement())
                    .setInnerSecD(product.getProductDetail().getInnerSecDiameter() + " " + product.getProductDetail().getInnerSecMeasurement())
                    .setPackagingPerCaton(product.getProductDetail().getContains())
                    .setReference(reference)
                    .setThread(product.getProductDetail().getThread())
                    .setTypeDetail(product.getTypeDetail().getName())
                    .setImageName(imageUrl);

            try {
                printerService.printProductDetails(builder.build());
            } catch (IOException | PrinterException | URISyntaxException e) {
                AlertUtil.showError("Error Printing Data");
                e.printStackTrace();
            }

        } else {
            AlertUtil.showError("Select any item");
        }


    }

    public void deleteProduct() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            if (AlertUtil.showDeleteConfirmation()) {
                String sakuraNo = isReferenceSelected() ?
                        tableView.getSelectionModel().getSelectedItem().getColumnThree() :
                        tableView.getSelectionModel().getSelectedItem().getColumnOne();

                productRepository.deleteProduct(sakuraNo);
                AlertUtil.showInfo(String.format("%s deleted successfully", sakuraNo));
                updateTable();
            }

        } else {
            AlertUtil.showError("Select any item to delete");
        }
    }

    public void updateProduct() throws Exception {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            String sakuraNo = isReferenceSelected() ?
                    tableView.getSelectionModel().getSelectedItem().getColumnThree() :
                    tableView.getSelectionModel().getSelectedItem().getColumnOne();

			Product product = productRepository.findBySakuraNoAndActive(sakuraNo, 0);
			ProductAddController.isUpdate = true;
			ProductAddController.product = product;

			screen.switchScreen(tableView,SakuraScreen.UPDATE_PRODUCT,true);

            ProductAddController.isUpdate = false;
            ProductAddController.product = null;
            updateTable();


        } else {
            AlertUtil.showError("Select any item to update");
        }
    }

    @FXML
    void hover(MouseEvent event) {
        Label btn = (Label) event.getSource();
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
            btn.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #e0e0e0;");
        else if (event.getEventType() == MouseEvent.MOUSE_EXITED)
            btn.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
    }
}
