package Model;

public class Outsourced extends Part{

    private String companyName;

    //CONSTRUCTOR
    public Outsourced(int partId, String partName, double partPrice, int partStock, int min, int max, String companyName) {
        super(partId,partName,partPrice,partStock,min,max);
        setCompanyName(companyName);
    }

    //GETTER AND SETTER FOR companyName
    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
