/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dao.UserDAO;
import com.mycompany.taskmanager.model.User;
import java.util.HashSet;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class UserController {
    public static User Register(String email, String name, char[] charPassword){
        
        String password = new String(charPassword);
        User user = new User(name, email, password);
        if(UserDAO.registerUser(user)){            
        return user;
        }
            
        return null;
        
        
        
    }
    public static User toEnter(String email, String name,  char[] charPassword){
        
        String password = new String(charPassword);
        User user = new User(name, email, password);
        
        return UserDAO.validateLogin(user);
         
    }
    public static boolean DeleteUser(User user, char[] charPassword){
        
        String password = new String(charPassword);
        User user2 =new User(user.getName(),user.getEmail(), password);
        
        if(UserDAO.validateLogin(user2) != null){
            UserDAO.deleteUser(user.getEmail());
            return true;
        }else {
            return false;
        }
        
    }
    public static boolean updateName(String email, String newName) {
        if (UserDAO.updateUserName(email, newName)){
            
         return UserDAO.updateUserName(email, newName);
        
        }else {
            return false; 
        }
    }
    public static boolean UpdateUser(User user, String newName,  char[] newPassword){
        String password = new String(newPassword);
        if(!newName.trim().equals("")||!newName.trim().equals("Name")){
            if(!password.trim().equals("")||!password.trim().equals("jPasswordField1")){
            return UserDAO.updateUser(user, newName, password);                           
        }else{
            return UserDAO.updateUser(user, newName, user.getPassword());
        }
        }else{
            if(!password.equals("")||!password.equals("jPasswordField1")){
            return UserDAO.updateUser(user, user.getName(), password);                           
        }else{
            return false;
        }
        }
        
        
        
    }
}
