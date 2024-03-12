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
                        <h5 class="card-title">Enviar por Correo</h5>
                        <hr>

                        <form action="ControladorRemito" method="post" name="Grabar" id="frmGrabar">

                            <div class="row">

                                <%
                                    String resultado = (String) request.getAttribute("resultado");
                                    String archivo = (String) request.getAttribute("archivo");
                                    String correo = (String) request.getAttribute("correo");
                                    String fenvio = (String) request.getAttribute("fenvio");
                                    String ccuentas = (String) request.getAttribute("ccuentas");
                                    String leyenda = null;
                                    if (resultado.equalsIgnoreCase("ok")) {
                                        leyenda = "Cancelar";
                                %>
                                <embed src="pdf/<%=archivo%>" width="800" height="600" type="application/pdf">
                                <script>
                                    alert("Remito firmado electrónicamente con éxito");                                    
                                </script>
                                <%
                                    }                                    
                                    if (resultado.equalsIgnoreCase("exito")) {                                        
                                %>
                                <script>
                                    alert("Remito grabado con éxito");
                                    window.location.href = "index.jsp";
                                </script>                                
                                <%
                                    }
                                    if (resultado.equalsIgnoreCase("error")) {                                    
                                %>
                                <script>
                                    alert("Error en la Grabación del Remito. No se grabaron los movimientos");
                                    window.location.href = "index.jsp";
                                </script>
                                <%
                                    }
                                %>

                            </div>

                            <div class="row mt-5">

                                <div class="col">

                                    <input type="hidden" name="accion" value="grabarRemito">
                                    <input type="hidden" name="archivo" value="<%=archivo%>">
                                    <input type="hidden" name="correo" value="<%=correo%>"> 
                                    <input type="hidden" name="fenvio" value="<%=fenvio%>"> 
                                    <input type="hidden" name="ccuentas" value="<%=ccuentas%>"> 

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