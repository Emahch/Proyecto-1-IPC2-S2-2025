/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.computers;

import com.joca.lacomputadorafeliz.database.DBComponents;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.InvalidDataException;
import com.joca.lacomputadorafeliz.model.computers.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminComponents {

    private DBComponents dbComponents;

    public AdminComponents(HttpSession session) throws SQLException, ClassNotFoundException {
        dbComponents = new DBComponents(session);
    }

    public List<Component> getComponents() throws SQLException {
        List<Component> computers = dbComponents.getComponents();
        return computers;
    }

    public void updateComponent(HttpServletRequest request) throws SQLException, InvalidDataException {
        Component component = getComponentFromRequest(request);
        String originalName = request.getParameter("originalName");
        dbComponents.updateComponent(component, originalName);
    }
    
    public Component searchComponent(HttpServletRequest request) throws SQLException, EntityNotFound {
        return dbComponents.searchComponent(request.getParameter("componentName"));
    }
    
    public void createComponent(HttpServletRequest request) throws SQLException, InvalidDataException {
        Component component = getComponentFromRequest(request);
        dbComponents.create(component);
    }
    
    public void deleteComponent(HttpServletRequest request) throws SQLException, EntityNotFound {
        dbComponents.deleteComponent(request.getParameter("componentName"));
    }

    private Component getComponentFromRequest(HttpServletRequest request) throws InvalidDataException {
        Component component = new Component();
        component.setName(request.getParameter("componentName"));
        try {
            component.setValue(Double.parseDouble(request.getParameter("value")));
            component.setAmount(Integer.parseInt(request.getParameter("amount")));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("No se ingreso una cantidad valida");
        }
        if (!component.isValid()) {
            throw new InvalidDataException("Revisa que el nombre del componente no exceda los 30 caracteres y que el costo sea mayor a 0");
        }
        return component;
    }
}
