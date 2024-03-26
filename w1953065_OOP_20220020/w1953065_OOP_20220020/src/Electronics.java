import java.io.Serializable;

public class Electronics extends Product implements Serializable {

    // Instance Variables
    private String brand;
    private int warrantyPeriod;

    // Constructors
    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        //Inherits from the parent class using SUPER.
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // No-argument constructor (DEFAULT)
    public Electronics() {
        super("", "", 0, 0.0);  // Set default values or adjust accordingly
        this.brand = "";
        this.warrantyPeriod = 0;
    }

    // Setter methods
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter methods
    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    //Add serialVersionUID for version compatibility
    //Having a fixed serialVersionUID for deserialization when class structure is changed
    private static final long serialVersionUID = 3495791101321526828L;
}
