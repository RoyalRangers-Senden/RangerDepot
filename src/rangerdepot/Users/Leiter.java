/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangerdepot.Users;

/**
 *
 * @author Florian
 */
public class Leiter extends User
{
    private final boolean isAdmin;
    
    public Leiter(int id,String name, boolean isAdmin)
    {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }
}
