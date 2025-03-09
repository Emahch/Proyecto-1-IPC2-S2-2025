<%-- 
    Document   : admin-computers
    Created on : Mar 8, 2025, 10:53:47 PM
    Author     : joca
--%>

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
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Tarjeta 1
                            <span class="badge bg-primary rounded-pill mx-2">14</span>
                            </h5
                            <p class="card-text">Descripción de la tarjeta 1.</p>
                            <a href="/LaComputadoraFeliz/admin/edit-computer.jsp" class="btn btn-info">Editar</a>
                            <button class="btn btn-primary mx-2">Eliminar</button>
                        </div>
                    </div>
                </div>
                <form class="col">
                    <div class="card" style="height: 100%">
                        <button style="width: 100%; height: 100%; color: #FF3B30" class="bg-transparent border-0"><h1><i class="bi bi-plus-circle"></i></h1></button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
