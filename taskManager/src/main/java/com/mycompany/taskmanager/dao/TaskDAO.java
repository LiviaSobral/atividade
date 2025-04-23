/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dao;

import com.mycompany.taskmanager.database.ConnectionSQL;
import com.mycompany.taskmanager.model.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class TaskDAO {
    public static boolean registerNewTask(String title, String description, java.sql.Date date, int userId){
        String sql = "Insert into tasks (title,description,date,userID) Values(?,?,?,?)";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, title);
            prep.setString(2, description);            
            prep.setDate(3, date);
            prep.setInt(4, userId);         
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
                prep.setString(1, "finished");
            }else{
                prep.setString(1, "Unfinished");
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
    public static ArrayList<Task> listTasksOfUser(int idUser){
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT taskID,title,description,date,status FROM tasks WHERE userID = ?";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, idUser);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                Task task = new Task(rs.getString("title"),rs.getString("description"),rs.getString("date"));
                task.setId(rs.getInt("taskID"));
                if(rs.getString("status").equals("finished")){
                    task.setStatus(true);
                }else{
                    task.setStatus(false);
                }
                tasks.add(task);
            }
            
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return tasks;
    }
    public static ArrayList<Task> listTasksOfUser(int idUser,String status){
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT taskID,title,description,date,status FROM tasks WHERE userID = ? AND status = ?";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, idUser);
            prep.setString(2, status);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                Task task = new Task(rs.getString("title"),rs.getString("description"),rs.getString("date"));
                task.setId(rs.getInt("taskID"));
                if(rs.getString("status").equals("finished")){
                    task.setStatus(true);
                }else{
                    task.setStatus(false);
                }
                tasks.add(task);
            }
            
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return tasks;
    }
    public static String returnTask(int taskID){
        String description = "";
        String sql = "SELECT description FROM tasks WHERE taskID = ?";
        try(Connection con = ConnectionSQL.connect()){
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, taskID);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                description = rs.getString("description");
            }
        }catch(SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        return description;
    }
}
