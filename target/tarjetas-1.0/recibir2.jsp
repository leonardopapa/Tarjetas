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

                        <form action="ControladorRemito" method="post" name="frmFirmar" id="frmFirmar">

                            <div class="row">

                                <%
                                    String resultado = (String) request.getAttribute("resultado");
                                    String nombreArchivo = (String) request.getAttribute("archivo");
                                    String correo = (String) request.getAttribute("correo");
                                    String frend = (String) request.getAttribute("frend");
                                    String ccuentas = (String) request.getAttribute("ccuentas");
                                    String cpiezas = (String) request.getAttribute("cpiezas");
                                    String cresultados = (String) request.getAttribute("cresultados");
                                    String cmotivos = (String) request.getAttribute("cmotivos");
                                    if (resultado.equalsIgnoreCase("error")) {                                        
                                %>
                                
                                <script>
                                    alert("Error en el proceso de firma");
                                </script>
                                    <%
                                    }
                                %>

                                <embed src="pdf/<%=nombreArchivo%>" width="800" height="600" type="application/pdf">    

                            </div>

                            <div class="row mt-5">

                                <div class="col">

                                    <input type="hidden" name="accion" value="firmarRecepcion">

                                    <input type="hidden" name="archivo" value="<%=nombreArchivo%>">

                                    <input type="hidden" name="firma">
                                                                        
                                    <input type="hidden" name="correo" value="<%= correo %>"> 
                                    <input type="hidden" name="frend" value="<%= frend %>"> 
                                    <input type="hidden" name="ccuentas" value="<%= ccuentas %>"> 
                                    <input type="hidden" name="cpiezas" value="<%= cpiezas %>"> 
                                    <input type="hidden" name="cresultados" value="<%= cresultados %>"> 
                                    <input type="hidden" name="cmotivos" value="<%= cmotivos %>"> 
                                    
                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal">Firmar Remito</button>

                                    <button type="button" class="btn btn-danger" onClick="cancelar();">Cancelar</button>

                                    <!-- Modal Ingresar alias y password -->
                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Ingrese clave de firma</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <label for="alias" class="form-label">Alias:</label>
                                                    <input type="text" class="form-control" name="alias" id="alias">
                                                    <label for="password" class="form-label">Password:</label>
                                                    <input type="password" class="form-control" name="password" id="password">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-danger" onclick="firmar();">Firmar</button>
                                                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                            </div>

                        </form>

                    </div>

                </div>

        </div>

    </section>

</div>

<script>

    function firmar() {
        var alias = document.getElementById("alias").value;
        var password = document.getElementById("password").value;        
        if (alias !== null && password !== null) {                        
            var frmFirmar = document.getElementById("frmFirmar");
            frmFirmar.submit();
        } else {
            alert("Debe ingresar un alias y un password");
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