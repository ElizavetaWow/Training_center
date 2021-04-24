package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Company;
import sample.models.Faculty;
import sample.utils.ApiSession;

import java.util.ArrayList;
import java.util.List;

public class FacultyEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker birthdayPicker;
    @FXML
    private TextField passwordField;


    private Faculty faculty;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;

    private ApiSession apiSession;

    public FacultyEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
    }

    public void setFaculty(Faculty faculty){
        if (faculty.getFirstName() != null){
            this.update = true;
        }
        this.faculty = faculty;
        firstNameField.setText(faculty.getFirstName());
        lastNameField.setText(faculty.getLastName());
        emailField.setText(faculty.getEmail());
        passwordField.setText(faculty.getPassword());
        birthdayPicker.setValue(faculty.getBirthday());
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            faculty.setFirstName(firstNameField.getText());
            faculty.setLastName(lastNameField.getText());
            faculty.setEmail(emailField.getText());
            faculty.setBirthday(birthdayPicker.getValue());
            faculty.setPassword(passwordField.getText());
            if (update) {
                apiSession.updateFaculty(faculty);
            }
            else {
                apiSession.createFaculty(faculty);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No first name input!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No last name input!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No password input!\n";
        }
        if (birthdayPicker.getValue() == null) {
            errorMessage += "No birthday selection!\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No password input!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong field input");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
