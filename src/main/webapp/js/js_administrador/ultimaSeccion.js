document.addEventListener("DOMContentLoaded", () => {
    const btnGuardar = document.getElementById("btnGuardarReto");
    const btnCancelar = document.getElementById("btnCancelarEdicionReto");
    const form = document.getElementById("formCrearReto");
    const inputId = document.getElementById("idReto");
    const inputAccion = document.getElementById("accionReto");

    btnGuardar.addEventListener("click", async (e) => {
        e.preventDefault();

        const nombre = document.getElementById("nombreReto").value.trim();
        const descripcion = document.getElementById("descripcionReto").value.trim();
        const puntos = document.getElementById("puntosReto").value.trim();
        const grado = document.getElementById("gradoReto").value.trim();

        if (!nombre || !descripcion || !puntos || !grado) {
            Swal.fire({
                icon: 'warning',
                title: 'Campos incompletos',
                text: 'Por favor completa todos los campos antes de continuar.'
            });
            return;
        }

        if (isNaN(puntos) || parseInt(puntos) <= 0) {
            Swal.fire({
                icon: 'warning',
                title: 'Puntos inválidos',
                text: 'Ingrese un número válido mayor que cero para los puntos.'
            });
            return;
        }

        btnGuardar.disabled = true;

        const datos = new URLSearchParams();
        datos.append("accion", inputAccion.value);
        if (inputAccion.value === "editar") {
            datos.append("id", inputId.value);
        }
        datos.append("nombre", nombre);
        datos.append("descripcion", descripcion);
        datos.append("puntos", puntos);
        datos.append("grado", grado);

        try {
            const response = await fetch("retos", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: datos.toString()
            });

            const texto = await response.text();

            if (texto === "OK") {
                Swal.fire({
                    icon: 'success',
                    title: inputAccion.value === 'agregar' ? 'Reto creado' : 'Reto actualizado',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => window.location.reload());
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'No se pudo guardar el reto. Inténtalo nuevamente.'
                });
            }
        } catch (error) {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'Ocurrió un problema al conectar con el servidor.'
            });
        } finally {
            btnGuardar.disabled = false;
        }
    });

    btnCancelar.addEventListener("click", (e) => {
        e.preventDefault();
        resetForm();
    });

    function resetForm() {
        inputId.value = "";
        inputAccion.value = "agregar";
        form.reset();
        btnCancelar.style.display = "none";
        btnGuardar.innerHTML = '<i class="fas fa-bolt"></i> Crear';
    }

    document.getElementById("tablaRetosBody").addEventListener("click", e => {
        if (e.target.closest(".btnEditar")) {
            const fila = e.target.closest("tr");
            inputId.value = fila.dataset.id;
            document.getElementById("nombreReto").value = fila.querySelector(".nombre-reto").textContent;
            document.getElementById("descripcionReto").value = fila.querySelector(".descripcion-reto").textContent;
            document.getElementById("puntosReto").value = fila.querySelector(".puntos-reto").textContent;
            const grado = fila.querySelector(".grado-reto").dataset.grado;
            document.getElementById("gradoReto").value = grado;


            inputAccion.value = "editar";
            btnCancelar.style.display = "inline-block";
            btnGuardar.innerHTML = '<i class="fas fa-edit"></i> Actualizar';
        }
    });
});
