document.addEventListener("DOMContentLoaded", () => {
    // Elementos del DOM
    const selectReporte = document.getElementById("tipo-reporte");
    const contenedorTablaReporte = document.querySelector(".table-responsive");
    const tituloImpresion = document.querySelector(".card-header h5");

    // Botones
    const btnGenerar = document.getElementById("btn-generar-reporte");
    const btnExportarPDF = document.getElementById("btn-exportar-pdf");
    const btnExportarExcel = document.getElementById("btn-exportar-excel");
    const btnImprimir = document.getElementById("btn-imprimir");

    // Eventos
    btnGenerar.addEventListener("click", generarReporte);
    btnExportarPDF.addEventListener("click", exportarPDF);
    btnExportarExcel.addEventListener("click", exportarExcel);

    // Función: Generar contenido según el tipo de reporte
    function generarReporte() {
        const tipo = selectReporte.value;
        let contenidoHTML = "";
        let tituloReporte = "";

        switch (tipo) {
            case "alumnos":
                contenidoHTML = `
        <table class="table table-bordered text-center align-middle">
          <thead class="encabezado-reportes">
            <tr><th>Alumno</th><th>Puntos</th><th>Ranking</th></tr>
          </thead>
          <tbody>
            <tr><td>Juan Pérez</td><td>120</td><td>🥇</td></tr>
            <tr><td>María Gómez</td><td>110</td><td>🥈</td></tr>
            <tr><td>Pedro Sánchez</td><td>105</td><td>🥉</td></tr>
          </tbody>
        </table>`;
                tituloReporte = "🎓 Alumnos - Ranking por Puntos";
                break;

            case "canjes":
                contenidoHTML = `
        <table class="table table-bordered text-center align-middle">
          <thead class="encabezado-reportes">
            <tr><th>Alumno</th><th>Premio</th><th>Estado</th><th>Fecha</th></tr>
          </thead>
          <tbody>
            <tr><td>Juan Pérez</td><td>Chocolate</td><td>Pendiente</td><td>2025-05-14</td></tr>
            <tr><td>María Gómez</td><td>Cuaderno</td><td>Entregado</td><td>2025-05-13</td></tr>
          </tbody>
        </table>`;
                tituloReporte = "🎁 Canjes Realizados";
                break;

            default:
                contenidoHTML = "<p>Seleccione un tipo de reporte válido.</p>";
                tituloReporte = "Reporte no disponible";
        }

        // 💡 Esta línea elimina el mensaje inicial si existe
        document.getElementById("mensajeInicialReporte")?.remove();

        contenedorTablaReporte.innerHTML = contenidoHTML;
        tituloImpresion.textContent = tituloReporte;

        Swal.fire({
            icon: 'success',
            title: '¡Reporte generado!',
            text: 'Puedes exportarlo o imprimirlo si lo deseas.',
            timer: 2000,
            showConfirmButton: false
        });
    }


    // Función: Exportar a PDF
    function exportarPDF() {
        const doc = new jspdf.jsPDF();
        const tituloOriginal = tituloImpresion.textContent;
        const tituloLimpio = tituloOriginal.replace(/[^\x00-\x7F]/g, ""); // quitar emojis

        const tabla = document.querySelector(".table-responsive table");
        if (!tabla) {
            Swal.fire('Error', 'No hay tabla para exportar.', 'error');
            return;
        }

        const encabezados = [];
        const filas = [];

        tabla.querySelectorAll("thead th").forEach(th => encabezados.push(th.textContent));
        tabla.querySelectorAll("tbody tr").forEach(tr => {
            const fila = [];
            tr.querySelectorAll("td").forEach(td => fila.push(td.textContent));
            filas.push(fila);
        });

        doc.text(tituloLimpio, 10, 10);
        doc.autoTable({
            head: [encabezados],
            body: filas,
            startY: 20,
            theme: 'grid'
        });

        doc.save("reporte.pdf");
    }

    // Función: Exportar a Excel
    function exportarExcel() {
        const tabla = document.querySelector(".table-responsive table");
        if (!tabla) {
            Swal.fire('Error', 'No hay tabla para exportar.', 'error');
            return;
        }

        const ws = XLSX.utils.table_to_sheet(tabla);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "Reporte");
        XLSX.writeFile(wb, "reporte.xlsx");
    }

    // Función: Imprimir reporte
    function imprimirReporte() {
        const tabla = document.querySelector(".table-responsive table");
        if (!tabla) {
            Swal.fire('Error', 'No hay tabla para imprimir.', 'error');
            return;
        }

        const contenidoImprimir = `
      <div style="text-align: center; font-size: 24px; font-weight: bold; color: #4e73df;">
        Reportes
      </div>
      <div style="text-align: center; font-size: 20px; margin: 20px 0;">
        ${tituloImpresion.textContent}
      </div>
      ${contenedorTablaReporte.innerHTML}
    `;

        const ventanaImpresion = window.open('', '', 'width=800,height=600');
        ventanaImpresion.document.write(`
      <html>
        <head><title>Imprimir Reporte</title></head>
        <body>
          ${contenidoImprimir}
          <script>
            window.print();
            window.onafterprint = function() { window.close(); };
          <\/script>
        </body>
      </html>
    `);
        ventanaImpresion.document.close();
    }
});
