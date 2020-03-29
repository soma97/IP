<%@page import="db.models.*"%>
<%@page import="db.dao.*"%>
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
<title>Početna</title>
</head>
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

		
		$('#add-post-id').click(function() {
			var isHidden = $('#form-id').is(":hidden");
			if(isHidden == true)
			{
				$('#form-id').fadeIn(1000);
			}
			else{
				$('#form-id').fadeOut(1000);
			}
		});
		
		$('input[type=radio][name=video-type]').change(function() {
		    if($(this).val() == "yt")
		    {
		    	$('#video-field').attr("type","text");
		    }
		    else{
		    	$('#yt-link-val').hide();
				$('#submit-button').prop("disabled",false);
		    	$('#video-field').attr("type","file");
		    }
		});
		
		$('#video-field').on('input',function(e){
			$('#yt-link-val').hide();
			$('#submit-button').prop("disabled",false);
			var type = $('input[type=radio][name=video-type]:checked').val();
			if(type == "yt")
			{
				var videoLink = $('#video-field').val();
				if(videoLink.includes("youtube.com")==false)
				{
					$('#yt-link-val').show();
					$('#submit-button').prop("disabled",true);
				}
			}
		});
	}
	
	function fillMap(xArg,yArg, mapId)
	{	
	    var x = xArg;
		var y = yArg;
		var currentMap = L.map(mapId).setView([x, y], 7);
		L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
			maxZoom: 18,
			id: 'mapbox/streets-v11',
			tileSize: 512,
			zoomOffset: -1
		}).addTo(currentMap);

		L.marker([x, y]).addTo(currentMap);
	}
	
	function show(element,current)
	{
		var selectedElement = $('#'+element);
		if(selectedElement.is(":hidden"))
		{
			selectedElement.show();	
			$(current).html('Sakrij');
		}
		else
		{
			selectedElement.hide();
			$(current).html('Detalji');
		}
	}
</script>
<body class="dark-theme" onload="prepare()">
<%
	UserAccount userAccount = UserAccountDAO.selectUserById((int)session.getAttribute("id"));
	request.setAttribute("user", userAccount);
	ArrayList<Post> emergencies = PostDAO.selectAllEmergencyPosts();
	int size = emergencies.size();
	Post post1 = emergencies.get(size-1);
	Post post2 = emergencies.get(size-2);
	Post post3 = emergencies.get(size-3);
