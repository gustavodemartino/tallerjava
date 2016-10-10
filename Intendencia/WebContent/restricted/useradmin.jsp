<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/estilo.css">
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
<title>Usuarios > Intendencia</title>
</head>
<body>
	<div class="cajaBlancaFondo">
	<f:view>		
		<a class="buttonMenu buttonLogout" href="/Intendencia/login.jsf">Cerrar sesión</a>
		<a class="buttonMenu" href="/Intendencia/restricted/menu.jsf">Volver</a>
		<h1>Administración de usuarios</h1>
		<br/>
	</f:view>
	</div>
</body>
</html>