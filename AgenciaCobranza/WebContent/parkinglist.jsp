<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="model.SalesManager"%>
<%@page import="data.ParkingDetail"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-MM-dd");
	SimpleDateFormat dfdt = new SimpleDateFormat("dd-MM-yyyy kk:mm");
	SimpleDateFormat dfh = new SimpleDateFormat("kk:mm");
	String today = sdf.format(new Date());
	String date_from = request.getParameter("date_from");
	if (date_from == null) {
		date_from = today;
	}
	String date_to = request.getParameter("date_to");
	if (date_to == null) {
		date_to = today;
	}
	Date from = sdf.parse(date_from);
	Date to = new Date(sdf.parse(date_to).getTime() + 86400000);
	List<ParkingDetail> sales = SalesManager.getInstance().getSales(from, to);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estacionamientos vendidos</title>
</head>
<body>
	<h1>Estacionamientos vendidos</h1>
	<form id="form-1" method="post" action="">
		<label for="date_from">Desde: </label> <input id="date_from"
			type="date" name="date_from" value="<%=today%>" /> <label
			for="date_to">Desde: </label> <input id="date_to" type="date"
			name="date_to" value="<%=today%>" />
		<button type="submit">Actualizar</button>
	</form>
	<table id="table-1">
		<thead>
			<tr>
				<th>Venta</th>
				<th>Matrícula</th>
				<th>Inicio</th>
				<th>Minutos</th>
				<th>Ticket</th>
				<th>Anulación</th>
				<th>Autorización</th>
				<th>Pago</th>
				<th>Crédito</th>

			</tr>
		</thead>
		<tbody>
			<%
				for (ParkingDetail p : sales) {
			%>
			<%="<tr><td>" + dfdt.format(p.getSaleDateTime()) + "</td><td>" + p.getPlate() + "</td><td>"
						+ dfdt.format(p.getParkingStart()) + "</td><td>" + p.getDuration() + "</td><td>"
						+ p.getSaleTicket() + "</td><td>" + dfdt.format(p.getCancelationDateTime()) + "</td><td>"
						+ p.getCancelationNumber() + "</td><td>" + p.getAmount() + "</td><td>" + p.getCredit()
						+ "</td></tr>"%>
			<%
				}
			%>
		</tbody>
	</table>
</body>
</html>