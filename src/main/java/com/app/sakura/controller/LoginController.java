package com.app.sakura.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.sakura.enums.SakuraScreen;
import com.app.sakura.util.ScreenUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

@Component
public class LoginController {
	@FXML
	private PasswordField password;

	@FXML
	private Label errorMessage;

	@FXML
	private TextField username;

	@FXML
	private Label logout;

	@Autowired
	private ScreenUtils screen;

	@FXML
	void login(ActionEvent event) throws IOException {
		errorMessage.setText("");
		if (username.getText().equals("admin") && password.getText().equals("admin")) {
			screen.switchScreen(event.getSource(), SakuraScreen.MAIN_SCREEN);

		} else
			errorMessage.setText("Wrong username or password");
	}

	@FXML
	void hover(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
			logout.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #e0e0e0;");
		else if (event.getEventType() == MouseEvent.MOUSE_EXITED)
			logout.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

	}

	@FXML
	void exit() {
		System.exit(0);
	}

}
