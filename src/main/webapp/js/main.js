$(document).ready(function () {
    tablaPersonas = $("#tablaPersonas").DataTable({
        "columnDefs": [{
                "targets": -1,
                "data": null,
                "defaultContent": "<div class='text-center'><div class='btn-group'><button class='btn btn-primary btnEditar'>Editar</button><button class='btn btn-danger btnBorrar'>Inactivar</button></div></div>"
            }],
        "pageLength": 5, // 👈 Muestra 5 filas por defecto al cargar
        "lengthMenu": [[5, 15, 25, 50, 100], [5, 15, 25, 50, 100]], // 👈 Selector personalizado
        "language": {
            "lengthMenu": "Mostrar _MENU_ registros",
            "zeroRecords": "No se encontraron resultados",
            "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
            "infoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sSearch": "Buscar:",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "sProcessing": "Procesando...",
        }
    });
    $("#btnNuevo").click(function () {
        $("#formPersonas").trigger("reset");
        $("#modalCRUD .modal-header").css("background-color", "#28a745");
        $("#modalCRUD .modal-header").css("color", "white");
        $("#modalCRUD .modal-title").text("Nuevo Usuario");
        $("#modalCRUD").modal("show");
        id = null;
        opcion = 1; //alta
    });
    var fila; //capturar la fila para editar o borrar el registro

// Botón EDITAR     
    $(document).on("click", ".btnEditar", function () {
        fila = $(this).closest("tr");  // Captura la fila seleccionada
        id = parseInt(fila.find('td:eq(0)').text());  // Obtiene el ID
        nombre = fila.find('td:eq(1)').text();  // Obtiene el nombre
        usuario = fila.find('td:eq(2)').text();  // Obtiene el usuario

        // Obtiene cargo y estado
        cargo = fila.find('td:eq(3)').text();  // Asegúrate de que cargo sea el valor que esperas
        estado = fila.find('td:eq(4)').text();  // Asegúrate de que estado sea el valor que esperas

        // Verifica que los valores sean correctos
        console.log('Cargo:', cargo);  // Asegúrate de que cargo tenga un valor válido
        console.log('Estado:', estado);  // Asegúrate de que estado tenga un valor válido

        // Asigna los valores al formulario en el modal
        $("#id").val(id);  // Asigna el ID
        $("#nombre").val(nombre);  // Asigna el nombre
        $("#usuario").val(usuario);  // Asigna el usuario

        // Limpiar el campo de contraseña y dejarlo en formato password
        $("#password").val("");  // Deja la contraseña vacía
        $("#password").attr("type", "password");  // Asegura que el tipo sea "password"
        $("#password").attr("placeholder", "Dejar vacío para no cambiar");  // Placeholder claro

        // Asigna el valor de "cargo" al select
        $("#cargo").val(cargo.trim());  // Aquí cargo debe ser un valor que coincida con el value de una de las opciones
        $("#estado").val(estado.trim());  // Asigna el valor de estado al select en el modal

        opcion = 2;  // Indica que estamos editando

        // Cambia el estilo del modal para editar
        $("#modalCRUD .modal-header").css("background-color", "#007bff");
        $("#modalCRUD .modal-header").css("color", "white");
        $("#modalCRUD .modal-title").text("Editar Usuario");

        // Muestra el modal
        $("#modalCRUD").modal("show");
    });


    // Inactivar usuario (eliminación lógica)
    $(document).on("click", ".btnBorrar", function () {
        fila = $(this);
        id = parseInt($(this).closest("tr").find('td:eq(0)').text());
        Swal.fire({
            title: '¿Estás seguro?',
            text: 'Esta acción inactivará al usuario.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, inactivar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "inactivarUsuario",
                    type: "POST",
                    data: {id: id},
                    success: function (respuesta) {
                        if (respuesta === "OK") {
                            Swal.fire({
                                icon: 'success',
                                title: 'Inactivado',
                                text: 'El usuario fue inactivado correctamente.',
                                confirmButtonColor: '#d33'
                            }).then(() => {
                                location.reload();
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'No se pudo inactivar el usuario.',
                                confirmButtonColor: '#dc3545'
                            });
                        }
                    },
                    error: function () {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error de red',
                            text: 'No se pudo completar la solicitud.',
                            confirmButtonColor: '#dc3545'
                        });
                    }
                });
            }
        });
    });

