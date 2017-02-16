
import GUI.MainFrame;
import GUI.guiElements.MyComboBox;
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
public class Test extends JFrame
{
    public static void main(String args[])
    {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setLocation(700,300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(MainFrame.BACKGROUND_COLOR);
        
        MyComboBox c = new MyComboBox();
        c.setSize(150,30);
        c.setLocation(10,10);
        c.setMaxShownLabels(10);
        c.addItem("Test 1");
        c.addItem("Test 2");
        c.addItem("Test 3");
        c.addItem("Test 4");
        c.addItem("Test 5");
        c.addItem("Test 6");
        c.addItem("Test 7");
        c.addItem("Test 8");
        c.addItem("Test 9");
        c.setBackgroundColor(MainFrame.BACKGROUND_COLOR);
        c.setBorderColor(MainFrame.BORDER_COLOR);
        c.setHoveredColor(MainFrame.MENU_HOVERED_COLOR);
        c.setBorderLightColor(MainFrame.MENU_COLOR);
        c.setScrollbarColor(MainFrame.BORDER_COLOR);
        frame.add(c);
        
        frame.setVisible(true);
    }
}