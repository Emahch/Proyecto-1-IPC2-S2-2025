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
            <h2 class="text-center my-4 col">Roles</h2>
            <table class="table table-dark shadow table-borderless mt-4">
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${roles}" var="rol" >
                        <tr>
                            <td>${rol.id}</td>
                            <td>
                                <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/edit-rol-servlet"
                                      class="d-flex">
                                    <input name="codigo" value="${rol.id}" hidden="true" />
                                    <input class="rounded form-control" name="nombre" value="${rol.name}" />
                                    <button class="btn btn-primary btn-sm mx-1">Actualizar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
