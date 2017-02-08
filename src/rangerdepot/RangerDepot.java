/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerdepot;

import GUI.GUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.UIManager;
import rangerdepot.Users.Leiter;
import rangerdepot.Users.Ranger;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class RangerDepot
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){System.err.println("Error loading Look and Feel!");e.printStackTrace();}
        
        new RangerDepot();
    }
    
    public static final int ADMIN = 0;
    public static final int LEITER = 1;
    public static final int RANGER = 2;
    private User user = null;
    
    
    private final DatabaseInterface di;
    private final GUI gui;
    
    private boolean connected = false;
    private int userType;
    
    
    public RangerDepot()
    {
        di = new DatabaseInterface(this);
        gui = new GUI(this);
    }
    public boolean logIn(String url, String userName, String password)
    {
        if(di.connect(url, userName, password) == -1)
        {
            try
            {
                ResultSet result = di.read("SELECT * FROM users WHERE name = \'"+userName+"\';");
                result.first();
                if(result.getRow() > 0)
                {
                    if(result.getInt("isLeiter") == 0)
                        user = new Ranger(result.getInt("id"), result.getString("name"));
                    else
                        user = new Leiter(result.getInt("id"), result.getString("name"), result.getInt("isAdmin")!=0);
                    System.out.println("User angelegt");
                }
                else
                {
                    user = null;
                    di.exit();
                    System.out.println("User NICHT angelegt");
                    return false;
                }
            }catch(SQLException e){user = null;di.exit(); return false;}
            
            
            connected = true;
            gui.buildMainFrame();
        }
        return connected;
    }
    
    public void exit()
    {
        di.exit();
        System.exit(0);
    }
    
    
    public void showError(String err)
    {
        gui.error(err);
    }
}
