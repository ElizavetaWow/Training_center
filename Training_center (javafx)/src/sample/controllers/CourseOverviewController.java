package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Course;
import sample.utils.ApiSession;

import java.time.LocalDate;

public class CourseOverviewController {
    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, String> courseInfoColumn;
    @FXML
    private TableColumn<Course, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Course, String> finishDateColumn;
    @FXML
    private TableColumn<Course, String> facultyColumn;


    @FXML
    private ComboBox<String> nameBox;

    private Main main;
    private ApiSession apiSession;
    private final ObservableList<Course> courseList = FXCollections.observableArrayList();
    private ObservableList<String> nameList = FXCollections.observableArrayList();
    public CourseOverviewController(){
    }

    @FXML
    private void initialize(){
        startDateColumn.setCellValueFactory(cellData -> cellData.getValue().getStartDateProperty());
        finishDateColumn.setCellValueFactory(cellData -> cellData.getValue().getFinishDateProperty().asString());
        courseInfoColumn.setCellValueFactory(cellData -> cellData.getValue().getCourseInfo().getNameProperty());
        facultyColumn.setCellValueFactory(cellData -> cellData.getValue().getFaculty().getNamesAndEmailProperty());
    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setMainApp(Main main){
        this.main = main;
        setNameBoxItems();
        updateCoursesTable();
    }

    public void setNameBoxItems(){
        updateCourseList();
        nameList.clear();
        nameList.add("All Courses");
        for (Course course: courseList){
            if (!nameList.contains(course.getCourseInfo().getName())){
                nameList.add(course.getCourseInfo().getName());
            }
        }
        nameBox.setItems(nameList);
        nameBox.getSelectionModel().select(0);
    }

    @FXML
    private void updateCoursesTable(){
        int selectionIndex = nameBox.getSelectionModel().getSelectedIndex();
        if (selectionIndex > 0) {
            updateCourseList(nameBox.getSelectionModel().getSelectedItem());
        }
        else if (selectionIndex == 0) {
            updateCourseList();
        }
        courseTableView.setItems(courseList);
    }


     public void updateCourseList(){
        courseList.clear();
        courseList.addAll(apiSession.getCourses());
    }

    public void updateCourseList(String name){
        courseList.clear();
        courseList.addAll(apiSession.getCoursesByName(name));
    }


    @FXML
    private void handleDeleteCourse(){
        Course currentCourse = courseTableView.getSelectionModel().getSelectedItem();
        if (currentCourse != null) {
            if (apiSession.deleteCourse(currentCourse)) {
                setNameBoxItems();
                updateCoursesTable();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Course Selected");
            alert.setContentText("Please select a course in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditCourse(){
        Course currentCourse = courseTableView.getSelectionModel().getSelectedItem();
        if (currentCourse != null) {
            boolean okClicked = main.showCourseEditDialog(currentCourse, nameBox.getSelectionModel().getSelectedItem());
            setNameBoxItems();
            updateCoursesTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Course Selected");
            alert.setContentText("Please select a course in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewCourse(){
        Course tempCourse = new Course();
        boolean okClicked = main.showCourseEditDialog(tempCourse, nameBox.getSelectionModel().getSelectedItem());
        setNameBoxItems();
        updateCoursesTable();
    }

}

