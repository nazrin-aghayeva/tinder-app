<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Sign in</title>

    <#include "css/bootstrap.min.css">
    <#include "css/style.css">

</head>

<body class="text-center">

    <form class="form" action=${rout} method="post">
        <img src="https://www.relevance.com/wp-content/uploads/2017/07/Tinder-for-Marketing.jpg"
             class="tinder-logo" alt="Tinder org.tinder.app.App logo">

        <h1 class="h3 mb-3 font-weight-normal">${message}</h1>
            <#list fields as field>
                <label for=${field} class="sr-only">${field}</label>
                <input class="form-control" type="text" name=${field} placeholder=${field}>
            </#list>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="Password" userId="inputPassword" class="form-control" placeholder="Password">
        <input class="btn btn-lg btn-primary btn-block"  class="submit" type="submit">
        <div class="or">or</div>
        <#if rout = "/login">
        <a href="/reg">Sign up</a>
        <#else>
        <a href="/login">Sign in</a>
        </#if>
        <p class="mt-5 mb-3 text-muted">&copy; Tinder 2020</p>
    </form>

</body>
</html>