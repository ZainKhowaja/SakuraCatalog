package com.app.sakura;

import com.sun.javafx.application.LauncherImpl;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@EnableScheduling
@EnableEncryptableProperties
@SpringBootApplication
public class SakuraFilterApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;
    private double xOffset = 0;
    private double yOffset = 0;

    @Value("${app.auth:true}")
    public boolean enableAuth;

    private static final Logger logger = LoggerFactory.getLogger(SakuraFilterApplication.class);
    @PostConstruct
    public void authenticate(){
        if (!appAuthentication()) {
            logger.info("APP NOT AUTHENTICATED");
            System.exit(-1);
        }
        logger.info("AUTHENTICATED");

    }
    public static void main(String[] args) {
        LauncherImpl.launchApplication(SakuraFilterApplication.class,SplashScreen.class,args);
        System.exit(-1);
    }

    public boolean appAuthentication() {
        String hash = System.getenv("APP_SAKURA");
        if(enableAuth) {
            return hash.equalsIgnoreCase("AE2B1FCA515949E5D54FB22B8ED95575");
        }else{
            return true;
        }
    }

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
        primaryStage.getIcons().add(new Image("fuji-icon.jpg"));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

}
