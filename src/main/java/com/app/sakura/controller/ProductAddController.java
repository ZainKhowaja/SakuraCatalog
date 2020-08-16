package com.app.sakura.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
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
	private ComboBox<?> filterType;

	@FXML
	private ComboBox<?> manufacturer;

	@FXML
	private ComboBox<?> typeDetails;

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

	}

	@FXML
	void addProduct(ActionEvent event) {

	}

	@FXML
	void openImage(ActionEvent event) throws FileNotFoundException {
		
		List<File> filePath = fileChooser.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());
		
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
