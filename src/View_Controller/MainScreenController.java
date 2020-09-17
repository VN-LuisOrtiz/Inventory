package View_Controller;

import Model.*;
import javafx.application.Platform;
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

public class MainScreenController implements Initializable {


    public static Inventory data = new Inventory();
    public static Inventory currentData = new Inventory();
    public static int index = 0;
    public static Part currentPart;
    public static Product currentProduct;
    public static int init = 0;
    public static int generatedPartID = 1;
    public static int generatedProductID = 100;
    public boolean alertAnswer = false;

    @FXML
    public static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    @FXML
    public static ObservableList<Product> productInventory = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Part> searchPartInventory = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Product> searchProductInventory = FXCollections.observableArrayList();

    //PARTS
    @FXML
    private TextField searchPartInput;

    //Parts TableView
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

    //Buttons
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button searchPartButton;


    //PRODUCTS

    @FXML
    private TextField searchProductInput;

    //Products TableView
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product,Integer> productIDColumn;
    @FXML
    private TableColumn<Product,String> productNameColumn;
    @FXML
    private TableColumn<Product,Integer> productStockColumn;
    @FXML
    private TableColumn<Product,Double> productPriceColumn;

    //Buttons
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button searchProductButton;



    private void addInitialData() {
        //InHouse Parts
        Part a1 = new InHouse(generatedPartID++,"Part A1",4.99,5,1,3,11);
        Part a2 = new InHouse(generatedPartID++,"Part A2",9.99,8,2,4,12);
        Part b1 = new InHouse(generatedPartID++,"Part B1",7.99,10,3,7,13);
        Part b2 = new InHouse(generatedPartID++,"Part B2",14.99,20,5,10,14);
        //Outsourced Parts
        Part c1 = new Outsourced(generatedPartID++,"Part Apple",9.99,30,10,15,"WGU1");
        Part c2 = new Outsourced(generatedPartID++,"Part Pear",19.99,40,15,20,"WGU2");
        Part d1 = new Outsourced(generatedPartID++,"Part Orange",5.99,20,5,8,"WGU3");
        Part d2 = new Outsourced(generatedPartID++,"Part Mango",5.00,10,1,6,"WGU4");
        //Adding Parts
        data.addPart(a1);
        data.addPart(a2);
        data.addPart(b1);
        data.addPart(b2);
        data.addPart(c1);
        data.addPart(c2);
        data.addPart(d1);
        data.addPart(d2);
        //Products
        Product p1 = new Product(generatedProductID++,"Product Red",81.00,20,5,15);
        Product p2 = new Product(generatedProductID++,"Product Blue",82.00,30,10,20);
        Product p3 = new Product(generatedProductID++,"Product Black",83.00,40,15,25);
        Product p4 = new Product(generatedProductID++,"Product Gray",84.00,50,10,30);

        //Adding Associated Parts to Products
        p1.addAssociatedPart(a1);
        p1.addAssociatedPart(c1);
        p2.addAssociatedPart(a2);
        p2.addAssociatedPart(c2);
        p3.addAssociatedPart(b1);
        p3.addAssociatedPart(d1);
        p4.addAssociatedPart(b2);
        p4.addAssociatedPart(d2);
        //Adding Products
        data.addProduct(p1);
        data.addProduct(p2);
        data.addProduct(p3);
        data.addProduct(p4);

    }

    public void setCurrentData(Inventory data) {
        currentData = data;
    }

    public Inventory getCurrentData() {
        return this.currentData;
    }

    public void setCurrentPart(Part data) {
        currentPart = data;
    }

    public Part getCurrentPart() {
        return this.currentPart;
    }


    public void setCurrentProduct(Product data) {
        currentProduct = data;
    }

    public Product getCurrentProduct() {
        return this.currentProduct;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(init==0) {
            addInitialData();
            showInitialPartData();
            showInitialProductData();
        } else {
            showPassedPartData();
            showPassedProductData();
        }

    }

