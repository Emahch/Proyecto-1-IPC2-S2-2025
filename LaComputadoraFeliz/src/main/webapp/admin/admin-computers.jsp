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
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/computer-loader">
                                        <input value="${computer.name}" name="computerName" hidden="true" />
                                        <button class="btn btn-info">Editar</button>
                                    </form>
                                        <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/computer-servlet">
                                        <input value="${computer.name}" name="computerName" hidden="true" />
                                        <button class="btn btn-danger mx-2">Eliminar</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="col">
                    <div class="card" style="height: 100%">
                        <a style="width: 100%; height: 100%; color: #FF3B30" class="bg-transparent border-0 text-center" href="/LaComputadoraFeliz/admin/new-computer.jsp">
                            <h1 class="my-auto d-flex justify-content-center align-items-center" style="height: 100%"><i class="bi bi-plus-circle align middle"></i></h1></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
