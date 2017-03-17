/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.guiElements;

import GUI.ScrollPane.MyScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Florian
 */
public class MyComboBox<T> extends JPanel implements ActionListener,MouseListener,FocusListener,KeyListener
{
    private static final int BORDER_WIDTH = 2;
    private static final int LABEL_BORDER = 5;
    private static final int EXTENSION_SPEED = 10;
    private boolean initialized = false;
    private boolean filtering = false;
    
    private boolean allowNotlistedElements = true;
    
    private int itemSize = 30;
    private int maxShownLabels = 5;
    
    private String text = "";
    private T oldObject = null;
    
    private Dimension size = new Dimension(150,30);
    
    private int extension = 0;
    private boolean expanding = false;
    private boolean tooBig = false;
    private final Timer timer;
    
    private Color backgroundC = new Color(238,238,238);
    private Color borderC = new Color(210,210,210);
    private Color borderLightC = new Color(230,230,230);
    private Color hoveredC = new Color(225,225,225);
    private Font font;
    
    private final JLabel borderL,borderR,borderO,borderU,borderM;
    private final ComboLabel<T> selectedL;
    private final MyScrollPane scroll;
    
    private ComboLabel<T> selectedItem = null;
    
    private final ArrayList<ComboLabel> allLabels = new ArrayList<ComboLabel>();
    private ArrayList<ComboLabel> labels = new ArrayList<ComboLabel>();
    
    private final ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
    
    private final JButton h;
    
    public MyComboBox()
    {
        super();
        setLayout(null);
        addFocusListener(this);
        setBackground(backgroundC);
        
        h = new JButton();h.setSize(1,1);h.setLocation(-1,-1);
        
        font = getFont();
        
        timer = new Timer(1000/60,this);
        timer.setRepeats(true);
        
        borderL = new JLabel();borderL.setOpaque(true);
        borderL.setLocation(0,0);borderL.setBackground(borderC);
        borderR = new JLabel();borderR.setBackground(borderC);borderR.setOpaque(true);
        borderO = new JLabel();borderO.setOpaque(true);
        borderO.setLocation(0,0);borderO.setBackground(borderC);
        borderU = new JLabel();borderU.setBackground(borderC);borderU.setOpaque(true);
        borderM = new JLabel();borderM.setBackground(borderLightC);borderM.setOpaque(true);
        
        selectedL = new ComboLabel<T>(null);
        selectedL.setLocation(BORDER_WIDTH+LABEL_BORDER, BORDER_WIDTH);
        selectedL.addMouseListener(this);
        selectedL.setFocusable(true);
        selectedL.addKeyListener(this);
        selectedL.setBackground(backgroundC);
        
        scroll = new MyScrollPane();
        scroll.setLayout(null);
        scroll.setLocation(BORDER_WIDTH,30);
        scroll.setBackground(backgroundC);
        scroll.setScrollbarThickness(7);
        
        setSize(size);
        
        add(borderL);add(borderR);add(borderO);add(borderU);add(borderM);
        add(selectedL);add(scroll);
        add(h);remove(h);
        
        initialized = true;
    }
    
    public void allowNotlistedElements(boolean allowed)
    {
        allowNotlistedElements = allowed;
    }
    
    @Override public void setSize(Dimension d)
    {
        setSize(d.width,d.height);
    }
    @Override public void setSize(int x,int y)
    {
        size.width = x;
        size.height = y;
        super.setSize(x,y+extension);
        selectedL.setSize(x-BORDER_WIDTH*2-LABEL_BORDER, size.height-BORDER_WIDTH*2);
        scroll.setLocation(BORDER_WIDTH, y);
        for(JLabel l:labels)
            l.setSize(x-BORDER_WIDTH*2-LABEL_BORDER,itemSize);
        update();
    }
    
    public void addActionListener(ActionListener l)
    {
        if(!listeners.contains(l))
            listeners.add(l);
    }
    public void removeActionListener(ActionListener l)
    {
        listeners.remove(l);
    }
    
    
    public void addItem(T s)
    {
        if(s != null)
        {
            for(ComboLabel l:allLabels)
                if(l.equals(s))
                    return;

            ComboLabel l = new ComboLabel(s);
            l.setFont(font);
            l.addMouseListener(this);
            l.setSize(getWidth()-BORDER_WIDTH*2-LABEL_BORDER,itemSize);
            l.setLocation(LABEL_BORDER,labels.size()*itemSize);
            l.setOpaque(true);
            l.setBackground(backgroundC);
            scroll.add(l);
            allLabels.add(l);
            filter();
            update();
        }
    }
    public void clear()
    {
        labels.clear();
        selectedItem = null;
        selectedL.setObject(null);
        
        expanding = false;
        timer.start();
    }
    
