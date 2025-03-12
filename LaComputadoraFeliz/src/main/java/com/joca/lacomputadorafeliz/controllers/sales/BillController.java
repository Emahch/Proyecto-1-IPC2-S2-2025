/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.joca.lacomputadorafeliz.controllers.sales;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.sales.AdminSales;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 *
 * @author joca
 */
@WebServlet(name = "BillController", urlPatterns = {"/controllers/bill-servlet"})
public class BillController extends HttpServlet {

    /**
     * Devuelve una factura en base al id de venta
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminSales adminSales = new AdminSales(request.getSession());
            byte[] pdf = adminSales.getBillPdf(request);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=factura_" + request.getParameter("id") + ".pdf");
            response.setContentLength(pdf.length);

            OutputStream out = response.getOutputStream();
            out.write(pdf);
            out.flush();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrio un error al obtener la factura, intentalo de nuevo más tarde");
            request.setAttribute("id", request.getParameter("id"));
            request.getRequestDispatcher("/sales/download-bill.jsp").forward(request, response);
        } catch (EntityNotFound ex) {
            ex.printStackTrace();
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("id", request.getParameter("id"));
            request.getRequestDispatcher("/sales/download-bill.jsp").forward(request, response);
        }
    }
}
