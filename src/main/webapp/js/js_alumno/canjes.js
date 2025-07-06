// Obtener los puntos del usuario desde el atributo data-puntos
const datosUsuario = document.getElementById("datosUsuario");
let puntosUsuario = parseInt(datosUsuario?.dataset?.puntos || "0");

function generarCodigo() {
    const caracteres = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let codigo = '';
    for (let i = 0; i < 8; i++) {
        codigo += caracteres.charAt(Math.floor(Math.random() * caracteres.length));
    }
    return codigo;
}

function canjearPremio(idPremio, nombrePremio, puntosNecesarios, tipoPremio) {
    if (puntosUsuario >= puntosNecesarios) {
        Swal.fire({
            title: `¿Deseas canjear "${nombrePremio}"?`,
            html: `Este premio cuesta <strong>${puntosNecesarios} puntos</strong>.<br>Tipo de premio: <strong>${tipoPremio.toUpperCase()}</strong>`,
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí, canjear',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const codigo = generarCodigo();
                const estadoCanje = tipoPremio === 'digital' ? 'Canje digital exitoso' : 'Canje físico solicitado';

                fetch('guardarCanje', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        idPremio: idPremio,
                        codigo: codigo,
                        estado: estadoCanje,
                        puntosPremio: puntosNecesarios
                    })
                })
                .then(res => res.json())
                .then(data => {
                    if (data.success) {
                        Swal.fire({
                            title: estadoCanje,
                            html: `Has canjeado <strong>${nombrePremio}</strong>.<br><strong>Código de canje: ${codigo}</strong>.<br><br>
                            Guarda este código para canjearlo más tarde.`,
                            icon: 'success',
                            confirmButtonText: 'OK'
                        }).then(() => {
                            location.reload(); // Recarga la página
                        });
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: data.message || 'No se pudo registrar el canje.',
                            icon: 'error'
                        });
                    }
                })
                .catch(err => {
                    console.error("Error al registrar canje:", err);
                    Swal.fire({
                        title: 'Error',
                        text: 'Error al comunicar con el servidor.',
                        icon: 'error'
                    });
                });
            }
        });
    } else {
        Swal.fire({
            title: 'Puntos insuficientes',
            text: `Necesitas ${puntosNecesarios} puntos. Solo tienes ${puntosUsuario}.`,
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }
}

document.getElementById('filtroPuntos').addEventListener('change', function () {
    const filtro = this.value;
    const tarjetas = document.querySelectorAll('#catalogoPremios .col');
    let visibles = 0;

    tarjetas.forEach((tarjeta) => {
        const textoPuntos = tarjeta.querySelector('.puntos-requeridos').innerText;
        const puntosNecesarios = parseInt(textoPuntos.match(/\d+/)[0]);

        if (filtro === 'disponibles' && puntosUsuario < puntosNecesarios) {
            tarjeta.style.display = 'none';
        } else {
            tarjeta.style.display = 'block';
            visibles++;
        }
    });

    let catalogoPremios = document.getElementById('catalogoPremios');
    let mensajeNoPremios = document.getElementById('mensajeNoPremios');

    if (!mensajeNoPremios) {
        mensajeNoPremios = document.createElement('div');
        mensajeNoPremios.id = 'mensajeNoPremios';
        mensajeNoPremios.className = 'alert alert-info mt-3 text-center';
        mensajeNoPremios.innerText = 'No tienes premios disponibles para canjear.';
        catalogoPremios.parentNode.insertBefore(mensajeNoPremios, catalogoPremios.nextSibling);
    }

    if (filtro === 'disponibles' && visibles === 0) {
        mensajeNoPremios.style.display = 'block';
    } else {
        mensajeNoPremios.style.display = 'none';
    }
});
