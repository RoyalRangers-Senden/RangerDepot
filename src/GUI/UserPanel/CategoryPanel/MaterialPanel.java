/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.UserPanel.CategoryPanel;

import GUI.GUI;
import GUI.MainFrame;
import GUI.ScrollPane.MyScrollPane;
import GUI.UserPanel.ContentPanel.StandortPanel;
import GUI.guiElements.MyComboBox;
import GUI.guiElements.MyComboBox.MyComboEvent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import rangerdepot.Users.Content.Stamm;
import rangerdepot.Users.Content.Standort;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class MaterialPanel extends JLayeredPane implements ActionListener
{
    private static final int STANDORTE_WIDTH = 150;
    private static final int STANDORTE_HEIGHT = 60;
    private static final int STANDORTE_GAP = 30;
    
    
    private final GUI gui;
    private final User user;
    
    //header
    private final JPanel header;
    private final JLabel stamm;
    private final MyComboBox<Stamm> stammCombo;
    
    //content
    private final MyScrollPane standortePanel;
    private final ArrayList<StandortPanel> standorte = new ArrayList<StandortPanel>();
    
    private final JButton h;
    
    public MaterialPanel(GUI g)
    {
        super();
        gui = g;
        user = gui.getUser();
        setLayout(null);
        
        h = new JButton();h.setSize(1,1);h.setLocation(-1,-1);
        
        header = new JPanel();
        header.setLocation(0,0);
        header.setBackground(MainFrame.MENU_COLOR);
        header.setLayout(null);
        stamm = new JLabel(user.getStamm().toString());
        stamm.setSize(300,26);stamm.setLocation(4,2);
        stamm.setFont(GUI.GUI_FONT);
        stammCombo = new MyComboBox<Stamm>();
        stammCombo.setSize(300,26);stammCombo.setLocation(4,2);
        stammCombo.setFont(GUI.GUI_FONT);
        stammCombo.allowNotlistedElements(false);
        stammCombo.addActionListener(this);
        stammCombo.setItemSize(26);
        stammCombo.setBackgroundColor(MainFrame.MENU_COLOR);
        stammCombo.setBorderColor(MainFrame.BORDER_HOVERED_COLOR);
        stammCombo.setHoveredColor(MainFrame.MENU_HOVERED_COLOR);
        stammCombo.setBorderLightColor(MainFrame.MENU_HOVERED_COLOR);
        stammCombo.setScrollbarColor(MainFrame.BORDER_HOVERED_COLOR);
        updateStaemme();
        
        if(user.isAdmin())
            add(stammCombo,21);
        else
            header.add(stamm);
        header.add(h);header.remove(h);
        
        
        standortePanel = new MyScrollPane();
        standortePanel.setLocation(0,30);
        standortePanel.setLayout(null);
        standortePanel.setBackground(MainFrame.BACKGROUND_COLOR);
        standortePanel.setScrollbarThickness(7);
        standortePanel.setScrollbarColor(MainFrame.BORDER_COLOR);
        
        add(header,20);
        add(standortePanel,1);
        add(h);remove(h);
    }
    
    private void updateStaemme()
    {
        ResultSet result = gui.depot.di.read("SELECT * from stamm ORDER BY id;");
        try
        {
            result.last();
            int size = result.getRow();
            result.first();
            
            stammCombo.clear();
            for(int i=0;i<size;i++)
            {
                stammCombo.addItem(new Stamm(result.getInt("id"),result.getString("name")));
                result.next();
            }
        }catch(Exception e){}
    }
    
    private void update(int stammID)
    {
        for(StandortPanel p:standorte)
            standortePanel.remove(p);
        standorte.clear();
        
        try
        {
            ResultSet result = gui.depot.di.read("SELECT * FROM standort WHERE stammID="+stammID+" ORDER BY zipcode;");
            result.last();
            int length = result.getRow();
            result.first();

            for(int i=0;i<length;i++)
            {
                standorte.add(new StandortPanel(new Standort(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("stammID"),
                        result.getInt("zipcode"),
                        result.getString("town"),
                        result.getString("town"),
                        result.getInt("houseNumber"),
                        result.getString("houseNumberAddition"))));
                result.next();
            }
        }catch(SQLException e){e.printStackTrace();}
        
        updateStandorteSize();
    }
    private void updateStandorteSize()
    {
        int columns = standortePanel.getWidth()/(STANDORTE_WIDTH+STANDORTE_GAP);
        if((STANDORTE_WIDTH+STANDORTE_GAP)*columns + STANDORTE_GAP > standortePanel.getWidth())
            columns--;
//        if(columns > standorte.size())
//            columns = standorte.size();
        if(columns < 1)
            columns = 1;
        int gap = (standortePanel.getWidth()-STANDORTE_WIDTH*columns) / (columns+1);
        for(int i=0;i<standorte.size();i++)
        {
            StandortPanel p = standorte.get(i);
            p.setSize(STANDORTE_WIDTH,STANDORTE_HEIGHT);
            p.setLocation(STANDORTE_WIDTH*(i%columns) + gap*(i%columns+1), STANDORTE_HEIGHT*(i/columns) + STANDORTE_GAP*(i/columns+1));
            p.setFontSmall(GUI.GUI_FONT);
            p.setFontBold(GUI.GUI_FONT_SEMI);
            p.setBackground(MainFrame.BACKGROUND_HOVERED_COLOR);
            p.setBorderColor(MainFrame.BORDER_COLOR);
            standortePanel.add(p);
        }
    }
    
    
    public void reset()
    {
        if(user.isAdmin())
            stammCombo.setSelectetItem(null);
        standortePanel.removeAll();
        standorte.clear();
        updateStaemme();
    }
    
    
    @Override public void setSize(int x, int y)
    {
        super.setSize(x, y);
        header.setSize(x, 30);
        standortePanel.setSize(x, y-30);
        updateStandorteSize();
    }
    @Override public void setSize(Dimension d)
    {
        setSize(d.width,d.height);
    }
    
    
    //========================================================================//
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof MyComboBox)
        {
            MyComboEvent c = (MyComboEvent)e;
            update(((Stamm)c.getObject()).getID());
        }
    }
    //</editor-fold> Action Listener
}
