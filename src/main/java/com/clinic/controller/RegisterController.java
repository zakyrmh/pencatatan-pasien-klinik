package com.clinic.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.clinic.dao.UserDao;
import com.clinic.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtRole;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;

    private UserDao userDao;

    public void initialize() {
        userDao = new UserDao();
        userDao.createTableIfNotExists();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String role = txtRole.getText().trim();
        String password = txtPassword.getText().trim();
        String confirm = txtConfirmPassword.getText().trim();

        if (name.isEmpty() || username.isEmpty() || role.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Semua field wajib diisi.");
            return;
        }

        if (!password.equals(confirm)) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Password dan konfirmasi tidak sama.");
            return;
        }

        // Cek apakah username sudah ada
        if (userDao.findByUsername(username) != null) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Username sudah terdaftar.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());

        // Simpan user baru
        String hashed = UserDao.hashPassword(password);
        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setRole(role);
        newUser.setPasswordHash(hashed);
        newUser.setCreatedAt(currentTime);
        newUser.setUpdatedAt(currentTime);

        boolean success = userDao.save(newUser);
        if (success) {
            successLabel.setVisible(true);
            successLabel.setManaged(true);
            successLabel.setText("Registrasi berhasil! Kembali ke login...");
            // Delay singkat lalu ke login
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    javafx.application.Platform.runLater(() -> {
                        goToLogin(null);
                    });
                } catch (InterruptedException ignored) {
                }
            }).start();
        } else {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText("Gagal menyimpan user. Coba lagi.");
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtName.getScene().getWindow();

            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
