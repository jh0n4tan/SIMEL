document.getElementById('selectCursoLogros').addEventListener('change', async function () {
    const selectCurso = this;
    const idCurso = selectCurso.value;

    const selectedOption = selectCurso.options[selectCurso.selectedIndex];
    const grado = selectedOption.getAttribute('data-grado');
    const seccion = selectedOption.getAttribute('data-seccion');
    const idGradoSeccion = selectedOption.getAttribute('data-idgradoseccion');

    // Guardar el id real de grado_seccion en el input hidden
    document.getElementById('idGradoSeccion').value = idGradoSeccion;

    const selectAlumno = document.getElementById('selectAlumno');
    selectAlumno.innerHTML = '<option value="" selected disabled>-- Selecciona un alumno --</option>';

    try {
        const response = await fetch(`/SIMEL_2_0/obtenerAlumnos?idCurso=${idCurso}&grado=${grado}&seccion=${seccion}`);
        if (!response.ok) throw new Error('Error en la respuesta del servidor');
        const alumnos = await response.json();

        if (alumnos.length > 0) {
            alumnos.forEach(alumno => {
                const option = document.createElement('option');
                option.value = alumno.id;
                option.textContent = alumno.nombre;
                selectAlumno.appendChild(option);
            });
            selectAlumno.disabled = false;
        } else {
            selectAlumno.innerHTML = '<option value="" selected disabled>-- No hay alumnos disponibles --</option>';
            selectAlumno.disabled = true;
        }

    } catch (error) {
        console.error('Error al obtener alumnos:', error);
        Swal.fire('Error', 'No se pudieron cargar los alumnos del curso.', 'error');
        selectAlumno.disabled = true;
    }
});

function asignarLogro(event) {
    event.preventDefault();

    const selectCurso = document.getElementById('selectCursoLogros');
    const selectAlumno = document.getElementById('selectAlumno');
    const selectTipoLogro = document.getElementById('selectTipoLogro');
    const inputPuntos = document.getElementById('puntos');
    const inputComentario = document.getElementById('comentario');
    const idGradoSeccion = document.getElementById('idGradoSeccion').value;

    // Validaciones básicas
    if (!selectCurso.value) {
        return Swal.fire({ icon: 'warning', title: 'Falta seleccionar curso', text: 'Por favor selecciona un curso.' });
    }
    if (!selectAlumno.value) {
        return Swal.fire({ icon: 'warning', title: 'Falta seleccionar alumno', text: 'Por favor selecciona un alumno.' });
    }
    if (!selectTipoLogro.value) {
        return Swal.fire({ icon: 'warning', title: 'Falta seleccionar tipo de logro', text: 'Por favor selecciona un tipo de logro.' });
    }

    const puntos = parseInt(inputPuntos.value);
    if (isNaN(puntos) || puntos < 1 || puntos > 100) {
        return Swal.fire({ icon: 'error', title: 'Puntos inválidos', text: 'Los puntos deben estar entre 1 y 100.' });
    }

    const comentario = inputComentario.value.trim();

    // Confirmación antes de asignar
    Swal.fire({
        title: '¿Deseas asignar este logro?',
        html: `<b>Curso:</b> ${selectCurso.options[selectCurso.selectedIndex].text}<br>
               <b>Alumno:</b> ${selectAlumno.options[selectAlumno.selectedIndex].text}<br>
               <b>Logro:</b> ${selectTipoLogro.options[selectTipoLogro.selectedIndex].text}<br>
               <b>Puntos:</b> ${puntos}<br>
               <b>Comentario:</b> ${comentario || 'Ninguno'}`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Sí, asignar',
        cancelButtonText: 'Cancelar'
    }).then(result => {
        if (result.isConfirmed) {
            const datos = new URLSearchParams();
            datos.append('idAlumno', selectAlumno.value);
            datos.append('idCurso', selectCurso.value);
            datos.append('idGradoSeccion', idGradoSeccion);
            datos.append('idLogro', selectTipoLogro.value);
            datos.append('puntos', puntos);
            datos.append('comentario', comentario);

            fetch('/SIMEL_2_0/AsignarLogroServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: datos.toString()
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        Swal.fire({
                            icon: 'success',
                            title: data.msg || 'Logro asignado correctamente',
                            timer: 1500,
                            showConfirmButton: false
                        }).then(() => {
                            location.reload();
                        });
                        document.getElementById('formLogro').reset();
                        selectAlumno.disabled = true;
                    } else {
                        // Mostrar error del servidor, ej: logro ya asignado
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: data.msg || 'No se pudo asignar el logro. Intenta de nuevo.'
                        });
                    }
                })
                .catch(error => {
                    console.error('Error en fetch:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de red',
                        text: 'No se pudo conectar con el servidor.'
                    });
                });
        }
    });

    return false;
}
