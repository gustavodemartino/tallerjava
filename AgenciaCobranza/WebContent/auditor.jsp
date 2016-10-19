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

<!--Font Awesome-->
<link href="plugins/font-awesome/css/font-awesome.css" rel="stylesheet">

<!--Bootstrap Table-->
<link href="plugins/datatables/media/css/dataTables.bootstrap.css"
	rel="stylesheet">

<!--Page Load Progress Bar [ OPTIONAL ]-->
<link href="plugins/pace/pace.min.css" rel="stylesheet">
<script src="plugins/pace/pace.min.js"></script>

<!--jQuery-->
<script src="js/jquery-2.1.1.min.js"></script>

<!--DataTables-->
<script src="plugins/datatables/media/js/jquery.dataTables.js"></script>
<script src="plugins/datatables/media/js/dataTables.bootstrap.js"></script>

<!--DataTables Sample-->
<script src="js/tables-datatables.js"></script>


<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css">
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>

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
<!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="navbar-brand">Agencia de Cobranza</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="userlist.jsp">Mantenimiento de usuarios</a></li>
            <li><a href="parkinglist.jsp">Reporte de estacionamientos</a></li>
            <li class="active"><a href="auditor.jsp">Reporte de auditoria</a></li>
            <li><a href="logout">Cerrar Sesión</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    
		
    <div class="container theme-showcase" role="main">
		<form method="post" action="auditor.jsp">
			<!-- Main jumbotron for a primary marketing message or call to action -->
		    <div class="jumbotron">
				<h2 class=>Informe de auditoria</h2>
				<br/><br/>
				<label for="filter">Estado </label> 
				<select id="filter" name="filter" onchange="this.form.submit()">
					<option value="info" <%=(filter.equals("info") ? "selected" : "")%>>Todo</option>
					<option value="warning"
						<%=(filter.equals("warning") ? "selected" : "")%>>Alertas y
						errores</option>
					<option value="error" <%=(filter.equals("error") ? "selected" : "")%>>Sólo
						errores</option>
				</select> 
				<label for="date_from">Desde </label> 
				<input id="date_from" type="date" name="date_from" value="<%=date_from%>" /> 
				<label for="date_to">Hasta </label> 
				<input id="date_to" type="date" name="date_to" value="<%=date_to%>" />
					
				<button type="submit" class="btn btn-default" aria-label="Left Align">
				  <span class="fa fa-refresh" aria-hidden="true"></span>
				</button>
				<button type="button" class="btn btn-default" aria-label="Left Align" onclick="previous()">
				  <span class="fa fa-caret-square-o-left" aria-hidden="true"></span>
				</button>
				<button type="button" class="btn btn-default" aria-label="Left Align" onclick="today()">
				  <span class="fa fa-calendar" aria-hidden="true"></span>
				</button>
				<button type="button" class="btn btn-default" aria-label="Left Align" onclick="next()">
				  <span class="fa fa-caret-square-o-right" aria-hidden="true"></span>
				</button>
			</div>
			
			<div id="container">
				<table id="table" class="table table-striped">
				<thead>
					<tr>
						<th>Nivel</th>
						<th>Fecha/Hora</th>
						<th>Tipo</th>
						<th>Usuario</th>
						<th>Ubicacion</th>
						<th>Detalle</th>
					</tr>
				</thead>
				<tbody>
				<%
					int line = 0;
					for (AuditEvent ev : events) {
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
				</tbody>
				</table>
			</div>
		</form>
	</div>
</body>
</html>
