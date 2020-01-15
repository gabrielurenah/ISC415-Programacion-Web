<!DOCTYPE html>
<html lang="en">

<#include 'head.ftl'>

<body>

<#include 'navbar.ftl'>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-6">

            <h1 class="my-4" style="text-align: center">👩🏽‍💻Create an article👨🏽‍💻</h1>

            <form action="/new-article" method="POST">
                <div class="form-group">
                    <label for="exampleInputEmail1">Title</label>
                    <input type="text" class="form-control" id="title" name="title"
                           placeholder="Title">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Body</label>
                    <textarea class="form-control" id="article-body" name="article-body" rows="10"></textarea>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail1">Tags</label>
                    <input type="text" class="form-control" id="tags" name="tags"
                           placeholder="e.g: Tag1, Hello-World, test">
                </div>
                <button type="submit" class="btn btn-primary" style="background-color: green">Create</button>
            </form>


        </div>

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->

<#include 'footer.ftl'>

</body>

</html>
