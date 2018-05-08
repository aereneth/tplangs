import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame implements ActionListener {
    private JFrame parent;
    private JPanel contentPanel;
    private JButton minimize;
    private JButton exit;
    private JLabel title;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Frame(String name, int width, int height){
        setTitle("Lexical Analyzer");
        setLayout(null);
        setSize(width, height);
        setLocation(screenSize.width/2 - getWidth()/2, screenSize.height/2 - getHeight()/2);
        getContentPane().setBackground(Color.decode("#222222"));
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(3);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setSize(getWidth() - 2, getHeight() - 31);
        contentPanel.setLocation(1,30);
        contentPanel.setBackground(Color.decode("#ffffff"));
        getContentPane().add(contentPanel);
        
        title = new JLabel();
        title.setLayout(null);
        title.setSize(getWidth()/2, 30);
        title.setLocation(getWidth()/2 - title.getWidth()/2, 0);
        title.setText(name.toUpperCase());
        title.setFont(new Font("Century Gothic", Font.BOLD, 18));
        title.setForeground(Color.decode("#ffffff"));
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(title);
        
        exit = new JButton();
        exit.setText("X");
        exit.setSize(50, 29);
        exit.setLocation(getWidth() - exit.getWidth() - 1, 1);
        exit.setBorder(null);
        exit.setFont(new Font("Century Gothic", Font.BOLD, 16));
        exit.setForeground(Color.decode("#ffffff"));
        exit.setBackground(Color.decode("#cc2222"));
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(this);
        getContentPane().add(exit);
        
        minimize = new JButton();
        minimize.setText("_");
        minimize.setSize(50, 29);
        minimize.setLocation(getWidth() - (exit.getWidth() + minimize.getWidth()) - 2, 1);
        minimize.setBorder(null);
        minimize.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        minimize.setForeground(Color.decode("#ffffff"));
        minimize.setBackground(Color.decode("#2222cc"));
        minimize.setBorderPainted(false);
        minimize.setFocusPainted(false);
        minimize.addActionListener(this);
        getContentPane().add(minimize);
    }
    
    public void addComponent(JComponent component){
        contentPanel.add(component);
    }
    
    public int getContainerWidth(){
        return contentPanel.getWidth();
    }
    
    public int getContainerHeight(){
        return contentPanel.getHeight();
    }
    
    public void setContainerBackground(Color color){
        contentPanel.setBackground(color);
    }
    
    public void setParent(JFrame frame){
        parent = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == minimize)
            setExtendedState(1);
            
        if(e.getSource() == exit)
            switch(getDefaultCloseOperation()){
                case 3: System.exit(0); break;
                default: dispose(); parent.setState(0);
            }
    }
}
