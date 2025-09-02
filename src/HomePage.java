import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame {
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final JLabel navbarTitle;

    public HomePage() {
        // Set up the title and basic frame properties
        setTitle("Book Store Management");
        setSize(1700, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

//        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Make the window undecorated (no title bar, borders, etc.)
        setResizable(true);
        setUndecorated(false);

        //FOR FULLSCREEN FEATURE
//        // Get the screen's GraphicsDevice
//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        // Set the frame to fullscreen mode
//        if (gd.isFullScreenSupported()) {
//            gd.setFullScreenWindow(this);
//        } else {
//            System.out.println("Fullscreen mode is not supported");
//            setVisible(true); // Fallback to normal mode if fullscreen is unavailable
//        }


        // Main container
        JPanel container = new JPanel(new BorderLayout());

        // Sidebar panel (Left)
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(24,28,20));
        sidePanel.setPreferredSize(new Dimension(275, getHeight()));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Admin Panel
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        adminPanel.setBackground(new Color(24,28,20));

        JLabel adminIcon = new JLabel(resizeIcon("resources/100px/admin.png", 100, 100)); // Adjusted icon size
        adminIcon.setBorder(BorderFactory.createEmptyBorder(20,10,0,10));
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("Poppins", Font.BOLD, 30));
        adminLabel.setForeground(new Color(236,223,204));

        adminPanel.add(adminIcon);
        adminPanel.add(adminLabel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 8));
        buttonPanel.setBackground(new Color(24,28,20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Button Details
        String[] buttons = {"Dashboard", "Inventory", "Add Book", "Billing"}; //"Orders", "Sales"
        String[] icons = {"resources/50px/home.png", "resources/50px/inventory.png", "resources/50px/addbook.png","resources/50px/estimate.png"}; //,"resources/50px/orders.png", "resources/50px/sales.png"
        int[][] iconSizes = {{32, 32}, {32, 32}, {32, 32}, {32, 32}};// Different sizes for each icon

        for (int i = 0; i < buttons.length; i++) {
            JButton btn = createSidebarButton(buttons[i], icons[i], iconSizes[i][0], iconSizes[i][1]);
            buttonPanel.add(btn);
            int index = i;
            btn.addActionListener(e ->
                    updatePage(buttons[index])); // Ensure correct page switching
        }


        // Logout Button (Styled Differently)
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Poppins", Font.BOLD, 18));
        logoutButton.setBackground(new Color(105,117,101)); // Red button
        logoutButton.setForeground(new Color(236,223,204));
        logoutButton.setFocusPainted(false);
        logoutButton.setBounds(120,140,350,40);
        logoutButton.setPreferredSize(new Dimension(280, 50));
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2,true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        logoutButton.setIcon(resizeIcon("resources/50px/logout.png", 30, 30)); // Custom logout icon size

        logoutButton.addActionListener(e ->{
            new LoginPage();
            dispose();
        });

        // Card Layout Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Navbar Panel
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setPreferredSize(new Dimension(getWidth(), 120));
        navbar.setBackground(new Color(105,117,101));
        navbar.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(24,28,20)));

        navbarTitle = new JLabel("DashBoard", SwingConstants.CENTER);
        navbarTitle.setFont(new Font("Poppins", Font.BOLD, 36));
        navbarTitle.setForeground(new Color(236,223,204));

        navbar.add(navbarTitle, BorderLayout.CENTER);

        // Content Panels
        JPanel homePanel = new Dashboard();
        JPanel inventoryPanel = new Inventory();
        JPanel addBookPanel = new AddBook();
        JPanel billingPanel = new Billing();
//        JPanel ordersPanel = new Orders();
//        JPanel salesPanel = new Sales();


        mainPanel.add(homePanel, buttons[0]);
        mainPanel.add(inventoryPanel, buttons[1]);
        mainPanel.add(addBookPanel, buttons[2]);
        mainPanel.add(billingPanel,buttons[3]);
//        mainPanel.add(ordersPanel,buttons[4]);
//        mainPanel.add(salesPanel, buttons[5]);


        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.X_AXIS));
        logoutPanel.setBackground(new Color(24,28,20));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(80,0,0,0));

        logoutPanel.add(Box.createHorizontalGlue());
        logoutPanel.add(logoutButton);
        logoutPanel.add(Box.createHorizontalGlue());

        sidePanel.add(adminPanel);
        sidePanel.add(Box.createVerticalStrut(20)); // Space between admin panel and buttons
        sidePanel.add(buttonPanel);
        sidePanel.add(Box.createVerticalGlue()); // Pushes logout to the bottom
        sidePanel.add(logoutPanel);

        container.add(sidePanel, BorderLayout.WEST);
        container.add(navbar, BorderLayout.NORTH);
        container.add(mainPanel, BorderLayout.CENTER);

        add(container);
        setVisible(true);
    }

    // Method to create sidebar buttons with resized icons
    private JButton createSidebarButton(String text, String iconPath, int iconWidth, int iconHeight) {
        JButton button = new JButton(text, resizeIcon(iconPath, iconWidth, iconHeight));
        button.setFont(new Font("Poppins", Font.BOLD, 20));
        button.setBackground(new Color(60,61,55));
        button.setForeground(new Color(236,223,204));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(250, 40));
        button.setIconTextGap(12);
        button.setBorder(BorderFactory.createEmptyBorder(5,15,5,5));

        return button;
    }

    // Method to resize icons
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    // Method to switch pages and update navbar title
    private void updatePage(String section) {
        cardLayout.show(mainPanel, section);
        navbarTitle.setText(section);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
        FontLoader.loadFont();
    }
}

