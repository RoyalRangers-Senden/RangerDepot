package GUI.Menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Florian
 */
public class MyMenu extends JLayeredPane implements ActionListener
{
    private static final Double OFFSET_ADD = (double)3;
    
    private Font font,smallFont;
    private int fontSize,smallFontSize;
    
    private Dimension size;
    private boolean initialized = false;
    protected boolean enabled = true;
    private int selectedItem,selectedItemIndex;
    protected JPanel panel,subPanel;
    
    private Color standardColor,selectedColor,hoveredColor,textColor;
    
    private ArrayList<PropertyChangeListener> propertyListener = new ArrayList<PropertyChangeListener>();
    private ArrayList<String> propertyListenerProperties = new ArrayList<String>();
    private ArrayList<MyMenuItem> menuItems;
    private ArrayList<Double> movementOffset;
    private ArrayList<ActionListener> actionListeners;
    
    private Timer timer;
    
    
    
    public MyMenu(int width)
    {
        super();
        size = new Dimension(width,0);
        setDoubleBuffered(true);
        setMinimumSize(new Dimension(80,30));
        setOpaque(false);setLayout(null);
        
        actionListeners = new ArrayList<ActionListener>();
        
        fontSize = 14;
        smallFontSize = 12;
        font = null;//getFont().deriveFont(Font.BOLD, fontSize);
        smallFont = null;//font.deriveFont(Font.PLAIN, smallFontSize);
        
        timer = new Timer(1000/60,this);
        timer.setRepeats(true);
        
        panel = new JPanel();
        panel.setLocation(0,0);panel.setSize(size);
        panel.setOpaque(false);
        panel.setLayout(null);
        subPanel = new JPanel();
        subPanel.setLocation(0,0);subPanel.setSize(size);
        subPanel.setOpaque(false);
        subPanel.setLayout(null);
        add(panel,200);
        add(subPanel,100);
        
        standardColor = new Color(238,238,238);
        selectedColor = new Color(180,180,180);
        hoveredColor = new Color(220,220,220);
        textColor = new Color(51,51,51);
        
        menuItems = new ArrayList<MyMenuItem>();
        selectedItem = 0;
        selectedItemIndex = -1;
        
        movementOffset = new ArrayList<Double>();
        
        
        setSize(size);
        initialized = true;
    }
    
    public void setSize(int x)
    {
        size.width = x;
        updateItems();
    }
    @Override public void setSize(int x, int y)
    {
        setSize(x);
    }
    @Override public void setSize(Dimension d)
    {
        setSize(d.width);
    }
    @Override public void setEnabled(boolean s)
    {
        super.setEnabled(s);
        enabled = s;
        updateItems();
    }
    @Override public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyListener.add(listener);
        propertyListenerProperties.add("");
        
