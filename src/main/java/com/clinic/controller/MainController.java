package com.clinic.controller;

import java.io.IOException;

import com.clinic.utils.Session;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

    @FXML
    private Label lblWelcome;

    public void initialize() {
        // Pastikan user sudah login; jika tidak, redirect ke login
        if (Session.getInstance().getUser() == null) {
            redirectToLogin();
            return;
        }

        // Hindari NullPointerException jika lblWelcome tidak terhubung dengan FXML
        if (lblWelcome != null) {
            String name = Session.getInstance().getUser().getName();
            lblWelcome.setText("Selamat datang, " + name + "!");
        } else {
            System.err.println("Label 'lblWelcome' tidak ditemukan di FXML.");
        }
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Login");
            stage.show();

            stage.initStyle(StageStyle.DECORATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToPatient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient.fxml"));

            // Dapatkan Stage yang benar
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Simpan ukuran dan posisi stage saat ini
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            double currentX = stage.getX();
            double currentY = stage.getY();
            boolean wasMaximized = stage.isMaximized();

            // Load scene baru
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            // Set scene baru
            stage.setScene(newScene);

            // Restore ukuran dan posisi
            Platform.runLater(() -> {
                if (wasMaximized) {
                    stage.setMaximized(true);
                } else {
                    stage.setX(currentX);
                    stage.setY(currentY);
                    stage.setWidth(currentWidth);
                    stage.setHeight(currentHeight);
                }
            });

            stage.setTitle("Patients");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
