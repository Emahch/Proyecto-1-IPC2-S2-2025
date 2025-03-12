/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.customers;

import com.joca.lacomputadorafeliz.sales.AdminCustomers;
import com.joca.lacomputadorafeliz.database.DBCustomers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.sales.Customer;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author joca
 */
@WebServlet(name = "CustomerLoader", urlPatterns = {"/controllers/customer-loader"})
public class CustomerLoader extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminCustomers adminCustomers = new AdminCustomers(request.getSession());
            Customer customer = adminCustomers.getCustomer(request);
            request.getRequestDispatcher("/controllers/sale-resume-loader?nit="+request.getParameter("nit")).forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los datos del cliente");
            request.getRequestDispatcher("/sales/new-sale.jsp").forward(request, response);
        } catch (EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("nit", request.getParameter("nit"));
            request.getRequestDispatcher("/sales/new-customer.jsp").forward(request, response);
        }
    }
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
