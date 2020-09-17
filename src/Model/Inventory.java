package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;


    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    // ADD
    public void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }


    public void addProduct(Product newProduct) {
        if (newProduct != null) {
            this.allProducts.add(newProduct);
        }
    }


    // DELETE
    public boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;

    }

    public boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }


    //UPDATE
    public void updatePart (int index, Part selectedPart) {
     allParts.set(index,selectedPart);

    }

    public void updateProduct (int index, Product newProduct) {
    allProducts.set(index,newProduct);
    }


    //LOOKUP

    public Part lookupPart(String partName) {
        if (allParts.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartName() == partName) {
                    return allParts.get((i));
                }
            }
        }
        return null;
    }

    public Product lookupProduct(String productName) {
        if (allProducts.isEmpty()) {
            return null;
        } else {
            for (int i  = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductName() == productName) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }


    //GET
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
