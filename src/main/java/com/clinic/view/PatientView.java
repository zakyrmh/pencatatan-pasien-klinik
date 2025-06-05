package com.clinic.view;

import com.clinic.controller.PatientController;
import com.clinic.model.Patient;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PatientView implements Initializable {
    @FXML private TextField medicalRecordField;
    @FXML private TextField nameField;
    @FXML private DatePicker dateOfBirthPicker;
    @FXML private ComboBox<String> genderCombo;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField identityNumberField;
    @FXML private DatePicker registeredAtPicker;
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> medicalRecordColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> dateOfBirthColumn;
    @FXML private TableColumn<Patient, String> genderColumn;
    @FXML private TableColumn<Patient, String> addressColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;
    @FXML private TableColumn<Patient, String> identityNumberColumn;
    @FXML private TableColumn<Patient, String> registeredAtColumn;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private PatientController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new PatientController(this);

        // Initialize Table Columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        medicalRecordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedicalRecord()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        identityNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentityNumber()));
        registeredAtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegisteredAt().toString()));

        // Load initial data
        patientTable.setItems(FXCollections.observableArrayList(controller.getAllPatients()));

        // Table row selection listener
        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                controller.populateFields(newSelection);
            }
        });
    }

    @FXML
    private void addPatient() {
        controller.addPatient();
    }

    @FXML
    private void updatePatient() {
        controller.updatePatient();
    }

    @FXML
    private void deletePatient() {
        controller.deletePatient();
    }

    @FXML
    private void clearFields() {
        controller.clearFields();
    }

    // Methods to access UI components
    public TextField getMedicalRecordField() { return medicalRecordField; }
    public TextField getNameField() { return nameField; }
    public DatePicker getDateOfBirthPicker() { return dateOfBirthPicker; }
    public ComboBox<String> getGenderCombo() { return genderCombo; }
    public TextField getAddressField() { return addressField; }
    public TextField getPhoneField() { return phoneField; }
    public TextField getIdentityNumberField() { return identityNumberField; }
    public DatePicker getRegisteredAtPicker() { return registeredAtPicker; }
    public TableView<Patient> getPatientTable() { return patientTable; }
}