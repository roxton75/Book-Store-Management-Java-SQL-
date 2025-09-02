import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Inventory extends JPanel {
    private JTable table;

    public Inventory() {
        setBackground(new Color(238, 238, 238));
        setLayout(new BorderLayout()); // Use BorderLayout for main panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Align components horizontally
        gbc.insets = new Insets(0, 30, 20, 30); // Padding

        // Create content panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(238, 238, 238));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50)); // Padding for content

        // centerPanel and Right Panels with Heading
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        // Curved Border for the panel
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 61, 55), 2, true));
        centerPanel.setBackground(new Color(238,238,238));
        // Set a preferred height for the center panel
        centerPanel.setPreferredSize(new Dimension(getWidth(), 600)); // Adjust the height (600px) as needed



//        //Navbar For Title
//        JPanel navbar = new JPanel();
//        navbar.setLayout(new BorderLayout());
//        navbar.setSize(getWidth(),20);
//
//        // Heading label for the panel
//        JLabel headingLabel = new JLabel("Book Stock",SwingConstants.CENTER);
//        headingLabel.setFont(new Font("Poppins", Font.BOLD, 18));
//        headingLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//        headingLabel.setForeground(new Color(24, 28, 20));
//
//
//        navbar.add(headingLabel);
//        centerPanel.add(navbar);



        // Create the search bar (JTextField) below the heading and center it
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2,1,300,0)); // Center the components, reduce space
        searchPanel.setSize(getWidth(),40);
        searchPanel.setBackground(new Color(236, 223, 204));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0,400,30,400));

        JLabel headingLabel = new JLabel("Book Stock",SwingConstants.CENTER);
        headingLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        headingLabel.setForeground(new Color(105, 117, 101));

        JTextField searchField = new JTextField("Search",SwingConstants.CENTER);
        searchField.setPreferredSize(new Dimension(400, 30)); // Set size of the search bar
        searchField.setFont(new Font("Poppins", Font.PLAIN, 18));
        searchField.setBackground(new Color(60, 61, 55));
        searchField.setForeground(new Color(236, 223, 204));
        searchField.setCaretColor(new Color(238,238,238));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                new EmptyBorder(3, 5, 2, 5)  // left padding of 10px
        ));

        searchPanel.add(headingLabel);

        // Add FocusListener for Placeholder Effect
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().trim().equals("Search".trim())) {
                    searchField.setText("");
                    searchField.setForeground(new Color(236, 223, 204));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Search");
                    searchField.setForeground(new Color(236, 223, 204));
                }
            }
        });

        searchPanel.add(searchField);

        // Add the search panel to the centerPanel, below the heading
        centerPanel.add(searchPanel);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]
                        {"Book ID", "Name", "Publisher", "Author", "Qty.", "Price", "Category", "Genre"},0);
//            // Override isCellEditable to make all cells non-editable
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;  // Make all cells non-editable
//
        table = new JTable(model);
        // Make all cells non-editable by default
        table.setDefaultEditor(Object.class, null); // Disables editing for all cells



        // Table Style Customizations
        table.setFont(new Font("Poppins", Font.PLAIN, 12)); // Set font
        table.setForeground(new Color(24, 28, 20)); // Set text color
        table.setBackground(new Color(238,238,238)); // Set background color
        table.setGridColor(new Color(222, 222, 222)); // Set grid line color
        table.setShowGrid(true); // Disable grid lines for a cleaner look
        table.setFocusable(false);
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(105, 117, 101)); // Set background color for selected rows
        table.setSelectionForeground(new Color(236, 223, 204)); // Set foreground color for selected rows

        // Customize the Table Header
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 16)); // Header font
        table.getTableHeader().setBackground(new Color(60, 61, 55)); // Header background color
        table.getTableHeader().setForeground(new Color(236, 223, 204)); // Header text color
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(24,28,20),1));

        // Center Aligning Column Content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(30);      // Book ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200);     // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(180);     // Publisher
        table.getColumnModel().getColumn(3).setPreferredWidth(150);     // Author
        table.getColumnModel().getColumn(4).setPreferredWidth(30);      // Quantity
        table.getColumnModel().getColumn(5).setPreferredWidth(30);      // Category
        table.getColumnModel().getColumn(6).setPreferredWidth(100);     // Genre
        table.getColumnModel().getColumn(7).setPreferredWidth(100);     // Price


        // Add the table to a JScrollPane
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for the scroll pane
        // Hide scrollbars but keep scrolling functionality
        tableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        tableScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        centerPanel.add(tableScrollPane);






        // Positioning centerPanel Panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        contentPanel.add(centerPanel, gbc);

        // Buttons Panel at the Bottom
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // 2 buttons in a row with space
        buttonPanel.setOpaque(false);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(0, 45));
        deleteButton.setBackground(new Color(105, 117, 101));
        deleteButton.setForeground(new Color(236, 223, 204));
        deleteButton.setFont(new Font("Poppins", Font.BOLD, 16));
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 2,true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(0, 45));
        editButton.setFont(new Font("Poppins", Font.BOLD, 16));
        editButton.setBackground(new Color(105, 117, 101));
        editButton.setForeground(new Color(236, 223, 204));
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 2,true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(0, 45));
        saveButton.setBackground(new Color(105, 117, 101));
        saveButton.setForeground(new Color(236, 223, 204));
        saveButton.setFont(new Font("Poppins", Font.BOLD, 16));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 2,true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                CustomOptionPane.showErrorDialog( null,"Please select a row to delete.");
            } else {
                int bookId = (int) table.getValueAt(selectedRow, 0);
                try {
                    DatabaseHelper.deleteBook(bookId);
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                CustomOptionPane.showMsgDialog( null,"Data Deleted Successfully.","Deleted");
            }
        });

