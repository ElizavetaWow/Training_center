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
    private ComboBox<String> companyBox;

    private Main main;
    private ApiSession apiSession;
    private final ObservableList<Employee> employees = FXCollections.observableArrayList();
    private final ObservableList<String> companyNameList = FXCollections.observableArrayList();
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
        updateEmployees();
        employeeTableView.setItems(employees);
    }

    public void setCompanyBoxItems(){
        companyList = apiSession.getCompanies();
        companyNameList.clear();
        companyNameList.add("All Companies");
        for (Company company: companyList){
            companyNameList.add(company.getName());
        }
        companyBox.setItems(companyNameList);
    }

    public void setCompany(Company company){
        if (company.getName() != null){
            companyBox.getSelectionModel().select(company.getName());
            chooseCompany();
        }
    }

    @FXML
    private void chooseCompany(){
        int selectionIndex = companyBox.getSelectionModel().getSelectedIndex();
        if (selectionIndex > 0) {
            updateEmployees("company", String.valueOf(companyList.get(selectionIndex - 1).getId()));
        }
        else if (selectionIndex == 0) {
            updateEmployees();
        }
        employeeTableView.setItems(employees);
    }


    public void updateEmployees(){
        employees.clear();
        employees.addAll(apiSession.getEmployees());
    }

    public void updateEmployees(String field, String value){
        employees.clear();
        employees.addAll(apiSession.getEmployees(field, value));
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
        Employee currentEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (currentEmployee != null) {
            boolean okClicked = main.showEmployeeEditDialog(currentEmployee);
            chooseCompany();
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
        Employee tempEmployee = new Employee();
        boolean okClicked = main.showEmployeeEditDialog(tempEmployee);
        chooseCompany();
        if (okClicked) {
            showEmployeeDetails(tempEmployee);
        }
    }

}

