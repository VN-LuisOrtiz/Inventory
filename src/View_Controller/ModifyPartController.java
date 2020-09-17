package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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

public class ModifyPartController implements Initializable {

    public Inventory passedData = MainScreenController.currentData;
    public Part passedPart = MainScreenController.currentPart;
    public boolean alertAnswer=false;



    @FXML
    private RadioButton inRadio;
    @FXML
    private RadioButton outRadio;
    @FXML
    private TextField selectedPartID;
    @FXML
    private TextField selectedPartName;
    @FXML
    private TextField selectedPartStock;
    @FXML
    private TextField selectedPartPrice;
    @FXML
    private TextField selectedPartMax;
    @FXML
    private TextField selectedPartMin;
    @FXML
    private TextField selectedCompanyName;
    @FXML
    private TextField selectedMachineID;
    @FXML
    private Label twoLabels;
    @FXML
    private Button saveIn;
    @FXML
    private Button saveOut;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedPartID.setDisable(true);

        if(passedPart instanceof InHouse) {
            inRadio.setSelected(true);
            inRadio.setDisable(false);
            outRadio.setSelected(false);
            outRadio.setDisable(false);
            twoLabels.setOpacity(1);
            twoLabels.setText("Machine ID");
            selectedMachineID.setOpacity(1);
            selectedMachineID.setDisable(false);
            saveIn.setOpacity(1);
            saveIn.setDisable(false);
            saveOut.setOpacity(0);
            saveOut.setDisable(true);
            InHouse inPart = (InHouse) passedPart;
            selectedPartID.setText(String.valueOf(inPart.getPartID()));
            selectedPartName.setText(inPart.getPartName());
            selectedPartStock.setText(String.valueOf(inPart.getPartStock()));
            selectedPartPrice.setText(String.valueOf(inPart.getPartPrice()));
            selectedPartMax.setText(String.valueOf(inPart.getMax()));
            selectedPartMin.setText(String.valueOf(inPart.getMin()));
            selectedMachineID.setText(String.valueOf(inPart.getMachineID()));
        }

