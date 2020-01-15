<!DOCTYPE html>
<html lang="en">

<#include 'head.ftl'>

<body>

<#include 'navbar.ftl'>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">

            <h1 class="my-4" style="text-align: center">⚡️All Articles⚡️</h1>

            <!-- Blog Post -->

            <#list articles as article>
                <div class="card mb-4">
                    <div class="card-body">
                        <h2 class="card-title">${article.title}</h2>
                        <p class="card-text">${article.information}</p>
                        <a href="articles/${article.id}" class="btn btn-primary">Read full article 👈</a>
                    </div>
                    <div class="card-footer text-muted">
                        <#list article.tags as tag>
                            <a href="#" class="badge badge-primary">${tag.tag}</a>
                        </#list>
                    </div>
                </div>
            </#list>
        </div>

        <!-- Sidebar Widgets Column -->
        <div class="col-md-4">
            <!-- Categories Widget -->
            <div class="card my-4">
                <h5 class="card-header">Tags🏷</h5>
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <ul class="list-unstyled mb-0">
                                <#list tags as tag>
                                    <li>
                                        <a href="#">${tag.tag}</a>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /.row -->

</div>
<!-- /.container -->

<#include 'footer.ftl'>

<!-- Bootstrap core JavaScript -->
<!--
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>-->

</body>

</html>
