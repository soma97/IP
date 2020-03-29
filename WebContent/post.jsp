<%@page import="db.models.*"%>
<%@page import="db.dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.Exception"%>
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
<title>Objava</title>
<style type="text/css">
.logo{
	width: 75px;
	height: 75px;
}
</style>
</head>
<%
	int id = -1;
	try{
		id = Integer.parseInt(request.getParameter("id"));
	}catch(Exception ex)
	{}
	Post post = PostDAO.selectPostById(id);
	if(post == null)
	{
		response.sendRedirect("error.jsp");
		return;
	}
	UserAccount user = UserAccountDAO.selectUserById(post.getUserAccountId());
	
%>
<script>
	function prepare()
	{	
	    var x = <%= post.getLocation() !=null ? post.getLocation().split(",")[0]:"0" %>;
		var y = <%= post.getLocation() !=null ? post.getLocation().split(",")[1]:"0" %>;
		var mymap = L.map('map-id').setView([x, y], 7);
		L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
			maxZoom: 18,
			id: 'mapbox/streets-v11',
			tileSize: 512,
			zoomOffset: -1
		}).addTo(mymap);

		var marker = L.marker([x, y]).addTo(mymap)
	}
</script>
<body class="dark-theme" onload="prepare()">
<div class="container body-content">
<h2><%=post.getTitle()%></h2>
<%
	if(post.isEmergencyWarning())
	{
%>
		<h4 class="emergency">Hitno upozorenje</h4>
<% 	} %>
<hr/>
	<div class="row">
		<div class="col-md-2 col-md-offset-1">
			<img class="logo rounded-image" src="<%= user.getImageSource() %>" alt="Profilna slika"/>
			<p><b><%= user.getName()+" "+user.getSurname() %></b></p>
		</div>
		<div class="col-md-9">
			<p><b><%= post.getText() %></b></p>
			<%
				if(post.getLink()!=null)
				{
			%>
	         	<hr/>
	        	<a href="<%= post.getLink() %>"><%= post.getLink() %></a>
	        <% } %>
	        <%
				if(post.getLocation()!=null)
				{
			%>
	         	<hr/>
	         	<p><b>Lokacija</b></p>
	        	<div id="map-id" class="map"></div>
	        	<p><%=post.getLocation() %></p>
	        <% } %>
	   		<%
				if(post.getVideoSource()!=null)
				{
			%>
					<hr/>
					<p><b>Video</b></p>
			<%
					if(post.getVideoSource().contains("youtube.com"))
					{
			%>
						<iframe width="500" height="315" allowfullscreen
							src="<%=post.getVideoSource().replace("watch?v=","embed/")%>">
						</iframe>
				<%  }
					else{
				%>
						<video width="500" height="315" controls>
						 	<source src="<%=post.getVideoSource() %>"/>
						</video>
	        <%		} 
				} 
			%>
	        
	        <%
	        	ArrayList<ImagePath> images = ImageDAO.selectAllForPostId(post.getId());
	        	if(images.size()>0)
	        	{
	        		%>
	        			<hr/>
	        			<p><b>Fotografije</b></p>
	        		<%
	        	}
	        	for(ImagePath image : images)
	        	{
	        %>
	        		<img class="post-image col-md-4" src="<%= image.getImageSource() %>" alt="slika posta"/>
	        <%  } %>
	        <div class="col-md-12">
	        	<hr/>
	        	<p><b>Komentari</b></p>
	        </div>
	        <%
	        	ArrayList<Comment> comments = PostDAO.selectCommentsForPost(post.getId());
	        	for(Comment comment : comments)
	        	{
	        		UserAccount commentary = UserAccountDAO.selectUserById(comment.getUserAccountId());
	        %>
	        	<div class="col-md-9">
	        	<br/>
	        	</div>
	        	<div class="col-md-9 comment-style">
	        		 <div class="row">
				        <div class="col-xs-1">
				            <img class="comment-logo rounded-image" src="<%= commentary.getImageSource() %>" alt="Profilna slika"/>
				        </div>
				        <div class="col-xs-6" style="height: 60px; display: flex; align-items: center;">
				            <p><b><%= commentary.getName()+" "+commentary.getSurname() %></b></p>
				        </div>
				    </div>
	        		<div class="col-md-offset-1 col-md-4">
	        			<p><%= comment.getText() %></p>
	        			<%
	        			 if(comment.getImageSource()!=null)
	        			 {
	        			%>
	        			<img class="comment-image rounded-image col-md-4" src="<%= comment.getImageSource() %>" alt="slika komentara"/>
	        			<%}%>
	        		</div>
	        	</div>
	        <%  } %>
		</div>
		<br/>
		<form method="post" action="CommentServlet" enctype="multipart/form-data">
			<div class="col-md-offset-3 col-md-4">
				<hr/>
	        	<input class="form-control" type="text" name="comment" value="Unesite komentar..."/>
	        	<input class="btn btn-info btn-sm" accept="image/*" type="file" name="comment-image"/>
	        	<input type="hidden" value="<%=session.getAttribute("id") %>" name="user-id" />
	        	<input type="hidden" value="<%=post.getId() %>" name="post-id" />
	        	<input type="submit" value="Potvrdi" hidden="true" />
			</div>
		</form>
	</div>
</div>
<br/>
<br/>
<br/>
</body>
</html>