// Front para registrar un nuevo usuario o editar un usuario existente
    $("#formPersonas").submit(function (e) {
        e.preventDefault();
        var id = $("#id").val(); // Necesario solo para editar
        var nombre = $("#nombre").val();
        var usuario = $("#usuario").val();
        var password = $("#password").val();
        var cargo = $("#cargo").val();
        var estado = $("#estado").val();

        if (opcion === 1) {
            // Alta: Registrar un nuevo usuario
            $.ajax({
                url: "agregarUsuario",
                type: "POST",
                data: {
                    nombre: nombre,
                    usuario: usuario,
                    password: password,
                    cargo: cargo,
                    estado: estado
                },
                success: function (respuesta) {
                    if (respuesta === "OK") {
                        $("#modalCRUD").modal("hide");
                        Swal.fire({
                            icon: 'success',
                            title: 'Usuario agregado',
                            text: 'El usuario fue registrado correctamente.',
                            confirmButtonColor: '#3085d6'
                        }).then(() => {
                            location.reload();
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo guardar el usuario.',
                            confirmButtonColor: '#dc3545'
                        });
                    }
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de red',
                        text: 'No se pudo completar la solicitud.',
                        confirmButtonColor: '#dc3545'
                    });
                }
            });
        } else if (opcion === 2) {
            // Edición de usuario
            var dataToSend = {
                id: id,
                nombre: nombre,
                usuario: usuario,
                password: password,
                cargo: cargo,
                estado: estado
            };

            // Si la contraseña no está vacía, se añade a la petición
            if (password.trim() !== "") {
                dataToSend.password = password;
            }

            $.ajax({
                url: "editarUsuario",
                type: "POST",
                data: dataToSend,
                success: function (respuesta) {
                    if (respuesta === "OK") {
                        $("#modalCRUD").modal("hide");
                        Swal.fire({
                            icon: 'success',
                            title: 'Usuario actualizado',
                            text: 'Los datos del usuario fueron actualizados.',
                            confirmButtonColor: '#007bff'
                        }).then(() => {
                            location.reload();
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo editar el usuario.',
                            confirmButtonColor: '#dc3545'
                        });
                    }
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de red',
                        text: 'No se pudo completar la solicitud.',
                        confirmButtonColor: '#dc3545'
                    });
                }
            });
        }
    });


    // Función para generar el nombre de usuario automáticamente
    $("#nombre").on("keyup", function () {
        var nombreCompleto = $(this).val(); // Obtiene el valor del campo "Nombre"

        // Separa el nombre y el apellido
        var partesNombre = nombreCompleto.split(" ");
        // Si el nombre tiene al menos dos partes (nombre y apellido)
        if (partesNombre.length >= 2) {
            var primerNombre = partesNombre[0].toLowerCase(); // Primer nombre en minúsculas
            var primerApellido = partesNombre[1].toLowerCase(); // Primer apellido en minúsculas

            // Generar el nombre de usuario (inicial del primer nombre + apellido)
            var usuarioGenerado = primerNombre.charAt(0) + primerApellido;
            $("#usuario").val(usuarioGenerado); // Coloca el usuario generado en el campo "Usuario"
        }
    });

    $('#modalCRUD').on('hidden.bs.modal', function () {
        $("#modalCRUD .modal-header")
                .removeAttr("style") // Elimina estilos inline
                .removeClass("bg-success bg-primary bg-danger text-white text-dark"); // Elimina clases previas si las usas
        $("#modalCRUD .modal-title").text("");
    });
});
