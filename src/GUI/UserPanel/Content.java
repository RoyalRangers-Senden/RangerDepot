/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.UserPanel;

import GUI.GUI;
import static GUI.GUI.MENU_WIDTH;
import static GUI.MainFrame.BORDER_COLOR;
import static GUI.MainFrame.BORDER_HOVERED_COLOR;
import static GUI.MainFrame.MENU_COLOR;
import static GUI.MainFrame.MENU_HOVERED_COLOR;
import GUI.Menu.MyMenu;
import GUI.ScrollPane.MyScrollPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class Content extends JLayeredPane implements ActionListener
{
    protected final GUI gui;
    protected final User user;
    
    //Menu
    protected final MyScrollPane menuPanel;
    protected final MyMenu menu;
    
    //Content
    protected JComponent selectedPanel;
    
    
    protected final JButton h;
    
    public Content(GUI gui, User user)
    {
        super();
        this.gui = gui;
        this.user = user;
        setLayout(null);
        setOpaque(false);
        
        h = new JButton();h.setSize(1,1);h.setLocation(-1,-1);
        
        //====================================================================//
        
        menuPanel = new MyScrollPane();
        menuPanel.setSize(MENU_WIDTH,getHeight());menuPanel.setLocation(0, 0);
        menuPanel.setBackground(MENU_COLOR);menuPanel.setScrollbarColor(BORDER_HOVERED_COLOR);
        menuPanel.setScrollbarThickness(7);
        menuPanel.setLayout(null);
        menu = new MyMenu(MENU_WIDTH);
        menu.addActionListener(this);
        menu.setFont(GUI.GUI_FONT_SEMI);
        menu.setFontSize(18);
        menu.setSmallFontSize(18);
        menu.setHoveredColor(MENU_HOVERED_COLOR);
        menu.setSelectedColor(BORDER_COLOR);
        menu.setStandardColor(MENU_COLOR);
        menuPanel.add(menu);
        menuPanel.add(h);menuPanel.remove(h);
        
        
        add(menuPanel,10);
        add(h);remove(h);
    }
    
    @Override public void setSize(Dimension d)
    {
        setSize(d.width,d.height);
    }
    @Override public void setSize(int x, int y)
    {
        super.setSize(x,y);
        menuPanel.setSize(MENU_WIDTH, getHeight());
    }
    
    
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e){}
    //</editor-fold> Action Listener
}