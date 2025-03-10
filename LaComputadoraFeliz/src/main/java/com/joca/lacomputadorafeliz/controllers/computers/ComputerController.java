/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.computers;

import com.joca.lacomputadorafeliz.computers.AdminComputers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
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
@WebServlet(name = "ComputerController", urlPatterns = {"/controllers/computer-servlet"})
public class ComputerController extends HttpServlet {

    /**
     * Crea una nueva computadora en la base de datos
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
            AdminComputers adminComputers = new AdminComputers(request.getSession());
            adminComputers.createComputer(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Computadora creada con exito");
            request.getRequestDispatcher("/controllers/computers-loader").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al crear la computadora, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/admin/new-computer.jsp").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/admin/new-computer.jsp").forward(request, response);
        }
    }
    
    /**
     * Elimina una computadora
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
            AdminComputers adminComputers = new AdminComputers(request.getSession());
            adminComputers.deleteComputer(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Computadora eliminada con exito");
            request.getRequestDispatcher("/controllers/computers-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException | EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al eliminar la computadora, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/computers-loader").forward(request, response);
        }
    }
}