//        editButton.addActionListener(e -> {
//            int selectedRow = table.getSelectedRow();
//            if (selectedRow == -1) {
//                JOptionPane.showMessageDialog(null, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                // Handle editing logic
//            }
//        });

        // Edit button action listener
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row
                int selectedRow = table.getSelectedRow();

                // Check if a row is selected
                if (selectedRow == -1) {
                    CustomOptionPane.showMsgDialog(null, "Please select a row to edit.", "Select Row");
                } else {
                    // Make the selected row editable
                    makeRowEditable(selectedRow);
                }
            }
        });



        saveButton.addActionListener(e -> {
            // Handle saving logic
            // Get the selected row
            int selectedRow = table.getSelectedRow();

            // Check if a row is selected
            if (selectedRow == -1) {
                CustomOptionPane.showErrorDialog(null, "Please select a row to save.");
            } else {
                // Retrieve values from the table for the selected row
                int bookId = (int) table.getValueAt(selectedRow, 0);  // Book ID
                String name = (String) table.getValueAt(selectedRow, 1);  // Name
                String publisher = (String) table.getValueAt(selectedRow, 2);  // Publisher
                String author = (String) table.getValueAt(selectedRow, 3);  // Author
                int quantity = (int) table.getValueAt(selectedRow, 4);  // Quantity
                double price = (double) table.getValueAt(selectedRow, 5);  // Price
                String category = (String) table.getValueAt(selectedRow, 6);  // Category
                String genre = (String) table.getValueAt(selectedRow, 7);  // Genre

                try {
                    // Call the updateBooks method to save changes to the database
                    DatabaseHelper.updateBook(bookId, name, publisher, author, quantity, price, category, genre);

                    // Optionally, update the table to reflect changes (if needed)
                    fetchDataFromDatabase(model);  // Refresh table data

                    // Show success message
                    CustomOptionPane.showMsgDialog(null, "Data Saved Successfully", "Saved");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    CustomOptionPane.showErrorDialog(null, "Error updating the database.");
                }
            }

        });


        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        // Add buttons to content panel with GridBagLayout constraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across both columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(buttonPanel, gbc);

        // Finally, add the content panel to the main panel (which uses BorderLayout)
        add(contentPanel, BorderLayout.CENTER);

        fetchDataFromDatabase(model);

        // Real-time search functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    List<Object[]> books = DatabaseHelper.searchBooksData(searchField.getText());
                    updateTable(books, model);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Method to fetch data from the database and populate the JTable
    private void fetchDataFromDatabase(DefaultTableModel model) {
        try {
            // Get the books from the database as a List
            List<Object[]> books = DatabaseHelper.getBooksDB();

            // Clear existing data from the model
            model.setRowCount(0);

            // Loop through the list and add rows to the table
            for (Object[] book : books) {
                model.addRow(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Method to update the table with new data
    private void updateTable(List<Object[]> books, DefaultTableModel model) {
        model.setRowCount(0);  // Clear existing rows
        for (Object[] book : books) {
            model.addRow(book);  // Add new rows to the table
        }
    }

    // Method to make the selected row editable
    private void makeRowEditable(int selectedRow) {
        // Get the table model
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Iterate through each column of the selected row
        for (int col = 0; col < model.getColumnCount(); col++) {
            // Set the editor for the cells in the selected row (column-based)
            table.getColumnModel().getColumn(col).setCellEditor(new DefaultCellEditor(new JTextField()));
        }

//        // Optionally, change the background color to indicate that the row is in edit mode
//        table.setSelectionBackground(new Color(236,223,204)); // Change to a light color
//        table.setSelectionForeground(new Color(105,117,101)); // Change to a light color
//        table.setFont(new Font("Poppins",Font.PLAIN,12));
    }


}
