<%-- 
    Document   : style-atributes
    Created on : Feb 27, 2025, 11:08:26 PM
    Author     : joca
    Palette Color: 0A192F, EAEAEA, FF3B30, 2E2E2E
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="${pageContext.servletContext.contextPath}/Resources/js/jquery-3.7.1.js"></script>
<script src="${pageContext.servletContext.contextPath}/Resources/js/bootstrap.bundle.js"></script>
<link href="${pageContext.servletContext.contextPath}/Resources/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
    body {
        <c:if test="${sessionScope.usuario.userRol.id == null}">
        background-color: #B71C1C;
        </c:if>
        <c:if test="${sessionScope.usuario.userRol.id == 1}">
        background-color: #33691E;
        </c:if>
        <c:if test="${sessionScope.usuario.userRol.id == 2}">
        background-color: #EF6C00;
        </c:if>
        <c:if test="${sessionScope.usuario.userRol.id == 3}">
        background-color: #0A192F;
        </c:if>
        color: #EAEAEA;
        font-family: "Courrier New";
    }
    h2 {
        color: #EAEAEA;
    }
    .contenedor-login {
        background-color: #2E2E2E;
        border-radius: 10px;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        padding: 3rem;
        width: 100%;
        max-width: 500px;
    }
    .contenedor {
        background-color: #2E2E2E;
        border-radius: 10px;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        padding: 3rem;
        width: auto;
        margin-left: 3rem;
        margin-right: 3rem;
    }
    .btn-primary {
        background-color: #FF3B30;
        color: #EAEAEA;
        border: none;
    }
    .btn-primary:hover {
        background-color: #D32F2F;
    }
    .navbar {
        background-color: #2E2E2E;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    }
    .navbar-brand, .nav-link {
        color: #EAEAEA !important;
    }
    .nav-link:hover {
        color: #FF3B30 !important;
    }
    .logo {
        margin-top: -0.25rem !important;
    }
    .card {
        background-color: #2E2E2E;
        color: #C0C0C0;
        border: none;
        transition: transform 0.3s ease;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    }
    .card:hover {
        transform: scale(1.05);
    }
</style>