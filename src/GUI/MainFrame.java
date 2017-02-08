/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.guiElements.CloseButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rangerdepot.RangerDepot;

/**
 *
 * @author Florian
 */
public class MainFrame extends JFrame implements ActionListener,MouseListener,MouseMotionListener
{
    private final GUI gui;
    private boolean initialized = false;
    
    private int lastX,lastY;        //Mouse Listener
    private final JLabel borderL,borderR,borderU,borderO;
    private final JLabel borderOL,borderOR,borderUL,borderUR;
    private static final int BORDER_WIDTH = 3;
    private final Color borderColor = new Color(220,220,220);
    
    private Dimension size,screenSize,minSize,resizeSize;
    
    private final JPanel title;
    private final JLabel titleL,titleLogo;
    private final CloseButton close;
    private final JButton h;
    
    public MainFrame(GUI gui)
    {
        super("Royal-Ranger Datenbank");
        this.gui = gui;
        size = new Dimension(1200,700);
        resizeSize = (Dimension)size.clone();
        minSize = new Dimension(400,100);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);
        setLocation((screenSize.width-size.width)/2, (screenSize.height-size.height)/2);
        setUndecorated(true);
        setLayout(null);
        
        h = new JButton();h.setSize(1,1);h.setLocation(-1,-1);
        
