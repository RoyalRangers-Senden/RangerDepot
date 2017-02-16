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
public class Ranger extends User
{
    public Ranger(int id,String name, Stamm stamm)
    {
        this.id = id;
        this.userType = RANGER;
        isAdmin = false;
        isStammAdmin = false;
        this.name = name;
        this.stamm = stamm;
    }
}