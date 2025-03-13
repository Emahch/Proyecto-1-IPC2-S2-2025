/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
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
public class DBComponentAssign extends DBConnection {

    public DBComponentAssign(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }
    
    public DBComponentAssign(Connection connection) {
        super(connection);
    }

    /**
     * Crea una nueva asignacion de componente
     *
     * @param componentAsign
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void addComponent(ComponentAsignDTO componentAsign) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO asignacion_componentes (nombre_componente,nombre_computadora,cantidad_componentes)"
                + " VALUES (?,?,?);");
        preparedStatement.setString(1, componentAsign.getComponentName());
        preparedStatement.setString(2, componentAsign.getComputerName());
        preparedStatement.setInt(3, componentAsign.getAmount());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("Ya existe una asignacion del mismo componente");
            } else {
                throw e;
            }
        }
    }

    /**
     * Busca una asignacion en base al nombre de componente y nombre de
     * computadora
     *
     * @param componentName
     * @param computerName
     * @return Asignacion encontrada
     * @throws SQLException
     * @throws EntityNotFound si no se encuentra ninguna asignacion
     */
    public ComponentAsignDTO searchAsign(String componentName, String computerName) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM asignacion_componentes INNER JOIN componentes "
                + "WHERE nombre_componente = ? AND nombre_computadora = ? AND nombre_componente = componentes.nombre;");
        preparedStatement.setString(1, componentName);
        preparedStatement.setString(2, computerName);
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()) {
            throw new EntityNotFound("No se encontró la asignacion de \"" + componentName + "\" a \"" + computerName + "\"");
        }
        return getAsignFromResult(result);
    }

    /**
     * Busca una asignacion en base a su nombre de componente y computadora
     *
     * @param componentName
     * @param computerName
     * @throws SQLException
     */
    public void deleteAsignacion(String componentName, String computerName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("DELETE FROM asignacion_componentes WHERE nombre_componente = ? AND nombre_computadora = ?;");
        preparedStatement.setString(1, componentName);
        preparedStatement.setString(2, computerName);
        preparedStatement.executeUpdate();
    }

    /**
     * Devuelve una lista de todas las asignaciones de componentes asignadas a
     * una computadora
     *
     * @param computerName
     * @return asignaciones encontradas
     * @throws java.sql.SQLException
     */
    public List<ComponentAsignDTO> getComponentAsign(String computerName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM asignacion_componentes INNER JOIN componentes "
                + "WHERE nombre_computadora = ? AND nombre_componente = componentes.nombre");
        preparedStatement.setString(1, computerName);
        ResultSet result = preparedStatement.executeQuery();

        List<ComponentAsignDTO> asigns = new ArrayList<>();
        while (result.next()) {
            asigns.add(getAsignFromResult(result));
        }
        return asigns;
    }

    /**
     * Actualiza la cantidad de componentes asignados a una computadora
     *
     * @param asign
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void updateAsignAmount(ComponentAsignDTO asign) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE asignacion_componentes SET cantidad_componentes = ? WHERE nombre_componente = ? AND nombre_computadora = ?;");
        preparedStatement.setInt(1, asign.getAmount());
        preparedStatement.setString(2, asign.getComponentName());
        preparedStatement.setString(3, asign.getComputerName());
        preparedStatement.executeUpdate();
    }

    private ComponentAsignDTO getAsignFromResult(ResultSet result) throws SQLException {
        ComponentAsignDTO asign = new ComponentAsignDTO();
        asign.setComponentName(result.getString("nombre_componente"));
        asign.setComputerName(result.getString("nombre_computadora"));
        asign.setAmount(result.getInt("cantidad_componentes"));
        asign.setComponentValue(result.getDouble("costo_unitario"));
        asign.setStock(result.getInt("cantidad"));
        return asign;
    }

    public Connection getConnection() {
        return connection;
    }
}
