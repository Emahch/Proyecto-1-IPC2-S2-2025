/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.users;

import com.joca.lacomputadorafeliz.database.DBRoles;
import com.joca.lacomputadorafeliz.model.users.UserRol;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminRoles {

    private DBRoles dbRoles;

    public AdminRoles(HttpSession session) throws SQLException, ClassNotFoundException {
        dbRoles = new DBRoles(session);
    }
    
    public List<UserRol> getUsers() throws SQLException {
        List<UserRol> roles = dbRoles.getRoles();
        return roles;
    }
}