    //Populating With Initial Part Data
    private void showInitialPartData() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        partInventory.setAll(data.getAllParts());
        partsTable.setItems(partInventory);
        partsTable.refresh();
    }

    //Populating With Passed Part Data From The Other Controllers
    private void showPassedPartData() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        partInventory.setAll(currentData.getAllParts());
        partsTable.setItems(partInventory);
        partsTable.refresh();
    }

    //Populating Initial Product Data
    private void showInitialProductData() {
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productInventory.setAll(data.getAllProducts());
        productsTable.setItems(productInventory);
        productsTable.refresh();

    }

    //Populating With Passed Product Data From The Other Controllers
    private void showPassedProductData() {
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productInventory.setAll(currentData.getAllProducts());
        productsTable.setItems(productInventory);
        productsTable.refresh();

    }

    //Searching in Part Table
    @FXML
    private void searchPart(MouseEvent mouseEvent) {
        if(searchPartInput.getText().isEmpty()) {
            partsTable.setItems(partInventory);
            partsTable.refresh();
        } else {
            searchPartInventory.clear();
            String input = searchPartInput.getText();
            for(Part search: data.getAllParts()) {
                if(search.getPartName().toLowerCase().contains(input.toLowerCase())) {
                    searchPartInventory.add(search);
                }
            }
            partsTable.setItems(searchPartInventory);
            partsTable.refresh();

        }

    }

    //Searching in Product Table
    @FXML
    private void searchProduct(MouseEvent mouseEvent) {
        if(searchProductInput.getText().isEmpty()) {
            productsTable.setItems(productInventory);
            productsTable.refresh();
        } else {
            searchProductInventory.clear();
            String input = searchProductInput.getText();
            for(Product search: data.getAllProducts()) {
                if(search.getProductName().toLowerCase().contains(input.toLowerCase())) {
                    searchProductInventory.add(search);
                }
            }
            productsTable.setItems(searchProductInventory);
            productsTable.refresh();
        }

    }

    //Delete from Part Table
    @FXML
    private void deletePartSelected(MouseEvent mouseEvent) {
        if(partsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else {
            cancelDelete();
            if(alertAnswer==true) {
                Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
                partsTable.getItems().remove(selectedPart);
                data.deletePart(selectedPart);
                partInventory.setAll(data.getAllParts());
                partsTable.setItems(partInventory);
                partsTable.refresh();
            }
        }

    }

    //Delete from Product Table
    @FXML
    private void deleteProductSelected(MouseEvent mouseEvent) {
        if(productsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else {
            cancelDelete();
            if(alertAnswer==true) {
                Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
                productsTable.getItems().remove(selectedProduct);
                data.deleteProduct(selectedProduct);
                productInventory.setAll(data.getAllProducts());
                productsTable.setItems(productInventory);
                productsTable.refresh();
            }
        }

    }

    //Changing to the Add Part Screen
    @FXML
    private void changeToAddPart(MouseEvent mouseEvent) throws IOException {
        setCurrentData(data);
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        addPartWindow.setScene(addPartScene);
        addPartWindow.show();
    }

    //Changing to the Modify Part Screen
    @FXML
    private void changeToModifyPart(MouseEvent mouseEvent) throws IOException {
        setCurrentData(data);
        if(partsTable.getSelectionModel().isEmpty()) {
            //Do nothing
        } else {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            setCurrentPart(selectedPart);
            index = data.getAllParts().indexOf(selectedPart);

            //setCurrentData(data);
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage modifyPartWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            modifyPartWindow.setScene(modifyPartScene);
            modifyPartWindow.show();
        }
    }

    //Changing to the Add Product Screen
    @FXML
    private void changeToAddProduct(MouseEvent mouseEvent) throws IOException {
        setCurrentData(data);
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        addProductWindow.setScene(addProductScene);
        addProductWindow.show();
    }

    //Changing to the Modify Product Screen
    @FXML
    private void changeToModifyProduct(MouseEvent mouseEvent) throws IOException {
        setCurrentData(data);
        if(productsTable.getSelectionModel().isEmpty()) {
            //Do Nothing
        } else {
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            setCurrentProduct(selectedProduct);
            //index = data.getAllParts().indexOf(selectedProduct);

            Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modifyProductScene = new Scene(modifyProductParent);
            Stage modifyProductWindow = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            modifyProductWindow.setScene(modifyProductScene);
            modifyProductWindow.show();

        }

    }


    //Exit Button
    @FXML
    private void exitApplication(MouseEvent mouseEvent) {
        Platform.exit();
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
