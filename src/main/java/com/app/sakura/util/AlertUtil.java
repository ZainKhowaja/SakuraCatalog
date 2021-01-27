package com.app.sakura.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtil {

    private static final Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    private static final Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Delete Item", ButtonType.YES, ButtonType.NO);

    public static void showError(String message) {
        errorAlert.setGraphic(null);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }


    public static void showInfo(String message) {
        infoAlert.setGraphic(null);
        infoAlert.setHeaderText("Info");
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public static boolean showDeleteConfirmation() {
        confirmation.setGraphic(null);
        confirmation.setHeaderText("Delete");
        confirmation.setContentText("Confirm you want to delete item ?");
        confirmation.getButtonTypes().clear();
        confirmation.getButtonTypes().add(ButtonType.YES);
        confirmation.getButtonTypes().add(ButtonType.NO);
        confirmation.showAndWait();
        return confirmation.getResult() == ButtonType.YES;
    }
}