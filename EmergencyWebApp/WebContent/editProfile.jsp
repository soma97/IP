<%@page import="services.Constants"%>
<%@page import="db.dao.*"%>
<%@page import="db.models.*"%>
<%@page import="services.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="global_styles.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Izmjena profila</title>
</head>
<%
	UserAccount user = UserAccountDAO.selectUserById((int)session.getAttribute("id"));
%>
<script>
	function prepare()
	{
		<%
		if(user.getCountry()==null)
		{
		%>
			$.ajax({
				  url: "<%=Constants.BASE_URL_COUNTRY%>",
				  type: "get",
				  async: false,
				  success: function(response) {
				    var json = response;
				    var result = "";
				    for(var i = 0; i < json.length; i++) {
				        var obj = json[i];
						result+="<option value='" + obj.alpha2Code + "'>" + obj.name + "</option>";
				    }
				    $('#select-country').html(result);
				  },
				  error: function(xhr) {
					  alert("Error");
				  }
				});
		
			$('#select-country').change(function() {
				var optionSelected = $("option:selected", this);
			    var valueSelected = this.value;
			    var countryUrl = "<%=Constants.BASE_URL_FLAG%>" + optionSelected.html();
			    $.ajax({
					  url: countryUrl,
					  type: "get",
					  success: function(response) {
					    var json = response;
					   	$('#country-id').val(json[0].flag);
					   	var selectedImage = $('#selected-image').val();
					   	if(selectedImage.length < 2)
					   	{
					   		$('#profile-image').attr("src",json[0].flag);
					   	}
					  },
					  error: function(xhr) {
						  alert("Error");
					  }
				});
			    
			    var regionUrl = "<%=Constants.BASE_URL_REGION%>"+ valueSelected +"/all/?key=<%=Constants.ACTIVE_REGION_KEY %>"
			    function JsonpHttpRequest(url, callback) {
			        var e = document.createElement('script');
			        e.src = url;
			        document.body.appendChild(e); 
			        window[callback] = (data) => {
			        	var json = data;
						var result = "";
						for(var i = 0; i < json.length; i++) {
						    var obj = json[i];
							result+="<option value='" + obj.region + "'>" + obj.region + "</option>";
						}
						$('#select-region').html(result);
			        }
			    }
			    JsonpHttpRequest(regionUrl + '&callback=cb', "cb");
				
			});
			
			$('#select-region').change(function() {
			    var valueSelected = this.value;
			    
			    var cityUrl = "<%=Constants.BASE_URL_CITY%>"+ $( "#select-country option:selected" ).val() +"/search/?region="+ valueSelected +"&key=<%=Constants.ACTIVE_REGION_KEY%>";
			    console.log(cityUrl);
			    function JsonpHttpRequest(url, callback) {
			        var e = document.createElement('script');
			        e.src = url;
			        document.body.appendChild(e); 
			        window[callback] = (data) => {
			        	var json = data;
						var result = "";
						for(var i = 0; i < json.length; i++) {
						    var obj = json[i];
							result+="<option value='" + obj.city + "'>" + obj.city + "</option>";
						}
						$('#select-city').html(result);
			        }
			    }
			    JsonpHttpRequest(cityUrl + '&callback=cb', "cb");
			    
			});
		<%}%>
	}
</script>
<body class="dark-theme" onload="prepare()">
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
<h2>Izmjena profila</h2>
<hr/>
<form method="post" action="EditProfileServlet" enctype="multipart/form-data">
	<input type="hidden" name="id" value="<%=user.getId()%>" />
	<input type="hidden" id="country-id" name="countryFlag" value="https://restcountries.eu/data/ala.svg"/> 
	<div class="form-horizontal col-md-6"> 
		<div class="form-group">
			<label class="control-label col-md-4">Profilna slika</label>
			<div class="col-md-8">
				<img id="profile-image" class="rounded-image profile-img" src="<%=user.getImageSource()%>" alt="Profilna slika"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Izaberi sliku</label>
			<div class="col-md-8">
				<input id="selected-image" class="form-control" accept="image/*" type="file" name="image" value="<%=user.getImageSource()%>"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Ime</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="text" name="name" value="<%=user.getName()%>" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Prezime</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="text" name="surname" value="<%=user.getSurname()%>" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Korisnicko ime</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="text" name="username" value="<%=user.getUsername()%>" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Email</label>
			<div class="col-md-8">
				<input class="form-control text-box single-line" type="email" name="email" value="<%=user.getEmail()%>" required/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Država</label>
			<div class="col-md-8">
				<% if(user.getCountry()!=null)
					{
				%>
					<input class="form-control text-box single-line" type="text" name="country" value="<%=user.getCountry()%>" readonly="readonly" required/>
				<%}else{ %>
				<select id="select-country" class="form-control" name="country">
				</select>
				<% } %>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Regija</label>
			<div class="col-md-8">
				<% if(user.getRegion()!=null)
					{
				%>
					<input class="form-control text-box single-line" type="text" name="region" value="<%=user.getRegion()%>" readonly="readonly" required/>
				<%}else{ %>
				<select id="select-region" class="form-control" name="region">
				</select>
				<%} %>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Grad</label>
			<div class="col-md-8">
			<% if(user.getCity()!=null)
					{
				%>
					<input class="form-control text-box single-line" type="text" name="city" value="<%=user.getCity()%>" readonly="readonly" required/>
				<%}else{ %>
				<select id="select-city" class="form-control" name="city">
				</select>
				<%} %>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Notifikacije o hitnim upozorenjima</label>
			<div class="col-md-8">
				<select class="form-control" name="notifications">
				  <option value="no" <%=user.getReceiveEmergencyNotifications().equals("no") ? "selected=\"selected\"" : ""%>>Bez notifikacija</option>
				  <option value="email" <%=user.getReceiveEmergencyNotifications().equals("email") ? "selected=\"selected\"" : ""%>>Na Email</option>
				  <option value="app" <%=user.getReceiveEmergencyNotifications().equals("app") ? "selected=\"selected\"" : ""%> >Kroz aplikaciju</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-4">Odobren</label>
			<div class="col-md-8">
				<input disabled="disabled" class="check-box" type="checkbox" name="approved" <%=user.isApproved() == true ? "checked":"" %> required/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-4 col-md-10">
				<input type="submit" value="Sačuvaj" class="btn btn-primary btn-lg"/>
			</div>
		</div>
	</div>
</form>
</div>
</body>
</html>

