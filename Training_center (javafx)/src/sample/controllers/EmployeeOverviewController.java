package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.models.Employee;
import sample.utils.DateUtil;

public class EmployeeOverviewController {
    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private TableColumn<Employee, String> firstNameColumn;
    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label companyLabel;

    private Main main;

    public EmployeeOverviewController(){
    }

    @FXML
    private void initialize(){
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());


        showEmployeeDetails(null);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showEmployeeDetails(newValue)
        );
    }

    public void setMainApp(Main main){
        this.main = main;
    }

    private void showEmployeeDetails(Employee employee){
        if (employee != null){
            firstNameLabel.setText(employee.getFirstName());
            lastNameLabel.setText(employee.getLastName());
            birthdayLabel.setText(DateUtil.format(employee.getBirthday()));
            emailLabel.setText(employee.getEmail());
            companyLabel.setText(employee.getCompanyName());
        }
        else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            birthdayLabel.setText("");
            emailLabel.setText("");
            companyLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteEmployee(){
        int selectionIndex = employeeTableView.getSelectionModel().getSelectedIndex();
        if (selectionIndex >= 0)
            employeeTableView.getItems().remove(selectionIndex);
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Employee Selected");
            alert.setContentText("Please select a employee in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditEmployee(){
    }

    @FXML
    private void handleNewEmployee(){
    }
}

