/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.sales;

import com.joca.lacomputadorafeliz.database.DBAssembly;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.sales.Sale;
import com.joca.lacomputadorafeliz.model.users.User;
import com.joca.lacomputadorafeliz.sales.AdminSales;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joca
 */
@WebServlet(name = "SalesController", urlPatterns = {"/controllers/sales-servlet"})
public class SalesController extends HttpServlet {

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
            AdminSales adminSales = new AdminSales(request.getSession());
            int id = adminSales.newSale(getSaleFromParameters(request));
            request.setAttribute("id", id);
            request.getRequestDispatcher("/sales/download-bill.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al realizar la venta, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/sales/new-sale.jsp").forward(request, response);
        } catch (InvalidDataException ex) {
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/sales/new-sale.jsp").forward(request, response);
        }
    }

    private Sale getSaleFromParameters(HttpServletRequest request) throws InvalidDataException, SQLException, ClassNotFoundException {
        Sale sale = new Sale();
        String[] assemblesId = request.getParameterValues("saleAssignment");
        if (assemblesId == null) {
            throw new InvalidDataException("No se especificarion computadoras para la venta");
        }
        String nit = request.getParameter("nit");
        if (nit == null) {
            throw new InvalidDataException("No se ingreso un nit valido");
        }
        List<AssemblyDTO> assembles = new ArrayList<>();
        for (String assemble : assemblesId) {
            DBAssembly dBAssembly = new DBAssembly(request.getSession());
            try {
                assembles.add(dBAssembly.getAssemble(Integer.valueOf(assemble)));
            } catch (EntityNotFound | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        sale.setAssembles(assembles);
        sale.setSellerUser(((User) request.getSession().getAttribute("usuario")).getUserName());
        sale.setNit(nit);
        return sale;
    }
}
