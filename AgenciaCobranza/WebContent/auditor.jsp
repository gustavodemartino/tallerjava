<%@page import="data.Login"%>
<%@page import="model.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Informe de auditoria</title>
</head>
<body>
	<h1>Informe de auditoria</h1>
	<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%>
	|
	<a href="logout">Cerrar Sesión</a> |
	<a href="menu.jsp">Menú principal</a>

</body>
</html>