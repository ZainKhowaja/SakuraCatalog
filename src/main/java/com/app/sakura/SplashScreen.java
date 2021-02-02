package com.app.sakura;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {
    private Parent rootNode;

    @FXML
    private TextField loading;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/splash.fxml"));
        rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image("fuji-icon.jpg"));
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if(info.getType() == StateChangeNotification.Type.BEFORE_START){
            try {
                stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        Scene scene = rootNode.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
