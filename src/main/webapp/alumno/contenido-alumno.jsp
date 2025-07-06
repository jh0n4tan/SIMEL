<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.List" %>


<c:choose>

    <%-------------------------------------------------------------- Sesion 1 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'misCursos'}">
        <div class="container mt-5">
            <section class="panel-alumno panel-cursos animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-alumno text-primary d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Mis asignaturas actuales</h5>
                        <i class="fas fa-book-reader"></i>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle tabla-cursos">
                                <thead class="encabezado-primaria">
                                    <tr>
                                        <th>Código</th>
                                        <th>Nombre del Curso</th>
                                        <th>Docente</th>
                                        <th>Grado</th>
                                        <th>Sección</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>    
                                <tbody>
                                    <c:forEach var="curso" items="${cursosAlumno}">
                                        <tr>
                                            <td>C${curso.idCurso}</td>
                                            <td>
                                                <%-- Solo hace el enlace si el nombre del curso no está vacío o no es uno de los cursos válidos --%>
                                                <c:choose>
                                                    <c:when test="${curso.nombreCurso == 'Matemáticas' || curso.nombreCurso == 'Comunicación' || curso.nombreCurso == 'Ciencias' || curso.nombreCurso == 'Historia' || curso.nombreCurso == 'Arte'}">
                                                        <a href="javascript:void(0);" 
                                                           class="link-curso" 
                                                           onclick="verDetalleCurso('${curso.nombreCurso}')">
                                                            ${curso.nombreCurso}
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${curso.nombreCurso}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${curso.nombreDocente}</td>
                                            <td>${curso.grado}°</td>
                                            <td>${curso.seccion}</td>
                                            <td><span class="estado-curso activo">🟢 Activo</span></td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty cursosAlumno}">
                                        <tr>
                                            <td colspan="6" class="text-center">No se encontraron cursos.</td>
                                        </tr>
                                    </c:if>
                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </c:when>




    <%-------------------------------------------------------------- Sesion 2 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'misNotas'}">
        <div class="container mt-5">
            <section class="panel-notas animate__animated animate__fadeIn">

                <div class="card shadow-sm">
                    <div class="card-header bg-alumno text-primary d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Mis notas académicas</h5>
                        <i class="fas fa-file-alt"></i>
                    </div>
                    <div class="card-body">
                        <div class="mb-4">
                            <form method="get" id="formCursoNota">
                                <label for="selectCursoNota" class="form-label fw-semibold">Selecciona un curso:</label>
                                <select id="selectCursoNota" name="idCurso" class="form-select select-notas-curso">
                                    <option selected disabled>Elige un curso</option>
                                    <c:forEach var="curso" items="${cursosParaNotas}">
                                        <option value="${curso.idCurso}-${curso.idGradoSeccion}">${curso.nombreCurso}</option>
                                    </c:forEach>
                                </select>

                            </form>

                        </div>
                        <div id="contenidoNotas" style="display: none;">

                            <div class="table-responsive">
                                <table class="table table-bordered text-center align-middle tabla-notas">
                                    <thead class="encabezado-notas">
                                        <tr>
                                            <th>Evaluación</th>
                                            <th>Nota</th>
                                            <th>Comentario</th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-center" id="tablaNotas">

                                    </tbody>
                                </table>
                            </div>
                            <div class="promedio-general mt-4 text-center">
                                <h4>Promedio General: <span id="promedio">--</span></h4>
                            </div>
                            <div class="grafico-evolucion mt-5">
                                <canvas id="graficoNotas"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </c:when>


    <%-------------------------------------------------------------- Sesion 3 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'logros'}">
        <div class="container mt-5">
            <section class="panel-logros animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-alumno text-primary d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Mis logros y nivel</h5>
                        <i class="fas fa-trophy me-2"></i>
                    </div>
                    <div class="card-body">

                        <div class="nivel-logros text-center mb-4">
                            <h4 class="fw-bold">Nivel ${nivelInfo.nivel}: ${nivelInfo.nombreNivel}</h4>
                            <div class="progress custom-progress" style="height: 20px;">
                                <c:choose>
                                    <c:when test="${not empty nivelInfo}">
                                        <div class="progress-bar ${nivelInfo.colorBarra}"
                                             style="width: ${nivelInfo.porcentajeAvance}%; transition: width 1s ease-in-out;">
                                            ${nivelInfo.puntosActuales} / ${nivelInfo.puntosMaximos} pts
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="progress-bar bg-secondary" style="width: 0%;">
                                            0 / 100 pts
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                        </div>
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                            <c:forEach var="logro" items="${listaLogros}">
                                <div class="col">
                                    <div class="card logro-card shadow-sm h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">🏅 ${logro.nombreLogro}</h5>
                                            <p class="card-text"><strong>Curso:</strong> ${logro.nombreCurso}</p>
                                            <p class="card-text"><strong>Fecha:</strong> ${logro.fechaSoloFormato}</p>                                            
                                            <p class="card-text text-primary"><strong>+${logro.puntos} puntos</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <c:if test="${empty listaLogros}">
                                <div class="col-12 text-center">
                                    <p>No hay logros disponibles por ahora.</p>
                                </div>
                            </c:if>
                        </div>

                    </div>
            </section>
        </div>
    </c:when>




    <%-------------------------------------------------------------- Sesion 4 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'canjes'}">
        <div class="container mt-5">
            <section class="panel-canjes animate__animated animate__fadeIn">
                <div class="card shadow-sm">

                    <div class="card-header bg-alumno text-primary d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Mis premios y canjes</h5>
                        <i class="fas fa-gift"></i>
                    </div>
                    <div class="card-body">
                        <div class="filtros-canjes d-flex justify-content-end mb-4">
                            <select id="filtroPuntos" class="form-select select-canjes-filtro w-auto">
                                <option value="todos">Todos los premios</option>
                                <option value="disponibles">Premios que puedo canjear</option>
                            </select>
                        </div>
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4" id="catalogoPremios">

                            <c:forEach var="premio" items="${premios}">
                                <div class="col">
                                    <div class="card premio-card h-100 shadow-sm">
                                        <img src="${pageContext.request.contextPath}/mostrarImagen?file=${premio.imagen}" class="card-img-top" alt="${premio.nombre}">
                                        <div class="card-body">
                                            <h5 class="card-title text-dark">${premio.nombre}</h5>
                                            <p class="card-text">${premio.descripcion}</p>
                                            <p class="puntos-requeridos text-primary"><strong>${premio.puntosRequeridos} puntos</strong></p>
                                            <button 
                                                class="btn btn-primary w-100" 
                                                onclick="canjearPremio(${premio.id}, '${premio.nombre}', ${premio.puntosRequeridos}, '${premio.tipo}')">Canjear</button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <h5 class="mt-5 fw-semibold">📝 Canjes realizados</h5>
                        <div id="canjesRealizados" class="list-group">
                            <c:choose>
                                <c:when test="${not empty listaCanjes}">
                                    <c:forEach var="canje" items="${listaCanjes}">
                                        <div class="list-group-item list-group-item-action">
                                            <strong>${canje.nombre}</strong><br>
                                            Código de canje: <code>${canje.codigo}</code><br>
                                            Estado: ${canje.estado}<br>
                                            Tipo: <c:choose>
                                                <c:when test="${canje.tipo == 'digital'}">Premio digital</c:when>
                                                <c:otherwise>Premio físico</c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-info">No se han realizado canjes aún.</div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                    </div>
                </div>
            </section>
        </div>
        <%-- Al final del contenido del canje (por ejemplo, justo antes del cierre del card-body) --%>
        <div id="datosUsuario" data-puntos="${nivelInfo != null ? nivelInfo.puntosActuales : 0}" style="display: none;"></div>
    </c:when>



    <%-------------------------------------------------------------- Sesion 5 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'retos'}">
        <div class="container mt-5">
            <section class="panel-retosalumno animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-alumno text-primary d-flex justify-content-between align-items-center">
                        <h5 class="mb-0 fw-bold">Mis Retos</h5>
                        <i class="fas fa-flag-checkered"></i>
                    </div>
                    <div class="card-body">

                        <!-- Tabs -->
                        <ul class="nav nav-tabs" id="retosTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="disponibles-tab" data-bs-toggle="tab" data-bs-target="#disponibles" type="button" role="tab">Disponibles</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="pendientes-tab" data-bs-toggle="tab" data-bs-target="#pendientes" type="button" role="tab">Pendientes</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="completados-tab" data-bs-toggle="tab" data-bs-target="#completados" type="button" role="tab">Completados</button>
                            </li>
                        </ul>

                        <%-- Contenido de las pestañas --%>
                        <div class="tab-content mt-4">

                            <!-- Retos Disponibles -->
                            <div class="tab-pane fade show active" id="disponibles" role="tabpanel">
                                <div class="row row-cols-1 row-cols-md-2 g-4">
                                    <c:forEach var="reto" items="${retosDisponibles}">
                                        <div class="col">
                                            <div class="card reto-card shadow-sm h-100">
                                                <div class="card-body">
                                                    <h5 class="card-title">${reto.nombre}</h5>
                                                    <p class="card-text">${reto.descripcion}</p>
                                                    <p class="text-warning">🎯 Puntos: <strong>${reto.puntos}</strong></p>
                                                    <button 
                                                        class="btn btn-primary btn-marcar-completado"
                                                        data-id="${reto.id}">
                                                        Marcar como completado
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <c:if test="${empty retosDisponibles}">
                                        <div class="col-12">
                                            <p>No hay retos disponibles para tu grado.</p>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <%-- Retos Pendientes --%>
                            <div class="tab-pane fade" id="pendientes" role="tabpanel">
                                <div class="row row-cols-1 row-cols-md-2 g-4">
                                    <c:forEach var="reto" items="${retosPendientes}">
                                        <div class="col">
                                            <div class="card reto-card shadow-sm h-100 border-warning">
                                                <div class="card-body">
                                                    <h5 class="card-title">${reto.nombre}</h5>
                                                    <p class="card-text">${reto.descripcion}</p>
                                                    <p class="text-warning">⏳ En revisión...</p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${empty retosPendientes}">
                                        <div class="col-12"><p>No tienes retos en revisión actualmente.</p></div>
                                    </c:if>
                                </div>
                            </div>

                            <!-- Retos Completados -->
                            <div class="tab-pane fade" id="completados" role="tabpanel">
                                <div class="row row-cols-1 row-cols-md-2 g-4">
                                    <c:forEach var="reto" items="${retosCompletados}">
                                        <div class="col">
                                            <div class="card reto-card shadow-sm h-100 border-success">
                                                <div class="card-body">
                                                    <h5 class="card-title">${reto.nombre}</h5>
                                                    <p class="card-text">${reto.descripcion}</p>
                                                    <p class="text-success">✅ Completado</p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${empty retosCompletados}">
                                        <div class="col-12"><p>No has completado retos aún.</p></div>
                                    </c:if>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </section>
        </div>
    </c:when>

</c:choose>



