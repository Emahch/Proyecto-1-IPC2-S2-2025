/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.users.User;
import com.joca.lacomputadorafeliz.model.users.UserRol;
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * @throws EntityNotFound si no se encuentra el usuario
     */
    public User searchUser(String username) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM usuarios INNER JOIN roles WHERE user = ? AND usuarios.codigo_rol = roles.codigo;");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        // Si no se encuentra ningun usuario se lanza una excepcion
        if (!result.next()) {
            throw new EntityNotFound("No se encontró el usuario \"" + username + "\"");
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

    /**
     * Crea un nuevo usuario en la base de datos
     * 
     * @param user
     * @throws SQLException
     */
    public void createUser(User user) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO usuarios (user,nombre,password,id) VALUES (?,?,?,?);");
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setInt(4, user.getUserRol().getId());
        preparedStatement.executeUpdate();
    }

    /**
     * Devuelve una lista de todos los usuarios del sistema
     *
     * @return Usuarios encontrados
     * @throws java.sql.SQLException
     */
    public List<User> getUsers() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM usuarios INNER JOIN roles Where codigo_rol = codigo;");
        ResultSet result = preparedStatement.executeQuery();

        List<User> users = new ArrayList<>();
        while (result.next()) {
            User userFound = new User();
            userFound.setUserName(result.getString("user"));
            userFound.setName(result.getString("nombre"));
            UserRol userRol = new UserRol();
            userRol.setId(result.getInt("roles.codigo"));
            userRol.setName(result.getString("roles.nombre"));
            userFound.setUserRol(userRol);
            users.add(userFound);
        }
        return users;
    }

}
