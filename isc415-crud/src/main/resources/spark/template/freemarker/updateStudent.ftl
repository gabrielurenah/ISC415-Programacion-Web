<!DOCTYPE html>
<html>
<head>
    <title>Update Student data</title>
    <link href="/css/bootstrap.css" rel="stylesheet">
    <script src="/js/bootstrap.js"></script>
</head>
<body style="margin:10px">
<h1>Updating Student:</h1>
</br>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-12">
            <div class="card" style="padding: 20px; background-color: lightblue">
                <form action="/updateStudent/" method="post"  enctype="application/x-www-form-urlencoded">
                   <div class="form-group">
                        <label for="matricula">ID:</label>
                        <input type="text" class="form-control" id="matricula" name="matricula" value="${student.id}">
                    </div>
                    <div class="form-group">
                        <label for="nombre">Name:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${student.name}">
                    </div>

                    <div class="form-group">
                        <label for="apellido">Last Name:</label>
                        <input type="text" class="form-control" id="apellido" name="apellido" value="${student.lastName}">
                    </div>
                    <div class="form-group">
                        <label for="telefono">Phone:</label>
                        <input type="text" class="form-control" id="telefono" name="telefono" value="${student.phone}">
                    </div>

                    <input name="index" type="hidden" value="${index}"/>

                    <button name="Enviar" type="submit" class="btn btn-primary" style="background-color:green">Update</button>
                    <button onclick="location.href = '/';" type="button" class="btn btn-primary" style="background-color:red">Cancel</button>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>