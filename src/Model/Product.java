package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int min;
    private int max;


    //CONSTRUCTOR
    public Product(int productID, String productName, double productPrice, int productStock, int min, int max) {
        setProductID(productID);
        setProductName(productName);
        setProductPrice(productPrice);
        setProductStock(productStock);
        setMin(min);
        setMax(max);
    }

    //GETTERS AND SETTERS
    public int getProductID() {
        return this.productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return this.productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }



    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedPart) {
        for(int i=0; i < associatedParts.size(); i++) {
            if(associatedParts.get(i) == selectedPart) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        for(int i=0; i < associatedParts.size(); i++) {
            return associatedParts;
        }
        return null;
    }

}
