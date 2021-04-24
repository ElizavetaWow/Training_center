package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.models.Company;
import sample.models.CourseInfo;
import sample.utils.ApiSession;
import sample.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseInfoOverviewController {
    @FXML
    private ListView<String> courseInfoListView;

    @FXML
    private TextField nameField;

    private Main main;
    private ApiSession apiSession;
    private ObservableList<String> courseInfoList = FXCollections.observableArrayList();
    private List<CourseInfo> courseInfos = new ArrayList<>();

    public CourseInfoOverviewController(){
    }

    @FXML
    private void initialize(){
        courseInfoListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showCourseInfoName(newValue));
    }

    public void showCourseInfoName(String name){
        nameField.setText(name);
    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setMainApp(Main main){
        this.main = main;
        updateCourseInfoListView();
    }

    @FXML
    private void updateCourseInfoListView(){
        courseInfoList.clear();
        courseInfos = apiSession.getCourseInfos();
        for (CourseInfo courseInfo: courseInfos){
            courseInfoList.add(courseInfo.getName());
        }
        courseInfoListView.setItems(courseInfoList);
    }


    @FXML
    private void handleDeleteCourseInfo(){
        int selectedIndex = courseInfoListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            CourseInfo currentCourseInfo = apiSession.getCourseInfosByName(courseInfoListView.getItems().get(selectedIndex));
            apiSession.deleteCourseInfo(currentCourseInfo);
            updateCourseInfoListView();
            courseInfoListView.getSelectionModel().selectFirst();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No CourseInfo Selected");
            alert.setContentText("Please select a courseInfo in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditCourseInfo(){
        int selectedIndex = courseInfoListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            CourseInfo currentCourseInfo = apiSession.getCourseInfosByName(courseInfoListView.getItems().get(selectedIndex));
            if(isInputValid(currentCourseInfo)){
                currentCourseInfo.setName(nameField.getText());
                apiSession.updateCourseInfo(currentCourseInfo);
            }
            updateCourseInfoListView();
            courseInfoListView.getSelectionModel().select(currentCourseInfo.getName());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No CourseInfo Selected");
            alert.setContentText("Please select a courseInfo in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewCourseInfo(){
        CourseInfo tempCourseInfo = new CourseInfo();
        if(isInputValid(tempCourseInfo)){
            tempCourseInfo.setName(nameField.getText());
            apiSession.createCourseInfo(tempCourseInfo);
        }
        updateCourseInfoListView();
        courseInfoListView.getSelectionModel().select(tempCourseInfo.getName());
    }

    private boolean isInputValid(CourseInfo courseInfo){
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No name input!\n";
        }
        else {
            if (!nameField.getText().matches("[\\w\\s]+\\w+")){
                errorMessage += "Wrong format of name input!\n";
            }
            else if (apiSession.getCourseInfosByName(nameField.getText()) != null && !Objects.equals(courseInfo.getName(), nameField.getText())) {
                errorMessage += "Such courseInfo is already exists!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong field input");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}

