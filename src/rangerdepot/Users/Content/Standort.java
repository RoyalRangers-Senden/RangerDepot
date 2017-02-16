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
public class Standort
{
    private final int id,stammID,zipcode,houseNumber;
    private final String name,town,street,houseNumberAddition;
    
    public Standort(int id,String name,int stammID,int zipcode,String town,String street,int houseNumber,String houseNumberAddition)
    {
        this.id = id;
        this.name = name;
        this.stammID = stammID;
        this.zipcode = zipcode;
        this.town = town;
        this.street = street;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
    }
    
    public int getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public int getStammID()
    {
        return stammID;
    }
    public int getZipcode()
    {
        return zipcode;
    }
    public String getTown()
    {
        return town;
    }
    public String getStreet()
    {
        return street;
    }
    public int getHouseNumber()
    {
        return houseNumber;
    }
    public String getHouseNumberAddition()
    {
        return houseNumberAddition;
    }
    
    
    @Override public String toString()
    {
        return id+"/"+stammID+": "+name+" ("+zipcode+" "+town+", "+street+" "+houseNumber+" "+houseNumberAddition+")";
    }
}
