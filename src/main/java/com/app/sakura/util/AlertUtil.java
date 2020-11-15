package com.app.sakura.util;

import javafx.scene.control.Alert;

public class AlertUtil {

    private static final Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public static void showError(String message){
        errorAlert.setGraphic(null);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }


    public static void showInfo(String message){
        infoAlert.setGraphic(null);
        infoAlert.setHeaderText("Info");
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }
}
