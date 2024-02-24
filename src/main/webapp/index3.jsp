<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/loading.css">
        <link rel="shortcut icon" href="img/logo.png" type="image/x-icon">
        <title>Tarjetas</title>        
    </head>
    <body>
        <div class="contenedor">
            <h2 role="status">Cargando la aplicación...</h2>
        <div class="contenedor">                        
            <div class="spinner-border" role="status" style="width: 5rem; height: 5rem;" > </div>                                       
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var servletURL = '/tarjetas/ControladorMenu?accion=iniciar';

                // Realizar la petición al servlet usando Fetch API
                fetch(servletURL)
                        .then(function (response) {
                            if (!response.ok) {
                                throw new Error('Error al iniciar la aplicación');
                            }
                            return response.text();
                        })                       
                        .catch(function (error) {
                            console.error('Error al iniciar la aplicación:', error);
                            // Manejar el error aquí, por ejemplo, mostrar un mensaje de error al usuario
                        });
            });
        </script>
    </body>
</html>
