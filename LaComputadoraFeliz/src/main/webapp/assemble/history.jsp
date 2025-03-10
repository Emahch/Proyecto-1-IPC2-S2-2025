<%-- 
    Document   : history
    Created on : Mar 10, 2025, 2:03:20 PM
    Author     : joca
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Historial</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <div class="">
            <h2 class="text-center my-4">Historial de ensambles</h2>
            <div class="contenedor mb-4">
                <form action="${pageContext.servletContext.contextPath}/controllers/assembles-loader" method="${ascendent ? 'GET' : 'POST'}" class="mb-4">
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-primary">
                            Ordenado por fecha: ${ascendent ? '▲ Ascendente' : '▼ Descendente'}
                        </button>
                    </div>
                </form>

                <div>
                    <table class="table table-hover table-dark table-striped">
                        <thead>
                            <tr>
                                <th>ID Ensamble</th>
                                <th>Nombre computadora</th>
                                <th>Usuario ensamblador</th>
                                <th>Fecha de ensamble</th>
                                <th>Costo de ensamble</th>
                                <th>Precio de venta</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- This would normally be populated from a database query -->
                            <c:forEach var="assemble" items="${assembles}">
                                <tr>
                                    <td>${assemble.id}</td>
                                    <td>${assemble.computerName}</td>
                                    <td>${assemble.assemblerUser}</td>
                                    <td>${assemble.getDateFormatted()}</td>
                                    <td>${assemble.totalValue}</td>
                                    <td>${assemble.computerPrice}</td>
                                    <td>${assemble.state.getPublicName()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
