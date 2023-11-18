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
                    <h5 class="card-title">Reportes</h5>
                    <hr>




                    <div class="card">


                        <div class="card-body">

                            <div class="row">
                                <label for="inputPassword" class="col-form-label">Reporte:</label>
                                <div class="col">
                                    <select class="form-select" aria-label="Default select example">
                                        <option selected>Seleccione el reporte</option>
                                        <option value="1">Tarjetas por Estado</option>
                                        <option value="2">Motivos de Rechazo</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <label for="inputPassword" class="col-form-label">Correo:</label>
                                <div class="col">
                                    <select class="form-select" aria-label="Default select example">
                                        <option selected>Seleccione el Correo</option>
                                        <option value="1">Todos</option>
                                        <option value="2">Servicios Modernos</option>
                                        <option value="3">Flash</option>
                                        <option value="4">Dago</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <label for="inputPassword" class="col-form-label">Fecha Desde:</label>
                                <div class="col">
                                    <input type="date" class="form-control" id="inputPassword">
                                </div>
                            </div>

                            <div class="row">
                                <label for="inputPassword" class="col-form-label">Fecha Hasta:</label>
                                <div class="col">
                                    <input type="date" class="form-control" id="inputPassword">
                                </div>
                            </div>

                            <br>
                            <div class="form-check ">
                                <input class="form-check-input" type="radio" name="flexRadioDefault"
                                    id="flexRadioDefault1">
                                <label class="form-check-label" for="flexRadioDefault1">
                                    Resumido
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="flexRadioDefault"
                                    id="flexRadioDefault2" checked>
                                <label class="form-check-label" for="flexRadioDefault2">
                                    Detallado
                                </label>
                            </div>
                            <br>


                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="detalle"
                                    id="flexRadioDefault1">
                                <label class="form-check-label" for="flexRadioDefault1">
                                    Por fecha de envÃ­o
                                </label>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="detalle"
                                    id="flexRadioDefault2" checked>
                                <label class="form-check-label" for="flexRadioDefault2">
                                    Por fecha de rendiciÃ³n
                                </label>
                            </div>

                            <br>

                            <button type="button" class="btn btn-danger">Obtener Reporte</button>

                            <button type="button" class="btn btn-danger">Cancelar</button>

                        </div>

                    </div>


                </div>
            </div>

        </section>

    </div>

</body>

</html>