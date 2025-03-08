    <%-- 
    Document   : update-password
    Created on : Mar 7, 2025, 9:30:35 PM
    Author     : joca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Contraseña</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
    </head>
    <body>
        <div class="d-flex justify-content-center align-items-center vh-100">
            <div class="contenedor-login">
                <h2 class="text-center">Actualizar Contraseña</h2>

                <h4 class="text-center"><b>Bienvenido ${usuario}</b></h4>
                <h6 class="text-center" style="color: #6c757d">Actualiza tu contraseña para ingresar</h6>
                <p>Ingresa una contraseña mayor a 6 caracteres y menor a 50 caracteres.
                </p>
                <h6 class="text-center" style="color:red">${error}</h6>
                <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/password-servlet">
                    <div class="mb-3">
                        <input type="text" class="form-control" id="username" name="usuario" hidden="true" value="${usuario}">
                        <label for="password" class="form-label">Nueva Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Ingresa tu nueva contraseña" required>
                    </div>
                    <button class="btn btn-primary w-100">
                        <i class="bi bi-box-arrow-in-right me-1"></i>Actualizar Contraseña</button>
                </form>
            </div>
        </div>
    </body>
</html>
