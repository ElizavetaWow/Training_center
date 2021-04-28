package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import sample.Main;
import sample.models.CourseInfo;
import sample.utils.ApiSession;

import java.util.List;
import java.util.Objects;

public class CourseInfoOverviewController extends OverviewController{
    @FXML
    private ListView<String> courseInfoListView;

    @FXML
    private TextField nameField;
    @FXML
    private HBox buttonsHBox;

    private Main main;
    private ApiSession apiSession;
    private ObservableList<String> courseInfoList = FXCollections.observableArrayList();

    public CourseInfoOverviewController(){
    }

    @FXML
    private void initialize(){
        courseInfoListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> showCourseInfoName(newValue));
    }

    public void showCourseInfoName(String name){
        nameField.setText(name);
        if (name != null){
            setItem(apiSession.getCourseInfosByName(name));
        }
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
        List<CourseInfo> courseInfos = apiSession.getCourseInfos();
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
            if (apiSession.deleteCourseInfo(currentCourseInfo)){
                updateCourseInfoListView();
            }
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
        nameField.setText("");
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

    public void setVisibleHBox(int i){
        buttonsHBox.setVisible(i >= 2);
    }

}

