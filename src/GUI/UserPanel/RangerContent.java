/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.UserPanel;

import GUI.GUI;
import GUI.Menu.MenuActionEvent;
import GUI.Menu.MyMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class RangerContent extends Content implements ActionListener
{
    public RangerContent(GUI gui, User user)
    {
        super(gui,user);
        
//        menu.addMenuItem("  Material");
//        menu.addMenuItem("  Test 2");
//        menu.addSubMenuItem(1,"Test 2.1");
//        menu.addSubMenuItem(1,"Test 2.2");
//        menu.addMenuItem("  Test 3");
//        menu.addMenuItem("  Test 4");
//        menu.addSubMenuItem(3,"Test 4.1");
    }
    
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof MyMenu)
        {
            MenuActionEvent event = (MenuActionEvent)e;
            //TODO:
        }
    }
    //</editor-fold> Action Listener
}
