<!DOCTYPE html>
<html>
<head>
    <title>Student Information</title>
    <link href="/css/bootstrap.css" rel="stylesheet">
    <script src="/js/bootstrap.js"></script>
</head>
<body>
<div class="container" style="margin:35px">
    <div class="row justify-content-md-center">
        <div class="col-12">
            <div class="card" style="padding: 20px; background-color: lighblue">

                <h4>Datos del estudiante:</h4>

                <p>ID: ${student.id}</p>
                <p>Name: ${student.name}</p>
                <p>Last Name: ${student.lastName}</p>
                <p>Phone: ${student.phone}</p>
            </div>
        </div>
    </div>
    <div class="row justify-content-md-center">
        <button onclick="location.href = '/';" type="button" class="btn btn-primary" style="margin-top: 10px;">Back</button>
    </div>
</div>


</body>
</html>