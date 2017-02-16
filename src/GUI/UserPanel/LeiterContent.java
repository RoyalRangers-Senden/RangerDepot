/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.UserPanel;

import GUI.UserPanel.CategoryPanel.MaterialPanel;
import GUI.GUI;
import static GUI.GUI.MENU_WIDTH;
import GUI.Menu.MenuActionEvent;
import GUI.Menu.MyMenu;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class LeiterContent extends RangerContent implements ActionListener
{
    private final MaterialPanel material;
    
    public LeiterContent(GUI gui, User user)
    {
        super(gui,user);
        
        menu.addMenuItem("  Material");
        menu.addMenuItem("  Team");
        
        material = new MaterialPanel(gui);
        material.setLocation(MENU_WIDTH,0);
        add(material,1);
        
        selectedPanel = material;
    }
    
    @Override public void setSize(int x,int y)
    {
        super.setSize(x, y);
        if(selectedPanel != null)
            selectedPanel.setSize(x-MENU_WIDTH, y);
    }
    @Override public void setSize(Dimension d)
    {
        setSize(d.width,d.height);
    }
    
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if(e.getSource() instanceof MyMenu)
        {
            MenuActionEvent event = (MenuActionEvent)e;
            if(event.selectedItem == 0)
            {
                if(selectedPanel != null)
                    remove(selectedPanel);
                selectedPanel = material;
                selectedPanel.setSize(getWidth()-MENU_WIDTH,getHeight());
                material.reset();
                add(selectedPanel,1);
            }
            else
            {
                if(selectedPanel != null)
                {
                    remove(selectedPanel);
                    selectedPanel = null;
                    repaint();
                }
            }
        }
    }
    //</editor-fold> Action Listener
}
