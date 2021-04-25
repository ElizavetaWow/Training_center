package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Place;
import sample.utils.ApiSession;

import java.util.Objects;

public class PlaceEditDialogController {
    @FXML
    private TextField cityField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField buildingField;
    @FXML
    private TextField roomField;


    private Place place;
    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean update = false;

    private ApiSession apiSession;

    public PlaceEditDialogController(){}

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage, ApiSession apiSession) {
        this.dialogStage = dialogStage;
        this.apiSession = apiSession;
    }

    public void setPlace(Place place){
        if (place.getCity() != null){
            this.update = true;
            roomField.setText(String.valueOf(place.getRoom()));
        }
        this.place = place;
        cityField.setText(place.getCity());
        streetField.setText(place.getStreet());
        buildingField.setText(place.getBuilding());
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
            place.setCity(cityField.getText());
            place.setStreet(streetField.getText());
            place.setBuilding(buildingField.getText());
            place.setRoom(Integer.parseInt(roomField.getText()));
            if (update) {
                apiSession.updatePlace(place);
            }
            else {
                apiSession.createPlace(place);
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No city input!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No street input!\n";
        }
        if (buildingField.getText() == null || buildingField.getText().length() == 0) {
            errorMessage += "No building input!\n";
        }
        if (roomField.getText() == null || roomField.getText().length() == 0) {
            errorMessage += "No room input!\n";
        }
        if (!Objects.requireNonNull(roomField.getText()).matches("\\d+")) {
            errorMessage += "Room must be a number!\n";
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
