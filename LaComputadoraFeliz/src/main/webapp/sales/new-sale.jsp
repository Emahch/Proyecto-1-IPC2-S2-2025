<%-- 
    Document   : admin-computers
    Created on : Mar 8, 2025, 10:53:47 PM
    Author     : joca
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nueva Venta</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <div>
            <div class="contenedor my-4">
                <h2 class="text-center pb-3">Resumen de la venta</h2>
                <div class="rounded">
                    <div style="background-color: #0A192F" class="pt-2 px-2 mb-2 rounded">
                        <div class="row">
                            <div class="col-md-4">
                                <h5>Venta #${sale.id}</h5>
                            </div>
                            <div class="col-md-4">
                                <h5>Nit cliente: ${sale.customerName}</h5>
                            </div>
                            <div class="col-md-4">
                                <h5>Fecha: ${sale.date}</h5>
                            </div>
                        </div>
                    </div>

                    <!-- Products Table -->
                    <table class="table table-striped table-dark">
                        <thead>
                            <tr>
                                <th>ID computadora</th>
                                <th>Nombre Computadora</th>
                                <th>Precio</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${sale.items}" var="item">
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.name}</td>
                                    <td>${item.price}</td>
                                    <td>
                                        <form action="removeSaleItem.jsp" method="post" class="d-inline">
                                            <input type="hidden" name="saleId" value="${sale.id}">
                                            <input type="hidden" name="itemId" value="${item.id}">
                                            <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="2" class="text-end">Total:</th>
                                <th>Q${sale.total}</th>
                                <th></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <!-- Add Product Card -->
            <div class="contenedor">
                <h4>Add Product to Sale</h4>
                <div class="card-body">
                    <form action="addSaleItem.jsp" method="post">
                        <input type="hidden" name="saleId" value="${sale.id}">
                        <div class="row">
                            <div class="col-md-8">
                                <select name="productId" class="form-select">
                                    <option selected disabled>Select a product to add</option>
                                    <c:forEach items="${availableProducts}" var="product">
                                        <option value="${product.id}">${product.name} - $${product.price}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <button type="submit" class="btn btn-primary">Add to Sale</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="container my-4">
                <div class="row">
                    <div class="col">
                        <a href="salesList.jsp" class="btn btn-info">Cancelar Venta</a>
                    </div>
                    <div class="col text-end">
                        <form action="finalizeSale.jsp" method="post" class="">
                            <input type="hidden" name="saleId" value="${sale.id}">
                            <button type="submit" class="btn btn-primary">Confirmar compra</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
