<%@page import="Modelo.Ubicacion"%>
<%@page import="Modelo.Estado"%>
<%@page import="Modelo.Tarjeta"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@page import="java.util.List"%>


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

                <%
                    // Recibir parámetros del servlet
                    String cuenta = (String) request.getAttribute("cuenta");
                    if (cuenta == null) {
                        cuenta = "";
                    }
                    String estado = (String) request.getAttribute("estado");
                    String enombre = (String) request.getAttribute("enombre");
                    if (estado == null) {
                        estado = "";
                        enombre = "";
                    }
                    String ubicacion = (String) request.getAttribute("ubicacion");
                    String unombre = (String) request.getAttribute("unombre");
                    if (ubicacion == null) {
                        ubicacion = "";
                        unombre = "";
                    }
                    String desde = (String) request.getAttribute("desde");
                    String hasta = (String) request.getAttribute("hasta");

                    List<Tarjeta> listat = (List<Tarjeta>) request.getAttribute("tlista");
                    List<Estado> listae = (List<Estado>) request.getAttribute("elista");
                    List<Ubicacion> listau = (List<Ubicacion>) request.getAttribute("ulista");

                    String tamanoLista = (String) request.getAttribute("tamanoLista");
                    String inicioLista = (String) request.getAttribute("inicioLista");
                    String tamanoSubLista = (String) request.getAttribute("tamanoSubLista");
                    int inicio = Integer.parseInt(inicioLista) + 1;
                    int fin = inicio + Integer.parseInt(tamanoSubLista) - 1;
                %>

                <div class="card" style="margin:20px">
                    <div class="card-body">                        
                        <form id="frmFiltros" action="Controlador?accion=filtrar" method="post">                        
                            <div class="row">

                                <div class="col">
                                    Cuenta:
                                    <br>
                                    <input type="text" class="form-control" name="cuenta" id="cuenta" value="<%=cuenta%>">
                                </div>

                                <div class="col">
                                    Estado

                                    <select class="form-select" name="selEstado" id="filtro-estado">
                                        <option selected value="<%= estado%>"> <%= enombre%> </option>
                                        <%
                                            if (!(estado.isEmpty())) {%>
                                        <option ></option>
                                        <%}
                                            for (Estado estado2 : listae) {%>
                                        <option value="<%= estado2.getId()%>"> <%= estado2.getNombre()%></option>                                                                        
                                        <% }%>
                                    </select>
                                </div>

                                <div class="col">
                                    Ubicación

                                    <select class="form-select" name="selUbicacion" id="filtro-ubicacion">
                                        <option selected value="<%= ubicacion%>"> <%= unombre%> </option>
                                        <%
                                            if (!(ubicacion.isEmpty())) {%>
                                        <option ></option>
                                        <% }
                                            for (Ubicacion ubicacion2 : listau) {%>
                                        <option value="<%= ubicacion2.getId()%>"> <%= ubicacion2.getNombre()%></option>                                                                        
                                        <% }%>
                                    </select>
                                </div>

                                <div class="col">
                                    Fecha Ultimo Estado Desde:
                                    <br>

                                    <input type="date" class="form-control" id="desde" name="desde" value="<%=desde%>">
                                </div>

                                <div class="col">
                                    Fecha Ultimo Estado Hasta:
                                    <br>

                                    <input type="date" class="form-control" id="hasta" name="hasta" value="<%=hasta%>">
                                </div>

                            </div>

                            <button class="btn btn-secondary mt-2" id="filtrar" name=btnFiltrar" type="submit" value="filtrar">
                                Aplicar filtros
                            </button>

                        </form>

                    </div>
                </div>

                <div class="card" style="margin:20px">
                    <div class="card-body">
                        <h5 class="card-title">Tarjetas</h5>
                        <hr>

                        <table id="tarjetas" class="table table-striped" style="width:100%">
                            <thead>
                                <tr>
                                    <th> <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                                    </th>
                                    <th>Cuenta</th>
                                    <th>Fecha de Emisión</th>
                                    <th>Estado</th>
                                    <th>Fecha de Último Estado</th>
                                    <th>Motivo</th>
                                    <th>Ubicación</th>
                                </tr>
                            </thead>
                            <tbody id="body">
                                <% for (Tarjeta tarjeta : listat) {%>
                                <tr id="fila<%= tarjeta.getCliente()%>">
                                    <td> <input class="form-check-input" type="checkbox" value="" id="check<%= tarjeta.getCliente()%>">
                                    </td>
                                    <td><%= tarjeta.getCliente()%></td>
                                    <td><%= tarjeta.getFechaEmision()%></td>
                                    <td><%= tarjeta.getEstado().getNombre()%></td>
                                    <td><%= tarjeta.getFechaEstado()%></td>
                                    <td><%= tarjeta.getMotivo().getNombre()%></td>
                                    <td><%= tarjeta.getUbicacion().getNombre()%></td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                        <p id="registros"> Mostrando registros <%= inicio%> a <%= fin%> de <%= tamanoLista%> registros </p>
                        
                        <input type="hidden" form="frmFiltros" value="<%=inicioLista%>" name="inicioLista" />
                        
                        <input type="hidden" form="frmFiltros" value="<%=tamanoSubLista%>" name="tamanoSubLista" />
                        

                        <div class="btn-group btn-group-sm" role="group" aria-label="Small button group">
                            <button type="button" form="frmFiltros" class="btn btn-outline-danger" onclick="primero();">Primero</button>
                            <button type="button" form="frmFiltros" class="btn btn-outline-danger" onclick="anterior();">Anterior</button>
                            <button type="button" form="frmFiltros" class="btn btn-outline-danger" onclick="siguiente();">Siguiente</button>
                            <button type="button" form="frmFiltros" class="btn btn-outline-danger" onclick="ultimo();">Ultimo</button>
                        </div>

                    </div>
                </div>

            </section>

        </div>

        <script>
            window.onload = iniciar;
            function iniciar() {
                InicializarFechas();
            }

            function InicializarFechas() {
                /*
                 var hoy = Date.now();
                 var fechaFin = new Date(hoy);
                 var fechaInicio = new Date(hoy);
                 fechaInicio.setMonth((fechaFin.getMonth() - 3));
                 var inputDesde = document.getElementById('desde');
                 var inputHasta = document.getElementById('hasta');
                 if (inputHasta.value === "")
                 inputHasta.value = fechaFin.toISOString().split('T')[0];
                 if (inputDesde.value === "")
                 inputDesde.value = fechaInicio.toISOString().split('T')[0];
                 * */
            }

            function aplicarFiltros() {
                var formulario = document.getElementById("frmFiltros");
                formulario.action = "Controlador?accion=filtrar";
                formulario.submit();
            }

            function primero() {
                var formulario = document.getElementById("frmFiltros");
                formulario.action = "Controlador?accion=primero";
                formulario.submit();
            }
            
            function anterior() {
                var formulario = document.getElementById("frmFiltros");
                formulario.action = "Controlador?accion=anterior";
                formulario.submit();
            }

            function siguiente() {
                var formulario = document.getElementById("frmFiltros");
                formulario.action = "Controlador?accion=siguiente";
                formulario.submit();
            }

            function ultimo() {
                var formulario = document.getElementById("frmFiltros");
                formulario.action = "Controlador?accion=ultimo";
                formulario.submit();
            }


        </script>
    </body>

</html>