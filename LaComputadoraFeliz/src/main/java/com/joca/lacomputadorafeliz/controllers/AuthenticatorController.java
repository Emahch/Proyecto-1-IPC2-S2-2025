/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers;

import com.joca.lacomputadorafeliz.authentication.Authenticator;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
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
@WebServlet(name = "AuthenticatorController", urlPatterns = {"/controllers/authenticator-servlet"})
public class AuthenticatorController extends HttpServlet {

    /**
     * Handles the HTTP <code>Login</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.removeAttribute("error");
        request.getSession().removeAttribute("usuario");
        try {
            Authenticator iniciadorSesion = new Authenticator(request.getSession());
            User usuario = iniciadorSesion.iniciarSesion(request);
            request.getSession().setAttribute("usuario", usuario);

            response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
        } catch (ClassNotFoundException | SQLException | InvalidDataException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

}
