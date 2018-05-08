import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class LineNumber extends JTextArea implements DocumentListener{
    private int lineCount = 0;
    private String lineNumber = "";
    private JComponent component;
    private Document document;
    
    public LineNumber(){
        setText("1");
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setFont(new Font("Consolas", Font.PLAIN, 15));
        setForeground(Color.decode("#999999"));
        setBackground(Color.decode("#ffffff"));
        setEditable(false);
        setWrapStyleWord(true);
        setLineWrap(true);
        setColumns(5);
    }
    
    public void setComponent(JComponent component){
        this.component = component;
        document = ((JTextComponent)component).getDocument();
        document.addDocumentListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        lineCount = ((JTextArea)component).getLineCount();
        //lineNumber.
        
    }
}
