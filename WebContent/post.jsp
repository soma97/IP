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
<body class="dark-theme">
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
				if(post.getVideoSource()!=null)
				{
			%>
	         	<hr/>
	        	<a href="<%= post.getVideoSource() %>"><%= post.getVideoSource() %></a>
	        <% } %>
	        <%
				if(post.getLocation()!=null)
				{
			%>
	         	<hr/>
	        	<a href="<%= post.getLocation() %>"><%= post.getLocation() %></a>
	        <% } %>
		</div>
	</div>
</div>
</body>
</html>