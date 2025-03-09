/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.users;

import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.users.AdminUsers;
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
@WebServlet(name = "UsersController", urlPatterns = {"/controllers/users-servlet"})
public class UsersController extends HttpServlet {

    /**
     * Elimina un usuario
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
            AdminUsers adminUsers = new AdminUsers(request.getSession());
            adminUsers.deleteUser(request);
            request.setAttribute("message", "Usuario eliminado con éxito");
            request.setAttribute("success", true);
            request.getRequestDispatcher("/controllers/users-loader").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al intentar eliminar el usuario");
            request.getRequestDispatcher("/controllers/users-loader").forward(request, response);
        }
    }
    
    /**
     * Crea un nuevo usuario en el sistema
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
            AdminUsers adminUsers = new AdminUsers(request.getSession());
            adminUsers.createUser(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Usuario creado con exito");
            request.getRequestDispatcher("/controllers/users-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al crear el usuario, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/new-user-loader").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/new-user-loader").forward(request, response);
        }
    }
}
