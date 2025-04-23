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

    public static User validateLogin(User user) {
        String sql = "SELECT password,userID FROM users WHERE email = ? ";

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passwordHash = rs.getString("password");                
                if(BCrypt.checkpw(user.getPassword(), passwordHash)){
                    user.setId(rs.getInt("userID"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User searchUserEmail(String email) {
        String sql = "SELECT * FROM  users WHERE email = ?";

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
                user.setId(rs.getInt("userID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateUserPassword(User user, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ? ";
        String passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, passwordHash);
            stmt.setString(2, user.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUserName(String email, String newName) {
        String sql = "UPDATE users SET name = ? WHERE email = ?";

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, email);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean deleteUser(String email) {
        String sql = "DELETE  FROM users WHERE email = ?";

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateUser(User user, String newName, String newPassword) {
        String sql = "UPDATE users SET name = ? , SET password = ? WHERE email = ?";

        try (Connection con = ConnectionSQL.connect(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, newPassword);
            stmt.setString(3, user.getEmail());
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
