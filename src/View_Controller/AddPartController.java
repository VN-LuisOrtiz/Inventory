package View_Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    public Inventory passedData = MainScreenController.currentData;
    public boolean alertAnswer=false;


    @FXML
    private RadioButton inRadio;
    @FXML
    private RadioButton outRadio;
    @FXML
    private TextField companyName;
    @FXML
    private TextField machineID;
    @FXML
    private Label twoLabels;
    @FXML
    private TextField partIDField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partStockField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    public Button saveIn;
    @FXML
    public Button saveOut;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partIDField.setText(Integer.toString(MainScreenController.generatedPartID));

        partIDField.setDisable(true);
        if (inRadio.isSelected()) {
            outRadio.setDisable(true);
        } else if (outRadio.isSelected()) {
            inRadio.setDisable(true);
        } else {
            inRadio.setDisable(false);
            outRadio.setDisable(false);
            inRadio.setSelected(false);
            outRadio.setSelected(false);
        }
    }


    //Going back to the Main Screen
    @FXML
    private void goBack(MouseEvent mouseEvent) throws IOException {
        cancelTransaction();
        if(alertAnswer == true) {
            MainScreenController.init = 1;
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainScreenWindow.setScene(mainScreenScene);
            MainScreenController.currentData=passedData;
            mainScreenWindow.show();
        }

    }

    //Clicking the In-House Radio Button
    @FXML
    private void inClick(MouseEvent mouseEvent) {
        if(inRadio.isSelected()){
            outRadio.setSelected(false);
            outRadio.setDisable(true);
            machineID.setOpacity(1);
            machineID.setDisable(false);
            twoLabels.setOpacity(1);
            twoLabels.setText("Machine ID");
            saveIn.setOpacity(1);
            saveIn.setDisable(false);
            saveOut.setOpacity(0);
            saveOut.setDisable(true);
        } else {
            outRadio.setSelected(false);
            outRadio.setDisable(false);
            machineID.setOpacity(0);
            machineID.setDisable(true);
            twoLabels.setOpacity(0);
            saveIn.setOpacity(0);
            saveIn.setDisable(true);
            saveOut.setOpacity(0);
            saveOut.setDisable(true);
        }
    }

    //Clicking the Outsourced Radio Button
    @FXML
    private void outClick(MouseEvent mouseEvent) {
        if(outRadio.isSelected()){
            inRadio.setSelected(false);
            inRadio.setDisable(true);
            companyName.setOpacity(1);
            companyName.setDisable(false);
            twoLabels.setOpacity(1);
            twoLabels.setText("Company Name");
            saveIn.setOpacity(0);
            saveIn.setDisable(true);
            saveOut.setOpacity(1);
            saveOut.setDisable(false);
        } else {
            inRadio.setSelected(false);
            inRadio.setDisable(false);
            companyName.setOpacity(0);
            companyName.setDisable(true);
            twoLabels.setOpacity(0);
            twoLabels.setText("Company Name");
            saveIn.setOpacity(0);
            saveIn.setDisable(true);
            saveOut.setOpacity(0);
            saveOut.setDisable(true);
        }
    }

    //When clicking from the button shown with an In-House Part
    @FXML
    private void saveInButton(MouseEvent mouseEvent) throws IOException {
        if(Integer.parseInt(partMaxField.getText()) < Integer.parseInt(partMinField.getText())) {
            displayMaxLessThanMinError();
        } else {
            MainScreenController.generatedPartID++;
            Part newInPart = new InHouse(Integer.parseInt(partIDField.getText()),partNameField.getText(),Double.parseDouble(partPriceField.getText()),Integer.parseInt(partStockField.getText()),Integer.parseInt(partMinField.getText()),Integer.parseInt(partMaxField.getText()),Integer.parseInt(machineID.getText()));
            passedData.addPart(newInPart);
            MainScreenController.init = 1;
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainScreenWindow.setScene(mainScreenScene);
            MainScreenController.currentData=passedData;
            mainScreenWindow.show();
        }

    }


    //When clicking from the button shown with an Outsourced Part
    @FXML
    private void saveOutButton(MouseEvent mouseEvent) throws IOException {
        if(Integer.parseInt(partMaxField.getText()) < Integer.parseInt(partMinField.getText())) {
            displayMaxLessThanMinError();
        } else {
            MainScreenController.generatedPartID++;
            Part newOutPart = new Outsourced(Integer.parseInt(partIDField.getText()),partNameField.getText(),Double.parseDouble(partPriceField.getText()),Integer.parseInt(partStockField.getText()),Integer.parseInt(partMinField.getText()),Integer.parseInt(partMaxField.getText()),companyName.getText());
            passedData.addPart(newOutPart);
            MainScreenController.init = 1;
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainScreenWindow.setScene(mainScreenScene);
            MainScreenController.currentData=passedData;
            mainScreenWindow.show();
        }

    }


    public static void displayMaxLessThanMinError() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ERROR");
        window.setMinWidth(300);
        Label message = new Label();
        message.setText("Max value cannot be less than Min value");
        Button closeError = new Button("Close");
        closeError.setOnAction(e -> window.close());
        VBox layout = new VBox(20);
        layout.getChildren().addAll(message,closeError);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void cancelTransaction() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setMinWidth(300);
        Label message = new Label();
        message.setText("Are you sure you want to cancel your transaction?");
        Button yesB = new Button("Yes");
        Button noB = new Button("No");
        noB.setOnAction(e -> {
            alertAnswer = false;
            window.close();
        });
        yesB.setOnMouseClicked(e -> {
            alertAnswer = true;
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(message,yesB,noB);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
