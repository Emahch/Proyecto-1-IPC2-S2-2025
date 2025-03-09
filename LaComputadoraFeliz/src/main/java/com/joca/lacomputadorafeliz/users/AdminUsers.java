/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.users;

import com.joca.lacomputadorafeliz.database.DBUsers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.users.User;
import com.joca.lacomputadorafeliz.model.users.UserRol;
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
        User newUser = getUserFromRequest(request);
        dbUsuarios.createUser(newUser);
    }
    
    public void deleteUser(HttpServletRequest request) throws SQLException {
        String username = request.getParameter("username");
        dbUsuarios.deleteUser(username);
    }
    
    public User getUser(HttpServletRequest request) throws SQLException, EntityNotFound {
        String username = request.getParameter("username");
        return dbUsuarios.searchUser(username);
    }
    
    public void updateUser(HttpServletRequest request) throws SQLException, InvalidDataException {
        User newUser = getUserFromRequest(request);
        String usernameOriginal = request.getParameter("usernameOriginal");
        dbUsuarios.updateUser(newUser, usernameOriginal);
    }
    
    private User getUserFromRequest(HttpServletRequest request) throws InvalidDataException {
        User user = new User();
        try {
            user.setName(request.getParameter("name"));
            user.setUserName(request.getParameter("username"));
            UserRol userRol = new UserRol();
            userRol.setId(Integer.parseInt(request.getParameter("rol")));
            user.setUserRol(userRol);

        } catch (NumberFormatException e) {
            throw new InvalidDataException("Por favor, selecciona un rol valido");
        }
        if (!user.isValid()) {
            throw new InvalidDataException("La información ingresada no es valida");
        }
        return user;
    }
}
