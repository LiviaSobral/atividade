/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dao;

import com.mycompany.taskmanager.database.ConnectionSQL;
import com.mycompany.taskmanager.model.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class TaskDAO {
    public static boolean registerNewTask(Task task, int UserId){
        String sql = "Insert into tasks (title,description,date,userID) Values(?,?,?,?)";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, task.getTitle());
            prep.setString(2, task.getDescription());
            java.util.Date date1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            try{                
            date1 = sdf.parse(task.getExpirationDate());
            }catch(ParseException e){
                System.out.println("Error: " + e.getMessage());
                return false;
            }
            java.sql.Date date = new java.sql.Date(date1.getTime());
            prep.setDate(3, date);
            prep.setInt(4, UserId);         
            prep.executeUpdate();
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }    
        return false;
    }
    public static boolean updateStatus(int taskID, boolean Status){
        String sql = "UPDATE tasks set status = ? WHERE taskID = ?";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(2, taskID);
            if(Status){
                prep.setString(1, "concluido");
            }else{
                prep.setString(1, "pendente");
            }
            return prep.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }
    public static boolean deleteTask(int taskID){
        String sql = "DELETE FROM tasks WHERE taskID = ?";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, taskID);
            return prep.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }
//    public static ArrayList<Task> listTasksOfUser(int idUser){
//        
//    }
}
