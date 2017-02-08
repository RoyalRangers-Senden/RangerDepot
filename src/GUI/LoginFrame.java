package GUI;

import GUI.guiElements.CloseButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.HORIZONTAL;
import static javax.swing.SwingConstants.VERTICAL;
import rangerdepot.RangerDepot;
import rangerdepot.Strings;


public class LoginFrame extends JFrame implements ActionListener,FocusListener,MouseListener,MouseMotionListener
{
    private static final int BACKGROUND_ALPHA = 30;
    private static final int BACKGROUND_SIZE = 500;
    
    private final RangerDepot depot;
    private final GUI gui;
    
    private final Dimension screenSize;
    private Dimension size;
    private final ErrorDialog error;
    
    //title
    private final JPanel titlePanel;
    private final JLabel titleL;
    private final CloseButton closeB;
    
    private final JPanel data;
    private final JLabel ipL,userL,pwL,rangerLogo;
    private final JTextField ipTF,userTF;
    private final JPasswordField pwTF;
    
    //login
    private final JPanel loginPanel;
    private final JButton enterButton,h;
    
    //motion
    private int lastX,lastY;
    
    
    public LoginFrame(RangerDepot depot, GUI gui)// check Resources
    {
        super("Login");
        this.depot = depot;
        this.gui = gui;
        setUndecorated(true);
        
        addFocusListener(this);
        size = new Dimension(250,330);
        h = new JButton();
        h.setSize(0,0);h.setLocation(0,0);h.setVisible(true);
        setLayout(null);
        
        screenSize = gui.screenSize;
        setSize(size);
        setResizable(false);
        setLocation((screenSize.width-size.width)/2,(screenSize.height-size.height)/2);
        
        //<editor-fold> Image
        try
        {
            setIconImage(ImageIO.read(RangerDepot.class.getResource("../resources/pictures/logo/RoyalRangersLogo_Icon.png")));
        }catch(Exception e){System.err.println("Error: Icon konnte nicht geladen werden!");}
        //</editor-fold> Image
        
        error = new ErrorDialog(gui,this);
        
        //<editor-fold> Title
        titlePanel = new JPanel();
        titlePanel.setSize(size.width, 50);titlePanel.setLocation(0,0);
        titlePanel.setBackground(new Color(161,2,0));
        titlePanel.setLayout(null);
        titlePanel.addMouseListener(this);
        titlePanel.addMouseMotionListener(this);
        
        titleL = new JLabel("Royal-Rangers Datenbank");
        titleL.setSize(220,40);titleL.setLocation(0,5);
        titleL.setFont(titleL.getFont().deriveFont((float)18));
        titlePanel.add(titleL);
        titleL.addMouseListener(this);
        titleL.addMouseMotionListener(this);
        
        closeB = new CloseButton(30);
        closeB.addActionListener(this);
        closeB.setLocation(size.width-40,10);
        closeB.setColor(161,2,0);closeB.setHoveredColor(128,1,0);
        titlePanel.add(closeB);
        
        titlePanel.add(h);titlePanel.remove(h);
        //</editor-fold> Title
        
        //<editor-fold> Data
        ipL = new JLabel("IP:");ipL.setSize(70,25);ipL.setLocation(0,5);ipL.setHorizontalAlignment(SwingConstants.RIGHT);
        ipTF = new JTextField("localhost");
        ipTF.setSize(150,25);ipTF.setLocation(81,5);ipTF.addActionListener(this);
        
        userL = new JLabel("User:");userL.setSize(70,25);userL.setLocation(0,35);userL.setHorizontalAlignment(SwingConstants.RIGHT);
        userTF = new JTextField();
        userTF.setSize(150,25);userTF.setLocation(81,35);userTF.addActionListener(this);
        
        pwL = new JLabel("Passwort:");pwL.setSize(70,25);pwL.setLocation(0,65);pwL.setHorizontalAlignment(SwingConstants.RIGHT);
        pwTF = new JPasswordField();
        pwTF.setSize(150,25);pwTF.setLocation(81,65);pwTF.addActionListener(this);
        
        data = new JPanel();data.setSize(size.width,size.height-90);data.setLocation(0,51);data.setLayout(null);data.setOpaque(false);
        data.add(ipL);data.add(ipTF);
        data.add(userL);data.add(userTF);
        data.add(pwL);data.add(pwTF);
        data.add(h);data.remove(h);
        //</editor-fold> Data
        
        //<editor-fold> Login
        loginPanel = new JPanel();
        loginPanel.setSize(size.width,40);loginPanel.setLocation(0,size.height-40);
        loginPanel.setBackground(new Color(230,230,230));
        loginPanel.setLayout(null);
                
        enterButton = new JButton("Login");
        enterButton.setSize(100,30);enterButton.setLocation(120,5);enterButton.addActionListener(this);
        
        loginPanel.add(enterButton);
        loginPanel.add(h);loginPanel.remove(h);
        //</editor-fold> Login
        
        /****/
        
        //<editor-fold> Ranger Logo
        rangerLogo = new JLabel();
        rangerLogo.setSize(BACKGROUND_SIZE, BACKGROUND_SIZE);
//        rangerLogo.setLocation(size.width-(BACKGROUND_SIZE/2), (-(BACKGROUND_SIZE/2))-(BACKGROUND_SIZE*59/1135));//rechts oben
//        rangerLogo.setLocation(-(BACKGROUND_SIZE/2), (-(BACKGROUND_SIZE/2))-(BACKGROUND_SIZE*59/1135));//links oben
        rangerLogo.setLocation(-(BACKGROUND_SIZE/2), (size.height-(BACKGROUND_SIZE/2))-(BACKGROUND_SIZE*59/1135));//links unten
        
        try
        {
            BufferedImage image = ImageIO.read(RangerDepot.class.getResource("../resources/pictures/logo/RoyalRangersLogo.png"));
            WritableRaster r = image.getRaster();
            int tempWidht = image.getWidth();
            int tempHeight = image.getHeight();
            for(int x=0;x<tempWidht;x++)
                for(int y=0;y<tempHeight;y++)
                {
                    int [] pixel = r.getPixel(x, y, (int[])null);
                    if(pixel[3] != 0)
                        pixel[3] = BACKGROUND_ALPHA;
                    r.setPixel(x, y, pixel);
                }
            
            
            rangerLogo.setIcon(new ImageIcon(image.getScaledInstance(BACKGROUND_SIZE,BACKGROUND_SIZE, Image.SCALE_SMOOTH)));
        }catch(Exception e){System.err.println("Fehler: Bild konnte nicht geladen werden!");e.printStackTrace();}
        //</editor-fold> Ranger Logo
        
        //====================================================================//
        
        add(titlePanel);
        add(data);
        add(loginPanel);
        add(h);remove(h);
    }
    
