/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dao.TaskDAO;
import com.mycompany.taskmanager.model.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class TaskController {
    private static ArrayList<Task> userTasks = new ArrayList<>();
    
    public static boolean registerTask(String title,String description,String strDate, int userID){
        java.util.Date date1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try{                
            date1 = sdf.parse(strDate);
            }catch(ParseException e){
                System.out.println("Error: " + e.getMessage());
                return false;
            }
            java.sql.Date date = new java.sql.Date(date1.getTime());
            return TaskDAO.registerNewTask(title, description, date, userID);
    }
    
    public static boolean updateStatus(int index, boolean Status){
        return TaskDAO.updateStatus(getTaskByIndex(index).getId(), Status);
    }
    
    public static boolean deleteTask(int index){
        return TaskDAO.deleteTask(getTaskByIndex(index).getId());
    }
    
    public static ArrayList<String> findTasks(int userID){
        ArrayList<String> tasksStr = new ArrayList<>();
        
        userTasks = TaskDAO.listTasksOfUser(userID);
        for(Task task : userTasks){
            String status;
            if(task.getStatus()){
                status = "Finished";
            }else{
                status = "Unfinished";
            }
        String info = "ID: " + task.getId()+
                " | Title: "+ task.getTitle()+
                " | Date: "+ task.getExpirationDate()+
                " | Status: "+status;
        tasksStr.add(info);
    }
        return tasksStr;        
    }
    public static ArrayList<String> findTasks(int userID,boolean boolStatus){
        ArrayList<String> tasksStr = new ArrayList<>();
        String strStatus = "";
        if(boolStatus){
            strStatus = "finished";
        }else{
            strStatus = "unfinished";
        }
        userTasks = TaskDAO.listTasksOfUser(userID,strStatus);
        for(Task task : userTasks){
            String status;
            if(task.getStatus()){
                status = "finished";
            }else{
                status = "unfinished";
            }
        String info = "ID: " + task.getId()+
                " | Title: "+ task.getTitle()+
                " | Date: "+ task.getExpirationDate()+
                " | Status: "+status;
        tasksStr.add(info);
    }
        return tasksStr;        
    }    
    
    public static String getTaskDesc(int index){
        return TaskDAO.returnTask(getTaskByIndex(index).getId());
    }
    
    private static Task getTaskByIndex(int index){
        if(index >= 0 && index < userTasks.size()){
            return userTasks.get(index);
        }
        return null;
    }
    
}
