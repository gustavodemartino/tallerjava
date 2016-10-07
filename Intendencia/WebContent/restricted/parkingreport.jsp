<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estacionamientos</title>
</head>
<body>
	<f:view>
		<h1>Estacionamientos</h1>
		<a href="/Intendencia//login.jsf">[ Cerrar sesi�n ]</a>
		<a href="/Intendencia/restricted/menu.jsf">[ Men� Principal ]</a>
		<h:form>
			<h:inputText title="Desde" value="#{parkingReportBean.reportFrom}" />
			<h:inputText title="Desde" value="#{parkingReportBean.reportTo}" />
			<h:commandButton action="#{parkingReportBean.update()}"
				value="Update" />
			<h:messages />
		</h:form>
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
	</f:view>
</body>
</html>
