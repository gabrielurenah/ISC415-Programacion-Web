<!DOCTYPE html>
<html lang="en">

<#include 'head.ftl'>

<body>

<#include 'navbar.ftl'>
    <div class="container">
        <h1 class="my-4" style="text-align: center">Log in to your SparkBlog⚡️ account️</h1>
        <br/>
        <div class="col-md-6">
            <form action="/login" method="POST">
                <div class="form-group">
                    <label for="exampleInputEmail1">Username✍️</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" name="username" placeholder="Username">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password🔒</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" name="password" placeholder="Password">
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" name="remember-me" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1" name="remember-me">Remember Me</label>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>

</body>

</html>
