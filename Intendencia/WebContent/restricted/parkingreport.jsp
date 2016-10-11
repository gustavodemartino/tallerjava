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
		<a class="buttonMenu buttonLogout" href="/Intendencia/login.jsf">Cerrar sesi�n</a>
		<a class="buttonMenu" href="/Intendencia/restricted/menu.jsf">Volver</a>
		<h1>Estacionamientos</h1>
		<br/>
		<h:form>
			<h:outputText value="Desde "></h:outputText>&nbsp;
			<h:inputText title="Desde" value="#{parkingReportBean.reportFrom}" />&nbsp;
			<h:outputText value="Hasta "></h:outputText>&nbsp;
			<h:inputText title="Desde" value="#{parkingReportBean.reportTo}" />&nbsp;&nbsp;&nbsp;
			<h:commandButton styleClass="buttonGrilla" action="#{parkingReportBean.update()}"
				value="Filtrar" />
			<h:messages />
		</h:form>
		<br/>
		<br/>
		<div class="grilla">
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
					<f:facet name="header"><h:outputText value="Matr�cula"/></f:facet>
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
					<f:facet name="header"><h:outputText value="Anulaci�n"/></f:facet>
					<h:outputText value="#{sale.cancelationTimeStamp}" >
					<f:convertDateTime pattern="yyyy-MM-dd hh:mm"/>
					</h:outputText>
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Autorizaci�n"/></f:facet>
					<h:outputText value="#{sale.authorization}" />
				</h:column>
				<h:column>
					<f:facet name="header"><h:outputText value="Cr�dito"/></f:facet>
					<h:outputText value="#{sale.floatCredit}" />
				</h:column>
			</h:dataTable>
		</div>
	</f:view>
	</div>
</body>
</html>
