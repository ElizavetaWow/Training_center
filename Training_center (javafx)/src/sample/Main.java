package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.*;
import sample.models.*;
import sample.utils.ApiSession;
import sample.utils.DateUtil;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ApiSession apiSession;

    public Main(){
        this.apiSession = new ApiSession();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("");
        initRootLayout();
        showCourseOverview();
    }

    public void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/rootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene= new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmployeeOverview(Company company){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/employee.fxml"));
            AnchorPane employeeOverview = loader.load();
            rootLayout.setCenter(employeeOverview);
            EmployeeOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            controller.setCompany(company);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showCourseOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/course.fxml"));
            AnchorPane courseOverview = loader.load();
            rootLayout.setCenter(courseOverview);
            CourseOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showCourseInfoOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/courseInfo.fxml"));
            AnchorPane courseInfoOverview = loader.load();
            rootLayout.setCenter(courseInfoOverview);
            CourseInfoOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showFacultyOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/faculty.fxml"));
            AnchorPane facultyOverview = loader.load();
            rootLayout.setCenter(facultyOverview);
            FacultyOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showPlaceOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/place.fxml"));
            AnchorPane placeOverview = loader.load();
            rootLayout.setCenter(placeOverview);
            PlaceOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showCompanyOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/company.fxml"));
            AnchorPane companyOverview = loader.load();
            rootLayout.setCenter(companyOverview);
            CompanyOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<Employee> GeneratePersons(){
        ObservableList<Employee> locData = FXCollections.observableArrayList();

        for (int i=1; i <= 20;i++){
            locData.add(new Employee((long) i, "Имя " + i ,"Фамилия " + i, "pass", "email", DateUtil.parse("28.10.2010"), new Company((long) i, "fdx", "zbdf")));
        }
        return locData;
    }

    public static ObservableList<Company> GenerateCompanies(){
        ObservableList<Company> locData = FXCollections.observableArrayList();

        for (int i=1; i <= 5;i++){
            locData.add(new Company((long) i, "Имя " + i ,"Счет " + i));
        }
        return locData;
    }

    public boolean showCompanyEditDialog(Company company){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/companyEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CompanyEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession);
            controller.setCompany(company);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEmployeeEditDialog(Employee employee){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/employeeEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EmployeeEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession);
            controller.setEmployee(employee);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPlaceEditDialog(Place place){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/placeEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PlaceEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession);
            controller.setPlace(place);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean showFacultyEditDialog(Faculty faculty){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/facultyEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FacultyEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession);
            controller.setFaculty(faculty);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCourseEditDialog(Course course, String name){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/courseEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CourseEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession);
            controller.setName(name);
            controller.setCourse(course);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
