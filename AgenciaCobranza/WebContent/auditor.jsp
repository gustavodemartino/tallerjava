<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="model.Constants"%>
<%@page import="model.AuditManager"%>
<%@page import="data.Login"%>
<%@page import="data.AuditEvent"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dfdt = new SimpleDateFormat("yyyy-MM-dd kk:mm");

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

	String filter = request.getParameter("filter");
	if (filter == null) {
		filter = "info";
	}
	int level = filter.equals("error") ? 3 : filter.equals("warning") ? 2 : 1;
	List<AuditEvent> events = AuditManager.getInstance().getEvents(from, to, level);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Informe de auditoria</title>
<link href="styles.css" rel="stylesheet">
<script>
function previous() {
	document.getElementById("date_from").value = '<%=prev_from%>'
	document.getElementById("date_to").value = '<%=prev_to%>'
}

function today(){
	document.getElementById("date_from").value = '<%=today%>'
	document.getElementById("date_to").value = '<%=today%>'
}

function next(){
	document.getElementById("date_from").value = '<%=next_from%>'
	document.getElementById("date_to").value = '<%=next_to%>'
}

function showDetail(puDetail) {
	document.getElementById(puDetail).classList.toggle('show');
}
</script>
</head>
<body>
	<h1>Informe de auditoria</h1>
	<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%>
	|
	<a href="logout">Cerrar Sesión</a> |
	<a href="menu.jsp">Menú principal</a>
	<form method="post" action="auditor.jsp">
		<select id="filter" name="filter" onchange="this.form.submit()">
			<option value="info" <%=(filter.equals("info") ? "selected" : "")%>>Todo</option>
			<option value="warning"
				<%=(filter.equals("warning") ? "selected" : "")%>>Alertas y
				errores</option>
			<option value="error" <%=(filter.equals("error") ? "selected" : "")%>>Sólo
				errores</option>
		</select> <label for="date_from">Desde: </label> <input id="date_from"
			type="date" name="date_from" value="<%=date_from%>" /> <label
			for="date_to">Desde: </label> <input id="date_to" type="date"
			name="date_to" value="<%=date_to%>" />
		<button type="submit">Actualizar</button>
		<button onclick="previous()">Anterior</button>
		<button onclick="next()">Siguiente</button>
		<button onclick="today()">Hoy</button>
	</form>
	<table>
		<%
			int line = 0;
			for (AuditEvent ev : events) {
				line++;
				out.print("<tr><td>" + ev.getLevel() + "</td><td>" + dfdt.format(ev.getDateTime()) + "</td><td>"
						+ ev.getSType() + "</td><td>"
						+ (ev.getUser() == null ? "" : ev.getUser().getName())
						+ "</td><td>" + (ev.getLocation() == null ? "" : ev.getLocation().getName()) + "</td><td>");
				if (ev.getDetail() != null) {
					out.print("<div class='tooltip'>Detalles<span class='tooltiptext'>"
							+ ev.getDetail().replaceAll("\n", "<br />") + "</span></div>");
				}
				out.print("</td></tr>\n");
			}
		%>
	</table>
</body>
</html>
