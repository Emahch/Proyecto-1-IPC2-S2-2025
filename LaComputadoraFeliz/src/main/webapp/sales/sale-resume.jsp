<%-- 
    Document   : admin-computers
    Created on : Mar 8, 2025, 10:53:47 PM
    Author     : joca
--%>

<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
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
                    <div style="background-color: #B71C1C" class="pt-2 px-2 mb-2 rounded">
                        <div class="row">
                            <div class="col-md-4">
                                <h5><b>Nit cliente:</b> ${customer.nit}</h5>
                            </div>
                            <div class="col-md-4">
                                <h5><b>Nombre:</b> ${customer.customerName}</h5>
                            </div>
                            <div class="col-md-4">
                                <h5><b>Fecha:</b> <c:out value="${LocalDate.now().format(DateTimeFormatter.ofPattern('dd-MM-yyyy'))}" /></h5>
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
                            <c:forEach items="${assemblesInList}" var="list">
                                <tr>
                                    <td>${list.id}</td>
                                    <td>${list.computerName}</td>
                                    <td>${list.computerPrice}</td>
                                    <td>
                                        <form action="${pageContext.servletContext.contextPath}/controllers/sale-resume-loader" method="GET" class="d-inline">
                                            <c:forEach items="${assemblesInList}" var="listed">
                                                <input type="hidden" name="saleAssignment" value="${listed.id}">
                                            </c:forEach>
                                            <input type="hidden" name="nit" value="${customer.nit}" >
                                            <input type="hidden" name="deleteId" value="${list.id}">
                                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="2" class="text-end">Total sin IVA:</th>
                                <th>Q${totalSale - totalSale*0.12}</th>
                                <th></th>
                            </tr>
                            <tr>
                                <th colspan="2" class="text-end">Total:</th>
                                <th>Q${totalSale}</th>
                                <th></th>
                            </tr>
                        </tfoot>
                    </table>
                    <b>Total de productos: ${assemblesInList.size()}</b>
                </div>
            </div>

            <!-- Add Product Card -->
            <div class="contenedor">
                <h4>Añadir a la compra</h4>
                <div class="card-body">
                    <form action="${pageContext.servletContext.contextPath}/controllers/sale-resume-loader" method="GET">
                        <c:forEach items="${assemblesInList}" var="list">
                            <input type="hidden" name="saleAssignment" value="${list.id}">
                        </c:forEach>
                        <input type="hidden" name="nit" value="${customer.nit}" >
                        <div class="row">
                            <div class="col-md-8">
                                <select name="newId" class="form-select">
                                    <option selected disabled>Selecciona un producto para añadir: </option>
                                    <c:forEach items="${assemblesAvailables}" var="product">
                                        <option value="${product.id}">${product.computerName} - Q${product.computerPrice}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <button type="submit" class="btn btn-primary">Añadir</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="container my-4">
                <div class="row">
                    <div class="col">
                        <a href="/LaComputadoraFeliz/sales/new-sale.jsp" class="btn btn-info">Cancelar Venta</a>
                    </div>
                    <div class="col text-end">
                        <form action="${pageContext.servletContext.contextPath}/controllers/sales-servlet" method="post">
                            <c:forEach items="${assemblesInList}" var="list">
                                <input type="hidden" name="saleAssignment" value="${list.id}">
                            </c:forEach>
                            <input type="hidden" name="nit" value="${customer.nit}" >
                            <button type="submit" class="btn btn-primary">Confirmar compra</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
