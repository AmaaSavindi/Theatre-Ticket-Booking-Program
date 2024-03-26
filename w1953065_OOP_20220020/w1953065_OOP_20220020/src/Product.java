import java.io.Serializable;

public abstract class Product implements Serializable {

    //Instance/Object Variables
    //Common set of object variables for every object, which is made related to this class.
    private String productID;
    //Since it's a representation of Letters + Numbers instead of using "int", used String
    private String productName;
    private int availableItems;
    private double price;

    private int quantity; // New field for quantity

    private double totalPrice;

    //Constructors for initialising the objects which are made relating to this class
    public Product(String productID, String productName, int availableItems, double price){
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
        this.quantity = 0; // Initialize quantity to 0
    }

    //No-Argument constructor (DEFAULT VALUES)
    public Product() {
        this("", "", 0, 0.0);
    }

    //Related setter methods
    public void setProductID(String productID){
        this.productID = productID;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setAvailableItems(int availableItems){
        this.availableItems = availableItems;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }




    //Related Getter methods
    public String getProductID(){
        return productID;
    }


    public String getProductName(){
        return productName;
    }

    public int getAvailableItems(){
        return availableItems;
    }

    public double getPrice(){
        return price;
    }

    // Getter method for quantity
    public int getQuantity() {
        return quantity;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    private static final long serialVersionUID = 1L;


}
