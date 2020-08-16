package com.app.sakura.controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Component
public class ProductSearchController {
	
	@FXML
	private Button dataBTN;
	
	public void clicked() {
		System.out.println(dataBTN.getText());
		System.out.println("dataBTN");
	}
}
