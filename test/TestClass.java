
import GUI.guiElements.CloseButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Florian
 */
public class TestClass extends JFrame implements ActionListener
{
    public static void main(String[] args)
    {
        TestClass test = new TestClass();
    }
    
    
    private final CloseButton close;
    private final JButton h;
    
    public TestClass()
    {
        super("Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200,100);
        setLocation(100,100);
        setLayout(null);
        
        close = new CloseButton(50);
        close.addActionListener(this);
        close.setLocation(10,10);
        add(close);
        
        h = new JButton();h.setSize(1,1);h.setLocation(-1,-1);
        add(h);remove(h);
        
        setVisible(true);
    }
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof CloseButton)
        {
            System.exit(0);
        }
    }
    //</editor-fold> Action Listener
}