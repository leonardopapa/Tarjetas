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
                    <h5 class="card-title">Consultar Estado</h5>
                    <hr>
                    

                    

                            <div class="card" >

                                
                                <div class="card-body">

                                    <label for="inputCuenta" class="col-form-label">Cuenta:</label>
                                        <div class="col">
                                            <input type="text" class="form-control" id="inputCuenta">
                                        </div>

                                    

                                    <br>

                                    <button type="button" class="btn btn-danger">Consultar</button>

                            <button type="button" class="btn btn-danger">Salir</button>

                                    
                                    <br>

                                </div>
                            </div>

                        
                            <div class="card"> 
                                Movimientos                               
                                <table id=" tarjetas" class="table table-striped" style="width:100%">
                                <thead>
                                    <tr>

                                        <th>Fecha</th>
                                        <th>Movimiento</th>
                                        <th>UbicaciÃ³n</th>
                                        <th>Motivo</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>

                                        <td>01/09/2023</td>
                                        <td>Impresa</td>
                                        <td>Casa Central</td>
                                        <td></td>

                                    </tr>
                                    <tr>

                                        <td>02/09/2023</td>
                                        <td>En distribuciÃ³n</td>
                                        <td>Andreani</td>
                                        <td></td>
                                          
                                    </tr>

                                    <tr>

                                        <td>07/09/2023</td>
                                        <td>Enviada a sucursal</td>
                                        <td>Salta</td>
                                        <td>Tiene 3 visitas</td>
                                          
                                    </tr>

                                    <tr>

                                        <td>08/09/2023</td>
                                        <td>Recibida en sucursal</td>
                                        <td>Salta</td>
                                        <td></td>
                                          
                                    </tr>

                                </tbody>
                                </table>
                            </div>
                            <br>
                            


                        </div>
                    </div>



        </section>

    </div>

</body>

</html>