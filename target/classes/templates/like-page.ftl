<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Tinder</title>

    <#include "css/bootstrap.min.css">
    <#include "css/style.css">

</head>

<body style="background-color: #f5f5f5;">
<form class="form-users" action="/users" method="post">
    <div class="col-4 offset-4">
        <div class="card">
            <div class="card-body">
                <div class="row">
                    <div class="col-12 col-lg-12 col-md-12 text-center">
                        <img style="border: 4px solid black" src=${user.imgUrl} class="mx-auto rounded-circle img-fluid">
                        <h3 class="mb-0 text-truncated">${user.name}  ${user.surname}</h3>
                        <br>
                    </div>
                    <input type="hidden" name="userId" value=${user.id}>
                    <div class="col-12 col-lg-6">
                        <button name="dislike" type="submit" class="btn btn-outline-danger btn-block"><span class="fa fa-times"></span>
                            Dislike
                        </button>
                    </div>
                    <div class="col-12 col-lg-6">

                        <button name="like" type="submit" class="btn btn-outline-success btn-block"><span class="fa fa-heart"></span>
                            Like
                        </button>
                    </div>
                    <!--/col-->
                </div>
                <!--/row-->
            </div>
            <!--/card-block-->
        </div>
        <div style="display: flex; justify-content: space-between; margin-top: 20px;">
            <a href="/liked" role="button" class="btn btn-primary" style="display: block;width:130px;">Liked</a>
            <a href="/logout" role="button" class="btn btn-danger" style="display: block;width:130px;">Log out</a>
        </div>
    </div>
</form>
</body>
</html>