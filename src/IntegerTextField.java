import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerTextField extends JTextField {
    public IntegerTextField(int columns) {
        super(columns);
        setDocumentFilter();
    }

    private void setDocumentFilter() {
        AbstractDocument doc = (AbstractDocument) this.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if (text.matches("\\d+")) {  // Allows only digits (0-9)
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {  // Allows only digits (0-9)
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
