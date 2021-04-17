package sample.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Company;
import sample.utils.ApiSession;

public class CompanyEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField accountField;

    private Company company;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;

    private ApiSession apiSession;

    public CompanyEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
    }

    public void setCompany(Company company){
        if (company.getName() != null){
            this.update = true;
        }
        this.company = company;
        nameField.setText(company.getName());
        accountField.setText(company.getAccount());
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
            company.setName(nameField.getText());
            company.setAccount(accountField.getText());
            if (update) {
                apiSession.updateCompany(company);
            }
            else {
                apiSession.createCompany(company);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No name input!\n";
        }
        if (accountField.getText() == null || accountField.getText().length() == 0) {
            errorMessage += "No account input!\n";
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