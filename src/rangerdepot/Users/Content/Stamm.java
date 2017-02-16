/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerdepot.Users.Content;

/**
 *
 * @author Florian
 */
public class Stamm implements Comparable<Stamm>
{
    private final int id;
    private final String name;
    
    public Stamm(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public int getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    
    @Override public String toString()
    {
        return id+" - "+name;
    }
    
    @Override public int compareTo(Stamm s)
    {
        return id-s.getID();
    }
}