<%@ include file="../Panel/head.jsp" %>
<body id="page-top">

    <div id="wrapper">
        <%@ include file="../alumno/secciones-alumno.jsp" %>

        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <%@ include file="../Panel/topbar.jsp" %>

                <!-- Contenido específico del docente -->
                <%@ include file="../alumno/contenido-alumno.jsp" %>
            </div>

            <%@ include file="../Panel/footer.jsp" %>
        </div>

        <%@ include file="../Panel/logoutModal.jsp" %>

    </div> 
    <!-- Bootstrap core JavaScript-->
    <script src="js/jquery.min.js" type="text/javascript"></script>

    <script src="js/bootstrap.bundle.min.js" type="text/javascript"></script>
    <!-- Core plugin JavaScript-->

    <script src="js/jquery.easing.min.js" type="text/javascript"></script>
    <!-- Custom scripts for all pages-->

    <script src="js/sb-admin-2.min.js" type="text/javascript"></script>

    <!-- datatables JS -->
    <script src="js/datatables.min.js" type="text/javascript"></script>
    <!-- código propio JS --> 
    <script src="js/main.js" type="text/javascript"></script>
    
    <script src="js/Topbar.js" type="text/javascript"></script>


    <!-- Librería para alertas -->

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- Librería para el gráfico -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


    <!-- Bootstrap JS (versión 5.x) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Código para notifiaciones -->
    <script src="js/notificaciones.js" type="text/javascript"></script>

    <!------------------------------------------------ Panel Alumno ---------------------------------------------------------------------->

    <!-- Sección Mis cursos -->
    <script src="js/js_alumno/misCursos.js" type="text/javascript"></script>

    <!-- Sección Mis Notas -->
    <script src="js/js_alumno/misNotas.js" type="text/javascript"></script>

    <!-- Sección Logros -->
    <script src="js/js_alumno/logros.js" type="text/javascript"></script>

    <!-- Sección Canjes -->
    <script src="js/js_alumno/canjes.js" type="text/javascript"></script>

    <!-- Sección Retos -->    
    <script src="js/js_alumno/retos.js" type="text/javascript"></script>
    
    <!--Filtro de tiempos en espera-->
   <script src="${pageContext.request.contextPath}/js/session-expiry.js"></script>
</body>
</html>
