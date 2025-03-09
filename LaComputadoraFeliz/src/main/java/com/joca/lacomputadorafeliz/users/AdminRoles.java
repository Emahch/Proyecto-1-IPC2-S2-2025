/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.users;

import com.joca.lacomputadorafeliz.database.DBRoles;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.users.UserRol;
import jakarta.servlet.http.HttpServletRequest;
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

    public List<UserRol> getRoles() throws SQLException {
        List<UserRol> roles = dbRoles.getRoles();
        return roles;
    }

    public void updateRoleName(HttpServletRequest request) throws SQLException, InvalidDataException {
        UserRol rol = getRolFromRequest(request);
        dbRoles.updateRol(rol);
    }

    private UserRol getRolFromRequest(HttpServletRequest request) throws InvalidDataException {
        UserRol userRol = new UserRol();
        try {
            userRol.setId(Integer.parseInt(request.getParameter("codigo")));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("¿Que código es ese?");
        }
        userRol.setName(request.getParameter("nombre"));
        if (!userRol.isValid()) {
            throw new InvalidDataException("El nombre del rol no debe superar los 45 caracteres");
        }
        return userRol;
    }
}
