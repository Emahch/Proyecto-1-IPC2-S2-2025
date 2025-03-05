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
            <a class="btn-primary rounded p-2" style="text-decoration: none" href="${pageContext.servletContext.contextPath}/admin/new-user.jsp">
                <i class="bi bi-person-fill-add"></i>Crear Nuevo Usuario</a>
            <a class="btn-primary rounded p-2" style="text-decoration: none" href="${pageContext.servletContext.contextPath}/admin/new-user.jsp">
                <i class="bi bi-person-fill-add"></i>Administrar Roles</a>
            <table class="table table-dark table-striped mt-4">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>Juan Pérez</td>
                        <td>juan@example.com</td>
                        <td>
                            <form method="">
                            <button class="btn btn-warning btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
