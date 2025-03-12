/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.sales;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.joca.lacomputadorafeliz.database.DBSales;
import com.joca.lacomputadorafeliz.exceptions.EntityNotFound;
import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import com.joca.lacomputadorafeliz.model.computers.ComputerStateEnum;
import com.joca.lacomputadorafeliz.model.sales.Sale;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author joca
 */
public class AdminSales {

    public static final double IVA_PERCENTAGE = 0.12;

    private DBSales dbSales;

    public AdminSales(HttpSession session) throws SQLException, ClassNotFoundException {
        dbSales = new DBSales(session);
    }

    public int newSale(Sale sale) throws SQLException {
        int id = dbSales.getIdAvailable();
        sale.setiDSale(id);
        sale.setTotalPrice(calculateTotalPrice(sale.getAssembles()));
        sale.setTotalValue(calculateTotalValue(sale.getAssembles()));

        try {
            dbSales.getConnection().setAutoCommit(false);
            dbSales.newSale(sale, generateBillPdf(sale));
            for (AssemblyDTO assemble : sale.getAssembles()) {
                dbSales.newSaleAssignment(id, assemble);
                dbSales.updateStateAssembly(assemble.getId(), ComputerStateEnum.VENDIDA);
            }
            dbSales.getConnection().commit();
        } catch (SQLException e) {
            dbSales.getConnection().rollback();
            dbSales.getConnection().setAutoCommit(true);
            throw e;
        } finally {
            dbSales.getConnection().setAutoCommit(true);
        }
        return id;
    }

    private double calculateTotalValue(List<AssemblyDTO> assembles) {
        double totalValue = assembles.stream()
                .mapToDouble(a -> a.getTotalValue())
                .sum();
        return totalValue + totalValue * IVA_PERCENTAGE;
    }

    private double calculateTotalPrice(List<AssemblyDTO> assembles) {
        double totalPrice = assembles.stream()
                .mapToDouble(a -> a.getComputerPrice())
                .sum();
        return totalPrice;
    }

    private byte[] generateBillPdf(Sale sale) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("La Computadora Feliz", titleFont));
            document.add(new Paragraph("Factura No. " + sale.getiDSale(), titleFont));
            document.add(new Paragraph("\nNit del cliente: " + sale.getNit(), textFont));
            document.add(new Paragraph("Fecha: " + LocalDate.now(), textFont));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(new PdfPCell(new Phrase("Identificador", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Nombre computadora", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", boldFont)));

            for (AssemblyDTO assemble : sale.getAssembles()) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(assemble.getId()), textFont)));
                table.addCell(new PdfPCell(new Phrase(assemble.getComputerName(), textFont)));
                table.addCell(new PdfPCell(new Phrase("Q" + assemble.getComputerPrice(), textFont)));
            }

            document.add(table);
            document.add(new Paragraph("Total sin iva: Q" + (sale.getTotalPrice() - sale.getTotalPrice() * IVA_PERCENTAGE), boldFont));
            document.add(new Paragraph("Total con impuestos: Q" + sale.getTotalPrice(), boldFont));
            document.add(new Paragraph("!Muchas gracias por su compra, esperamos que vuelva pronto!", textFont));
            document.add(new LineSeparator());
            document.add(new Paragraph("La Computadora Feliz© 2025. Todos los derechos reservados", textFont));
            document.close();
            byte[] pdfBytes = outputStream.toByteArray();
            return pdfBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getBillPdf(HttpServletRequest request) throws SQLException, EntityNotFound {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new EntityNotFound("No se ingreso un id de venta valido");
        }
        if (id <= 0) {
            throw new EntityNotFound("No se ingreso un id de venta valido");
        }
        return dbSales.getBillPdf(id);
    }
}
