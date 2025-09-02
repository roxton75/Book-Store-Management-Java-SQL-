import javax.swing.*;
import java.awt.*;

class CustomComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setBackground(new Color(236, 223, 204));
        label.setFont(new Font("Netflix Sans", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        if (isSelected) {
            label.setBackground(new Color(105, 117, 101)); // Yellow background for selected item
            label.setForeground(new Color(236, 223, 204));
        } else {
            label.setBackground(new Color(236, 223, 204));
            label.setForeground(new Color(34,40,42));
        }

        return label;
    }
}
