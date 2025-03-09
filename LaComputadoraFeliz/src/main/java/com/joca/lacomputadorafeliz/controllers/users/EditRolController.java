/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.users;

import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.users.AdminRoles;
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
@WebServlet(name = "EditRolController", urlPatterns = {"/controllers/edit-rol-servlet"})
public class EditRolController extends HttpServlet {

    /**
     * Edita la información de un rol en el sistema
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
            AdminRoles adminRoles = new AdminRoles(request.getSession());
            adminRoles.updateRoleName(request);
            request.setAttribute("success", true);
            request.setAttribute("message", "Rol actualizado con exito");
            request.getRequestDispatcher("/controllers/edit-roles-loader").forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al actualizar el rol, intentalo de nuevo más tarde");
            request.getRequestDispatcher("/controllers/edit-roles-loader").forward(request, response);
        } catch (InvalidDataException ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/controllers/edit-roles-loader").forward(request, response);
        }
    }
}
