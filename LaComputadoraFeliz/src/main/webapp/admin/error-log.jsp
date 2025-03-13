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
        <title>Errores</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <h2 class="text-center py-4">Reporte de errores</h2>
        <div class="contenedor">
            <c:forEach items="${errors}" var="error" >
                <p>${error}</p>
            </c:forEach>
        </div>
    </body>
</html>
