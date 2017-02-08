/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Menu;

import java.awt.event.ActionEvent;

/**
 *
 * @author Florian
 */
public class MenuActionEvent extends ActionEvent
{
    public int selectedItem,selectedItemIndex;
    public boolean isButton;
    
    public MenuActionEvent(MyMenu source,int index1, int index2, boolean button)
    {
        super(source,1001,"");
        selectedItem = index1;
        selectedItemIndex = index2;
        isButton = button;
    }
}
