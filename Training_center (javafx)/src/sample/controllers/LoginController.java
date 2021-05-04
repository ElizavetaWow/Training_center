package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Admin;
import sample.models.Employee;
import sample.models.Faculty;
import sample.models.Person;
import sample.utils.ApiSession;


public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;


    private ApiSession apiSession;
    private Stage stage;
    private Person user;

    public LoginController(){
    }
    @FXML
    private void initialize(){
        emailField.setText("");
        passwordField.setText("");
    }

    @FXML
    private void handleSignIn(){
        if (isInputValid()){
            Faculty faculty = apiSession.getFacultiesByEmail(emailField.getText(), passwordField.getText());
            Employee employee = apiSession.getEmployeesByEmail(emailField.getText(), passwordField.getText());
            Admin admin = apiSession.getAdminsByEmail(emailField.getText());
            if (employee != null && employee.getPassword().equals(passwordField.getText())){
                setUser(employee);
            }
            if (faculty != null){
                setUser(faculty);
            }
            if (admin != null && admin.getPassword().equals(passwordField.getText())){
                setUser(admin);
            }
            if (getUser() != null){
                stage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect username or password");
                alert.showAndWait();
            }
        }

    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No email input!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No password input!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong field input");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public void setMain() {
    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }
}
