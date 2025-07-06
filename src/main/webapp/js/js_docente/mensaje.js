document.getElementById('selectGrado').addEventListener('change', function () {
    const selected = this.value;
    const [idCurso, gradoStr, seccion] = selected.split('-');
    const grado = parseInt(gradoStr);
    const selectAlumno = document.getElementById('selectAlumnoReto');

    selectAlumno.innerHTML = '<option selected disabled>-- Selecciona un alumno --</option>';
    selectAlumno.disabled = true;

    if (!idCurso || !grado || !seccion)
        return;

    fetch(`/SIMEL_2_0/obtenerAlumnos?idCurso=${idCurso}&grado=${grado}&seccion=${seccion}`)
        .then(response => {
            if (!response.ok)
                throw new Error('Error al obtener alumnos');
            return response.json();
        })
        .then(alumnos => {
            if (!alumnos || alumnos.length === 0) {
                selectAlumno.innerHTML = '<option disabled>No hay alumnos disponibles</option>';
                return;
            }

            alumnos.forEach(alumno => {
                const option = document.createElement('option');
                option.value = alumno.id; // Aquí usas el ID real del alumno
                option.textContent = alumno.nombre;
                selectAlumno.appendChild(option);
            });

            selectAlumno.disabled = false;
        })
        .catch(error => {
            console.error('Error al cargar alumnos:', error);
            Swal.fire('Error', 'No se pudo cargar la lista de alumnos.', 'error');
        });
});


// Al hacer click en mostrar retos, cargamos desde backend
document.getElementById('btnMostrarRetos').addEventListener('click', () => {
    const selectCurso = document.getElementById('selectGrado');
    const selectAlumno = document.getElementById('selectAlumnoReto');

    if (!selectCurso.value || selectCurso.value === '-- Selecciona --') {
        Swal.fire({
            icon: 'warning',
            title: 'Curso no seleccionado',
            text: 'Selecciona un curso antes de continuar.',
            confirmButtonText: 'Entendido'
        });
        return;
    }

    if (!selectAlumno.value || selectAlumno.value === '-- Selecciona un alumno --') {
        Swal.fire({
            icon: 'warning',
            title: 'Alumno no seleccionado',
            text: 'Selecciona un alumno antes de ver los retos.',
            confirmButtonText: 'Entendido'
        });
        return;
    }

    const alumnoId = parseInt(selectAlumno.value);

    fetch(`/SIMEL_2_0/obtenerRetosAlumno?idAlumno=${alumnoId}`)
        .then(response => {
            if (!response.ok) throw new Error('Error al obtener retos');
            return response.json();
        })
        .then(retos => {
            mostrarRetosAlumno(retos, alumnoId);
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire('Error', 'No se pudieron cargar los retos.', 'error');
        });
});

// Renderiza los retos en pantalla
function mostrarRetosAlumno(retos, alumnoId) {
    const contenedor = document.getElementById('contenedorRetos');
    contenedor.innerHTML = '';
    contenedor.style.display = 'block';

    if (!retos || retos.length === 0) {
        contenedor.innerHTML = '<p>No hay retos pendientes para este alumno.</p>';
        return;
    }

    retos.forEach(reto => {
        const retoDiv = document.createElement('div');
        retoDiv.className = 'reto-card';
        retoDiv.innerHTML = `
            <h5>${reto.nombre}</h5>
            <p>${reto.descripcion}</p>
            <p><strong>Puntos:</strong> ${reto.puntos}</p>
            <select class="select-estado" 
                onchange="cambiarEstado(this, ${alumnoId}, ${reto.id})">
                <option value="Pendiente" ${reto.estado.toLowerCase() === 'pendiente' ? 'selected' : ''}>Pendiente</option>
                <option value="Completado" ${reto.estado.toLowerCase() === 'completado' ? 'selected' : ''}>Completado</option>
                <option value="Rechazado" ${reto.estado.toLowerCase() === 'rechazado' ? 'selected' : ''}>Rechazado</option>
            </select>
        `;
        contenedor.appendChild(retoDiv);
    });
}

// Actualiza el estado del reto en backend
function cambiarEstado(selectElement, alumnoId, retoIdAlumno) {
    const nuevoEstado = selectElement.value;

    fetch('/SIMEL_2_0/actualizarEstadoReto', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `idRetoAlumno=${retoIdAlumno}&nuevoEstado=${nuevoEstado}`
    })
    .then(response => {
        if (!response.ok) throw new Error('No se pudo actualizar el estado');
        return response.text();
    })
    .then(mensaje => {
        Swal.fire({
            icon: 'success',
            title: 'Estado actualizado',
            text: mensaje,
            timer: 1500,
            showConfirmButton: false
        });

        // Recarga los retos del alumno para actualizar la lista
        fetch(`/SIMEL_2_0/obtenerRetosAlumno?idAlumno=${alumnoId}`)
            .then(response => response.json())
            .then(retos => mostrarRetosAlumno(retos, alumnoId))
            .catch(error => {
                console.error('Error al recargar retos:', error);
            });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire('Error', 'No se pudo actualizar el estado del reto.', 'error');
    });
}

