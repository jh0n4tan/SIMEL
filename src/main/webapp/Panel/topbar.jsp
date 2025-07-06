<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
    </button>

    <!-- Logo -->
    <a href="#" class="navbar-brand d-lg-block" style="padding-left: 10px;">
        <img src="img/logo.svg" alt="Logo SIMEL" width="200" height="70">
    </a>

    <ul class="navbar-nav ml-auto d-flex align-items-center">


        
        
        <!-- Ícono de Notificaciones 
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link" href="javascript:void(0);" onclick="toggleNotifications()">
                <i class="fa fa-bell" style="font-size: 24px; color: #004E92; background-color: white; border-radius: 50%; padding: 5px;"></i>
            </a>
        </li>
        -->
        
        
        
        <!-- Separador -->
        <li class="nav-item d-none d-lg-block" style="margin-right: 1px;">
            <div class="topbar-divider" style="border-left: 1.5px solid black; height: 40px;"></div>
        </li>

        <!-- Usuario -->
        <li class="nav-item dropdown no-arrow d-none d-lg-flex">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
                <div class="d-inline-block text-left">
                    <div style="font-size: 15px; color: black;">
                        Hola, <span class="font-weight-bold"><%= session.getAttribute("nombre")%></span>
                    </div>
                    <div style="margin-left: 40px; font-size: 14px; color: black;">
                        <%= session.getAttribute("rol")%>
                    </div>
                </div>
                <img src="<%= session.getAttribute("imgSrc")%>" alt="usuario" width="40" style="margin-left: 10px;">
                <i id="dropdownArrow" class="fas fa-chevron-down ml-2 text-dark"></i>
            </a>
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-dark"></i>
                    Cerrar Sesión
                </a>
            </div>
        </li>
    </ul>

    <!-- Contenedor de Notificaciones (fuera del ul para que no desordene) -->
    <div id="notificationDrawer" class="notification-drawer" style="display:none;">
        <div class="notification-header">
            <span>Notificaciones</span>
            <button onclick="toggleNotifications()">×</button>
        </div>
        <div class="notification-body">
            <p>No tienes notificaciones.</p>
        </div>
    </div>
</nav>