        super.addPropertyChangeListener(listener);
        for(int i=0;i<menuItems.size();i++)
        {
            MyMenuItem item = menuItems.get(i);
            item.addPropertyChangeListener(listener);
            for(int k=0;k<item.getSubMenu().size();k++)
                item.getSubMenu().get(k).addPropertyChangeListener(listener);
        }
    }
    @Override public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener)
    {
        propertyListener.add(listener);
        propertyListenerProperties.add(propertyName);
        
        super.addPropertyChangeListener(propertyName, listener);
        for(int i=0;i<menuItems.size();i++)
        {
            MyMenuItem item = menuItems.get(i);
            item.addPropertyChangeListener(propertyName,listener);
            for(int k=0;k<item.getSubMenu().size();k++)
                item.getSubMenu().get(k).addPropertyChangeListener(propertyName,listener);
        }
    }
    @Override public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        int tempInt = propertyListener.indexOf(listener);
        propertyListener.remove(tempInt);
        propertyListenerProperties.remove(tempInt);
        
        super.removePropertyChangeListener(listener);
        for(int i=0;i<menuItems.size();i++)
        {
            MyMenuItem item = menuItems.get(i);
            item.removePropertyChangeListener(listener);
            for(int k=0;k<item.getSubMenu().size();k++)
                item.getSubMenu().get(k).removePropertyChangeListener(listener);
        }
    }
    @Override public void removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
    {
        int tempInt = propertyListener.indexOf(listener);
        propertyListener.remove(tempInt);
        propertyListenerProperties.remove(tempInt);
        
        super.removePropertyChangeListener(propertyName, listener);
        for(int i=0;i<menuItems.size();i++)
        {
            MyMenuItem item = menuItems.get(i);
            item.removePropertyChangeListener(propertyName,listener);
            for(int k=0;k<item.getSubMenu().size();k++)
                item.getSubMenu().get(k).removePropertyChangeListener(propertyName,listener);
        }
    }
    
    public void setStandardColor(Color c)
    {
        standardColor = c;
        for(MyMenuItem item : menuItems)
        {
            if(!item.isSelected() && !item.isMouseEntered())
                item.setBackground(c);
            
            for(MyMenuItem subItem : item.getSubMenu())
                if(!subItem.isSelected() && !subItem.isMouseEntered())
                    subItem.setBackground(c);
        }
    }
    public Color getStandardColor()
    {
        return standardColor;
    }
    public void setHoveredColor(Color c)
    {
        hoveredColor = c;
        for(MyMenuItem item : menuItems)
        {
            if(!item.isSelected() && item.isMouseEntered())
                item.setBackground(c);
            
            for(MyMenuItem subItem : item.getSubMenu())
                if(!subItem.isSelected() && subItem.isMouseEntered())
                    subItem.setBackground(c);
        }
    }
    public Color getHoveredColor()
    {
        return hoveredColor;
    }
    public void setSelectedColor(Color c)
    {
        selectedColor = c;
        for(MyMenuItem item : menuItems)
        {
            if(item.isSelected())
                item.setBackground(c);
            
            for(MyMenuItem subItem : item.getSubMenu())
                if(subItem.isSelected())
                    subItem.setBackground(c);
        }
    }
    public Color getSelectedColor()
    {
        return selectedColor;
    }
    public void setTextColor(Color c)
    {
        textColor = c;
        for(MyMenuItem item : menuItems)
        {
            item.setForeground(c);
            
            for(MyMenuItem subItem : item.getSubMenu())
                subItem.setForeground(c);
        }
    }
    public Color getTextColor()
    {
        return textColor;
    }
    
    
    @Override public void setFont(Font f)
    {
        font = f.deriveFont((float)fontSize);
//        font = f.deriveFont(Font.BOLD, fontSize);
        smallFont = f.deriveFont((float)smallFontSize);
        for(MyMenuItem i:menuItems)
        {
            i.setFont(font);
            for(MyMenuItem k:i.getSubMenu())
                k.setFont(smallFont);
        }
    }
    public void setFontSize(int s)
    {
        fontSize = s;
        font = font.deriveFont((float)fontSize);
        for(MyMenuItem i:menuItems)
            i.setFont(font);
    }
    public void setSmallFontSize(int s)
    {
        smallFontSize = s;
        smallFont = smallFont.deriveFont((float)smallFontSize);
        for(MyMenuItem i:menuItems)
            for(MyMenuItem k:i.getSubMenu())
                k.setFont(smallFont);
    }
    protected Font getBigFont()
    {
        return font;
    }
    protected Font getSmallFont()
    {
        return smallFont;
    }
    
    
    
    public void addActionListener(ActionListener a)
    {
        if(!actionListeners.contains(a))
            actionListeners.add(a);
    }
    public void removeActionListener(ActionListener a)
    {
        while(actionListeners.contains(a))
            actionListeners.remove(a);
    }
    
    
    public void addMenuItem(String title)
    {
        MyMenuItem tempItem = new MyMenuItem(this,title,new Dimension(getWidth(),30));
        tempItem.setFont(font);
        
        for(int i=0;i<propertyListener.size();i++)
            if(propertyListenerProperties.get(i).equals(""))
                tempItem.addPropertyChangeListener(propertyListener.get(i));
            else
                tempItem.addPropertyChangeListener(propertyListenerProperties.get(i),propertyListener.get(i));
        
        menuItems.add(tempItem);
        movementOffset.add((double)0);
        updateItems();
    }
    public void addSubMenuItem(int MenuIndex,String title)
    {
        if(MenuIndex < menuItems.size() && MenuIndex >= 0)
        {
            MyMenuItem tempItem = new MyMenuItem(this,menuItems.get(MenuIndex),title,new Dimension(getWidth()-20,30));
            tempItem.setFont(font.deriveFont(Font.PLAIN,12));
            
            for(int i=0;i<propertyListener.size();i++)
                if(propertyListenerProperties.get(i).equals(""))
                    tempItem.addPropertyChangeListener(propertyListener.get(i));
                else
                    tempItem.addPropertyChangeListener(propertyListenerProperties.get(i),propertyListener.get(i));
            
            menuItems.get(MenuIndex).addSubMenuItem(tempItem);
            if(MenuIndex == selectedItem)
                movementOffset.set(MenuIndex, movementOffset.get(MenuIndex)+30);
        }
        updateItems();
    }
    public void removeMenuItem(int index)
    {
        if(index < menuItems.size() && index >= 0)
        {
            for(MyMenuItem item : menuItems.get(index).getSubMenu())
                subPanel.remove(item);
            panel.remove(menuItems.get(index));
            menuItems.remove(index);
            movementOffset.remove(index);
            updateItems();
        }
    }
    public void removeSubMenuItem(int MenuIndex, int index)
    {
        if(MenuIndex < menuItems.size() && MenuIndex >= 0)
        {
            if(index < menuItems.get(MenuIndex).getSubMenu().size() && index >= 0)
            {
                subPanel.remove(menuItems.get(MenuIndex).getSubMenu().get(index));
                menuItems.get(MenuIndex).getSubMenu().remove(index);
                movementOffset.set(MenuIndex, movementOffset.get(MenuIndex)-30);
                updateItems();
            }
        }
    }
    
    public void setButton(int MenuIndex, int index, boolean button)
    {
        if(MenuIndex < menuItems.size() && MenuIndex >= 0)
            if(index < menuItems.get(MenuIndex).getSubMenu().size() && index >= 0)
                menuItems.get(MenuIndex).getSubMenu().get(index).setButton(button);
    }
    
    
    public void setSelected(int menuIndex)
    {
        if(menuIndex < menuItems.size())
        {
            selectedItem = menuIndex;
            selectedItemIndex = -1;
            timer.start();
            itemClicked(menuItems.get(menuIndex),-1);
        }
    }
    public void setSelected(int menuIndex, int index)
    {
        if(menuIndex < menuItems.size())
        {
            if(index < menuItems.get(menuIndex).getSubMenu().size())
            {
                selectedItem = menuIndex;
                selectedItemIndex = index;
                timer.start();
                itemClicked(menuItems.get(menuIndex),index);
            }
        }
    }
    protected void itemClicked(MyMenuItem source, int index)
    {
        selectedItem = menuItems.indexOf(source);
        selectedItemIndex = index;
        for(int i=0;i<menuItems.size();i++)
        {
            MyMenuItem item = menuItems.get(i);
            
            item.setSelected(false);
            for(MyMenuItem subItem : item.getSubMenu())
                subItem.setSelected(false);
        }
        
        for(ActionListener listener : actionListeners)
            listener.actionPerformed(new MenuActionEvent(this,selectedItem,selectedItemIndex,(selectedItemIndex>=0) ? menuItems.get(selectedItem).getSubMenu().get(selectedItemIndex).isButton() : false));
        
        if(selectedItemIndex >= 0)
            if(menuItems.get(selectedItem).getSubMenu().get(selectedItemIndex).isButton())
                selectedItemIndex = -1;
        
        timer.start();
    }
    
    
    
    
    
    
    
    
    private Dimension getComponentSize()
    {
        Dimension tempS = size;
        if(menuItems.size()>0)
        {
            MyMenuItem item = menuItems.get(menuItems.size()-1);
            if(item.getSubMenu().size()>0)
            {
                MyMenuItem item2 = item.getSubMenu().get(item.getSubMenu().size()-1);
                size.height = (item.getY()+item.getHeight() > item2.getY()+item2.getHeight())?
                        item.getY()+item.getHeight():item2.getY()+item2.getHeight();
            }
            else
                size.height = item.getY()+item.getHeight();
        }
        else
            size.height = 0;
        
        return tempS;
    }
    
    protected void updateItems()
    {
        if(initialized)
        {
            int tempInt = 0;
            for(int i=0;i<menuItems.size();i++)
            {
                MyMenuItem tempItem = menuItems.get(i);
//                tempItem.setSelected((selectedItem==i && selectedItemIndex==-1));
//                tempItem.setBackground((selectedItem==i&&selectedItemIndex==-1)?selectedColor:((tempItem.isMouseEntered() && enabled)?hoveredColor:standardColor));
                tempItem.setSelected(selectedItem==i);
                tempItem.setBackground((selectedItem==i)?selectedColor:((tempItem.isMouseEntered() && enabled)?hoveredColor:standardColor));
                
                tempItem.setLocation(0,i*30 + tempInt);
                tempItem.setSize(size.width,30);


                int tempSubInt = 0;
                int maxSubInt = tempItem.getSubMenu().size()*30;
                for(int k=0;k<tempItem.getSubMenu().size();k++)
                {
                    tempSubInt += 30;

                    MyMenuItem tempSubItem = tempItem.getSubMenu().get(k);
                    tempSubItem.setSelected(selectedItem==i && selectedItemIndex==k);
                    tempSubItem.setBackground((selectedItem==i&&selectedItemIndex==k)?selectedColor:((tempSubItem.isMouseEntered() && enabled)?hoveredColor:standardColor));

                    if(((i*30+tempInt+tempSubInt)-maxSubInt) + movementOffset.get(i) < i*30+tempInt)
                        tempSubItem.setLocation(10,i*30+tempInt+5);
                    else
                        tempSubItem.setLocation(20,(int)(((i*30+tempInt+tempSubInt+5)-maxSubInt) + movementOffset.get(i)));

                    tempSubItem.setSize(size.width-20,20);
                }
                tempInt += movementOffset.get(i);
            }
            
            size = getComponentSize();
            panel.setSize(size);
            subPanel.setSize(size);
            super.resize(size.width,size.height);
        }
    }
    
    
    
    /************Action Listener**************/
    @Override public void actionPerformed(ActionEvent e)
    {
        int checkInt = 0;
        for(int i=0;i<movementOffset.size();i++)
        {
            if(selectedItem == i)
            {
                if(movementOffset.get(i)+OFFSET_ADD >= menuItems.get(i).getSubMenu().size()*30)
                {
                    movementOffset.set(i,new Double(menuItems.get(i).getSubMenu().size()*30));
                    checkInt++;
                }
                else
                    movementOffset.set(i, movementOffset.get(i)+OFFSET_ADD);
            }
            else
            {
                if(movementOffset.get(i)-OFFSET_ADD <= 0)
                {
                    movementOffset.set(i,(double)0);
                    checkInt++;
                }
                else
                    movementOffset.set(i, movementOffset.get(i)-OFFSET_ADD);
            }
        }
        
        if(checkInt == movementOffset.size())
            timer.stop();
        updateItems();
    }
    /************Action Listener End**********/
}
