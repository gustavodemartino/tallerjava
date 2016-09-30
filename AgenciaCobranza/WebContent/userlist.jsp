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

<!--Bootstrap Stylesheet-->
<link href="css/bootstrap.css" rel="stylesheet">

<!--Nifty Stylesheet-->
<link href="css/styles.css" rel="stylesheet">

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
</head>
<body>
	<h1>Administración de Usuarios</h1>
	<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%>
	|
	<a href="logout">Cerrar Sesión</a> |
	<a href="menu.jsp">Menú principal</a>
	<h2 class=>Lista de usuarios</h2>
	<button onclick="window.location.href='new_user'">Nuevo usuario</button>
	<div id="container">
		<div class="boxed">
			<div id="page-content">
				<div class="panel">
					<div class="panel-body">
						<form id="select_user" method="post" action="get_user">
							<input id="selected_user" name="selected_user" type="hidden"
								value="0">
							<table id="demo-dt-basic"
								class="table table-striped table-bordered" cellspacing="0"
								width="100%">
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
											String btnAdmin = "<button class='demo-delete-row btn btn-default btn-xs btn-icon fa fa-user-plus'></button>";
											String btnUser = "<button class='demo-delete-row btn btn-default btn-xs btn-icon fa fa-user'></button>";
									%>
									<tr>
										<td><%=(user.getIsAdmin() ? btnAdmin : btnUser) + " " + user.getShortName()%></td>
										<td><%=user.getName()%></td>
										<td>
											<%
												List<Location> permissions = UserManager.getInstance().getPermissions(user.getId());
													for (Location l : permissions) {
											%> <%="<button class='demo-delete-row btn btn-default btn-xs'>" + l.getName() + "</button>"%>
											<%
												}
											%>
										</td>
										<td><button
												class="demo-delete-row btn btn-default btn-xs btn-icon fa fa-pencil"
												onclick=<%=onclick%> type="submit"></button></td>
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
</body>
</html>