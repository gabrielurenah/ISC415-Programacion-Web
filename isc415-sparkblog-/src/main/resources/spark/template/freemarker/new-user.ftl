<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h1 class="my-4" style="text-align: center">Create a new user🤰</h1>
            <form action="/create-user" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username"
                           placeholder="Username">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="new-password" name="password"
                           placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control" id="new-name" name="name" placeholder="Full Name">
                </div>
                <div class="form-group">
                    <label for="role">Role</label>
                    <select class="form-control" id="role" name="role">
                        <option value="admin">Admin</option>
                        <option value="author">Author</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" style="background-color: green">Create</button>
            </form>
        </div>
    </div>
</div>
<#include 'footer.ftl'>
</body>
</html>