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
        <title>Editar Computadora</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>

    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <h2 class="text-center my-4">Editar Computadora</h2>
        <div class="contenedor">
            <form method="POST" action="${pageContext.servletContext.contextPath}/controllers/edit-computer-servlet">
                <input type="text" id="originalName" name="originalName" class="form-control" required value="${computer.name}" hidden="true">
                <div class="mb-3">
                    <label for="computerName" class="form-label">Nombre de la computadora</label>
                    <input type="text" id="computerName" name="computerName" class="form-control" required value="${computer.name}">
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Precio</label>
                    <input type="text" id="price" name="price" class="form-control" required placeholder="Ingresa un precio" value="${computer.price}">
                </div>
                <div class="d-flex row">
                    <div class="mb-3 col">
                        <label for="cost" class="form-label">Costo Total</label>
                        <input type="number" id="cost" name="cost" class="form-control form-control-sm" disabled="true" value="${computer.value}">
                    </div>
                    <div class="mb-3 col">
                        <label for="stock" class="form-label">Cantidad en stock</label>
                        <input type="number" id="stock" name="stock" class="form-control form-control-sm" disabled="true" value="${computer.amount}">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </form>
        </div>
        <div class="container mb-4">

            <h2 class="text-center my-4">Editar Componentes</h2>
            <div class="row row-cols-1 row-cols-md-4 g-4">
                <c:forEach items="${assignments}" var="assignment" >
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title" style="color: white">${assignment.componentName}
                                    <span class="badge bg-primary rounded-pill mx-2">${assignment.amount}</span>
                                </h5>
                                <div class="d-flex">
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/edit-asign-loader">
                                        <input value="${assignment.componentName}" name="componentName" hidden="true" />
                                        <input value="${assignment.computerName}" name="computerName" hidden="true" />
                                        <button class="btn btn-info" type="submit">Editar</button>
                                    </form>
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/assignment-servlet">
                                        <input value="${assignment.componentName}" name="componentName" hidden="true" />
                                        <input value="${assignment.computerName}" name="computerName" hidden="true" />
                                        <button class="btn btn-danger mx-2">Eliminar</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <form class="col" method="GET" action="${pageContext.servletContext.contextPath}/controllers/new-assign-loader" >
                    <input value="${computer.name}" name="computerName" hidden="true" />
                    <div class="card" style="height: 100%">
                        <button style="width: 100%; height: 100%; color: #FF3B30" class="bg-transparent border-0"><h1><i class="bi bi-plus-circle"></i></h1></button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
