package com.app.sakura;

import com.app.sakura.util.AlertUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SakuraFilterApplication extends Application {

	private ConfigurableApplicationContext context;
	private Parent rootNode;
	private double xOffset = 0;
	private double yOffset = 0;

	public void init() throws Exception {
		context = SpringApplication.run(SakuraFilterApplication.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
		loader.setControllerFactory(context::getBean);
		rootNode = loader.load();
		rootNode.setOnMousePressed((event) -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		rootNode.setOnMouseDragged((event) -> {
			primaryStage.setX(event.getScreenX() - xOffset);
			primaryStage.setY(event.getScreenY() - yOffset);

		});

		Scene scene = new Scene(rootNode);
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("Style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		context.close();
	}

	public static void main(String[] args) {
		if(appAuthentication()) {
			launch(args);
		}else{
//			AlertUtil.showError("Unable to verify computer");
		}
		System.exit(-1);
	}

	private static boolean appAuthentication() {
		String hash = System.getenv("APP_SAKURA");
		System.out.println("Hash : "+hash);
		return hash == null ? true : hash.equalsIgnoreCase("AE2B1FCA515949E5D54FB22B8ED95575");
	}

}
