<%@page import="model.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.List, data.Login, data.User, data.Location, model.UserManager"%>
<%
	List<User> users = UserManager.getInstance().getUsers();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Agencia | Administración</title>

<!--link
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin"
	rel="stylesheet" -->

<!--Bootstrap Stylesheet
<link href="css/bootstrap.css" rel="stylesheet">
-->

<!--Nifty Stylesheet
<link href="css/styles.css" rel="stylesheet">
-->

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

<!--Agencia Admin-->
<script src="js/agencia.min.js"></script>
<script>
function setSelectedUser(userId) {
	document.getElementById("selected_user").value=userId;	
}
</script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css">
	<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
	
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
            <li class="active"><a href="userlist.jsp">Mantenimiento de usuarios</a></li>
            <li><a href="parkinglist.jsp">Reporte de estacionamientos</a></li>
            <li><a href="auditor.jsp">Reporte de auditoria</a></li>
            <li><a href="logout">Cerrar Sesión</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <div class="container theme-showcase" role="main">
      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
		<h2 class=>Usuarios del sistema</h2>
	  </div>
    	 	
		<div id="container">
			<div align="right"><a><img src="${pageContext.request.contextPath}/css/add.png" onclick="window.location.href='new_user'" style="margin-right:15px;"></img></a></div>
			<div class="boxed">
				<div id="page-content">		
					<div class="panel">
						<div class="panel-body">
							<form id="select_user" method="post" action="get_user">
								<input id="selected_user" name="selected_user" type="hidden"
									value="0">
								<table id="demo-dt-basic"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th>Usuario</th>
											<th>Nombre</th>
											<th>Autorizaciones</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<%
											for (User user : users) {
												String onclick = "'setSelectedUser(" + user.getId() + ")'";
												String btnAdmin = "<span style='width:30px' class='demo-delete-row btn btn-default btn-xs btn-icon fa fa-user-plus'></span>";
												String btnUser = "<span style='width:30px' class='demo-delete-row btn btn-default btn-xs btn-icon fa fa-user'></span>";
										%>
										<tr>
											<td><%=(user.getIsAdmin() ? btnAdmin : btnUser) + " " + user.getShortName()%></td>
											<td><%=user.getName()%></td>
											<td>
												<%
													List<Location> permissions = UserManager.getInstance().getPermissions(user.getId());
														for (Location l : permissions) {
												%> <%="<span class='demo-delete-row btn btn-default btn-xs'>" + l.getName() + "</span>"%>
												<%
													}
												%>
											</td>
											<td><button type="submit" class="demo-delete-row btn btn-default btn-xs btn-icon fa fa-pencil" onclick=<%= onclick %>></button></td>
										</tr>
										<%
											}
										%>
									</tbody>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
    </div>
</body>
</html>