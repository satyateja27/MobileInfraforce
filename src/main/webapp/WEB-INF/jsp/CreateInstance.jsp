<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache"> 
    <meta http-equiv="Cache-Control" content="no-cache"> 
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    
    <title>MSCIaaS | User</title>
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	  
	  <style>
		table {
		    border-collapse: collapse;
		    width: 100%;
		}
		
		th, td {
		    text-align: left;
		    padding: 8px;
		}
		
		tr:nth-child(even){background-color: #f2f2f2}
		
		th {
		    background-color: #5784cc;
		    color: white;
		}
	</style>
	  
</head>
<body>

	<div class = "panel panel-default">
            <div class = "panel-body bg-primary" style=" height:65px">
               <nav class="navbar navbar-light">
                  <div class="container-fluid">
                     <ul class="nav navbar-nav">
                        <li class="nav-item">
                           <a class="nav-link" href="/user/${user.getUser_id()}/dashBoard" style="color:white">User Dashboard</a>
                        </li>
                        <li class="nav-item">
                           <a class="nav-link" href="/user/${user.getUser_id()}/createInstance"style="color:white">Create Instances</a>
                        </li>
                        <li class="nav-item">
                           <a class="nav-link" href="/user/${user.getUser_id()}/monitorInstance" style="color:white">Monitor Instances</a>
                        </li>
                        <li class="nav-item">
                           <a class="nav-link" href="/user/${user.getUser_id()}/userProfile" style="color:white">User Profile</a>
                        </li>
                        <li class="nav-item">
                           <a class="nav-link" href="/user/${user.getUser_id()}/userBilling" style="color:white">User Billing</a>
                        </li>
                     </ul>
                     <ul class="nav navbar-nav navbar-right">
                     	<li class="nav-item">
                           <a class="nav-link" href="#" style="color:white">Hi, ${user.getFirst_name() }</a>
                        </li>
                     	<li class="nav-item">
                           <a class="nav-link" href="/" style="color:white"><span class="glyphicon glyphicon-off"></span> Logout</a>
                        </li>
                     </ul>
                  </div>
               </nav>
            </div>
         </div>
         <div>
         	<div class="col-sm-3"></div>
         	<div class="col-sm-6" style="text-align:center">
			<h1>Create Instances</h1>
			<form method="post" action="/api/instance/create/${user.getUser_id()}">
			<div>
				<input type="text" class="form-control" placeholder="Instance Name" name="instance_name"/><br/>
			</div>
			<div>
				<select class="form-control" placeholder="Select AMI" name="ami_name">
					<option value="default">Select the AMI image</option>
					<option value="ami-1480d574">Marine Sensor AMI</option>
				</select>
			</div><br/>
			<div>
				<select  class="form-control" placeholder="Select No. of CPU" name="num_CPU">
					<option value="default">Select No.of CPU (2.5GHz each)</option>
					<option value="2">2</option>
					<option value="4">4</option>
					<option value="6">6</option>
					<option value="8">8</option>
				</select>
			</div><br/>
			<div>
				<select  class="form-control" placeholder="Select required Storage" name="num_Storage">
					<option value="default">Select required Storage</option>
					<option value="2">2GB</option>
					<option value="4">4GB</option>
					<option value="6">6GB</option>
					<option value="8">8GB</option>
				</select>
			</div><br>
			<div>
				<select class="form-control" placeholder="Select No. of Instance" name="num_instance">
					<option value="default">Select No.of Instances</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select>
			</div><br>
			<div><input type="submit" class="btn btn-primary" value="Create"/></div>
			</form>
			</div>
         	<div class="col-sm-3"></div>
         </div>
	
</body>
</html>