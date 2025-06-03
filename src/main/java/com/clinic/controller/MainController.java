package com.clinic.controller;

import java.io.IOException;

import com.clinic.utils.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

    @FXML private Label lblWelcome;

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
            stage.setTitle("Login");

            // Pastikan stage bisa di-maximize
            stage.setResizable(true);
            stage.initStyle(StageStyle.DECORATED);
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
