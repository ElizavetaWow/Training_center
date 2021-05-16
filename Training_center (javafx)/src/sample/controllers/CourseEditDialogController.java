package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Course;
import sample.models.CourseInfo;
import sample.models.Faculty;
import sample.utils.ApiSession;
import sample.utils.DateUtil;

public class CourseEditDialogController {
    @FXML
    private DatePicker startPicker;
    @FXML
    private DatePicker finishPicker;
    @FXML
    private Label facultyLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField priceField;


    private Course course;
    private Faculty selectedFaculty;
    private CourseInfo selectedCourseInfo;
    private Main main;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;

    private ApiSession apiSession;

    public CourseEditDialogController() {
    }

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession, Main main) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
        this.main = main;
    }

    public void setCourse(Course course) {
        if (course.getCourseInfo() != null) {
            this.update = true;
            selectedCourseInfo = course.getCourseInfo();
            nameLabel.setText(course.getCourseInfo().getName());
            selectedFaculty = course.getFaculty();
            facultyLabel.setText(course.getFaculty().getNamesAndEmail());
        }
        this.course = course;
        startPicker.setValue(course.getStartDate());
        finishPicker.setValue(course.getFinishDate());
        priceField.setText(String.valueOf(course.getPrice()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleSelectFaculty() {
        Faculty faculty = main.showChooseFacultyDialog(dialogStage);
        if (faculty != null) {
            selectedFaculty = faculty;
            facultyLabel.setText(selectedFaculty.getNamesAndEmail());
        }
    }

    @FXML
    private void handleSelectName() {
        CourseInfo courseInfo = main.showChooseCourseInfoDialog(dialogStage);
        if (courseInfo != null) {
            selectedCourseInfo = courseInfo;
            nameLabel.setText(selectedCourseInfo.getName());
        }
    }

    @FXML
    private void handleOk() {
        try {
            startPicker.setValue(DateUtil.parse(startPicker.getEditor().getText()));
        } catch (Exception ignored) {
        }
        try {
            finishPicker.setValue(DateUtil.parse(finishPicker.getEditor().getText()));
        } catch (Exception ignored) {
        }
        if (isInputValid()) {
            course.setStartDate(startPicker.getValue());
            course.setFinishDate(finishPicker.getValue());
            course.setCourseInfo(selectedCourseInfo);
            course.setFaculty(selectedFaculty);
            course.setPrice(Integer.parseInt(priceField.getText()));
            if (update) {
                apiSession.updateCourse(course);
            } else {
                apiSession.createCourse(course);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (nameLabel.getText() == null || nameLabel.getText().length() == 0) {
            errorMessage += "No name selection!\n";
        }
        if (startPicker.getValue() == null) {
            errorMessage += "No start date selection!\n";
        }
        if (finishPicker.getValue() == null) {
            errorMessage += "No finish date selection!\n";
        }

        if (facultyLabel.getText() == null || facultyLabel.getText().length() == 0) {
            errorMessage += "No faculty selection!\n";
        }
        if (startPicker.getValue().isAfter(finishPicker.getValue())) {
            errorMessage += "Start date must be before finish date!\n";
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No price input!\n";
        } else if (!priceField.getText().matches("[1-9][0-9]*")) {
            errorMessage += "Price must consists of numbers and be more than 0!\n";
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

}
