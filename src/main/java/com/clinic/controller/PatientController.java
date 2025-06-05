package com.clinic.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.clinic.dao.PatientDAO;
import com.clinic.model.Patient;
import com.clinic.view.PatientView;

import javafx.collections.FXCollections;

public class PatientController {
    private final PatientDAO patientDAO;
    private final PatientView view;
    private Patient selectedPatient;

    public PatientController(PatientView view) {
        this.view = view;
        this.patientDAO = new PatientDAO();
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public void addPatient() {
        Patient patient = createPatientFromFields();
        if (patient != null) {
            patientDAO.addPatient(patient);
            refreshTable();
            clearFields();
        }
    }

    public void updatePatient() {
        if (selectedPatient != null) {
            Patient updatedPatient = createPatientFromFields();
            if (updatedPatient != null) {
                updatedPatient.setId(selectedPatient.getId());
                updatedPatient.setCreatedAt(selectedPatient.getCreatedAt());
                patientDAO.updatePatient(updatedPatient);
                refreshTable();
                clearFields();
            }
        }
    }

    public void deletePatient() {
        if (selectedPatient != null) {
            patientDAO.deletePatient(selectedPatient.getId());
            refreshTable();
            clearFields();
        }
    }

    public void clearFields() {
        view.getMedicalRecordField().clear();
        view.getNameField().clear();
        view.getDateOfBirthPicker().setValue(null);
        view.getGenderCombo().setValue(null);
        view.getAddressField().clear();
        view.getPhoneField().clear();
        view.getIdentityNumberField().clear();
        view.getRegisteredAtPicker().setValue(null);
        selectedPatient = null;
    }

    public void populateFields(Patient patient) {
        selectedPatient = patient;
        view.getMedicalRecordField().setText(patient.getMedicalRecord());
        view.getNameField().setText(patient.getName());
        view.getDateOfBirthPicker().setValue(patient.getDateOfBirth());
        view.getGenderCombo().setValue(patient.getGender());
        view.getAddressField().setText(patient.getAddress());
        view.getPhoneField().setText(patient.getPhone());
        view.getIdentityNumberField().setText(patient.getIdentityNumber());
        view.getRegisteredAtPicker().setValue(patient.getRegisteredAt());
    }

    private Patient createPatientFromFields() {
        try {
            String medicalRecord = view.getMedicalRecordField().getText();
            String name = view.getNameField().getText();
            LocalDate dateOfBirth = view.getDateOfBirthPicker().getValue();
            String gender = view.getGenderCombo().getValue();
            String address = view.getAddressField().getText();
            String phone = view.getPhoneField().getText();
            String identityNumber = view.getIdentityNumberField().getText();
            LocalDate registeredAt = view.getRegisteredAtPicker().getValue();

            if (medicalRecord.isEmpty() || name.isEmpty() || dateOfBirth == null || gender == null || registeredAt == null) {
                return null; // Validation failed
            }

            LocalDateTime now = LocalDateTime.now();
            return new Patient(0, medicalRecord, name, dateOfBirth, gender, address, phone, identityNumber, registeredAt, now, now);
        } catch (Exception e) {
            return null;
        }
    }

    private void refreshTable() {
        view.getPatientTable().setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));
    }
}