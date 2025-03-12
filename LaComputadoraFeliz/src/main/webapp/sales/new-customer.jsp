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
        <title>Nuevo Cliente</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>

    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <div class="d-flex justify-content-center align-items-center mt-5">
            <div class="contenedor-login">
                <h2 class="text-center">Nuevo cliente</h2>
                <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/customer-servlet">
                    <div class="mb-3">
                        <label for="nit" class="form-label">Nit</label>
                        <input type="text" class="form-control" id="nit" name="nit" placeholder="Ingresa un nit" value="${nit}" required>
                    </div>
                    <div class="mb-3">
                        <label for="customerName" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="customerName" name="customerName" placeholder="Ingresa un nombre" required>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Direccion</label>
                        <input type="text" class="form-control" id="address" name="address" placeholder="Ingresa una direccion" required>
                    </div>
                    <button class="btn btn-primary w-100">
                        <i class="bi bi-person-plus me-2"></i>Crear cliente y continuar
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
