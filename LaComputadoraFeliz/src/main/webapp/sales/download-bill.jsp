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
        <title>Iniciar venta</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <h2 class="text-center py-4">Venta realizada con éxito</h2>
        <div class="d-flex justify-content-center align-items-center vh-80">
            <div class="contenedor-login">
                <div class="d-flex justify-content-center align-items-center mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor" class="bi bi-bag-check" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M10.854 8.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708 0"/>
                    <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1m3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1z"/>
                    </svg>
                </div>
                <h2 class="text-center">¡Muchas gracias por tu compra!</h2>
                <h5 class="text-center">Aqui tienes un comporbante de tu compra</h5>
                <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/bill-servlet">
                    <div class="mb-3 row">
                        <label for="id" class="form-label">ID de compra</label>
                        <input type="text" class="form-control" id="nit" name="id" value="${id}" required>
                    </div>
                    <button class="btn btn-primary w-100">
                        <i class="bi bi-search me-1"></i></i>Continuar</button>
                </form>
            </div>
        </div>
    </body>
</html>
