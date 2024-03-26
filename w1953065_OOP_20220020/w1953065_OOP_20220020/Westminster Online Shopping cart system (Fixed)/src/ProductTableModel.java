import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;

//Custom table for JTable
public class ProductTableModel extends AbstractTableModel {

    //List of product Objects
    private final List<Product> productList;

    //String representing column headers
    private final String[] columnNames = {"Product ID", "Product Name", "Available Items", "Price", "Info"};

    //Constructor
    //Initialize PRODUCTLIST taking product objects as parameter
    public ProductTableModel(List<Product> productList) {
        this.productList = productList != null ? productList : List.of(); // Ensure productList is not null
    }

    //ABSTRACTTABLEMODEL Methods to override
    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    //Used by JTable to render cells
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // Return the appropriate class for each column
        if (columnIndex == 0) {
            return Product.class;  // Assuming Product is the class of the "Product" column
        } else if (columnIndex == 1) {
            return Integer.class;  // Assuming "Available Items" is of type Integer
        } else if (columnIndex == 2) {
            return Double.class;   // Assuming "Price" is of type Double
        } else if (columnIndex == 3) {
            return String.class;   // Assuming "Info" is of type String
        } else if (columnIndex == 4) {
            return Integer.class;  // Assuming "Warranty Period" is of type Integer
        }
        return Object.class;
    }

    //Get values at specific rows and column in the table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = productList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return product.getProductID();
            case 1:
                return product.getProductName();
            case 2:
                return product.getAvailableItems();
            case 3:
                return product.getPrice();
                //Added case 4
            case 4:
                return getInfoColumnValue(product);
            default:
                return null;
        }
    }

    //Get the info column
    private String getInfoColumnValue(Product product) {
        if (product instanceof Electronics) {
            Electronics electronicProduct = (Electronics) product;
            return "Brand: " + electronicProduct.getBrand() + ", Warranty Period: " + electronicProduct.getWarrantyPeriod();
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return "Size: " + clothingProduct.getSize() + ", Color: " + clothingProduct.getColor();
        } else {
            return "";
        }
    }

    //Return the name of specified column
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Make all cells non-editable
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
