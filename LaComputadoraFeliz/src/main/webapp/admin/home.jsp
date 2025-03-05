<%-- 
    Document   : Home
    Created on : Mar 2, 2025, 10:32:10 PM
    Author     : joca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <div class="d-flex justify-content-center align-items-center my-4">
            <h1 class="mx-auto">Bienvenido ${sessionScope.usuario.name}</h1>
        </div>
    </body>
</html>
