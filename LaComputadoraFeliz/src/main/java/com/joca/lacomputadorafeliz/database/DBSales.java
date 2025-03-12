/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.database;

import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.computers.ComputerStateEnum;
import com.joca.lacomputadorafeliz.model.sales.Sale;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author joca
 */
public class DBSales extends DBConnection {

    public DBSales(HttpSession session) throws SQLException, ClassNotFoundException {
        super(session);
    }

    public DBSales(Connection connection) {
        super(connection);
    }

    public void newSale(Sale sale, byte[] pdf) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO ventas (id,nit,fecha,total_costo,total_venta,usuario_vendedor,factura) VALUES (?,?,?,?,?,?,?);");
        preparedStatement.setInt(1, sale.getiDSale());
        preparedStatement.setString(2, sale.getNit());
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
        preparedStatement.setDouble(4, sale.getTotalValue());
        preparedStatement.setDouble(5, sale.getTotalPrice());
        preparedStatement.setString(6, sale.getSellerUser());
        preparedStatement.setBytes(7, pdf);
        preparedStatement.executeUpdate();
    }
    
    public byte[] getBillPdf(int saleID) throws SQLException, EntityNotFound {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT factura FROM ventas WHERE id = ?;");
        preparedStatement.setInt(1, saleID);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
            return result.getBytes("factura");
        }
        throw new EntityNotFound("No se encontro la factura para la venta " + saleID);
    }

    public void newSaleAssignment(int saleID, AssemblyDTO assembly) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("INSERT INTO asignacion_ventas (id_venta,id_ensamble,nombre_computadora) VALUES (?,?,?)");
        preparedStatement.setInt(1, saleID);
        preparedStatement.setInt(2, assembly.getId());
        preparedStatement.setString(3, assembly.getComputerName());
        preparedStatement.executeUpdate();
    }

    public void updateStateAssembly(int id, ComputerStateEnum state) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("UPDATE ensambles SET estado = ? WHERE id = ?;");
        preparedStatement.setString(1, state.name());
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public int getIdAvailable() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareCall("SELECT id FROM ventas ORDER BY ID DESC LIMIT 1;");
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {
            return result.getInt("id") + 1;
        }
        return 1;
    }

    public Connection getConnection() {
        return connection;
    }
}
