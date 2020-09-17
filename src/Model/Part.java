package Model;

public abstract class Part {

    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int min;
    private int max;

    // Constructor

    public Part(int partID, String partName, double partPrice, int partStock, int min, int max) {
        setPartID(partID);
        setPartName(partName);
        setPartPrice(partPrice);
        setPartStock(partStock);
        setMin(min);
        setMax(max);
    }


    // GETTERS AND SETTERS

    public int getPartID() {
        return this.partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPartPrice() {
        return this.partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    public int getPartStock() {
        return this.partStock;
    }

    public void setPartStock(int partStock) {
        this.partStock = partStock;
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

}
