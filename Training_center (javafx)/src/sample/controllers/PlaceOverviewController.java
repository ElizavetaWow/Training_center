package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import sample.Main;
import sample.models.Place;
import sample.utils.ApiSession;

public class PlaceOverviewController extends OverviewController {
    @FXML
    private TableView<Place> placeTableView;
    @FXML
    private TableColumn<Place, String> cityColumn;
    @FXML
    private TableColumn<Place, String> streetColumn;
    @FXML
    private TableColumn<Place, String> buildingColumn;
    @FXML
    private TableColumn<Place, Integer> roomColumn;
    @FXML
    private HBox buttonsHBox;

    @FXML
    private ComboBox<String> cityBox;

    private Main main;
    private ApiSession apiSession;
    private final ObservableList<Place> placeList = FXCollections.observableArrayList();

    public PlaceOverviewController(){
    }

    @FXML
    private void initialize(){
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().getCityProperty());
        streetColumn.setCellValueFactory(cellData -> cellData.getValue().getStreetProperty());
        buildingColumn.setCellValueFactory(cellData -> cellData.getValue().getBuildingProperty());
        roomColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomProperty().asObject());
        placeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> setItem(newValue));
    }

    public void setApiSession(ApiSession apiSession) {
        this.apiSession = apiSession;
    }

    public void setMainApp(Main main){
        this.main = main;
        setCityBoxItems();
        updatePlacesTable();
    }

    public void setCityBoxItems(){
        updatePlaceList();
        ObservableList<String> cityList = FXCollections.observableArrayList();
        cityList.clear();
        cityList.add("All Cities");
        for (Place place: placeList){
            if (!cityList.contains(place.getCity())){
                cityList.add(place.getCity());
            }
        }
        cityBox.setItems(cityList);
        cityBox.getSelectionModel().select(0);
    }

    public void setCity(String city){
        if (city != null){
            cityBox.getSelectionModel().select(city);
            updatePlacesTable();
        }
    }

    @FXML
    private void updatePlacesTable(){
        int selectionIndex = cityBox.getSelectionModel().getSelectedIndex();
        if (selectionIndex > 0) {
            updatePlaceList(cityBox.getSelectionModel().getSelectedItem());
        }
        else if (selectionIndex == 0) {
            updatePlaceList();
        }
        placeTableView.setItems(placeList);
    }


    public void updatePlaceList(){
        placeList.clear();
        placeList.addAll(apiSession.getPlaces());
    }

    public void updatePlaceList(String city){
        placeList.clear();
        placeList.addAll(apiSession.getPlacesByCity(city));
    }


    @FXML
    private void handleDeletePlace(){
        Place currentPlace = placeTableView.getSelectionModel().getSelectedItem();
        if (currentPlace != null) {
            if (apiSession.deletePlace(currentPlace)) {
                setCityBoxItems();
                updatePlacesTable();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Place Selected");
            alert.setContentText("Please select a place in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditPlace(){
        Place currentPlace = placeTableView.getSelectionModel().getSelectedItem();
        if (currentPlace != null) {
            main.showPlaceEditDialog(currentPlace);
            setCityBoxItems();
            updatePlacesTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Place Selected");
            alert.setContentText("Please select a place in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPlace(){
        Place tempPlace = new Place();
        main.showPlaceEditDialog(tempPlace);
        setCityBoxItems();
        updatePlacesTable();
    }

    public void setVisibleHBox(int i){
        buttonsHBox.setVisible(i >= 2);
    }
}

