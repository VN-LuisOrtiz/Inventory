package Model;

public class InHouse extends Part{

    private int machineID;

    //CONSTRUCTOR
    public InHouse (int partID, String partName, double partPrice, int partStock, int min, int max, int machineID) {
        super(partID,partName,partPrice,partStock,min,max);
        setMachineID(machineID);
    }


    //GETTER AND SETTER FOR machineID
    public int getMachineID() {
        return this.machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }


}