    public void setBackgroundColor(Color c)
    {
        backgroundC = c;
        setBackground(c);
        scroll.setBackground(c);
        for(JLabel l:labels)
            l.setBackground(c);
    }
    public void setBorderColor(Color c)
    {
        borderC = c;
        borderL.setBackground(c);
        borderR.setBackground(c);
        borderO.setBackground(c);
        borderU.setBackground(c);
    }
    public void setBorderLightColor(Color c)
    {
        borderLightC = c;
        borderM.setBackground(c);
    }
    public void setHoveredColor(Color c)
    {
        hoveredC = c;
    }
    public void setScrollbarColor(Color c)
    {
        scroll.setScrollbarColor(c);
    }
    @Override public void setFont(Font f)
    {
        font = f;
        super.setFont(font);
        
        if(initialized)
        {
            selectedL.setFont(font);
            for(JLabel l:allLabels)
                l.setFont(font);
        }
    }
    public void setItemSize(int itemSize)
    {
        this.itemSize = itemSize;
        
        for(JLabel l:labels)
            l.setSize(size.width-BORDER_WIDTH*2-LABEL_BORDER,itemSize);
        update();
    }
    public void setMaxShownLabels(int maxShownLabels)
    {
        this.maxShownLabels = maxShownLabels;
    }
    
    public T getSelectedItem()
    {
        if(selectedItem == null)
            return null;
        else
            return selectedItem.getObject();
    }
    public void setSelectetItem(T t)
    {
        selectedItem = null;
        if(t != null)
            for(ComboLabel l:labels)
                if(l.getObject().equals(t))
                {
                    selectedItem = l;
                    selectedL.setObject(selectedItem.getObject());
                    break;
                }

        if(selectedItem == null)
        {
            if(allowNotlistedElements)
            {
                selectedL.setObject(t);
            }
            else if(expanding == true)
                selectedL.setObject(oldObject);
        }
        
        if(t != null)
        {
            MyComboEvent ae;
            if(selectedItem == null)
                ae = new MyComboEvent(this,-1,(t==null?null:t.toString()),null);
            else
                ae = new MyComboEvent(this,labels.indexOf(selectedItem),selectedItem.getText(),selectedItem.object);

            for(ActionListener l:listeners)
                l.actionPerformed(ae);
        }

        expanding = false;
        timer.start();
    }
    public int getSelectedItemIndex()
    {
        if(selectedItem == null)
            return -1;
        else
             return allLabels.indexOf(selectedItem);
    }
    
    
    
    private void update()
    {
        int x = getWidth();
        int y = getHeight();
        
        borderL.setSize(BORDER_WIDTH,y);
        borderR.setSize(BORDER_WIDTH,y);
        borderR.setLocation(x-BORDER_WIDTH,0);
        borderO.setSize(x,BORDER_WIDTH);
        borderU.setSize(x,BORDER_WIDTH);
        borderU.setLocation(0,y-BORDER_WIDTH);
        borderM.setSize(x,BORDER_WIDTH);
        borderM.setLocation(0,size.height-BORDER_WIDTH);
     
        scroll.setSize(x-BORDER_WIDTH*2, y-(size.height+BORDER_WIDTH));
    }
    private void filter()
    {
        if(!filtering)
        {
            filtering = true;
            
            labels = (ArrayList<ComboLabel>)allLabels.clone();
            for(JLabel l:labels)
                scroll.remove(l);
            for(int i=0;i<labels.size();i++)
                if(labels.get(i).getText().toLowerCase().contains(text.toLowerCase()))
                    scroll.add(labels.get(i));
                else
                {
                    labels.remove(labels.get(i));
                    i--;
                }
            for(int i=0;i<labels.size();i++)
                labels.get(i).setLocation(LABEL_BORDER,itemSize*i);

            timer.start();
            filtering = false;
        }
    }
    
    
    
    
    //<editor-fold> Action Listener
    @Override public void actionPerformed(ActionEvent e)
    {
        if(expanding)
        {
            boolean move = true;
            if(extension >= labels.size()*itemSize)
            {
                if(extension-EXTENSION_SPEED >= labels.size()*itemSize)
                {
                    tooBig = true;
                }
                else
                {
                    tooBig = false;
                    if(labels.isEmpty())
                        extension = 0;
                    else
                        extension = labels.size()*itemSize + BORDER_WIDTH;
                    timer.stop();
                    move = false;
                }
            }
            else if(extension >= maxShownLabels*itemSize)
            {
                tooBig = false;
                if(labels.isEmpty())
                    extension = 0;
                else
                    extension = maxShownLabels*itemSize + BORDER_WIDTH;
                timer.stop();
                move = false;
            }
            
            if(move)
            {
                if(tooBig)
                    extension -= EXTENSION_SPEED;
                else
                    extension += EXTENSION_SPEED;
            }
        }
        else
        {
            extension -= EXTENSION_SPEED;
            if(extension <= BORDER_WIDTH)
            {
                extension = 0;
                timer.stop();
            }
        }
        super.setSize(size.width, size.height+extension);
        update();
    }
    //</editor-fold> Action Listener
    
