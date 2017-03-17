/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.UserPanel.AdminContent;
import GUI.UserPanel.Content;
import GUI.UserPanel.LeiterContent;
import GUI.UserPanel.RangerContent;
import GUI.guiElements.MyButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import rangerdepot.RangerDepot;
import rangerdepot.Users.Leiter;
import rangerdepot.Users.Ranger;
import rangerdepot.Users.User;

/**
 *
 * @author Florian
 */
public class MainFrame extends JFrame implements WindowStateListener,ActionListener,MouseListener,MouseMotionListener
{
    private final GUI gui;
    private final User user;
    private boolean initialized = false;

    
    private int lastX,lastY;        //Mouse Listener
    private final JLabel borderL,borderR,borderU,borderO;
    private final JLabel borderOL,borderOR,borderUL,borderUR;
    private static final int BORDER_WIDTH = 3;
    private final Timer doubleClickTimer;
    private static final int TIMER_TIME = 250;
    private boolean isClicked = false;
    
    public static final Color BORDER_COLOR = new Color(210,197,164);
    public static final Color BORDER_HOVERED_COLOR = new Color(200,185,145);
    public static final Color BACKGROUND_COLOR = new Color(244,241,233);
    public static final Color BACKGROUND_HOVERED_COLOR = new Color(239,234,223);
    public static final Color MENU_COLOR = new Color(226,217,196);
    public static final Color MENU_HOVERED_COLOR = new Color(217,206,179);
    
    private Dimension size,minSize,resizeSize,minimiseSize;
    private Point location;
    private boolean maximized = false;
    
    //Title
    private final JPanel title;
    private final JLabel titleL,titleLogo;
    private final MyButton closeButton,minimizeButton,maximizeButton;
    private final JButton h;
    
    //Content
    private final Content contentPanel;
        
    
    public MainFrame(GUI g)
    {
        super("Royal-Ranger Datenbank");
        this.gui = g;
        this.user = gui.getUser();
        size = new Dimension(800,480);
        resizeSize = (Dimension)size.clone();
        minSize = new Dimension(420,100);
        minimiseSize = size;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);
        setLocation((screenSize.width-size.width)/2, (screenSize.height-size.height)/2);
        location = getLocation();
        setUndecorated(true);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowStateListener(this);
        addWindowListener(new WindowAdapter(){
            @Override public void windowClosed(WindowEvent e)
                {
                    gui.exit();
                };
        });
        
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        h = new JButton();h.setSize(1,1);h.setLocation(-1,-1);
        
        
        //<editor-fold> Image
        try
        {
            setIconImage(ImageIO.read(RangerDepot.class.getResource("../resources/pictures/logo/RoyalRangersLogo_Icon.png")));
        }catch(Exception e){System.err.println("Error: Icon konnte nicht geladen werden!");}
        //</editor-fold> Image
        
        
        //====================================================================//
        
