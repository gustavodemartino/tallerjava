<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agencia | Administración</title>

    <!--STYLESHEET-->
    <!--=================================================-->

    <!--Open Sans Font [ OPTIONAL ] -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin" rel="stylesheet">

    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="css/styles.css" rel="stylesheet">

    <!--Font Awesome [ OPTIONAL ]-->
    <link href="plugins/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!--Bootstrap Table [ OPTIONAL ]-->
    <link href="plugins/datatables/media/css/dataTables.bootstrap.css" rel="stylesheet">

    <!--SCRIPT-->
    <!--=================================================-->

    <!--Page Load Progress Bar [ OPTIONAL ]-->
    <link href="../template/plugins/pace/pace.min.css" rel="stylesheet">
    <script src="../template/plugins/pace/pace.min.js"></script>
        
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
                <h1 class="page-header text-overflow"><strong>Panel Administrativos</strong></h1>
                <div class="searchbox">
                    <div class="input-group custom-search-form">
                        Bienvenido, <strong><%= session.getAttribute("userSession") %></strong>
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
                        <h3 class="panel-title">Agregar un nuevo usuario</h3>
                    </div>
                    <div class="panel-body">
                
                        <!-- Inline Form  -->
                        <!--===================================================-->
                        <form class="form-inline">
                            <div class="form-group">
                                <label for="demo-inline-inputmail" class="sr-only">Nombre de usuario</label>
                                <input type="text" placeholder="Nombre de usuario" id="demo-inline-inputmail" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="demo-inline-inputpass" class="sr-only">Password</label>
                                <input type="password" placeholder="Password" id="demo-inline-inputpass" class="form-control">
                            </div>
                            <button class="btn btn-primary" type="submit">Agregar</button>
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
                                    <th>Identificador</th>
                                    <th>Nombre Usuario</th>
                                    <th>Administrativo</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Tiger Nixon</td>
                                    <td>System Architect</td>
                                    <td>Edinburgh</td>
                                    <td>61</td>
                                </tr>
                                <tr>
                                    <td>Rhona Davidson</td>
                                    <td>Integration Specialist</td>
                                    <td>Tokyo</td>
                                    <td>55</td>
                                </tr>
                                <tr>
                                    <td>Charde Marshall</td>
                                    <td>Regional Director</td>
                                    <td>San Francisco</td>
                                    <td>36</td>
                                </tr>
                                <tr>
                                    <td>Haley Kennedy</td>
                                    <td>Senior Marketing Designer</td>
                                    <td>London</td>
                                    <td>43</td>
                                </tr>
                                <tr>
                                    <td>Tatyana Fitzpatrick</td>
                                    <td>Regional Director</td>
                                    <td>London</td>
                                    <td>19</td>
                                </tr>
                                <tr>
                                    <td>Michael Silva</td>
                                    <td>Marketing Designer</td>
                                    <td>London</td>
                                    <td>66</td>
                                </tr>
                                <tr>
                                    <td>Paul Byrd</td>
                                    <td>Chief Financial Officer (CFO)</td>
                                    <td>New York</td>
                                    <td>64</td>
                                </tr>
                                <tr>
                                    <td>Dai Rios</td>
                                    <td>Personnel Lead</td>
                                    <td>Edinburgh</td>
                                    <td>35</td>
                                </tr>
                                <tr>
                                    <td>Jenette Caldwell</td>
                                    <td>Development Lead</td>
                                    <td>New York</td>
                                    <td>30</td>
                                </tr>
                                <tr>
                                    <td>Doris Wilder</td>
                                    <td>Sales Assistant</td>
                                    <td>Sidney</td>
                                    <td>23</td>
                                </tr>
                                <tr>
                                    <td>Angelica Ramos</td>
                                    <td>Chief Executive Officer (CEO)</td>
                                    <td>London</td>
                                    <td>47</td>
                                </tr>
                                <tr>
                                    <td>Gavin Joyce</td>
                                    <td>Developer</td>
                                    <td>Edinburgh</td>
                                    <td>42</td>
                                </tr>
                                <tr>
                                    <td>Jennifer Chang</td>
                                    <td>Regional Director</td>
                                    <td>Singapore</td>
                                    <td>28</td>
                                </tr>
                                <tr>
                                    <td>Brenden Wagner</td>
                                    <td>Software Engineer</td>
                                    <td>San Francisco</td>
                                    <td>28</td>
                                </tr>
                                <tr>
                                    <td>Fiona Green</td>
                                    <td>Chief Operating Officer (COO)</td>
                                    <td>San Francisco</td>
                                    <td>48</td>
                                </tr>
                                <tr>
                                    <td>Shou Itou</td>
                                    <td>Regional Marketing</td>
                                    <td>Tokyo</td>
                                    <td>20</td>
                                </tr>
                                <tr>
                                    <td>Michelle House</td>
                                    <td>Integration Specialist</td>
                                    <td>Sidney</td>
                                    <td>37</td>
                                </tr>
                                <tr>
                                    <td>Suki Burks</td>
                                    <td>Developer</td>
                                    <td>London</td>
                                    <td>53</td>
                                </tr>
                                <tr>
                                    <td>Prescott Bartlett</td>
                                    <td>Technical Author</td>
                                    <td>London</td>
                                    <td>27</td>
                                </tr>
                                <tr>
                                    <td>Gavin Cortez</td>
                                    <td>Team Leader</td>
                                    <td>San Francisco</td>
                                    <td>22</td>
                                </tr>
                                <tr>
                                    <td>Martena Mccray</td>
                                    <td>Post-Sales support</td>
                                    <td>Edinburgh</td>
                                    <td>46</td>
                                </tr>
                                <tr>
                                    <td>Unity Butler</td>
                                    <td>Marketing Designer</td>
                                    <td>San Francisco</td>
                                    <td>47</td>
                                </tr>
                                <tr>
                                    <td>Howard Hatfield</td>
                                    <td>Office Manager</td>
                                    <td>San Francisco</td>
                                    <td>51</td>
                                </tr>
                                <tr>
                                    <td>Hope Fuentes</td>
                                    <td>Secretary</td>
                                    <td>San Francisco</td>
                                    <td>41</td>
                                </tr>
                                <tr>
                                    <td>Vivian Harrell</td>
                                    <td>Financial Controller</td>
                                    <td>San Francisco</td>
                                    <td>62</td>
                                </tr>
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

    <!--jQuery [ REQUIRED ]-->
    <script src="js/jquery-2.1.1.min.js"></script>

    <!--DataTables [ OPTIONAL ]-->
    <script src="plugins/datatables/media/js/jquery.dataTables.js"></script>
	<script src="plugins/datatables/media/js/dataTables.bootstrap.js"></script>

    <!--DataTables Sample [ SAMPLE ]-->
    <script src="js/tables-datatables.js"></script>

    
</body>
</html>