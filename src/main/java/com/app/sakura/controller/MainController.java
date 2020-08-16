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
import org.springframework.stereotype.Controller;

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

@Controller
public class MainController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label logout;

	@Autowired
	private ScreenUtils screen;
	
	@FXML
	private Tab productSearchTab;
	
	@FXML
	private Tab addProductTab;
	
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
	
	public void switchScreen(Event event) throws IOException {
		try {
			System.out.println(event.getSource().equals(addProductTab) +","+event.getSource().equals(productSearchTab));
			if(event.getSource().equals(addProductTab)) {
				addProductTab.setContent(screen.getFXMLNode(SakuraScreen.ADD_PRODUCT));
			}else if(event.getSource().equals(productSearchTab)) {
				productSearchTab.setContent(screen.getFXMLNode(SakuraScreen.PRODUCT_SEARCH));
			}
		} catch (Exception e) {
			
		}
	}
}
