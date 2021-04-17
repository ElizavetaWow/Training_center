package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Company;
import sample.models.Employee;
import sample.utils.ApiSession;
import sample.utils.DateUtil;

import java.util.ArrayList;
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
    private ApiSession apiSession;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<String> companyList = FXCollections.observableArrayList();
    private List<Company> companies = new ArrayList<>();

    public EmployeeOverviewController(){
    }

    @FXML
    private void initialize(){
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        setCompanyBoxItems();
        showEmployeeDetails(null);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showEmployeeDetails(newValue)
        );
    }

    public void setCompanyBoxItems(){
        companyList.clear();
        List<Company> companies = apiSession.getCompanies();
        companyList.add("All Companies");
        for (Company company: companies){
            companyList.add(company.getName());
        }
        companyBox.setItems(companyList);
    }

    public void setMainApp(Main main){
        this.main = main;
        updateEmployees();
        employeeTableView.setItems(employees);
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
            companyLabel.setText(apiSession.getCompanies(employee.getCompanyId()).getName());
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
        if (selectionIndex >= 0) {
            employeeTableView.getItems().remove(selectionIndex);
            Employee currentEmployee = employeeTableView.getItems().get(selectionIndex);
            if (apiSession.deleteEmployee(currentEmployee)) {
                employeeTableView.getItems().remove(selectionIndex);
            }
        }
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
        int selectionIndex = employeeTableView.getSelectionModel().getSelectedIndex();
        if (selectionIndex >= 0) {
            Employee currentEmployee = employeeTableView.getItems().get(selectionIndex);
            boolean okClicked = main.showEmployeeEditDialog(currentEmployee);
            updateEmployees();
            if (okClicked) {
                showEmployeeDetails(currentEmployee);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Employee Selected");
            alert.setContentText("Please select a employee in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewEmployee(){
        Employee tempEmplolyee = new Employee();
        boolean okClicked = main.showEmployeeEditDialog(tempEmplolyee);
        updateEmployees();
        if (okClicked) {
            showEmployeeDetails(tempEmplolyee);
        }
    }
    @FXML
    private void chooseCompany(){
        int selectionIndex = companyBox.getSelectionModel().getSelectedIndex();


    }

    public void setRestApi(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void updateEmployees(){
        employees.clear();
        employees.addAll(apiSession.getEmployees());
    }
}

