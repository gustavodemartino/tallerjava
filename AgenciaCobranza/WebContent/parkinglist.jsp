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
	String show = request.getParameter("show");
	if (show == null) {
		show = "20";
	}
	int nShow = Integer.parseInt(show);

	String firstRecord = request.getParameter("firstrecord");
	if (firstRecord == null) {
		firstRecord = "0";
	}
	int nFirstRecord = Integer.parseInt(firstRecord);

	String filter = request.getParameter("filter");
	if (filter == null) {
		filter = "all";
	}

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

	String prev_from = sdf.format(new Date(from.getTime() - to.getTime() + from.getTime()));
	String prev_to = sdf.format(new Date(from.getTime() - 86400000));

	String next_from = sdf.format(to);
	String next_to = sdf.format(new Date(to.getTime() - from.getTime() + to.getTime() - 86400000));

	List<ParkingDetail> sales;
	long total;
	if (filter.equals("active")) {
		total = SalesManager.getInstance().getActiveSalesCount(from, to);
		sales = SalesManager.getInstance().getActiveSales(from, to, nFirstRecord, nShow);
	} else if (filter.equals("canceled")) {
		total = SalesManager.getInstance().getCanceledSalesCount(from, to);
		sales = SalesManager.getInstance().getCanceledSales(from, to, nFirstRecord, nShow);
	} else {
		total = SalesManager.getInstance().getSalesCount(from, to);
		sales = SalesManager.getInstance().getSales(from, to, nFirstRecord, nShow);
	}
	int actualPage = nFirstRecord / nShow;
	int lastPage = (int)total/nShow;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estacionamientos vendidos</title>
<link href="styles.css" rel="stylesheet">
<script>
function previous(){
	document.getElementById("date_from").value = '<%=prev_from%>'
	document.getElementById("date_to").value = '<%=prev_to%>'
	update()	
}

function today(){
	document.getElementById("date_from").value = '<%=today%>'
	document.getElementById("date_to").value = '<%=today%>'
	update()	
}

function next(){
	document.getElementById("date_from").value = '<%=next_from%>'
	document.getElementById("date_to").value = '<%=next_to%>'
	update()
}
function goPage(pagenum){
	document.getElementById("firstrecord").value = pagenum*<%=nShow%>
	document.getElementById("parking").submit()
}
function update(){
	document.getElementById("firstrecord").value = 0	
	document.getElementById("parking").submit()
}
</script>
</head>
<body>
	<h1>Estacionamientos vendidos</h1>
	<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%>
	|
	<a href="logout">Cerrar Sesión</a> |
	<a href="menu.jsp">Menú principal</a>
	<form id="parking" method="post" action="parkinglist.jsp">
		<input type="hidden" id="firstrecord" name="firstrecord" value="<%=nFirstRecord%>"/>
		<div>
			<label for="show">Mostrar</label>
			<select id="show" name="show" onchange="update()">
				<option value="20" <%=(show.equals("20") ? "selected" : "")%>>20</option>
				<option value="50" <%=(show.equals("50") ? "selected" : "")%>>50</option>
				<option value="100" <%=(show.equals("100") ? "selected" : "")%>>100</option>
			</select>
			<%
				if (total == 0) {
					out.print("No hay registros que coincidan con los filtros de búsqueda");
				} else {
					out.print("Mostrando registros " + (nFirstRecord + 1) + " al " + (nFirstRecord + sales.size()) + " de "
							+ total);
				}
			%>			
		</div>
		<select id="filter" name="filter" onchange="update()">
			<option value="all" <%=(filter.equals("all") ? "selected" : "")%>>Todos</option>
			<option value="active" <%=(filter.equals("active") ? "selected" : "")%>>Sólo activos</option>
			<option value="canceled" <%=(filter.equals("canceled") ? "selected" : "")%>>Sólo anulados</option>
		</select>
		<label for="date_from">Desde: </label>
		<input id="date_from" type="date" name="date_from" value="<%=date_from%>" />
		<label for="date_to">Desde: </label>
		<input id="date_to" type="date" name="date_to" value="<%=date_to%>" />
		<button onclick="update()">Actualizar</button>
		<button onclick="previous()">Anterior</button>
		<button onclick="next()">Siguiente</button>
		<button onclick="today()">Hoy</button>
		<table id="table">
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
				%>
			</tbody>
		</table>
		<div>
		
		<%
		if (total > nShow) {
			for (int p = actualPage -3;p<actualPage+4; p++){
				if (p >=0 && p <= lastPage){
					if (p == actualPage - 1){
						out.print("<button onclick='goPage("+p+")'>Anterior</button>");
					} else if (p == actualPage){
						out.print(" "+(p+1)+" ");
					} else if (p == actualPage + 1) {
						out.print("<button onclick='goPage("+ p +")'>Siguiente</button>");
					} else {
						out.print("<button onclick='goPage("+p+")'>"+(p+1)+"</button>");
					}
				}
			}
		}
		%>
		</div>
	</form>
</body>
</html>