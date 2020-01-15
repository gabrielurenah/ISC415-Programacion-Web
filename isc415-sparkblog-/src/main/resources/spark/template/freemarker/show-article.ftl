<!DOCTYPE html>
<html lang="en">

<#include 'head.ftl'>

<body>

<#include 'navbar.ftl'>

<br/>
<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <#if user?? && (user.role == "admin" || user.id = article.author.id)>
                <h1 class="my-4" style="text-align: center"></h1>
            </#if>
            <div class="card">
                <h5 class="card-header">Full Article</h5>
                <div class="card-body">
                    <h2>${article.title}</h2>
                    <p>${article.information}</p>
                    <p>Author: ${article.author.username} on ${article.date}</p>
                    <strong>Tags:</strong>
                    <#list tags as tag>
                        <a href="#" class="badge badge-primary">${tag.tag}</a>
                    </#list>
                    <br/><br/>
                    <#if user??>
                        <form action="/comments/new/${article.id}" method="post">
                            <textarea placeholder="Body of comment" class="form-control" name="comment"></textarea>
                            <button type="submit" class="btn btn-primary" style="background-color: green">Post</button>
                        </form>
                    </#if>
                    <br/>
                    <h3>Comments:</h3>
                    <hr/>
                    <#list comments as comment>
                        <p>
                            ${comment.author.username} commented:
                        </p>
                        <p>
                            ${comment.comment}
                        </p>
                        <#if user?? && (user.role == "admin" || user.id == comment.author.id)>
                            <form action="/articles/${article.id}/comments/${comment.id}" method="post">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </#if>
                        <hr/>
                    </#list>
                </div>
            </div>
        </div>

        <#if user?? && (user.role == "admin" || user.id = article.author.id)>
            <div class="col-md-2" style="margin-top: 10px;">
                <div class="card my-2">
                    <h5 class="card-header">Actions</h5>
                    <div class="card-body">
                        <a href="/articles/${article.id}/edit" class="btn btn-secondary" style="margin: 10px;">Edit✍️</a>
                        <form action="/articles/${article.id}/delete" method="post">
                            <button type="submit" class="btn btn-danger">Delete🗑</button>
                        </form>
                    </div>
                </div>
            </div>
        </#if>


    </div>
</div>
<!-- /.container -->

<#include 'footer.ftl'>

</body>

</html>
