/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.taskmanager;

import com.mycompany.taskmanager.controller.TaskController;
import com.mycompany.taskmanager.dao.TaskDAO;
import com.mycompany.taskmanager.dao.UserDAO;
import com.mycompany.taskmanager.database.ConnectionSQL;
import com.mycompany.taskmanager.model.*;
import com.mycompany.taskmanager.view.UserLogin;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class TaskManager {

    public static void main(String[] args) {
//        ConnectionSQL.connect();       
//        User user = new User("Roberto Carlos","robertoCarlos@mail","1234");
//        UserDAO.registerUser(user);
//        Task task = new Task("Comprar pão","Ir na padaria compra pão","22/04/2025");
//        TaskDAO.registerNewTask(task, 1);
//        TaskDAO.updateStatus(1, true);
//          TaskDAO.deleteTask(1);


//        System.out.println("AQ:: "+TaskController.findTasks(1));
          new UserLogin().setVisible(true);
        
        
        
    }
}
