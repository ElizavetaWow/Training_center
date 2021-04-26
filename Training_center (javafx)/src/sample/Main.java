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

    private Person user;
    private BorderPane rootLayout;
    private ApiSession apiSession;

    public Main(){
        this.apiSession = new ApiSession();
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Training Center");
        setUser(showLoginForm());
        if (user != null){
            initRootLayout();
            showTimetableOverview();
        }
    }

    public void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/rootLayout.fxml"));
            rootLayout = loader.load();
            RootController controller = loader.getController();
            controller.setMain(this);
            controller.setVisibleItems(getUser().getRole());
            Scene scene= new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Person showLoginForm(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/loginForm.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Log in");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LoginController controller = loader.getController();
            controller.setMain();
            controller.setApiSession(apiSession);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
            return controller.getUser();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
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
            controller.setVisibleHBox(user.getRole());
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
            controller.setVisibleHBox(user.getRole());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showTimetableOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/timetable.fxml"));
            AnchorPane timetableOverview = loader.load();
            rootLayout.setCenter(timetableOverview);
            TimetableOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            controller.setVisibleHBox(user.getRole());
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
            controller.setVisibleHBox(user.getRole());
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
            controller.setVisibleHBox(user.getRole());
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
            controller.setVisibleHBox(user.getRole());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showCompanyOverview(){
        try{
            if (user.getRole() > 0) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("views/company.fxml"));
                AnchorPane companyOverview = loader.load();
                rootLayout.setCenter(companyOverview);
                CompanyOverviewController controller = loader.getController();
                controller.setApiSession(apiSession);
                controller.setMainApp(this);
                controller.setVisibleHBox(user.getRole());
            }
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
            Stage dialogStage = createEditDialog(loader);

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

    private Stage createEditDialog(FXMLLoader loader) throws IOException {
        AnchorPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit");
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    public boolean showEmployeeEditDialog(Employee employee){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/employeeEditDialog.fxml"));
            Stage dialogStage = createEditDialog(loader);

            EmployeeEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession, this);
            controller.setEmployee(employee);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showPlaceEditDialog(Place place){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/placeEditDialog.fxml"));
            Stage dialogStage = createEditDialog(loader);

            PlaceEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession);
            controller.setPlace(place);
            dialogStage.showAndWait();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTimetableEditDialog(Timetable timetable){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/timetableEditDialog.fxml"));
            Stage dialogStage = createEditDialog(loader);

            TimetableEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession, this);
            controller.setTimetable(timetable);
            dialogStage.showAndWait();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean showFacultyEditDialog(Faculty faculty){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/facultyEditDialog.fxml"));
            Stage dialogStage = createEditDialog(loader);

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

    public void showCourseEditDialog(Course course){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/courseEditDialog.fxml"));
            Stage dialogStage = createEditDialog(loader);

            CourseEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, apiSession, this);
            controller.setCourse(course);
            dialogStage.showAndWait();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Faculty showChooseFacultyDialog(Stage owner){
        try {

            Stage dialogStage = new Stage();
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/faculty.fxml"));
            AnchorPane page = loader.load();
            FacultyOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            return (Faculty) createSelectionDialog(page, dialogStage, controller).getSelectedItem();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private SelectionController createSelectionDialog(AnchorPane page, Stage dialogStage, OverviewController controller){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/selectionLayout.fxml"));
            BorderPane selectionLayout = loader.load();
            SelectionController selectionController = loader.getController();
            selectionController.setDialogStage(dialogStage);
            selectionController.setSceneController(controller);
            selectionLayout.setCenter(page);
            Scene scene= new Scene(selectionLayout);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            return selectionController;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public CourseInfo showChooseCourseInfoDialog(Stage owner){
        try {

            Stage dialogStage = new Stage();
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/courseInfo.fxml"));
            AnchorPane page = loader.load();
            CourseInfoOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            return (CourseInfo) createSelectionDialog(page, dialogStage, controller).getSelectedItem();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Company showChooseCompanyDialog(Stage owner){
        try {

            Stage dialogStage = new Stage();
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/company.fxml"));
            AnchorPane page = loader.load();
            CompanyOverviewController controller = loader.getController();
            controller.setShowEmployeesButtonVisible(false);
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            return (Company) createSelectionDialog(page, dialogStage, controller).getSelectedItem();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Place showChoosePlaceDialog(Stage owner){
        try {

            Stage dialogStage = new Stage();
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/place.fxml"));
            AnchorPane page = loader.load();
            PlaceOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            return (Place) createSelectionDialog(page, dialogStage, controller).getSelectedItem();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course showChooseCourseDialog(Stage owner){
        try {

            Stage dialogStage = new Stage();
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/course.fxml"));
            AnchorPane page = loader.load();
            CourseOverviewController controller = loader.getController();
            controller.setApiSession(apiSession);
            controller.setMainApp(this);
            return (Course) createSelectionDialog(page, dialogStage, controller).getSelectedItem();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }
}
