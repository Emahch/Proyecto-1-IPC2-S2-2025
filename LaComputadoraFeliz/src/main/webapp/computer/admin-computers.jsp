<%-- 
    Document   : admin-computers
    Created on : Mar 8, 2025, 10:53:47 PM
    Author     : joca
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Computadoras</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <div class="container">
            <h2 class="text-center my-4">Computadoras</h2>
            <form action="${pageContext.servletContext.contextPath}/controllers/computers-loader" method="${ascendent ? 'POST' : 'GET'}" class="mb-4">
                <div class="d-flex justify-content-end">
                    <button type="submit" class="btn btn-primary">
                        Ordenadas por cantidad: ${ascendent ? '▲ Ascendente' : '▼ Descendente'}
                    </button>
                </div>
            </form>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${computers}" var="computer" >

                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title" style="color: white">${computer.name}
                                    <c:if test="${computer.amount <= 3}" >
                                        <span class="badge bg-danger rounded-pill mx-2">${computer.amount}</span>
                                    </c:if>
                                    <c:if test="${computer.amount > 3}" >
                                        <span class="badge bg-primary rounded-pill mx-2">${computer.amount}</span>
                                    </c:if>
                                </h5
                                <p class="card-text">Precio de venta: Q ${computer.price}<br>
                                    Costo total: Q ${computer.value}</p>
                                <div class="d-flex">
                                    <c:if test="${sessionScope.usuario.userRol.id == 3}">
                                        <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/computer-loader">
                                            <input value="${computer.name}" name="computerName" hidden="true" />
                                            <!-- boton editar -->
                                            <button class="btn btn-info" type="submit"><i class="bi bi-pencil me-2"></i>Editar</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${sessionScope.usuario.userRol.id == 1}">
                                        <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/computer-loader">
                                            <input value="${computer.name}" name="computerName" hidden="true" />
                                            <button class="btn btn-info"><i class="bi bi-hammer me-2"></i>Ensamblar</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${sessionScope.usuario.userRol.id == 3}">
                                        <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/computer-servlet">
                                            <input value="${computer.name}" name="computerName" hidden="true" />
                                            <!-- boton eliminar -->
                                            <button class="btn btn-danger mx-2"><i class="bi bi-trash"></i></button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${sessionScope.usuario.userRol.id == 3}">
                    <div class="col">
                        <div class="card" style="height: 100%">
                            <a style="width: 100%; height: 100%; color: #FF3B30" class="bg-transparent border-0 text-center" href="/LaComputadoraFeliz/computer/new-computer.jsp">
                                <h1 class="my-auto d-flex justify-content-center align-items-center" style="height: 100%"><i class="bi bi-plus-circle align middle"></i></h1></a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>
