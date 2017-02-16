/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.ScrollPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

/**
 *
 * @author Florian
 */
public class MyScrollPane extends JPanel implements MouseWheelListener,PropertyChangeListener
{
    private static final int ADD = 10;
    
    private final MyScrollBar sbV,sbH;
    private int scrollbarThickness = 10;
    private boolean enabled = true;
    
    private Dimension size,contentSize;
    private double contentXPos,contentYPos;
    private JPanel contentPane;
    
    
    
    public MyScrollPane()
    {
        super();
        setDoubleBuffered(true);
        super.setLayout(null);
        addMouseWheelListener(this);
        size = new Dimension(0,0);
        
        sbV = new MyScrollBar(this);
        sbV.setAlignment(MyScrollBar.VERTICAL);
        super.add(sbV);
        sbH = new MyScrollBar(this);
        sbH.setAlignment(MyScrollBar.HORIZONTAL);
        super.add(sbH);
        
        contentSize = new Dimension(0,0);
        contentXPos = 0;
        contentYPos = 0;
        
        contentPane = new JPanel();
        contentPane.setOpaque(false);
        super.add(contentPane);
    }
    
    @Override public void setLayout(LayoutManager mgr)
    {
        try
        {
            contentPane.setLayout(mgr);
        }catch(Exception e){}
    }
    @Override public Component add(Component comp)
    {
        contentPane.add(comp);
        comp.addPropertyChangeListener(this);
        return comp;
    }
    @Override public void remove(Component comp)
    {
        contentPane.remove(comp);
        comp.removePropertyChangeListener(this);
    }
    @Override public void removeAll()
    {
        for(Component c:contentPane.getComponents())
            remove(c);
    }
    
    @Override public void setSize(Dimension d)
    {
        size = d;
        super.setSize(d);
    }
    @Override public void setSize(int x, int y)
    {
        size.width = x;
        size.height = y;
        super.setSize(x, y);
    }
    
    public void setScrollbarColor(Color c)
    {
        sbV.setColor(c);
        sbH.setColor(c);
    }
    public void setScrollbarThickness(int t)
    {
        if(t > 0)
        {
            scrollbarThickness = t;
            sbV.setThickness(t);
            sbH.setThickness(t);
            repaint();
        }
    }
    
    
    
    public void updateContent()
    {
        calcContentPanel();
        calcScrollBars();
    }
    
    private void getContentSize()
    {
        Component [] components = contentPane.getComponents();
        contentSize.width = 0;
        contentSize.height = 0;
        for(Component c : components)
        {
            Dimension d = c.getSize();
            if(c.getX()+d.width > contentSize.width)
                contentSize.width = c.getX()+d.width;
            if(c.getY()+d.height > contentSize.height)
                contentSize.height = c.getY()+d.height;
        }
    }
    private void calcContentPanel()
    {
        getContentSize();
        contentPane.setSize(contentSize);
        
        if(contentSize.width - contentXPos < size.width)
            contentXPos = (contentSize.width<size.width) ? 0 : contentSize.width-size.width;
        if(contentXPos < 0)contentXPos = 0;
        
        if(contentSize.height - contentYPos < size.height)
            contentYPos = (contentSize.height<size.height) ? 0 : contentSize.height-size.height;
        if(contentYPos < 0)contentYPos = 0;
        
        contentPane.setLocation(-(int)contentXPos, -(int)contentYPos);
    }
    
    private void calcScrollBars()
    {
        /*Vertical*/
        int barLength = (int)(((double)size.height/contentSize.height)*size.height) - 10;
        int barStart = (int)(((double)contentYPos/contentSize.height)*size.height);
        if(barLength<10)
            barLength = 10;
        sbV.setSize(barLength);
        sbV.setLocation(size.width-(scrollbarThickness+2), barStart+2);
        
        /*Horizontal*/
        barLength = (int)(((double)size.width/contentSize.width)*size.width) - 10;
        barStart = (int)(((double)contentXPos/contentSize.width)*size.width);
        if(barLength<10)
            barLength = 10;
        sbH.setSize(barLength);
        sbH.setLocation(barStart+2, size.height-(scrollbarThickness+2));
        
        /**/
        
        if(contentSize.height > size.height)
            sbV.setVisible(true);
        else
            sbV.setVisible(false);
        
        if(contentSize.width > size.width)
            sbH.setVisible(true);
        else
            sbH.setVisible(false);
    }
    
    
    
    public void scrollBarMoved(MyScrollBar source, int movement)
    {
        if(source == sbV)
        {
            contentYPos += ((double)movement/size.height)*contentSize.height;
        }
        else if(source == sbH)
        {
            contentXPos += ((double)movement/size.width)*contentSize.width;
        }
        repaint();
    }
    
    
    
    @Override public void paint(Graphics g)
    {
        updateContent();
        
        super.paint(g);
    }
    
    
    
    
    /************Mouse Wheel Listener**************/
    @Override public void mouseWheelMoved(MouseWheelEvent e)
    {
        if(e.getModifiers() == 0)
            contentYPos += ADD*e.getWheelRotation();
        else if(e.getModifiers() == 1)
            contentXPos += ADD*e.getWheelRotation();
        repaint();
    }
    /************Mouse Wheel Listener End**********/
    
    /************Property Change Listener**********/
    @Override public void propertyChange(PropertyChangeEvent e)
    {
        repaint();
    }
    /************Property Change Listener End**********/
}
