/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.User;

/**
 *
 * @author user
 */
public class UserFacade extends AbstractFacade<User>{

    public UserFacade(Class<User> entityClass) {
        super(entityClass);
    }

}