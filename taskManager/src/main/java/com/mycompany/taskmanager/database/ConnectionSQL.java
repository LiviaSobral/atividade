/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class ConnectionSQL {
    private static final Dotenv dotenv = Dotenv.load();
    private static Connection con;
    
    public static Connection connect(){
        try{
            if(con == null || con.isClosed()){
                con = DriverManager.getConnection(dotenv.get("DB_URL"),dotenv.get("DB_USER"),dotenv.get("DB_PASSWORD"));
                createTables();
                System.out.println("connecting to database");
            }
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return con;
    }
    
    private static void createTables(){
        String sql2 = "CREATE TABLE IF NOT EXISTS users ("
                + "userID INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(100) NOT NULL,"
                + "email VARCHAR(100) NOT NULL UNIQUE,"
                + "password VARCHAR(100) NOT NULL);";
        
         String sql = "CREATE TABLE IF NOT EXISTS tasks ("+
                 "taskID INT AUTO_INCREMENT PRIMARY KEY,"
                 + "title VARCHAR(100) NOT NULL UNIQUE,"
                 + "description VARCHAR(100),"
                 + "date DATE,"
                 + "status ENUM('unfinished', 'finished') DEFAULT 'unfinished',"
                 + "userID INT,"
                 + "FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE ON UPDATE CASCADE)";
                
         
        
        try(Statement stmt = con.createStatement()){
            stmt.execute(sql2);
            stmt.execute(sql);
        }catch (SQLException e ){
            System.out.println("Error: "+ e.getMessage());
    }
    }
    
}
