<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.UserManager"%>
<%@page import="data.Location"%>
<%@page import="data.Login"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.Constants, data.User"%>
<%
	User user = (User) session.getAttribute(Constants.SESSION_IDENTFIER_USER_INFO);
	if (user == null) {
		user = new User(0, "", "", false);
	}
	String title;
	String button;
	List<Location> currentPermissions;
	List<Location> missingPermissions;
	if (user.getId() == 0) {
		title = "Nuevo usuario";
		button = "Crear";
		currentPermissions = new ArrayList<Location>();
		missingPermissions = new ArrayList<Location>();
	} else {
		title = "Modificar usuario";
		button = "Modificar";
		currentPermissions = UserManager.getInstance().getPermissions(user.getId());
		missingPermissions = UserManager.getInstance().getMissingPermission(user.getId());
	}
	String message = (String) session.getAttribute("mensaje");
	if (message == null) {
		message = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuarios</title>
<script>
	function addPermission() {
		document.getElementById("action").value = "add_one";
	}
	function addAllPermissions() {
		document.getElementById("action").value = "add_all";
	}
	function removePermission() {
		document.getElementById("action").value = "rem_one";
	}
	function removeAllPermissions() {
		document.getElementById("action").value = "rem_all";
	}
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css">
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
<!--Font Awesome-->
<link href="plugins/font-awesome/css/font-awesome.css" rel="stylesheet">
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
        <h1><%=title%></h1>
        	<form method="post" action="modify_user">
			<input type="hidden" id="userid" name="userid" value=<%=user.getId()%>>
				<div class="form-group">
					<label for="shortName">Usuario</label>
					<input class="form-control" type="text" id="shortName" name="shortName" value="<%=user.getShortName()%>" placeholder="Nickname" required />
				</div>
				
				<div class="form-group">
					<label for="name">Nombre completo</label>
					<input class="form-control" type="text" id="name" name="name" value="<%=user.getName()%>" placeholder="Nombre completo" required />
				</div>
				
				<div class="form-group">
					<label for="password">Contraseña</label>
					<input class="form-control" type="password" id="password" name="password" placeholder="Contraseña" required />
				</div>
				
				<div class="checkbox">
				    <label>
				      <input type="checkbox" id="admin" name="admin" <%=(user.getIsAdmin() ? "checked" : "")%>> Administrador
				    </label>
				</div>
				
				<button class="btn btn-lg btn-primary btn-block" id="btnAdd" type="submit"><%=button%></button>

			<%=message%>
		</form>
      </div>
    </div>
    
    <div class="container">
	<h2>Permisos</h2>
	<form method="post" action="permit_user">
		<input id="action" name="action" type="hidden" value="none">
		<div class="form-group">
			<div style="float:left;width:45%" class="form-group">
				<p>Permisos inactivos</p>
				<select id="to_add_location" name="to_add_location" size="10" style="width: 100%">
					<%
						for (Location l : missingPermissions) {
							out.print("<option value ='" + l.getId() + "'>" + l.getName() + "</option>");
						}
					%>
				</select>
			</div>
			<div style="float:left;padding-left: 3%;" class="form-group">
				<p>Acciones</p>
				<table>
					<tr>
						<td align="center">
							<button type="button" class="btn btn-default" aria-label="Left Align" onclick="addPermission()" style="width: 40px;height: 40px;">
							  <span class="fa fa-angle-right" aria-hidden="true"></span>
							</button>
						</td>
					</tr>
					<tr>
						<td align="center">
							<button type="button" class="btn btn-default" aria-label="Left Align" onclick="addAllPermissions()" style="width: 40px;height: 40px;">
							  <span class="fa fa-angle-double-right" aria-hidden="true"></span>
							</button>
						</td>
					</tr>
					<tr>
						<td align="center">
							<button type="button" class="btn btn-default" aria-label="Left Align" onclick="removePermission()" style="width: 40px;height: 40px;">
							  <span class="fa fa-angle-left" aria-hidden="true"></span>
							</button>
						</td>
					</tr>
					<tr>
						<td align="center">
							<button type="button" class="btn btn-default" aria-label="Left Align" onclick="removeAllPermissions()" style="width: 40px;height: 40px;">
							  <span class="fa fa-angle-double-left" aria-hidden="true"></span>
							</button>
						</td>
					</tr>
				</table>
			</div>
			<div style="float:right;width:45%" class="form-group">
				<p>Permisos activos</p>
				<select id="to_remove_location" name="to_remove_location" size="10" style="width: 100%">
							<%
								for (Location l : currentPermissions) {
									out.print("<option value ='" + l.getId() + "'>" + l.getName() + "</option>");
								}
							%>
				</select>
			</div>
		</div>
	</form>
	<br/><br/><br/>
	<form method="post" action="delete_user">
		<input id="userid" name="userid" type="hidden" value=<%=user.getId()%>>
		<button class="btn btn-lg btn-danger btn-block" id="btnDlt" <%=(user.getId() == 0 ? "disabled" : "")%> >Eliminar este usuario</button>
	</form>
	<br/>
	<button class="btn btn-lg btn-default btn-block" onclick="window.location.href='userlist.jsp'">Volver</button>
	</div>
</body>
</html>
