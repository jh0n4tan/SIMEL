document.addEventListener('DOMContentLoaded', () => {
    const btnGuardar = document.getElementById('btnGuardarLogro');
    const btnCancelar = document.getElementById('btnCancelarEdicion');
    const inputId = document.getElementById('idLogro');
    const inputNombre = document.getElementById('nombreLogro');
    const inputDescripcion = document.getElementById('descripcionLogro');
    const tablaBody = document.getElementById('tablaLogrosDocenteBody');

    function resetForm() {
        inputId.value = '';
        inputNombre.value = '';
        inputDescripcion.value = '';
        btnCancelar.classList.add('d-none');
        btnGuardar.textContent = 'Guardar';
        btnGuardar.querySelector('i').className = 'fas fa-save';
    }

    btnGuardar.addEventListener('click', () => {
        const id = inputId.value;
        const nombre = inputNombre.value.trim();
        const descripcion = inputDescripcion.value.trim();

        if (!nombre || !descripcion) {
            Swal.fire({
                icon: 'warning',
                title: 'Campos incompletos',
                text: 'Por favor, completa todos los campos'
            });
            return;
        }

        const accion = id ? 'editar' : 'agregar';

        const datos = new URLSearchParams();
        datos.append('accion', accion);
        if (id)
            datos.append('id', id);
        datos.append('nombre', nombre);
        datos.append('descripcion', descripcion);

        fetch('logro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: datos.toString()
        })
                .then(res => res.text())
                .then(resp => {
                    console.log("Respuesta del servidor:", resp);
                    if (resp === 'OK') {
                        Swal.fire({
                            icon: 'success',
                            title: accion === 'agregar' ? 'Logro agregado' : 'Logro actualizado',
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() => location.reload());
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Hubo un problema al guardar el logro: ' + resp
                        });
                    }
                })
                .catch(error => {
                    console.error("Error de red o servidor:", error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de red',
                        text: 'No se pudo conectar con el servidor'
                    });
                });

    });

    btnCancelar.addEventListener('click', () => {
        Swal.fire({
            title: '¿Cancelar edición?',
            text: 'Se perderán los cambios no guardados.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, cancelar',
            cancelButtonText: 'No, continuar editando'
        }).then((result) => {
            if (result.isConfirmed) {
                resetForm();
                Swal.fire({
                    title: 'Edición cancelada',
                    icon: 'info',
                    timer: 1500,
                    showConfirmButton: false
                });
            }
        });
    });

    tablaBody.addEventListener('click', e => {
        if (e.target.closest('.btnEditar')) {
            const fila = e.target.closest('tr');
            inputId.value = fila.dataset.id;
            inputNombre.value = fila.querySelector('.nombre-logro').textContent;
            inputDescripcion.value = fila.querySelector('.descripcion-logro').textContent;
            btnCancelar.classList.remove('d-none');
            btnGuardar.textContent = 'Actualizar';
            btnGuardar.querySelector('i').className = 'fas fa-edit';
        }
    });
});
