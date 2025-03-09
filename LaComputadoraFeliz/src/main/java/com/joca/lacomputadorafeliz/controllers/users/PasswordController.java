/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.users;

import com.joca.lacomputadorafeliz.authentication.PasswordVTO;
import com.joca.lacomputadorafeliz.database.DBUsers;
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
@WebServlet(name = "PasswordController", urlPatterns = {"/controllers/password-servlet"})
public class PasswordController extends HttpServlet {


    /**
     * Handles the HTTP <code>POST</code> method.
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
            DBUsers dBUsers = new DBUsers(request.getSession());
            PasswordVTO newPassword = new PasswordVTO(request.getParameter("password"), true);
            if (!newPassword.isValid()) {
                throw new InvalidDataException("La contraseña no cumple con los requisitos");
            }
            String username = request.getParameter("usuario");
            dBUsers.updatePassword(newPassword, username);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrio un error al actualizar la contraseña");
            request.setAttribute("usuario", request.getParameter("usuario"));
            request.getRequestDispatcher("/pages/update-password.jsp").forward(request, response);
        } catch (InvalidDataException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("usuario", request.getParameter("usuario"));
            request.getRequestDispatcher("/pages/update-password.jsp").forward(request, response);
        }
    }
}
