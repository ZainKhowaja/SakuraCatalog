package com.app.sakura.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.app.sakura.SakuraFilterApplication;
import com.app.sakura.enums.SakuraScreen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Component
public class ScreenUtils {

	private static final String VIEW_LOCATION = "/com/app/sakura/view/";
	
	@Autowired
    private ConfigurableApplicationContext springContext ;

	public void switchScreen(Object node, SakuraScreen screen) {
		try {
			FXMLLoader loader = new FXMLLoader(SakuraFilterApplication.class.getResource(VIEW_LOCATION + screen.getScreenName()));
			loader.setControllerFactory(springContext::getBean);
			Parent root = loader.load();
			Stage stage = (Stage) ((Node) node).getScene().getWindow();
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.println("Error -> " + screen.getScreenName() + " Not Found !");
			e.printStackTrace();
		}
	}

}