        //<editor-fold> Border
        borderL = new JLabel();
        borderL.setBackground(BORDER_COLOR);borderL.setOpaque(true);
        borderL.setSize(BORDER_WIDTH, size.height-(BORDER_WIDTH*2));borderL.setLocation(0, BORDER_WIDTH);
        borderL.addMouseListener(this);borderL.addMouseMotionListener(this);
        borderL.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));

        borderR = new JLabel();
        borderR.setBackground(BORDER_COLOR);borderR.setOpaque(true);
        borderR.setSize(BORDER_WIDTH, size.height-(BORDER_WIDTH*2));borderR.setLocation(size.width-BORDER_WIDTH, BORDER_WIDTH);
        borderR.addMouseListener(this);borderR.addMouseMotionListener(this);
        borderR.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        
        borderO = new JLabel();
        borderO.setBackground(BORDER_COLOR);borderO.setOpaque(true);
        borderO.setSize(size.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderO.setLocation(BORDER_WIDTH, 0);
        borderO.addMouseListener(this);borderO.addMouseMotionListener(this);
        borderO.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        
        borderU = new JLabel();
        borderU.setBackground(BORDER_COLOR);borderU.setOpaque(true);
        borderU.setSize(size.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderU.setLocation(BORDER_WIDTH, size.height-BORDER_WIDTH);
        borderU.addMouseListener(this);borderU.addMouseMotionListener(this);
        borderU.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        
        borderOL = new JLabel();
        borderOL.setBackground(BORDER_COLOR);borderOL.setOpaque(true);
        borderOL.setSize(BORDER_WIDTH, BORDER_WIDTH);borderOL.setLocation(0, 0);
        borderOL.addMouseListener(this);borderOL.addMouseMotionListener(this);
        borderOL.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        
        borderOR = new JLabel();
        borderOR.setBackground(BORDER_COLOR);borderOR.setOpaque(true);
        borderOR.setSize(BORDER_WIDTH, BORDER_WIDTH);borderOR.setLocation(size.width-BORDER_WIDTH, 0);
        borderOR.addMouseListener(this);borderOR.addMouseMotionListener(this);
        borderOR.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        
        borderUL = new JLabel();
        borderUL.setBackground(BORDER_COLOR);borderUL.setOpaque(true);
        borderUL.setSize(BORDER_WIDTH, BORDER_WIDTH);borderUL.setLocation(0, size.height-BORDER_WIDTH);
        borderUL.addMouseListener(this);borderUL.addMouseMotionListener(this);
        borderUL.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
        
        borderUR = new JLabel();
        borderUR.setBackground(BORDER_COLOR);borderUR.setOpaque(true);
        borderUR.setSize(BORDER_WIDTH, BORDER_WIDTH);borderUR.setLocation(size.width-BORDER_WIDTH, size.height-BORDER_WIDTH);
        borderUR.addMouseListener(this);borderUR.addMouseMotionListener(this);
        borderUR.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        
        doubleClickTimer = new Timer(TIMER_TIME,this);
        //</editor-fold> Border
        
        
        //<editor-fold> Title
        title = new JPanel();
        title.setSize(size.width-(BORDER_WIDTH*2), 40-BORDER_WIDTH);title.setLocation(BORDER_WIDTH, BORDER_WIDTH);
        title.setBackground(BORDER_COLOR);
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
        titleL.setFont(GUI.GUI_FONT.deriveFont((float)22));
        titleL.addMouseListener(this);
        titleL.addMouseMotionListener(this);
        title.add(titleL);
        
        closeButton = new MyButton(25, "closeButton", Image.SCALE_SMOOTH);
        closeButton.setLocation(size.width-(32+BORDER_WIDTH),7-BORDER_WIDTH);
        closeButton.setColor(BORDER_COLOR);
        closeButton.setHoveredColor(BORDER_HOVERED_COLOR);
        closeButton.addActionListener(this);
        title.add(closeButton);
        
        maximizeButton = new MyButton(25, "maximizeButton", Image.SCALE_DEFAULT);
        maximizeButton.setLocation(size.width-(28+32+BORDER_WIDTH),7-BORDER_WIDTH);
        maximizeButton.setColor(BORDER_COLOR);
        maximizeButton.setHoveredColor(BORDER_HOVERED_COLOR);
        maximizeButton.addActionListener(this);
        title.add(maximizeButton);
        
        minimizeButton = new MyButton(25, "minimizeButton", Image.SCALE_DEFAULT);
        minimizeButton.setLocation(size.width-(28+28+32+BORDER_WIDTH),7-BORDER_WIDTH);
        minimizeButton.setColor(BORDER_COLOR);
        minimizeButton.setHoveredColor(BORDER_HOVERED_COLOR);
        minimizeButton.addActionListener(this);
        title.add(minimizeButton);
        
        title.add(h);title.remove(h);
        //</editor-fold> Title
        
        //<editor-fold> Content
        if(user instanceof Ranger)
        {
            contentPanel = new RangerContent(gui,user);
            System.out.println("Ranger");
        }
        else
        {
            if(((Leiter)user).isAdmin())
            {
                contentPanel = new AdminContent(gui,user);
                System.out.println("Admin");
            }
            else
            {
                contentPanel = new LeiterContent(gui,user);
                System.out.println("Leiter");
            }
        }
        
        contentPanel.setSize(size.width-BORDER_WIDTH*2, size.height-(BORDER_WIDTH+40));
        contentPanel.setLocation(BORDER_WIDTH, 40);
        //</editor-fold> Content
                
        
        add(borderL);add(borderR);add(borderO);add(borderU);
        add(borderOL);add(borderOR);add(borderUL);add(borderUR);
        add(title);
        add(contentPanel);
        add(h);remove(h);
        setVisible(true);
        
        initialized = true;
    }
    
    
    @Override public void setLocation(Point p)
    {
        setLocation(p.x,p.y);
    }
    @Override public void setLocation(int x,int y)
    {
        if(!maximized)
            location = getLocation();
        super.setLocation(x, y);
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
        
        updateComponentSize();
    }
    private void updateComponentSize()
    {
        if(initialized)
        {
            Dimension tempSize;
            if(maximized)
            {
                Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                tempSize = new Dimension(rect.width,rect.height);
            }
            else
                tempSize = size;
            
            borderL.setSize(BORDER_WIDTH, tempSize.height-(BORDER_WIDTH*2));
            borderL.setLocation(0, BORDER_WIDTH);
            borderR.setSize(BORDER_WIDTH, tempSize.height-(BORDER_WIDTH*2));borderR.setLocation(tempSize.width-BORDER_WIDTH, BORDER_WIDTH);
            borderO.setSize(tempSize.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderO.setLocation(BORDER_WIDTH, 0);
            borderU.setSize(tempSize.width-(BORDER_WIDTH*2), BORDER_WIDTH);borderU.setLocation(BORDER_WIDTH, tempSize.height-BORDER_WIDTH);
            
            borderOR.setLocation(tempSize.width-BORDER_WIDTH, 0);
            borderUL.setLocation(0, tempSize.height-BORDER_WIDTH);
            borderUR.setLocation(tempSize.width-BORDER_WIDTH, tempSize.height-BORDER_WIDTH);

            title.setSize(tempSize.width-(BORDER_WIDTH*2), 40-BORDER_WIDTH);
            closeButton.setLocation(tempSize.width-(32+BORDER_WIDTH),7-BORDER_WIDTH);
            maximizeButton.setLocation(tempSize.width-(28+32+BORDER_WIDTH),7-BORDER_WIDTH);
            minimizeButton.setLocation(tempSize.width-(28+28+32+BORDER_WIDTH),7-BORDER_WIDTH);
            
            
            contentPanel.setSize(tempSize.width-BORDER_WIDTH*2, tempSize.height-(BORDER_WIDTH+40));
        }
    }
    
    
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof MyButton)
        {
            MyButton mb = (MyButton)e.getSource();
            if(mb == closeButton)
                gui.exit();
            else if(mb == maximizeButton)
            {
                if(maximized)
                {
                    maximized = false;
                    setLocation(location);
                    setSize(minimiseSize);
                }
                else
                {
                    maximized = true;
                    minimiseSize = (Dimension)size.clone();
                    Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                    setLocation(0,0);
                    setSize(new Dimension(rect.width,rect.height));
                }
                updateComponentSize();
            }
            else if(mb == minimizeButton)
                setExtendedState(JFrame.ICONIFIED);
        }
        else if(e.getSource() instanceof Timer)
        {
            isClicked = false;
        }
    }
    //</editor-fold> Action Listener
    
    //<editor-fold> Mouse Listener
    @Override public void mouseClicked(MouseEvent e)
    {
        if(!isClicked)
        {
            isClicked = true;
            doubleClickTimer.restart();
        }
        else
        {
            if(maximized)
            {
                maximized = false;
                setLocation(location);
                setSize(minimiseSize);
            }
            else
            {
                maximized = true;
                minimiseSize = (Dimension)size.clone();
                Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                setLocation(0,0);
                setSize(new Dimension(rect.width,rect.height));
            }
            updateComponentSize();
        }
    }
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

        int mouseOffsetX = e.getXOnScreen()-lastX;
        int mouseOffsetY = e.getYOnScreen()-lastY;

        lastX = e.getXOnScreen();
        lastY = e.getYOnScreen();

        if(e.getSource() instanceof JLabel)
        {
            JLabel l = (JLabel)e.getSource();
            if(l == titleL || l == titleLogo)
            {
                if(maximized)
                {
                    setLocation(e.getXOnScreen()-150, e.getYOnScreen()-15);
                    setSize(minimiseSize);

                    maximized = false;
                    updateComponentSize();
                }
                else
                    setLocation(oldX+mouseOffsetX, oldY+mouseOffsetY);
            }
            //<editor-fold> Border
            else if(l == borderL)
            {
                resizeSize.width -= mouseOffsetX;
                if(resizeSize.width < minSize.width)
                {
                    int tempInt = 0;
                    tempInt = size.width - minSize.width;
                    setLocation(oldX+tempInt, oldY);
                }
                else
                    setLocation(oldX+mouseOffsetX, oldY);
                setSize(resizeSize);
            }
            else if(l == borderR)
            {
                resizeSize.width += mouseOffsetX;
                setSize(resizeSize);
            }
            else if(l == borderO)
            {
                resizeSize.height -= mouseOffsetY;
                if(resizeSize.height < minSize.height)
                {
                    int tempInt = 0;
                    tempInt = size.height - minSize.height;
                    setLocation(oldX, oldY+tempInt);
                }
                else
                    setLocation(oldX, oldY+mouseOffsetY);
                setSize(resizeSize);
            }
            else if(l == borderU)
            {
                resizeSize.height += mouseOffsetY;
                setSize(resizeSize);
            }
            else if(l == borderOL)
            {
                resizeSize.width -= mouseOffsetX;
                resizeSize.height -= mouseOffsetY;
                if(resizeSize.width < minSize.width  ||  resizeSize.height < minSize.height)
                {
                    int newX = mouseOffsetX;
                    int newY = mouseOffsetY;

                    if(resizeSize.width < minSize.width)
                    {
                        int tempInt = 0;
                        newX = size.width - minSize.width;
                        setLocation(oldX+tempInt, oldY);
                    }
                    if(resizeSize.height < minSize.height)
                    {
                        int tempInt = 0;
                        newY = size.height - minSize.height;
                        setLocation(oldX, oldY+tempInt);
                    }

                    setLocation(oldX+newX, oldY+newY);
                }
                else
                    setLocation(oldX+mouseOffsetX, oldY+mouseOffsetY);
                setSize(resizeSize);
            }
            else if(l == borderOR)
            {
                resizeSize.width += mouseOffsetX;
                resizeSize.height -= mouseOffsetY;
                if(resizeSize.height < minSize.height)
                {
                    int tempInt = 0;
                    tempInt = size.height - minSize.height;
                    setLocation(oldX, oldY+tempInt);
                }
                else
                    setLocation(oldX, oldY+mouseOffsetY);
                setSize(resizeSize);
            }
            else if(l == borderUL)
            {
                resizeSize.width -= mouseOffsetX;
                resizeSize.height += mouseOffsetY;
                if(resizeSize.width < minSize.width)
                {
                    int tempInt = 0;
                    tempInt = size.width - minSize.width;
                    setLocation(oldX+tempInt, oldY);
                }
                else
                    setLocation(oldX+mouseOffsetX, oldY);
                setSize(resizeSize);
            }
            else if(l == borderUR)
            {
                resizeSize.width += mouseOffsetX;
                resizeSize.height += mouseOffsetY;
                setSize(resizeSize);
            }
            //</editor-fold> Border
        }
        else if(e.getSource() instanceof JPanel)
        {
            if(maximized)
            {
                setLocation(e.getXOnScreen()-150, e.getYOnScreen()-15);
                setSize(minimiseSize);
                
                maximized = false;
                updateComponentSize();
            }
            else
                setLocation(oldX+mouseOffsetX, oldY+mouseOffsetY);
        }
    }
    @Override public void mouseMoved(MouseEvent e){}
    //</editor-fold> Mouse Motion Listener
    
    //<editor-fold> Window State Listener
    @Override public void windowStateChanged(WindowEvent e)
    {
        switch (e.getNewState()) {
            case JFrame.MAXIMIZED_BOTH:
                setExtendedState(JFrame.NORMAL);
                if(!maximized)
                {
                    maximized = true;
                    minimiseSize = (Dimension)size.clone();
                    Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                    setLocation(0,0);
                    setSize(new Dimension(rect.width,rect.height));
                    updateComponentSize();
                }
                break;
        }
    }
    //</editor-fold> Window State Listener
}
