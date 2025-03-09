/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.Computer;
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
public class DBComputers extends DBConnection {

    public DBComputers(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
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
        preparedStatement = connection.prepareCall("INSERT INTO computadoras (nombre,precio_unitario,costo_total,cantidad) VALUES (?,?,?,?);");
        preparedStatement.setString(1, computer.getName());
        preparedStatement.setDouble(2, computer.getPrice());
        preparedStatement.setDouble(3, 0);
        preparedStatement.setInt(4, 0);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("El nombre de computadora no esta disponible");
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
        return getComputerFromResult(result);
    }

    /**
     * Busca una computadora en base a su nombre
     *
     * @param computerName
     * @throws SQLException
     */
    public void deleteComputer(String computerName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("DELETE FROM computadoras WHERE nombre = ?");
        preparedStatement.setString(1, computerName);
        preparedStatement.executeUpdate();
    }

    /**
     * Devuelve una lista de todas las computadoras registrados en el sistema
     *
     * @return Computadora encontrada
     * @throws java.sql.SQLException
     */
    public List<Computer> getComputers() throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM computadoras;");
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
        preparedStatement = connection.prepareCall("UPDATE computadora SET nombre = ?, precio_unitario = ? WHERE codigo = ?;");
        preparedStatement.setString(1, computer.getName());
        preparedStatement.setDouble(2, computer.getPrice());
        preparedStatement.setString(3, originalName);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("El nombre de computadora no esta disponible");
            } else {
                throw e;
            }
        }
    }

    private Computer getComputerFromResult(ResultSet result) throws SQLException, EntityNotFound {
        if (!result.next()) {
            throw new EntityNotFound("No se encontró la computadora");
        }
        Computer computer = new Computer();
        computer.setName(result.getString("nombre"));
        computer.setPrice(result.getDouble("precio_unitario"));
        computer.setValue(result.getDouble("costo_total"));
        computer.setAmount(result.getInt("cantidad"));
        return computer;
    }
}
