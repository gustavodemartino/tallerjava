<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.Constants, data.Login"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administración</title>
</head>
<body>
	<div>
		<h1>
			Administración
		</h1>
		<div>
			<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%></strong>
			| <a href="logout">Cerrar Sesión</a>
		</div>
	</div>
	<br />
	<div>
		<a href="userlist.jsp">[ Mantenimiento de usuarios ]</a>
		<a href="parkinglist.jsp">[ Reporte de estacionamientos ]</a>
	</div>
</body>
</html>