public interface ShoppingManager {
        // Interface for ShoppingManager

        // Abstract methods for managing the product list

        void addProduct(Product product);

        void deleteProduct(String productID);

        void printProducts();

        void saveProducts();

        void loadProducts();
        // Implement the loadProducts method in WestminsterShoppingManager class

}
