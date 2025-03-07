<%-- 
    Document   : admin-users
    Created on : Mar 4, 2025, 9:19:29 AM
    Author     : joca
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuarios</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center my-4">Usuarios</h2>
            <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/roles-servlet">
                <button class="btn-primary rounded p-2 mb-2">
                    <i class="bi bi-person-fill-add me-1"></i>Crear Nuevo Usuario</button>
            </form>
            <button class="btn-primary rounded p-2" style="text-decoration: none" href="${pageContext.servletContext.contextPath}/admin/roles.jsp">
                <i class="bi bi-person-fill-add"></i>Administrar Roles</button>
            <table class="table table-dark table-striped mt-4">
                <thead>
                    <tr>
                        <th>Usuario</th>
                        <th>Nombre</th>
                        <th>Rol</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user" >
                        <tr>
                            <td>${user.userName}</td>
                            <td>${user.name}</td>
                            <td>${user.userRol.name}</td>
                            <td>
                                <form method="">
                                    <button class="btn btn-warning btn-sm">Editar</button>
                                    <button class="btn btn-danger btn-sm">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
