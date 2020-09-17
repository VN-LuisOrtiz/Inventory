package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private Inventory passedData = MainScreenController.currentData;    //Passed Data from the Main Screen
    private Inventory dataBeforeChanges;                                //Since passedData will be changed, dataBeforeChanges will hold original data just in case user clicks the Cancel button.
    private Product createdProduct;                                     //Added product by user

    @FXML
    public static ObservableList<Part> partInventory = FXCollections.observableArrayList();

    @FXML
    private Button sButton;
    @FXML
    private  TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartPriceColumn;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productStockField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;
    @FXML
    private ObservableList<Part> searchPartInventory = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part,Integer> partIDColumn;
    @FXML
    private TableColumn<Part,String> partNameColumn;
    @FXML
    private TableColumn<Part,Integer> partStockColumn;
    @FXML
    private TableColumn<Part,Double> partPriceColumn;
    @FXML
    private TextField searchPartInput;

    private int clickedCounter = 0;
    private int addedAlready = 0;
    public boolean alertAnswer=false;


    //Add Product Screen will be initialized with the data that was passed.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBeforeChanges=MainScreenController.currentData;
        productIDField.setText(Integer.toString(MainScreenController.generatedProductID));
        productIDField.setDisable(true);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        showPassedPartData();
    }

    //Method to populate the Parts Table with the passed data
    private void showPassedPartData() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        partInventory.setAll(MainScreenController.currentData.getAllParts());
        partsTable.setItems(partInventory);
        partsTable.refresh();
    }


    //Searching in the Parts Table
    @FXML
    private void searchPart(MouseEvent mouseEvent) {
        if(searchPartInput.getText().isEmpty()) {
            partsTable.setItems(partInventory);
            partsTable.refresh();
        } else {
            searchPartInventory.clear();
            String input = searchPartInput.getText();
            for(Part search: MainScreenController.currentData.getAllParts()) {
                if(search.getPartName().toLowerCase().contains(input.toLowerCase())) {
                    searchPartInventory.add(search);
                }
            }
            partsTable.setItems(searchPartInventory);
            partsTable.refresh();

        }

    }



    //When clicking in Save button after providing information about the new product
    @FXML
    private void saveButton(MouseEvent mouseEvent) throws IOException {
        if(!productNameField.getText().isEmpty() && !productStockField.getText().isEmpty() && !productPriceField.getText().isEmpty() && !productMaxField.getText().isEmpty() && !productMinField.getText().isEmpty() && addedAlready == 0) {
            if(Integer.parseInt(productMaxField.getText()) < Integer.parseInt(productMinField.getText())) {
                displayMaxLessThanMinError();
            } else {
                addedAlready++;
                MainScreenController.generatedProductID++;
                createdProduct = new Product(Integer.parseInt(productIDField.getText()),productNameField.getText(),Double.parseDouble(productPriceField.getText()),Integer.parseInt(productStockField.getText()),Integer.parseInt(productMinField.getText()),Integer.parseInt(productMaxField.getText()));
                passedData.addProduct(createdProduct);
                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=passedData;
                mainScreenWindow.show();
            }
        } else if(!productNameField.getText().isEmpty() && !productStockField.getText().isEmpty() && !productPriceField.getText().isEmpty() && !productMaxField.getText().isEmpty() && !productMinField.getText().isEmpty() && addedAlready > 0){
            MainScreenController.init = 1;
            passedData.addProduct(createdProduct);
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainScreenWindow.setScene(mainScreenScene);
            MainScreenController.currentData=passedData;
            mainScreenWindow.show();
        } else {
            //Do Nothing
        }
    }


    //When clicking in the add button to add an associated part to the new product
    @FXML
    private void addAssociatedPartSelected(MouseEvent mouseEvent) {

        clickedCounter++;
        if(partsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else if(!productNameField.getText().isEmpty() && !productStockField.getText().isEmpty() && !productPriceField.getText().isEmpty() && !productMaxField.getText().isEmpty() && !productMinField.getText().isEmpty() && addedAlready == 0) {
            if(Integer.parseInt(productMaxField.getText()) < Integer.parseInt(productMinField.getText())) {
                displayMaxLessThanMinError();
            } else {

                addedAlready++;
                MainScreenController.generatedProductID++;
                createdProduct = new Product(Integer.parseInt(productIDField.getText()),productNameField.getText(),Double.parseDouble(productPriceField.getText()),Integer.parseInt(productStockField.getText()),Integer.parseInt(productMinField.getText()),Integer.parseInt(productMaxField.getText()));
                //passedData.addProduct(newProduct);
                MainScreenController.init = 1;

                Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
                createdProduct.addAssociatedPart(selectedPart);
                associatedPartsTable.setItems(createdProduct.getAllAssociatedParts());
                associatedPartsTable.refresh();
            }
        } else if(!productNameField.getText().isEmpty() && !productStockField.getText().isEmpty() && !productPriceField.getText().isEmpty() && !productMaxField.getText().isEmpty() && !productMinField.getText().isEmpty() && addedAlready > 0) {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            createdProduct.addAssociatedPart(selectedPart);
            associatedPartsTable.setItems(createdProduct.getAllAssociatedParts());
            associatedPartsTable.refresh();
        } else {
            displayEmptyFieldsError();
        }

    }

    //Deleting from the associated part table
    @FXML
    private void deleteAssociatedPartSelected(MouseEvent mouseEvent) {
        clickedCounter++;
        if(associatedPartsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else {
            cancelDelete();
            if(alertAnswer==true) {
                Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
                associatedPartsTable.getItems().remove(selectedPart);
                createdProduct.deleteAssociatedPart(selectedPart);
                associatedPartsTable.setItems(createdProduct.getAllAssociatedParts());
                associatedPartsTable.refresh();
            }

        }
    }


    //Going back to the Main Screen
    @FXML
    private void goBack(MouseEvent mouseEvent) throws IOException {
        cancelTransaction();
        if(alertAnswer==true) {
            if(clickedCounter != 0) {
                MainScreenController.generatedProductID--;
            }
            if(clickedCounter > 0) {
                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=dataBeforeChanges;
                mainScreenWindow.show();
            } else {
                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=dataBeforeChanges;
                mainScreenWindow.show();
            }
        }

    }

    //Error message of Max is less than Min
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


    //Error message user tries to add an associated part with empty fields
    public static void displayEmptyFieldsError() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ERROR");
        window.setMinWidth(300);
        Label message = new Label();
        message.setText("Please fill out the empty fields");
        Button closeError = new Button("Close");
        closeError.setOnAction(e -> window.close());
        VBox layout = new VBox(20);
        layout.getChildren().addAll(message,closeError);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    //Cancel pop-up
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


    //Delete pop-up
    public void cancelDelete() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setMinWidth(300);
        Label message = new Label();
        message.setText("Are you sure you want to delete the Associated Part?");
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
