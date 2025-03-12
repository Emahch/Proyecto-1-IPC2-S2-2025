/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.customers;

import com.joca.lacomputadorafeliz.database.DBAssembly;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.sales.Customer;
import com.joca.lacomputadorafeliz.sales.AdminCustomers;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author joca
 */
@WebServlet(name = "SaleResumeLoader", urlPatterns = {"/controllers/sale-resume-loader"})
public class SaleResumeLoader extends HttpServlet {

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
            request.setAttribute("customer", customer);
            List<Integer> idAssembles = getSaleAssignments(request);
            DBAssembly dBAssembly = new DBAssembly(request.getSession());
            List<AssemblyDTO> assembles = dBAssembly.getAssembles();
            Map<Boolean, List<AssemblyDTO>> assemblesPartitioned = assembles.stream()
                    .collect(Collectors.partitioningBy(as -> idAssembles.stream()
                    .noneMatch(id -> as.getId() == id)));

            List<AssemblyDTO> assemblesAvailables = assemblesPartitioned.get(true);
            List<AssemblyDTO> assemblesInList = assemblesPartitioned.get(false);
            double totalSale = assemblesInList.stream()
                    .mapToDouble(AssemblyDTO::getComputerPrice)
                    .sum();
            request.setAttribute("assemblesAvailables", assemblesAvailables);
            request.setAttribute("assemblesInList", assemblesInList);
            request.setAttribute("totalSale", totalSale);
            request.getRequestDispatcher("/sales/sale-resume.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los datos");
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

    private List<Integer> getSaleAssignments(HttpServletRequest request) {
        String[] idAssembles;
        idAssembles = request.getParameterValues("saleAssignment");
        List<Integer> idAssemblesFixed = new ArrayList<>();
        if (idAssembles != null) {
            for (String idAssemble : idAssembles) {
                try {
                    idAssemblesFixed.add(Integer.parseInt(idAssemble));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        String id = request.getParameter("newId");
        if (id != null) {
            try {
                idAssemblesFixed.add(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String deleteID = request.getParameter("deleteId");
        if (deleteID != null) {
            try {
                int delete = Integer.parseInt(deleteID);
                idAssemblesFixed = idAssemblesFixed.stream()
                        .filter(iD -> iD != delete)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idAssemblesFixed;
    }
}
