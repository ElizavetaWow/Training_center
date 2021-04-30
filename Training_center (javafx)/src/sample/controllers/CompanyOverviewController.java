package sample.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import sample.Main;
import sample.models.Company;
import sample.utils.ApiSession;


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
    private Button showEmployeesButton;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private BarChart chart;

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

    private void showChart(Company company){
        if (company != null){
        }
        else {
        }
    }

    private void showCompanyDetails(Company company){
        if (company != null){
            setItem(company);
            nameLabel.setText(company.getName());
            accountLabel.setText(company.getAccount());
            employeesNumberLabel.setText(String.valueOf(apiSession.countEmployeesByCompanyId(company.getId())));
        }
        else {
            nameLabel.setText("");
            accountLabel.setText("");
            employeesNumberLabel.setText("");
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

