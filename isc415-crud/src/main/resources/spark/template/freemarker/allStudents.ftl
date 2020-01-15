<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/css/bootstrap.css" rel="stylesheet">
    <script src="/js/bootstrap.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>List of All Students</title>
</head>
<body style="margin: 10px">
<h1>List of All Students</h1>
</br>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-12">
            <table class="table table-striped">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
                <#list students as student>
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.name}</td>
                        <td>${student.lastName}</td>
                        <td>${student.phone}</td>
                        <td>
                            <a href="/update/${student?index}/"><i class="material-icons">edit</i></a>
                            <a href="/delete/${student?index}/"><i class="material-icons" style="color:red">delete</i></a>
                            <a href="/${student?index}/"><i class="material-icons" style="color:black">remove_red_eye</i></a>
                        </td>
                    </tr>
                </#list>
            </table>
            <br/><br/>

            <form action="/register/" method="get">
                <button type="submit" class="btn btn-primary">Register Student</button>
            </form>
        </div>
    </div>
</div>

</div>


</body>
</html>