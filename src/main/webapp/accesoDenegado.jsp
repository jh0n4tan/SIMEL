<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Acceso Denegado</title>

        <link href="css/accesoDenegado.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="acceso-denegado-page">

        <div class="main-content">
            <div class="container">
                <div class="icon">⛔</div>
                <h1>Acceso Denegado</h1>
                <p>
                    Su sesión actual no es válida o ha intentado acceder a una sección del sistema que no corresponde con su perfil de usuario. 
                    Por motivos de seguridad y cumplimiento con las buenas prácticas definidas por normas como la <strong>ISO/IEC 27001</strong>, 
                    cada usuario debe operar únicamente dentro de los permisos asignados a su rol correspondiente.
                </p>
                <p>
                    Esta política garantiza la confidencialidad, integridad y disponibilidad de la información del sistema. 
                    El acceso indebido puede generar alertas, auditorías o bloqueos de sesión. 
                    Le recomendamos no intentar ingresar a rutas no autorizadas. Si considera que esto es un error, 
                    contacte al administrador del sistema para mayor orientación.
                </p>
                <a href="index.jsp" class="button">Volver al inicio</a>
            </div>
        </div>
    </body>
</html>
