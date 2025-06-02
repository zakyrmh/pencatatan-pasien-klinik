package com.clinic.controller;

import com.clinic.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private Label lblWelcome;

    public void initialize() {
        // Pastikan user sudah login; jika tidak, redirect ke login
        if (Session.getInstance().getUser() == null) {
            redirectToLogin();
            return;
        }
        String name = Session.getInstance().getUser().getName();
        lblWelcome.setText("Selamat datang, " + name + "!");
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // Hapus session, kembali ke login
        Session.getInstance().clear();
        redirectToLogin();
    }
}
