package com.joca.lacomputadorafeliz.controllers.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author emahch
 */
@WebServlet(name = "LogOutController", urlPatterns = {"/controllers/log-out-servlet"})
public class LogOutController extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("usuario");
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath());
    }
}
