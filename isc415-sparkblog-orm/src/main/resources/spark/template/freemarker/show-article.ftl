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
            <div class="card">
                <h5 class="card-header">Full Article</h5>
                <div class="card-body">
                    <h2>${article.title}</h2>
                    <p>${article.information}</p>
                    <p>Published by: ${article.author.username} on ${article.date}</p>
                    <strong>Tags:</strong>
                    <#list tags as tag>
                        <a href="/articles/tag/${tag.uid}" class="badge badge-primary">${tag.tag}</a>
                    </#list>
                    <br/><br/>
                    <#if user??>
                        <form action="/comments/new/${article.uid}" method="post">
                            <textarea placeholder="Body of comment" class="form-control" name="comment" required></textarea>
                            <button type="submit" class="btn btn-primary">Post</button>
                        </form>
                    </#if>
                    <br/>
                    <h3>Comments:</h3>
                    <hr/>
                    <#list comments as comment>
                        <p>${comment.author.username} commented:</p>
                        <p>${comment.comment}</p>
                        <#if user?? && (user.role == "admin" || user.uid == comment.author.uid)>
                            <form action="/articles/${article.uid}/comments/${comment.uid}" method="post">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </#if>
                        <hr/>
                    </#list>
                </div>
            </div>
        </div>

        <div class="col-md-2">
            <#if user?? && (user.role == "admin" || user.uid = article.author.uid)>
                <div class="card my-2">
                    <h5 class="card-header">Actions</h5>
                    <div class="card-body">
                        <a href="/articles/${article.uid}/edit" class="btn btn-secondary" style="margin: 10px;">Edit✍️</a>
                        <form action="/articles/${article.uid}/delete" method="post">
                            <button type="submit" class="btn btn-danger">Delete🗑</button>
                        </form>
                    </div>
                </div>
            </#if>

            <#if user??>
                <div class="card my-4">
                    <h5 class="card-header">Likes👍🏼</h5>
                    <div class="card-body">
                        <form action="/articles/${article.uid}/like" class="formRecommendations" method="post">
                            <#if like == "null" || like == "false" >
                                <button class="like color-grey" name="like" type="submit"><span class="" aria-hidden="true">+</span></button>
                            <#else>
                                <button class="like" name="like" type="submit"><span class="fa fa-thumbs-o-up" aria-hidden="true">+</span></button>
                            </#if>
                        </form>
                        <form action="/articles/${article.uid}/dislike" class="formRecommendations" method="post">
                            <#if like == "null" || like == "true" >
                                <button class="dislike color-grey" name="dislike" type="submit"><span class="fa fa-thumbs-o-down color-grey" aria-hidden="true">-</span></button>
                            <#else>
                                <button class="dislike" name="dislike" type="submit"><span class="fa fa-thumbs-o-down" aria-hidden="true">-</span></button>
                            </#if>
                        </form>
                        <span class="likes-total" style="font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';"><b>${likesTotal}</b> likes</span>
                        <span class="likes-total" style="font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';"><b>${dislikesTotal}</b> dislikes</span>
                    </div>
                </div>
            </#if>
        </div>
    </div>
</div>
<!-- /.container -->

<#include 'footer.ftl'>

</body>

</html>
