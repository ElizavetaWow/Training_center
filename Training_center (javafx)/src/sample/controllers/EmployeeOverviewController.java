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

public class EmployeeOverviewController extends OverviewController {
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
    private ComboBox<String> companyBox;

    private Main main;
    private ApiSession apiSession;
    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    private List<Company> companyList = new ArrayList<>();

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

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setMainApp(Main main){
        this.main = main;
        setCompanyBoxItems();
        updateEmployeesTable();
    }

    public void setCompanyBoxItems(){
        companyList = apiSession.getCompanies();
        ObservableList<String> companyNameList = FXCollections.observableArrayList();
        companyNameList.clear();
        companyNameList.add("All Companies");
        for (Company company: companyList){
            companyNameList.add(company.getName());
        }
        companyBox.setItems(companyNameList);
        companyBox.getSelectionModel().select(0);
    }

    public void setCompany(Company company){
        if (company != null && company.getName() != null){
            companyBox.getSelectionModel().select(company.getName());
            updateEmployeesTable();
        }
    }

    @FXML
    private void updateEmployeesTable(){
        int selectionIndex = companyBox.getSelectionModel().getSelectedIndex();
        if (selectionIndex > 0) {
            updateEmployeeList(companyList.get(selectionIndex - 1).getName());
        }
        else if (selectionIndex == 0) {
            updateEmployeeList();
        }
        employeeTableView.setItems(employeeList);
    }


    public void updateEmployeeList(){
        employeeList.clear();
        employeeList.addAll(apiSession.getEmployees());
    }

    public void updateEmployeeList(String companyName){
        employeeList.clear();
        employeeList.addAll(apiSession.getEmployeesByCompanyName(companyName));
    }

    private void showEmployeeDetails(Employee employee){
        if (employee != null){
            firstNameLabel.setText(employee.getFirstName());
            lastNameLabel.setText(employee.getLastName());
            birthdayLabel.setText(DateUtil.format(employee.getBirthday()));
            emailLabel.setText(employee.getEmail());
            companyLabel.setText(employee.getCompany().getName());
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
        Employee currentEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        int i = employeeTableView.getSelectionModel().getSelectedIndex();
        if (currentEmployee != null) {
            if (apiSession.deleteEmployee(currentEmployee)) {
                updateEmployeesTable();
                employeeTableView.getSelectionModel().select(i - 1);
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
        Employee currentEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (currentEmployee != null) {
            boolean okClicked = main.showEmployeeEditDialog(currentEmployee);
            updateEmployeesTable();
            if (okClicked) {
                showEmployeeDetails(currentEmployee);
                employeeTableView.getSelectionModel().selectLast();
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
        Employee tempEmployee = new Employee();
        boolean okClicked = main.showEmployeeEditDialog(tempEmployee);
        updateEmployeesTable();
        if (okClicked) {
            showEmployeeDetails(tempEmployee);
            employeeTableView.getSelectionModel().selectLast();
        }
    }

}

