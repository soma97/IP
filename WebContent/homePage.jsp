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
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.0";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));</script>
<title>Početna</title>
</head>
<script>
	function ajaxComment(id)
	{
		var formData = new FormData($("#commentform"+id)[0]);
		$.ajax({
			  url: "CommentServlet",
			  type: "post",
			  async: false,
			  data: formData,
			  processData: false,
			  contentType: false,
			  success: function(response) {
				var json = JSON.parse(response);
				
				var result = "<div class=\"col-md-12\"><br/></div><div class=\"col-md-10 comment-style\"><div class=\"row\"><div class=\"col-xs-2\"><img class=\"comment-logo rounded-image\" src=\""+ json.profileImage + "\" alt=\"Profilna slika\"/></div><div class=\"col-xs-6\" style=\"height: 60px; display: flex; align-items: center;\"><p><b>"+json.user+"</b></p></div></div><div class=\"col-md-offset-1 col-md-6\"><p>"+json.comment+"</p>";
	        	if(json.image != null)
	        	{
	        		result+="<img class=\"comment-image rounded-image col-md-4\" src=\""+ json.image +"\" alt=\"slika komentara\"/>";
	        	}
	        	result+="</div></div>";
			    result+="<div id='new-comment"+id+"'></div>";
			    $('#new-comment'+id).replaceWith(result);
				$("#commentform"+id+" input[name=comment]").val('');
				$("#commentform"+id+" input[name=comment-image]").val('');
			  },
			  error: function(xhr) {
				  alert(xhr);
				  event.preventDefault();
			  }
		});
	}

	function prepare()
	{
		setInterval(function refreshContent()
		{
			$.ajax({
	            type: 'GET',
	            url: 'http://localhost:8080/EmergencyWebApp/homePage.jsp',
	            success: function(htmlString) {
	            	var html = $(htmlString);
	            	var newPosts = $("div[id^=post-number]",html);
	            	var oldPosts = $("div[id^=post-number]");
	            	for(var i=0; i<newPosts.length; ++i)
	            	{
	            			var newElement = newPosts[i];
	            			var searchFor = "#"+ $(newElement).attr("id");
	            			if($(searchFor).html() == null)
	            			{
	            				var htmlToAdd = "<div id='" + $(newElement).attr("id") + "'>" + $(newElement).html() + "</div>";
	            				var oldPostsHtml = $('#posts').html();
	            				$('#posts').html(htmlToAdd+oldPostsHtml);
	            			}
	            			else{
	            				var comId = searchFor.split("-")[2];
	            				comId = '#comments'+comId;
	            				var newComments = $(comId,newElement).html();
	            				if($(comId).html().length != newComments.length)
	            				{
		            				$(comId).html(newComments);
	            				}
	            			}
	            	}
	            	
	            },
	            error: function(error)
	            {
	            	alert(error);
	            }
	        });
		},10000);
		
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
		
		$('#post-rss-toggle').click(function() {
			var isHidden = $('#posts').is(":hidden");
			if(isHidden == true)
			{
				$('#rss').fadeOut(750);
				$('#posts').fadeIn(1500);
				$('#post-rss-toggle').html('Prikazi RSS');
			}
			else{
				$('#posts').fadeOut(750);
				$('#rss').fadeIn(1500);
				$('#post-rss-toggle').html('Prikazi postove');
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
	ArrayList<Post> dangerPosts = PostDAO.selectAllPotentialDangerPosts();
%>
<div class="row">
	<div class="col-md-3">
		<div class="col-md-12 text-center">
	        <img class="rounded-image profile-img" src="<%=userAccount.getImageSource()%>" alt="Profilna slika"/>
	        <br/>
	        <hr/>
	        <p><b><%=userAccount.getName() + " " + userAccount.getSurname()%></b></p>
	         <hr/>
	        <p><b>Broj prijava <%=userAccount.getNumberOfLogins()%></b></p>	
	   		 <hr/>
	   		 <% if(userAccount.getReceiveEmergencyNotifications().equals("app"))
	   			{
	   		  %>
	   		 <p><b>Hitna upozorenja</b></p>
	        <div>
	        <% for(int i=dangerPosts.size()-1, j=0; i>=0 && j<3;--i,++j)
	           {
	        %>
	        	<p><a href="dangerDetails.jsp?id=<%=dangerPosts.get(i).getId()%>"><%=dangerPosts.get(i).getTitle() %></a></p>
	        <% } %>
	        </div>
	         <% } %>
	         <br/>
			 <a href="javascript:void(0);" id="add-post-id" class="btn btn-default">Dodaj post</a>
			 <br/>
			 <br/>
			 <a href="danger.jsp" class="btn btn-default">Dodaj upozorenje o opasnosti</a>
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
		
	<a id="post-rss-toggle" href="javascript:void(0)" class="btn btn-success">Prikazi RSS</a>
		
	<!--  POSTOVI  -->
	
	<div id="posts">
	<%
		ArrayList<Post> posts = PostDAO.selectAllPosts();
		posts.sort((a,b) -> b.getId()-a.getId());
		for(Post post : posts)
		{
			UserAccount postUser = UserAccountDAO.selectUserById(post.getUserAccountId());
	%>
	<div id="post-number-<%=post.getId()%>">
	<div class="col-md-12">
		<br/>
	</div>
	<div class="col-md-12 post-border">
			<div class="row">
				<div class="col-md-2">
				   	<img class="logo rounded-image" src="<%= postUser.getImageSource() %>" alt="Profilna slika"/>
				</div>
				<div class="col-md-10">
					<div style="height: 60px; display: flex; align-items: flex-end;">
				    	<p><b><%=postUser.getUsername() %></b></p>
				    </div>
				    
				    <% if(post.isPotentialDanger())
				       {
				    %>
				    <a class="text-danger" href="dangerDetails.jsp?id=<%=post.getId()%>"><h2><%= post.getTitle() %></h2></a>
				    <%
				    	if(post.isEmergencyWarning())
				    	{
				    		%><p class="text-danger">Hitno upozorenje</p><%
				    	}
				      }
				    else{
				    %>
				    		<a href="post.jsp?id=<%=post.getId()%>"><h2><%= post.getTitle() %></h2></a>
				    <% } %>
				    
				    <% if(userAccount.getId()!=postUser.getId())
				    	{
				    %>
					    <div id="fb-root"></div>
						<div class="fb-share-button" 
						    data-href="http://localhost:8080/EmergencyWebApp/<%= post.isPotentialDanger() ? "dangerDetails":"post" %>.jsp?id=<%=post.getId()%>" 
						    data-layout="button_count">
						 </div>
						<a class="btn btn-primary btn-sm" href="https://twitter.com/intent/tweet?text=http://localhost:8080/EmergencyWebApp/<%= post.isPotentialDanger() ? "dangerDetails":"post" %>.jsp?id=<%=post.getId()%>" target="_blank">
	  						Tweet
						</a>
					<% } %>
				</div>
			</div>
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
	        		fillMap(<%= post.getLocation().split(",")[0] %>,<%= post.getLocation().split(",")[1] %>,mapId);
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
	        <div id='comments<%=post.getId()%>'>
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
	    <div id="new-comment<%=post.getId()%>"></div>
	    </div>
		<br/>
		<form id="commentform<%=post.getId() %>" method="post" action="javascript:ajaxComment(<%=post.getId() %>)" enctype="multipart/form-data">
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
	</div>
	<% } %>
	</div>
	
	<!--  KRAJ POSTOVA -->
	
	<!-- RSS -->
	
	<div hidden="true" id="rss">
	<%
		for(int i=0;i<0;++i)
		{
	%>
		<div class="col-md-12">
		<br/>
		</div>
		
		<div class="post-border">
		</div>
	<% } %>
	</div>
	
	<!-- KRAJ RSS-A -->
	
	</div>
	<div class="col-md-3">
		NESTO
	</div>
</div>
</body>
</html>