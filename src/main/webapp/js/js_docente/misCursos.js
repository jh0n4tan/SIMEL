document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.btn-ver-alumnos').forEach(button => {
        button.addEventListener('click', () => {
            const curso = button.getAttribute('data-curso');
            const grado = button.getAttribute('data-grado').replace('°', '');
            const seccion = button.getAttribute('data-seccion');
            const idCursoTexto = button.parentElement.parentElement.children[0].textContent; // Ej: "C001"
            const idCurso = parseInt(idCursoTexto.replace('C', ''));

            fetch(`obtenerAlumnos?idCurso=${idCurso}&grado=${grado}&seccion=${seccion}`)
                    .then(response => response.json())
                    .then(alumnos => {
                        let htmlAlumnos = '<ul class="list-group text-left">';
                        alumnos.forEach(a => {
                            htmlAlumnos += `<li class="list-group-item">${a.nombre}</li>`;
                        });
                        htmlAlumnos += '</ul>';

                        Swal.fire({
                            title: `👨‍🏫 Alumnos de ${curso} (${grado}° ${seccion})`,
                            html: htmlAlumnos,
                            icon: 'info',
                            confirmButtonText: 'Cerrar',
                            customClass: {
                                popup: 'text-left'
                            }
                        });
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        Swal.fire('Error', 'No se pudieron cargar los alumnos.', 'error');
                    });
        });
    });
});
