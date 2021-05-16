package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import sample.Main;
import sample.models.Faculty;
import sample.utils.ApiSession;
import sample.utils.DateUtil;

public class FacultyOverviewController extends OverviewController {
    @FXML
    private TableView<Faculty> facultyTableView;
    @FXML
    private TableColumn<Faculty, String> firstNameColumn;
    @FXML
    private TableColumn<Faculty, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private HBox buttonsHBox;

    private Main main;
    private ApiSession apiSession;

    public FacultyOverviewController() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        showFacultyDetails(null);
        facultyTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFacultyDetails(newValue)
        );
    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setMainApp(Main main) {
        this.main = main;
        updateFacultiesTable();
    }

    @FXML
    private void updateFacultiesTable() {
        ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
        facultyList.clear();
        facultyList.addAll(apiSession.getFaculties());
        facultyTableView.setItems(facultyList);
    }


    private void showFacultyDetails(Faculty faculty) {
        if (faculty != null) {
            setItem(faculty);
            firstNameLabel.setText(faculty.getFirstName());
            lastNameLabel.setText(faculty.getLastName());
            birthdayLabel.setText(DateUtil.format(faculty.getBirthday(), true));
            emailLabel.setText(faculty.getEmail());
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            birthdayLabel.setText("");
            emailLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteFaculty() {
        Faculty currentFaculty = facultyTableView.getSelectionModel().getSelectedItem();
        if (currentFaculty != null) {
            if (apiSession.deleteFaculty(currentFaculty)) {
                updateFacultiesTable();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Faculty Selected");
            alert.setContentText("Please select a faculty in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditFaculty() {
        Faculty currentFaculty = facultyTableView.getSelectionModel().getSelectedItem();
        if (currentFaculty != null) {
            boolean okClicked = main.showFacultyEditDialog(currentFaculty);
            updateFacultiesTable();
            if (okClicked) {
                showFacultyDetails(currentFaculty);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Faculty Selected");
            alert.setContentText("Please select a faculty in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewFaculty() {
        Faculty tempFaculty = new Faculty();
        boolean okClicked = main.showFacultyEditDialog(tempFaculty);
        updateFacultiesTable();
        if (okClicked) {
            showFacultyDetails(tempFaculty);
        }
    }

    public void setVisibleHBox(int i) {
        buttonsHBox.setVisible(i >= 2);
    }
}

