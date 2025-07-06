document.addEventListener('DOMContentLoaded', () => {
    const btnGuardar = document.getElementById('btnGuardarPremio');
    const btnCancelar = document.getElementById('btnCancelarEdicion');
    const inputId = document.getElementById('premioId');
    const inputNombre = document.getElementById('nombrePremio');
    const inputDescripcion = document.getElementById('descripcionPremio');
    const inputPuntos = document.getElementById('puntosPremio');
    const selectTipo = document.getElementById('tipoPremio');
    const inputArchivo = document.getElementById('archivoImagenPremio');
    const previewImagen = document.getElementById('previewImagenPremio');
    const tablaBody = document.getElementById('tablaPremiosBody');

    let imagenAntigua = null; // Para mantener imagen si no se cambia en edición

    function resetForm() {
        inputId.value = '';
        inputNombre.value = '';
        inputDescripcion.value = '';
        inputPuntos.value = '';
        selectTipo.value = '';
        inputArchivo.value = '';
        previewImagen.innerHTML = '';
        imagenAntigua = null;
        btnCancelar.style.display = 'none';
        btnGuardar.innerHTML = `<i class="fas fa-gift"></i> Guardar`;
    }

    // Mostrar preview cuando se selecciona archivo
    inputArchivo.addEventListener('change', () => {
        const file = inputArchivo.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = e => {
                previewImagen.innerHTML = `<img src="${e.target.result}" alt="Preview" style="max-width: 150px; max-height: 150px; object-fit: contain;"/>`;
            };
            reader.readAsDataURL(file);
        } else {
            previewImagen.innerHTML = '';
        }
    });

    btnGuardar.addEventListener('click', () => {
        const id = inputId.value;
        const nombre = inputNombre.value.trim();
        const descripcion = inputDescripcion.value.trim();
        const puntos = inputPuntos.value;
        const tipo = selectTipo.value;

        if (!nombre || !descripcion || !puntos || !tipo) {
            Swal.fire({
                icon: 'warning',
                title: 'Campos incompletos',
                text: 'Por favor, completa todos los campos'
            });
            return;
        }

        const formData = new FormData();
        formData.append('accion', id ? 'editar' : 'agregar');
        if (id)
            formData.append('premioId', id);
        formData.append('nombrePremio', nombre);
        formData.append('descripcionPremio', descripcion);
        formData.append('puntosPremio', puntos);
        formData.append('tipoPremio', tipo);
        // Si se seleccionó archivo nuevo, se manda, sino se manda imagen antigua
        if (inputArchivo.files.length > 0) {
            formData.append('archivoImagenPremio', inputArchivo.files[0]);
        } else if (imagenAntigua) {
            formData.append('imagenAntigua', imagenAntigua);
        }

        fetch('premio', {
            method: 'POST',
            body: formData
        })
                .then(res => res.text())
                .then(resp => {
                    if (resp === 'OK') {
                        Swal.fire({
                            icon: 'success',
                            title: id ? 'Premio actualizado' : 'Premio agregado',
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() => location.reload());
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo guardar el premio'
                        });
                    }
                });
    });

    btnCancelar.addEventListener('click', () => resetForm());

    tablaBody.addEventListener('click', e => {
        if (e.target.closest('.btnEditar')) {
            const btn = e.target.closest('.btnEditar');
            const tr = btn.closest('tr');

            inputId.value = tr.dataset.id;
            inputNombre.value = tr.querySelector('.nombre-premio').textContent.trim();
            inputDescripcion.value = tr.querySelector('.descripcion-premio').textContent.trim();
            inputPuntos.value = tr.querySelector('.puntos-premio').textContent.trim();
            selectTipo.value = tr.querySelector('.tipo-premio').textContent.trim();

            // Obtener src de imagen y mostrar preview
            const img = tr.querySelector('td img');
            if (img) {
                previewImagen.innerHTML = `<img src="${img.src}" alt="Preview" style="max-width: 150px; max-height: 150px; object-fit: contain;"/>`;

                // Extraer solo el nombre del archivo de la URL del src
                try {
                    const url = new URL(img.src, window.location.origin);
                    imagenAntigua = url.searchParams.get('file');
                } catch (error) {
                    // Si falla, asigna null o el src completo para evitar errores
                    imagenAntigua = null;
                }

            } else {
                previewImagen.innerHTML = '';
                imagenAntigua = null;
            }

            btnCancelar.style.display = 'inline-block';
            btnGuardar.innerHTML = `<i class="fas fa-save"></i> Guardar Cambios`;
        }
    });


    resetForm();
});
