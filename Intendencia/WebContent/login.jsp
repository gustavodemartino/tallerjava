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
<title>Login > Intendencia</title>
<meta http-equiv="refresh" content="90; login.jsf">
</head>
<body>
	<div class="cajaBlancaCentrada">
	<f:view>
		<h1>Intendencia</h1>
		<h:form>
			<h:panelGrid columns="2">
				<h:outputLabel value="Usuario" />
				<h:inputText value="#{loginBean.username}" />
				<h:outputLabel value="Contraseña" />
				<h:inputSecret value="#{loginBean.password}" />
				<h:outputText />
				<br/>
				<h:commandButton style="float:right" styleClass="button" action="#{loginBean.login()}" value="Ingresar" />
			</h:panelGrid>
			<h:messages />
		</h:form>
	</f:view>
	</div>
</body>
</html>