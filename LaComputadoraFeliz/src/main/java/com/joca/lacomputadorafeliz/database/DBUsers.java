/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.UserNotFoundException;
import com.joca.lacomputadorafeliz.model.users.User;
import com.joca.lacomputadorafeliz.model.users.UserRol;
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joca
 */
public class DBUsers extends DBConnection {
    
    public DBUsers(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }
    
    /**
     * Busca un usuario en base al username
     *
     * @param username
     * @return Usuario encontrado
     * @throws java.sql.SQLException
     * @throws josecarlos.magnetix.exceptions.UserNotFindException
     */
    public User searchUser(String username) throws SQLException, UserNotFoundException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM usuarios INNER JOIN roles WHERE user = ? AND usuarios.codigo_rol = roles.codigo;");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        // Si no se encuentra ningun usuario se lanza una excepcion
        if (!result.next()) {
            throw new UserNotFoundException("No se encontró el usuario \"" + username + "\"");
        }
        User userFound = new User();
        userFound.setUserName(result.getString("user"));
        userFound.setName(result.getString("nombre"));
        userFound.setPassword(result.getString("password"));
        
        UserRol userRol = new UserRol();
        userRol.setId(result.getInt("roles.codigo"));
        userRol.setName(result.getString("roles.nombre"));
        userFound.setUserRol(userRol);
        return userFound;
    }
    
}