    //<editor-fold> Mouse Listener
    @Override public void mouseClicked(MouseEvent e)
    {
        ComboLabel<T> l = (ComboLabel<T>)e.getSource();
        if(l == selectedL)
        {
            if(expanding)
            {
                if(selectedItem == null)
                    selectedL.setObject(oldObject);
                else
                    selectedL.setObject(selectedItem.getObject());
                expanding = false;
                timer.start();
            }
            else
            {
                oldObject = selectedL.getObject();
                selectedL.setText("");
                text = "";
                expanding = true;
                filter();
            }
        }
        else
        {
            l.setBackground(backgroundC);
            selectedItem = l;
            selectedL.setObject(l.getObject());
            expanding = false;
            timer.start();
            
            for(ActionListener listener:listeners)
                listener.actionPerformed(new MyComboEvent(this,labels.indexOf(l),l.getText(),l.getObject()));
        }
    }
    @Override public void mouseEntered(MouseEvent e)
    {
        ((JLabel)e.getSource()).setBackground(hoveredC);
    }
    @Override public void mouseExited(MouseEvent e)
    {
        ((JLabel)e.getSource()).setBackground(backgroundC);
    }
    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    //</editor-fold> Mouse Listener
    
    //<editor-fold> Focus Listener
    @Override public void focusGained(FocusEvent e){}
    @Override public void focusLost(FocusEvent e)
    {
        expanding = false;
        timer.start();
    }
    //</editor-fold> Focus Listener
    
    //<editor-fold> Key Listener
    @Override public void keyPressed(KeyEvent e){}
    @Override public void keyReleased(KeyEvent e){}
    @Override public void keyTyped(KeyEvent e)
    {
        if(expanding)
        {
                if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
            {
                if(text.length() > 0)
                {
                    text = text.substring(0, text.length()-1);
                    selectedL.setObject(null);
                    selectedL.setText(text);
                    filter();
                }
            }
            else if(e.getKeyChar() == KeyEvent.VK_ENTER)
            {
                selectedItem = null;
                for(ComboLabel l:labels)
                    if(l.getText().toLowerCase().equals(text.toLowerCase()))
                    {
                        selectedItem = l;
                        selectedL.setObject(selectedItem.getObject());
                        break;
                    }

                if(selectedItem == null)
                {
                    if(allowNotlistedElements)
                    {
                        selectedL.setObject(null);
                        selectedL.setText(text);
                    }
                    else
                        selectedL.setObject(oldObject);
                }

                MyComboEvent ae;
                if(selectedItem == null)
                    ae = new MyComboEvent(this,-1,text,null);
                else
                    ae = new MyComboEvent(this,labels.indexOf(selectedItem),selectedItem.getText(),selectedItem.object);

                for(ActionListener l:listeners)
                    l.actionPerformed(ae);

                expanding = false;
                timer.start();
            }
            else if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
            {
                if(selectedItem == null)
                    selectedL.setObject(oldObject);
                else
                    selectedL.setObject(selectedItem.getObject());
                expanding = false;
                timer.start();
            }

            else if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
            {
                if(e.getKeyChar() >= 32  &&  e.getKeyChar() <= 126)
                {
                    text += e.getKeyChar();
                    selectedL.setObject(null);
                    selectedL.setText(text);
                    filter();
                }
            }
        }
    }
    //</editor-fold> Key Listener
    
    
    //========================================================================//
    
    private class ComboLabel<T> extends JLabel
    {
        private T object;
        
        public ComboLabel(T o)
        {
            super((o==null)?"":o.toString());
            object = o;
        }
        
        public T getObject()
        {
            return object;
        }
        public void setObject(T o)
        {
            object = o;
            setText((o==null)?"":o.toString());
        }
    }
    
    public class MyComboEvent<T> extends ActionEvent
    {
        private final T o;
        
        public MyComboEvent(Object source, int id, String command, T o)
        {
            super(source,id,command);
            this.o = o;
        }
        
        public T getObject()
        {
            return o;
        }
    }
}