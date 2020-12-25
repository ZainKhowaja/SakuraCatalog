package com.app.sakura.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.util.ScreenUtils;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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

	@FXML
	private TabPane tabPane;
	
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Pane borderPane;
	
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
			if(event.getSource().equals(addProductTab)) {
				addProductTab.setContent(screen.getFXMLNode(SakuraScreen.ADD_PRODUCT));
//				tabPane.setPrefHeight(664);
//				mainPane.setPrefHeight(626);
//				borderPane.setPrefSize(borderPane.getPrefWidth(), 664);
			}else if(event.getSource().equals(productSearchTab)) {
//				tabPane.setPrefHeight(900);
//				mainPane.setPrefHeight(900);
//				borderPane.setPrefSize(borderPane.getPrefWidth(), 1000);
				productSearchTab.setContent(screen.getFXMLNode(SakuraScreen.PRODUCT_SEARCH));
				
			}
		} catch (Exception e) {
			
		}
	}
}
