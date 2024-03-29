<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
                        <h5 class="card-title">Reportes</h5>
                        <hr>

                        <%
                            String nreporte = request.getParameter("nreporte");
                            String reporten = request.getParameter("reporten");
                            String desde = request.getParameter("desde");
                            String hasta = request.getParameter("hasta");
                            String correo = request.getParameter("correo");
                            String nombrec = request.getParameter("nombrec");
                            List<ArrayList<Object>> reporte = (List<ArrayList<Object>>) request.getAttribute("reporte");
                        %>
                        
                        <script>
                            console.log("Correo:" + "<%=correo %>" );
                            console.log("Nombre Correo:" + "<%=nombrec %>" );
                            console.log("Reporte:" + "<%=nreporte %>" );
                            console.log("Nombre Reporte:" + "<%=reporten %>" );
                            
                        </script>

                        <div class="row">

                            <div class="col">

                                <div class="card">

                                    <div class="card-body">

                                        <form action="ControladorTarjetas" method="post" id="frmReportes">

                                            <div class="row">
                                                <label for="selReporte" class="col-form-label">Reporte:</label>
                                                <div class="col">
                                                    <select class="form-select" id="selReporte" name="reporte">

                                                        <%
                                                            if (nreporte == null) {
                                                            %>
                                                            <option selected value="0">Seleccione el reporte</option>
                                                            <%
                                                            } else { %>
                                                            <option selected value="<%= nreporte %>"><%= reporten %></option>
                                                            <option value="0">Seleccione el reporte</option>
                                                            <%
                                                                }
                                                        %>

                                                        <option value="1">Tarjetas por Estado</option>
                                                        <option value="2">Motivos de Rechazo</option>
                                                        <option value="3">Rapidez en la entrega</option>
                                                        <option value="4">Efectividad de la entrega</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="row">

                                                <label for="selCorreo" class="col-form-label">Correo:</label>
                                                <div class="col">
                                                    <select class="form-select" id="selCorreo" name="correo">
                                                        
                                                        <%
                                                            if (correo == null) {
                                                            %>
                                                            <option selected value="0">Seleccione el correo</option>
                                                            <%
                                                            } else { %>
                                                            <option selected value="<%= correo %>"><%= nombrec %></option>
                                                            <option value="0">Seleccione el correo</option>
                                                            <%
                                                                }
                                                        %>
                                                        
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
                                                <label for="desde" class="col-form-label">Fecha Desde:</label>
                                                <div class="col">
                                                    <input type="date" class="form-control" id="desde" name="desde"  value="<%=desde%>">
                                                </div>
                                            </div>

                                            <div class="row">
                                                <label for="hasta" class="col-form-label">Fecha Hasta:</label>
                                                <div class="col">
                                                    <input type="date" class="form-control" id="hasta" name="hasta" value="<%=hasta%>">
                                                </div>
                                            </div>

                                            <br>

                                            <input type="hidden" name="accion" value="reportes">

                                            <button type="button" class="btn btn-danger" onclick="obtenerReporte();">Obtener Reporte</button>

                                            <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>

                                        </form>

                                    </div>

                                </div> 

                            </div>
                            <!-- col  -->

                            <div class="col">    
                                <div class="card">

                                    <div class="card-body"> 
                                        <h6>Resultados</h6>

                                        <form id="exportForm" action="ExportController" method="post">

                                            <table id="tblReporte" class="table table-striped" style="width:100%">
                                                <thead>
                                                    <tr>

                                                        <%                                                            // Obtener el reporte del request

                                                            // Verificar si el reporte no es nulo y tiene al menos una fila
                                                            if (reporte != null && !reporte.isEmpty()) {
                                                                // Obtener los nombres de los campos de la primera fila
                                                                ArrayList<Object> nombresCampos = reporte.get(0);

                                                                // Iterar sobre los nombres de los campos para construir las columnas de la tabla
                                                                for (Object nombreCampo : nombresCampos) {
                                                        %>
                                                        <th><%= nombreCampo%></th>
                                                            <%
                                                                }
                                                            %>

                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%
                                                        // Iterar sobre las filas de datos del reporte
                                                        for (int i = 1; i < reporte.size(); i++) {
                                                            ArrayList<Object> fila = reporte.get(i);
                                                    %>
                                                    <tr>
                                                        <%
                                                            // Iterar sobre los valores de los campos en la fila actual
                                                            for (Object valor : fila) {
                                                        %>
                                                        <td><%= valor%></td>
                                                        <%
                                                            }
                                                        %>
                                                    </tr>
                                                    <%
                                                            }
                                                        }
                                                    %>

                                                </tbody>
                                            </table> 

                                            <input type="hidden" id="columnaInput" name="columna">
                                            <input type="hidden" id="datoInput" name="dato">
                                            <input type="hidden" id="exportTypeInput" name="exportType">

                                            <button type="button" class="btn btn-success" onclick="submitForm('XLS')">
                                                <img src="img/xls.png" width="25" height="25" alt="Exportar a XLS"/>
                                                Exportar a XLS                                                
                                            </button>

                                            <button type="button" class="btn btn-success" onclick="submitForm('XLSX')">
                                                <img src="img/xlsx.png" width="25" height="25" alt="Exportar a XLSX"/>
                                                Exportar a XLSX                                            
                                            </button>  

                                        </form>

                                    </div>
                                </div>

                            </div> <!-- col  -->
                        </div> <!-- row  -->

                    </div>  <!-- card-body -->

                </div>  <!-- card -->

            </section> <!-- main -->

        </div> <!-- contenedor -->

        <script>

            function obtenerReporte() {

                // Validar datos de entreda                
                var reporte = document.getElementById("selReporte");
                var desde = document.getElementById("desde");
                var hasta = document.getElementById("hasta");
                if (reporte.value === "0") {
                    alert("Debe seleccionar un reporte");
                    return;
                }

                // Validar que los campos no estén vacíos
                if (desde === '' || hasta === '') {
                    alert('Por favor, ingrese un rango de fechas válido');
                    return;
                }

                // Generar el reporte                
                var formularioReporte = document.getElementById("frmReportes");
                formularioReporte.submit();

            }

            function cancelar() {
                var confirmacion = confirm("¿Está seguro de que desea salir?");
                if (confirmacion) {
                    window.location.href = "index.jsp";
                }
            }

            function submitForm(exportType) {
                var table = document.getElementById("tblReporte");
                var rows = table.getElementsByTagName("tr");
                var columnas = [];

                // Obtener encabezados
                var headerRow = rows[0];
                var cells = headerRow.getElementsByTagName("th");
                for (var i = 0; i < cells.length; i++) {
                    columnas.push(cells[i].innerText);
                }

                // Construir datos
                var datos = [];
                for (var j = 1; j < rows.length; j++) {
                    var cells = rows[j].getElementsByTagName("td");
                    var rowData = [];
                    for (var k = 0; k < cells.length; k++) {
                        rowData.push(cells[k].innerText);
                    }
                    datos.push(rowData.join(","));
                }

                // Crear campos ocultos y enviar formulario
                document.getElementById("columnaInput").value = columnas.join(",");
                document.getElementById("datoInput").value = datos.join(",");
                document.getElementById("exportTypeInput").value = exportType;

                document.forms["exportForm"].submit();
            }

        </script>

    </body>

</html>