import javax.swing.*;
import java.awt.*;

public class Orders extends JPanel {
    public Orders() {
        setBackground(new Color(238, 238, 238));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Orders", SwingConstants.CENTER);
        label.setFont(new Font("Poppins", Font.BOLD, 30));
        label.setForeground(Color.BLACK);

        add(label, BorderLayout.CENTER);
    }
}
