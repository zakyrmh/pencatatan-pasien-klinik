package com.clinic;

import com.clinic.dao.UserDao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Membuat tabel users di database jika belum ada
        new UserDao().createTableIfNotExists();

        // Memuat file FXML untuk tampilan login dari folder resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

        // Membuat scene dari FXML dan menambahkan stylesheet CSS
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        // Mengatur scene
        primaryStage.setScene(scene);

        // Memberi judul
        primaryStage.setTitle("Login");

        // Menampilkan window
        primaryStage.show();
    }

    // Entry point aplikasi yang memanggil method launch() untuk memulai JavaFX application lifecycle.
    public static void main(String[] args) {
        launch(args);
    }
}
