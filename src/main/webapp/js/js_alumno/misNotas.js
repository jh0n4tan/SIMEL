document.getElementById('selectCursoNota').addEventListener('change', function () {
    const valor = this.value; // ej: "3-1"
    if (!valor)
        return;

    fetch(`notasAlumno?idCurso=${valor}`)
            .then(response => response.json())
            .then(data => {
                if (Object.keys(data).length === 0) {
                    Swal.fire({
                        icon: 'info',
                        title: 'Sin notas',
                        text: 'No hay notas registradas para este curso.',
                        confirmButtonText: 'Aceptar'
                    });
                    document.getElementById('contenidoNotas').style.display = 'none';
                    return;
                }


                // Mostrar tabla con las notas
                document.getElementById('contenidoNotas').style.display = 'block';

                const tablaNotas = document.getElementById('tablaNotas');
                tablaNotas.innerHTML = `
                <tr><td>Evaluación 1</td><td>${data.notaEva1}</td><td>${data.comentario}</td></tr>
                <tr><td>Evaluación 2</td><td>${data.notaEva2}</td><td>${data.comentario}</td></tr>
                <tr><td>Evaluación 3</td><td>${data.notaEva3}</td><td>${data.comentario}</td></tr>
            `;

                document.getElementById('promedio').innerText = data.promedio;

                dibujarGraficoNotas([data.notaEva1, data.notaEva2, data.notaEva3]);
            })
            .catch(error => {
                console.error('Error al obtener las notas:', error);
                alert('Error al cargar las notas.');
            });
});

// Función para dibujar gráfico de línea con estilo suave
function dibujarGraficoNotas(notas) {
    const ctx = document.getElementById('graficoNotas').getContext('2d');
    if (window.chartNotas) {
        window.chartNotas.destroy();
    }

    window.chartNotas = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Evaluación 1', 'Evaluación 2', 'Evaluación 3'],
            datasets: [{
                    label: 'Notas',
                    data: notas,
                    borderColor: '#0d6efd',
                    backgroundColor: '#0d6efd',
                    fill: false,
                    tension: 0.2,
                    pointRadius: 5,
                    pointHoverRadius: 6
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: 'Evolución de notas en el curso',
                    font: {
                        size: 16
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: 22,
                    ticks: {
                        stepSize: 2
                    },
                    title: {
                        display: true,
                        text: 'Nota'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Evaluaciones'
                    }
                }
            }
        }
    });
}
