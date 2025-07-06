// Exportar Excel
document.querySelector('.btn-exportar-ranking').addEventListener('click', () => {
    const tabla = document.getElementById('tablaRanking');
    const wb = XLSX.utils.table_to_book(tabla, { sheet: "Ranking" });
    XLSX.writeFile(wb, "ranking_alumnos.xlsx");

    Swal.fire({
        icon: 'success',
        title: 'Exportación exitosa',
        text: 'Se ha descargado el archivo Excel.',
        timer: 1500,
        showConfirmButton: false
    });
});

document.querySelector('.btn-generar-pdf').addEventListener('click', () => {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const pdfWidth = doc.internal.pageSize.getWidth();
    const y = 10;

    doc.setFontSize(16);
    doc.text('Ranking de Alumnos', pdfWidth / 2, y, { align: 'center' });

    const tabla = document.getElementById('tablaRanking');
    const filas = [...tabla.querySelectorAll('tbody tr')].map(tr => {
        const tds = [...tr.children];
        return [
            tds[0].innerText.trim(),
            tds[1].innerText.trim(),
            tds[2].innerText.trim(),
            tds[3].innerText.trim()
        ];
    });

    doc.autoTable({
        head: [['#', 'Nombre del Alumno', 'Total de Puntos', 'Medalla']],
        body: filas,
        startY: y + 10,
        theme: 'grid',
        styles: { halign: 'center' },
        headStyles: { fillColor: [255, 243, 205] }
    });

    doc.save('ranking_alumnos.pdf');

    Swal.fire({
        icon: 'success',
        title: 'PDF generado',
        text: 'El archivo se ha descargado correctamente.',
        timer: 1500,
        showConfirmButton: false
    });
});
