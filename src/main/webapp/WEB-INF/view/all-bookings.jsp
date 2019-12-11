<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Bookings</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
body {
	font-family: Palatino, Helvetica, sans-serif;
	background-color: #222831;
	color: #eeeeee;
}

h2 {
	color: #eeeeee;
	font-size: 38px;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #CDCDCD
}

tr:nth-child(odd) {
	background-color: whitesmoke
}

th {
	background-color: #00adb5;
	color: #eeeeee;
}

ul.b {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #00adb5;
}

li {
	float: left;
}

li.navLi a.active {
	color: #222831;
	background-color: #eeeeee;
}

li a, .dropbtn {
	display: block;
	color: #eeeeee;
	font-size: medium;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	margin: 1px 0px 0px 0px;
}

li a:hover, .dropdown:hover .dropbtn {
	background-color: #eeeeee;
}

li.dropdown {
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #00adb5;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: #222831;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #eeeeee;
}

.dropdown:hover .dropdown-content {
	display: block;
}

#logoutButton {
	background-color: #00adb5;
	border: 0;
	color: #eeeeee;
	font-size: medium;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 0;
	padding: 13px 16px 0px 16px;
	cursor: pointer;
}
/* unvisited link */
a:link {
	color: #393e46;
}

/* visited link */
a:visited {
	color: #393e46;
}

/* mouse over link */
a:hover {
	color: #00adb5;
}