        if(passedPart instanceof Outsourced) {
            inRadio.setSelected(false);
            inRadio.setDisable(false);
            outRadio.setSelected(true);
            outRadio.setDisable(false);
            selectedCompanyName.setOpacity(1);
            selectedCompanyName.setDisable(false);
            twoLabels.setOpacity(1);
            twoLabels.setText("Company Name");
            saveIn.setOpacity(0);
            saveIn.setDisable(true);
            saveOut.setOpacity(1);
            saveOut.setDisable(false);
            Outsourced outPart = (Outsourced) passedPart;
            selectedPartID.setText(String.valueOf(outPart.getPartID()));
            selectedPartName.setText(outPart.getPartName());
            selectedPartStock.setText(String.valueOf(outPart.getPartStock()));
            selectedPartPrice.setText(String.valueOf(outPart.getPartPrice()));
            selectedPartMax.setText(String.valueOf(outPart.getMax()));
            selectedPartMin.setText(String.valueOf(outPart.getMin()));
            selectedCompanyName.setText(String.valueOf(outPart.getCompanyName()));
        }


    }

    //Clicking In-House Radio button
    @FXML
    private void inClick(MouseEvent mouseEvent) {
        if(inRadio.isSelected()){
            outRadio.setSelected(false);
            outRadio.setDisable(false);
            selectedMachineID.setOpacity(1);
            selectedMachineID.setDisable(false);
            selectedCompanyName.setOpacity(0);
            selectedCompanyName.setDisable(true);
            twoLabels.setOpacity(1);
            twoLabels.setText("Machine ID");

        } else {
            outRadio.setSelected(true);
            outRadio.setDisable(false);
            selectedMachineID.setOpacity(0);
            selectedMachineID.setDisable(true);
            selectedCompanyName.setOpacity(1);
            selectedCompanyName.setDisable(false);
            twoLabels.setOpacity(1);
            twoLabels.setText("Company Name");

        }
    }

    //Clicking Outsourced Radio button
    @FXML
    private void outClick(MouseEvent mouseEvent) {
        if(outRadio.isSelected()){
            inRadio.setSelected(false);
            inRadio.setDisable(false);
            selectedMachineID.setOpacity(0);
            selectedMachineID.setDisable(true);
            selectedCompanyName.setOpacity(1);
            selectedCompanyName.setDisable(false);
            twoLabels.setOpacity(1);
            twoLabels.setText("Company Name");

        } else {
            inRadio.setSelected(true);
            inRadio.setDisable(false);
            selectedMachineID.setOpacity(1);
            selectedMachineID.setDisable(false);
            selectedCompanyName.setOpacity(0);
            selectedCompanyName.setDisable(true);
            twoLabels.setOpacity(1);
            twoLabels.setText("Machine ID");
        }
    }


    //When clicking from the button shown with an In-House Part
    @FXML
    private void saveInButton(MouseEvent mouseEvent) throws IOException {
        boolean isChanging = false;
        if(outRadio.isSelected()) {
            isChanging=true;
        }

        if(isChanging) {
            if(Integer.parseInt(selectedPartMax.getText()) < Integer.parseInt(selectedPartMin.getText())) {
                displayMaxLessThanMinError();
            } else {
                Outsourced outPart = new Outsourced(Integer.parseInt(selectedPartID.getText()),selectedPartName.getText(),Double.parseDouble(selectedPartPrice.getText()),Integer.parseInt(selectedPartStock.getText()),Integer.parseInt(selectedPartMin.getText()),Integer.parseInt(selectedPartMax.getText()),selectedCompanyName.getText());
                passedData.updatePart(MainScreenController.index,outPart);

                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=passedData;
                mainScreenWindow.show();
            }

        } else {
            if(Integer.parseInt(selectedPartMax.getText()) < Integer.parseInt(selectedPartMin.getText())){
                displayMaxLessThanMinError();
            } else {
                InHouse inPart = (InHouse) passedPart;
                inPart.setPartID(Integer.parseInt(selectedPartID.getText()));
                inPart.setPartName(selectedPartName.getText());
                inPart.setPartStock(Integer.parseInt(selectedPartStock.getText()));
                inPart.setPartPrice(Double.parseDouble(selectedPartPrice.getText()));
                inPart.setMax(Integer.parseInt(selectedPartMax.getText()));
                inPart.setMin(Integer.parseInt(selectedPartMin.getText()));
                inPart.setMachineID(Integer.parseInt(selectedMachineID.getText()));
                passedData.updatePart(MainScreenController.index,inPart);

                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=passedData;
                mainScreenWindow.show();
            }

        }



    }


    //When clicking from the button shown with an Outsourced Part
    @FXML
    private void saveOutButton(MouseEvent mouseEvent) throws IOException {
        boolean isChanging = false;
        if(inRadio.isSelected()) {
            isChanging=true;
        }

        if(isChanging) {
            if(Integer.parseInt(selectedPartMax.getText()) < Integer.parseInt(selectedPartMin.getText())) {
                displayMaxLessThanMinError();
            } else {
                InHouse inPart = new InHouse(Integer.parseInt(selectedPartID.getText()),selectedPartName.getText(),Double.parseDouble(selectedPartPrice.getText()),Integer.parseInt(selectedPartStock.getText()),Integer.parseInt(selectedPartMin.getText()),Integer.parseInt(selectedPartMax.getText()),Integer.parseInt(selectedMachineID.getText()));
                passedData.updatePart(MainScreenController.index,inPart);

                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=passedData;
                mainScreenWindow.show();
            }

        } else {
            if(Integer.parseInt(selectedPartMax.getText()) < Integer.parseInt(selectedPartMin.getText())) {
                displayMaxLessThanMinError();
            } else {
                Outsourced outPart = (Outsourced) passedPart;

                outPart.setPartID(Integer.parseInt(selectedPartID.getText()));
                outPart.setPartName(selectedPartName.getText());
                outPart.setPartStock(Integer.parseInt(selectedPartStock.getText()));
                outPart.setPartPrice(Double.parseDouble(selectedPartPrice.getText()));
                outPart.setMax(Integer.parseInt(selectedPartMax.getText()));
                outPart.setMin(Integer.parseInt(selectedPartMin.getText()));
                outPart.setCompanyName(selectedCompanyName.getText());
                passedData.updatePart(MainScreenController.index,outPart);

                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=passedData;
                mainScreenWindow.show();
            }

        }



    }

    //Going back to the Main Screen
    @FXML
    private void goBack(MouseEvent mouseEvent) throws IOException {
        cancelTransaction();
        if(alertAnswer==true) {
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
