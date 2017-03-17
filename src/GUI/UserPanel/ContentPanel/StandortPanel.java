/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.UserPanel.ContentPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rangerdepot.Users.Content.Standort;

/**
 *
 * @author Florian
 */
public class StandortPanel extends JPanel implements MouseListener
{
    private static final int BORDER_WIDTH = 2;
    private static final int HEIGHT = 100;
    
    private final Standort standort;
    private final JLabel name;
    private final JLabel town,street;
    private final JLabel h;
    
    private final ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
    
    public StandortPanel(Standort s)
    {
        super();
        standort = s;
        
        addMouseListener(this);
        setBorder(BorderFactory.createLineBorder(new Color(220,220,220), BORDER_WIDTH));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        h = new JLabel();h.setSize(1,1);h.setLocation(-1,-1);
        
        name = new JLabel(s.getName());
        name.setLocation(BORDER_WIDTH,BORDER_WIDTH);
        name.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        name.setFont(getFont().deriveFont(Font.BOLD));
        name.addMouseListener(this);
        
        town = new JLabel((s.getZipcode()==-1?"":Integer.toString(s.getZipcode())) + (s.getTown()==null?"":" "+s.getTown()));
        town.setAlignmentX(JLabel.CENTER_ALIGNMENT);town.addMouseListener(this);
        street = new JLabel((s.getStreet()==null?"":s.getStreet()) + (s.getHouseNumber()==-1?"":(" "+s.getHouseNumber()+(s.getHouseNumberAddition()==null?"":" "+s.getHouseNumberAddition()))));
        street.setAlignmentX(JLabel.CENTER_ALIGNMENT);street.addMouseListener(this);
        
        add(name);
        add(town);add(street);
        add(h);remove(h);
    }
    
    public Standort getStandort()
    {
        return standort;
    }
    
//    @Override public void setSize(Dimension d)
//    {
//        setSize(d.width,d.height);
//    }
//    @Override public void setSize(int x,int y)
//    {
//        super.setSize(x, y);
//        name.setSize(x-BORDER_WIDTH*2, 30);
//    }
    
    public void setFontSmall(Font f)
    {
        town.setFont(f);
        street.setFont(f);
    }
    public void setFontBold(Font f)
    {
        name.setFont(f);
    }
    
    public void setBorderColor(Color c)
    {
        setBorder(BorderFactory.createLineBorder(c, BORDER_WIDTH));
    }
    
    
    public void addActionListener(ActionListener al)
    {
        if(!listeners.contains(al))
            listeners.add(al);
    }
    public void removeActionListener(ActionListener al)
    {
        listeners.remove(al);
    }
    
    
    
    
    
    
    //<editor-fold> Mouse Listener
    @Override public void mouseClicked(MouseEvent e)
    {
        ActionEvent ae = new ActionEvent(this,0,"Standort clicked");
        for(ActionListener al:listeners)
            al.actionPerformed(ae);
    }
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    //</editor-fold> Mouse Listener
}
