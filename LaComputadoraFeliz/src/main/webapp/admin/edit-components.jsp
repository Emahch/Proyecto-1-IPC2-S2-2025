<%-- 
    Document   : new-user
    Created on : Mar 4, 2025, 9:55:24 AM
    Author     : joca
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="container mb-4">
    <h2 class="text-center my-4">Editar Componentes</h2>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Tarjeta 1
                        <span class="badge bg-primary rounded-pill mx-2">14</span>
                    </h5>
                    <p class="card-text">Descripción de la tarjeta 1.</p>
                    <a class="btn btn-info" href="/LaComputadoraFeliz/admin/edit-component.jsp">Editar</a>
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
