<nav class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-graduation-cap"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Centro Educativo 123<sup></sup></div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider">

    <!-- Heading -->
    <div class="sidebar-heading">
        Gestión
    </div>

    <ul class="nav flex-column">
        <!-- Nav Item - Usuarios -->
        <li class="nav-item ${param.seccion == 'usuarios' ? 'active' : ''}">
            <a class="nav-link" href="administrador?seccion=usuarios">
                <i class="fas fa-user-plus"></i>
                <span>Gestión de Usuarios</span>
            </a>
        </li>

        <!-- Nav Item - Cursos y Asignaciones -->
        <li class="nav-item ${param.seccion == 'cursosAsignaciones' ? 'active' : ''}">
            <a class="nav-link" href="administrador?seccion=cursosAsignaciones">
                <i class="fas fa-book-open"></i>
                <span>Gestión de Asignaciones</span>
            </a>
        </li>

        <!-- Nav Item - Logros y Premios -->
        <li class="nav-item ${param.seccion == 'logrosPremios' ? 'active' : ''}">
            <a class="nav-link" href="administrador?seccion=logrosPremios">
                <i class="fas fa-medal"></i>
                <span>Gestión de Logros</span>
            </a>
        </li>

        <!-- Nav Item - Canjes -->
        <li class="nav-item ${param.seccion == 'canjes' ? 'active' : ''}">
            <a class="nav-link" href="administrador?seccion=canjes">
                <i class="fas fa-gift"></i>
                <span>Gestión de Canjes</span>
            </a>
        </li>

        <!-- Nav Item - Reportes -->
        <li class="nav-item ${param.seccion == 'reportes' ? 'active' : ''}">
            <a class="nav-link" href="administrador?seccion=reportes">
                <i class="fas fa-chart-bar"></i>
                <span>Reportes</span>
            </a>
        </li>

        <!-- Nav Item - Configuración -->
        <li class="nav-item ${param.seccion == 'retosadmin' ? 'active' : ''}">
            <a class="nav-link" href="administrador?seccion=retosadmin">
                <i class="fas fa-cogs"></i>
                <span>Gestión de Retos</span>
            </a>
        </li>
    </ul>

    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Botón para toggle -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</nav>
