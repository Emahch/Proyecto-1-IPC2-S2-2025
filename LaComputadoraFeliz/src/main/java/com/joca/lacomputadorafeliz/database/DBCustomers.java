/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.StateEnum;
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
public class DBCustomers extends DBConnection {

    public DBCustomers(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    /**
     * Busca un cliente en base al nit
     *
     * @param nit
     * @return cliente encontrado
     * @throws java.sql.SQLException
     * @throws EntityNotFound si no se encuentra el cliente
     */
    public Customer searchCustomer(String nit) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM clientes WHERE nit = ? AND estado = ?;");
        preparedStatement.setString(1, nit);
        preparedStatement.setString(2, StateEnum.HABILITADO.name());
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()) {
            throw new EntityNotFound("No se encontró el cliente con el nit \"" + nit + "\"");
        }
        return getCustomerFromResult(result);
    }

    /**
     * Crea un nuevo cliente en la base de datos
     *
     * @param customer
     * @throws SQLException
     * @throws com.joca.lacomputadorafeliz.exceptions.InvalidDataException
     */
    public void createCustomer(Customer customer) throws SQLException, InvalidDataException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO clientes (nit,nombre,direccion,estado) VALUES (?,?,?,?);");
        preparedStatement.setString(1, customer.getNit());
        preparedStatement.setString(2, customer.getCustomerName());
        preparedStatement.setString(3, customer.getAddress());
        preparedStatement.setString(4, StateEnum.HABILITADO.name());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATED_KEY) {
                if (isEnabled(customer.getNit())) {
                    throw new InvalidDataException("El nit no esta disponible");
                }
                tryUpdateOldCustomer(customer);
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
        preparedStatement = connection.prepareCall("SELECT * FROM clientes WHERE estado = ?;");
        preparedStatement.setString(1, StateEnum.HABILITADO.name());
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
                if (isEnabled(customer.getNit())) {
                    throw new InvalidDataException("El nit no esta disponible");
                }
                tryUpdateOldCustomer(customer);
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
        preparedStatement = connection.prepareCall("UPDATE clientes SET state = ? WHERE nit = ?;");
        preparedStatement.setString(1, StateEnum.DESHABILITADO.name());
        preparedStatement.setString(2, nit);
        preparedStatement.executeUpdate();
    }

    private Customer getCustomerFromResult(ResultSet result) throws SQLException {
        Customer customer = new Customer();
        customer.setNit(result.getString("nit"));
        customer.setCustomerName(result.getString("nombre"));
        customer.setAddress(result.getString("direccion"));
        return customer;
    }

    public boolean isEnabled(String nit) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM clientes WHERE nit = ? AND estado = ?;");
        preparedStatement.setString(1, nit);
        preparedStatement.setString(2, StateEnum.HABILITADO.name());
        ResultSet result = preparedStatement.executeQuery();
        return result.next();
    }

    private void tryUpdateOldCustomer(Customer customer) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE clientes SET nombre = ?, direccion = ?, estado = ? WHERE nit = ?;");
        preparedStatement.setString(1, customer.getCustomerName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, StateEnum.HABILITADO.name());
        preparedStatement.setString(4, customer.getNit());
        preparedStatement.executeUpdate();
    }

}
