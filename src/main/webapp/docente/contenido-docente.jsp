<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>


<c:choose>


    <%-------------------------------------------------------------- Sesion 1 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'misCursos'}">
        <div class="container mt-5">
            <section class="panel-docente panel-cursos animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-docente text-info d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Vista general de asignaturas</h5>
                        <i class="fas fa-chalkboard-teacher"></i>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle tabla-cursos">
                                <thead class="encabezado-tabla-cursos">
                                    <tr>
                                        <th>Código</th>
                                        <th>Nombre del Curso</th>
                                        <th>Grado</th>
                                        <th>Sección</th>
                                        <th>Total de Alumnos</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="curso" items="${cursosAsignados}">
                                        <tr>
                                            <td>C${curso.idCurso}</td>
                                            <td>${curso.nombreCurso}</td>
                                            <td>${curso.grado}°</td>
                                            <td>${curso.seccion}</td>
                                            <td>${curso.totalAlumnos}</td>
                                            <td>
                                                <button class="btn btn-sm btn-outline-info btn-ver-alumnos"
                                                        data-curso="${curso.nombreCurso}"
                                                        data-grado="${curso.grado}°"
                                                        data-seccion="${curso.seccion}">
                                                    <i class="fas fa-users"></i> Ver Alumnos
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </c:when>


    <%-------------------------------------------------------------- Sesion 2 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'ingresarNotas'}">
        <div class="container mt-5">
            <section class="panel-docente panel-notas animate__animated animate__fadeIn">
                <div class="card shadow-sm">     
                    <div class="card-header bg-docente text-info d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Registro de Evaluaciones Académicas</h5>
                        <i class="fas fa-clipboard-list"></i>
                    </div>    
                    <div class="card-body">
                        <div class="mb-4">
                            <label for="selectCurso" class="form-label">Selecciona una materia y sección:</label>
                            <select id="selectCurso" class="form-control select-curso-notas">
                                <option disabled selected>-- Selecciona --</option>
                                <c:forEach var="curso" items="${cursosAsignados}">
                                    <option value="${curso.idCurso}-${curso.grado}-${curso.seccion}">
                                        ${curso.nombreCurso} - ${curso.grado}°${curso.seccion}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div id="tablaNotasContainer" style="display: none;">
                            <div class="table-responsive">
                                <table class="table table-bordered text-center align-middle tabla-notas">
                                    <thead class="encabezado-tabla-notas">
                                        <tr>
                                            <th>Alumno</th>
                                            <th>Eval. 1</th>
                                            <th>Eval. 2</th>
                                            <th>Eval. 3</th>
                                            <th>Promedio</th>
                                            <th>Comentario</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablaNotasBody">

                                    </tbody>
                                </table>
                            </div>

                            <div class="text-end mt-3">
                                <button class="btn btn-info" onclick="guardarNotas()">💾 Guardar Notas</button>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </div>
    </c:when>



    <%-------------------------------------------------------------- Sesion 3 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'asignarLogros'}">
        <div class="container mt-5">
            <section class="panel-logros animate__animated animate__fadeIn">
                <div class="card shadow-sm">

                    <div class="card-header bg-docente text-info d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Asignación de logros académicos</h5>
                        <i class="fas fa-award"></i>
                    </div>

                    <div class="card-body">
                        <form id="formLogro" onsubmit="return asignarLogro(event)">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="selectCursoLogros" class="form-label label-curso-logros">Curso:</label>
                                    <select id="selectCursoLogros" name="idCurso" class="form-select select-curso-logros" required>
                                        <option value="" selected disabled>-- Selecciona un curso --</option>
                                        <c:forEach var="curso" items="${cursosAsignados}">
                                            <option value="${curso.idCurso}" 
                                                    data-idgradoseccion="${curso.idGradoSeccion}" 
                                                    data-grado="${curso.grado}" 
                                                    data-seccion="${curso.seccion}">
                                                ${curso.nombreCurso} - ${curso.grado}°${curso.seccion}
                                            </option>

                                        </c:forEach>
                                    </select>
                                    <!-- Hidden para enviar grado y sección concatenados -->
                                    <input type="hidden" id="idGradoSeccion" name="idGradoSeccion" value="">
                                </div>
                                <div class="col-md-6">
                                    <label for="selectAlumno" class="form-label label-alumno-logros">Alumno:</label>
                                    <select id="selectAlumno" name="idAlumno" class="form-select select-alumno-logros" disabled required>
                                        <option value="" selected disabled>-- Selecciona un alumno --</option>
                                        <!-- Los alumnos se cargarán dinámicamente con JS -->
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label for="selectTipoLogro" class="form-label label-tipo-logro">Tipo de logro:</label>
                                    <select id="selectTipoLogro" name="idLogro" class="form-select select-tipo-logro" required>
                                        <option value="" selected disabled>-- Selecciona un logro --</option>
                                        <c:forEach var="logro" items="${logros}">
                                            <option value="${logro.id}">${logro.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label for="puntos" class="form-label label-puntos-logro">Puntos:</label>
                                    <input type="number" id="puntos" name="puntos" class="form-control input-puntos-logro" min="1" max="100" placeholder="Ej. 10" required>
                                </div>
                                <div class="col-md-8">
                                    <label for="comentario" class="form-label label-comentario-logro">Comentario:</label>
                                    <textarea id="comentario" name="comentario" class="form-control textarea-comentario-logro" rows="1" placeholder="Comentario opcional"></textarea>
                                </div>
                            </div>
                            <div class="text-end">
                                <button type="submit" class="btn bg-info text-white btn-asignar-logro">
                                    <i class="fas fa-plus-circle"></i> Asignar logro 
                                </button>
                            </div>
                        </form>

                        <hr class="my-4">

                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h5 class="mb-0">📜 Historial de Logros</h5>
                            <input type="text" id="filtroHistorial" class="form-control w-25 filtro-historial-logros" placeholder="Filtrar por curso o fecha">
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle tabla-historial-logros" id="tablaHistorial">
                                <thead class="encabezado-tabla-logros">
                                    <tr>
                                        <th>id</th>
                                        <th>Alumno</th>
                                        <th>Curso</th>
                                        <th>Grado</th>
                                        <th>Logro</th>
                                        <th>Puntos</th>
                                        <th>Comentario</th>
                                        <th>Fecha</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyHistorialLogros">
                                    <c:choose>
                                        <c:when test="${not empty historialLogros}">
                                            <c:forEach var="logro" items="${historialLogros}">
                                                <tr>
                                                    <td>${logro.id}</td>
                                                    <td>${logro.nombreAlumno}</td>
                                                    <td>${logro.nombreCurso}</td>
                                                    <td>${logro.grado}° ${logro.seccion}</td>
                                                    <td>${logro.nombreLogro}</td>
                                                    <td>${logro.puntos}</td>
                                                    <td>${logro.comentario}</td>
                                                    <td>${logro.fechaAsignacion}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr id="filaSinDatos">
                                                <td colspan="8" class="text-center text-muted">🛈 No se encontraron resultados.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </c:when>




    <%-------------------------------------------------------------- Sesion 4 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'rankingEstudiantes'}">
        <div class="container mt-5">
            <section class="panel-ranking animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-docente text-info d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Ranking de alumnos</h5>
                        <i class="fas fa-trophy"></i>
                    </div>
                    <div class="card-body">
                        <!-- Botones de acción -->
                        <div class="d-flex justify-content-end mb-3">
                            <button class="btn btn-success me-2 btn-exportar-ranking">
                                <i class="fas fa-file-excel"></i> Exportar Excel
                            </button>
                            <button class="btn btn-danger btn-generar-pdf">
                                <i class="fas fa-file-pdf"></i> Generar PDF
                            </button>
                        </div>

                        <%-- Tabla de Ranking --%>
                        <div class="table-responsive" style="max-height: 500px; overflow-y: auto;">
                            <table class="table table-bordered text-center align-middle tabla-ranking" id="tablaRanking">
                                <thead class="encabezado-ranking">
                                    <tr>
                                        <th>#</th>
                                        <th>Nombre del Alumno</th>
                                        <th>Total de Puntos</th>
                                        <th>Medalla</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyRanking">
                                    <c:choose>
                                        <c:when test="${not empty rankingAlumnos}">
                                            <c:forEach var="alumno" items="${rankingAlumnos}" varStatus="status">
                                                <tr>
                                                    <td>${status.index + 1}</td>
                                                    <td>${alumno.nombre}</td>
                                                    <td>${alumno.puntos}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${alumno.medalla == 'oro'}">
                                                                <i class="fas fa-medal medalla oro" title="Oro"></i> Oro
                                                            </c:when>
                                                            <c:when test="${alumno.medalla == 'plata'}">
                                                                <i class="fas fa-medal medalla plata" title="Plata"></i> Plata
                                                            </c:when>
                                                            <c:when test="${alumno.medalla == 'bronce'}">
                                                                <i class="fas fa-medal medalla bronce" title="Bronce"></i> Bronce
                                                            </c:when>

                                                            <c:otherwise>
                                                                <span>-</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr id="filaSinRanking">
                                                <td colspan="4" class="text-center text-muted">🛈 No hay datos disponibles para el ranking.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </c:when>



    <%-------------------------------------------------------------- Sesion 5 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'retosEstudiantes'}">
        <div class="container mt-5">
            <section class="panel-retosEstudiantes animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-docente text-info d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Retos Pendientes por Grado</h5>
                        <i class="fas fa-puzzle-piece"></i>
                    </div>
                    <div class="card-body">

                        <div class="selector-grado">
                            <label for="selectCursoReto" class="form-label">Selecciona curso y sección:</label>

                            <select id="selectGrado" class="form-control mb-2">
                                <option selected disabled>-- Selecciona --</option>
                                <c:forEach var="curso" items="${cursosAsignados}">
                                    <option value="${curso.idCurso}-${curso.grado}-${curso.seccion}">
                                        ${curso.nombreCurso} - ${curso.grado}°${curso.seccion}
                                    </option>
                                </c:forEach>
                            </select>

                            <label for="selectAlumnoReto" class="form-label">Selecciona un alumno:</label>
                            <select id="selectAlumnoReto" class="form-control mb-3" disabled>
                                <option selected disabled>-- Selecciona un alumno --</option>
                            </select>
                            <button id="btnMostrarRetos" class="btn-ver-retos">Ver Retos</button>
                        </div>
                        <div id="contenedorRetos" style="margin-top: 20px; display: none;">
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </c:when>

</c:choose>

