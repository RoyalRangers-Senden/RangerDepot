/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.guiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import rangerdepot.RangerDepot;

/**
 *
 * @author Florian
 */
public class CloseButton extends JLabel implements MouseListener
{
    private boolean enabled = true;
    private boolean entered = false;
    
    private Dimension size;
    
    private Color color,hoveredColor,crossColor;
    private BufferedImage closeImage_Buffered;
    private Image closeImage;
    private ArrayList<ActionListener> actionListeners;
    
    public CloseButton(int width)
    {
        super();
        addMouseListener(this);
        actionListeners = new ArrayList<ActionListener>();
        size = new Dimension(width,width);
        
        color = new Color(238,238,238);
        hoveredColor = new Color(198,198,198);
        crossColor = new Color(51,51,51);
        setOpaque(true);
        
        super.setSize(size);
        
        try
        {
//            URL url = RangerDepot.class.getResource("../resources/pictures/closeButton.png");
//            System.out.println("URL: "+url.getPath());
            closeImage_Buffered = ImageIO.read(RangerDepot.class.getResource("../resources/pictures/closeButton.png"));
            setImage();
        }catch(Exception e){System.err.println("Fehler: Bild konnte nicht geladen werden!");e.printStackTrace();}
        
        setVisible(true);
    }
    
    public void setSize(int width)
    {
        size.width = width;
        size.height = width;
        super.setSize(size);
        setImage();
    }
    
    public void addActionListener(ActionListener listener)
    {
        if(!actionListeners.contains(listener))
            actionListeners.add(listener);
    }
    public void removeActionListener(ActionListener listener)
    {
        actionListeners.remove(listener);
    }
    
    @Override
    public void setEnabled(boolean e)
    {
        super.setEnabled(e);
        enabled = e;
        if(!enabled)
        {
            setBackground(color);
            entered = false;
        }
    }
    
    public void setColor(int r, int g, int b)
    {
        setColor(new Color(r,g,b));
    }
    public void setColor(Color c)
    {
        color = c;
        if(!entered)
            setBackground(c);
    }
    public void setHoveredColor(Color c)
    {
        hoveredColor = c;
        if(entered)
            setBackground(c);
    }
    public void setHoveredColor(int r, int g, int b)
    {
        setHoveredColor(new Color(r,g,b));
    }
    public void setCrossColor(Color c)
    {
        crossColor = c;
        setImage();
    }
    
    
    
    
    
    private void setImage()
    {
        int width = closeImage_Buffered.getWidth();
        int height = closeImage_Buffered.getHeight();
        WritableRaster raster = closeImage_Buffered.getRaster();
        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                int [] pixels = raster.getPixel(x, y, (int[])null);
                pixels[0] = crossColor.getRed();
                pixels[1] = crossColor.getGreen();
                pixels[2] = crossColor.getBlue();
                raster.setPixel(x, y, pixels);
            }
        }
        
        closeImage = closeImage_Buffered.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(closeImage));
    }
    
    
    //========================================================================//
    
    //<editor-fold> Mouse Listener
    @Override public void mouseClicked(MouseEvent e)
    {
        if(enabled)
        {
            for(int i=0;i<actionListeners.size();i++)
                actionListeners.get(i).actionPerformed(new ActionEvent(this,1001,"Close Button Clicked"));
            entered = false;
            setBackground(color);
        }
    }
    @Override public void mouseEntered(MouseEvent e)
    {
        if(enabled)
        {
            entered = true;
            setBackground(hoveredColor);
        }
    }
    @Override public void mouseExited(MouseEvent e)
    {
        if(enabled)
        {
            entered = false;
            setBackground(color);
        }
        
    }
    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    //</editor-fold> Mouse Listener
}
