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
        <title>Nuevo Componente</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
        
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <h2 class="text-center my-4">Nuevo Componente</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/component-servlet">
                <div class="mb-3">
                    <label for="componentName" class="form-label">Nombre</label>
                    <input type="text" id="componentName" name="componentName" class="form-control" required placeholder="Ingresa un nombre para el componente">
                </div>
                <div class="mb-3">
                    <label for="value" class="form-label">Costo unitario</label>
                    <input type="text" id="value" name="value" class="form-control" required placeholder="Ingresa el costo del componente en Quetzales (Q)">
                </div>
                <div class="mb-3 col">
                    <label for="amount" class="form-label">Cantidad</label>
                    <input type="number" id="amount" name="amount" class="form-control" required placeholder="Ingresa la cantidad de componentes existentes">
                </div>
                <button type="submit" class="btn btn-primary">Crear Componente</button>
            </form>
        </div>
    </body>
</html>
