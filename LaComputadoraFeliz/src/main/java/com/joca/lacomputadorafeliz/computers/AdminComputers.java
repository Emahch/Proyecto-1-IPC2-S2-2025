/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.computers;

import com.joca.lacomputadorafeliz.database.DBComputers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.Computer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminComputers {

    private DBComputers dbComputers;

    public AdminComputers(HttpSession session) throws SQLException, ClassNotFoundException {
        dbComputers = new DBComputers(session);
    }

    public List<Computer> getComputers() throws SQLException {
        List<Computer> computers = dbComputers.getComputers();
        return computers;
    }

    public void updateInfoComputer(HttpServletRequest request) throws SQLException, InvalidDataException {
        dbComputers.updateComputer(getComputerFromRequest(request), request.getParameter("originalName"));
    }
    
    public Computer searchComputer(HttpServletRequest request) throws SQLException, EntityNotFound {
        return dbComputers.searchComputer(request.getParameter("computerName"));
    }
    
    public void createComputer(HttpServletRequest request) throws SQLException, InvalidDataException {
        Computer computer = getComputerFromRequest(request);
        dbComputers.createComputer(computer);
    }
    
    public void deleteComputer(HttpServletRequest request) throws SQLException, EntityNotFound {
        dbComputers.deleteComputer(request.getParameter("computerName"));
    }

    private Computer getComputerFromRequest(HttpServletRequest request) throws InvalidDataException {
        Computer computer = new Computer();
        computer.setName(request.getParameter("computerName"));
        try {
            computer.setPrice(Double.parseDouble(request.getParameter("price")));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("No se ingreso un precio valido");
        }
        if (!computer.isValid()) {
            throw new InvalidDataException("Revisa que el nombre de la computadora no exceda los 30 caracteres y que el precio sea mayor a 0");
        }
        return computer;
    }
}
