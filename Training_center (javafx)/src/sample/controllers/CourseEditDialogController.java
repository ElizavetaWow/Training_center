package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import sample.models.Course;
import sample.models.CourseInfo;
import sample.models.Faculty;
import sample.utils.ApiSession;

import java.util.ArrayList;
import java.util.List;

public class CourseEditDialogController {
    @FXML
    private ComboBox<String> nameBox;
    @FXML
    private DatePicker startPicker;
    @FXML
    private DatePicker finishPicker;
    @FXML
    private ComboBox<String> facultyBox;



    private Course course;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;
    private ObservableList<String> namesList = FXCollections.observableArrayList();
    private List<CourseInfo> courseInfos = new ArrayList<>();
    private ObservableList<String> facultyList = FXCollections.observableArrayList();
    private List<Faculty> faculties = new ArrayList<>();

    private ApiSession apiSession;

    public CourseEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
        setNameBoxItems();
        setFacultyBoxItems();
    }

    public void setCourse(Course course){
        if (course.getCourseInfo() != null){
            this.update = true;
            nameBox.getSelectionModel().select(course.getCourseInfo().getName());
            facultyBox.getSelectionModel().select(course.getFaculty().getNamesAndEmail());
        }
        this.course = course;
        startPicker.setValue(course.getStartDate());
        finishPicker.setValue(course.getFinishDate());

    }

    public void setName(String name){
        if (name != null && !name.equals("All Courses")){
            nameBox.getSelectionModel().select(name);
        }
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            course.setStartDate(startPicker.getValue());
            course.setFinishDate(finishPicker.getValue());
            course.setCourseInfo(apiSession.getCourseInfosByName(nameBox.getSelectionModel().getSelectedItem()));
            String facultyString = facultyBox.getSelectionModel().getSelectedItem();
            course.setFaculty(apiSession.getFacultiesByEmail(
                    facultyString.substring(facultyString.indexOf("[") + 1, facultyString.lastIndexOf("]"))));
            if (update) {
                apiSession.updateCourse(course);
            }
            else {
                apiSession.createCourse(course);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if (nameBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No name selection!\n";
        }
        if (startPicker.getValue() == null) {
            errorMessage += "No start date selection!\n";
        }
        if (finishPicker.getValue() == null) {
            errorMessage += "No finish date selection!\n";
        }

        if (facultyBox.getSelectionModel().getSelectedIndex() < 0) {
            errorMessage += "No faculty selection!\n";
        }
        if (startPicker.getValue().isAfter(finishPicker.getValue())){
            errorMessage += "Start date must be before finish date!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong field input");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public void setNameBoxItems(){
        courseInfos = apiSession.getCourseInfos();
        namesList.clear();
        for (CourseInfo courseInfo: courseInfos){
            namesList.add(courseInfo.getName());
        }
        nameBox.setItems(namesList);
    }

    public void setFacultyBoxItems(){
        faculties = apiSession.getFaculties();
        facultyList.clear();
        for (Faculty faculty: faculties){
            facultyList.add(faculty.getNamesAndEmail());
        }
        facultyBox.setItems(facultyList);
    }
}
