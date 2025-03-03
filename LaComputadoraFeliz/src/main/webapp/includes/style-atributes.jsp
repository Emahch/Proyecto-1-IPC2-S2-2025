<%-- 
    Document   : style-atributes
    Created on : Feb 27, 2025, 11:08:26 PM
    Author     : joca
    Palette Color: 0A192F, EAEAEA, FF3B30, 2E2E2E
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="${pageContext.servletContext.contextPath}/Resources/js/jquery-3.7.1.js"></script>
<script src="${pageContext.servletContext.contextPath}/Resources/js/bootstrap.bundle.js"></script>
<link href="${pageContext.servletContext.contextPath}/Resources/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
    body {
        background-color: #0A192F;
        color: #EAEAEA;
        font-family: "Courrier New";
    }
    h2 {
        color: #EAEAEA;
    }
    .contenedor {
        background-color: #2E2E2E;
        border-radius: 10px;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        padding: 3rem;
        width: 100%;
        max-width: 500px;
    }
    .btn-primary {
        background-color: #FF3B30;
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
</style>