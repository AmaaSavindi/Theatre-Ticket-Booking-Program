import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ShoppingCartTableModel extends AbstractTableModel {
    private List<Product> productList;
    private Shopping_Cart shoppingCart;
    private final String[] columnNames = {"Product", "Quantity", "Price"};

    public ShoppingCartTableModel(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < productList.size()) {
            // For cart items
            Product product = productList.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return getProductInfo(product);
                case 1:
                    return product.getQuantity(); // Display the quantity
                case 2:
                    return product.getPrice() * product.getQuantity();
                default:
                    return null;

            }

        } else {
            // For summary rows
            double totalPrice = Shopping_Cart.calculateTotalCost(productList);
            double discount = Shopping_Cart.calculateDiscount(productList);
            double finalTotal = totalPrice - discount;


            switch (columnIndex) {
                case 0:
                    return "Total";
                case 1:
                    return "";
                case 2:
                    return finalTotal;
                default:
                    return null;

            }
        }
    }

    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronicProduct = (Electronics) product;
            return String.format("ID: %s, Name: %s, Brand: %s, Warranty: %s",
                    electronicProduct.getProductID(), electronicProduct.getProductName(),
                    electronicProduct.getBrand(), electronicProduct.getWarrantyPeriod());
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return String.format("ID: %s, Name: %s, Size: %s, Color: %s",
                    clothingProduct.getProductID(), clothingProduct.getProductName(),
                    clothingProduct.getSize(), clothingProduct.getColor());
        } else {
            return "";
        }
    }

    @Override
    public String getColumnName(int column) {

        // Return column names for the table headers
        switch (column) {
            case 0:
                return "Product";
            case 1:
                return "Quantity";
            case 2:
                return "Price";
            default:
                return "";

        }
    }
}