        //<editor-fold> Border
        borderL = new JLabel();
        borderL.setBackground(borderColor);borderL.setOpaque(true);
        borderL.setSize(BORDER_WIDTH, size.height-(BORDER_WIDTH*2));borderL.setLocation(0, BORDER_WIDTH);
        borderL.addMouseListener(this);borderL.addMouseMotionListener(this);
        borderL.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));

        borderR = new JLabel();
        borderR.setBackground(borderColor);borderR.setOpaque(true);
        borderR.setSize(BORDER_WIDTH, size.height-(BORDER_WIDTH*2));borderR.setLocation(size.width-BORDER_WIDTH, BORDER_WIDTH);
        borderR.addMouseListener(this);borderR.addMouseMotionListener(this);
        borderR.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        
        borderO = new JLabel();
        borderO.setBackground(borderColor);borderO.setOpaque(true);
        borderO.setSize(size.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderO.setLocation(BORDER_WIDTH, 0);
        borderO.addMouseListener(this);borderO.addMouseMotionListener(this);
        borderO.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        
        borderU = new JLabel();
        borderU.setBackground(borderColor);borderU.setOpaque(true);
        borderU.setSize(size.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderU.setLocation(BORDER_WIDTH, size.height-BORDER_WIDTH);
        borderU.addMouseListener(this);borderU.addMouseMotionListener(this);
        borderU.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        
        borderOL = new JLabel();
        borderOL.setBackground(borderColor);borderOL.setOpaque(true);
        borderOL.setSize(BORDER_WIDTH, BORDER_WIDTH);borderOL.setLocation(0, 0);
        borderOL.addMouseListener(this);borderOL.addMouseMotionListener(this);
        borderOL.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        
        borderOR = new JLabel();
        borderOR.setBackground(borderColor);borderOR.setOpaque(true);
        borderOR.setSize(BORDER_WIDTH, BORDER_WIDTH);borderOR.setLocation(size.width-BORDER_WIDTH, 0);
        borderOR.addMouseListener(this);borderOR.addMouseMotionListener(this);
        borderOR.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        
        borderUL = new JLabel();
        borderUL.setBackground(borderColor);borderUL.setOpaque(true);
        borderUL.setSize(BORDER_WIDTH, BORDER_WIDTH);borderUL.setLocation(0, size.height-BORDER_WIDTH);
        borderUL.addMouseListener(this);borderUL.addMouseMotionListener(this);
        borderUL.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
        
        borderUR = new JLabel();
        borderUR.setBackground(borderColor);borderUR.setOpaque(true);
        borderUR.setSize(BORDER_WIDTH, BORDER_WIDTH);borderUR.setLocation(size.width-BORDER_WIDTH, size.height-BORDER_WIDTH);
        borderUR.addMouseListener(this);borderUR.addMouseMotionListener(this);
        borderUR.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        //</editor-fold> Border
        
        
        //<editor-fold> Title
        title = new JPanel();
        title.setSize(size.width-(BORDER_WIDTH*2), 40-BORDER_WIDTH);title.setLocation(BORDER_WIDTH, BORDER_WIDTH);
        title.setBackground(borderColor);
        title.addMouseListener(this);
        title.addMouseMotionListener(this);
        title.setLayout(null);
        
        titleLogo = new JLabel();
        titleLogo.setSize(36,36);
        titleLogo.setLocation(3,0);
        try
        {
            BufferedImage image = ImageIO.read(RangerDepot.class.getResource("../resources/pictures/logo/RoyalRangersLogo.png"));
            titleLogo.setIcon(new ImageIcon(image.getScaledInstance(36,36, Image.SCALE_SMOOTH)));
        }catch(Exception e){System.err.println("Fehler: Bild konnte nicht geladen werden!");e.printStackTrace();}
        titleLogo.addMouseListener(this);
        titleLogo.addMouseMotionListener(this);
        title.add(titleLogo);
        
        titleL = new JLabel("Royal-Ranger Datenbank");
        titleL.setSize(300,40-BORDER_WIDTH);titleL.setLocation(50-BORDER_WIDTH, 0);
        titleL.setFont(titleL.getFont().deriveFont(Font.BOLD).deriveFont((float)22));
        titleL.addMouseListener(this);
        titleL.addMouseMotionListener(this);
        title.add(titleL);
        
        close = new CloseButton(36);
        close.setLocation(size.width-(38+BORDER_WIDTH),0);
        close.addActionListener(this);
        title.add(close);
        
        title.add(h);title.remove(h);
        //</editor-fold> Title
        
        
        add(borderL);add(borderR);add(borderO);add(borderU);
        add(borderOL);add(borderOR);add(borderUL);add(borderUR);
        add(title);
        add(h);remove(h);
        setVisible(true);
        
        initialized = true;
    }
    
    
    @Override public void setSize(Dimension s)
    {
        setSize(s.width, s.height);
    }
    @Override public void setSize(int x, int y)
    {
        if(x < minSize.width)
            x = minSize.width;
        if(y < minSize.height)
            y = minSize.height;
        
        super.setSize(x,y);
        size.width = x;
        size.height = y;
        
        if(initialized)
        {
            borderL.setSize(BORDER_WIDTH, size.height-(BORDER_WIDTH*2));
            borderL.setLocation(0, BORDER_WIDTH);
            borderR.setSize(BORDER_WIDTH, size.height-(BORDER_WIDTH*2));borderR.setLocation(size.width-BORDER_WIDTH, BORDER_WIDTH);
            borderO.setSize(size.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderO.setLocation(BORDER_WIDTH, 0);
            borderU.setSize(size.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderU.setLocation(BORDER_WIDTH, size.height-BORDER_WIDTH);
            
            borderOR.setLocation(size.width-BORDER_WIDTH, 0);
            borderUL.setLocation(0, size.height-BORDER_WIDTH);
            borderUR.setLocation(size.width-BORDER_WIDTH, size.height-BORDER_WIDTH);

            close.setLocation(size.width-(38+BORDER_WIDTH),0);
        }
    }
    
    
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof CloseButton)
        {
            gui.exit();
        }
    }
    //</editor-fold> Action Listener
    
    //<editor-fold> Mouse Listener
    @Override public void mouseClicked(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void mousePressed(MouseEvent e)
    {
        lastX = e.getXOnScreen();
        lastY = e.getYOnScreen();
        
        resizeSize.width = size.width;
        resizeSize.height = size.height;
    }
    @Override public void mouseReleased(MouseEvent e){}
    //</editor-fold> Mouse Listener
    
    //<editor-fold> Mouse Motion Listener
    @Override public void mouseDragged(MouseEvent e)
    {
        int oldX = getLocation().x;
        int oldY = getLocation().y;

        int newX = e.getXOnScreen()-lastX;
        int newY = e.getYOnScreen()-lastY;
        
        lastX = e.getXOnScreen();
        lastY = e.getYOnScreen();
        
        if(e.getSource() instanceof JLabel)
        {
            JLabel l = (JLabel)e.getSource();
            if(l == titleL || l == titleLogo)
            {
                setLocation(oldX+newX, oldY+newY);
            }
            else if(l == borderL)
            {
                resizeSize.width -= newX;
                setSize(resizeSize);
                setLocation(oldX+newX, oldY);
            }
            else if(l == borderR)
            {
                resizeSize.width += newX;
                setSize(resizeSize);
            }
            else if(l == borderO)
            {
                resizeSize.height -= newY;
                setSize(resizeSize);
                setLocation(oldX, oldY+newY);
            }
            else if(l == borderU)
            {
                resizeSize.height += newY;
                setSize(resizeSize);
            }
            else if(l == borderOL)
            {
                resizeSize.width -= newX;
                resizeSize.height -= newY;
                setSize(resizeSize);
                setLocation(oldX+newX, oldY+newY);
            }
            else if(l == borderOR)
            {
                resizeSize.width += newX;
                resizeSize.height -= newY;
                setSize(resizeSize);
                setLocation(oldX, oldY+newY);
            }
            else if(l == borderUL)
            {
                resizeSize.width -= newX;
                resizeSize.height += newY;
                setSize(resizeSize);
                setLocation(oldX+newX, oldY);
            }
            else if(l == borderUR)
            {
                resizeSize.width += newX;
                resizeSize.height += newY;
                setSize(resizeSize);
            }
        }
        else if(e.getSource() instanceof JPanel)
        {
            setLocation(oldX+newX, oldY+newY);
        }
    }
    @Override public void mouseMoved(MouseEvent e){}
    //<editor-fold> Mouse Motion Listener
}
