import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {
    public static void loadFont() {
        try {
            // Path to the font file in your project resources
            File fontFile = new File("resources/fonts/Poppins-Regular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(16f); // Set font size

            // Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            // Set this font globally if needed
            UIManager.put("Label.font", font); // Set the font for all JLabel components
            UIManager.put("Button.font", font); // Set the font for all JButton components
            UIManager.put("TextField.font", font); // Set the font for all JTextField components
            UIManager.put("ComboBox.font",font);
            // Repeat for other components as necessary

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