%>
<div class="row">
	<div class="col-md-3">
		<div class="col-md-12 text-center">
	        <img class="rounded-image profile-img" src="<%=((db.models.UserAccount)request.getAttribute("user")).getImageSource()%>" alt="Profilna slika"/>
	        <br/>
	        <hr/>
	        <p><b><%=((db.models.UserAccount)request.getAttribute("user")).getName() + " " + ((db.models.UserAccount)request.getAttribute("user")).getSurname()%></b></p>
	         <hr/>
	        <p><b>Broj prijava <%=((db.models.UserAccount)request.getAttribute("user")).getNumberOfLogins()%></b></p>	
	   		 <hr/>
	   		 <% if(userAccount.getReceiveEmergencyNotifications().equals("app"))
	   			{
	   		  %>
	   		 <p><b>Hitna upozorenja</b></p>
	        <div>
	        	<p><a href="post.jsp?id=<%=post1.getId()%>"><%=post1.getTitle() %></a></p>
	        	<p><a href="post.jsp?id=<%=post2.getId()%>"><%=post2.getTitle() %></a></p>
	        	<p><a href="post.jsp?id=<%=post3.getId()%>"><%=post3.getTitle() %></a></p>
	        </div>
	         <% } %>
	         <br/>
			 <a href="javascript:void(0);" id="add-post-id" class="btn btn-default">Dodaj post</a>
	    </div>
	</div>
	<div class="col-md-6">
		<br/>
		<form hidden="true" id="form-id" method="post" action="PostServlet" enctype="multipart/form-data">
			<div class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-md-1">Naslov</label>
					<div class="col-md-6">
						<input class="form-control text-box single-line" type="text" name="title" required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Hitno upozorenje</label>
					<div class="col-md-6">
						<input class="check-box" type="checkbox" name="emergency"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Tekst</label>
					<div class="col-md-6">
						<textarea class="form-control text-box" name="text" form="form-id"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Link</label>
					<div class="col-md-6">
						<input class="form-control text-box single-line" type="text" name="link"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Lokacija</label>
					<div class="col-md-6">
						<input id="location-field" class="form-control text-box single-line" type="text" name="location"/>
					</div>
				</div>
				<div id="map-id" class="map"></div>
				
				<div class="custom-control custom-radio">
				  <input type="radio" class="custom-control-input" id="video-upload" name="video-type" value="upload" checked>
				  <label class="custom-control-label" for="video-upload">Upload video snimka</label>
				</div>
				<div class="custom-control custom-radio">
				  <input type="radio" class="custom-control-input" id="video-link" name="video-type" value="yt">
				  <label class="custom-control-label" for="video-link">Youtube link</label>
				</div>
				
				<div class="form-group">
					<label class="control-label col-md-1">Video</label>
					<div class="col-md-6">
						<input id="video-field" class="form-control" accept="video/*" type="file" name="video"/>
					</div>
				</div>
				<div class="text-danger">
					<p id="yt-link-val" hidden="true"><b>Youtube link nije validan</b></p>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Slika</label>
					<div class="col-md-6">
						<input class="form-control" accept="image/*" type="file" name="images" multiple="multiple" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-3 col-md-1">
						<input id="submit-button" type="submit" value="Podijeli" class="btn btn-primary"/>
					</div>
				</div>
			</div>
		</form>
		
	<!--  POSTOVI  -->
	
	<%
		ArrayList<Post> posts = PostDAO.selectAllPosts();
		posts.sort((a,b) -> b.getId()-a.getId());
		for(Post post : posts)
		{
	%>
	<div class="col-md-12">
		<br/>
	</div>
	<div class="col-md-12 post-border">
			<a href="post.jsp?id=<%=post.getId()%>"><h2><%= post.getTitle() %></h2></a>
			<a href="javascript:void(0);" onclick="show('hidden<%=post.getId()%>',this)">Detalji</a>
			<br/>
			<br/>
			<div id="hidden<%=post.getId() %>" hidden="true">
			<p><b><%= post.getText() %></b></p>
			<%
				if(post.getLink()!=null)
				{
			%>
	        		<a href="<%= post.getLink() %>"><%= post.getLink() %></a>
	        		<br/>
	        		<br/>
	        <% } %>
	        <%
				if(post.getLocation()!=null && post.getLocation().contains(","))
				{
			%>
	         	<h4><b>Lokacija</b></h4>
	        	<div class="map" id="map<%=post.getId()%>">
	        	<script>
	        		mapId = 'map'+<%=post.getId()%>;
	        		fillMap(<%= post.getLocation() !=null ? post.getLocation().split(",")[0]:"0" %>,<%= post.getLocation() !=null ? post.getLocation().split(",")[1]:"0" %>,mapId);
	        	</script>
	        	</div>
	        	<p><%=post.getLocation() %></p>
	        <% } %>
	   		<%
				if(post.getVideoSource()!=null)
				{
			%>
					<h4><b>Video</b></h4>
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
	        			<h4><b>Fotografije</b></h4>
	        			<div class="row">
	        		<%
	        	}
	        	for(ImagePath image : images)
	        	{
	        %>		<div class="col-md-6">
	        		<img class="home-page-img" src="<%= image.getImageSource() %>" alt="slika posta"/>
	        		</div>
	        <%  
	        	} 
	        	if(images.size()>0)
	        	{
	        %>
	      		</div>
	      	<% } %>
	        <div class="col-md-12">
	        	<h4><b>Komentari</b></h4>
	        </div>
	        <%
	        	ArrayList<Comment> comments = PostDAO.selectCommentsForPost(post.getId());
	        	for(Comment comment : comments)
	        	{
	        		UserAccount commentary = UserAccountDAO.selectUserById(comment.getUserAccountId());
	        %>
	        	<div class="col-md-12">
	        	<br/>
	        	</div>
	        	<div class="col-md-10 comment-style">
	        		 <div class="row">
				        <div class="col-xs-2">
				            <img class="comment-logo rounded-image" src="<%= commentary.getImageSource() %>" alt="Profilna slika"/>
				        </div>
				        <div class="col-xs-6" style="height: 60px; display: flex; align-items: center;">
				            <p><b><%= commentary.getName()+" "+commentary.getSurname() %></b></p>
				        </div>
				    </div>
	        		<div class="col-md-offset-1 col-md-6">
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
		<br/>
		<form method="post" action="CommentServlet" enctype="multipart/form-data">
			<div class="col-md-12">
				<br/>
	        	<input class="form-control" type="text" name="comment" value="Unesite komentar..."/>
	        	<input class="btn btn-info btn-sm" accept="image/*" type="file" name="comment-image"/>
	        	<input type="hidden" value="<%=session.getAttribute("id") %>" name="user-id" />
	        	<input type="hidden" value="<%=post.getId() %>" name="post-id" />
	        	<input type="submit" value="Potvrdi" hidden="true" />
			</div>
		</form>
		<div class="col-md-12">
			<br/>
		</div>
	</div>
	</div>
	<% } %>
		
	<!--  KRAJ POSTOVA -->
	
	</div>
	<div class="col-md-3">
		NESTO
	</div>
</div>
</body>
</html>