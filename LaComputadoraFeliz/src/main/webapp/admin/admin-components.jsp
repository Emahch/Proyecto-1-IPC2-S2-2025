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
        <title>Componentes</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/toast.jsp"/>
        <div class="container mb-4">

            <h2 class="text-center my-4">Componentes</h2>
            <div class="row row-cols-1 row-cols-md-4 g-4">
                <c:forEach items="${components}" var="component" >
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title" style="color: white">${component.name}
                                    <c:if test="${component.amount <= 3}" >
                                        <span class="badge bg-danger rounded-pill mx-2">${component.amount}</span>
                                    </c:if>
                                    <c:if test="${component.amount > 3}" >
                                        <span class="badge bg-primary rounded-pill mx-2">${component.amount}</span>
                                    </c:if>
                                </h5>
                                <p class="card-text">Costo unitario: Q ${component.value}</p>
                                <div class="d-flex">
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/edit-component-loader">
                                        <input value="${component.name}" name="componentName" hidden="true" />
                                        <button class="btn btn-info" type="submit">Editar</button>
                                    </form>
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/component-servlet">
                                        <input value="${component.name}" name="componentName" hidden="true" />
                                        <button class="btn btn-danger mx-2">Eliminar</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="col">
                    <div class="card" style="height: 100%">
                        <a style="width: 100%; height: 100%; color: #FF3B30" class="bg-transparent border-0 text-center" href="/LaComputadoraFeliz/admin/new-component.jsp">
                            <h1 class="my-auto d-flex justify-content-center align-items-center" style="height: 100%"><i class="bi bi-plus-circle align middle"></i></h1></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
