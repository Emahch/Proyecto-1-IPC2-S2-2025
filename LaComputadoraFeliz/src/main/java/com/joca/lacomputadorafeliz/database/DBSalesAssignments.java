/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.sales.Customer;
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
public class DBSalesAssignments extends DBConnection {

    public DBSalesAssignments(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    /**
     * Busca una asignacion de venta en base al id de venta e id de ensamble
     *
     * @param idVenta
     * @param idEnsamble
     * @return asignacion encontrada
     * @throws java.sql.SQLException
     * @throws EntityNotFound si no se encuentra la asignacion
     */
    public Customer searchAssignment(int idVenta, int idEnsamble) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM asignacion_ventas WHERE id_venta = ? AND id_ensamble = ?;");
        preparedStatement.setInt(1, idVenta);
        preparedStatement.setInt(2, idEnsamble);
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()) {
            throw new EntityNotFound("No se encontró la asignacion");
        }
        return getCustomerFromResult(result);
    }

    /**
     * Crea una nueva asignacion de venta en la base de datos
     *
     * @param customer
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void createCustomer(Customer customer) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO asignacion_ventas (id_venta,id_ensamble,nombre_computadora) VALUES (?,?,?);");
        preparedStatement.setString(1, customer.getNit());
        preparedStatement.setString(2, customer.getCustomerName());
        preparedStatement.setString(3, customer.getAddress());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("La asignacion ya existe");
            } else {
                throw e;
            }
        }
    }

    /**
     * Devuelve una lista de todos los clientes del sistema
     *
     * @return Clientes encontrados
     * @throws java.sql.SQLException
     */
    public List<Customer> getCustomers() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM clientes");
        ResultSet result = preparedStatement.executeQuery();

        List<Customer> customers = new ArrayList<>();
        while (result.next()) {
            customers.add(getCustomerFromResult(result));
        }
        return customers;
    }

    /**
     * Actualiza la informacion de un cliente
     *
     * @param customer
     * @param originalNit
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void updateCustomer(Customer customer, String originalNit) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE clientes SET nit = ?, nombre = ?, direccion = ? WHERE nit = ?;");
        preparedStatement.setString(1, customer.getNit());
        preparedStatement.setString(2, customer.getCustomerName());
        preparedStatement.setString(3, customer.getAddress());
        preparedStatement.setString(3, originalNit);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                throw new InvalidDataException("El nit ingresado ya esta en uso");
            } else {
                throw e;
            }
        }
    }

    /**
     * Elimina un cliente de la base de datos
     *
     * @param nit
     * @throws java.sql.SQLException
     */
    public void deleteCustomer(String nit) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("DELETE from clientes WHERE nit = ?;");
        preparedStatement.setString(1, nit);
        preparedStatement.executeUpdate();
    }

    private Customer getCustomerFromResult(ResultSet result) throws SQLException {
        Customer customer = new Customer();
        customer.setNit(result.getString("nit"));
        customer.setCustomerName(result.getString("nombre"));
        customer.setAddress(result.getString("direccion"));
        return customer;
    }

}
