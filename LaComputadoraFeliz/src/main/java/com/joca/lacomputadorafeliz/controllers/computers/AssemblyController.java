/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.computers;

import com.joca.lacomputadorafeliz.database.DBAssembly;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.NotEnoughComponentsException;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.users.User;
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
@WebServlet(name = "AssemblyController", urlPatterns = {"/controllers/assembly-servlet"})
public class AssemblyController extends HttpServlet {

    /**
     * Ensambla una nueva computadora en el sistema
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            DBAssembly dBAssembly = new DBAssembly(request.getSession());
            AssemblyDTO assembly = new AssemblyDTO();
            assembly.setAssemblerUser(((User)request.getSession().getAttribute("usuario")).getUserName());
            assembly.setComputerName(request.getParameter("computerName"));
            dBAssembly.newAssembly(assembly);
            request.setAttribute("success", true);
            request.setAttribute("message", "Ensamblaje realizado con exito");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException  ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al realizar el ensamblaje, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        } catch (EntityNotFound | NotEnoughComponentsException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/computer-loader").forward(request, response);
        } 
    }
}
