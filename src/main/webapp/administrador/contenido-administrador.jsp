<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>



<c:choose>

    <%-------------------------------------------------------------- Sesion 1 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'usuarios'}">
        <div class="container">
            <div class="card shadow-sm">
                <div class="card-header bg-administrador text-secondary d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Registro de usuarios</h5>
                    <i class="fas fa-trophy"></i>
                </div>

                <div class="container">
                    <br>  
                    <div class="row">
                        <div class="col-lg-12">            
                            <button id="btnNuevo" type="button" class="btn btn-secondary" data-toggle="modal">Nuevo</button>    
                        </div>    
                    </div>    
                </div>    
                <br>  
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="table-responsive">        
                                <table id="tablaPersonas" class="table table-striped table-bordered table-condensed text-center" style="width:100%">
                                    <thead class="encabezado-gestionCanjes">
                                        <tr>
                                            <th>Id</th>
                                            <th>Nombre</th>
                                            <th>Usuario</th>                                                                          
                                            <th>Cargo</th>
                                            <th>Estado</th>
                                            <th>Fecha de creación</th> 
                                            <th>Acciones</th>                                   
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="usuario" items="${usuarios}">
                                            <tr>
                                                <td>${usuario.id}</td>
                                                <td>${usuario.nombre}</td>
                                                <td>${usuario.usuario}</td>                                        
                                                <td>${usuario.rol}</td>
                                                <td>${usuario.estado}</td>
                                                <td>${usuario.fechaCreacion}</td>
                                                <td></td>                                       
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 
                            </div>
                        </div>
                    </div> 
                </div>    
            </div>

            <%-------------------------------------------------------------- Modal ----------------------------------------------------------------%>
            <div class="modal fade" id="modalCRUD" tabindex="-1" role="dialog" aria-labelledby="modalCRUDLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel"></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>
                        <form id="formPersonas">    
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="nombre" class="col-form-label">Nombre:</label>
                                    <input type="text" class="form-control" id="nombre" required>
                                </div>
                                <div class="form-group">
                                    <label for="usuario" class="col-form-label">Usuario:</label>
                                    <input type="text" class="form-control" id="usuario" required readonly>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-form-label">Contraseña:</label>
                                    <input type="password" class="form-control" id="password">
                                </div>
                                <div class="form-group">
                                    <label for="cargo" class="col-form-label">Cargo:</label>
                                    <select class="form-control" id="cargo" required>   
                                        <option value="alumno">alumno</option>
                                        <option value="docente">docente</option>
                                        <option value="administrador">administrador</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="estado" class="col-form-label">Estado:</label>
                                    <select class="form-control" id="estado" required>
                                        <option value="activo">Activo</option>         
                                    </select>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-light" data-dismiss="modal">Cancelar</button>
                                <button type="submit" id="btnGuardar" class="btn btn-dark">Guardar</button>
                            </div>

                            <input type="hidden" id="id" name="id">
                        </form>    
                    </div>
                </div>
            </div>  

        </div>

    </c:when>

    <%-------------------------------------------------------------- Sesion 2 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'cursosAsignaciones'}">
        <div class="container mt-5">
            <section class="panel-asignacion animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-administrador text-secondary d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Administración de asignaturas</h5>
                        <i class="fas fa-chalkboard-teacher"></i>
                    </div>
                    <div class="card-body">


                        <ul class="nav nav-tabs mb-4" id="asignacionTabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="docentes-tab" data-toggle="tab" href="#docentes" role="tab" aria-controls="docentes" aria-selected="true">
                                    <i class="fas fa-user-tie"></i> Docentes
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="alumnos-tab" data-toggle="tab" href="#alumnos" role="tab" aria-controls="alumnos" aria-selected="false">
                                    <i class="fas fa-user-graduate"></i> Alumnos
                                </a>
                            </li>
                        </ul>

                        <div class="tab-content" id="asignacionTabsContent">

                            <%-- Pestaña Docentes --%>
                            <div class="tab-pane fade show active" id="docentes" role="tabpanel" aria-labelledby="docentes-tab">

                                <div class="card mb-4 shadow-sm">
                                    <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="fas fa-chalkboard-teacher"></i> Asignar Curso y Grado a Docente</h5>
                                    </div>
                                    <div class="card-body">
                                        <form id="form-asignar-docente" method="post" action="AsignarCursoDocente">
                                            <div class="form-group">
                                                <label for="docente-select">Seleccionar docente:</label>
                                                <select class="form-control" id="docente-select" name="id_docente" required>
                                                    <option value="">-- Selecciona --</option>
                                                    <c:forEach var="docente" items="${docentes}">
                                                        <option value="${docente.id}">${docente.nombre}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>

                                            <div class="form-group">
                                                <label for="curso-select">Seleccionar curso:</label>
                                                <select class="form-control" id="curso-select" name="id_curso" required>
                                                    <option value="">-- Selecciona --</option>
                                                    <c:forEach var="curso" items="${cursos}">
                                                        <option value="${curso.id}">${curso.nombre}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>

                                            <div class="form-group">
                                                <label for="grado-select">Seleccionar grado/sección:</label>
                                                <select class="form-control" id="grado-select" name="id_grado_seccion" required>
                                                    <option value="">-- Selecciona --</option>
                                                    <c:forEach var="gs" items="${gradosSeccion}">
                                                        <option value="${gs.id}">${gs.grado}° - ${gs.seccion}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>

                                            <button type="submit" class="btn btn-success shadow-sm">
                                                <i class="fas fa-plus-circle"></i> Asignar
                                            </button>
                                        </form>
                                    </div>
                                </div>

                                <div class="card border-left-secondary shadow-sm">
                                    <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="fas fa-list"></i> Cursos Asignados a Docentes</h5>
                                    </div>
                                    <div class="card-body p-0">
                                        <ul id="cursos-asignados-docente" class="list-group lista-scroll mb-0">
                                            <c:forEach var="asignado" items="${cursosAsignados}">
                                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                                    ${asignado.docenteNombre} - ${asignado.cursoNombre} (${asignado.grado}° - ${asignado.seccion})
                                                    <span class="badge bg-success text-white">Asignado</span>
                                                </li>
                                            </c:forEach>
                                        </ul>

                                    </div>
                                </div>

                            </div>

                            <%--Formulario del alumno  --%>
                            
                            <div class="tab-pane fade" id="alumnos" role="tabpanel" aria-labelledby="alumnos-tab">
                                <div class="card mb-4 shadow-sm">
                                    <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="fas fa-user-graduate"></i> Asignar Grado a Alumno</h5>
                                    </div>
                                    <div class="card-body">
                                        <form id="form-asignar-alumno" method="post" action="AsignarAlumno">
                                            <div class="form-group">
                                                <label for="alumno-select">Seleccionar alumno:</label>
                                                <select class="form-control" id="alumno-select" name="id_alumno" required>
                                                    <option value="">-- Selecciona --</option>
                                                    <c:forEach var="alumno" items="${alumnos}">
                                                        <option value="${alumno.id}">${alumno.nombre}</option>
                                                    </c:forEach>
                                                </select>


                                            </div>

                                            <div class="form-group">
                                                <label for="grado-seccion-select">Seleccionar grado/sección:</label>
                                                <select class="form-control" id="grado-seccion-select" name="id_grado_seccion" required>
                                                    <option value="">-- Selecciona --</option>
                                                    <c:forEach var="gs" items="${gradosSeccion}">
                                                        <option value="${gs.id}">${gs.grado}° - ${gs.seccion}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>

                                            <button type="submit" class="btn btn-success shadow-sm">
                                                <i class="fas fa-plus-circle"></i> Asignar
                                            </button>
                                        </form>
                                    </div>
                                </div>

                                <div class="card border-left-secondary shadow-sm">
                                    <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="fas fa-list-alt"></i> Alumnos por Sección</h5>
                                    </div>
                                    <div class="card-body p-0">
                                        <ul id="alumnos-por-seccion" class="list-group lista-scroll mb-0">
                                            <c:forEach var="item" items="${resumenPorSeccion}">
                                                <li class="list-group-item">
                                                    <c:out value="${item}" escapeXml="false" />
                                                </li>
                                            </c:forEach>
                                        </ul>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

    </c:when>

    <%-------------------------------------------------------------- Sesion 3 ----------------------------------------------------------------%>
    <c:when test="${seccion == 'logrosPremios'}">
        <div class="container mt-5">
            <section class="panel-logros animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-docente text-gray d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Asignación y registro de logros</h5>
                        <i class="fas fa-award"></i>
                    </div>

                    <div class="card-body">
                        <div class="mb-4">
                            <label for="selectLogro" class="form-label">Asigne un nuevo logro: </label>
                            <input type="text" id="nombreLogro" class="form-control mb-2" placeholder="Nombre del logro" />
                            <textarea id="descripcionLogro" class="form-control mb-3" rows="2" placeholder="Descripción del logro" style="resize: none;"></textarea>
                            <input type="hidden" id="idLogro" value="" />
                            <button id="btnGuardarLogro" class="btn btn-success">
                                <i class="fas fa-save"></i> Guardar
                            </button>
                            <button id="btnCancelarEdicion" class="btn btn-secondary d-none">Cancelar</button>
                        </div>

                        <hr>
                        <h5>Logros creados/asignados</h5>

                        <%-- aparezca una barra de desplazamiento vertical --%>
                        <div class="table-scroll" style="max-height: 300px; overflow-y: auto;">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="encabezado-logros">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Descripción</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="tablaLogrosDocenteBody">
                                    <c:choose>
                                        <c:when test="${not empty logros}">
                                            <c:forEach var="logro" items="${logros}">
                                                <tr data-id="${logro.id}">
                                                    <td>${logro.id}</td>
                                                    <td class="nombre-logro">${logro.nombre}</td>
                                                    <td class="descripcion-logro">${logro.descripcion}</td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-primary btnEditar" data-id="${logro.id}">
                                                            <i class="fas fa-edit"></i> Editar
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="4">No se encontraron logros.</td>
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
    <c:when test="${seccion == 'canjes'}">
        <div class="container mt-5">
            <section class="panel-gestionPremios animate__animated animate__fadeIn">
                <div class="card shadow-sm">
                    <div class="card-header bg-admin text-gray d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Historial y control de canjes</h5>
                        <i class="fas fa-gift"></i>
                    </div>
                    <div class="card-body">
                        <div class="mb-4">
                            <input type="hidden" id="premioId" value="" />
                            <input type="hidden" id="imagenAntigua" name="imagenAntigua" value="">
                            <input type="text" id="nombrePremio" class="form-control mb-2" placeholder="Nombre del premio" />
                            <textarea id="descripcionPremio" class="form-control mb-2" rows="2" placeholder="Descripción del premio" style="resize: none;"></textarea>
                            <input type="number" id="puntosPremio" class="form-control mb-2" placeholder="Puntos requeridos" min="0" />
                            <select id="tipoPremio" class="form-control mb-2">
                                <option value="">Seleccione tipo</option>
                                <option value="físico">Físico</option>
                                <option value="digital">Digital</option>
                            </select>
                            <label class="form-label">Imagen del premio</label>
                            <div class="custom-file-upload mb-2 d-flex justify-content-center">
                                <label for="archivoImagenPremio" class="upload-btn text-center">
                                    <i class="fas fa-upload me-2"></i> Elegir imagen
                                </label>
                                <input type="file" id="archivoImagenPremio" accept="image/*" />
                            </div>
                            <div id="previewImagenPremio" class="mb-3"></div>
                            <button id="btnGuardarPremio" class="btn btn-success">
                                <i class="fas fa-gift"></i> Guardar
                            </button>
                            <button id="btnCancelarEdicion" class="btn btn-secondary" style="display:none;">Cancelar</button>
                        </div>
                        <hr />
                        <h5>Premios creados</h5>
                        <div class="table-scroll" style="max-height: 300px; overflow-y: auto;">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="encabezado-gestionCanjes">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Descripción</th>
                                        <th>Puntos</th>
                                        <th>Tipo</th>
                                        <th>Imagen</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="tablaPremiosBody">
                                    <c:choose>
                                        <c:when test="${not empty premios}">
                                            <c:forEach var="premio" items="${premios}">
                                                <tr data-id="${premio.id}">
                                                    <td>${premio.id}</td>
                                                    <td class="nombre-premio">${premio.nombre}</td>
                                                    <td class="descripcion-premio">${premio.descripcion}</td>
                                                    <td class="puntos-premio">${premio.puntosRequeridos}</td>
                                                    <td class="tipo-premio">${premio.tipo}</td>
                                                    <td>
                                                        <img src="${pageContext.request.contextPath}/mostrarImagen?file=${premio.imagen}" alt="Imagen Premio" style="width: 60px; height: 60px; object-fit: cover;" />
                                                    </td>
                                                    <td>
                                                        <button class="btn btn-sm btn-outline-primary btnEditar" data-id="${premio.id}">
                                                            <i class="fas fa-edit"></i> Editar
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="7">No se encontraron premios registrados.</td>
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

    <c:when test="${seccion == 'reportes'}">
        <div class="container mt-5">
            <section id="seccion-reportes" class="reportes">
                <div class="card shadow-sm">
                    <div class="card-header bg-admintrador text-gray d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Panel de Reportes</h5>
                        <i class="fas fa-chart-bar"></i>
                    </div>
                    <div class="card-body">

                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs mb-4" id="reportesTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <a class="nav-link active" id="ranking-tab" data-toggle="tab" href="#ranking" role="tab" aria-controls="ranking" aria-selected="true">
                                    🎓 Ranking Alumnos
                                </a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link" id="canjes-tab" data-toggle="tab" href="#canjes" role="tab" aria-controls="canjes" aria-selected="false">
                                    🎁 Canjes Realizados
                                </a>
                            </li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content" id="reportesTabsContent">

                            <!-- Ranking Alumnos -->
                            <div class="tab-pane fade show active" id="ranking" role="tabpanel" aria-labelledby="ranking-tab">

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

                            <!-- Canjes Realizados -->
                            <div class="tab-pane fade" id="canjes" role="tabpanel" aria-labelledby="canjes-tab">

                                <div class="table-responsive" style="max-height: 500px; overflow-y: auto;">
                                    <table class="table table-bordered text-center align-middle tabla-canjes" id="tablaCanjes">
                                        <thead class="encabezado-canjes">
                                            <tr>
                                                <th>#</th>
                                                <th>Nombre del Alumno</th>
                                                <th>Premio</th>
                                                <th>Código Canje</th>
                                                <th>Estado</th>
                                                <th>Fecha Canje</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tbodyCanjes">
                                            <c:choose>
                                                <c:when test="${not empty listaCanjes}">
                                                    <c:forEach var="canje" items="${listaCanjes}" varStatus="status">
                                                        <tr>
                                                            <td>${status.index + 1}</td>
                                                            <td>${canje.alumnoNombre}</td>
                                                            <td>${canje.premioNombre}</td>
                                                            <td><code>${canje.codigoCanje}</code></td>
                                                            <td>${canje.estado}</td>
                                                            <td>
                                                    <fmt:formatDate value="${canje.fechaCanje}" pattern="dd/MM/yyyy HH:mm" />
                                                    </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr id="filaSinCanjes">
                                                    <td colspan="6" class="text-center text-muted">🛈 No hay datos disponibles para canjes.</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                        </tbody>

                                    </table>
                                </div>

                            </div>

                        </div>

                        <div class="acciones-reporte mt-4 d-flex justify-content-center gap-3">
                            <button class="btn btn-danger" id="btn-exportar-pdf">
                                <i class="fas fa-file-pdf"></i> Generar PDF
                            </button>
                            <button class="btn btn-success" id="btn-exportar-excel">
                                <i class="fas fa-file-excel"></i> Exportar Excel
                            </button>
                        </div>

                    </div>
                </div>
            </section>
        </div>
    </c:when>



    <%-------------------------------------------------------------- Sesion 6 ----------------------------------------------------------------%>

    <c:when test="${seccion == 'retosadmin'}">
        <div class="container mt-5">
            <section id="seccion-reportes" class="reportes">
                <div class="card shadow-sm">
                    <div class="card-header bg-admintrador text-gray d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Desafíos académicos</h5>
                        <i class="fas fa-chart-bar"></i>
                    </div>
                    <div class="card-body">

                        <form id="formCrearReto" method="post" action="${pageContext.request.contextPath}/retos">
                            <input type="hidden" id="idReto" name="id" value="">
                            <input type="hidden" id="accionReto" name="accion" value="agregar" />
                            <div class="form-group">
                                <label for="nombreReto">Nombre del reto</label>
                                <input type="text" class="form-control" id="nombreReto" name="nombre" required>
                            </div>

                            <div class="form-group mt-3">
                                <label for="descripcionReto">Descripción</label>
                                <textarea class="form-control" id="descripcionReto" name="descripcion" rows="2" style="resize: none;"></textarea>
                            </div>

                            <div class="form-group mt-3">
                                <label for="puntosReto">Puntos</label>
                                <input type="number" class="form-control" id="puntosReto" name="puntos" required>
                            </div>

                            <div class="form-group mt-3">
                                <label for="gradoReto">Grado al que va dirigido</label>
                                <select class="form-control" id="gradoReto" name="grado"required>
                                    <option value="">Seleccione un grado</option>
                                    <option value="4">4to</option>
                                    <option value="5">5to</option>
                                    <option value="6">6to</option>

                                </select>
                            </div>
                            <button type="submit" id="btnGuardarReto" class="btn btn-secondary mt-2">
                                <i class="fas fa-bolt"></i> Crear
                            </button>
                            <button id="btnCancelarEdicionReto" class="btn btn-outline-secondary mt-2" style="display: none;">
                                Cancelar edición
                            </button>
                        </form>
                        <hr>
                        <h5>Retos creados</h5>
                        <div class="table-scroll" style="max-height: 300px; overflow-y: auto;">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="encabezado-gestionRetos">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Descripción</th>
                                        <th>Puntos</th>
                                        <th>Grado</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="tablaRetosBody">
                                    <c:forEach var="reto" items="${retos}">
                                        <tr data-id="${reto.id}">
                                            <td>${reto.id}</td>
                                            <td class="nombre-reto">${reto.nombre}</td>
                                            <td class="descripcion-reto">${reto.descripcion}</td>
                                            <td class="puntos-reto">${reto.puntos}</td>
                                            <td class="grado-reto" data-grado="${reto.grado}">${reto.grado} - ${reto.seccion}</td>
                                            <td>
                                                <button class="btn btn-sm btn-outline-primary btnEditar" data-id="${reto.id}">
                                                    <i class="fas fa-edit"></i> Editar
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty retos}">
                                        <tr>
                                            <td colspan="6" class="text-center">No se encontraron retos.</td>
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


</c:choose>