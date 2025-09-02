import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Billing extends JPanel {

    private JTextField titleField, authorField, quantityField, priceField, genreField, customerField;
    private JComboBox<String> bookId,modeOfPay;

    public Billing() {
        setBackground(new Color(238, 238, 238));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 50, 10, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel centerPanel = createPanelWithHeading("Billing Section");
        centerPanel.setPreferredSize(new Dimension(900, 500));
        add(centerPanel, gbc);

        // Buttons panel
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 50, 30, 50);
        gbc.weighty = 0;
        add(createButtonPanel(), gbc);
    }

    private JPanel createPanelWithHeading(String heading) {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(new Color(238, 238, 238));
        outerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel headingLabel = new JLabel(heading, SwingConstants.CENTER);
        headingLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        headingLabel.setForeground(new Color(24, 28, 20));
        outerPanel.add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        formPanel.setBackground(new Color(238, 238, 238));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));

        // Left section
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,5));
        leftPanel.setOpaque(false);


        bookId = new JComboBox<>();
        bookId.setBackground(new Color(238,238,238));
        bookId.setForeground(new Color(105, 117, 101));
        bookId.setUI(new CustomComboBoxUI());
        bookId.setFocusable(false);
        bookId.setUI(new CustomComboBoxUI()); // Apply custom UI
        bookId.setRenderer(new CustomComboBoxRenderer());
        bookId.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        loadBookIdsIntoComboBox();

        // ActionListener for bookId JComboBox
        bookId.addActionListener(e -> {
            String selectedId = (String) bookId.getSelectedItem();
            if (selectedId != null && !selectedId.isEmpty()) {
                try {
                    ResultSet rs = DatabaseHelper.getBookById(selectedId);
                    if (rs.next()) {
                        titleField.setText(rs.getString("name"));
                        authorField.setText(rs.getString("author"));
                        quantityField.setText(String.valueOf(rs.getInt("quantity"))); // Fetch and set quantity
                        priceField.setText(rs.getString("price"));
                        genreField.setText(rs.getString("genre"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        // Create and add pairs of Label: TextField
        leftPanel.add(createFieldPanel("Book Id:", bookId));
        leftPanel.add(Box.createVerticalStrut(3)); // Add some vertical space between pairs

        titleField = new JTextField();
        titleField.setEditable(false);
        titleField.setFocusable(false);
        leftPanel.add(createFieldPanel("Title:", titleField));

        leftPanel.add(Box.createVerticalStrut(3));

        authorField = new JTextField();
        authorField.setEditable(false);
        authorField.setFocusable(false);
        leftPanel.add(createFieldPanel("Author:", authorField));

        leftPanel.add(Box.createVerticalStrut(3));

        quantityField = new IntegerTextField(5);
        quantityField.setEditable(true);
        quantityField.setFocusable(true);
        leftPanel.add(createFieldPanel("Quantity:", quantityField));

        leftPanel.add(Box.createVerticalStrut(3));

        priceField = new IntegerTextField(10);
        priceField.setEditable(false);
        priceField.setFocusable(false);
        leftPanel.add(createFieldPanel("Price:", priceField));

        leftPanel.add(Box.createVerticalStrut(3));

        genreField = new JTextField();
        genreField.setEditable(false);
        genreField.setFocusable(false);
        leftPanel.add(createFieldPanel("Genre:", genreField));

        leftPanel.add(Box.createVerticalStrut(3));

        customerField = new JTextField();  // assign to the class variable
        leftPanel.add(createFieldPanel("Customer Name:", customerField));

        leftPanel.add(Box.createVerticalStrut(3));

        String[] mop = {"Cash","UPI","Card","Cheque"};
        modeOfPay = new JComboBox(mop);
        modeOfPay.setBackground(new Color(238,238,238));
        modeOfPay.setForeground(new Color(105, 117, 101));
        modeOfPay.setUI(new CustomComboBoxUI());
        modeOfPay.setFocusable(false);
        modeOfPay.setUI(new CustomComboBoxUI()); // Apply custom UI
        modeOfPay.setRenderer(new CustomComboBoxRenderer());
        modeOfPay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        styleComboBox(modeOfPay);
        leftPanel.add(createFieldPanel("Mode of Payment:",modeOfPay));

        // Right section with FlowLayout for better control of space
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));  // Use BoxLayout for vertical stacking
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
//        rightPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(24,28,20))
//                ,BorderFactory.createEmptyBorder(0,5,1,10)));
        rightPanel.setOpaque(false);

        // Create and add the search bar with correct size constraints
        JTextField searchField = new JTextField("Search...",SwingConstants.TOP);
        searchField.setFont(new Font("Poppins", Font.BOLD, 14));
        searchField.setForeground(new Color(105, 117, 101));
        searchField.setBackground(new Color(236, 223, 204));
        searchField.setPreferredSize(new Dimension(250, 30));  // Adjusted size for the search bar
        searchField.setMaximumSize(new Dimension(250, 30));  // Ensure it doesn't expand too wide
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(new Color(60, 61, 55));
                }
            }

            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(new Color(105, 117, 101));
                }
            }
        });


        rightPanel.add(searchField);
        // Add some spacing between search bar and table
        rightPanel.add(Box.createVerticalStrut(10));  // Adds 10 pixels of vertical space

        // Create and add the table below the search bar
        String[] columnNames = {"Book ID", "Name", "Author", "Qty.", "Price"};
        Object[][] data = {
                {"B001", "Book 1", "Author A", 10, 199.99},
                {"B002", "Book 2", "Author B", 5, 299.49},
                // add more if needed
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This makes all cells non-editable
            }
        };
        JTable table = new JTable(model);

        loadBooksIntoTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(750, 300));  // Set the width and height of the table
        JScrollPane scrollPane = new JScrollPane(table);  // Wrap the table in a JScrollPane to handle scrolling

        // Remove blue border/focus highlight
        table.setShowHorizontalLines(true);
        table.setBorder(BorderFactory.createLineBorder(new Color(24,28,20),2));
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionForeground(new Color(24,28,20));

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(24,28,20),1));
        header.setOpaque(false);
        header.setFocusable(false);
        header.setResizingAllowed(false);
        header.setBackground(new Color(60, 61, 55)); // your dark blue
        header.setForeground(new Color(236, 223, 204));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        // Hide scrollbars but keep scrolling functionality
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        rightPanel.add(scrollPane);

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);



        searchField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            public void changedUpdate(DocumentEvent e) {
                filter();
            }


            private void filter() {
                String text = searchField.getText().trim();
                if (text.length() == 0 || text.equals("Search...")) {
                    loadBooksIntoTable(model);
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        loadBooksIntoTable(model);

        // To avoid unnecessary space, ensure the rightPanel doesn't expand in width:
        rightPanel.setMaximumSize(new Dimension(750, 300));
        table.setFont(new Font("Poppins", Font.BOLD, 12));
        table.setRowHeight(30);
        table.setBackground(new Color(238,238,238)); // Soft beige background
        table.setGridColor(new Color(222,222,222)); // Grid border color
        table.setShowGrid(true);
        table.setFocusable(false);
        table.setSelectionBackground(new Color(105,117,101));
        table.setSelectionForeground(new Color(236, 223, 204));
        table.setBorder(BorderFactory.createLineBorder(new Color(60, 61, 55),2));

        table.getColumnModel().getColumn(0).setPreferredWidth(70);   // Book ID
        table.getColumnModel().getColumn(1).setPreferredWidth(240);  // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(210);  // Author
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Genre
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // Price

        // Center Aligning Column Content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Add the table to a JScrollPane for scrollable behavior

        formPanel.add(leftPanel);
        formPanel.add(rightPanel);
        outerPanel.add(formPanel, BorderLayout.CENTER);

        return outerPanel;
    }

    private JPanel createFieldPanel(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(1, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Poppins", Font.BOLD, 14));
        label.setForeground(new Color(24, 28, 20));

        inputComponent.setFont(new Font("Poppins", Font.BOLD, 16));
        inputComponent.setBackground(new Color(238,238,238));
        inputComponent.setForeground(new Color(105, 117, 101));
        inputComponent.setPreferredSize(new Dimension(0, 32));

        if (inputComponent instanceof JTextField) {
            styleTextField((JTextField) inputComponent);
        }

        //        if (inputComponent instanceof JComboBox) {
        //            ((JComboBox<?>) inputComponent).setBackground(Color.WHITE);
        //
        //        }

        panel.add(label, BorderLayout.NORTH);
        panel.add(inputComponent, BorderLayout.CENTER);

        printComponentHierarchy(this, "");

        return panel;

    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);

        JButton clearButton = new JButton("Clear Fields");
        styleButton(clearButton);

        clearButton.addActionListener(e -> {
                titleField.setText("");
                authorField.setText("");
                quantityField.setText("");
                priceField.setText("");
                genreField.setText("");
                customerField.setText("");
        });

        JButton billButton = new JButton("Bill");
        styleButton(billButton);

        billButton.addActionListener(e -> {
            handleBilling();
        });


        buttonPanel.add(clearButton);
        buttonPanel.add(billButton);

        return buttonPanel;
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(0, 45));
        button.setBackground(new Color(105, 117, 101));
        button.setForeground(new Color(236, 223, 204));
        button.setFont(new Font("Poppins", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Poppins", Font.BOLD, 16));
        textField.setPreferredSize(new Dimension(0, 30));
        textField.setForeground(new Color(105, 117, 101));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 1, true),
                BorderFactory.createEmptyBorder(2, 10, 2, 5)
        ));
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setPreferredSize(new Dimension(0, 40));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(105, 117, 101));
        comboBox.setFont(new Font("Poppins", Font.PLAIN, 14));
        comboBox.setFocusable(false);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 1, true),
                BorderFactory.createEmptyBorder(0, 5, 0, 0)
        ));
    }

    private void loadBooksIntoTable(DefaultTableModel model) {
        // Clear existing rows
        model.setRowCount(0);

        try (ResultSet rs = DatabaseHelper.getAllBooksForBilling()) {
            while (rs.next()) {
                String id = rs.getString("book_id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");

                model.addRow(new Object[]{id, name, author, quantity, price, genre});
            }
            rs.getStatement().getConnection().close(); // Close connection used in helper
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBookIdsIntoComboBox() {
        bookId.removeAllItems(); // Clear the combo box before adding items
        bookId.addItem(""); // Add an empty item for default selection

        try (ResultSet rs = DatabaseHelper.getAllBooksForBilling()) {
            while (rs.next()) {
                String id = rs.getString("book_id");
                bookId.addItem(id); // Add each book ID to the combo box
            }
            rs.getStatement().getConnection().close(); // Close connection used in helper
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleBilling() {
        String selectedId = (String) bookId.getSelectedItem();
        if (selectedId == null || selectedId.isEmpty()) {
            CustomOptionPane.showErrorDialog(null, "Please Select the Book Id.");
            return;
        }

        String quantityText = quantityField.getText().trim();
        if (quantityText.isEmpty() || !quantityText.matches("\\d+")) {
            CustomOptionPane.showErrorDialog(null, "Please enter valid quantity.");
            return;
        }

        int requestedQuantity = Integer.parseInt(quantityText);

        try {
            ResultSet rs = DatabaseHelper.getBookById(selectedId);
            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");

                // Check if there is enough stock
                if (requestedQuantity > currentQuantity) {
                    CustomOptionPane.showErrorDialog(null, "Insufficient stock. Available quantity: " + currentQuantity);
                    return;
                }

                // Subtract the requested quantity and update the database
                int updatedQuantity = currentQuantity - requestedQuantity;
                DatabaseHelper.updateBookQuantity(selectedId, updatedQuantity);

                // Insert a new bill into the bills table
                String title = rs.getString("name");
                String author = rs.getString("author");
                double unitPrice = rs.getDouble("price"); // Price per unit
                double totalPrice = unitPrice * requestedQuantity; // Calculate total price
                String genre = rs.getString("genre");
                String customerName = customerField.getText().trim();
                String paymentMode = (String) modeOfPay.getSelectedItem();

                if (customerName.isEmpty()) {
                    CustomOptionPane.showErrorDialog(null, "Please enter the customer's name.");
                    return;
                }

                // Insert the bill into the database
                DatabaseHelper.insertBill(selectedId, title, author, requestedQuantity, totalPrice, genre, customerName, paymentMode);

                // Provide feedback to the user
                CustomOptionPane.showMsgDialog(null, "Billing successful! Total price: " + totalPrice + "\nRemaining stock: " + updatedQuantity, "Success");

                // Clear fields after billing
                titleField.setText("");
                authorField.setText("");
                quantityField.setText("");
                priceField.setText("");
                genreField.setText("");
                customerField.setText("");
                bookId.setSelectedIndex(0);

                // Refresh table and combo box
                loadBooksIntoTable((DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) getComponent(0)).getComponent(1)).getViewport().getView()).getModel());
                loadBookIdsIntoComboBox();
            } else {
                CustomOptionPane.showErrorDialog(null, "Book not found in the database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            CustomOptionPane.showErrorDialog(null, "An error occurred while processing the bill.");
        }
    }

    private void printComponentHierarchy(Container container, String indent) {
        for (Component component : container.getComponents()) {
            System.out.println(indent + component.getClass().getName());
            if (component instanceof Container) {
                printComponentHierarchy((Container) component, indent + "  ");
            }
        }
    }

}
