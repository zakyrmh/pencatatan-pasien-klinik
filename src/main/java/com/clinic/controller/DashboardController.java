package com.clinic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class DashboardController {

    @FXML
    public void handleButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText("Tombol ditekan!");
        alert.showAndWait();
    }
}
