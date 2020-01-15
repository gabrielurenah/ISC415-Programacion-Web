<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="my-4" style="text-align: center">⚡️All Articles⚡️</h1>
            <!-- Blog Post -->
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
            <!-- Pagination -->
            <ul class="pagination justify-content-center mb-4">
                <ul class="pagination">
                    <#list 1 ..<pages as page_number>
                        <li class="page-item"><a class="page-link" href="/?page=${page_number}">${page_number}</a></li>
                    </#list>
                </ul>
            </ul>
        </div>
        <!-- Sidebar Widgets Column -->
        <div class="col-md-4">
            <!-- Categories Widget -->
            <div class="card my-4">
                <h6 class="card-header">Tags🏷</h6>
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <ul class="list-unstyled mb-0">
                                <#list tags as tag>
                                    <li>
                                        <a href="/articles/tag/${tag.uid}">${tag.tag}</a>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include 'footer.ftl'>
<!-- Bootstrap core JavaScript -->
<!--
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>-->
</body>
</html>
