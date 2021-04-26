package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import sample.Main;

public class RootController {

    @FXML
    private MenuItem companyItem;

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
    @FXML
    private void closeApp(){
        main.getPrimaryStage().close();
    }
    @FXML
    private void signOut() throws Exception {
        main.getPrimaryStage().close();
        main.start(main.getPrimaryStage());
    }

    public void setVisibleItems(int i){
        companyItem.setVisible(i != 0);
    }


}
