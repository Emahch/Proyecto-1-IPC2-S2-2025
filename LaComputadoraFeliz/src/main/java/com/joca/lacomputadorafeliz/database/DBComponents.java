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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joca
 */
public class DBComponents extends DBConnection {

    public DBComponents(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
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
        preparedStatement.setInt(3, 0);
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
     * @param computerName
     * @return Componente encontrado
     * @throws SQLException
     * @throws EntityNotFound si no se encuentra ningun componente
     */
    public Component search(String computerName) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM computadoras WHERE nombre = ?");
        preparedStatement.setString(1, computerName);
        ResultSet result = preparedStatement.executeQuery();
        return null;
    }
}
