package com.joca.lacomputadorafeliz.authentication;

import com.joca.lacomputadorafeliz.database.DBUsers;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.users.User;
import com.mysql.cj.exceptions.DataReadException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 *
 * @author emahch
 */
public class Authenticator {

    private DBUsers dbUsuarios;

    public Authenticator(HttpSession session) throws SQLException, ClassNotFoundException {
        dbUsuarios = new DBUsers(session);
    }

    public User iniciarSesion(HttpServletRequest req) throws SQLException, InvalidDataException {
        if (req.getParameter("password") == null || req.getParameter("username") == null) {
            throw new InvalidDataException("Por favor llene los campos vacios");
        }
        try {
            User usuario = dbUsuarios.searchUser(req.getParameter("username"));
            PasswordEncripter encriptador = new PasswordEncripter();
            String contraseña = encriptador.encriptPassword(req.getParameter("password"));
            if (usuario.getPassword().equals(contraseña)) {
                return usuario;
            }
        } catch (EntityNotFound e) {
            e.printStackTrace();
        }
        throw new InvalidDataException("Nombre de usuario o contraseña incorrectos");
    }

    public void registerUser(HttpServletRequest req) throws SQLException, InvalidDataException {
        
    }
}
