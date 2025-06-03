package com.clinic.controller;

import java.io.IOException;

import com.clinic.dao.UserDao;
import com.clinic.model.User;
import com.clinic.utils.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label errorLabel;
    @FXML private Label successLabel;

    private UserDao userDao;

    public void initialize() {
        userDao = new UserDao();
        userDao.createTableIfNotExists(); // Pastikan tabel ada
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Username dan password wajib diisi.");
            return;
        }

        User user = userDao.findByUsername(username);
        if (user == null) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("User tidak ditemukan.");
            return;
        }

        // Hash input password lalu bandingkan
        String hashedInput = UserDao.hashPassword(password);
        if (!hashedInput.equals(user.getPasswordHash())) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Password salah.");
            return;
        }

        // Login sukses: simpan di Session
        Session.getInstance().setUser(user);

        // Buka halaman utama (main.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtUsername.getScene().getWindow(); // stage yang sama
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Aplikasi Klinik - Selamat Datang, " + user.getName());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Gagal memuat halaman utama.");
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Registrasi User");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
