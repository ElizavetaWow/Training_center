package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SelectionController {

    private OverviewController controller;
    private Stage dialogStage;
    private Object selectedItem;

    public void setSceneController(OverviewController controller) {
        this.controller = controller;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleSelect() {
        if (controller.getItem() != null) {
            dialogStage.close();
            selectedItem = controller.getItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("No selection");
            alert.setContentText("You haven't selected the item");
            alert.showAndWait();
        }
    }

    public Object getSelectedItem() {
        return selectedItem;
    }
}
