/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import rangerdepot.RangerDepot;

/**
 *
 * @author Florian
 */
public class GUI implements WindowListener
{
    private final RangerDepot depot;
    
    private final LoginFrame login;
    private MainFrame mainFrame = null;
    
    private ErrorDialog error = null;
    
    public final Dimension screenSize;
    
    
    public GUI(RangerDepot depot)
    {
        this.depot = depot;
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        login = new LoginFrame(depot, this);
        login.setVisible(true);
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
