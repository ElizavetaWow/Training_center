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
import sample.models.Employee;
import sample.utils.ApiSession;

import java.util.ArrayList;
import java.util.List;

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
    private ComboBox<String> companyBox;
    @FXML
    private TextField passwordField;


    private Employee employee;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;
    private ObservableList<String> companyList = FXCollections.observableArrayList();
    private List<Company> companies = new ArrayList<>();

    private ApiSession apiSession;

    public EmployeeEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
        setCompanyBoxItems();
    }

    public void setEmployee(Employee employee){
        if (employee.getFirstName() != null){
            this.update = true;
            companyBox.getSelectionModel().select(employee.getCompany().getName());
        }
        this.employee = employee;
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        passwordField.setText(employee.getPassword());
        birthdayPicker.setValue(employee.getBirthday());

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
            employee.setFirstName(firstNameField.getText());
            employee.setLastName(lastNameField.getText());
            employee.setEmail(emailField.getText());
            employee.setBirthday(birthdayPicker.getValue());
            employee.setPassword(passwordField.getText());
            employee.setCompany(apiSession.getCompaniesByName(
                    companyBox.getSelectionModel().getSelectedItem()).get(0));
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
        if (companyBox.getSelectionModel().getSelectedIndex() < 0) {
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

    public void setCompanyBoxItems(){
        companies = apiSession.getCompanies();
        companyList.clear();
        for (Company company: companies){
            companyList.add(company.getName());
        }
        companyBox.setItems(companyList);
    }
}
