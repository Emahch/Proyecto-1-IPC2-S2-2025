/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.loaders.computers;

import com.joca.lacomputadorafeliz.controllers.loaders.UserRedirect;
import com.joca.lacomputadorafeliz.database.DBAssembly;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author joca
 */
@WebServlet(name = "AssemblesLoader", urlPatterns = {"/controllers/assembles-loader"})
public class AssemblesLoader extends HttpServlet {

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
            DBAssembly dBAssembly = new DBAssembly(request.getSession());
            List<AssemblyDTO> assembles = dBAssembly.getAssembles();
            List<AssemblyDTO> assemblesOrderd;
            assemblesOrderd = assembles.stream()
                    .sorted(Comparator.comparing(AssemblyDTO::getDate).reversed())
                    .collect(Collectors.toList());
            request.setAttribute("assembles", assemblesOrderd);
            request.setAttribute("ascendent", false);
            request.getRequestDispatcher("/assemble/history.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los datos de los ensambles");
            UserRedirect userRedirect = new UserRedirect();
            request.getRequestDispatcher(userRedirect.redirect(request, response)).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            DBAssembly dBAssembly = new DBAssembly(request.getSession());
            List<AssemblyDTO> assembles = dBAssembly.getAssembles();
            List<AssemblyDTO> assemblesOrderd;
            assemblesOrderd = assembles.stream()
                    .sorted(Comparator.comparing(AssemblyDTO::getDate))
                    .collect(Collectors.toList());
            request.setAttribute("assembles", assemblesOrderd);
            request.setAttribute("ascendent", true);
            request.getRequestDispatcher("/assemble/history.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener los datos de los ensambles");
            UserRedirect userRedirect = new UserRedirect();
            request.getRequestDispatcher(userRedirect.redirect(request, response)).forward(request, response);
        }
    }
}
