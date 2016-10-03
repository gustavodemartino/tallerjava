<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="model.SalesManager"%>
<%@page import="model.Constants"%>
<%@page import="data.Login"%>
<%@page import="data.ParkingDetail"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dfdt = new SimpleDateFormat("dd-MM-yyyy kk:mm");

	String today = sdf.format(new Date());
	String date_from = request.getParameter("date_from");
	if (date_from == null || date_from.equals("")) {
		date_from = today;
	}
	String date_to = request.getParameter("date_to");
	if (date_to == null || date_to.equals("")) {
		date_to = today;
	}
	Date from = sdf.parse(date_from);
	Date to = new Date(sdf.parse(date_to).getTime() + 86400000);

	String prev_from = sdf.format(new Date(from.getTime()-to.getTime()+from.getTime()));
	String prev_to = sdf.format(new Date(from.getTime() - 86400000));

	String next_from = sdf.format(to);
	String next_to = sdf.format(new Date(to.getTime()-from.getTime()+to.getTime()-86400000));

	List<ParkingDetail> sales = SalesManager.getInstance().getSales(from, to);

	String filter = request.getParameter("filter");
	if (filter == null) {
		filter = "all";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estacionamientos vendidos</title>
<link href="styles.css" rel="stylesheet">
<script>
function previous_day(){
	document.getElementById("date_from").value = '<%=prev_from%>'
	document.getElementById("date_to").value = '<%=prev_to%>'
}

function today(){
	document.getElementById("date_from").value = '<%=today%>'
	document.getElementById("date_to").value = '<%=today%>'
}

function next_day(){
	document.getElementById("date_from").value = '<%=next_from%>'
	document.getElementById("date_to").value = '<%=next_to%>'
	}
</script>
</head>
<body>
	<h1>Estacionamientos vendidos</h1>
	<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%>
	|
	<a href="logout">Cerrar Sesión</a> |
	<a href="menu.jsp">Menú principal</a>
	<form method="post" action="parkinglist.jsp">
		<select id="filter" name="filter" onchange="this.form.submit()" >
			<option value="all" <%=(filter.equals("all")?"selected":"")%>>Todos</option>
			<option value="active" <%=(filter.equals("active")?"selected":"")%>>Sólo activos</option>
			<option value="canceled" <%=(filter.equals("canceled")?"selected":"")%>>Sólo anulados</option>
		</select> <label for="date_from">Desde: </label> <input id="date_from"
			type="date" name="date_from" value="<%=date_from%>" /> <label
			for="date_to">Desde: </label> <input id="date_to" type="date"
			name="date_to" value="<%=date_to%>" />
		<button type="submit">Actualizar</button>
		<button onclick="previous_day()">Anterior</button>
		<button onclick="today()">Hoy</button>
		<button onclick="next_day()">Siguiente</button>
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
				if (filter.equals("active")) {
					for (ParkingDetail p : sales) {
						if (!p.getIsCanceled()) {
							out.print("<tr><td>" + dfdt.format(p.getSaleDateTime()) + "</td><td>" + p.getPlate()
									+ "</td><td>" + dfdt.format(p.getParkingStart()) + "</td><td>" + p.getDuration()
									+ "</td><td>" + p.getSaleTicket() + "</td><td></td><td></td><td>" + p.getAmount()
									+ "</td><td></td></tr>");
						}

					}
				} else if (filter.equals("canceled")) {
					for (ParkingDetail p : sales) {
						if (p.getIsCanceled()) {
							out.print("<tr><td>" + dfdt.format(p.getSaleDateTime()) + "</td><td>" + p.getPlate()
									+ "</td><td>" + dfdt.format(p.getParkingStart()) + "</td><td>" + p.getDuration()
									+ "</td><td>" + p.getSaleTicket() + "</td><td>"
									+ dfdt.format(p.getCancelationDateTime()) + "</td><td>" + p.getCancelationNumber()
									+ "</td><td>" + p.getAmount() + "</td><td>" + p.getCredit() + "</td></tr>");
						}
					}
				} else {
					for (ParkingDetail p : sales) {
						out.print("<tr><td>" + dfdt.format(p.getSaleDateTime()) + "</td><td>" + p.getPlate() + "</td><td>"
								+ dfdt.format(p.getParkingStart()) + "</td><td>" + p.getDuration() + "</td><td>"
								+ p.getSaleTicket() + "</td><td>");
						if (p.getIsCanceled()) {
							out.print(dfdt.format(p.getCancelationDateTime()) + "</td><td>" + p.getCancelationNumber()
									+ "</td><td>" + p.getAmount() + "</td><td>" + p.getCredit() + "</td></tr>");
						} else {
							out.print("</td><td></td><td>" + p.getAmount() + "</td><td></td></tr>");
						}
					}
				}
			%>
		</tbody>
	</table>
</body>
</html>