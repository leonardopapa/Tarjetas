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
                        <hr>


                        <div class="row">
                            <div class="col">

                                <div class="card" >


                                    <div class="card-body">

                                        <div class="row">
                                            <label for="inputPassword" class="col-form-label">Nuevo Estado:</label>
                                            <div class="col">
                                                <select class="form-select" aria-label="Default select example">
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
                                            <label for="inputPassword" class="col-form-label">Nueva Ubicación:</label>
                                            <div class="col">
                                                <select class="form-select" aria-label="Default select example">
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
                                        <br>

                                    </div>
                                </div>

                            </div>
                            <div class="col">
                                <div class="card"">                                
                                    <table id=" tarjetas" class="table table-striped" style="width:100%">
                                        <thead>
                                            <tr>

                                                <th>Cuenta</th>
                                                <th>Fecha de Emisión</th>
                                                <th>Estado</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>

                                                <td>758699</td>
                                                <td>07/10/2023</td>
                                                <td>En Sucursal</td>

                                            </tr>
                                            <tr>

                                                <td>758699</td>
                                                <td>07/10/2023</td>
                                                <td>En Sucursal</td>

                                            </tr>

                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <button type="button" class="btn btn-danger">Confirmar</button>
                                <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </body>
</html>