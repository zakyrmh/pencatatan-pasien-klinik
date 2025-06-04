package com.clinic.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static String URL = "jdbc:mysql://localhost:3306/clinic";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Koneksi database berhasil!");
            return conn;
        } catch (Exception e) {
            System.err.println("Gagal koneksi ke database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
