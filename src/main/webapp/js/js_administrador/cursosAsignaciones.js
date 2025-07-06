document.addEventListener("DOMContentLoaded", function () {
    inicializarFormularioDocente();
    inicializarFormularioAlumno();
});

function inicializarFormularioDocente() {
    const formDocente = document.getElementById("form-asignar-docente");

    if (!formDocente) return;

    formDocente.addEventListener("submit", function (e) {
        e.preventDefault();

        const idDocente = formDocente.querySelector('[name="id_docente"]')?.value;
        const idCurso = formDocente.querySelector('[name="id_curso"]')?.value;
        const idGradoSeccion = formDocente.querySelector('[name="id_grado_seccion"]')?.value;

        const datos = new URLSearchParams();
        datos.append("id_docente", idDocente);
        datos.append("id_curso", idCurso);
        datos.append("id_grado_seccion", idGradoSeccion);

        fetch("AsignarCursoDocente", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: datos.toString()
        })
        .then(response => response.json())
        .then(data => {
            if (data.mensajeExito) {
                Swal.fire({
                    icon: 'success',
                    title: 'Asignado',
                    text: data.mensajeExito
                }).then(() => location.reload());
            } else if (data.mensajeError) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensajeError
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error inesperado',
                    text: 'Respuesta del servidor no reconocida.'
                });
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de red',
                text: 'Hubo un problema al intentar enviar los datos.'
            });
            console.error("Error:", error);
        });
    });
}

function inicializarFormularioAlumno() {
    const formAlumno = document.getElementById("form-asignar-alumno");

    if (!formAlumno) return;

    formAlumno.addEventListener("submit", function (e) {
        e.preventDefault();

        const idAlumno = formAlumno.querySelector('[name="id_alumno"]')?.value;
        const idGradoSeccion = formAlumno.querySelector('[name="id_grado_seccion"]')?.value;

        if (!idAlumno) {
            Swal.fire({
                icon: 'warning',
                title: 'Ups...',
                text: 'Por favor selecciona un alumno.'
            });
            return;
        }

        if (!idGradoSeccion) {
            Swal.fire({
                icon: 'info',
                title: 'Sin selección',
                text: 'Selecciona un grado/sección para asignar.'
            });
            return;
        }

        const datos = new URLSearchParams();
        datos.append("id_alumno", idAlumno);
        datos.append("id_grado_seccion", idGradoSeccion);

        fetch("AsignarAlumno", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: datos.toString()
        })
        .then(response => response.json())
        .then(data => {
            if (data.mensajeExito) {
                Swal.fire({
                    icon: 'success',
                    title: 'Asignado',
                    text: data.mensajeExito
                }).then(() => location.reload());
            } else if (data.mensajeError) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensajeError
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error inesperado',
                    text: 'Respuesta del servidor no reconocida.'
                });
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de red',
                text: 'Hubo un problema al intentar enviar los datos.'
            });
            console.error("Error:", error);
        });
    });
}
