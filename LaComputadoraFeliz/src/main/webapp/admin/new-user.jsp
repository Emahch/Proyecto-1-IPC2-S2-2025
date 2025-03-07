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
        <title>Nuevo Usuario</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let toastElement = document.getElementById('toastMessage');
                if (toastElement && toastElement.style.display !== "none") {
                    let toast = new bootstrap.Toast(toastElement);
                    toast.show();
                }
            });
        </script>
    </head>
    <body>
        <div class="toast-container position-absolute p-3 top-10 end-0">
            <div id="toastMessage" class="toast align-items-center text-bg-danger" role="alert" aria-live="polite" aria-atomic="true"
                 <%  String error = (String) request.getAttribute("error");
                if (error == null) { %> style="display: none;" <% }%>>
                <div class="d-flex">
                    <div class="toast-body">
                        ${error}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </div>
        <h2 class="text-center my-4">Nuevo usuario</h2>
        <div class="contenedor">
            <form class="form">
                <div class="mb-3">
                    <label class="form-label">Nombre</label>
                    <input type="text" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Nombre de usuario</label>
                    <input type="text" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Contraseña</label>
                    <input type="password" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Rol</label>
                    <select class="form-select">
                        <option selected>Selecciona un rol</option>
                        <c:forEach items="${roles}" var="rol">
                            <option value="${rol.id}">${rol.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Crear Usuario</button>
            </form>
        </div>
    </body>
</html>
