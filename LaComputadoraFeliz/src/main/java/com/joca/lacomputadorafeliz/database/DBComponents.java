/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import static com.joca.lacomputadorafeliz.database.DBConnection.MYSQL_DUPLICATED_KEY;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.Component;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joca
 */
public class DBComponents extends DBConnection {

    public DBComponents(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    public DBComponents(Connection connection) {
        super(connection);
    }

    /**
     * Crea un nuevo componente
     *
     * @param component
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void create(Component component) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO componentes (nombre,costo_unitario,cantidad) VALUES (?,?,?);");
        preparedStatement.setString(1, component.getName());
        preparedStatement.setDouble(2, component.getValue());
        preparedStatement.setInt(3, component.getAmount());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("El nombre de componente no esta disponible");
            } else {
                throw e;
            }
        }
    }

    /**
     * Busca un componente en base a su nombre
     *
     * @param componentName
     * @return Componente encontrado
     * @throws SQLException
     * @throws EntityNotFound si no se encuentra ningun componente
     */
    public Component searchComponent(String componentName) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM componentes WHERE nombre = ?");
        preparedStatement.setString(1, componentName);
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()) {
            throw new EntityNotFound("No se encontró el componente + \"" + componentName + "\"");
        }
        return getComponentFromResult(result);
    }

    /**
     * Busca una computadora en base a su nombre
     *
     * @param componentName
     * @throws SQLException
     */
    public void deleteComponent(String componentName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("DELETE FROM componentes WHERE nombre = ?");
        preparedStatement.setString(1, componentName);
        preparedStatement.executeUpdate();
    }

    /**
     * Devuelve una lista de todos los componentes registrados en el sistema
     *
     * @return Componentes encontrados
     * @throws java.sql.SQLException
     */
    public List<Component> getComponents() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM componentes;");
        ResultSet result = preparedStatement.executeQuery();

        List<Component> components = new ArrayList<>();
        while (result.next()) {
            components.add(getComponentFromResult(result));
        }
        return components;
    }

    /**
     * Actualiza el nombre, costo y cantidad de un componente
     *
     * @param component
     * @param originalName
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void updateComponent(Component component, String originalName) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE componentes SET nombre = ?, costo_unitario = ?, cantidad = ? WHERE nombre = ?;");
        preparedStatement.setString(1, component.getName());
        preparedStatement.setDouble(2, component.getValue());
        preparedStatement.setInt(3, component.getAmount());
        preparedStatement.setString(4, originalName);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("El nombre de componente no esta disponible");
            } else {
                throw e;
            }
        }
    }

    /**
     * Actualiza la cantidad de un componente
     *
     * @param componentName
     * @param amount
     * @throws SQLException
     */
    public void updateComponentAmount(String componentName, int amount) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE componentes SET cantidad = ? WHERE nombre = ?;");
        preparedStatement.setInt(1, amount);
        preparedStatement.setString(2, componentName);
        preparedStatement.executeUpdate();
    }

    private Component getComponentFromResult(ResultSet result) throws SQLException {
        Component component = new Component();
        component.setName(result.getString("nombre"));
        component.setValue(result.getDouble("costo_unitario"));
        component.setAmount(result.getInt("cantidad"));
        return component;
    }
}
