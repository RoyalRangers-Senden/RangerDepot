/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerdepot.Users;

import rangerdepot.Users.Content.Stamm;

/**
 *
 * @author Florian
 */
public abstract class User
{
    public static final String ADMIN = "Admin";
    public static final String LEITER = "Leiter";
    public static final String RANGER = "Ranger";
    
    protected int id;
    protected String name,userType;
    protected Stamm stamm;
    protected boolean isStammAdmin,isAdmin;
    
    
    public String getType()
    {
        return userType;
    }
    public boolean isStammAdmin()
    {
        return isStammAdmin;
    }
    public boolean isAdmin()
    {
        return isAdmin;
    }
    
    public int getID()
    {
        return id;
    }
    public Stamm getStamm()
    {
        return stamm;
    }
}