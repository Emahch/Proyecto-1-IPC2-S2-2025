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
        <title>Nuevo Usuario</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>

    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <h2 class="text-center my-4">Editar Requisito Componente</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/edit-component-servlet">
                <div class="mb-3">
                    <label for="name" class="form-label">Nombre</label>
                    <input type="text" id="name" name="name" class="form-control" disabled="true" required value="COMP-1">
                </div>
                <div class="d-flex row">
                    <div class="mb-3 col">
                        <label for="cost" class="form-label">Costo Unitario</label>
                        <input type="number" id="cost" name="cost" class="form-control" disabled="true" value="100">
                    </div>
                    <div class="mb-3 col">
                        <label for="stock" class="form-label">Cantidad requerida</label>
                        <input type="number" id="stock" name="stock" class="form-control" value="3">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </form>
        </div>
    </body>
</html>
