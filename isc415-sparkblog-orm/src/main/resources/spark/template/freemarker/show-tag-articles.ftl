<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="my-4" style="text-align: center">You're now looking at the ${tag.tag} tags🏷</h1>
            <#list articles as article>
                <div class="card mb-4">
                    <div class="card-body">
                        <h2 class="card-title">${article.title}</h2>
                        <p class="card-text">${article.information}</p>
                        <a href="articles/${article.uid}" class="btn btn-primary">Read More &rarr;</a>
                    </div>
                    <div class="card-footer text-muted">
                        <#list article.tags as tag>
                            <a href="/articles/tag/${tag.uid}" class="badge badge-primary">${tag.tag}</a>
                        </#list>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>
<#include 'footer.ftl'>
</body>
</html>
