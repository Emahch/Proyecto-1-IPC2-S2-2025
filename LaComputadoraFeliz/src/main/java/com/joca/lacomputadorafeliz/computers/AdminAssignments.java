/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.computers;

import com.joca.lacomputadorafeliz.database.DBComponentAssign;
import com.joca.lacomputadorafeliz.database.DBComponents;
import com.joca.lacomputadorafeliz.database.DBComputers;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.Component;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminAssignments {

    private DBComponentAssign dbAssign;

    public AdminAssignments(HttpSession session) throws SQLException, ClassNotFoundException {
        dbAssign = new DBComponentAssign(session);
    }

    public List<ComponentAsignDTO> getComponentsAssigned(HttpServletRequest request) throws SQLException {
        List<ComponentAsignDTO> componentsAsigned = dbAssign.getComponentAsign(request.getParameter("computerName"));
        return componentsAsigned;
    }

    public void updateAssignmentAmount(HttpServletRequest request) throws SQLException, InvalidDataException, ClassNotFoundException, EntityNotFound {
        ComponentAsignDTO componentAssigned = getComponentAsignedFromRequest(request);
        dbAssign.updateAsignAmount(componentAssigned);
        updateComputerValue(request);
    }

    public ComponentAsignDTO searchAssignment(HttpServletRequest request) throws SQLException, EntityNotFound {
        return dbAssign.searchAsign(request.getParameter("componentName"), request.getParameter("computerName"));
    }

    public void createAssignment(HttpServletRequest request) throws SQLException, InvalidDataException, ClassNotFoundException, EntityNotFound {
        ComponentAsignDTO componentAssigned = getComponentAsignedFromRequest(request);
        dbAssign.addComponent(componentAssigned);
        updateComputerValue(request);
    }

    public void deleteAsignment(HttpServletRequest request) throws SQLException, EntityNotFound, ClassNotFoundException {
        dbAssign.deleteAsignacion(request.getParameter("componentName"), request.getParameter("computerName"));
        updateComputerValue(request);
    }

    private ComponentAsignDTO getComponentAsignedFromRequest(HttpServletRequest request) throws InvalidDataException {
        ComponentAsignDTO componentAsigned = new ComponentAsignDTO();
        componentAsigned.setComputerName(request.getParameter("computerName"));
        componentAsigned.setComponentName(request.getParameter("componentName"));
        try {
            componentAsigned.setAmount(Integer.parseInt(request.getParameter("amount")));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("No se ingreso una cantidad valida");
        }
        if (!componentAsigned.isValid()) {
            throw new InvalidDataException("La cantidad asignada no puede ser menor o igual a 0");
        }
        return componentAsigned;
    }

    private void updateComputerValue(HttpServletRequest request) throws SQLException, ClassNotFoundException, EntityNotFound {
        List<ComponentAsignDTO> assignments = getComponentsAssigned(request);
        DBComponents dBComponents = new DBComponents(request.getSession());
        double total_value = 0;
        for (ComponentAsignDTO assignment : assignments) {
            Component component = dBComponents.searchComponent(assignment.getComponentName());
            total_value += component.getValue() * assignment.getAmount();
        }
        DBComputers dBComputers = new DBComputers(request.getSession());
        dBComputers.updateComputerValue(request.getParameter("computerName"), total_value);
    }
}
