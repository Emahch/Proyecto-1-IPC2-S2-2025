<%-- 
    Document   : new-user
    Created on : Mar 4, 2025, 9:55:24 AM
    Author     : joca
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nueva Computadora</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
        
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <h2 class="text-center my-4">Nueva Computadora</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/computer-servlet">
                <div class="mb-3">
                    <label for="computerName" class="form-label">Nombre</label>
                    <input type="text" id="computerName" name="computerName" class="form-control" required placeholder="Ingresa un nombre para la computadora">
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Precio de Venta</label>
                    <input type="text" id="price" name="price" class="form-control" required placeholder="Ingresa un precio de venta en Quetzales (Q)">
                </div>
                <button type="submit" class="btn btn-primary">Crear Computadora</button>
            </form>
        </div>
    </body>
</html>
