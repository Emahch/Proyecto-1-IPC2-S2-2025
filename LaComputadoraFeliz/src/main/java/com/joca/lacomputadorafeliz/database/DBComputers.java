/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.Computer;
import com.joca.lacomputadorafeliz.model.StateEnum;
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
public class DBComputers extends DBConnection {

    public DBComputers(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    public DBComputers(Connection connection) {
        super(connection);
    }

    /**
     * Crea una nueva computadora
     *
     * @param computer
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void createComputer(Computer computer) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO computadoras (nombre,precio_unitario,costo_total,estado) VALUES (?,?,?,?);");
        preparedStatement.setString(1, computer.getName());
        preparedStatement.setDouble(2, computer.getPrice());
        preparedStatement.setDouble(3, 0);
        preparedStatement.setString(4, StateEnum.HABILITADO.name());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                if (isEnabled(computer.getName())) {
                    throw new InvalidDataException("El nombre de computadora no esta disponible");
                }
                tryUpdateOldComputer(computer);
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Busca una computadora en base a su nombre
     *
     * @param computerName
     * @return Computadora encontrado
     * @throws SQLException
     * @throws EntityNotFound si no se encuentra ninguna computadora
     */
    public Computer searchComputer(String computerName) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM computadoras WHERE nombre = ?");
        preparedStatement.setString(1, computerName);
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()) {
            throw new EntityNotFound("No se encontró la computadora \"" + computerName + "\"");
        }
        return getComputerFromResult(result);
    }

    /**
     * Deshabilita una computadora en base a su nombre
     *
     * @param computerName
     * @throws SQLException
     */
    public void deleteComputer(String computerName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE computadoras SET estado = ? WHERE nombre = ?");
        preparedStatement.setString(1, StateEnum.DESHABILITADO.name());
        preparedStatement.setString(2, computerName);
        preparedStatement.executeUpdate();
    }

    /**
     * Devuelve una lista de todas las computadoras registrados en el sistema
     *
     * @return Computadora encontrada
     * @throws java.sql.SQLException
     */
    public List<Computer> getComputers() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM computadoras WHERE estado = ?;");
        preparedStatement.setString(1, StateEnum.HABILITADO.name());
        ResultSet result = preparedStatement.executeQuery();

        List<Computer> computers = new ArrayList<>();
        while (result.next()) {
            computers.add(getComputerFromResult(result));
        }
        return computers;
    }

    /**
     * Actualiza el nombre y precio de venta de una computadora
     *
     * @param computer
     * @param originalName
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void updateComputer(Computer computer, String originalName) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE computadoras SET nombre = ?, precio_unitario = ?, estado = ? WHERE nombre = ?;");
        preparedStatement.setString(1, computer.getName());
        preparedStatement.setDouble(2, computer.getPrice());
        preparedStatement.setString(3, StateEnum.HABILITADO.name());
        preparedStatement.setString(4, originalName);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                if (isEnabled(computer.getName())) {
                    throw new InvalidDataException("El nombre de computadora no esta disponible");
                }
                tryUpdateOldComputer(computer);
                deleteComputer(originalName);
            } else {
                throw e;
            }
        }
    }

    public boolean isEnabled(String computerName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM computadoras WHERE nombre = ? AND estado = ?;");
        preparedStatement.setString(1, computerName);
        preparedStatement.setString(2, StateEnum.HABILITADO.name());
        ResultSet result = preparedStatement.executeQuery();
        return result.next();
    }

    /**
     * Actualiza el precio de una computadora
     *
     * @param computerName
     * @param value
     * @throws SQLException
     */
    public void updateComputerValue(String computerName, double value) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE computadoras SET costo_total = ? WHERE nombre = ?;");
        preparedStatement.setDouble(1, value);
        preparedStatement.setString(2, computerName);
        preparedStatement.executeUpdate();
    }

    /**
     * Devuelve el precio actual de la computadora
     *
     * @param computerName
     * @return precio
     * @throws SQLException
     * @throws EntityNotFound
     */
    public double getComputerValue(String computerName) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT (precio_unitario) FROM computadoras WHERE nombre = ?;");
        preparedStatement.setString(1, computerName);
        ResultSet result = preparedStatement.executeQuery();

        if (!result.next()) {
            throw new EntityNotFound("No se encontro el precio para la computadora " + computerName);
        }
        return result.getDouble("precio_unitario");
    }

    private Computer getComputerFromResult(ResultSet result) throws SQLException {
        Computer computer = new Computer();
        computer.setName(result.getString("nombre"));
        computer.setPrice(result.getDouble("precio_unitario"));
        computer.setValue(result.getDouble("costo_total"));
        computer.setAmount(getComputerCount(computer.getName()));
        return computer;
    }

    private void tryUpdateOldComputer(Computer computer) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE computadoras SET precio_unitario = ?, estado = ? WHERE nombre = ?;");
        preparedStatement.setDouble(1, computer.getPrice());
        preparedStatement.setString(2, StateEnum.HABILITADO.name());
        preparedStatement.setString(3, computer.getName());
        preparedStatement.executeUpdate();
    }

    private int getComputerCount(String computerName) throws SQLException {
        int ensambles = 0;
        int ventas = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT COUNT(*) as c FROM ensambles WHERE nombre_computadora = ?;");
        preparedStatement.setString(1, computerName);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
            ensambles = result.getInt("c");
        }
        PreparedStatement preparedStatement2;
        preparedStatement2 = connection.prepareCall("SELECT COUNT(*) as c FROM asignacion_ventas WHERE nombre_computadora = ?;");
        preparedStatement2.setString(1, computerName);
        ResultSet result2 = preparedStatement2.executeQuery();
        if (result.next()) {
            ventas = result2.getInt("c");
        }
        return (ensambles - ventas) <= 0 ? 0 : ensambles - ventas;
    }
}
