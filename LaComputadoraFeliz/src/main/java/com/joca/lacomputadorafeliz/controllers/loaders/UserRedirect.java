/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders;

import com.joca.lacomputadorafeliz.model.users.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author joca
 */
public class UserRedirect {

    public UserRedirect() {
    }
    
    public String redirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        try {
            user = (User) request.getSession().getAttribute("usuario");
        } catch (Exception e) {
            e.printStackTrace();
            return "/controllers/log-out-servlet";
        }
        
        if (user == null) {
            return "/controllers/log-out-servlet";
        }
        
        switch (user.getUserRol().getId()) {
            case 1:
                return "/controllers/computers-loader";
            case 2:
                return "/sales/new-sale.jsp";
            case 3:
                return "/admin/home.jsp";
            default:
                return "/index.jsp";
        }
    }
}
