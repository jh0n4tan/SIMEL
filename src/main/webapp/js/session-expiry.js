(function () {
    // Tiempo total de sesión en milisegundos (3 minutos = 180000 ms)
    const sessionTimeout = 3 * 60 * 1000;

    // Tiempo antes de expiración para mostrar alerta de advertencia (1 minuto = 60000 ms)
    const warningTime = 1 * 60 * 1000;

    let warningTimer, expiryTimer;

    // Función para renovar sesión con ping al backend (opcional)
    function renovarSesion() {
        fetch('RenovarSesionServlet')
            .then(response => {
                if (response.ok) {
                    console.log("Sesión renovada con éxito");
                } else {
                    console.warn("No se pudo renovar la sesión");
                }
            })
            .catch(error => console.error('Error al renovar sesión:', error));
    }

    // Función para mostrar alerta previa con SweetAlert2
    function mostrarAlertaPrevio() {
        Swal.fire({
            title: 'Sesión próxima a expirar',
            text: '¿Quieres continuar conectado?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, continuar',
            cancelButtonText: 'No, salir',
            allowOutsideClick: false,
            allowEscapeKey: false,
            backdrop: true,
            timer: 30000, // 30 segundos para responder
            timerProgressBar: true
        }).then((result) => {
            if (result.isConfirmed) {
                // Usuario confirma que quiere seguir conectado
                renovarSesion();
                iniciarTimers(); // Reiniciar timers de sesión
            } else if (result.dismiss === Swal.DismissReason.timer) {
                // Usuario no respondió en 30 segundos, sesión expira
                mostrarAlertaExpiracion();
            } else {
                // Usuario cancela explícitamente, redirigir a login
                redirigirALogin();
            }
        });
    }

    // Función para mostrar alerta de expiración y redirigir
    function mostrarAlertaExpiracion() {
        Swal.fire({
            title: 'Sesión expirada',
            text: 'Tu sesión ha expirado por inactividad. Serás redirigido al login.',
            icon: 'error',
            confirmButtonText: 'Aceptar',
            allowOutsideClick: false,
            allowEscapeKey: false,
            backdrop: true
        }).then(() => {
            redirigirALogin();
        });
    }

    // Redirige al login con parámetro para mostrar mensaje en index.jsp
    function redirigirALogin() {
        window.location.href = "index.jsp?expired=true";
    }

    // Inicia o reinicia los timers para alerta previa y expiración
    function iniciarTimers() {
        if (warningTimer) clearTimeout(warningTimer);
        if (expiryTimer) clearTimeout(expiryTimer);

        warningTimer = setTimeout(() => {
            mostrarAlertaPrevio();
        }, sessionTimeout - warningTime);

        expiryTimer = setTimeout(() => {
            // Solo mostrar alerta expiracion si no se mostró antes
            mostrarAlertaExpiracion();
        }, sessionTimeout);
    }

    // Arranca los timers cuando carga la página
    iniciarTimers();

})();
