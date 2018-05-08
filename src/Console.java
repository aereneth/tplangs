import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Console {
    Frame frame;
    Frame parent;
    JTextArea consoleArea;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Console(Frame parent){
        frame = new Frame("Console", (int)(screenSize.width*0.60), (int)(screenSize.height*0.60));
        frame.setLocation(screenSize.width/2 - frame.getWidth()/2, screenSize.height/2 - frame.getHeight()/2);
        frame.setDefaultCloseOperation(2);
        
        consoleArea = new JTextArea();
        consoleArea.setText("");
        consoleArea.setSize(frame.getContainerWidth(), frame.getContainerHeight());
        consoleArea.setLocation(0, 0);
        consoleArea.setFont(new Font("Courier New", Font.BOLD, 24));
        consoleArea.setForeground(Color.decode("#ffffff"));
        consoleArea.setBackground(Color.decode("#000000"));
        consoleArea.setWrapStyleWord(true);
        consoleArea.setTabSize(4);
        consoleArea.setEditable(false);
        frame.addComponent(consoleArea);
        frame.setParent(parent);
    }
}
