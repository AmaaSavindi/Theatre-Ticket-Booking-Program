import java.util.ArrayList;
import java.util.List;
class Shopping_Cart  implements java.io.Serializable {
    private static List<Product> productList;

    //Constructor initializes the productList as an empty Array list
    public Shopping_Cart() {
        this.productList = new ArrayList<>();
    }

    // Method to add products to the cart
    public void addProduct(Product product) {   // Parameter = product (from Product class, object typically introduced as "product")

        int existingIndex = indexOfProduct(product);

        if (existingIndex >= 0) {
            // Product is already in the cart, update the quantity and price
            Product existingProduct = productList.get(existingIndex);
            existingProduct.setQuantity(existingProduct.getQuantity() + 1);
            updateTotalPrice(existingProduct);

        } else {
            // Product is not in the cart, set quantity to 1 and add it
            product.setQuantity(1);
            productList.add(product);

        }
        updateTotalPrice();
    }


    //Updating the price based on its quantity
    private void updateTotalPrice() {
        for (Product product : productList) {
            updateTotalPrice(product);
        }
    }


    //Finding the indexes through ProductIDs
    public int indexOfProduct(Product product) {

        for (int i = 0; i < productList.size(); i++) {
            Product existingProduct = productList.get(i);
            if (existingProduct.getProductID().equals(product.getProductID())) {
                return i;
            }
        }
        return -1;
    }

    private void updateTotalPrice(Product product) {
        double totalPrice = product.getPrice() * product.getQuantity();
        product.setTotalPrice(totalPrice);
    }


    public List<Product> getProductList() {
        return productList;
    }

    public double calculateFinalPrice() {
        // Calculate final price with discounts
        // (Additional logic for discounts as specified)
        return 0.0; // Placeholder, implement the actual calculation
    }

    // Method to calculate the total cost of items in the cart
    public static double calculateTotalCost(List<Product> cartItems) {
        double totalCost = 0;

        if (cartItems != null) {
            for (Product product : productList) { //This is for, FOR-EACH loop, and commonly used to iterate over collections such as LIST/ARRAYLIST.
                // Iterating over each element of the PRODUCT OBJECT, in the PRODUCTLIST.
                totalCost += product.getPrice()* product.getQuantity();  //totalCost = totalCost + product.getPrice()
            }
        }

        totalCost = categoryDiscountApply(totalCost);
        totalCost = firstPurchaseDiscountApply(totalCost);

        return totalCost;
    }


    private static double categoryDiscountApply(double totalCost) {
        // Apply 20% discount when the user buys at least three products of the same category
        long electronicsCount = productList.stream().filter(product -> product instanceof Electronics).count();
        long clothingCount = productList.size() - electronicsCount;

        if (electronicsCount >= 3 || clothingCount >= 3) {
            totalCost *= 0.8;

        }

        return totalCost;
    }

    static double categoryDiscountCalculate(List<Product> productList) {
        double totalPrice = calculateTotalCost(productList);

        long electronicsCount = productList.stream().filter(product -> product instanceof Electronics).count();
        long clothingCount = productList.size() - electronicsCount;

        if (electronicsCount >= 3 || clothingCount >= 3) {
            return 0.2; // 20% discount
        }

        return 0;
    }


    private static double firstPurchaseDiscountApply(double totalCost) {
        // Apply 10% discount for the very first purchase
        if (productList.size() > 0) {
            Product firstProduct = productList.get(0);
            if (firstProduct instanceof Electronics || firstProduct instanceof Clothing) {
                totalCost *= 0.9; // 10% discount
            }
        }

        return totalCost;
    }


    static double firstPurchaseDiscountCalculate(List<Product> productList) {
        if (!productList.isEmpty()) {
            Product firstProduct = productList.get(0);
            if (firstProduct instanceof Electronics || firstProduct instanceof Clothing) {
                return 0.1; // 10% discount
            }
        }

        return 0;
    }

    public static double calculateDiscount(List<Product> productList) {
        double discount = 0;

        // Calculate discount based on the product category
        long electronicsCount = productList.stream().filter(product -> product instanceof Electronics).count();
        long clothingCount = productList.stream().filter(product -> product instanceof Clothing).count();

        if (electronicsCount >= 3) {
            // Apply 20% discount for at least three electronics products
            discount += 0.2;
        }

        if (clothingCount >= 3) {
            // Apply 20% discount for at least three clothing products
            discount += 0.2;
        }

        // Apply 10% discount for the very first purchase
        if (productList.size() > 0) {
            Product firstProduct = productList.get(0);
            if (firstProduct instanceof Electronics || firstProduct instanceof Clothing) {
                discount += 0.1;
            }
        }

        return discount;
    }


    public void incrementQuantity(int index) {
        Product product = productList.get(index);
        product.setQuantity(product.getQuantity() + 1);
        updateTotalPrice(product);
    }

    public void updateTotalPrice(int index) {
        Product product = productList.get(index);
        updateTotalPrice(product);
    }


    @Override
    public String toString() {
        // Format the shopping cart details for display
        StringBuilder cartDetails = new StringBuilder("Shopping Cart:\n");
        for (Product item : productList) {
            cartDetails.append(item.toString()).append("\n");
        }
        cartDetails.append("Final Price: $").append(calculateFinalPrice());
        return cartDetails.toString();
    }
}
















