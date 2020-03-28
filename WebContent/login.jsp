<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="global_styles.css">
<title>Prijava</title>
</head>
<body class="dark-theme">
<div class="container body-content">
<h2>Prijava</h2>
<hr/>
<form method="post" action="LoginServlet">
	<div class="form-horizontal col-md-6">
		<div class="form-group">
			<label class="control-label col-md-2">Korisničko ime</label>
			<div class="col-md-10">
				<input class="form-control text-box single-line" type="text" name="username" required="required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2">Lozinka</label>
			<div class="col-md-10">
				<input class="form-control text-box single-line" type="password" name="password" required="required"/>
			</div>
		</div>
		<div class="text-danger col-md-offset-4">
			<p id="pass-val" <%=request.getAttribute("error")==null? "hidden=\"true\"" : "" %>><b><%=request.getAttribute("error")==null? "" : request.getAttribute("error") %></b></p>
		</div>
		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<input type="submit" value="Prijava" class="btn btn-primary btn-lg"/>
			</div>
		</div>
	</div>
</form>
</div>
<br/>
<br/>
<div class="container body-content">
	<p>Nemate nalog?</p>
	<a href="register.jsp" class="btn btn-default">Registruj se ovdje</a>
</div>
</body>
</html>