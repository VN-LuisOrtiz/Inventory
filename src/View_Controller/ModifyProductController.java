package View_Controller;

import Model.*;
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

public class ModifyProductController implements Initializable {

    private Inventory passedData = MainScreenController.currentData;
    private Product passedProduct = MainScreenController.currentProduct;
    public boolean alertAnswer=false;

    @FXML
    private ObservableList<Part> partsThatWereAdded = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Part> partsThatWereDeleted = FXCollections.observableArrayList();
    @FXML
    private static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartPriceColumn;
    @FXML
    private TextField selectedProductID;
    @FXML
    private TextField selectedProductName;
    @FXML
    private TextField selectedProductStock;
    @FXML
    private TextField selectedProductPrice;
    @FXML
    private TextField selectedProductMax;
    @FXML
    private TextField selectedProductMin;
    @FXML
    private ObservableList<Part> searchPartInventory = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private  TableView<Part> associatedPartsTable;
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
    private Part selectedPartToBeAdded;
    private Part selectedPartToBeDeleted;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedProductID.setDisable(true);
        showPassedPartData();
        showAssociatedPartsOfProduct();

        selectedProductID.setText(String.valueOf(passedProduct.getProductID()));
        selectedProductName.setText(passedProduct.getProductName());
        selectedProductStock.setText(String.valueOf(passedProduct.getProductStock()));
        selectedProductPrice.setText(String.valueOf(passedProduct.getProductPrice()));
        selectedProductMax.setText(String.valueOf(passedProduct.getMax()));
        selectedProductMin.setText(String.valueOf(passedProduct.getMin()));

    }

    private void showPassedPartData() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        partInventory.setAll(MainScreenController.currentData.getAllParts());
        partsTable.setItems(partInventory);
        partsTable.refresh();
    }

    private void showAssociatedPartsOfProduct() {
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        associatedPartsTable.setItems(passedProduct.getAllAssociatedParts());
        associatedPartsTable.refresh();
    }

    //When clicking on the Save button after modifying products
    @FXML
    private void saveButton(MouseEvent mouseEvent) throws IOException {
        if(Integer.parseInt(selectedProductMax.getText()) < Integer.parseInt(selectedProductMin.getText())) {
            displayMaxLessThanMinError();
        } else {
            //passedProduct.deleteAssociatedPart(selectedPartToBeDeleted);
            //associatedPartsTable.getItems().remove(selectedPartToBeAdded);                                      //Deleting the "temporary" row from the Associated Parts Table. If it doesn't get deleted, the part appears twice due to the next line of code.
            //passedProduct.addAssociatedPart(selectedPartToBeAdded);                                             //Adding the Associated Part of the Product being Modified to the Associated Parts Table
            passedProduct.setProductID(Integer.parseInt(selectedProductID.getText()));
            passedProduct.setProductName(selectedProductName.getText());
            passedProduct.setProductStock(Integer.parseInt(selectedProductStock.getText()));
            passedProduct.setProductPrice(Double.parseDouble(selectedProductPrice.getText()));
            passedProduct.setMax(Integer.parseInt(selectedProductMax.getText()));
            passedProduct.setMin(Integer.parseInt(selectedProductMin.getText()));
            MainScreenController.init = 1;
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainScreenWindow.setScene(mainScreenScene);
            MainScreenController.currentData=passedData;
            mainScreenWindow.show();
        }

    }


    //Deleting associated parts from products
    @FXML
    private void deleteAssociatedPartSelected(MouseEvent mouseEvent) {
        clickedCounter++;
        if(associatedPartsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else {
            cancelDelete();
            if(alertAnswer==true) {
                Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
                partsThatWereDeleted.add(selectedPart);
                selectedPartToBeDeleted = selectedPart;
                associatedPartsTable.getItems().remove(selectedPart);
                associatedPartsTable.setItems(passedProduct.getAllAssociatedParts());
                associatedPartsTable.refresh();
            }
        }
    }

    //Adding an associated part to the product
    @FXML
    private void addAssociatedPartSelected(MouseEvent mouseEvent) {
        clickedCounter++;
        if(partsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            partsThatWereAdded.add(selectedPart);
            selectedPartToBeAdded = selectedPart;
            associatedPartsTable.getItems().add(selectedPart);
            associatedPartsTable.refresh();
        }

    }


    //Search in the Parts table
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


    //Going back to the Main Screen
    @FXML
    private void goBack(MouseEvent mouseEvent) throws IOException {
        cancelTransaction();
        if(alertAnswer==true) {
            if(clickedCounter > 0) {
                System.out.println("123");
                for(int i = 0; i < partsThatWereDeleted.size(); i++)
                    passedProduct.addAssociatedPart(partsThatWereDeleted.get(i));
                for(int j = 0; j < partsThatWereAdded.size(); j++)
                    passedProduct.deleteAssociatedPart(partsThatWereAdded.get(j));
                MainScreenController.init = 1;
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainScreenWindow.setScene(mainScreenScene);
                MainScreenController.currentData=passedData;
                mainScreenWindow.show();
            } else {
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
