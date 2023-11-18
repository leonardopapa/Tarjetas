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
                    <h5 class="card-title">Recibir Rendición</h5>
                    <hr>
                    <div class="alert alert-danger" role="alert">
                        Esperando ingreso de tarjetas...
                    </div>
                    <div class="row">
                        <div class="col">

                            <div class="card" >

                                
                                <div class="card-body">

                                    <div class="row">
                                        <label for="inputPassword" class="col-form-label">Correo:</label>
                                        <div class="col">
                                            <select class="form-select" aria-label="Default select example">
                                                <option selected>Seleccione el Correo</option>
                                                <option selected>Seleccione el Correo</option>
                                                <option value="29">Servicios Modernos</option>
                                                <option value="32">Dago</option>
                                                <option value="30">Flash</option>
                                                <option value="33">La Veloz</option>
                                                <option value="31">Coprisa</option>
                                              </select>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <label for="inputFechaRendicion" class="col-form-label">Fecha de Rendición:</label>
                                        <div class="col">
                                            <input type="date" class="form-control" id="inputFechaRendicion">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <label for="inputRendicion" class="col-form-label">Número de Rendición:</label>
                                        <div class="col">
                                            <input type="text" class="form-control" id="inputRendicion">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <label for="inputPassword" class="col-form-label">Ingreso Manual:</label>
                                        <div class="col">
                                            <input type="text" class="form-control" id="inputPassword" placeholder="Ingrese el número de cuenta">
                                        </div>
                                    </div>


                                    <br>

                                    
                                    <button type="button" class="btn btn-danger">Agregar</button>

                                </div>
                            </div>

                        </div>
                        <div class="col">
                            <div class="card"">                                
                                <table id=" tarjetas" class="table table-striped" style="width:100%">
                                <thead>
                                    <tr>

                                        <th>Cuenta</th>
                                        <th>Motivo</th>
                                        <th>Acciones</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>

                                        <td>758699</td>
                                        <td>
                                            <select class="form-select" aria-label="Default select example">
                                                <option selected>Seleccione el motivo</option>
                                                <option value="1">TucumÃ¡n</option>
                                                <option value="2">Salta</option>
                                                <option value="3">Santiago del Estero</option>
                                                <option value="3">Jujuy</option>
                                              </select>
                                        </td>
                                        <td><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
                                            <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
                                          </svg></td>

                                    </tr>
                                    <tr>

                                        <td>758699</td>
                                        <td>
                                        <select class="form-select" aria-label="Default select example">
                                            <option selected>Seleccione el motivo</option>
                                            <option value="1">TucumÃ¡n</option>
                                            <option value="2">Salta</option>
                                            <option value="3">Santiago del Estero</option>
                                            <option value="3">Jujuy</option>
                                          </select>
                                        </td>
                                        <td><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
                                            <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
                                          </svg></td>

                                    </tr>

                                </tbody>
                                </table>
                            </div>
                            <br>
                            <button type="button" class="btn btn-danger">Confirmar Recepción</button>

                            <button type="button" class="btn btn-danger">Firmar Comprobante</button>

                            <button type="button" class="btn btn-danger" onclick="cancelar();">Cancelar</button>

                            <div class="row">
                                <div class="row">
                                    <label for="inputPassword" class="col-form-label">Enviar a Sucursal:</label>
                                    <div class="col">
                                        <select class="form-select" >
                                            <option selected>Seleccione la sucursal de destino</option>
                                            <option value="1">TucumÃ¡n</option>
                                            <option value="2">Salta</option>
                                            <option value="3">Santiago del Estero</option>
                                            <option value="3">Jujuy</option>
                                          </select>
                                    </div>
                                </div>
                            </div>


                            <br>

                            
                            <button type="button" class="btn btn-danger">Enviar a Sucursal</button>



                        </div>
                    </div>


                </div>
            </div>

        </section>

    </div>

</body>

</html>