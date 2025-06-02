package com.clinic.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.clinic.database.DatabaseUtil;
import com.clinic.model.User;

public class UserDao {
    // Membuat tabel users jika belum ada
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                   + "username VARCHAR(50) NOT NULL UNIQUE,"
                   + "password VARCHAR(255) NOT NULL,"
                   + "role ENUM('admin', 'doctor', 'nurse', 'pharmacy') NOT NULL,"
                   + "name VARCHAR(100) NOT NULL,"
                   + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                   + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                   + ");";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Simpan user baru (registrasi)
    public boolean save(User user) {
        String sql = "INSERT INTO users(username, password, role, name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getName());
            ps.setString(5, user.getCreatedAt());
            ps.setString(6, user.getUpdatedAt());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Jika username sudah ada, UNIQUE constraint violation
            e.printStackTrace();
            return false;
        }
    }

    // Cari user berdasarkan username
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, role, name FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password"));
                u.setName(rs.getString("name"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hash password dengan SHA-256 (contoh sederhana)
    public static String hashPassword(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(plain.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
    }
}
