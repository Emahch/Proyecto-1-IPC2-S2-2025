/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author joca
 */
public abstract class DBConnection {
    
    public static final int MYSQL_DUPLICATED_KEY = 1062;
    
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/LaComputadoraFeliz";
    private static final String USER = "rootx";
    private static final String PASSWORD = "password1234";

    protected Connection connection;

    public DBConnection(HttpSession session) throws SQLException, ClassNotFoundException {
        obtenerOCrearConexion(session);
    }

    /**
     * Intenta obtener una conexion existente en la sesion o crea una nueva
     *
     * @param session
     * @throws SQLException si ocurre un error al conectar con la base de datos
     * @throws ClassNotFoundException si la clase no es encontrada
     */
    private void obtenerOCrearConexion(HttpSession session) throws SQLException, ClassNotFoundException {
        connection = (Connection) session.getAttribute("connection");

        if (connection != null) {
            return;
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
        session.removeAttribute("connection");
        session.setAttribute("connection", connection);
    }
    
}
