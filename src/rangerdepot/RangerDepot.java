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
import rangerdepot.Users.Content.Stamm;
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
    
    
    public final DatabaseInterface di;
    private final GUI gui;
    
    private boolean connected = false;
    
    
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
                ResultSet result = di.read("SELECT * FROM users JOIN stamm on users.stammID=stamm.id WHERE users.name = \'"+userName+"\';");
                result.first();
                if(result.getRow() > 0)
                {
                    if(result.getInt("isLeiter") == 0)
                        user = new Ranger(result.getInt("users.id"), result.getString("users.name"), new Stamm(result.getInt("stamm.id"),result.getString("stamm.name")));
                    else
                        user = new Leiter(result.getInt("users.id"), result.getString("users.name"), new Stamm(result.getInt("stamm.id"),result.getString("stamm.name")), result.getBoolean("users.isStammAdmin"),result.getBoolean("users.isAdmin"));
                    System.out.println("User angelegt: "+user.getType());
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
    
    
    public User getUser()
    {
        return user;
    }
    
    
    public void showError(String err)
    {
        gui.error(err);
    }
}