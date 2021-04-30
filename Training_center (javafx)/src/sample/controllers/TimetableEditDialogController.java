package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Course;
import sample.models.Place;
import sample.models.Timetable;
import sample.utils.ApiSession;
import sample.utils.DateUtil;
import sample.utils.TimeUtil;

import java.time.LocalDate;

public class TimetableEditDialogController {
    @FXML
    private TextField timeField;
    @FXML
    private Label courseLabel;
    @FXML
    private Label placeLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button placeButton;

    private Timetable timetable;
    private Place selectedPlace;
    private Course selectedCourse;
    private Main main;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;

    private ApiSession apiSession;

    public TimetableEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession, Main main) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
        this.main = main;
    }

    public void setTimetable(Timetable timetable){
        if (timetable.getDate() != null){
            this.update = true;
            selectedPlace = timetable.getPlace();
            placeLabel.setText(timetable.getPlace().getFullPlace());
            selectedCourse = timetable.getCourse();
            courseLabel.setText(timetable.getCourse().getCourseInfo().getName());
        }
        this.timetable = timetable;
        datePicker.setValue(timetable.getDate());
        timeField.setText(TimeUtil.format(timetable.getTime()));
        if (!update){
            datePicker.setDisable(true);
            timeField.setDisable(true);
            placeButton.setDisable(true);
        }
        datePicker.setDayCellFactory(cell -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(selectedCourse.getStartDate()) || date.isAfter(selectedCourse.getFinishDate()));
            }
        });
    }

    @FXML
    private void handleSelectPlace(){
        Place place = main.showChoosePlaceDialog(dialogStage);
        if (place != null){
            selectedPlace = place;
            placeLabel.setText(selectedPlace.getFullPlace());
        }
    }

    @FXML
    private void handleSelectCourse(){
        Course course = main.showChooseCourseDialog(dialogStage, null);
        if (course != null) {
            selectedCourse = course;
            courseLabel.setText(selectedCourse.getCourseInfo().getName());
            datePicker.setValue(course.getStartDate());
            datePicker.setDisable(false);
            timeField.setDisable(false);
            placeButton.setDisable(false);
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
        try {
            datePicker.setValue(DateUtil.parse(datePicker.getEditor().getText()));
        } catch (Exception ignored){}
        if(isInputValid()){
            timetable.setDate(datePicker.getValue());
            timetable.setTime(TimeUtil.parse(timeField.getText()));
            timetable.setCourse(selectedCourse);
            timetable.setPlace(selectedPlace);
            if (update) {
                apiSession.updateTimetable(timetable);
            }
            else {
                apiSession.createTimetable(timetable);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (timeField.getText() == null || timeField.getText().length() == 0) {
            errorMessage += "No time input!\n";
        } else{
            if (!timeField.getText().matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")){
                errorMessage += "Wrong format of time input!\n";
            }
        }
        if (datePicker.getValue() == null) {
            errorMessage += "No date selection!\n";
        } else if (datePicker.getValue().isAfter(selectedCourse.getFinishDate())
                || datePicker.getValue().isBefore(selectedCourse.getStartDate())){
            errorMessage += "Date must be between "+selectedCourse.getStartDate()+" and "+selectedCourse.getFinishDate()+"!\n";
        }

        if (courseLabel.getText() == null || courseLabel.getText().length() == 0) {
            errorMessage += "No course selection!\n";
        }
        if (placeLabel.getText() == null || placeLabel.getText().length() == 0) {
            errorMessage += "No place selection!\n";
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
