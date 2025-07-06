// Función para obtener la tabla activa según la pestaña seleccionada
function obtenerTablaActiva() {
    // El contenedor de pestañas
    const tabsContent = document.getElementById('reportesTabsContent');
    // Encontrar el div con clase "tab-pane" y clase "show active"
    const tabActivo = tabsContent.querySelector('.tab-pane.show.active');
    if (!tabActivo) return null;
    // Buscar la tabla dentro de esa pestaña
    return tabActivo.querySelector('table');
}

// Exportar Excel
document.getElementById('btn-exportar-excel').addEventListener('click', () => {
    const tabla = obtenerTablaActiva();
    if (!tabla) {
        Swal.fire({
            icon: 'warning',
            title: 'No hay tabla visible',
            text: 'Por favor selecciona una pestaña con datos.'
        });
        return;
    }
    const wb = XLSX.utils.table_to_book(tabla, { sheet: "Reporte" });
    // Puedes personalizar el nombre del archivo según la pestaña
    const nombreArchivo = tabla.id === 'tablaRanking' ? 'ranking_alumnos.xlsx' : 'canjes_realizados.xlsx';
    XLSX.writeFile(wb, nombreArchivo);

    Swal.fire({
        icon: 'success',
        title: 'Exportación exitosa',
        text: `Se ha descargado el archivo ${nombreArchivo}.`,
        timer: 1500,
        showConfirmButton: false
    });
});

// Generar PDF
document.getElementById('btn-exportar-pdf').addEventListener('click', () => {
    const tabla = obtenerTablaActiva();
    if (!tabla) {
        Swal.fire({
            icon: 'warning',
            title: 'No hay tabla visible',
            text: 'Por favor selecciona una pestaña con datos.'
        });
        return;
    }

    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const pdfWidth = doc.internal.pageSize.getWidth();
    const y = 10;

    // Título según tabla
    let titulo = 'Reporte';
    if (tabla.id === 'tablaRanking') {
        titulo = 'Ranking de Alumnos';
    } else if (tabla.id === 'tablaCanjes') {
        titulo = 'Canjes Realizados';
    }

    doc.setFontSize(16);
    doc.text(titulo, pdfWidth / 2, y, { align: 'center' });

    // Extraer filas y celdas de la tabla
    const filas = [...tabla.querySelectorAll('tbody tr')].map(tr => {
        return [...tr.children].map(td => td.innerText.trim());
    });

    // Para el encabezado tomamos el texto del thead
    const encabezado = [...tabla.querySelectorAll('thead tr th')].map(th => th.innerText.trim());

    doc.autoTable({
        head: [encabezado],
        body: filas,
        startY: y + 10,
        theme: 'grid',
        styles: { halign: 'center' },
        headStyles: { fillColor: [255, 243, 205] }
    });

    const nombreArchivo = tabla.id === 'tablaRanking' ? 'ranking_alumnos.pdf' : 'canjes_realizados.pdf';
    doc.save(nombreArchivo);

    Swal.fire({
        icon: 'success',
        title: 'PDF generado',
        text: `El archivo ${nombreArchivo} se ha descargado correctamente.`,
        timer: 1500,
        showConfirmButton: false
    });
});
