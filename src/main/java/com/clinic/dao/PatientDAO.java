package com.clinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.clinic.database.DatabaseUtil;
import com.clinic.model.Patient;

public class PatientDAO {

    public PatientDAO() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS patients ("
                + "id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                + "medical_record VARCHAR(50) NOT NULL,"
                + "name VARCHAR(100) NOT NULL,"
                + "date_of_birth DATE NOT NULL,"
                + "gender ENUM('male','female') NOT NULL,"
                + "address TEXT,"
                + "phone VARCHAR(20),"
                + "identity_number VARCHAR(50),"
                + "registered_at DATE NOT NULL,"
                + "created_at DATETIME NOT NULL,"
                + "updated_at DATETIME NOT NULL"
                + ");";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("Failed to create table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patients (medical_record, name, date_of_birth, gender, address, phone, identity_number, registered_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getMedicalRecord());
            pstmt.setString(2, patient.getName());
            pstmt.setDate(3, java.sql.Date.valueOf(patient.getDateOfBirth()));
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getAddress());
            pstmt.setString(6, patient.getPhone());
            pstmt.setString(7, patient.getIdentityNumber());
            pstmt.setDate(8, java.sql.Date.valueOf(patient.getRegisteredAt()));
            pstmt.setTimestamp(9, java.sql.Timestamp.valueOf(patient.getCreatedAt()));
            pstmt.setTimestamp(10, java.sql.Timestamp.valueOf(patient.getUpdatedAt()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add patient: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("medical_record"),
                        rs.getString("name"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("identity_number"),
                        rs.getDate("registered_at").toLocalDate(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve patients: " + e.getMessage());
            e.printStackTrace();
        }
        return patients;
    }

    public void updatePatient(Patient patient) {
        String sql = "UPDATE patients SET medical_record = ?, name = ?, date_of_birth = ?, gender = ?, address = ?, phone = ?, identity_number = ?, registered_at = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getMedicalRecord());
            pstmt.setString(2, patient.getName());
            pstmt.setDate(3, java.sql.Date.valueOf(patient.getDateOfBirth()));
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getAddress());
            pstmt.setString(6, patient.getPhone());
            pstmt.setString(7, patient.getIdentityNumber());
            pstmt.setDate(8, java.sql.Date.valueOf(patient.getRegisteredAt()));
            pstmt.setTimestamp(9, java.sql.Timestamp.valueOf(patient.getUpdatedAt()));
            pstmt.setInt(10, patient.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update patient: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete patient: " + e.getMessage());
            e.printStackTrace();
        }
    }
}