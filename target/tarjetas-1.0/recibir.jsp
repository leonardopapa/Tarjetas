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
                        <h5 class="card-title">Recibir Rendición</h5>
                        <hr>
                        <div class="alert alert-danger" role="alert">
                            Esperando ingreso de tarjetas...
                        </div>
                        <form action="ControladorTarjetas" method="POST" id="frmRecibir">
                            <div class="row">


                                <div class="col">

                                    <div class="card" >


                                        <div class="card-body">

                                            <div class="row">
                                                <label for="inputPassword" class="col-form-label">Correo:</label>
                                                <div class="col">
                                                    <select class="form-select" name="correo" id="selCorreo">
                                                        <option selected>Seleccione el Correo</option>
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
                                                <label for="inputFechaRendicion" class="col-form-label">Fecha de Rendición:</label>
                                                <div class="col">
                                                    <input type="date" class="form-control" id="inputFechaRendicion" name="frend">
                                                </div>
                                            </div>

                                            <div class="row">
                                                <label for="inputRendicion" class="col-form-label">Número de Rendición:</label>
                                                <div class="col">
                                                    <input type="text" class="form-control" id="inputRendicion" name="nrend">
                                                </div>
                                            </div>

                                            <form action="ControladorBuscar2" method="get" name="addCuenta" id="addCuenta">

                                                <div class="row">
                                                    <label for="inputPassword" class="col-form-label">Ingreso Manual:</label>
                                                    <div class="col">
                                                        <input type="text" class="form-control" id="inputCuenta" placeholder="Ingrese el número de cuenta">
                                                    </div>
                                                </div>

                                                <br>

                                                <button type="button" class="btn btn-danger" onclick="agregarFila();">Agregar</button>
                                            </form>

                                        </div>
                                    </div>

                                </div>
                                <div class="col">
                                    <div class="card"">                                
                                        <table id="tblTarjetas" class="table table-striped" style="width:100%">
                                            <thead>
                                                <tr>
                                                    <th>Cuenta</th>
                                                    <th>Resultado</th>
                                                    <th>Motivo</th>
                                                    <th>Acciones</th>

                                                </tr>
                                            </thead>
                                            <tbody>

                                            </tbody>
                                        </table>
                                    </div>
                                    <br>

                                    <input type="hidden" name="accion" value="recibir">

                                    <button type="button" class="btn btn-danger" onclick="recibir();">Confirmar Recepción</button>

                                    <button type="button" class="btn btn-danger">Firmar Comprobante</button>

                                    <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>

                                </div>
                            </div>
                        </form>

                    </div>
                </div>

            </section>

        </div>

        <script>

            var contadorFilas = 0;

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

                // Verificar que la cuenta esté en estado "En Distribución"

                console.log("Iniciando llamada Http - cuenta:" + cuenta);
                var http = new XMLHttpRequest();
                url = 'ControladorBuscar2?cuenta=' + cuenta;
                http.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        resultado = this.responseText.trim();
                        console.log("Response text:[" + resultado + "]");
                        if (resultado === "no encontrado") {
                            alert("No existe una tarjeta en estado En Distribución con ese número");
                            return;
                        }

                        // Obtener la referencia de la tabla
                        var tabla = document.getElementById("tblTarjetas");

                        // Crear una nueva fila
                        var fila = tabla.insertRow();
                        fila.id = "fila" + contadorFilas;

                        // Insertar celdas con los valores de los input
                        var celdaCuenta = fila.insertCell(0);

                        celdaCuenta.innerHTML = '<input type="text" name="cuenta' + contadorFilas + '" value="' + cuenta + '" readonly>';

                        var celdaResultado = fila.insertCell(1);
                        celdaResultado.innerHTML = '<select class="form-select" name="resultado' + contadorFilas +
                                '"> <option selected value="">Seleccione el resultado</option>' +
                                '<option value="7">Entregada</option>' +
                                '<option value="3">Devuelta</option> </select>';

                        var celdaMotivo = fila.insertCell(2);
                        celdaMotivo.innerHTML = '<select class="form-select" name="motivo' + contadorFilas +
                                '"> <option selected value="">Seleccione el motivo</option>' +
                                '<option value="1">Faltan datos</option>' +
                                '<option value="2">Zona no atendida</option>' +
                                '<option value="3">No existe número</option>' +
                                '<option value="4">No existe calle</option>' +
                                '<option value="5">Se mudó</option>' +
                                '<option value="6">Titular desconocido</option>' +
                                '<option value="7">Se negó a recibirlo</option>' +
                                '<option value="8">Tiene 3 visitas</option>' +
                                '<option value="9">Está de vacaciones</option>' +
                                '<option value="10">Falleció el titular</option> </select>';

                        // Agregar un icono de cesto de basura y asociar un evento de clic para eliminar la fila
                        var celdaEliminar = fila.insertCell(3);
                        celdaEliminar.innerHTML = '<button onclick="eliminarFila2(' + contadorFilas + ')"><i class="fas fa-trash-alt"></i></button>';

                        // Limpiar los valores de los input
                        document.getElementById("inputCuenta").value = '';
                        contadorFilas++;
                    }
                };
                http.open('get', url);
                http.send();
            }


            function eliminarFila2(indiceFila) {
                var confirmacion = confirm("¿Está seguro de que desea eliminar esta fila?");
                if (confirmacion) {
                    var tabla = document.getElementById("tblTarjetas");
                    tabla.deleteRow(indiceFila + 1); // +1 para tener en cuenta la fila de encabezado
                }
            }


            function cancelar() {
                var confirmacion = confirm("¿Está seguro de que desea cancelar?");
                if (confirmacion) {
                    window.location.href = "index.jsp";
                }
            }
            
            function recibir() {
                // Validar Fecha de Envío
                var fecha = document.getElementById("inputFechaRendicion").value;
                var correo = document.getElementById("selCorreo").value;
                var rendicion = document.getElementById("inputRendicion").value;

                // Validar que los campos no estén vacíos
                if (fecha === '' || correo === '' || rendicion === '') {
                    alert('Por favor, ingrese correo, fecha de rendicón y número de rendición.');
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
                formularioRecibir = document.getElementById("frmRecibir");
                formularioRecibir.submit();
            }

        </script>

    </body>

</html>