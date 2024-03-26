import java.io.Serializable;
//Implements Serializable interface to allow the objects to convert into a byte stream for storage/Transmission
public class Clothing extends Product implements Serializable {
    private String size;
    private String color;

    // Constructor
    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {

        //Inherits from the parent class using SUPER.
        super(productID, productName, availableItems, price);
        //Other variables,
        this.size = size;
        this.color = color;
    }

    // Setter methods
    public void setSize(String size) {
        this.size = size;
    }
    public void setColor(String color) {
        this.color = color;
    }

    //Getter methods
    public String getSize() {
        return size;
    }
    public String getColor() {
        return color;
    }


}
