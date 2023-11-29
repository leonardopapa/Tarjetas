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
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

                        <form action="ControladorTarjetas" method="post" id="frmDashboard">
                            <div class="row">

                                <div class="col">

                                    <label for="selCorreo"> Correo: </label>

                                    <select class="form-select" id="selCorreo" name="correo">
                                        <option selected>Seleccione el Correo</option>
                                        <option value="29">Servicios Modernos</option>
                                        <option value="32">Dago</option>
                                        <option value="30">Flash</option>
                                        <option value="33">La Veloz</option>
                                        <option value="31">Coprisa</option>
                                    </select>

                                </div>

                                <div class="col">
                                    Fecha Desde:
                                    <br>

                                    <input type="date" class="form-control" id="fecha-desde" name="desde" value="2023-01-01">
                                </div>

                                <div class="col">
                                    Fecha Hasta:
                                    <br>

                                    <input type="date" class="form-control" id="fecha-hasta" name="hasta" value="2023-12-31">
                                </div>

                                <div class="col">

                                    <input type="hidden" name="accion" value="dashboard">

                                    <button type="button" class="btn btn-info btn-lg" onclick="filtrar();">Confirmar</button>
                                </div>

                            </div>
                        </form>
                    </div>

                </div>

                <div class="card" style="margin:20px">
                    <div class="card-body">
                        <h5 class="card-title">Dashboard</h5>
                        <hr>

                        <div class="row">
                            <div class="col">

                            </div>
                        </div>


                        <div class="row">
                            <div class="col">

                                <div class="card">

                                    <h5 class="card-title">Efectividad</h5>
                                    <h6 class="card-subtitle mb-2 text-body-secondary">Nivel de tarjetas entregadas sobre
                                        las impuestas</h6>


                                    <div class="card-body">
                                        <div>
                                            <canvas id="efectividad"></canvas>
                                        </div>


                                    </div>
                                </div>

                            </div>
                            <div class="col">
                                <div class="card"">                                
                                    <h5 class=" card-title">Rapidez</h5>
                                    <h6 class="card-subtitle mb-2 text-body-secondary">Tiempo promedio entre la imposición y
                                        la rendición</h6>

                                    <div class="card-body">

                                        <div>
                                            <canvas id="rapidez"></canvas>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>


                    </div>
                </div>

            </section>

        </div>

        <script>

            // Obtener los KPI para el rango de fechas por defecto

            var desde_init = '2023-01-01';
            var hasta_init = '2023-12-31';
            var meses = ['Abr-23', 'May-23', 'Jun-23', 'Jul-23', 'Ago-23', 'Sept-23'];
            var efectividad = [50.00, 60.00, 70.00, 76.00, 80.00, 65.00];
            var meses2 = ['Abr-23', 'May-23', 'Jun-23', 'Jul-23', 'Ago-23', 'Sept-23'];
            var rapidez = [7.5, 8, 10, 15, 9, 12];

            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // Parse the JSON response
                        var responseObject = JSON.parse(xhr.responseText);
                        // var jsonArray = xhr.responseText;
                        meses = responseObject.meses;
                        efectividad = responseObject.efectividad;
                        meses2 = responseObject.meses2;
                        rapidez = responseObject.rapidez;
                        drawCharts();

                        // Process the array as needed
                        //console.log("Desde el JS:" + responseObjetct);
                    } else {
                        console.error('Error:', xhr.status);
                    }
                }
            };

            var url = 'ControladorTarjetas?desde=' + desde_init + '&hasta=' + hasta_init + '&accion=dashboard';
            // console.log(url);
            xhr.open('post', url);
            xhr.send();            

            function drawCharts() {

                const ctx = document.getElementById('efectividad');

                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: meses,
                        datasets: [{
                                label: '% de efectividad',
                                data: efectividad,
                                borderWidth: 1
                            }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });

                const ctx2 = document.getElementById('rapidez');

                new Chart(ctx2, {
                    type: 'bar',
                    data: {
                        labels: meses2,
                        datasets: [{
                                label: 'dí­as promedio',
                                data: rapidez,
                                borderWidth: 1
                            }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });

                function filtrar() {
                    var desde = document.getElementById("fecha-desde");
                    var hasta = document.getElementById("fecha-hasta");
                    if (desde === "" || hasta === "") {
                        alert("Debe indicar un rango de fechas válido");
                        return;
                    }
                    console.log("Validaciones superadas");
                    var formularioDashboard = document.getElementById("frmDashboard");
                    formularioDashboard.submit();
                }

            }

        </script>

    </body>

</html>