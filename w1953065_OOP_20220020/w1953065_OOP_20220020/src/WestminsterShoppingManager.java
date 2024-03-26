import javax.swing.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    //Maximum number 50 is considered
    private static final int MAX_PRODUCTS = 50;

    //ArrayList to store products list
    private ArrayList<Product> productList;

    //Constructor
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        //Check the maximum products
        if (productList.size() < MAX_PRODUCTS) {
            productList.add(product);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Cannot add more products. Maximum limit reached.");
        }
    }

    @Override
    public void deleteProduct(String productID) {
        Optional<Product> productToRemove = productList.stream()
                .filter(product -> Objects.equals(productID, product.getProductID()))
                .findFirst();

        if (productToRemove.isPresent()) {
            Product removedProduct = productToRemove.get();
            productList.remove(removedProduct);
            System.out.println("Product ID: " + removedProduct.getProductID() +
                    "\nProduct Type: " + (removedProduct instanceof Electronics ? "Electronics" : "Clothing") +
                    "\nProduct deleted successfully.");

            //Displaying the remaining no.of products
            System.out.println("Remaining Total Number of Products: " + productList.size());
        } else {
            System.out.println("Product with ID " + productID + " not found.");
        }
    }

    @Override
    public void printProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products available.");
        } else {
            //Sorting Alphabetically through IDs
            Collections.sort(productList, Comparator.comparing(Product::getProductID));

            for (Product product : productList) {
                printProductDetails(product);
                System.out.println("-------------------------");
            }
        }
    }

    private void printProductDetails(Product product) {
        System.out.println("Product ID: " + product.getProductID());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Available Items: " + product.getAvailableItems());
        System.out.println("Price: " + product.getPrice());

        if (product instanceof Electronics) {
            Electronics electronicProduct = (Electronics) product;
            System.out.println("Brand: " + electronicProduct.getBrand());
            System.out.println("Warranty Period: " + electronicProduct.getWarrantyPeriod());
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            System.out.println("Size: " + clothingProduct.getSize());
            System.out.println("Color: " + clothingProduct.getColor());
        }
    }

    @Override
    public void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
            oos.writeObject(productList);
            System.out.println("Products saved to 'products.dat' successfully!");
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        //Giving the user to select the Console or GUI

        System.out.println("Choose an interface. Enter 1 for Console, 2 for GUI, 0 to Exit : ");
        int interfaceChoice = getValidIntInput(scanner, Arrays.asList(0, 1, 2));

        switch (interfaceChoice) {
            case 0:
                System.out.println("Exiting Westminster Shopping Manager! Have a nice day!");
                return;
            case 1:
                displayConsoleMenu(scanner);
                break;
            case 2:
                SwingUtilities.invokeLater(() -> new WestminsterShoppingGUI());
                break;
            default:
                System.out.println("Invalid choice! Exiting Westminster Shopping Manager!");
        }
    }

    private void displayConsoleMenu(Scanner scanner) {

        int choice;
        do {
            System.out.println("\n===== Westminster Shopping Manager Menu =====");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Save products to a file");
            System.out.println("5. Load products from a file");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addProductFromConsole(scanner);
                        break;
                    case 2:
                        deleteProductFromConsole(scanner);
                        break;
                    case 3:
                        printProducts();
                        break;
                    case 4:
                        saveProducts();
                        break;
                    case 5:
                        loadProducts();
                        break;
                    case 0:
                        System.out.println("Exiting Westminster Shopping Manager. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1;
            }
        } while (choice != 0);
    }

    private boolean isProductIDUnique(String productID) {
        return productList.stream()
                .map(Product::getProductID)
                .noneMatch(id -> Objects.equals(id, productID));
    }

    private void addProductFromConsole(Scanner scanner) {
        System.out.println("Enter the product type (1 for Electronics, 2 for Clothing): ");
        int productType = getValidIntInput(scanner, Arrays.asList(1, 2));

        if (productType == -1) {
            System.out.println("Invalid product type! Please enter 1 for Electronics or 2 for Clothing.");
            return;
        }

        System.out.println("Enter the product ID: ");
        String productID = getValidStringInput(scanner);

        if (!isProductIDUnique(productID)) {
            System.out.println("Error: Product ID already exists. Please enter a unique ID.");
            return;
        }

        System.out.println("Enter the product name: ");
        String productName = getValidStringInput(scanner);

        System.out.println("Enter the number of available items: ");
        int availableItems = getValidNonNegativeIntInput(scanner);

        System.out.println("Enter the price: ");
        double price = getValidNonNegativeDoubleInput(scanner);

        if (productType == 1) {
            System.out.println("Enter the brand: ");
            String brand = getValidStringInput(scanner);

            System.out.println("Enter the warranty period: ");
            int warrantyPeriod = getValidNonNegativeIntInput(scanner);

            Electronics electronicsProduct = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
            addProduct(electronicsProduct);

        } else if (productType == 2) {
            System.out.println("Enter the size: ");
            String size = getValidStringInput(scanner);

            System.out.println("Enter the color: ");
            String color = getValidStringInput(scanner);

            Clothing clothingProduct = new Clothing(productID, productName, availableItems, price, size, color);
            addProduct(clothingProduct);

        } else {
            System.out.println("Invalid product type! Please enter 1 for Electronics or 2 for Clothing.");
        }
    }

    private void deleteProductFromConsole(Scanner scanner) {
        System.out.println("Enter the product ID to delete: ");
        String productID = getValidStringInput(scanner);

        deleteProduct(productID);
    }

    private int getValidIntInput(Scanner scanner, List<Integer> validOptions) {
        int input = -1;
        boolean isValid = false;

        while (!isValid) {
            try {
                input = scanner.nextInt();
                // Consume the newline
                scanner.nextLine();

                if (validOptions.contains(input)) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                // Consume the invalid input
                scanner.nextLine();
            }
        }

        return input;
    }

    private String getValidStringInput(Scanner scanner) {
        String input = "";

        while (input.trim().isEmpty()) {
            input = scanner.nextLine().trim();

            if (input.trim().isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
            }
        }

        return input;
    }

    private int getValidNonNegativeIntInput(Scanner scanner) {
        int input = -1;

        while (input < 0) {
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (input < 0) {
                    System.out.println("Invalid input. Please enter a non-negative integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return input;
    }

    private double getValidNonNegativeDoubleInput(Scanner scanner) {
        double input = -1;

        while (input < 0) {
            try {
                input = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                if (input < 0) {
                    System.out.println("Invalid input. Please enter a non-negative number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return input;
    }

    @Override
    public void loadProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.dat"))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                productList = (ArrayList<Product>) obj;
                System.out.println("Products loaded from file successfully.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. No products loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.displayMenu();
    }
}
