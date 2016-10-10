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
<title>Estacionamientos > Intendencia</title>
</head>
<body>
	<div class="cajaBlancaFondo">
	<f:view>
		<a class="buttonMenu" href="/Intendencia/login.jsf">Cerrar sesión</a>
		<a class="buttonMenu" href="/Intendencia/restricted/menu.jsf">Menú Principal</a>
		<h1>Estacionamientos</h1>
		<br/>
		<h:form>
			<h:inputText title="Desde" value="#{parkingReportBean.reportFrom}" />
			<h:inputText title="Desde" value="#{parkingReportBean.reportTo}" />
			<h:commandButton action="#{parkingReportBean.update()}"
				value="Filtrar" />
			<h:messages />
		</h:form>
		<br/>
		<h:dataTable value="#{parkingReportBean.sales}" var="sale">
			<h:column>
				<f:facet name="header"><h:outputText value="Venta"/></f:facet>
				<h:outputText value="#{sale.saleTimeStamp}">
				<f:convertDateTime pattern="yyyy-MM-dd hh:mm"/>
				</h:outputText>
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Operador"/></f:facet>
				<h:outputText value="#{sale.operator.name}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Matrícula"/></f:facet>
				<h:outputText value="#{sale.plate}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Inicio"/></f:facet>
				<h:outputText value="#{sale.startTimeStamp}">
				<f:convertDateTime pattern="hh:mm"/>
				</h:outputText>
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Final"/></f:facet>
				<h:outputText value="#{sale.endTimeStamp}">
				<f:convertDateTime pattern="hh:mm"/>
				</h:outputText>
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Ticket"/></f:facet>
				<h:outputText value="#{sale.ticket}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Importe"/></f:facet>
				<h:outputText value="#{sale.floatAmount}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Anulación"/></f:facet>
				<h:outputText value="#{sale.cancelationTimeStamp}" >
				<f:convertDateTime pattern="yyyy-MM-dd hh:mm"/>
				</h:outputText>
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Autorización"/></f:facet>
				<h:outputText value="#{sale.authorization}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Crédito"/></f:facet>
				<h:outputText value="#{sale.floatCredit}" />
			</h:column>
		</h:dataTable>
	</f:view>
	</div>
</body>
</html>
