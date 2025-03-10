<%-- 
    Document   : new-user
    Created on : Mar 4, 2025, 9:55:24 AM
    Author     : joca
--%>
<%@page import="com.joca.lacomputadorafeliz.model.computers.Computer"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${computer.name}</title>
        <jsp:include page="/includes/style-atributes.jsp"/>
        <jsp:include page="/includes/navbar.jsp"/>

    </head>
    <body>
        <jsp:include page="/includes/toast.jsp" />
        <c:if test="${sessionScope.usuario.userRol.id == 3}">
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
        </c:if>
        <div class="container mb-4">

            <h2 class="text-center my-4">Componentes Asignados</h2>
            <div class="row row-cols-1 row-cols-md-4 g-4">
                <c:forEach items="${assignments}" var="assignment" >
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title" style="color: white">${assignment.componentName}
                                    <span class="badge bg-primary rounded-pill mx-2">${assignment.amount}</span>
                                </h5>
                                    <c:if test="${assignment.stock <= 3}" >
                                        <span class="badge bg-danger rounded-pill mb-2">Stock: ${assignment.stock}</span>
                                    </c:if>
                                    <c:if test="${assignment.stock > 3}" >
                                        <span class="badge bg-primary rounded-pill mb-2">Stock: ${assignment.stock}</span>
                                    </c:if>
                                <div class="d-flex">
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/edit-asign-loader">
                                        <input value="${assignment.componentName}" name="componentName" hidden="true" />
                                        <input value="${assignment.computerName}" name="computerName" hidden="true" />
                                        <!-- boton editar -->
                                        <button class="btn btn-info" type="submit"><i class="bi bi-pencil"></i></button>
                                    </form>
                                    <form method="GET" action="${pageContext.servletContext.contextPath}/controllers/assignment-servlet">
                                        <input value="${assignment.componentName}" name="componentName" hidden="true" />
                                        <input value="${assignment.computerName}" name="computerName" hidden="true" />
                                        <!-- boton eliminar -->
                                        <button class="btn btn-danger mx-2"><i class="bi bi-trash"></i></button>
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
        <% Computer computer = (Computer) request.getAttribute("computer");
        %>
        <div class="contenedor mb-5">
            <div class="mb-3">
                <h2 class="text-center">Información de la computadora</h2>
            </div>
            <table class="table table-dark table-striped mt-4">
                <tr>
                    <th>Nombre</th>
                    <td>${computer.name}</td>
                </tr>
                <tr>
                    <th>Cantidad en Stock</th>
                    <td style="color: <%= (computer.getAmount() <= 3) ? "#d9534f" : "white"%>;"
                        >${computer.amount}</td>
                </tr>
                <tr>
                    <th>Precio de Venta</th>
                    <td>Q${computer.price}</td>
                </tr>
                <tr>
                    <th>Costo Total de Armado</th>
                    <td>Q${computer.value}</td>
                </tr>
                <tr>
                    <th>Ganancia</th>
                    <td style="color: <%= (computer.getPrice() - computer.getValue() <= 0) ? "#d9534f" : "#5cb85c"%>">
                        Q${computer.price-computer.value}
                    </td>
                </tr>
            </table>
            <form class="d-flex justify-content-center" method="POST" action="${pageContext.servletContext.contextPath}/controllers/assembly-servlet">
                <input type="text" id="computerName" name="computerName" class="form-control" required value="${computer.name}" hidden="true">
                <button type="submit" class="btn btn-info" style="width: 50%"><i class="bi bi-hammer me-2"></i>Ensamblar</button>
            </form>
        </div>
    </body>
</html>
