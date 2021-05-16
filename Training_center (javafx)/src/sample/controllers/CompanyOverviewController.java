package sample.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import sample.Main;
import sample.models.Company;
import sample.models.Course;
import sample.models.Employee;
import sample.utils.ApiSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CompanyOverviewController extends OverviewController{
    @FXML
    private TableView<Company> companyTableView;
    @FXML
    private TableColumn<Company, String> companyColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label accountLabel;
    @FXML
    private Label employeesNumberLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Button showEmployeesButton;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private PieChart chart;

    private Main main;
    private ApiSession apiSession;
    private ObservableList<Company> companyList = FXCollections.observableArrayList();


    public CompanyOverviewController(){
    }

    @FXML
    private void initialize(){
        companyColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        showCompanyDetails(null);
        companyTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showCompanyDetails(newValue)
        );
    }

    public void setMainApp(Main main){
        this.main = main;
        updateCompanyList();
    }

    public void setShowEmployeesButtonVisible(boolean status){
        showEmployeesButton.setVisible(status);
    }

    private void showChart(){
        Company currentCompany = companyTableView.getSelectionModel().getSelectedItem();
        if (currentCompany != null) {
            List<Employee> employees = apiSession.getEmployeesByCompanyName(currentCompany.getName());
            HashMap<Long, Long> dataMap = new HashMap<>();
            for (Employee employee: employees) {
                for (Course course : employee.getCourses()) {
                    if (!dataMap.containsKey(course.getId())) {
                        dataMap.put(course.getId(), (long) 0);
                    }
                    dataMap.put(course.getId(), dataMap.get(course.getId()) + 1);
                }
            }
            List<PieChart.Data> pieList = new ArrayList<>();
            for (HashMap.Entry<Long, Long> entry: dataMap.entrySet()){
                pieList.add(new PieChart.Data(apiSession.getCoursesById(entry.getKey()).getNamesAndFaculty(),
                        entry.getValue()));
            }
            chart.getData().setAll(pieList);
        }
    }

    private void showCompanyDetails(Company company){
        if (company != null){
            setItem(company);
            nameLabel.setText(company.getName());
            accountLabel.setText(company.getAccount());
            moneyLabel.setText(String.valueOf(company.getMoney()));
            employeesNumberLabel.setText(String.valueOf(apiSession.countEmployeesByCompanyId(company.getId())));
            showChart();
        }
        else {
            nameLabel.setText("");
            accountLabel.setText("");
            employeesNumberLabel.setText("");
            moneyLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteCompany(){
        Company currentCompany = companyTableView.getSelectionModel().getSelectedItem();
        if (currentCompany != null) {
            if (apiSession.deleteCompany(currentCompany)) {
                updateCompanyList();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Company Selected");
            alert.setContentText("Please select a company in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditCompany(){
        Company currentCompany = companyTableView.getSelectionModel().getSelectedItem();
        if (currentCompany != null) {
            boolean okClicked = main.showCompanyEditDialog(currentCompany);
            updateCompanyList();
            if (okClicked) {
                showCompanyDetails(currentCompany);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Company Selected");
            alert.setContentText("Please select a company in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewCompany(){
        Company tempCompany = new Company();
        boolean okClicked = main.showCompanyEditDialog(tempCompany);
        updateCompanyList();
        if (okClicked) {
            showCompanyDetails(tempCompany);
        }
    }

    @FXML
    private void handleShowEmployees(){
        Company selectionItem = companyTableView.getSelectionModel().getSelectedItem();
        if (selectionItem != null)
            main.showEmployeeOverview(selectionItem);
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Company Selected");
            alert.setContentText("Please select a company in the table");
            alert.showAndWait();
        }

    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void updateCompanyList(){
        companyList.clear();
        companyList.addAll(apiSession.getCompanies());
        companyTableView.setItems(companyList);
    }

    public void setVisibleHBox(int i){
        buttonsHBox.setVisible(i >= 2);
    }

}

