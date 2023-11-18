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
                        <!-- 
                        <form id="frmIniciar">
                            <button type="submit"></button>
                        </form>
                        -->
                        <form id="frmFiltros">                        
                            <div class="row">

                                <div class="col">
                                    Cuenta:
                                    <br>
                                    <input type="text" class="form-control" name="cuenta" id="cuenta">
                                </div>

                                <div class="col">
                                    Estado

                                    <select class="form-select" name="selEstado" id="filtro-estado">
                                        <option selected></option>
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


                                <div class="col">
                                    Ubicación

                                    <select class="form-select" name="selUbicacion" id="filtro-ubicacion">
                                        <option selected></option>
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
                                        <option value="29">Servicios Modernos</option>
                                        <option value="30">Flash</option>
                                        <option value="31">Coprisa</option>
                                        <option value="32">Dago</option>
                                        <option value="33">La Veloz</option>
                                    </select>
                                </div>

                                <div class="col">
                                    Fecha Ultimo Estado Desde:
                                    <br>

                                    <input type="date" class="form-control" id="desde" name="desde">
                                </div>

                                <div class="col">
                                    Fecha Ultimo Estado Hasta:
                                    <br>

                                    <input type="date" class="form-control" id="hasta" name="hasta">
                                </div>

                            </div>


                            <button class="btn btn-secondary mt-2" id="filtrar" name=btnFiltrar" type="submit" value="filtrar">
                                Aplicar filtros
                            </button>

                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-funnel" viewBox="0 0 16 16">
                            <path
                                d="M1.5 1.5A.5.5 0 0 1 2 1h12a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.128.334L10 8.692V13.5a.5.5 0 0 1-.342.474l-3 1A.5.5 0 0 1 6 14.5V8.692L1.628 3.834A.5.5 0 0 1 1.5 3.5v-2zm1 .5v1.308l4.372 4.858A.5.5 0 0 1 7 8.5v5.306l2-.666V8.5a.5.5 0 0 1 .128-.334L13.5 3.308V2h-11z" />
                            </svg>

                        </form>

                    </div>
                </div>

                <div class="card" style="margin:20px">
                    <div class="card-body">
                        <h5 class="card-title">Tarjetas</h5>
                        <hr>

                        <%
                            List<Tarjeta> lista = (List<Tarjeta>) request.getAttribute("tlista");
                        %>

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
                            <tbody>
                                <% for (Tarjeta tarjeta : lista) {%>
                                <tr>
                                    <td> <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
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


                        <a href="ControladorTarjetas?accion=primero">Primero</a> <a href="ControladorTarjetas?accion=anterior">Anterior</a> <a href="ControladorTarjetas?accion=siguiente">Siguiente</a> <a href="ControladorTarjetas?accion=ultimo">Último</a>

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
                var hoy = Date.now();
                var fechaFin = new Date(hoy);
                var fechaInicio = new Date(hoy);
                fechaInicio.setMonth((fechaFin.getMonth() - 3));
                var inputDesde = document.getElementById('desde');
                var inputHasta = document.getElementById('hasta');
                inputHasta.value = fechaFin.toISOString().split('T')[0];
                inputDesde.value = fechaInicio.toISOString().split('T')[0];
            }

            function aplicarFiltros() {
                var formulario = document.getElementById("frmFiltros");
                formulario.action = "Controlador?accion=filtrar";
                formulario.submit();
            }

        </script>
    </body>

</html>