import javax.swing.*;
import java.awt.*;

public class Sales extends JPanel {
    public Sales() {
        setBackground(new Color(238, 238, 238));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Sales", SwingConstants.CENTER);
        label.setFont(new Font("Poppins", Font.BOLD, 30));
        label.setForeground(Color.BLACK);

        add(label, BorderLayout.CENTER);
    }
}
