<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="model.Constants, data.Login"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administración</title>
<link href="css/styles.css" rel="stylesheet">
</head>
<body>
	<div id="page-title">
		<h1 class="page-header text-overflow">
			<strong>Administración</strong>
		</h1>
		<div class="searchbox">
			<div class="input-group custom-search-form">
				<strong><%=((Login) session.getAttribute(Constants.IDENTFIER_SESSION_LOGIN_INFO)).getUser().getName()%></strong>
				| <a href="logout">Cerrar Sesión</a>
			</div>
		</div>
	</div>
	<div>
		<a href="userlist.jsp">Mantenimiento de usuarios</a>
	</div>
</body>
</html>