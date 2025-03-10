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
        <title>Editar Componente</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>

    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <h2 class="text-center my-4">Editar Componente</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/edit-component-servlet">
                <input type="text" id="originalName" name="originalName" class="form-control" required value="${component.name}" hidden="true">
                <div class="mb-3">
                    <label for="componentName" class="form-label">Nombre del componente</label>
                    <input type="text" id="componentName" name="componentName" class="form-control" required value="${component.name}">
                </div>
                <div class="mb-3 col">
                    <label for="value" class="form-label">Costo Unitario</label>
                    <input type="text" id="value" name="value" class="form-control form-control-sm" required value="${component.value}">
                </div>
                <div class="mb-3 col">
                    <label for="amount" class="form-label">Cantidad</label>
                    <input type="number" id="amount" name="amount" class="form-control" required value="${component.amount}">
                </div>
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </form>
        </div>
    </body>
</html>
