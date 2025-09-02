import javax.swing.*;
import java.awt.*;

public class AddBook extends JPanel {

    private JTextField titleField, publisherField, authorField, quantityField, priceField, categoryField;
    private JComboBox<String> genreComboBox;

    public AddBook() {
        setBackground(new Color(238, 238, 238));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 50, 10, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel centerPanel = createPanelWithHeading("Book Specifications");
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

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        formPanel.setBackground(new Color(238, 238, 238));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50,0,30,0));

        // Left section
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 40));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,5));
        leftPanel.setOpaque(false);


        JLabel bookId = new JLabel("0");
        bookId.setBackground(new Color(238,238,238));
        bookId.setForeground(new Color(105, 117, 101));
        bookId.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        titleField = new JTextField();
        publisherField = new JTextField();
        authorField = new JTextField();



        leftPanel.add(createFieldPanel("Book Id:", bookId));
        leftPanel.add(createFieldPanel("Title:", titleField));
        leftPanel.add(createFieldPanel("Publisher:", publisherField));
        leftPanel.add(createFieldPanel("Author:", authorField));

        quantityField = new IntegerTextField(5);
        priceField = new IntegerTextField(10);
        categoryField = new JTextField();



        // Right section
        JPanel rightPanel = new JPanel(new GridLayout(4, 1, 10, 40));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,10));
        rightPanel.setOpaque(false);
        rightPanel.add(createFieldPanel("Quantity:", quantityField));
        rightPanel.add(createFieldPanel("Price:", priceField));
        rightPanel.add(createFieldPanel("Category:", categoryField));

        genreComboBox = getStringJComboBox();
        genreComboBox.setFont(new Font("Poppins", Font.BOLD, 15));
        genreComboBox.setBackground(new Color(238,238,238));
        genreComboBox.setForeground(new Color(105, 117, 101));
        genreComboBox.setFocusable(false);
        genreComboBox.setUI(new CustomComboBoxUI()); // Apply custom UI
        genreComboBox.setRenderer(new CustomComboBoxRenderer());
        genreComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

//        JComboBox<String> cbGenre = getStringJComboBox();
        styleComboBox(genreComboBox);

        rightPanel.add(createFieldPanel("Genre:", genreComboBox));

        formPanel.add(leftPanel);
        formPanel.add(rightPanel);
        outerPanel.add(formPanel, BorderLayout.CENTER);

        return outerPanel;
    }

    private static JComboBox<String> getStringJComboBox() {
        String[] genre =
                {
                "Drama",
                "Sci-Fi", "Mystery,Adventure\n" ,
                "Adventure\n" ,
                "Self Improvement",
                "Children\n" ,
                "Coming-of-age\n" ,
                "Crime Fiction\n" ,
                "Drama\n" ,
                "Epic\n" ,
                "Family Drama\n" ,
                "Fantasy\n" ,
                "Feminist Fiction\n" ,
                "Gothic\n" ,
                "Historical\n" ,
                "Horror\n" ,
                "Literary Fiction\n" ,
                "Magical Realism\n" ,
                "Political\n" ,
                "Psychological\n" ,
                "Psychological Thriller\n" ,
                "Romance\n" ,
                "Romantic Drama\n" ,
                "Satire\n" ,
                "Space Exploration\n" ,
                "Spy Fiction\n" ,
                "Survival Fiction\n" ,
                "Thriller\n" ,
                "Time Travel Fiction\n" ,
                "Young Adult\n"
                };

        JComboBox<String> cbGenre = new JComboBox<>(genre);
        cbGenre.setFont(new Font("Poppins", Font.BOLD, 15));
        cbGenre.setBackground(new Color(236, 223, 204));
        cbGenre.setForeground(new Color(105, 117, 101));
        cbGenre.setFocusable(false);
        cbGenre.setUI(new CustomComboBoxUI()); // Apply custom UI
        cbGenre.setRenderer(new CustomComboBoxRenderer());
        cbGenre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        return cbGenre;
    }

    private JPanel createFieldPanel(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Poppins", Font.BOLD, 18));
        label.setForeground(new Color(24, 28, 20));

        inputComponent.setFont(new Font("Poppins", Font.BOLD, 16));
        inputComponent.setBackground(new Color(236, 223, 204));
        inputComponent.setPreferredSize(new Dimension(0, 35));

        if (inputComponent instanceof JTextField) {
            styleTextField((JTextField) inputComponent);
        }

//        if (inputComponent instanceof JComboBox) {
//            ((JComboBox<?>) inputComponent).setBackground(Color.WHITE);
//
//        }

        panel.add(label, BorderLayout.NORTH);
        panel.add(inputComponent, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);

        JButton clearButton = new JButton("Clear Fields");
        styleButton(clearButton);

        JButton addButton = new JButton("Add Book");
        styleButton(addButton);

        clearButton.addActionListener(e -> {
            clearFields();
        });

        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String publisher = publisherField.getText().trim();
            String author = authorField.getText().trim();
            String quantityStr = quantityField.getText().trim();
            String priceStr = priceField.getText().trim();
            String category = categoryField.getText().trim();
            String genre = genreComboBox.getSelectedItem().toString();

            if (title.isEmpty() || publisher.isEmpty() || author.isEmpty() ||
                    quantityStr.isEmpty() || priceStr.isEmpty() || category.isEmpty() || genre.isEmpty()) {
                CustomOptionPane.showErrorDialog( null,"All fields are required!");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                boolean success = DatabaseHelper.insertBook(title, publisher, author, quantity, price, category, genre);
                if (success) {
                    CustomOptionPane.showMsgDialog(this, "Book added successfully!","Success");
                    clearFields();
                } else {
                    CustomOptionPane.showErrorDialog( null,"Failed to add Book!");
                }

            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "Quantity must be integer and Price must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
                CustomOptionPane.showErrorDialog( null,"Quantity must be integer and Price must be numeric.");
            }
        });


        buttonPanel.add(clearButton);
        buttonPanel.add(addButton);

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
//        textField.setBackground(new Color(236, 223, 204));
        textField.setForeground(new Color(105, 117, 101));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setPreferredSize(new Dimension(0, 40));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(24, 28, 20));
        comboBox.setFont(new Font("Poppins", Font.PLAIN, 14));
        comboBox.setFocusable(false);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 61, 55), 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
    }

    private void clearFields() {
        titleField.setText("");
        publisherField.setText("");
        authorField.setText("");
        quantityField.setText("0");
        priceField.setText("0");
        categoryField.setText("");
        genreComboBox.setSelectedIndex(0);
    }


}
