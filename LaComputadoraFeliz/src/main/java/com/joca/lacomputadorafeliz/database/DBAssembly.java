/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.exceptions.NotEnoughComponentsException;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.computers.ComponentAsignDTO;
import com.joca.lacomputadorafeliz.model.computers.ComputerStateEnum;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joca
 */
public class DBAssembly extends DBConnection {

    public DBAssembly(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    /**
     * Crea un nuevo ensamblaje
     *
     * @param assembly
     * @throws SQLException
     * @throws
     * com.joca.lacomputadorafeliz.exceptions.NotEnoughComponentsException
     * @throws com.joca.lacomputadorafeliz.exceptions.EntityNotFound
     */
    public void newAssembly(AssemblyDTO assembly) throws SQLException, NotEnoughComponentsException, EntityNotFound {
        double totalValue = 0;

        try {
            totalValue = calculateTotalValue(assembly);

        } catch (SQLException e) {
            connection.setAutoCommit(true);
            throw e;
        }
        assembly.setTotalValue(totalValue);
        assembly.setState(ComputerStateEnum.EN_VENTA);
        try (PreparedStatement statementAssembly = connection.prepareStatement("INSERT INTO ensambles (nombre_computadora,usuario_ensamblador,"
                + "fecha_ensamble,costo_ensamblaje,precio_venta,estado)"
                + " VALUES (?,?,?,?,?,?);")) {
            statementAssembly.setString(1, assembly.getComputerName());
            statementAssembly.setString(2, assembly.getAssemblerUser());
            LocalDate date = LocalDate.now();
            statementAssembly.setDate(3, Date.valueOf(date));
            statementAssembly.setDouble(4, assembly.getTotalValue());
            statementAssembly.setDouble(5, assembly.getComputerPrice());
            statementAssembly.setString(6, assembly.getState().name());
            statementAssembly.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Calcula el valor total del ensamblaje y actualiza las cantidades de los
     * componentes
     *
     * @param assembly
     * @return total value
     * @throws SQLException
     * @throws NotEnoughComponentsException
     */
    private double calculateTotalValue(AssemblyDTO assembly) throws SQLException, NotEnoughComponentsException, EntityNotFound {
        DBComponentAssign dBComponentAssign = new DBComponentAssign(connection);
        List<ComponentAsignDTO> componentsAssigned = dBComponentAssign.getComponentAsign(assembly.getComputerName());
        DBComputers dBComputers = new DBComputers(connection);
        assembly.setComputerPrice(dBComputers.getComputerValue(assembly.getComputerName()));
        double totalValue = 0;
        for (ComponentAsignDTO assignment : componentsAssigned) {
            if (assignment.getAmount() > assignment.getStock()) {
                throw new NotEnoughComponentsException("No hay suficientes " + assignment.getComponentName() + " para ensamblar la computadora");
            }
            totalValue += assignment.getAmount() * assignment.getComponentValue();
        }
        DBComponents dBComponents = new DBComponents(connection);
        connection.setAutoCommit(false);
        for (ComponentAsignDTO assignment : componentsAssigned) {
            dBComponents.updateComponentAmount(assignment.getComponentName(), assignment.getStock() - assignment.getAmount());
        }
        return totalValue;
    }

    public List<AssemblyDTO> getAssembles() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM ensambles WHERE estado = ?;");
        preparedStatement.setString(1, ComputerStateEnum.EN_VENTA.name());
        ResultSet result = preparedStatement.executeQuery();

        List<AssemblyDTO> assembles = new ArrayList<>();
        while (result.next()) {
            assembles.add(getAssembleFromResult(result));
        }
        return assembles;
    }
    
    public AssemblyDTO getAssemble(int id) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT * FROM ensambles WHERE id = ? AND estado = ?;");
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, ComputerStateEnum.EN_VENTA.name());
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {
            return getAssembleFromResult(result);
        }
        throw new EntityNotFound("No se encontro la computadora con el id " + id);
    }

    private AssemblyDTO getAssembleFromResult(ResultSet result) throws SQLException {
        AssemblyDTO assemble = new AssemblyDTO();
        assemble.setId(result.getInt("id"));
        assemble.setAssemblerUser(result.getString("usuario_ensamblador"));
        assemble.setComputerName(result.getString("nombre_computadora"));
        assemble.setDate(result.getDate("fecha_ensamble").toLocalDate());
        assemble.setTotalValue(result.getDouble("costo_ensamblaje"));
        assemble.setComputerPrice(result.getDouble("precio_venta"));
        assemble.setState(ComputerStateEnum.valueOf(result.getString("estado")));
        return assemble;
    }
}