/* selected link */
a:active {
	color: #393e46;
}
#searchGame {
  width: 100%;
  font-size: 16px;
  padding: 12px 20px 12px 40px;
  border: 1px solid #ddd;
  margin-bottom: 12px;
  color: #393e46;
}
</style>
</head>
<body>
	<div style="margin: 10px 10px;">
		<ul class="b">
			<li class="navLi"><a href="${pageContext.request.contextPath}">Home</a></li>
			<li class="navLi"><a
				href="${pageContext.request.contextPath}/booking/myBookings?userId=${user.id}">My
					Bookings</a></li>
			<security:authorize access="hasRole('ADMIN')">
				<li class="navLi"><a
					href="${pageContext.request.contextPath}/booking/showAllBookings"
					class="active">All Bookings</a></li>
			</security:authorize>


			<li class="navLi"><a
				href="${pageContext.request.contextPath}/game/showAllGames">All
					Games</a></li>
			<security:authorize access="hasRole('ADMIN')">
				<li class="navLi"><a
					href="${pageContext.request.contextPath}/game/create">Create
						Game</a></li>
				<li class="navLi"><a
					href="${pageContext.request.contextPath}/category/create">Create
						Category</a></li>
				<li class="navLi"><a
					href="${pageContext.request.contextPath}/platform/create">Create
						Platform</a></li>
			</security:authorize>
			<li class="navLi"><a
					href="${pageContext.request.contextPath}/aboutme">About
						Me</a>
			</li>
			<li class="navLi">
				<!-- Add a logout button --> <form:form
					action="${pageContext.request.contextPath}/logout" method="POST">

					<input type="submit" value="Logout" id="logoutButton" />

				</form:form>
			</li>
			
		</ul>
	</div>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div id="loginbox" style="margin-top: 50px;"
		class="mainbox col-md-10 col-md-offset-1 col-sm-1 col-sm-offset-1">

		<div class="panel panel-info">

			<div class="panel-heading"
				style="background-color: #00adb5; color: #eeeeee;">
				<i class="glyphicon glyphicon-search"></i><input type="text"
					id="searchGame" onkeyup="searchGameFunc()"
					placeholder="Search By Games.." title="Type in a game name">
				<div class="panel-title">All Bookings</div> 
			</div>

			<div
				style="padding-top: 30px; color: #393e46; font-size: medium; background-color: #eeeeee;"
				class="panel-body"><div style="margin: 0px 0px 25px 0px">
				<form action="${pageContext.request.contextPath}/game/games-sales-chart">
					<button type="submit" style="color:#eeeeee; background-color:#00adb5; padding: 10px 10px;">Sales Chart</button>
				</form></div> 
				<div class="container"
					style="width: 80%; margin: 0px 10px 0px 100px;">
					<div class="table-responsive">
						<table class="table table-hover" id="all-bookings">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">Game</th>
									<th scope="col" style="cursor: pointer;"
										onclick="sortMyTable(1)">IGN Rating</th>
									<th scope="col">Premium</th>
									<th scope="col">Booking Date</th>
									<th scope="col">Return Date</th>
									<th scope="col"">Total Amount</th>
									<th scope="col">Paid?</th> 
									<th scope="col">Returned?</th>
									<th scope="col">Delete</th>
								</tr>
							</thead>
							<c:forEach var="booking" items="${allBookings}"
								varStatus="status">
								<tbody>
									<tr>
										<th scope="row"><c:out value="${status.count}" /></th>
										<td><u><a class="featured-links"
												href="${pageContext.request.contextPath}/game/showGame?gameId=${booking.game.gameId}"><c:out
														value="${booking.game.title}" /></a></u></td>
										<td><c:out value="${booking.game.ignrating}" /></td>
										<td><c:out value="${booking.game.premium}" /></td>
										<td><c:out value="${booking.bookingDate}" /></td>
										<td><c:out value="${booking.returnDate}" /></td>
										<td>$ &nbsp;<c:out value="${booking.bookingAmount}" /></td>
										<td><c:if test="${booking.paid == 'No'}">No</c:if> <c:if
												test="${booking.paid == 'Yes'}">Yes</c:if></td>
										<td><c:if test="${booking.returned == 'No'}">No
											</c:if> <c:if test="${booking.returned == 'Returned'}">Yes
											</c:if></td>

										<td><u><a
												href="${pageContext.request.contextPath}/booking/deleteFromAllBookings?gameId=${booking.game.gameId}&bookingId=${booking.bookingId}">
													Delete </a></u></td>
									</tr>
								</tbody>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		function sortMyTable(n) {
			var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
			table = document.getElementById("all-bookings");
			switching = true;
			//Set the sorting direction to ascending:
			dir = "asc";
			/*Make a loop that will continue until
			no switching has been done:*/
			while (switching) {
				//start by saying: no switching is done:
				switching = false;
				rows = table.rows;
				/*Loop through all table rows (except the
				first, which contains table headers):*/
				for (i = 1; i < (rows.length - 1); i++) {
					//start by saying there should be no switching:
					shouldSwitch = false;
					/*Get the two elements you want to compare,
					one from current row and one from the next:*/
					x = rows[i].getElementsByTagName("TD")[n];
					y = rows[i + 1].getElementsByTagName("TD")[n];
					/*check if the two rows should switch place,
					based on the direction, asc or desc:*/
					if (dir == "asc") {
						/* if (n == 1) { */
							if (parseFloat(x.innerHTML) > parseFloat(y.innerHTML)) {
								//if so, mark as a switch and break the loop:
								shouldSwitch = true;
								break;
							}
						/* }  else {
							if (x.innerHTML.toLowerCase() > y.innerHTML
									.toLowerCase()) {
								//if so, mark as a switch and break the loop:
								shouldSwitch = true;
								break;
							}
						} */ 
					} else if (dir == "desc") {
						/* if (n == 1) {  */
							if (parseFloat(x.innerHTML) < parseFloat(y.innerHTML)) {
								//if so, mark as a switch and break the loop:
								shouldSwitch = true;
								break;
							}
						/* } else {
							if (x.innerHTML.toLowerCase() < y.innerHTML
									.toLowerCase()) {
								//if so, mark as a switch and break the loop:
								shouldSwitch = true;
								break;
							}
						} */
					}
				}
				if (shouldSwitch) {
					/*If a switch has been marked, make the switch
					and mark that a switch has been done:*/
					rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
					switching = true;
					//Each time a switch is done, increase this count by 1:
					switchcount++;
				} else {
					/*If no switching has been done AND the direction is "asc",
					set the direction to "desc" and run the while loop again.*/
					if (switchcount == 0 && dir == "asc") {
						dir = "desc";
						switching = true;
					}
				}
			}
		}

		function searchGameFunc() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("searchGame");
			filter = input.value.toUpperCase();
			table = document.getElementById("all-bookings");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[0];
				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
		
		
	</script>

</body>
</html>