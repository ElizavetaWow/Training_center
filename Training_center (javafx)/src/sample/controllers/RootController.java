package sample.controllers;

import javafx.fxml.FXML;
import sample.Main;

public class RootController {

    private Main main;

    public RootController(){
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void showFaculties(){
        main.showFacultyOverview();
    }

    @FXML
    private void showStudents(){
        main.showEmployeeOverview(null);
    }
    @FXML
    private void showTimetable(){
        main.showTimetableOverview();
    }
    @FXML
    private void showCourses(){
        main.showCourseInfoOverview();
    }
    @FXML
    private void showConcreteCourses(){
        main.showCourseOverview();
    }
    @FXML
    private void showCompanies(){
        main.showCompanyOverview();
    }
    @FXML
    private void showPlaces(){
        main.showPlaceOverview();
    }


}
