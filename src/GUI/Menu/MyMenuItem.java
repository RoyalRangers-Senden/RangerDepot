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
    protected MyMenuItem(MyMenu m,String title,Dimension d)
    {
        super(title);
        menu = m;
        parentItem = null;
        setForeground(menu.getTextColor());
        setFont(m.getBigFont());
        setOpaque(true);setSize(d);
        addMouseListener(this);
        menu.panel.add(this);
        
        subMenu = new ArrayList<MyMenuItem>();
    }
    /*Konstruktor for Submenu-Item*/
    protected MyMenuItem(MyMenu m,MyMenuItem parent,String title,Dimension d)
    {
        super(title);
        menu = m;
        parentItem = parent;
        setForeground(menu.getTextColor());
        setFont(m.getSmallFont());
        setOpaque(true);setSize(d);
        addMouseListener(this);
        menu.subPanel.add(this);
        
        subMenu = null;
    }
    
    
    
    protected void addSubMenuItem(MyMenuItem item)
    {
        if(parentItem == null)
            subMenu.add(item);
    }
    
    protected void subItemClicked(MyMenuItem source)
    {
        menu.itemClicked(this, subMenu.indexOf(source));
    }
    
    
    protected void setButton(boolean b)
    {
        if(parentItem != null)
        {
            isButton = b;
            menu.updateItems();
        }
    }
    protected boolean isButton()
    {
        return isButton;
    }
    
    
    
    protected void setSelected(boolean s)
    {
        selected = s;
    }
    protected boolean isSelected()
    {
        return selected;
    }
    protected boolean isMouseEntered()
    {
        return mouseEntered;
    }
    protected ArrayList<MyMenuItem> getSubMenu()
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
