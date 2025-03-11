<%-- 
    Document   : index
    Created on : Feb 27, 2025, 11:08:26 PM
    Author     : joca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar Cliente</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <h2 class="text-center py-4">Iniciar venta</h2>
        <div class="d-flex justify-content-center align-items-center vh-80">
            <div class="contenedor-login">
                <div class="d-flex justify-content-center align-items-center mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor" class="bi bi-person-square" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
                    <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1v-1c0-1-1-4-6-4s-6 3-6 4v1a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>
                    </svg>
                </div>
                <h2 class="text-center">Buscar cliente</h2>
                <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/new-sale-loader">
                    <div class="mb-3">
                        <label for="nit" class="form-label">Nit</label>
                        <input type="number" class="form-control" id="nit" name="nit" placeholder="Ingresa un nit" required>
                    </div>
                    <button class="btn btn-primary w-100">
                        <i class="bi bi-search me-1"></i></i>Continuar</button>
                </form>
            </div>
        </div>
    </body>
</html>
