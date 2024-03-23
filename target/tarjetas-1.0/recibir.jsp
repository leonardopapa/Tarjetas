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
        <title>Tarjetas</title>
    </head>

    <body>

        <div class="contenedor">

            <aside class="menu-lateral">
                <jsp:include page="menu_lateral.jsp" />
            </aside>

            <section class="main">

                <div class="card" style="margin:20px">
                    <div class="card-body">
                        <h5 class="card-title">Recibir Rendición</h5>
                        <hr>                        
                        <form action="ControladorRemito" method="post" id="frmRecibir">
                            <div class="row">

                                <div class="col-3">

                                    <div class="card" >

                                        <div class="card-body">

                                            <div class="row">
                                                <label for="selCorreo" class="col-form-label">Correo:</label>
                                                <div class="col">
                                                    <select class="form-select" name="correo" id="selCorreo">                                                        
                                                        <option selected value="0">Seleccione el Correo</option>
                                                        <option value="29">Servicios Modernos</option>
                                                        <option value="32">Dago</option>
                                                        <option value="30">Flash</option>
                                                        <option value="33">La Veloz</option>
                                                        <option value="31">Coprisa</option>
                                                        <option value="34">Oca</option>
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

                                                <div class="row">
                                                    <label for="inputPieza" class="col-form-label">Ingreso Manual:</label>
                                                    <div class="col">
                                                        <input type="text" class="form-control" id="inputPieza" placeholder="Ingrese el número de pieza">
                                                    </div>
                                                </div>

                                                <br>

                                                <button type="button" class="btn btn-danger" onclick="agregarFila();">Agregar</button>
                                            
                                        </div>
                                    </div>

                                </div>
                                <div class="col">
                                    <div class="card"">                                
                                        <table id="tblTarjetas" class="table table-striped table-sm table-responsive" style="width:100%">
                                            <thead>
                                                <tr>
                                                    <th>Pieza</th>
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
                var formatoFecha = fechaActual.toISOString().split('T')[0];                
                var inputFecha = document.getElementById('inputFechaRendicion');                
                inputFecha.value = formatoFecha;
            }

            function agregarFila() {
                // Obtener los valores de los input
                var pieza = document.getElementById("inputPieza").value;
                var correo = document.getElementById("selCorreo").value;
                
                // Validar que los campos no estén vacíos
                if (pieza === '') {
                    alert('Por favor, ingrese una cuenta.');
                    return;
                }
                
                if (correo === '0') {
                    alert('Por favor, seleccione un correo.');
                    return;
                }                

                // Verificar que la pieza no haya sido ingresada en el mismo formulario
                if (document.getElementById("fila"+pieza)) {
                    alert('Número de pieza no válido. Ya fue ingresado.');
                    return;                    
                }
                
                // Verificar que la cuenta esté en estado "En Distribución" y haya sido impuesta al correo seleccionado
                console.log("Iniciando llamada Http - cuenta:" + pieza);
                var http = new XMLHttpRequest();
                url = 'ControladorTarjetas?pieza=' + pieza +'&correo=' + correo + '&accion=verificar';
                http.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        var cuenta = this.responseText.trim();
                        console.log("Response text:[" + cuenta + "]");
                        if (cuenta === "no encontrado") {
                            alert("La pieza ingresada no se encuentra en estado En Distribución con ese correo");
                            return;
                        }

                        // Obtener la referencia de la tabla
                        var tabla = document.getElementById("tblTarjetas");

                        // Obtener la referencia al tbody
                        var tbody = tabla.getElementsByTagName("tbody")[0];

                        // Crear una nueva fila
                        var fila = tbody.insertRow();
                        fila.id = "fila" + pieza;

                        // Insertar celdas con los valores de los input
                        
                        var celdaPieza = fila.insertCell(0);
                        celdaPieza.innerHTML = '<input type="text" class="form-control-plaintext" name="pieza' + pieza + '" value="' + pieza + '" readonly>';
                        
                        var celdaCuenta = fila.insertCell(1);
                        celdaCuenta.innerHTML = '<input type="text" class="form-control-plaintext" name="cuenta' + pieza + '" value="' + cuenta + '" readonly>';

                        var celdaResultado = fila.insertCell(2);
                        celdaResultado.innerHTML = '<select class="form-select" name="resultado' + pieza +
                                '"> <option selected value="">Seleccione el resultado</option>' +
                                '<option value="7">Entregada</option>' +
                                '<option value="3">Devuelta</option> </select>';

                        var celdaMotivo = fila.insertCell(3);
                        celdaMotivo.innerHTML = '<select class="form-select" name="motivo' + pieza +
                                '"> <option selected value="0">Seleccione el motivo</option>' +
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
                        var celdaEliminar = fila.insertCell(4);
                        celdaEliminar.innerHTML = '<button class="btn" onclick="eliminarFila2(' + pieza + ')"><i class="fas fa-trash-alt"></i></button>';

                        // Limpiar los valores de los input                        
                        document.getElementById("inputPieza").value = '';
                        var enfocar = document.getElementById("inputPieza");
                        enfocar.focus();
                    }
                };
                http.open('get', url);
                http.send();
            }

            function eliminarFila2(indiceFila) {
                var confirmacion = confirm("¿Está seguro de que desea eliminar esta fila?");
                if (confirmacion) {
                    var row = document.getElementById("fila" + indiceFila);
                    row.remove();
                }
            }

            function cancelar() {
                var confirmacion = confirm("¿Está seguro de que desea cancelar?");
                if (confirmacion) {
                    window.location.href = "index.jsp";
                }
            }

            function recibir() {
                // Obtener parámetros generales
                var fecha = document.getElementById("inputFechaRendicion").value;
                var correo = document.getElementById("selCorreo").value;
                var rendicion = document.getElementById("inputRendicion").value;

                // Validar que los campos no estén vacíos
                if (fecha === '' || correo === '0' || rendicion === '') {
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

                // Verificar que estén completos los campos Resultado y Motivo cuando corresponda                
                var tabla = document.getElementById('tblTarjetas');
                var filas = tabla.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
                for (var i = 0; i < filas.length; i++) {
                    var selects = filas[i].getElementsByTagName('select');
                    // Verificar el valor seleccionado en el combo "resultado"
                    var resultado = parseInt(selects[0].value);
                    if (resultado !== 3 && resultado !== 7) {
                        alert("Error en la fila " + (i + 1) + ": El valor seleccionado en 'resultado' debe ser Entregada o Devuelta.");
                        return;                     
                    }
                    // Verificar el valor seleccionado en el combo "motivo" si el resultado es 3
                    if (resultado === 3) {
                        var motivo = parseInt(selects[1].value);
                        if (motivo < 1 || motivo > 10) {
                            alert("Error en la fila " + (i + 1) + ": Falta indicar el motivo de la devolución.");
                            return; 
                        }                    
                    }
                }
                
                // Si se llega aquí, todas las filas cumplen con las condiciones
                console.log("Validación de datos entrada exitosa");

                // Confeccionar el remito de recepción
                formularioRecibir = document.getElementById("frmRecibir");
                formularioRecibir.submit();
                }

        </script>

    </body>

</html>