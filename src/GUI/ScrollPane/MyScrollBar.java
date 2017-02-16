/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.ScrollPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

/**
 *
 * @author Florian
 */
public class MyScrollBar extends JLabel implements MouseListener,MouseMotionListener
{
    protected static final int VERTICAL = 0;
    protected static final int HORIZONTAL = 1;
    
    private final MyScrollPane scrollPane;
    private Color c;
    private int alignment = VERTICAL;
    private int length = 0;
    private int thickness = 10;
    private Dimension size;
    
    private int lastX = 0;
    private int lastY = 0;
    
    
    
    protected MyScrollBar(MyScrollPane sp)
    {
        super();
        setDoubleBuffered(true);
        scrollPane = sp;
        size = new Dimension();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    protected void setAlignment(int a)
    {
        if(a==0 || a==1)
        {
            alignment = a;
            setSize(length);
        }
    }
    
    protected void setThickness(int t)
    {
        if(t > 0)
        {
            thickness = t;
            setSize(length);
        }
    }
    
    protected void setColor(Color c)
    {
        this.c = c;
        paintImmediately(0,0,size.width,size.height);
    }
    
    @Override public void setSize(int x, int y)
    {
        if(alignment == HORIZONTAL)
            setSize(x);
        else if(alignment == VERTICAL)
            setSize(y);
    }
    @Override public void setSize(Dimension d)
    {
        setSize(d.width,d.height);
    }
    public void setSize(int l)
    {
        length = l;
        if(alignment == HORIZONTAL)
        {
            super.setSize(length, thickness);
        }
        else if(alignment == VERTICAL)
        {
            super.setSize(thickness, length);
        }
        size = getSize();
    }
    
    
    @Override protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setColor(c);
        
        if(alignment == HORIZONTAL)
        {
            if(length < thickness)
            {
                g2.fillOval(0, 0, length, thickness);
            }
            else
            {
                g2.fillOval(0, 0, thickness, thickness);
                g2.fillRect(thickness/2, 0, length-thickness, thickness);
                g2.fillOval(length-thickness, 0, thickness, thickness);
            }
        }
        else if(alignment == VERTICAL)
        {
            if(length < thickness)
            {
                g2.fillOval(0, 0, thickness, length);
            }
            else
            {
                g2.fillOval(0, 0, thickness, thickness);
                g2.fillRect(0, thickness/2, thickness, length-thickness);
                g2.fillOval(0, length-thickness, thickness, thickness);
            }
        }
    }
    
    
    
    
    /*Mouse Listener*/
    @Override public void mouseClicked(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void mousePressed(MouseEvent e)
    {
        if(scrollPane.isEnabled())
        {
            lastX = e.getXOnScreen();
            lastY = e.getYOnScreen();
        }
    }
    @Override public void mouseReleased(MouseEvent e){}
    /*Mouse Listener End*/
    
    /*Mouse Motion Listener*/
    @Override public void mouseDragged(MouseEvent e)
    {
        if(scrollPane.isEnabled())
        {
            if(alignment == HORIZONTAL)
            {
                if(e.getXOnScreen() != lastX)
                    scrollPane.scrollBarMoved(this, e.getXOnScreen()-lastX);
            }
            else if(alignment == VERTICAL)
            {
                if(e.getYOnScreen() != lastY)
                    scrollPane.scrollBarMoved(this, e.getYOnScreen()-lastY);
            }

            lastX = e.getXOnScreen();
            lastY = e.getYOnScreen();
        }
    }
    @Override public void mouseMoved(MouseEvent e){}
    /*Mouse Motion Listener End*/
}
