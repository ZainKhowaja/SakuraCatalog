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

@Component
public class ScreenUtils {

	private static final String VIEW_LOCATION = "/com/app/sakura/view/";

	@Autowired
	private ConfigurableApplicationContext springContext;

	private double xOffset = 0;

	private double yOffset = 0;

	public void switchScreen(Object node, SakuraScreen screen) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SakuraFilterApplication.class.getResource(VIEW_LOCATION + screen.getScreenName()));
			loader.setControllerFactory(springContext::getBean);
			Parent root = loader.load();
			Stage stage = (Stage) ((Node) node).getScene().getWindow();
			root.setOnMousePressed((event) -> {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			});

			root.setOnMouseDragged((event) -> {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);

			});
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("Style.css").toExternalForm());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			System.out.println("Error -> " + screen.getScreenName() + " Not Found !");
			e.printStackTrace();
		}
	}

	public Node getFXMLNode(SakuraScreen screen) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/sakura/view/"+screen.getScreenName()));
			loader.setControllerFactory(springContext::getBean);
			Node rootNode = loader.load();
			return rootNode;
		} catch (Exception e) {
			System.out.println("Error -> " + screen.getScreenName() + " Not Found !");
			throw new Exception(screen.getScreenName() + " Not Found !");
		}
	}

}
