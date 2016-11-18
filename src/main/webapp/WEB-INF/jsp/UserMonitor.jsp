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
         	<div class="col-sm-1"></div>
         	<div class="col-sm-10">
         		<form method="post" action="/user/${user.getUser_id()}/monitorInstance">
         		<h3>Select one of the Running Instance for monitoring</h3>
         		<select name="desc" class="form-control" required>
         			<c:forEach items="${Instance}" var="instance">
					<option value="${instance.getInstance_name()}" selected>${instance.getInstance_name()}</option>
					</c:forEach>
				</select><br/>
				<input type="submit" value="Get Stats"/>
				</form>
				<c:if test="${show}">
					<br/>
					<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
				   <script type="text/javascript">
				      google.charts.load('current', {'packages':['gauge']});
				      google.charts.setOnLoadCallback(drawChart);
				      function drawChart() {
				
				        var data = google.visualization.arrayToDataTable([
				          ['Label', 'Value'],
				          ['Memory', 12],
				          ['CPU', 17],
				          ['Network', 28]
				        ]);
				
				        var options = {
				          width: 800, height: 240,
				          redFrom: 90, redTo: 100,
				          yellowFrom:75, yellowTo: 90,
				          minorTicks: 5
				        };
				
				        var chart = new google.visualization.Gauge(document.getElementById('chart_div'));
				
				        chart.draw(data, options);
				
				        setInterval(function() {
				          data.setValue(0, 1, 15 + Math.round(15 * Math.random()));
				          chart.draw(data, options);
				        }, 13000);
				        setInterval(function() {
				          data.setValue(1, 1, 5 + Math.round(25 * Math.random()));
				          chart.draw(data, options);
				        }, 5000);
				        setInterval(function() {
				          data.setValue(2, 1, 10 + Math.round(10 * Math.random()));
				          chart.draw(data, options);
				        }, 26000);
				      }
				    </script>
				    <div id="chart_div" style="width: 1200px; height: 480px;"></div>
				</c:if>
			</div>
         	</div>
         	<div class="col-sm-1"></div>
         </div>
	
</body>
</html>