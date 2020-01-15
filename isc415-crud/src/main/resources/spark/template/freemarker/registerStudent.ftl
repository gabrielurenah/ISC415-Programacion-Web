<!DOCTYPE html>
<html>
<head>
    <title>Registrar Estudiante</title>
    <link href="/css/bootstrap.css" rel="stylesheet">
    <script src="/js/bootstrap.js"></script>
</head>
<body>
<h1 style="text-align: center;">Enter the data of the student:</h1>
</br>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-12">
            <div class="card" style="padding: 20px; background-color: lightblue">
                <form action="/addStudent/" method="post"  enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <label for="matricula">ID:</label>
                        <input type="text" class="form-control" id="matricula" name="matricula">
                    </div>
                    <div class="form-group">
                        <label for="nombre">Name:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre">
                    </div>

                    <div class="form-group">
                        <label for="apellido">Last Name:</label>
                        <input type="text" class="form-control" id="apellido" name="apellido">
                    </div>
                    <div class="form-group">
                        <label for="telefono">Phone:</label>
                        <input type="text" class="form-control" id="telefono" name="telefono">
                    </div>
                    <button type="submit" class="btn btn-primary" style="background-color:green ">Register</button>
                    <button onclick="location.href = '/';" type="button" class="btn btn-primary" style="background-color:red">Cancel</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>