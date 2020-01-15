<!DOCTYPE html>
<html lang="en">

<#include 'head.ftl'>

<body>

<#include 'navbar.ftl'>

<!-- Page Content -->
<div class="container">
        <div class="col-md-6">
            <h1 class="my-4" style="text-align: center">
                👩🏽‍💻Update your article👨🏽‍💻
            </h1>
            <form action="/articles/${article.id}" method="POST">
                <div class="form-group">
                    <label for="exampleInputEmail1">Title</label>
                    <input type="text" class="form-control" id="title" name="title"
                           placeholder="Title" value="${article.title}">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Article</label>
                    <textarea class="form-control" id="article-body" name="article-body"
                              rows="10">${article.information}</textarea>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail1">Tags</label>
                    <input type="text" class="form-control" id="tags" name="tags"
                           placeholder="e.g: Tag1, Hello-World, Test" value="${tags}">
                </div>
                <button type="submit" class="btn btn-primary" style="background-color: green">Update Article</button>
            </form>
        </div>
</div>
<!-- /.container -->

<#include 'footer.ftl'>

</body>

</html>
