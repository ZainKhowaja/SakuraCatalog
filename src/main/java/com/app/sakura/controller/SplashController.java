package com.app.sakura.controller;

import com.app.sakura.config.AppConfig;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SplashController {
    @FXML
    private ImageView loader;

    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {
        loadImages();
    }

    public void loadImages(){
        File file = new File(AppConfig.RESOURCE_PATH+"splash3.gif");
        Image image = new Image(file.toURI().toString());
        loader.setImage(image);

        file = new File(AppConfig.RESOURCE_PATH+"fuji-icon.jpg");
        image = new Image(file.toURI().toString());
        logo.setImage(image);

    }
}
