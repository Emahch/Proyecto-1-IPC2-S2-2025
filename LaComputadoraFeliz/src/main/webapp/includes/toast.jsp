<%-- 
    Document   : toast
    Created on : Mar 8, 2025, 6:16:45 PM
    Author     : joca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let toastElement = document.getElementById('toastMessage');
        if (toastElement && toastElement.style.display !== "none") {
            let toast = new bootstrap.Toast(toastElement);
            toast.show();
        }
    });
</script>
<div class="toast-container position-absolute p-3 top-10 end-0">
    <%  String message = (String) request.getAttribute("message");
        Object success = request.getAttribute("success");%>
    <div id="toastMessage" class="toast align-items-center <%if (success != null) {%>text-bg-success"<%} else {%>text-bg-danger"<%}%>role="alert" aria-live="polite" aria-atomic="true"
         <% if (message == null) { %> style="display: none;" <% }%>>
        <div class="d-flex">
            <div class="toast-body">
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
</div>
