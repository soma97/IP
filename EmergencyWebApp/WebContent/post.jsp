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
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.0";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));</script>
<title>Objava</title>
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
	function ajaxComment()
	{
	$('#comment-form').submit(function( event ) {
		event.preventDefault();
		var formData = new FormData(this);
		
		$.ajax({
			  url: "CommentServlet",
			  type: "post",
			  async: false,
			  data: formData,
			  processData: false,
			  contentType: false,
			  success: function(response) {
				var json = JSON.parse(response);
				var result = "<div class=\"col-md-9\"><br/></div><div class=\"col-md-9 comment-style\"><div class=\"row\"><div class=\"col-xs-1\"><img class=\"comment-logo rounded-image\" src=\""+ json.profileImage + "\" alt=\"Profilna slika\"/></div><div class=\"col-xs-6\" style=\"height: 60px; display: flex; align-items: center;\"><p><b>"+json.user+"</b></p></div></div><div class=\"col-md-offset-1 col-md-4\"><p>"+json.comment+"</p>";
	        	if(json.image != null)
	        	{
	        		result+="<img class=\"comment-image rounded-image col-md-4\" src=\""+ json.image +"\" alt=\"slika komentara\"/>";
	        	}
	        	result+="</div></div>";
				result+="<div id='new-comment'></div>";
			    $('#new-comment').replaceWith(result);
			    $('#comment-box').val('');
			    $('#comment-image').val('');
			  },
			  error: function(xhr) {
				  alert(xhr);
				  event.preventDefault();
			  }
		});
		
	});
	}
	
	function prepare()
	{	
		ajaxComment();
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
<h2><%=post.getTitle()%></h2>
<%
	if(user.getId() != (int)session.getAttribute("id"))
	{
%>
<div id="fb-root"></div>
<div class="fb-share-button" 
    data-href="http://localhost:8080/EmergencyWebApp/post.jsp?id=<%=post.getId()%>" 
    data-layout="button_count">
 </div>
<a class="btn btn-primary btn-sm" href="https://twitter.com/intent/tweet?text=http://localhost:8080/EmergencyWebApp/post.jsp?id=<%=post.getId()%>" target="_blank">
  Tweet
</a>
<% } %>
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
	    <div id="new-comment"></div>
		</div>
		<br/>
		<form id="comment-form" method="post" action="CommentServlet" enctype="multipart/form-data">
			<div class="col-md-offset-3 col-md-4">
				<hr/>
	        	<input id="comment-box" class="form-control" type="text" name="comment" placeholder="Unesite komentar..." />
	        	<input id="comment-image" class="btn btn-info btn-sm" accept="image/*" type="file" name="comment-image"/>
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