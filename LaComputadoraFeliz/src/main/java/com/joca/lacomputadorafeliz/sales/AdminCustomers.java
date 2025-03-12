/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.sales;

import com.joca.lacomputadorafeliz.database.DBCustomers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.sales.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminCustomers {

    private DBCustomers dbCustomers;

    public AdminCustomers(HttpSession session) throws SQLException, ClassNotFoundException {
        dbCustomers = new DBCustomers(session);
    }

    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = dbCustomers.getCustomers();
        return customers;
    }

    public void createCustomer(HttpServletRequest request) throws SQLException, InvalidDataException {
        Customer customer = getCustomerFromRequest(request);
        dbCustomers.createCustomer(customer);
    }

    public void deleteCustomer(HttpServletRequest request) throws SQLException {
        String nit = request.getParameter("nit");
        dbCustomers.deleteCustomer(nit);
    }

    public Customer getCustomer(HttpServletRequest request) throws SQLException, EntityNotFound {
        String nit = request.getParameter("nit");
        return dbCustomers.searchCustomer(nit);
    }

    public void updateCustomer(HttpServletRequest request) throws SQLException, InvalidDataException {
        Customer customer = getCustomerFromRequest(request);
        String originalNit = request.getParameter("nitOriginal");
        dbCustomers.updateCustomer(customer, originalNit);
    }

    private Customer getCustomerFromRequest(HttpServletRequest request) throws InvalidDataException {
        Customer customer = new Customer();
        customer.setNit(request.getParameter("nit"));
        customer.setCustomerName(request.getParameter("customerName"));
        customer.setAddress(request.getParameter("address"));
        if (!customer.isValid()) {
            throw new InvalidDataException("Verifica que el nit sea valido");
        }
        return customer;
    }
}
