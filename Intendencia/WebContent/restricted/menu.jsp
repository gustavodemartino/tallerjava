<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Men� principal</title>
</head>
<body>
	<f:view>
		<h1>Men� principal</h1>
		<a href="/Intendencia/login.jsf">[ Cerrar sesi�n ]</a>
		<a href="/Intendencia/restricted/useradmin.jsf">[ Usuarios ]</a>
		<a href="/Intendencia/restricted/parkingreport.jsf">[ Estacionamientos ]</a>
		<a href="/Intendencia/restricted/auditreport.jsf">[ Auditor�a ]</a>
	</f:view>
</body>
</html>