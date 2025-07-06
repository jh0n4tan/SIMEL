document.addEventListener("DOMContentLoaded", () => {
    // Delegación de evento para botones "Marcar como completado" en la pestaña 'disponibles'
    document.querySelector("#disponibles").addEventListener("click", function (e) {
        if (e.target.matches(".btn-marcar-completado")) {
            const idReto = e.target.dataset.id;
            Swal.fire({
                title: "¿Deseas enviar este reto como completado?",
                text: "El docente revisará tu reto antes de asignarte los puntos.",
                icon: "question",
                showCancelButton: true,
                confirmButtonText: "Sí, enviar",
                cancelButtonText: "Cancelar"
            }).then(result => {
                if (result.isConfirmed) {
                    marcarRetoComoCompletado(idReto);
                }
            });
        }
    });
});

function marcarRetoComoCompletado(idReto) {
    fetch("marcarReto", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            idReto: idReto
        })
    })
    .then(response => {
        if (response.ok) {
            Swal.fire({
                title: "🎉 ¡Reto enviado como pendiente!",
                icon: "success",
                timer: 1500,
                showConfirmButton: false
            }).then(() => {
                location.reload();
            });
        } else if (response.status === 409) {
            Swal.fire("Atención", "Este reto ya fue enviado anteriormente.", "warning");
        } else {
            throw new Error("Error al marcar reto como completado");
        }
    })
    .catch(error => {
        Swal.fire("Error", error.message, "error");
    });
}
