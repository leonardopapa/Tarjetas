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
                        <h5 class="card-title">Cambiar Estado</h5>

                        <%
                            String accion = (String) request.getAttribute("accion");
                            if (!(accion == null)) {
                                if (accion.equalsIgnoreCase("ok")) {
                        %>
                        <script>
                            alert("Se registró correctamente el cambio de estado");
                            window.location.href = "index.jsp";
                        </script>
                        <%
                            }
                            if (accion.equalsIgnoreCase("error")) {
                                String error = (String) request.getAttribute("error");
                        %>
                        <script>
                            alert("<%=error%>");
                            // window.location.href = "index.jsp";
                        </script>
                        <%
                                }
                            }
                        %>

                        <hr>

                        <div class="row">
                            <form action="ControladorTarjetas" method="post" id="frmCambiar">
                                <div class="col">

                                    <div class="card" >

                                        <div class="card-body">

                                            <div class="row">
                                                <div class="col">
                                                    <label for="inputFechaCambio" class="col-form-label">Fecha de nuevo estado:</label>
                                                </div>
                                                <div class="col">
                                                    <input type="date" class="form-control" id="inputFechaCambio" name="fcambio">
                                                </div>

                                            </div>

                                            <div class="row">
                                                <div class="col">
                                                    <label for="selEstado" class="col-form-label">Nuevo Estado:</label>
                                                </div>
                                                <div class="col">
                                                    <select class="form-select" id="selEstado" name="selEstado">
                                                        <option selected>Seleccione el nuevo estado</option>
                                                        <option value="1">Impresa</option>
                                                        <option value="2">En Distribución</option>
                                                        <option value="3">Devuelta</option>
                                                        <option value="4">En Sucursal</option>
                                                        <option value="5">Reenvío</option>
                                                        <option value="6">Enviada a Sucursal</option>
                                                        <option value="7">Entregada</option>
                                                        <option value="8">Destruida</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col">
                                                    <label for="selUbicacion" class="col-form-label">Nueva Ubicación:</label>
                                                </div>
                                                <div class="col">
                                                    <select class="form-select" id="selUbicacion" name="selUbicacion">
                                                        <option selected>Seleccione la nueva ubicación</option>
                                                        <option value="17">Tucuman</option>
                                                        <option value="18">Banda Rio Sali</option>
                                                        <option value="19">Concepcion</option>
                                                        <option value="20">Santiago</option>
                                                        <option value="21">La Banda</option>
                                                        <option value="22">Salta</option>
                                                        <option value="23">Oran</option>
                                                        <option value="24">Tartagal</option>
                                                        <option value="25">Jujuy</option>
                                                        <option value="26">San Pedro</option>
                                                        <option value="27">Libertador</option>
                                                        <option value="28">Yerba Buena</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col">
                                                    <label for="inputCuenta" class="col-form-label">Cuenta:</label>
                                                </div>
                                                <div class="col">
                                                    <input type="text" class="form-control" name="inputCuenta" id="inputCuenta" placeholder="Ingrese el número de cuenta">
                                                </div>
                                            </div>
                                            <br>
                                            <button type="button" class="btn btn-danger" onclick="agregarFila();">Agregar</button>

                                        </div> <!-- card body -->
                                    </div> <!-- card -->
                                </div> <!-- col -->
                                <div class="col">
                                    <div class="card"">                                
                                        <table id="tblTarjetas" class="table table-striped" style="width:100%">
                                            <thead>
                                                <tr>
                                                    <th>Cuenta</th>
                                                    <th>Fecha de Emisión</th>
                                                    <th> </th>
                                                    <th>Estado</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div> <!-- card -->
                                    <br>
                                    <input type="hidden" name="accion" value="cambiar">
                                    <button type="button" class="btn btn-danger" onclick="cambiarEstado();">Confirmar</button>
                                    <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>
                                </div> <!-- col -->
                            </form>
                        </div> <!-- row -->
                    </div> <!-- card body -->
                </div> <!-- card -->
            </section> <!-- main -->
        </div> <!-- contenedor -->

        <script>

            // var contadorFilas = 0;

            window.onload = iniciar;
            function iniciar() {
                InicializarFechas();
            }

            function InicializarFechas() {
                var hoy = Date.now();
                var fechaHoy = new Date(hoy);
                var inputFechaCambio = document.getElementById('inputFechaCambio');
                inputFechaCambio.value = fechaHoy.toISOString().split('T')[0];
            }

            function cambiarEstado() {
                // Validar que los cambios no estén vacíos                
                var nuevoEstado = document.getElementById("selEstado").value;
                var nuevaUbicacion = document.getElementById("selUbicacion").value;

                if (nuevoEstado === '' || nuevaUbicacion === "") {
                    alert('Debe ingresar un nuevo estado y una nueva ubicación.');
                    return;
                }
                if (!(hayFilas())) {
                    alert('No hay cuentas seleccionadas.');
                    return;
                }

                // Enviar los datos al servet para su validación y registro
                var frmCambiar = document.getElementById("frmCambiar");
                frmCambiar.submit();
            }

            function hayFilas() {
                var tabla = document.getElementById("tblTarjetas");
                var tbody = tabla.getElementsByTagName("tbody")[0];
                return tbody && tbody.rows.length > 0;
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

                // Recuperar los datos de la cuenta (fecha emisión y estado)

                var fechaEmision = "";
                var estado = "";
                var resultado = "";

                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // Parse the JSON response

                            var response = xhr.responseText;
                            console.log(response);

                            var responseObject = JSON.parse(xhr.responseText);
                            // var jsonArray = xhr.responseText;
                            resultado = responseObject.resultado;
                            fechaEmision = responseObject.fechaEmision;
                            estado = responseObject.estado;
                            estadoid = responseObject.estadoid;
                            if (resultado === "no encontrado") {
                                alert("No se encuentra la cuenta");
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
                            // celdaCuenta.innerHTML = cuenta;
                            celdaCuenta.innerHTML = '<input type="text" class="form-control-plaintext" name="cuenta' + cuenta + '" value="' + cuenta + '" readonly>';

                            var celdaFecha = fila.insertCell(1);
                            var fechaFormateada = new (Date);
                            fechaFormateada = formatearFecha2(fechaEmision);
                            celdaFecha.innerHTML = '<input type="text" class="form-control-plaintext" name="fecha' + cuenta + '" value="' + fechaFormateada + '" readonly>';

                            var celdaEstadoId = fila.insertCell(2);
                            celdaEstadoId.innerHTML = '<input type="hidden" name="resultado' + cuenta + '" value="' + estadoid + '">';
                            
                            var celdaEstado = fila.insertCell(3);
                            celdaEstado.innerHTML = '<input type="text" class="form-control-plaintext" ' + cuenta + '" value="' + estado + '" readonly>';

                            // Agregar un icono de cesto de basura y asociar un evento de clic para eliminar la fila
                            var celdaEliminar = fila.insertCell(4);
                            celdaEliminar.innerHTML = '<button class="btn" onclick="eliminarFila2(' + cuenta + ')"><i class="fas fa-trash-alt"></i></button>';

                            // Limpiar los valores de los input
                            document.getElementById("inputCuenta").value = '';
                            var enfocar = document.getElementById("inputCuenta");
                            enfocar.focus();

                        } else {
                            console.error('Error:', xhr.status);
                        }
                    }
                };

                var url = 'ControladorTarjetas?cuenta=' + cuenta + '&accion=buscarCambiar';
                xhr.open('post', url);
                xhr.send();
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

            function eliminarFila2(indiceFila) {
                var confirmacion = confirm("¿Está seguro de que desea eliminar esta fila?");
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