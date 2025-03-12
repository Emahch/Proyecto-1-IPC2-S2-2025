/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.sales;

import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.sales.AdminCustomers;
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
@WebServlet(name = "ComputerController", urlPatterns = {"/controllers/customer-servlet"})
public class CustomerController extends HttpServlet {

    /**
     * Crea un nuevo cliente en la base de datos
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminCustomers adminCustomers = new AdminCustomers(request.getSession());
            adminCustomers.createCustomer(request);
            request.getRequestDispatcher("/controllers/customer-loader?nit="+request.getParameter("nit")).forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al cargar los datos, intentalo de nuevo más tarde");
            request.setAttribute("nit", request.getParameter("nit"));
            request.getRequestDispatcher("/sales/new-customer.jsp").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("nit", request.getParameter("nit"));
            request.getRequestDispatcher("/sales/new-customer.jsp").forward(request, response);
        }
    }
}
