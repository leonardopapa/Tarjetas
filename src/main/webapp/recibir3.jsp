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

                        <form action="ControladorRemito" method="post" name="Grabar" id="frmGrabar">

                            <div class="row">

                                <%
                                    String resultado = (String) request.getAttribute("resultado");
                                    String archivo = (String) request.getAttribute("archivo");
                                    String correo = (String) request.getAttribute("correo");
                                    String frend = (String) request.getAttribute("frend");
                                    String ccuentas = (String) request.getAttribute("ccuentas");
                                    String cresultados = (String) request.getAttribute("cresultados");
                                    String cmotivos = (String) request.getAttribute("cmotivos");
                                    String leyenda = null;
                                    if (resultado.equalsIgnoreCase("ok")) {
                                        leyenda = "Cancelar";
                                %>
                                <embed src="pdf/<%=archivo%>" width="800" height="600" type="application/pdf">
                                <script>
                                    alert("Informe de Recepción firmado electrónicamente con éxito");                                    
                                </script>
                                <%
                                    }                                    
                                    if (resultado.equalsIgnoreCase("exito")) {                                        
                                %>
                                <script>
                                    alert("Informe de Recepción grabado con éxito");
                                    window.location.href = "index.jsp";
                                </script>
                                <%
                                    }
                                    if (resultado.equalsIgnoreCase("error")) {                                    
                                %>
                                <script>
                                    alert("Error en la Grabación del Informe de Recepción. No se grabaron los movimientos");
                                    window.location.href = "index.jsp";
                                </script>
                                <%
                                    }
                                %>

                            </div>

                            <div class="row mt-5">

                                <div class="col">

                                    <input type="hidden" name="accion" value="grabarRecepcion">
                                    <input type="hidden" name="archivo" value="<%=archivo%>">
                                    <input type="hidden" name="correo" value="<%=correo%>"> 
                                    <input type="hidden" name="frend" value="<%=frend%>"> 
                                    <input type="hidden" name="ccuentas" value="<%=ccuentas%>">
                                    <input type="hidden" name="cresultados" value="<%= cresultados %>"> 
                                    <input type="hidden" name="cmotivos" value="<%= cmotivos %>"> 

                                    <%
                                        if (resultado.equalsIgnoreCase("ok")) {
                                    %>
                                    <button type="submit" class="btn btn-danger" >Grabar Remito</button>
                                    <% }%>
                                    <a role="button" class="btn btn-danger" href="index.jsp"><%=leyenda%></a>

                                </div>

                            </div>

                        </form>

                    </div>

                </div>

        </div>

    </section>

</div>

</body>

</html>