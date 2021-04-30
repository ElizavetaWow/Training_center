package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Company;
import sample.models.Employee;
import sample.utils.ApiSession;
import sample.utils.DateUtil;

public class EmployeeEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker birthdayPicker;
    @FXML
    private Label companyLabel;
    @FXML
    private TextField passwordField;


    private Employee employee;
    private Company selectedCompany;
    private Main main;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;

    private ApiSession apiSession;

    public EmployeeEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession, Main main) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
        this.main = main;
    }

    public void setEmployee(Employee employee){
        if (employee.getFirstName() != null){
            this.update = true;
            selectedCompany = employee.getCompany();
            companyLabel.setText(employee.getCompany().getName());
        }
        this.employee = employee;
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        passwordField.setText(employee.getPassword());
        birthdayPicker.setValue(employee.getBirthday());

    }

    @FXML
    private void handleSelectCompany(){
        Company company = main.showChooseCompanyDialog(dialogStage);
        if (company != null){
            selectedCompany = company;
            companyLabel.setText(selectedCompany.getName());
        }
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
        try {
            birthdayPicker.setValue(DateUtil.parse(birthdayPicker.getEditor().getText()));
        } catch (Exception ignored){}
        if(isInputValid()){
            employee.setFirstName(firstNameField.getText());
            employee.setLastName(lastNameField.getText());
            employee.setEmail(emailField.getText());
            employee.setBirthday(birthdayPicker.getValue());
            employee.setPassword(passwordField.getText());
            employee.setCompany(selectedCompany);
            if (update) {
                apiSession.updateEmployee(employee);
            }
            else {
                apiSession.createEmployee(employee);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No first name input!\n";
        } else if (!firstNameField.getText().matches("[A-z\\s]+[A-z]")){
            errorMessage += "First name must be in latin!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No last name input!\n";
        }else if (!lastNameField.getText().matches("[A-z\\s]+[A-z]")){
            errorMessage += "Last name must be in latin!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No password input!\n";
        }
        if (birthdayPicker.getValue() == null) {
            errorMessage += "No birthday selection!\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No email input!\n";
        } else if (!emailField.getText().matches("\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,4}")){
            errorMessage += "Wrong format of email input!\n";
        }
        if (companyLabel.getText() == null || companyLabel.getText().length() == 0) {
            errorMessage += "No company selection!\n";
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
