<nav class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-graduation-cap"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Centro Educativo 123</div>
    </a>

    <hr class="sidebar-divider my-0">
    <hr class="sidebar-divider">
    <div class="sidebar-heading">Categorías</div>

    <ul class="nav flex-column">
        <li class="nav-item ${param.seccion == 'misCursos' ? 'active' : ''}">
            <a class="nav-link" href="alumno?seccion=misCursos">
                <i class="fas fa-book-open"></i>
                <span>Mis Cursos</span>
            </a>
        </li>
        <li class="nav-item ${param.seccion == 'misNotas' ? 'active' : ''}">
            <a class="nav-link" href="alumno?seccion=misNotas">
                <i class="fas fa-clipboard-list"></i>
                <span>Mis Notas</span>
            </a>
        </li>
        <li class="nav-item ${param.seccion == 'logros' ? 'active' : ''}">
            <a class="nav-link" href="alumno?seccion=logros">
                <i class="fas fa-award"></i>
                <span>Logros</span>
            </a>
        </li>
        <li class="nav-item ${param.seccion == 'canjes' ? 'active' : ''}">
            <a class="nav-link" href="alumno?seccion=canjes">
                <i class="fas fa-gift"></i>
                <span>Canjes</span>
            </a>
        </li>
        <li class="nav-item ${param.seccion == 'retos' ? 'active' : ''}">
            <a class="nav-link" href="alumno?seccion=retos">
                <i class="fas fa-bolt"></i>
                <span>Retos</span>
            </a>
        </li>
    </ul>

    <hr class="sidebar-divider d-none d-md-block">
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>
</nav>
