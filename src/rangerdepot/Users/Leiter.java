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
public class Leiter extends User
{
    public Leiter(int id,String name, Stamm stamm, boolean isStammAdmin, boolean isAdmin)
    {
        this.id = id;
        this.userType = isAdmin? ADMIN : LEITER;
        this.name = name;
        this.stamm = stamm;
        this.isAdmin = isAdmin;
        this.isStammAdmin = isStammAdmin;
    }
}