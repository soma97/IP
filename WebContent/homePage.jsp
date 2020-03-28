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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Početna</title>
</head>
<script>
	function prepare()
	{
		$('#add-post-id').click(function() {
			var isHidden = $('#form-id').is(":hidden");
			if(isHidden == true)
			{
				$('#form-id').show();
			}
			else{
				$('#form-id').hide();
			}
		});
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
			 <a href="#" id="add-post-id" class="btn btn-default">Dodaj post</a>
	    </div>
	</div>
	<div class="col-md-6">
		<br/>
		<form hidden="true" id="form-id" method="post" action="PostServlet">
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
						<input class="form-control text-box single-line" type="text" name="location"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Video</label>
					<div class="col-md-6">
						<input class="form-control" accept="video/*" type="file" name="video"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">Slika</label>
					<div class="col-md-6">
						<input class="form-control" accept="image/*" type="file" name="image1"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-3 col-md-1">
						<input type="submit" value="Podijeli" class="btn btn-primary"/>
					</div>
				</div>
			</div>
		</form>
		<hr/>
		NESTO DALJE
		
	</div>
	<div class="col-md-3">
	Nesto
	</div>
</div>
</body>
</html>