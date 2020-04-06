<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="global_styles.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Registracija</title>
</head>
<script>
	function prepare()
	{
		$('#form-id').submit(function( event ) {
			$('#username-val').hide();
			$('#pass-val').hide();
			$('#email-val').hide();
  		
			$.ajax({
				  url: "RegisterServlet",
				  type: "get",
				  async: false,
				  data: $('#form-id').serializeArray().reduce(function(obj, item) {
					    obj[item.name] = item.value;
					    return obj;
					}, {}),
				  success: function(response) {
				    var json = JSON.parse(response);
				    var result = true;
				    var pass1=$('#pass1').val();
					if(pass1.localeCompare($('#pass2').val()) != 0)
					{
						$('#pass-val').show();
						result = false;
					}
				    if(json.username != null)
				    {
				    	$('#username-val').show();
				    	result = false;
				    }
				    if(json.email != null)
				    {
				    	$('#email-val').show();
				    	result = false;
				    }
				    if(result != true)
				    {
				    	event.preventDefault();
				    }
				  },
				  error: function(xhr) {
					  alert("Error");
					  event.preventDefault();
				  }
				});
			});
	}
	
</script>
<body class="dark-theme" onload="prepare()">
<div class="container body-content">
<h2>Registracija</h2>
<hr/>
<form id="form-id" method="post" action="RegisterServlet">
	<div class="form-horizontal col-md-6">
		<div class="form-group">
			<label class="control-label col-md-4">Ime</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="text" name="name" required="required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Prezime</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="text" name="surname" required="required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Korisničko ime</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="text" name="username" required="required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Lozinka</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="password" id="pass1" name="password" required="required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Unesite lozinku ponovo</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="password" id="pass2" name="password" required="required"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Email</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="email" name="email" required="required"/>
			</div>
		</div>
		<div class="text-danger col-md-offset-4">
			<p id="pass-val" hidden="true"><b>Lozinke se ne poklapaju</b></p>
			<p id="username-val" hidden="true"><b>Korisničko ime već postoji</b></p>
			<p id="email-val" hidden="true"><b>Email već postoji</b></p>
		</div>
		<div class="form-group">
			<div class="col-md-offset-4 col-md-10">
				<input type="submit" value="Potvrdi" class="btn btn-primary btn-lg"/>
			</div>
		</div>
	</div>
</form>
</div>
</body>
</html>