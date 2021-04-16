package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Company;
import sample.models.Employee;
import sample.utils.DateUtil;

import java.util.List;

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

    @FXML
    private ComboBox companyBox;

    private Main main;

    public EmployeeOverviewController(){
    }

    @FXML
    private void initialize(){
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        ObservableList<String> locData = FXCollections.observableArrayList();
        locData.add("All Companies");
        for (Company comp: Main.GenerateCompanies()){
            locData.add(comp.getName());
        }
        companyBox.setItems(locData);
        employeeTableView.setItems(Main.GeneratePersons());
        showEmployeeDetails(null);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showEmployeeDetails(newValue)
        );
    }

    public void setMainApp(Main main){
        this.main = main;
    }

    public void setCompany(Company company){
        if (company != null){
            companyBox.getSelectionModel().select(company.getName());
        }

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
    @FXML
    private void chooseCompany(){
        int selectionIndex = companyBox.getSelectionModel().getSelectedIndex();


    }
}

