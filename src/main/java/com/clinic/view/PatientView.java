package com.clinic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.clinic.controller.PatientController;
import com.clinic.model.Patient;
import com.clinic.utils.Session;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientView implements Initializable {
    @FXML
    private TextField medicalRecordField;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private ComboBox<String> genderCombo;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField identityNumberField;
    @FXML
    private DatePicker registeredAtPicker;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> idColumn;
    @FXML
    private TableColumn<Patient, String> medicalRecordColumn;
    @FXML
    private TableColumn<Patient, String> nameColumn;
    @FXML
    private TableColumn<Patient, String> dateOfBirthColumn;
    @FXML
    private TableColumn<Patient, String> genderColumn;
    @FXML
    private TableColumn<Patient, String> addressColumn;
    @FXML
    private TableColumn<Patient, String> phoneColumn;
    @FXML
    private TableColumn<Patient, String> identityNumberColumn;
    @FXML
    private TableColumn<Patient, String> registeredAtColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;

    private PatientController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new PatientController(this);

        // Initialize Table Columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        medicalRecordColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedicalRecord()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dateOfBirthColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        identityNumberColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentityNumber()));
        registeredAtColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getRegisteredAt().toString()));

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
    public TextField getMedicalRecordField() {
        return medicalRecordField;
    }

    public TextField getNameField() {
        return nameField;
    }

    public DatePicker getDateOfBirthPicker() {
        return dateOfBirthPicker;
    }

    public ComboBox<String> getGenderCombo() {
        return genderCombo;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getIdentityNumberField() {
        return identityNumberField;
    }

    public DatePicker getRegisteredAtPicker() {
        return registeredAtPicker;
    }

    public TableView<Patient> getPatientTable() {
        return patientTable;
    }

    private void redirectToLogin(Stage stage) {
        try {
            URL fxmlLocation = getClass().getResource("/fxml/LoginView.fxml");
            if (fxmlLocation == null) {
                System.err.println("Kritis: File FXML login tidak ditemukan!");
                return;
            }

            Scene scene = new Scene(FXMLLoader.load(fxmlLocation));

            URL cssLocation = getClass().getResource("/css/style.css");
            if (cssLocation != null) {
                scene.getStylesheets().add(cssLocation.toExternalForm());
            }

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman login.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Stage currentStage = (Stage) patientTable.getScene().getWindow();
        Session.getInstance().clear();
        redirectToLogin(currentStage);
    }
}