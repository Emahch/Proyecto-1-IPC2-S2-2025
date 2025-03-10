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
        <title>Editar Asignacion</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>

    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <h2 class="text-center my-4">Nueva asignacion de componente</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/assignment-servlet">
                <h2 class="text-center mb-2">Editando para la computadora: ${computerName}</h2>
                <div class="mb-3">
                    <label for="componentName" class="form-label">Componentes disponibles</label>
                    <select class="form-select" id="componentName" name="componentName">
                        <option selected>Selecciona un componente</option>
                        <c:forEach items="${components}" var="component">
                            <option value="${component.name}">${component.name} - Q ${component.value}</option>
                        </c:forEach>
                    </select>
                    <input type="text" id="computerName" name="computerName" hidden="true" readonly value="${computerName}" />
                </div>
                <div class="mb-3 col">
                    <label for="amount" class="form-label">Cantidad requerida</label>
                    <input type="number" id="amount" name="amount" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </form>
        </div>
    </body>
</html>
