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
                 <%  String message = (String) request.getAttribute("message");
                     Object success = request.getAttribute("success");%>
            <div id="toastMessage" class="toast align-items-center <%if (success != null) {%>text-bg-success<%}else {%>text-bg-danger"<%}%>role="alert" aria-live="polite" aria-atomic="true"
               <% if (message == null && success == null) { %> style="display: none;" <% }%>>
                <div class="d-flex">
                    <div class="toast-body">
                        ${message}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </div>
        <h2 class="text-center my-4">Nuevo usuario</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/users-servlet">
                <div class="mb-3">
                    <label for="name" class="form-label">Nombre</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Nombre de usuario</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="rol" class="form-label">Rol</label>
                    <select id="rol" name="rol" class="form-select">
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
