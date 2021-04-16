package sample.controllers;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Company;


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

    public CompanyOverviewController(){
    }

    @FXML
    private void initialize(){
        companyColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        companyTableView.setItems(Main.GenerateCompanies());
        showCompanyDetails(null);
        companyTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showCompanyDetails(newValue)
        );
    }

    public void setMainApp(Main main){
        this.main = main;
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
        if (selectionIndex >= 0)
            companyTableView.getItems().remove(selectionIndex);
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
    private void handleEditCompany(){
        Company selectedCompany = companyTableView.getSelectionModel().getSelectedItem();
        if (selectedCompany != null) {
            boolean okClicked = main.showCompanyEditDialog(selectedCompany);
            if (okClicked) {
                showCompanyDetails(selectedCompany);
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
    private void handleAddCompany(){
        Company tempCompany = new Company();
        boolean okClicked = main.showCompanyEditDialog(tempCompany);

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

}

