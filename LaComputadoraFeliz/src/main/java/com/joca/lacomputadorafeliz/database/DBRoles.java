/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
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
public class DBRoles extends DBConnection {

    public DBRoles(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    /**
     * Crea un nuevo rol, indicando su codigo y nombre
     * 
     * @param userRol
     * @throws SQLException 
     */
    public void createRol(UserRol userRol) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO roles (codigo,nombre) VALUES (?,?);");
        preparedStatement.setInt(1, userRol.getId());
        preparedStatement.setString(2, userRol.getName());
        preparedStatement.executeUpdate();
    }

    /**
     * Busca un rol en base a su codigo
     * 
     * @param id
     * @return rol encontrado
     * @throws SQLException
     * @throws EntityNotFound si no se encuentra ningun rol
     */
    public UserRol searchRol(int id) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM roles WHERE codigo = ?");
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        // Si no se encuentra ningun usuario se lanza una excepcion
        if (!result.next()) {
            throw new EntityNotFound("No se encontró el rol #\"" + id + "\"");
        }
        UserRol userRol = new UserRol();
        userRol.setId(result.getInt("codigo"));
        userRol.setName(result.getString("nombre"));
        return userRol;
    }

    /**
     * Devuelve una lista de todos los roles registrados en el sistema
     *
     * @return Roles encontrados
     * @throws java.sql.SQLException
     */
    public List<UserRol> getRoles() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM roles;");
        ResultSet result = preparedStatement.executeQuery();

        List<UserRol> usersRoles = new ArrayList<>();
        while (result.next()) {
            UserRol userRol = new UserRol();
            userRol.setId(result.getInt("codigo"));
            userRol.setName(result.getString("nombre"));
            usersRoles.add(userRol);
        }
        return usersRoles;
    }

}
