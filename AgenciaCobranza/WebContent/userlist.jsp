<%@page import="model.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="data.Login, data.User, java.util.List, model.UserManager"%>

<%
	List<User> users = UserManager.getInstance().getUsers();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Agencia | Administración</title>

<!--Open Sans Font-->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin"
	rel="stylesheet">

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
function editUser(userId, shortName, name, admin) {
	document.getElementById("userid").value=userId;
	document.getElementById("shortName").value =shortName;
	document.getElementById("name").value = name;
	document.getElementById("password").value = "";
	document.getElementById("admin").checked = admin;
	document.getElementById("btnAdd").type="hidden";
	document.getElementById("btnMod").type="submit";
}

function newUser(){
	document.getElementById("btnAdd").type="submit";
	document.getElementById("btnMod").type="hidden";

	document.getElementById("userid").value = 0;
	document.getElementById("shortName").value ="";
	document.getElementById("name").value = "";
	document.getElementById("password").value = "";
	document.getElementById("admin").checked = false;	
}

</script>

</head>

<body>
	<div id="container">

		<div class="boxed">

			<div id="content-container">

				<div id="page-title">
					<h1 class="page-header text-overflow">
						<strong>Administración de Usuarios</strong>
					</h1>
					<div class="searchbox">
						<div class="input-group custom-search-form">
							<strong><%=((Login) session.getAttribute(Constants.IDENTFIER_SESSION_LOGIN_INFO)).getUser().getName()%></strong>
							| <a href="logout">Cerrar Sesión</a>
						</div>
					</div>
				</div>
				<a href="menu.jsp">Menú principal</a>
				<div id="page-content">

					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">Agregar un nuevo Usuario</h3>
						</div>
						<div class="panel-body">

							<form class="form-inline" method="post" action="user_servlet">
								<input type="hidden" id="userid" name="userid" value="0">
								<div class="form-group">
									<label for="demo-inline-inputmail" class="sr-only">Usuario</label>
									<input type="text" id="shortName" name="shortName"
										placeholder="Nickname" id="demo-inline-inputmail"
										class="form-control" required>
								</div>
								<div class="form-group">
									<label for="demo-inline-inputmail" class="sr-only">Nombre
										completo</label> <input type="text" id="name" name="name"
										placeholder="Nombre completo" id="demo-inline-inputmail"
										class="form-control" required>
								</div>
								<div class="form-group">
									<label for="demo-inline-inputpass" class="sr-only">Contraseña</label>
									<input type="password" id="password" name="password"
										placeholder="Contraseña" id="demo-inline-inputpass"
										class="form-control">
								</div>
								<div class="form-group">
									<div class="checkbox">
										<!-- <label class="form-checkbox form-icon"> -->
										<input id="admin" name="admin" type="checkbox">
										Administrativo</label>
									</div>
								</div>
								<input id="btnAdd" class="btn btn-primary" type="submit"
									value="Agregar" />
								<input id="btnMod" class="btn btn-primary"
									type="hidden" value="Modificar" /> <input
									id="btnNew" class="btn btn-primary" value="Nuevo"
									onclick="newUser()" />

								<div class="form-group"><%=session.getAttribute("mensaje") == null ? "" : session.getAttribute("mensaje")%></div>
							</form>
						</div>
					</div>

					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">Lista de usuarios disponibles</h3>
						</div>
						<div class="panel-body">
							<table id="demo-dt-basic"
								class="table table-striped table-bordered" cellspacing="0"
								width="100%">
								<thead>
									<tr>
										<th>Usuario</th>
										<th>Nombre</th>
										<th>Administrativo</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<%
										for (User user : users) {
									%>
									<tr>
										<td><%=user.getShortName()%></td>
										<td><%=user.getName()%></td>
										<td><%=(user.getIsAdmin() ? "si" : "no")%></td>
										<td><button
												class="demo-delete-row btn btn-default btn-xs btn-icon fa fa-pencil"
												onclick=<%="'editUser(" + user.getId() + ", \"" + user.getShortName() + "\", \"" + user.getName()
						+ "\", " + user.getIsAdmin() + ")'"%>></button>
											<button
												class="demo-delete-row btn btn-default btn-xs btn-icon fa fa-trash"></button></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>