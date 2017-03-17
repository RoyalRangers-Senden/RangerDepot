/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import javax.swing.JLabel;
import rangerdepot.RangerDepot;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class GUI implements WindowListener
{
    public static final int MENU_WIDTH = 100;
    public static final Font GUI_FONT = getFont("MYRIADAT");
    public static final Font GUI_FONT_SEMI = getFont("MYRIADAS");
    public static final Font GUI_FONT_MEDIUM = getFont("MYRIADAM");
    public static final Font GUI_FONT_BOLD = getFont("MYRIADAB");
    
    
    public final RangerDepot depot;
    
    private final LoginFrame login;
    private MainFrame mainFrame = null;
    
    private ErrorDialog error = null;
    
    public final Dimension screenSize;
    
    
    public GUI(RangerDepot depot)
    {
        this.depot = depot;
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        login = new LoginFrame(depot, this);
        login.open();
    }
    private static Font getFont(String s)
    {
        try
        {
            URL url = RangerDepot.class.getResource("../resources/font/"+s+".ttf");
            return Font.createFont(Font.TRUETYPE_FONT, url.openStream()).deriveFont((float)15);
        }catch(Exception e)
        {
            return new JLabel().getFont();
        }
    }
    public void exit()
    {
        depot.exit();
    }
    
    public void buildMainFrame()
    {
        mainFrame = new MainFrame(this);
        error = new ErrorDialog(this,mainFrame);
        
        login.dispose();
    }
    
    
    
    public void error(String err)
    {
        if(error != null)
            error.showError(err);
    }
    
    
    public User getUser()
    {
        return depot.getUser();
    }
    
    
    //========================================================================//
    
    //<editor-fold> Window Listener
    @Override public void windowActivated(WindowEvent e){}
    @Override public void windowClosed(WindowEvent e){}
    @Override public void windowClosing(WindowEvent e){}
    @Override public void windowDeactivated(WindowEvent e){}
    @Override public void windowDeiconified(WindowEvent e){}
    @Override public void windowIconified(WindowEvent e){}
    @Override public void windowOpened(WindowEvent e){}
    //</editor-fold> Window Listener
}
