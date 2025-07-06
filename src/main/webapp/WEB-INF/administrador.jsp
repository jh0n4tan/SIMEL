<%@ include file="../Panel/head.jsp" %>
<body id="page-top">

    <div id="wrapper">
        <%@ include file="../administrador/secciones-administrador.jsp" %>

        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <%@ include file="../Panel/topbar.jsp" %>

                <!-- Contenido específico del docente -->
                <%@ include file="../administrador/contenido-administrador.jsp" %>
            </div>

            <%@ include file="../Panel/footer.jsp" %>
        </div>

        <%@ include file="../Panel/logoutModal.jsp" %>

    </div>

    <!--------------------------------------------------- CÓDIGOS JAVASCRIP ----------------------------------------------------------------->   




    <!-- Bootstrap core JavaScript-->
    <script src="js/jquery.min.js" type="text/javascript"></script>

    <script src="js/bootstrap.bundle.min.js" type="text/javascript"></script>
    <!-- Core plugin JavaScript-->

    <script src="js/jquery.easing.min.js" type="text/javascript"></script>
    <!-- Custom scripts for all pages-->

    <script src="js/sb-admin-2.min.js" type="text/javascript"></script>

    <!-- datatables JS -->
    <script src="js/datatables.min.js" type="text/javascript"></script>

    <!-- Código para notifiaciones -->
    <script src="js/notificaciones.js" type="text/javascript"></script>

    <script src="js/main.js" type="text/javascript"></script>
    
    
    <script src="js/Topbar.js" type="text/javascript"></script>

    <!-------------------------------------------------Panel Administrador -------------------------------------------------------------->

    <!-- Sección Cursos y asignaciones -->
    <script src="js/js_administrador/cursosAsignaciones.js" type="text/javascript"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://cdn.jsdelivr.net/npm/choices.js/public/assets/scripts/choices.min.js"></script>

    <!-- Sección Logros y premios -->
    <script src="js/js_administrador/logrosPremios.js" type="text/javascript"></script>

    <!-- Sección Canjes -->
    <script src="js/js_administrador/canjes.js" type="text/javascript"></script>

    <!-- Sección Reportes -->
    <script src="js/js_administrador/reporte.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.25/jspdf.plugin.autotable.min.js"></script>

    <!-- Sección Configuración -->

    <script src="js/js_administrador/ultimaSeccion.js" type="text/javascript"></script>

    <!--Filtro de tiempos en espera-->
    <script src="${pageContext.request.contextPath}/js/session-expiry.js"></script>

</body>
</html>
