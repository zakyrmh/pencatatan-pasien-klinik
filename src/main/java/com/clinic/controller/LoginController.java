package com.clinic.controller;

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

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMessage;

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
            lblMessage.setText("Username dan password wajib diisi.");
            return;
        }

        User user = userDao.findByUsername(username);
        if (user == null) {
            lblMessage.setText("User tidak ditemukan.");
            return;
        }

        // Hash input password lalu bandingkan
        String hashedInput = UserDao.hashPassword(password);
        if (!hashedInput.equals(user.getPasswordHash())) {
            lblMessage.setText("Password salah.");
            return;
        }

        // Login sukses: simpan di Session
        Session.getInstance().setUser(user);

        // Buka halaman utama (main.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtUsername.getScene().getWindow(); // stage yang sama
            stage.setScene(scene);
            stage.setTitle("Aplikasi Klinik - Selamat Datang, " + user.getName());
        } catch (IOException e) {
            e.printStackTrace();
            lblMessage.setText("Gagal memuat halaman utama.");
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Registrasi User");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
