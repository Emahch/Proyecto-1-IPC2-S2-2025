<%-- 
    Document   : admin-users
    Created on : Mar 4, 2025, 9:19:29 AM
    Author     : joca
--%>

<%@page import="com.joca.lacomputadorafeliz.model.users.User"%>
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
        <jsp:include page="/includes/toast.jsp"/>
        <div class="container">
            <div class="d-flex row">
                <form method="GET" class="col d-flex mt-5" action="${pageContext.servletContext.contextPath}/controllers/new-user-loader">
                    <button class="btn-primary rounded p-2">
                        <i class="bi bi-person-fill-add me-1"></i>Crear Nuevo Usuario</button>
                </form>
                <h2 class="text-center my-4 col">Usuarios</h2>
                <form method="GET" class="col d-flex justify-content-end mt-5" action="${pageContext.servletContext.contextPath}/controllers/edit-roles-loader">
                    <button class="btn-primary rounded p-2">
                        <i class="bi bi-pencil me-1"></i>Editar Roles</button>
                </form>
            </div>
            <table class="table table-dark shadow table-striped mt-4 table-hover">
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
                                <div class="d-flex">
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/edit-user-loader">
                                        <input name="username" value="${user.userName}" hidden="true" />
                                        <!-- boton editar -->
                                        <button class="btn btn-info" type="submit"><i class="bi bi-pencil"></i></button>
                                    </form>
                                    <c:if test="${user.userName != sessionScope.usuario.userName}">
                                        <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/users-servlet">
                                            <input name="username" value="${user.userName}" hidden="true" />
                                            <!-- boton eliminar -->
                                            <button class="btn btn-danger mx-2"><i class="bi bi-trash"></i></button>
                                        </form>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
