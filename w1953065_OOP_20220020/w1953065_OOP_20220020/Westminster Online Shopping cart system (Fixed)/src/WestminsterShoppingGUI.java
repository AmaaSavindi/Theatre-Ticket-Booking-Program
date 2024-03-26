import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class WestminsterShoppingGUI extends JFrame {
    private List<Product> productList;
    private Shopping_Cart shoppingCart;

    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton shoppingCartButton;

    //Main constructor : Applies styling/ Configures main frame/ Reads products from file/ Updates the product table/ Add event listeners
    public WestminsterShoppingGUI() {
        productList = new ArrayList<>();
        shoppingCart = new Shopping_Cart();

        initializeComponents();
        applyStyling();
        configureMainFrame();

        readProductsFromFile("products.dat");
        updateProductTable();
        addEventListeners();
    }

    //Initializing the GUI components
    private void initializeComponents() {
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTable = new JTable();
        productDetailsTextArea = new JTextArea(10, 30);
        addToCartButton = new JButton("Add to Cart");
        shoppingCartButton = new JButton("Shopping Cart");
    }

    //Styles GUI components
    private void applyStyling() {
        Font boldFont = new Font("Arial", Font.BOLD, 14);

        productTypeComboBox.setFont(boldFont);
        shoppingCartButton.setFont(boldFont);
        addToCartButton.setFont(boldFont);

        productDetailsTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        productDetailsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        productTable.getTableHeader().setFont(boldFont);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));

        addToCartButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        shoppingCartButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    //Main frame configuration
    private void configureMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("Westminster Shopping Centre");
        setLayout(new BorderLayout());

        createTopPanel();
        createMainPanel();
        createCenterPanel();
        createDetailsPanel();

        setVisible(true);
    }

    //Panels creation
    private void createTopPanel() {

        JPanel topPanel = new JPanel(new BorderLayout());
        // Combo-box in the middle of the top panel
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboPanel.add(new JLabel("Product Type: "));
        comboPanel.add(productTypeComboBox);

        // Shopping Cart button next to combo-box on right
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.add(shoppingCartButton);

        topPanel.add(comboPanel, BorderLayout.CENTER);
        topPanel.add(cartPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);


    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        add(mainPanel);
    }

    private void createCenterPanel() {
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productTable.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        //Changed 100th line from shoppingCartButton
        centerPanel.add(addToCartButton, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsTextArea);
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);
        detailsPanel.add(addToCartButton, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.SOUTH);
    }

    //Event listeners - Combo box/ Product table/ Button-clicks
    private void addEventListeners() {

        productTypeComboBox.addActionListener(e -> updateProductTable());
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                displayProductDetails(productList.get(selectedRow));
            }
        });
        addToCartButton.addActionListener(e -> addToShoppingCart());
        shoppingCartButton.addActionListener(e -> showShoppingCart());

    }

    private void addToShoppingCart() {

        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Product selectedProduct = productList.get(selectedRow);

            // Check if the product is already in the shopping cart
            int existingIndex = shoppingCart.indexOfProduct(selectedProduct);
            if (existingIndex >= 0) {

                shoppingCart.incrementQuantity(existingIndex);
                shoppingCart.updateTotalPrice(existingIndex);

            } else {
                // If the product is not in the cart, add it
                selectedProduct.setQuantity(1);
                shoppingCart.addProduct(selectedProduct);
            }

            JOptionPane.showMessageDialog(this, "Product added to cart.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product.");
        }

        updateDiscounts();
        updateProductTable();
    }

    private void updateProductTable() {
        String selectedType = (String) productTypeComboBox.getSelectedItem();
        List<Product> filteredList = (selectedType.equals("All")) ? productList : filterProductsByType(selectedType);

        ProductTableModel tableModel = new ProductTableModel(filteredList);
        productTable.setModel(tableModel);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                displayProductDetails(productList.get(selectedRow));
            }
        });
    }

    private void showShoppingCart() {
        JFrame cartFrame = new JFrame("Shopping Cart");
        configureCartFrame(cartFrame);
        cartFrame.setVisible(true);
    }

    private void configureCartFrame(JFrame cartFrame) {
        cartFrame.setSize(600, 400);
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable cartTable = new JTable();
        cartTable.setRowHeight(30);

        ShoppingCartTableModel cartTableModel = new ShoppingCartTableModel(shoppingCart.getProductList());
        cartTable.setModel(cartTableModel);

        JPanel totalPricePanel = new JPanel();
        totalPricePanel.add(new JLabel(String.format("Final Total Price: $%.2f", Shopping_Cart.calculateTotalCost(shoppingCart.getProductList()))));

        JTextArea discountsTextArea = new JTextArea(5, 40);
        JScrollPane discountsScrollPane = new JScrollPane(discountsTextArea);

        configureCartFrameStyling(cartTable, discountsTextArea, totalPricePanel, cartFrame);
    }

    private void configureCartFrameStyling(JTable cartTable, JTextArea discountsTextArea, JPanel totalPricePanel, JFrame cartFrame) {
        Font boldFont = new Font("Arial", Font.BOLD, 14);

        cartTable.getTableHeader().setFont(boldFont);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));

        discountsTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        discountsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        totalPricePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        totalPricePanel.setFont(new Font("Arial", Font.PLAIN, 16));

        cartFrame.setLayout(new BorderLayout());
        cartFrame.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(totalPricePanel, BorderLayout.NORTH);
        cartFrame.add(southPanel, BorderLayout.SOUTH);
    }

    private void updateDiscounts() {
        double firstPurchaseDiscount = Shopping_Cart.firstPurchaseDiscountCalculate(shoppingCart.getProductList());
        double categoryDiscount = Shopping_Cart.categoryDiscountCalculate(shoppingCart.getProductList());

        JTextArea discountsTextArea = new JTextArea(5, 40);
        JScrollPane discountsScrollPane = new JScrollPane(discountsTextArea);
    }

    private List<Product> filterProductsByType(String type) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if ((type.equals("Electronics") && product instanceof Electronics) ||
                    (type.equals("Clothes") && product instanceof Clothing)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }

    private void displayProductDetails(Product selectedProduct) {
        StringBuilder detailsBuilder = new StringBuilder();
        detailsBuilder.append("Product ID: ").append(selectedProduct.getProductID()).append("\n");
        detailsBuilder.append("Product Name: ").append(selectedProduct.getProductName()).append("\n");
        detailsBuilder.append("Available Items: ").append(selectedProduct.getAvailableItems()).append("\n");
        detailsBuilder.append("Price: ").append(selectedProduct.getPrice()).append("\n");

        if (selectedProduct instanceof Electronics) {
            Electronics electronicProduct = (Electronics) selectedProduct;
            detailsBuilder.append("Brand: ").append(electronicProduct.getBrand()).append("\n");
            detailsBuilder.append("Warranty Period: ").append(electronicProduct.getWarrantyPeriod()).append("\n");
        } else if (selectedProduct instanceof Clothing) {
            Clothing clothingProduct = (Clothing) selectedProduct;
            detailsBuilder.append("Size: ").append(clothingProduct.getSize()).append("\n");
            detailsBuilder.append("Color: ").append(clothingProduct.getColor()).append("\n");
        }

        productDetailsTextArea.setText(detailsBuilder.toString());
    }

    private void readProductsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                productList = (List<Product>) obj;
                System.out.println("Products loaded from file successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WestminsterShoppingGUI());
    }
}