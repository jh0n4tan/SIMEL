document.getElementById('selectCurso').addEventListener('change', async function () {
    const selected = this.value; // Ej: "1-4-A"
    const [idCurso, gradoStr, seccion] = selected.split('-');
    const grado = parseInt(gradoStr);

    try {
        const [alumnosResp, notasResp] = await Promise.all([
            fetch(`/SIMEL_2_0/obtenerAlumnos?idCurso=${idCurso}&grado=${grado}&seccion=${seccion}`),
            fetch(`/SIMEL_2_0/obtenerNotasEvaluacion?idCurso=${idCurso}&grado=${grado}&seccion=${seccion}`)
        ]);

        const alumnos = await alumnosResp.json();
        const notas = await notasResp.json();
        mostrarAlumnos(alumnos, notas);
    } catch (error) {
        console.error('Error al cargar datos:', error);
        Swal.fire('Error', 'No se pudieron cargar los alumnos o notas del servidor.', 'error');
    }
});

function mostrarAlumnos(alumnos, notas) {
    const notasMap = {};
    notas.forEach(n => notasMap[n.idAlumno] = n);

    let html = '';
    alumnos.forEach((alumno, index) => {
        const nota = notasMap[alumno.id] || {};
        html += `
            <tr data-id="${alumno.id}">
                <td>${alumno.nombre}</td>
                <td><input type="number" class="form-control nota-input" data-row="${index}" data-col="1" value="${nota.nota1 ?? ''}" min="0" max="20"></td>
                <td><input type="number" class="form-control nota-input" data-row="${index}" data-col="2" value="${nota.nota2 ?? ''}" min="0" max="20"></td>
                <td><input type="number" class="form-control nota-input" data-row="${index}" data-col="3" value="${nota.nota3 ?? ''}" min="0" max="20"></td>
                <td><input type="text" class="form-control promedio-input" id="promedio-${index}" value="${nota.promedio ?? '--'}" readonly></td>
                <td><input type="text" class="form-control comentario-input" data-row="${index}" value="${nota.comentario ?? ''}"></td>
            </tr>`;
    });

    document.getElementById('tablaNotasBody').innerHTML = html;
    document.getElementById('tablaNotasContainer').style.display = 'block';

    // Listeners para calcular promedio en tiempo real
    document.querySelectorAll('.nota-input').forEach(input => {
        input.addEventListener('input', function () {
            validarNotaInput(this);
            calcularPromedioPorFila.call(this);
        });
    });
}

function validarNotaInput(input) {
    const val = parseFloat(input.value);
    if (isNaN(val) || val < 0 || val > 20) {
        input.classList.add('is-invalid');
        input.classList.remove('is-valid');
    } else {
        input.classList.remove('is-invalid');
        input.classList.add('is-valid');
    }
}

function calcularPromedioPorFila() {
    const row = this.dataset.row;
    const notas = document.querySelectorAll(`input.nota-input[data-row="${row}"]`);
    let suma = 0;
    let completas = 0;
    let hayError = false;

    notas.forEach(input => {
        const val = parseFloat(input.value);
        if (isNaN(val) || val < 0 || val > 20) {
            hayError = true;
        } else {
            suma += val;
            completas++;
        }
    });

    const promedioInput = document.getElementById(`promedio-${row}`);
    promedioInput.value = (!hayError && completas === 3) ? (suma / 3).toFixed(2) : '--';

    if (!hayError && completas === 3) {
        promedioInput.classList.remove('is-invalid');
        promedioInput.classList.add('is-valid');
    } else {
        promedioInput.classList.remove('is-valid');
        promedioInput.classList.add('is-invalid');
    }
}

function guardarNotas() {
    const select = document.getElementById('selectCurso');
    const [idCurso, gradoStr, seccion] = select.value.split('-');
    const grado = parseInt(gradoStr);

    const filas = document.querySelectorAll('#tablaNotasBody tr');
    const datos = [];
    let errores = false;

    filas.forEach((fila, index) => {
        const idAlumno = fila.getAttribute('data-id');
        const notasInputs = fila.querySelectorAll('.nota-input');
        const comentarioInput = fila.querySelector('.comentario-input');

        const nota1 = notasInputs[0].value.trim();
        const nota2 = notasInputs[1].value.trim();
        const nota3 = notasInputs[2].value.trim();
        const comentario = comentarioInput.value.trim();

        const filaVacia = [nota1, nota2, nota3].every(n => n === '') && comentario === '';
        if (filaVacia) return;

        const n1 = parseFloat(nota1);
        const n2 = parseFloat(nota2);
        const n3 = parseFloat(nota3);
        if ([n1, n2, n3].some(n => isNaN(n) || n < 0 || n > 20)) {
            errores = true;
            notasInputs.forEach(input => input.classList.add('is-invalid'));
            return;
        }

        if (comentario === '') {
            errores = true;
            comentarioInput.classList.add('is-invalid');
            return;
        }

        datos.push({
            idAlumno: parseInt(idAlumno),
            nota1: n1,
            nota2: n2,
            nota3: n3,
            comentario: comentario
        });
    });

    if (datos.length === 0) {
        Swal.fire('Atención', 'Debes ingresar al menos una fila con notas y comentario.', 'warning');
        return;
    }

    if (errores) {
        Swal.fire('Error', 'Corrige los errores antes de guardar.', 'error');
        return;
    }

    // Confirmar antes de enviar
    Swal.fire({
        title: '¿Guardar todas las notas ingresadas?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Sí, guardar',
        cancelButtonText: 'Cancelar'
    }).then(result => {
        if (result.isConfirmed) {
            fetch('/SIMEL_2_0/guardarNotasEvaluacion', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    idCurso: parseInt(idCurso),
                    grado: grado,
                    seccion: seccion,
                    alumnos: datos
                })
            })
            .then(res => res.json())
            .then(data => {
                if (data.status === 'ok') {
                    Swal.fire('¡Guardado!', 'Las notas se guardaron correctamente.', 'success');
                } else {
                    throw new Error('Error en el servidor');
                }
            })
            .catch(error => {
                console.error('Error al guardar:', error);
                Swal.fire('Error', 'No se pudieron guardar las notas.', 'error');
            });
        }
    });
}
