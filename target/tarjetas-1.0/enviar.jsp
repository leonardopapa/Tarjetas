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
        <script src="https://kit.fontawesome.com/842280e38e.js" crossorigin="anonymous"></script>
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

                        <form action="ControladorRemito" method="POST" id="frmEnviar">
                            <div class="row">
                                <div class="col">

                                    <div class="card" >

                                        <div class="card-body">

                                            <div class="row">
                                                <label for="selCorreo" class="col-form-label">Correo:</label>
                                                <div class="col">
                                                    <select class="form-select" id="selCorreo" name="correo">
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
                                                    <input type="date" class="form-control" id="inputFechaEnvio" name="fenvio">
                                                </div>
                                            </div>

                                            <form action="ControladorBuscar" method="get" name="addCuenta" id="addCuenta">

                                                <div class="row">
                                                    <label for="inputCuenta" class="col-form-label">Ingreso Manual:</label>
                                                    <div class="col">
                                                        <input type="text" class="form-control" name="inputCuenta" id="inputCuenta" placeholder="Ingrese el número de cuenta">

                                                    </div>
                                                </div>
                                                <br>
                                                <button type="button" class="btn btn-danger" onclick="agregarFila();">Agregar</button>

                                            </form>

                                            <br>

                                        </div>
                                    </div>

                                </div>

                                <div class="col">

                                    <div class="card"">                                
                                        <table id="tblTarjetas" class="table" style="width:100%">
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

                                    <input type="hidden" name="accion" value="remito">

                                    <button type="button" class="btn btn-danger" onclick="generarRemito();">Generar Remito</button>

                                    <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>

                                </div>

                            </div>

                        </form>

                    </div>

                </div>

            </section>

        </div>

        <script>

            window.onload = iniciar;
            function iniciar() {
                var fechaActual = new Date();
                // Formatear la fecha como DD/MM/AAAA
                var formatoFecha = fechaActual.toISOString().split('T')[0];
                // Obtener el elemento de entrada de texto por su ID
                var inputFecha = document.getElementById('inputFechaEnvio');
                // Establecer el valor por defecto del input text como la fecha formateada
                inputFecha.value = formatoFecha;
            }


            function agregarFila() {
                // Obtener los valores de los input
                var cuenta = document.getElementById("inputCuenta").value;

                // Validar que los campos no estén vacíos
                if (cuenta === '') {
                    alert('Por favor, ingrese una cuenta.');
                    return;
                }

                // Validar el formato de la cuenta(número de 6 dígitos)
                var cuentaRegex = /^\d{6}$/;

                if (!cuenta.match(cuentaRegex)) {
                    alert('Número de cuenta no válido. Debe ser un número de 6 dígitos.');
                    return;
                }

                // Verificar que la cuenta esté en estado "Impresa"

                var fechaEmision = "";
                console.log("Iniciando llamada Http - cuenta:" + cuenta);
                var http = new XMLHttpRequest();
                url = 'ControladorBuscar?inputCuenta=' + cuenta;
                http.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        fechaEmision = this.responseText.trim();
                        console.log("Response text:[" + fechaEmision + "]");
                        if (fechaEmision === "no encontrado") {
                            alert("No existe una tarjeta en estado Impresa con ese número de cuenta");
                            return;
                        }

                        // Obtener la referencia de la tabla
                        var tabla = document.getElementById("tblTarjetas");
                        // Obtener la referencia al tbody
                        var tbody = tabla.getElementsByTagName("tbody")[0];
                        // Crear una nueva fila
                        var fila = tbody.insertRow();
                        fila.id = "fila" + cuenta;

                        // Insertar celdas con los valores de los input
                        var celdaCuenta = fila.insertCell(0);                        
                        celdaCuenta.innerHTML = '<input type="text" class="form-control-plaintext" name="cuenta' + cuenta + '" value="' + cuenta + '" readonly>';

                        var celdaFecha = fila.insertCell(1);
                        var fechaFormateada = new (Date);
                        fechaFormateada = formatearFecha2(fechaEmision);
                        celdaFecha.innerHTML = '<input type="text" class="form-control-plaintext" name="fecha' + cuenta + '" value="' + fechaFormateada + '" readonly>';

                        // Agregar un icono de cesto de basura y asociar un evento de clic para eliminar la fila
                        var celdaEliminar = fila.insertCell(2);
                        celdaEliminar.innerHTML = '<button class="btn" onclick="eliminarFila(' + cuenta + ')"><i class="fas fa-trash-alt"></i></button>';

                        // Limpiar los valores de los input
                        document.getElementById("inputCuenta").value = '';
                        var enfocar = document.getElementById("inputCuenta");
                        enfocar.focus();
                    }
                };
                http.open('get', url);
                http.send();
            }

            function generarRemito() {
                // Validar Fecha de Envío
                var fecha = document.getElementById("inputFechaEnvio").value;
                var correo = document.getElementById("selCorreo").value;

                // Validar que los campos no estén vacíos
                if (fecha === '' || correo === '') {
                    alert('Por favor, ingrese fecha de envío y correo.');
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

                // Cambiar tarjetas a estado enviada
                formularioEnviar = document.getElementById("frmEnviar");
                console.log("Todos los controles OK");
                formularioEnviar.submit();
            }

            function formatearFecha2(fecha) {
                // Suponiendo que la fecha es ingresada en el formato aa/mm/dd
                var partes = fecha.split('-');
                var anio = partes[0];
                var mes = partes[1];
                var dia = partes[2];

                // Formatear a dd/mm/aa
                return dia + '/' + mes + '/' + anio;
            }

            function eliminarFila(indiceFila) {
                var confirmacion = confirm("¿Está seguro de que desea eliminar la cuenta " + indiceFila + "?");
                if (confirmacion) {
                    var row = document.getElementById("fila" + indiceFila);
                    row.remove();
                }
            }

            function cancelar() {
                var confirmacion = confirm("¿Está seguro de que desea salir?");
                if (confirmacion) {
                    window.location.href = "index.jsp";
                }
            }


        </script>

    </body>

</html>