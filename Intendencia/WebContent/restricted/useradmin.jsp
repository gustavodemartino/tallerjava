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
		<h:form>
			<a class="buttonMenu buttonLogout" href="/Intendencia/login.jsf">Cerrar sesión</a>
			<a class="buttonMenu" href="/Intendencia/restricted/menu.jsf">Volver</a>
			<h1>Administración de usuarios</h1>
			<br/>		
			<fieldset>
	    		<legend><strong><h:outputText value="#{usuarioBean.accionEtiqueta}"></h:outputText></strong></legend>				
				<h:outputText value="Identificador: "></h:outputText>
				<h:inputText title="Identificador" value="#{usuarioBean.usuarioId}" />
				&nbsp;
				<h:outputText value=" Nombre: "></h:outputText>
				<h:inputText title="Nombre" value="#{usuarioBean.usuarioNombre}" />
				&nbsp;
				<h:outputText value=" Clave: "></h:outputText>
				<h:inputSecret title="Clave" value="#{usuarioBean.usuarioClavePlana}" />
				&nbsp;&nbsp;&nbsp;
				<h:commandButton styleClass="buttonGrilla" action="#{usuarioBean.nuevoUsuario()}" value="#{usuarioBean.botonEtiqueta}" />&nbsp;
				<h:commandButton styleClass="buttonGrilla" action="#{usuarioBean.limpiarVariables()}" value="X" />
			</fieldset>		
			<br/>
			<h:messages errorStyle="display:inline;background:#f9a1a1;margin:-40px;list-style-type:none;padding:8px;" infoStyle="display:inline;background:#ffefb0;margin:-40px;list-style-type:none;padding:8px;" /> 
			<br/>
			<div class="grilla">				
				<h:dataTable value="#{usuarioBean.lista}" var="item">
					<h:column>
						<f:facet name="header"><h:outputText value="Identificador"/></f:facet>
						<h:outputText value="#{item.id}">
						</h:outputText>
					</h:column>
					<h:column>
						<f:facet name="header"><h:outputText value="Nombre"/></f:facet>
						<h:outputText value="#{item.name}" />
					</h:column>
					<h:column>
						<f:facet name="header"><h:outputText value=""/></f:facet>
						<h:commandButton styleClass="buttonGrilla" action="#{usuarioBean.cargarUsuario(item)}" value="Modificar" />&nbsp;
						<h:commandButton styleClass="buttonGrilla" action="#{usuarioBean.eliminarUsuario(item)}" value="Eliminar" />
					</h:column>
				</h:dataTable>
			</div>
		</h:form>
	</f:view>
	</div>
</body>
</html>