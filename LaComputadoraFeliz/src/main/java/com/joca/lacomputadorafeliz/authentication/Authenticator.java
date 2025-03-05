package com.joca.lacomputadorafeliz.authentication;

import com.joca.lacomputadorafeliz.database.DBUsers;
import com.joca.lacomputadorafeliz.exceptions.UserNotFoundException;
import com.joca.lacomputadorafeliz.model.users.User;
import com.mysql.cj.exceptions.DataReadException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 *
 * @author emahch
 */
public class IniciadorSesion {

    private DBUsers dbUsuarios;

    public IniciadorSesion(HttpSession session) throws SQLException, ClassNotFoundException {
        dbUsuarios = new DBUsers(session);
    }

    public User iniciarSesion(HttpServletRequest req) throws SQLException, DataReadException {
        if (req.getParameter("password") == null || req.getParameter("username") == null) {
            throw new DataReadException("Por favor llene los campos vacios");
        }
        try {
            User usuario = dbUsuarios.searchUser(req.getParameter("username"));
            PasswordEncripter encriptador = new PasswordEncripter();
            String contraseña = encriptador.encriptPassword(req.getParameter("password"));
            if (usuario.getPassword().equals(contraseña)) {
                return usuario;
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        throw new DataReadException("Nombre de usuario o contraseña incorrectos");
    }

}
