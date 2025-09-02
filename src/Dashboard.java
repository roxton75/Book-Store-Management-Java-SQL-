import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.List;

public class Dashboard extends JPanel {
    private JTextField searchField;
    private JTable bookTable;
    private final String placeholderText = "Search books...";
    private JLabel totalSalesLabel;

    public Dashboard() {
        setBackground(new Color(238, 238, 238));
        setLayout(new BorderLayout());

        UIManager.put("Label.font", new Font("Poppins", Font.BOLD, 16));
        UIManager.put("Button.font", new Font("Poppins", Font.BOLD, 16));
        // repeat for all components you use


        // Main Content Panel with Padding
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(50, 80, 40, 80)); // Padding

        // Info Boxes Panel (Row Layout)
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 3 columns, 20px gap
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(1400, 150));

        JLabel totalBooksLabel = new JLabel("Loading...");
        JLabel totalQuantityLabel = new JLabel("Loading...");
        JLabel totalSalesLabel = new JLabel("Loading...");

        infoPanel.add(createInfoBox("Total Books", totalBooksLabel));
        infoPanel.add(createInfoBox("Total Quantity", totalQuantityLabel));
        infoPanel.add(createInfoBox("Total Sales", totalSalesLabel));
        fetchTotals(totalBooksLabel, totalQuantityLabel,totalSalesLabel);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setMaximumSize(new Dimension(1200, 40));
        searchPanel.setOpaque(false);

        searchField = new JTextField(placeholderText);
        searchField.setFont(new Font("Poppins", Font.PLAIN, 18));
        searchField.setBackground(new Color(60, 61, 55));
        searchField.setForeground(new Color(236, 223, 204));
        searchField.setCaretColor(new Color(238,238,238));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                new EmptyBorder(1, 8, 0, 0)  // left padding of 10px
        ));


        // Add FocusListener for Placeholder Effect
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().trim().equals(placeholderText.trim())) {
                    searchField.setText("");
                    searchField.setForeground(new Color(236, 223, 204));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText(placeholderText);
                    searchField.setForeground(new Color(236, 223, 204));
                }
            }
        });


        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Poppins", Font.BOLD, 16));
        searchButton.setBackground(new Color(105, 117, 101));
        searchButton.setFocusPainted(false);
        searchButton.setForeground(new Color(236, 223, 204));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        searchButton.addActionListener(e -> {
            String input = searchField.getText().trim();
            if (input.equals(placeholderText.trim()) || input.isEmpty()) {
                loadBookDataFromDatabase(); // Reset view
                return;
            }

            try {
                double priceLimit = Double.parseDouble(input);
                searchBooksByPrice(priceLimit);
            } catch (NumberFormatException ex) {
                searchBooksByText(input);
            }
        });


        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);


        DefaultTableModel model = new DefaultTableModel(new Object[]{"Book ID", "Title", "Author", "Genre", "Price"}, 0) {
            // Override isCellEditable to make all cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };

        // Create the JTable with the non-editable model
        bookTable = new JTable(model);
        styleTable(bookTable);
        loadBookDataFromDatabase();


        bookTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // Book ID
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(230);  // Title
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(220);  // Author
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(150);  // Genre
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Price


        JScrollPane tableScroll = new JScrollPane(bookTable);
        tableScroll.setMaximumSize(new Dimension(1200, 300));
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(60, 61, 55), 2, true));

        // Hide scrollbars but keep scrolling functionality
        tableScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        tableScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));


        // Sold History Panel
        JPanel soldHistoryPanel = createInfoBox("Recently Sold", new JLabel("No Data"));
        soldHistoryPanel.setPreferredSize(new Dimension(1200, 100));

        // Adding Components to Main Panel
        contentPanel.add(infoPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(searchPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(tableScroll);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(soldHistoryPanel);

        add(contentPanel, BorderLayout.CENTER);

    }

    // Method to Apply Custom Styling to the Table
    private void styleTable(JTable table) {
        table.setFont(new Font("Poppins", Font.BOLD, 14));
        table.setRowHeight(30);
        table.setBackground(new Color(238,238,238)); // Soft beige background
        table.setGridColor(new Color(222,222,222)); // Grid border color
        table.setShowGrid(true);
        table.setFocusable(false);
        table.setSelectionBackground(new Color(105,117,101));
        table.setSelectionForeground(new Color(236, 223, 204));

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Poppins", Font.BOLD, 18));
        header.setForeground(new Color(236, 223, 204));
        header.setBackground(new Color(60, 61, 55)); // Deep Blue
        header.setBorder(BorderFactory.createLineBorder(new Color(60, 61, 55)));
        header.setFocusable(false);

        // Center Aligning Column Content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    private JPanel createInfoBox(String title, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        panel.setPreferredSize(new Dimension(350, 100));
        panel.setBackground(new Color(236, 223, 204));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        titleLabel.setForeground(new Color(24, 28, 20));

        valueLabel.setFont(new Font("Poppins", Font.BOLD, 30));  // Bigger & Bold
        valueLabel.setForeground(new Color(105, 117, 101));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Center

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private void loadBookDataFromDatabase() {
        try {
            List<Object[]> books = DatabaseHelper.getBooks(2);
            DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
            model.setRowCount(0);
            for (Object[] row : books) model.addRow(row);
        } catch (Exception e) {
            e.printStackTrace();
            CustomOptionPane.showMsgDialog(this, "Failed to load book data.", "Error");
        }
    }

    private void searchBooksByPrice(double price) {
        try {
            List<Object[]> books = DatabaseHelper.searchBooksByPrice(price);
            DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
            model.setRowCount(0);
            for (Object[] row : books) model.addRow(row);
        } catch (Exception e) {
            e.printStackTrace();
            CustomOptionPane.showMsgDialog(this, "Search by price failed.", "Error");
        }
    }

    private void searchBooksByText(String keyword) {
        try {
            List<Object[]> books = DatabaseHelper.searchBooks(keyword);
            DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
            model.setRowCount(0);
            for (Object[] row : books) model.addRow(row);
        } catch (Exception e) {
            e.printStackTrace();
            CustomOptionPane.showMsgDialog(this, "Search failed.", "Error");
        }
    }

    // Merged method to fetch total books, total quantity, and total sales from the database
    private void fetchTotals(JLabel totalBooksLabel, JLabel totalQuantityLabel, JLabel totalSalesLabel) {
        try {
            // Fetch total books and total quantity
            int totalBooks = DatabaseHelper.getTotalBooks();
            int totalQuantity = DatabaseHelper.getTotalQuantity();

            // Set values to the labels
            totalBooksLabel.setText(String.valueOf(totalBooks));
            totalQuantityLabel.setText(String.valueOf(totalQuantity));

            // Fetch total sales
            double totalSales = DatabaseHelper.getTotalSales();
            totalSalesLabel.setText(String.format("%.2f", totalSales));

        } catch (SQLException e) {
            e.printStackTrace();
            CustomOptionPane.showMsgDialog(this, "Failed to fetch totals from database.", "Error");
        }
    }






}
