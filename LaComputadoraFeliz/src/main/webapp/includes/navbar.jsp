<%-- 
    Document   : navbar
    Created on : Mar 3, 2025, 11:59:24 AM
    Author     : joca
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand" href="/LaComputadoraFeliz/Home.jsp">
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height:"25" class="logo me-2" viewBox="0 0 1200 1200">
            <path fill="currentColor" d="M0 0v1200h1200V0zm392.285 228.223c71.542 0 129.564 57.95 129.564 129.492S463.827 487.28 392.285 487.28S262.72 429.257 262.72 357.715s58.023-129.492 129.565-129.492m403.784 0c71.542 0 129.491 57.95 129.491 129.492S867.611 487.28 796.069 487.28s-129.565-58.023-129.565-129.565s58.023-129.492 129.565-129.492M183.032 636.108h833.936c0 230.285-186.682 417.04-416.968 417.04s-416.968-186.753-416.968-417.04"/>
            </svg>
            La Computadora Feliz</a>
        <h5 class="text-center mt-2 ms-1" style="color: #888888">${sessionScope.usuario.userRol.name}</h5>
        <c:if test="${sessionScope.usuario.userRol.id == 3}">
            <div class="navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.servletContext.contextPath}/admin/home.jsp">Finanzas</a></li>
                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/users-servlet">
                        <li class="nav-item"><button class="nav-link">Usuarios</button></li>
                    </form>
                    <li class="nav-item">
                        <button class="nav-link" href="#">Computadoras</button>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="#">Cargar</a></li>
                </ul>
            </div>
        </c:if>
    </div>
</nav>
