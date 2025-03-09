/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.authentication.PasswordVTO;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.exceptions.PasswordNotFoundException;
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
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void createUser(User user) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO usuarios (user,nombre,codigo_rol) VALUES (?,?,?);");
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setInt(3, user.getUserRol().getId());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            if (e.getErrorCode() == 1062) {
                throw new InvalidDataException("El nombre de usuario no esta disponible");
            } else {
                throw e;
            }
        }
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

    /**
     * Busca una contraseña en base al username
     *
     * @param username
     * @return Contraseña encontrada
     * @throws java.sql.SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.PasswordNotFoundException
     */
    public PasswordVTO getPassword(String username) throws SQLException, PasswordNotFoundException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT (password) FROM usuarios WHERE user = ?;");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        // Si no se encuentra ningun usuario se lanza una excepcion
        if (!result.next()) {
            throw new PasswordNotFoundException("No se encontró la contraseña para el usuario \"" + username + "\"");
        }
        PasswordVTO password = new PasswordVTO(result.getString("password"), false);
        if (!password.isValid()) {
            throw new PasswordNotFoundException("La contraseña para el usuario \"" + username + "\" debe ser actualizada");
        }
        return password;
    }

    /**
     * Actualiza la contraseña de un usuario
     *
     * @param password
     * @param username
     * @throws SQLException
     */
    public void updatePassword(PasswordVTO password, String username) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE usuarios SET password = ? WHERE user = ?;");
        preparedStatement.setString(1, password.getPassword());
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
    }

    /**
     * Elimina un usuario
     *
     * @param username
     * @throws java.sql.SQLException
     */
    public void deleteUser(String username) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("DELETE from usuarios WHERE user = ?;");
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
    }

    /**
     * Actualiza los datos de un usuario en el sistema
     *
     * @param user
     * @param originalUsername
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void updateUser(User user, String originalUsername) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE usuarios SET user = ?, nombre = ?, codigo_rol = ? WHERE user = ?;");
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setInt(3, user.getUserRol().getId());
        preparedStatement.setString(4, originalUsername);

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new InvalidDataException("El nombre de usuario no esta disponible");
            } else {
                throw e;
            }
        }
    }

}
