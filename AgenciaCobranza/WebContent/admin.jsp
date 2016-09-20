<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="data.User, java.util.List, model.UserManager"
%>

<%	
	List<User> users = UserManager.getInstance().getUsers();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agencia | Administración</title>

    <!--STYLESHEET-->
    <!--=================================================-->

    <!--Open Sans Font-->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin" rel="stylesheet">

    <!--Bootstrap Stylesheet-->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!--Nifty Stylesheet-->
    <link href="css/styles.css" rel="stylesheet">

    <!--Font Awesome-->
    <link href="plugins/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!--Bootstrap Table-->
    <link href="plugins/datatables/media/css/dataTables.bootstrap.css" rel="stylesheet"> 

    <!--SCRIPT-->
    <!--=================================================-->

    <!--Page Load Progress Bar [ OPTIONAL ]-->
    <link href="plugins/pace/pace.min.css" rel="stylesheet">
    <script src="plugins/pace/pace.min.js"></script>
        
</head>

<body>
	<div id="container">
		
	<div class="boxed">

        <!--CONTENT CONTAINER-->
        <!--===================================================-->
        <div id="content-container">
        
            <!--Page Title-->
            <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
            <div id="page-title">
                <h1 class="page-header text-overflow"><strong>Panel de Administración de Usuarios</strong></h1>
                <div class="searchbox">
                    <div class="input-group custom-search-form">
                        Bienvenido, <strong><%= ((User)session.getAttribute("userSession")).getName() %></strong> | Cerrar Sesión
                    </div>
                </div>
            </div>
            <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
            <!--End page title-->        
            
            <!--Page content-->
            <!--===================================================-->
            <div id="page-content">
            
                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">Agregar un nuevo Usuario</h3>
                    </div>
                    <div class="panel-body">
                
                        <!-- Inline Form  -->
                        <!--===================================================-->
                        <form class="form-inline" method="post" action="UserServlet">
                            <div class="form-group">
                                <label for="demo-inline-inputmail" class="sr-only">Usuario</label>
                                <input type="text" id="shortName" name="shortName" placeholder="Nickname" id="demo-inline-inputmail" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="demo-inline-inputmail" class="sr-only">Nombre completo</label>
                                <input type="text" id="name" name="name" placeholder="Nombre completo" id="demo-inline-inputmail" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="demo-inline-inputpass" class="sr-only">Contraseña</label>
                                <input type="password" id="password" name="password" placeholder="Contraseña" id="demo-inline-inputpass" class="form-control">
                            </div>
                            <div class="form-group">
                            	<div class="checkbox">
									<label class="form-checkbox form-icon"><input id="admin" name="admin" type="checkbox" checked=""> Administrativo</label>
								</div>
                            </div>
                            <button class="btn btn-primary" type="submit">Agregar</button>
                            <div class="form-group"><%= session.getAttribute("mensaje")==null?"":session.getAttribute("mensaje") %></div>
                        </form>
                        <!--===================================================-->
                        <!-- End Inline Form  -->
                
                    </div>
                </div>             
                
                <!-- Basic Data Tables -->
                <!--===================================================-->
                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">Lista de usuarios disponibles</h3>
                    </div>
                    <div class="panel-body">
                        <table id="demo-dt-basic" class="table table-striped table-bordered" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Usuario</th>
                                    <th>Nombre</th>
                                    <th>Administrativo</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                            	<% for(User user: users){ %>
                                <tr>
                                    <td><%= user.getShortName()  %></td>
                                    <td><%= user.getName()  %></td>
                                    <td><%= (user.getIsAdmin()?"si":"no") %></td>
                                    <td><button class="demo-delete-row btn btn-default btn-xs btn-icon fa fa-pencil"></button>  <button class="demo-delete-row btn btn-default btn-xs btn-icon fa fa-trash"></button></td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!--===================================================-->
                <!-- End Striped Table -->
                
            </div>
            <!--===================================================-->
            <!--End page content-->

        </div>
        <!--===================================================-->
        <!--END CONTENT CONTAINER-->
</div>
	</div>
	<!--===================================================-->
	<!-- END OF CONTAINER -->

    <!--SCRIPT-->
    <!--=================================================-->

    <!--jQuery-->
    <script src="js/jquery-2.1.1.min.js"></script>

    <!--DataTables-->
    <script src="plugins/datatables/media/js/jquery.dataTables.js"></script>
	<script src="plugins/datatables/media/js/dataTables.bootstrap.js"></script>

    <!--DataTables Sample-->
    <script src="js/tables-datatables.js"></script>
    
    <!--Agencia Admin-->
    <script src="js/agencia.min.js"></script>
    
</body>
</html>