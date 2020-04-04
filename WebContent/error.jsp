<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="global_styles.css">
<title>Greška</title>
</head>
<body class="dark-theme">
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="homePage.jsp">Početna</a></li>
                <li><a href="editProfile.jsp">Izmjena profila</a></li>
                <li><a href="AuthServlet">Odjava</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container body-content body-jsp">
        <h1 class="text-danger">Greška.</h1>
    	<h2 class="text-danger">Došlo je do greške pri obradi zahtjeva.</h2>
        <hr />
    </div>
</body>
</html>