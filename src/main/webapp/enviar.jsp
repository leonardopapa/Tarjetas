<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/estilos.css">
    <link rel="shortcut icon" href="img/logo.png" type="image/x-icon">
    <script defer src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script defer src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script defer src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <script>
        new DataTable('#tarjetas');
    </script>
    <title>Tarjetas</title>
</head>

<body>

    <div class="contenedor">

        <aside class="menu-lateral">

            <div class="logo">
                <img src="img/logo.png" width="75">
            </div>

            <br>
            <br>
            <br>
            <ul class="menu flex-column mb-auto">
                <li class="item">
                    <a href="index.jsp">
                        Home
                    </a>
                </li>
                <li class="item">
                    <a href="capturar.jsp">
                        Capturar Tarjetas
                    </a>
                </li>
                <li class="item">
                    <a href="enviar.jsp">

                        Enviar por correo
                    </a>
                </li>
                <li class="item">
                    <a href="recibir.jsp">

                        Recibir Rendición
                    </a>
                </li>
                <li class="item">
                    <a href="cambiar.jsp">
                        Cambiar Estado
                    </a>
                </li>
                <li class="item">
                    <a href="consultar.jsp">
                        Consultar Estado
                    </a>
                </li>
                <li class="item">
                    <a href="reportes.jsp">
                        Reportes
                    </a>
                </li>
                <li class="item">
                    <a href="dashboard.jsp">
                        Dashboard
                    </a>
                </li>
            </ul>

        </aside>

        <section class="main">

            <div class="card" style="margin:20px">
                <div class="card-body">
                    <h5 class="card-title">Enviar por Correo</h5>
                    <hr>

                    <div class="alert alert-danger" role="alert">
                        Esperando ingreso de tarjetas...
                    </div>
                    

                    <div class="row">
                        <div class="col">

                            <div class="card" >

                                
                                <div class="card-body">

                                    <div class="row">
                                        <label for="selCorreo" class="col-form-label">Correo:</label>
                                        <div class="col">
                                            <select class="form-select" id="selCorreo">
                                                <option selected>Seleccione el Correo</option>
                                                <option value="29">Servicios Modernos</option>
                                                <option value="32">Dago</option>
                                                <option value="30">Flash</option>
                                                <option value="33">La Veloz</option>
                                                <option value="31">Coprisa</option>
                                              </select>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <label for="inputFechaEnvio" class="col-form-label">Fecha de Enví­o</label>
                                        <div class="col">
                                            <input type="date" class="form-control" id="inputFechaEnvio">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <label for="inputCuenta" class="col-form-label">Ingreso Manual:</label>
                                        <div class="col">
                                            <input type="text" class="form-control" id="inputCuenta" placeholder="Ingrese el número de cuenta">
                                        </div>
                                    </div>


                                    <br>

                                    
                                    <button type="button" class="btn btn-danger">Agregar</button>

                                    <br>

                                </div>
                            </div>

                        </div>
                        <div class="col">
                            <div class="card"">                                
                                <table id="tblTarjetas" class="table table-striped" style="width:100%">
                                <thead>
                                    <tr>

                                        <th>Cuenta</th>
                                        <th>Fecha de Emisión</th>
                                        <th>Acciones</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    

                                </tbody>
                                </table>
                            </div>
                            <br>
                            <button type="button" class="btn btn-danger">Generar Remito</button>

                            <button type="button" class="btn btn-danger">Firmar Remito</button>

                            <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>


                        </div>
                    </div>


                </div>
            </div>

        </section>

    </div>
    
    <script>

            var contadorFilas = 0;


            function agregarFila() {
                // Obtener los valores de los input
                var cuenta = document.getElementById("inputCuenta").value;
                var fecha = document.getElementById("inputFechaEnvio").value;

                // Validar que los campos no estén vacíos
                if (cuenta === '') {
                    alert('Por favor, ingrese una cuneta.');
                    return;
                }

                // Validar el formato de la cuenta(número de 6 dígitos)
                var cuentaRegex = /^\d{6}$/;
                if (!cuenta.match(cuentaRegex)) {
                    alert('Número de cuenta no válido. Debe ser un número de 6 dígitos.');
                    return;
                }

                // Validar la fecha
                var fechaIngresada = new Date(fecha);
                var fechaActual = new Date();

                if (isNaN(fechaIngresada.getTime()) || fechaIngresada > fechaActual) {
                    alert('Fecha no válida. Debe ser una fecha no futura.');
                    return;
                }

                // Validar que la fecha no tenga más de un año atrás
                var unAnioAtras = new Date();
                unAnioAtras.setFullYear(unAnioAtras.getFullYear() - 1);

                if (fechaIngresada < unAnioAtras) {
                    alert('Fecha no válida. Debe ser una fecha dentro del último año.');
                    return;
                }
                
                // Verificar que la cuenta esté en estado "Impresa"
                var http = new XMLHttpRequest();
                url = 'ControladorTarjetas?accion=buscarFechaEmison&cuenta=' + cuenta;
                http.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        console.log(${fechaEmision});
                        var fechaEmision = this.responseText;
                    }
                };
                http.open('get', url);
                http.send();
                
                if (fechaEmison==="no encontrado") {
                    alert("No existe una tarjeta en estado "Impresa" con ese número de cuenta");
                    return;
                }

                // Obtener la referencia de la tabla
                var tabla = document.getElementById("tblTarjetas");

                // Crear una nueva fila
                var fila = tabla.insertRow();
                fila.id = "fila" + contadorFilas;

                // Insertar celdas con los valores de los input
                var celdaCuenta = fila.insertCell(0);
                // celdaCuenta.innerHTML = cuenta;
                celdaCuenta.innerHTML = '<input type="text" name="cuenta' + contadorFilas + '" value="' + cuenta + '" readonly>';

                var celdaFecha = fila.insertCell(1);
                var fechaFormateada = new (Date);
                fechaFormateada = formatearFecha(fechaEmision);
                // celdaFecha.innerHTML = fechaFormateada;
                celdaFecha.innerHTML = '<input type="text" name="fecha' + contadorFilas + '" value="' + fechaFormateada + '" readonly>';

                // Agregar un icono de cesto de basura y asociar un evento de clic para eliminar la fila
                var celdaEliminar = fila.insertCell(2);
                celdaEliminar.innerHTML = '<button onclick="eliminarFila(' + contadorFilas + ')"><i class="fas fa-trash-alt"></i></button>';
                
                // Limpiar los valores de los input
                document.getElementById("inputCuenta").value = '';
                
                contadorFilas++;
            }

            function formatearFecha(fecha) {
                // Suponiendo que la fecha es ingresada en el formato dd/mm/aa
                var partes = fecha.split('-');
                var anio = partes[0];
                var mes = partes[1];
                var dia = partes[2];

                // Formatear a dd/mm/aa
                return dia + '/' + mes + '/' + anio;
            }

            function eliminarFila(indiceFila) {
                var confirmacion = confirm("¿Está seguro de que desea eliminar esta fila?");
                if (confirmacion) {
                    var tabla = document.getElementById("tblTarjetas");
                    tabla.deleteRow(indiceFila + 2); // +2 para tener en cuenta la fila de encabezado
                }
            }

            function cancelar() {
                var confirmacion = confirm("¿Está seguro de que desea cancelar?");
                if (confirmacion) {
                    window.location.href = "index.jsp";
                }
            }


        </script>

</body>

</html>