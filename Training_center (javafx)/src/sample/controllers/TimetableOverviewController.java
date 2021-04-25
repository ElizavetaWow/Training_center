package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Timetable;
import sample.utils.ApiSession;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimetableOverviewController  extends OverviewController {
    @FXML
    private TableView<Timetable> timetableTableView;
    @FXML
    private TableColumn<Timetable, LocalDate> dateColumn;
    @FXML
    private TableColumn<Timetable, LocalTime> timeColumn;
    @FXML
    private TableColumn<Timetable, String> courseColumn;
    @FXML
    private TableColumn<Timetable, String> facultyColumn;
    @FXML
    private TableColumn<Timetable, String> placeColumn;



    @FXML
    private ComboBox<String> courseBox;
    @FXML
    private DatePicker datePicker;

    private Main main;
    private ApiSession apiSession;
    private final ObservableList<Timetable> timetableList = FXCollections.observableArrayList();
    private ObservableList<String> courseList = FXCollections.observableArrayList();
    public TimetableOverviewController(){
    }

    @FXML
    private void initialize(){
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
        courseColumn.setCellValueFactory(cellData -> cellData.getValue().getCourse().getCourseInfo().getNameProperty());
        facultyColumn.setCellValueFactory(cellData -> cellData.getValue().getCourse().getFaculty().getNamesAndEmailProperty());
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().getPlace().getFullPlaceProperty());
    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setMainApp(Main main){
        this.main = main;
        setCourseBoxItems();
        updateTimetablesTable();
    }

    public void setCourseBoxItems(){
        updateTimetableList();
        courseList.clear();
        courseList.add("All Courses");
        for (Timetable timetable: timetableList){
            if (!courseList.contains(timetable.getCourse().getCourseInfo().getName())){
                courseList.add(timetable.getCourse().getCourseInfo().getName());
            }
        }
        courseBox.setItems(courseList);
        courseBox.getSelectionModel().select(0);
    }


    @FXML
    private void updateTimetablesTable(){
        LocalDate selectedDate = datePicker.getValue();
        int selectionIndex = courseBox.getSelectionModel().getSelectedIndex();
        if (selectionIndex > 0 && selectedDate == null) {
            updateTimetableList(courseBox.getSelectionModel().getSelectedItem());
        }
        else if (selectionIndex > 0 && selectedDate != null) {
            updateTimetableList(courseBox.getSelectionModel().getSelectedItem(), selectedDate);
        }
        else if (selectionIndex == 0 && selectedDate != null) {
            updateTimetableList(selectedDate);
        }
        else if (selectionIndex == 0 && selectedDate == null) {
            updateTimetableList();
        }
        timetableTableView.setItems(timetableList);
    }

    @FXML
    private void handleCleanDate(){
        datePicker.setValue(null);
    }


    public void updateTimetableList(){
        timetableList.clear();
        timetableList.addAll(apiSession.getTimetables());
    }

    public void updateTimetableList(String name){
        timetableList.clear();
        timetableList.addAll(apiSession.getTimetablesByCourseName(name));
    }

    public void updateTimetableList(LocalDate date){
        timetableList.clear();
        timetableList.addAll(apiSession.getTimetablesByDate(date));
    }

    public void updateTimetableList(String name, LocalDate date){
        timetableList.clear();
        timetableList.addAll(apiSession.getTimetablesByCourseNameAndDate(name, date));
    }


    @FXML
    private void handleDeleteTimetable(){
        Timetable currentTimetable = timetableTableView.getSelectionModel().getSelectedItem();
        if (currentTimetable != null) {
            if (apiSession.deleteTimetable(currentTimetable)) {
                setCourseBoxItems();
                updateTimetablesTable();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Timetable Selected");
            alert.setContentText("Please select a timetable in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditTimetable(){
        Timetable currentTimetable = timetableTableView.getSelectionModel().getSelectedItem();
        if (currentTimetable != null) {
            boolean okClicked = main.showTimetableEditDialog(currentTimetable);
            setCourseBoxItems();
            updateTimetablesTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Timetable Selected");
            alert.setContentText("Please select a timetable in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewTimetable(){
        Timetable tempTimetable = new Timetable();
        boolean okClicked = main.showTimetableEditDialog(tempTimetable);
        setCourseBoxItems();
        updateTimetablesTable();
    }

}
