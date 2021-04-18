package sample.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Company;
import sample.utils.ApiSession;


public class CompanyOverviewController {
    @FXML
    private TableView<Company> companyTableView;
    @FXML
    private TableColumn<Company, String> companyColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label accountLabel;

    @FXML
    private BarChart chart;

    private Main main;
    private ApiSession apiSession;
    private ObservableList<Company> companies = FXCollections.observableArrayList();


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
        updateCompanies();
        companyTableView.setItems(companies);
    }

    private void showChart(Company company){
        if (company != null){
        }
        else {
        }
    }

    private void showCompanyDetails(Company company){
        if (company != null){
            nameLabel.setText(company.getName());
            accountLabel.setText(company.getAccount());
        }
        else {
            nameLabel.setText("");
            accountLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteCompany(){
        int selectionIndex = companyTableView.getSelectionModel().getSelectedIndex();
        if (selectionIndex >= 0) {
            Company currentCompany = companyTableView.getItems().get(selectionIndex);
            if (apiSession.deleteCompany(currentCompany)) {
                companyTableView.getItems().remove(selectionIndex);
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
        int selectionIndex = companyTableView.getSelectionModel().getSelectedIndex();
        if (selectionIndex >= 0) {
            Company currentCompany = companyTableView.getItems().get(selectionIndex);
            boolean okClicked = main.showCompanyEditDialog(currentCompany);
            updateCompanies();
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
        updateCompanies();
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

    public void updateCompanies(){
        companies.clear();
        companies.addAll(apiSession.getCompanies());
    }

}

