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
        <h2 class="text-center my-4">Editar usuario</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/edit-user-servlet">
                <input type="text" id="name" name="usernameOriginal" class="form-control" required value="${usuario.userName}" hidden="true">
                <div class="mb-3">
                    <label for="name" class="form-label">Nombre</label>
                    <input type="text" id="name" name="name" class="form-control" required value="${usuario.name}">
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Nombre de usuario</label>
                    <input type="text" id="username" name="username" class="form-control" required placeholder="Ingresa un usuario" value="${usuario.userName}">
                </div>
                <div class="mb-3">
                    <label for="rol" class="form-label">Rol</label>
                    <select id="rol" name="rol" class="form-select">
                        <option selected value="${usuario.userRol.id}">${usuario.userRol.name}</option>
                        <c:forEach items="${roles}" var="rol">
                            <c:if test="${rol.id != usuario.userRol.id}">
                                <option value="${rol.id}">${rol.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </form>
        </div>
    </body>
</html>
