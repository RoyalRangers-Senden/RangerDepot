package GUI.Menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Florian
 */
public class MyMenuItem extends JLabel implements MouseListener
{
    private final MyMenu menu;
    private final MyMenuItem parentItem;
    
    private boolean selected = false;
    private boolean mouseEntered = false;
    private boolean isButton = false;
    
    private ArrayList<MyMenuItem> subMenu;
    
    
    /*Standard MyMenu-Item Konstuktor*/
    public MyMenuItem(MyMenu m,String title,Dimension d)
    {
        super(title);
        menu = m;
        parentItem = null;
        setForeground(menu.getTextColor());
        setFont(getFont().deriveFont(Font.BOLD,14));
        setOpaque(true);setSize(d);
        addMouseListener(this);
        menu.panel.add(this);
        
        subMenu = new ArrayList<MyMenuItem>();
    }
    /*Konstruktor for Submenu-Item*/
    public MyMenuItem(MyMenu m,MyMenuItem parent,String title,Dimension d)
    {
        super(title);
        menu = m;
        parentItem = parent;
        setForeground(menu.getTextColor());
        setFont(getFont().deriveFont(Font.PLAIN,12));
        setOpaque(true);setSize(d);
        addMouseListener(this);
        menu.subPanel.add(this);
        
        subMenu = null;
    }
    
    
    
    public void addSubMenuItem(MyMenuItem item)
    {
        if(parentItem == null)
            subMenu.add(item);
    }
    
    public void subItemClicked(MyMenuItem source)
    {
        menu.itemClicked(this, subMenu.indexOf(source));
    }
    
    
    public void setButton(boolean b)
    {
        if(parentItem != null)
        {
            isButton = b;
            menu.updateItems();
        }
    }
    public boolean isButton()
    {
        return isButton;
    }
    
    
    
    public void setSelected(boolean s)
    {
        selected = s;
    }
    public boolean isSelected()
    {
        return selected;
    }
    public boolean isMouseEntered()
    {
        return mouseEntered;
    }
    public ArrayList<MyMenuItem> getSubMenu()
    {
        return subMenu;
    }
    
    
    
    
    
    /************Mouse Listener*******************/
    @Override public void mouseClicked(MouseEvent e)
    {
        if(menu.enabled)
        {
            setBackground(menu.getStandardColor());
            if(parentItem != null)
                parentItem.subItemClicked(this);
            else
                menu.itemClicked(this,-1);
        }
    }
    @Override public void mouseEntered(MouseEvent e)
    {
        mouseEntered = true;
        if(!selected && menu.enabled)
            setBackground(menu.getHoveredColor());
    }
    @Override public void mouseExited(MouseEvent e)
    {
        mouseEntered = false;
        if(!selected)
            setBackground(menu.getStandardColor());
    }
    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    /************Mouse Listener End***************/
}
