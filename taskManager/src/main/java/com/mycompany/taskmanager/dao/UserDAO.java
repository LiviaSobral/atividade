/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taskmanager.dao;

import com.mycompany.taskmanager.database.ConnectionSQL;
import com.mycompany.taskmanager.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author LIVIAHELOISAALVESSOB
 */
public class UserDAO {
    public static boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        String passwordHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, passwordHash);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean validateLogin(User user) {
        String sql = "SELECT password FROM user WHERE email = ? ";

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passwordHash = rs.getString("password");
                return BCrypt.checkpw(user.getPassword(), passwordHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
