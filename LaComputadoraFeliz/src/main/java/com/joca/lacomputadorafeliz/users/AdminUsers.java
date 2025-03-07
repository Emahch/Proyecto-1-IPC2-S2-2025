/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.users;

import com.joca.lacomputadorafeliz.database.DBUsers;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.users.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminUsers {

    private DBUsers dbUsuarios;

    public AdminUsers(HttpSession session) throws SQLException, ClassNotFoundException {
        dbUsuarios = new DBUsers(session);
    }
    
    public List<User> getUsers() throws SQLException {
        List<User> users = dbUsuarios.getUsers();
        return users;
    }
    
    public void createUser(HttpServletRequest request) throws SQLException, InvalidDataException {
        User newUser = new User();
        try {
            newUser.setName((String)request.getParameter("name"));
            newUser.setUserName((String)request.getParameter("name"));
            
        } catch (Exception e) {
        }
        
    }
}
