<%@page import="db.dao.EmergencyDAO"%>
<%@page import="db.models.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="global_styles.css">
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css" integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==" crossorigin=""/>
<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js" integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew==" crossorigin=""></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
function prepare()
{
	var mymap = L.map('map-id').setView([45.752193, 16.394348], 7);
	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		id: 'mapbox/streets-v11',
		tileSize: 512,
		zoomOffset: -1
	}).addTo(mymap);

	mymap.on('click', function onMapClick(e) {
		var posString = e.latlng.toString();
	    var submitString = posString.split('(')[1].split(')')[0];
	    $('#location-field').val(submitString);
	    var marker = L.marker([parseFloat(submitString.split(',')[0]), parseFloat(submitString.split(',')[1])]).addTo(mymap);
	});
}
</script>
<%
	ArrayList<EmergencyCategory> categories = EmergencyDAO.selectEmergencyCategories();
%>
<title>Potencijalna opasnost</title>
</head>
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
<h2>Potencijalna opasnost</h2>
<hr/>
<div class="row">
	<div class="col-md-offset-2 col-md-6">
		<form id="form-id" method="post" action="DangerServlet">
			<div class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-4">Naslov</label>
					<div class="col-md-6">
						<input class="form-control text-box single-line" type="text" name="title" required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Kategorije (Ctrl + klik za izbor kategorije)</label>
					<div class="col-md-6">
						<select class="form-control" style="color: black;" name="categories" multiple>
						<%
							for(EmergencyCategory cat : categories)
							{
						%>
								<option value="<%=cat.getId()%>"><%=cat.getName()%></option>
						<%  } %>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Opis</label>
					<div class="col-md-6">
						<textarea class="form-control text-box" name="text" form="form-id"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Hitno upozorenje</label>
					<div class="col-md-6">
						<input class="check-box" type="checkbox" name="emergency"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Lokacija</label>
					<div class="col-md-6">
						<input id="location-field" readonly="readonly" class="form-control text-box single-line" type="text" name="location"/>
					</div>
				</div>
				<div id="map-id" class="map col-md-offset-2"></div>
				<br/>
				<div class="form-group">
					<div class="col-md-offset-5 col-md-1">
						<input id="submit-button" type="submit" value="Podijeli" class="btn btn-primary"/>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
</div>
</body>
</html>