import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main implements ActionListener, ChangeListener{
    Frame frame;
    JPanel toolPanel;
    JPanel editPanel;
    JTextArea workArea;
    JComboBox styleSelector;
    JSpinner sizeSelector;
    TextLineNumber lineNumber;
    JScrollPane inputScroll;
    JButton themeButton;
    JButton compileButton;
    JButton runButton;
    JButton saveButton;
    JButton openButton;
    
    SpinnerNumberModel fontSizeModel;
    
    Console console;
    Compiler compiler;
    
    int toolIconNumber = 8;
    int editIconNumber = 8;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension toolIconMargin;
    Dimension toolIconSize;
    Dimension editIconMargin;
    Dimension editIconSize;
    String baseColor = "#ffffff";
    String accentColor = "#111111";
    
    public Main(){
        frame = new Frame("HexaCode+++", (int)(screenSize.width*0.80), (int)(screenSize.height*0.80));
        
        editPanel = new JPanel();
        editPanel.setLayout(null);
        editPanel.setSize(frame.getContainerWidth(), (int)(frame.getContainerHeight()*0.91));
        editPanel.setLocation(0, (int)(frame.getContainerHeight()*0.10));
        editPanel.setBackground(Color.decode(baseColor));
        frame.addComponent(editPanel);
        
        editIconMargin = new Dimension((int)(editPanel.getWidth()*0.10/editIconNumber*0.80), (int)(editPanel.getHeight()*0.10*0.2));
        editIconSize = new Dimension(editPanel.getWidth()/editIconNumber - editIconMargin.width*2, (int)(editPanel.getHeight()*0.10) - editIconMargin.height*2);
        
        workArea = new JTextArea();
        workArea.setText("");
        workArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        workArea.setForeground(Color.decode(accentColor));
        workArea.setBackground(Color.decode(baseColor));
        workArea.setWrapStyleWord(true);
        //workArea.setLineWrap(true);
        workArea.setTabSize(4);
        
        lineNumber = new TextLineNumber(workArea);
        
        inputScroll = new JScrollPane();
        inputScroll.setSize(editPanel.getWidth(), (int)(editPanel.getHeight()*0.91));
        inputScroll.setLocation(0, (int)(editPanel.getHeight()*0.10));
        inputScroll.getViewport().add(workArea);
        inputScroll.setRowHeaderView(lineNumber);
        editPanel.add(inputScroll);
        
        styleSelector = new JComboBox();
        styleSelector.setSize(editIconSize.width*2 + editIconMargin.width*2, editIconSize.height);
        styleSelector.setLocation(editPanel.getWidth()/editIconNumber*5+editIconMargin.width, editIconMargin.height);
        styleSelector.setModel(new DefaultComboBoxModel(new String[]{"Arial", "Calibri", "Century Gothic", "Comic Sans MS", "Consolas", "Courier New", "Lucida Sans", "Tahoma", "Times New Roman", "Verdana"}));
        styleSelector.setFont(new Font("Calibri", Font.PLAIN, 24));
        styleSelector.setForeground(Color.decode(accentColor));
        styleSelector.setBackground(Color.decode(baseColor));
        styleSelector.setSelectedIndex(4);
        styleSelector.addActionListener(this);
        editPanel.add(styleSelector);
        
        fontSizeModel = new SpinnerNumberModel();
        fontSizeModel.setMaximum(72);
        fontSizeModel.setMinimum(8);
        fontSizeModel.setStepSize(2);
        fontSizeModel.setValue(15);
        
        sizeSelector = new JSpinner();
        sizeSelector.setSize(editIconSize);
        sizeSelector.setLocation(editPanel.getWidth()/editIconNumber*7+editIconMargin.width, editIconMargin.height);
        sizeSelector.setFont(new Font("Calibri", Font.PLAIN, 24));
        sizeSelector.setForeground(Color.decode(accentColor));
        sizeSelector.setBackground(Color.decode(baseColor));
        sizeSelector.setModel(fontSizeModel);
        sizeSelector.setValue(16);
        sizeSelector.addChangeListener(this);
        editPanel.add(sizeSelector);

        toolPanel = new JPanel();
        toolPanel.setLayout(null);
        toolPanel.setSize(frame.getContainerWidth(), (int)(frame.getContainerHeight()*0.10));
        toolPanel.setLocation(0, 0);
        toolPanel.setBackground(Color.decode(baseColor));
        frame.addComponent(toolPanel);
        
        toolIconMargin = new Dimension((int)(toolPanel.getWidth()/toolIconNumber*0.05), (int)(toolPanel.getHeight()*0.2));
        toolIconSize = new Dimension(toolPanel.getWidth()/toolIconNumber - toolIconMargin.width*2, toolPanel.getHeight() - toolIconMargin.height*2);
        
        compileButton = new JButton();
        compileButton.setText("COMPILE");
        compileButton.setSize(toolIconSize);
        compileButton.setLocation(toolPanel.getWidth()/toolIconNumber*0+toolIconMargin.width, toolIconMargin.height);
        compileButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        compileButton.setForeground(Color.decode(baseColor));
        compileButton.setBackground(Color.decode(accentColor));
        //compileButton.setBorderPainted(false);
        //compileButton.setOpaque(false);
        compileButton.setFocusPainted(false);
        //compileButton.setContentAreaFilled(false);
        compileButton.addActionListener(this);
        //toolPanel.add(compileButton);
        
        runButton = new JButton();
        runButton.setText("COMPILE & RUN");
        runButton.setSize(toolIconSize.width*2 + toolIconMargin.width*2, toolIconSize.height);
        runButton.setLocation(toolPanel.getWidth()/toolIconNumber*0+toolIconMargin.width*2, toolIconMargin.height);
        runButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        runButton.setForeground(Color.decode(baseColor));
        runButton.setBackground(Color.decode(accentColor));
        runButton.setBorderPainted(false);
        //runButton.setOpaque(false);
        runButton.setFocusPainted(false);
        //runButton.setContentAreaFilled(false);
        runButton.addActionListener(this);
        toolPanel.add(runButton);
        
        themeButton = new JButton();
        themeButton.setText("DARK");
        themeButton.setSize(toolIconSize.width*2 + toolIconMargin.width*2, toolIconSize.height);
        themeButton.setLocation(toolPanel.getWidth()/toolIconNumber*6+toolIconMargin.width, toolIconMargin.height);
        themeButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        themeButton.setForeground(Color.decode(baseColor));
        themeButton.setBackground(Color.decode(accentColor));
        themeButton.setBorderPainted(false);
        //themeButton.setOpaque(false);
        themeButton.setFocusPainted(false);
        //themeButton.setContentAreaFilled(false);
        themeButton.addActionListener(this);
        toolPanel.add(themeButton);
        
        saveButton = new JButton();
        saveButton.setText("SAVE");
        saveButton.setSize(toolIconSize);
        saveButton.setLocation(toolPanel.getWidth()/toolIconNumber*6+toolIconMargin.width, toolIconMargin.height);
        saveButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        saveButton.setForeground(Color.decode(baseColor));
        saveButton.setBackground(Color.decode(accentColor));
        //saveButton.setBorderPainted(false);
        //saveButton.setOpaque(false);
        saveButton.setFocusPainted(false);
        //saveButton.setContentAreaFilled(false);
        saveButton.addActionListener(this);
        //toolPanel.add(saveButton);
        
        openButton = new JButton();
        openButton.setText("OPEN");
        openButton.setSize(toolIconSize);
        openButton.setLocation(toolPanel.getWidth()/toolIconNumber*7+toolIconMargin.width, toolIconMargin.height);
        openButton.setFont(new Font("Century Gothic", Font.PLAIN, 28));
        openButton.setForeground(Color.decode(baseColor));
        openButton.setBackground(Color.decode(accentColor));
        //openButton.setBorderPainted(false);
        //openButton.setOpaque(false);
        openButton.setFocusPainted(false);
        //openButton.setContentAreaFilled(false);
        openButton.addActionListener(this);
        //toolPanel.add(openButton);
    }
    
    public static void main(String[] args){
        Main main = new Main();
        main.frame.setVisible(true);
    }
    
    private void toggleTheme() {
        baseColor = baseColor.equals("#ffffff") ? "#111111" : "#ffffff";
        accentColor = baseColor.equals("#ffffff") ? "#111111" : "#ffffff";

//        compileButton.setForeground(Color.decode(baseColor));
//        compileButton.setBackground(Color.decode(accentColor));
//        runButton.setForeground(Color.decode(baseColor));
//        runButton.setBackground(Color.decode(accentColor));
//        saveButton.setForeground(Color.decode(baseColor));
//        saveButton.setBackground(Color.decode(accentColor));
//        openButton.setForeground(Color.decode(baseColor));
//        openButton.setBackground(Color.decode(accentColor));
//        themeButton.setForeground(Color.decode(baseColor));
//        themeButton.setBackground(Color.decode(accentColor));
        workArea.setForeground(Color.decode(accentColor));
        workArea.setBackground(Color.decode(baseColor));

        frame.setContainerBackground(Color.decode(baseColor));
//        editPanel.setBackground(Color.decode(baseColor));
//        toolPanel.setBackground(Color.decode(baseColor));
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == compileButton){
            console = new Console(frame);
            compiler = new Compiler(workArea, console.consoleArea);
            compiler.compile();
        }
        
        if(e.getSource() == runButton){
            console = new Console(frame);
            compiler = new Compiler(workArea, console.consoleArea);
            console.frame.setVisible(true);
            compiler.compile();
            frame.setState(1);
        }
        
        if(e.getSource() == themeButton){
            toggleTheme();
            themeButton.setText(themeButton.getText().equals("DARK") ? "LIGHT" : "DARK");
        }
        
        if(e.getSource() == styleSelector){
            workArea.setFont(new Font(String.valueOf(styleSelector.getSelectedItem()), Font.PLAIN, (int)sizeSelector.getValue()));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == sizeSelector){
            workArea.setFont(new Font(String.valueOf(styleSelector.getSelectedItem()), Font.PLAIN, (int)sizeSelector.getValue()));
        }
    }
}