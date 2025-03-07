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
        <title>Bienvenido</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
    </head>
    <body>
        <div class="d-flex justify-content-center align-items-center vh-100">
            <div class="contenedor-login">
                <div class="d-flex justify-content-center align-items-center mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" width="100" height:"100" class="logo me-2" viewBox="0 0 1200 1200">
                <path fill="currentColor" d="M0 0v1200h1200V0zm392.285 228.223c71.542 0 129.564 57.95 129.564 129.492S463.827 487.28 392.285 487.28S262.72 429.257 262.72 357.715s58.023-129.492 129.565-129.492m403.784 0c71.542 0 129.491 57.95 129.491 129.492S867.611 487.28 796.069 487.28s-129.565-58.023-129.565-129.565s58.023-129.492 129.565-129.492M183.032 636.108h833.936c0 230.285-186.682 417.04-416.968 417.04s-416.968-186.753-416.968-417.04"/>
                </svg>
                </div>
                <h2 class="text-center">Iniciar Sesión</h2>
                <h6 class="text-center" style="color:red">${error}</h6>
                <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/authenticator-servlet">
                    <div class="mb-3">
                        <label for="username" class="form-label">Usuario</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Ingresa tu usuario" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Ingresa tu contraseña" required>
                    </div>
                    <button class="btn btn-primary w-100">
                        <i class="bi bi-box-arrow-in-right me-1"></i>Continuar</button>
                </form>
            </div>
        </div>
    </body>
</html>
