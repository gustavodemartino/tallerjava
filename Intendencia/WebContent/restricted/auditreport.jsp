<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/estilo.css">
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
<title>Auditoria > Intendencia</title>
</head>
<body>
	<div class="cajaBlancaFondo">
	<f:view>
		<a class="buttonMenu buttonLogout" href="${pageContext.request.contextPath}/login.jsf">Cerrar sesión</a>
		<a class="buttonMenu" href="${pageContext.request.contextPath}/restricted/menu.jsf">Volver</a>
		<h1>Informe de auditoría</h1>
		<br/>
		<h:form>
			<h:outputText value="Desde "></h:outputText>&nbsp;
			<h:inputText title="Desde" value="#{auditorBean.filtroDesde}" />&nbsp;
			<h:outputText value="Hasta "></h:outputText>&nbsp;
			<h:inputText title="Hasta" value="#{auditorBean.filtroHasta}" />&nbsp;&nbsp;&nbsp;
			<h:commandButton styleClass="buttonGrilla" action="#{auditorBean.listarAuditoria()}" value="Filtrar" />
			<h:messages />
		</h:form>
		<br/>
		<br/>
		<div class="grilla">
			<h:dataTable value="#{auditorBean.lista}" var="item">
				<h:column>
					<f:facet name="header"><h:outputText value="Fecha"/></f:facet>
					<h:outputText value="#{item.fechaHora}">
					<f:convertDateTime pattern="yyyy-MM-dd hh:mm"/>
					</h:outputText>
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Operador"/></f:facet>
					<h:outputText value="#{item.operador.name}" />
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Usuario"/></f:facet>
					<h:outputText value="#{item.usuario.name}" />
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Evento"/></f:facet>
					<h:outputText value="#{item.evento}">
					</h:outputText>
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Nivel"/></f:facet>
					<h:outputText value="#{item.nivel}">
					</h:outputText>
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Detalle"/></f:facet>
					<h:outputText value="#{item.detalle}" />
				</h:column>
			</h:dataTable>
		</div>
	</f:view>
	</div>
</body>
</html>