    public void error(String err)
    {
        error.showError(err);
    }
    
    
    public void setStatus(boolean s)
    {
        userTF.setEnabled(s);
        ipTF.setEnabled(s);
        pwTF.setEnabled(s);
        enterButton.setEnabled(s);
        ipL.setEnabled(s);
        userL.setEnabled(s);
        pwL.setEnabled(s);
    }
    public void open()
    {
        update();
        
        setLocation((screenSize.width-size.width)/2,(screenSize.height-size.height)/2);
        setVisible(true);
        userTF.requestFocus();
    }
    public void update()
    {
//        ipTF.setText(depot.lastIP);
        userTF.setText("");
        pwTF.setText("");
        userTF.requestFocus();
    }
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof CloseButton)
        {
            System.exit(0);
        }
        else
        {
            if(ipTF.getText().equals(""))
                error(Strings.IP_ERROR);
            else if(userTF.getText().equals(""))
                error(Strings.USERNAME_ERROR);

            if(!depot.logIn(ipTF.getText(),userTF.getText(),pwTF.getText()))
                update();
        }
    }
    //</editor-fold> Action Listener

    //<editor-fold> Focus Listener
    @Override public void focusGained(FocusEvent e)
    {
        userTF.requestFocus();
    }
    @Override public void focusLost(FocusEvent e){}
    //</editor-fold> Focus Listener
    
    
    //<editor-fold> Mouse Listener
    @Override public void mouseClicked(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void mousePressed(MouseEvent e)
    {
        lastX = e.getXOnScreen();
        lastY = e.getYOnScreen();
    }
    @Override public void mouseReleased(MouseEvent e){}
    //</editor-fold> Mouse Listener
    
    //<editor-fold> Mouse Motion Listener
    @Override public void mouseDragged(MouseEvent e)
    {
        int newX = getLocation().x;
        int newY = getLocation().y;
        
        newX += e.getXOnScreen()-lastX;
        newY += e.getYOnScreen()-lastY;
        
        setLocation(newX,newY);

        lastX = e.getXOnScreen();
        lastY = e.getYOnScreen();
    }
    @Override public void mouseMoved(MouseEvent e){}
    //<editor-fold> Mouse Motion Listener
}
