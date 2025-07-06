document.addEventListener("DOMContentLoaded", function () {
    const formDocente = document.getElementById("form-asignar-docente");
    const formAlumno = document.getElementById("form-asignar-alumno");
    const historialList = document.getElementById("alumnos-por-seccion");

    // 👉 Verificamos que exista el formulario del docente antes de agregarle eventos
    if (formDocente) {
        formDocente.addEventListener("submit", function (e) {
            e.preventDefault();

            const formData = new FormData(formDocente);
            formData.append("accion", "asignarCursoDocente");

            fetch("administrador", {
                method: "POST",
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: 'Asignado',
                        text: 'Curso asignado correctamente.'
                    }).then(() => location.reload());
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: data.message
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

    // 👉 Verificamos que exista el formulario del alumno antes de agregarle eventos
    if (formAlumno && historialList) {
        formAlumno.addEventListener("submit", function (e) {
            e.preventDefault();
            const formData = new FormData(formAlumno);
            const idAlumno = formData.get("id_alumno");
            const cursosManual = formData.getAll("cursos_manual");

            if (!idAlumno) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Ups...',
                    text: 'Por favor selecciona un alumno.'
                });
                return;
            }

            if (cursosManual.length === 0) {
                Swal.fire({
                    icon: 'info',
                    title: 'Sin selección',
                    text: 'Selecciona al menos un curso para actualizar.'
                });
                return;
            }

            cursosManual.forEach(cursoId => {
                const label = formAlumno.querySelector(`input[value="${cursoId}"]`).nextElementSibling.textContent.trim();
                const yaExiste = Array.from(historialList.children).some(li => li.textContent.includes(label));
                if (!yaExiste) {
                    const li = document.createElement("li");
                    li.className = "list-group-item";
                    li.textContent = `2024 - ${label} - Asignado manualmente`;
                    historialList.appendChild(li);
                }
            });

            Swal.fire({
                icon: 'success',
                title: 'Actualizado',
                text: 'Cursos del alumno actualizados correctamente.'
            });
        });
    }
});
