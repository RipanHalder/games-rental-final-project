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
<title>Booking form</title>
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
			<li class="navLi"><a class="active"
				href="${pageContext.request.contextPath}/booking/myBookings?userId=${user.id}">My
					Bookings</a></li>
			<security:authorize access="hasRole('ADMIN')">
				<li class="navLi"><a
					href="${pageContext.request.contextPath}/booking/showAllBookings">All
						Bookings</a></li>
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
				<div class="panel-title">Your Bookings</div>
			</div>

			<div
				style="padding-top: 30px; color: #393e46; font-size: medium; background-color: #eeeeee;"
				class="panel-body">
				<div class="container"
					style="width: 80%; margin: 0px 10px 0px 100px;">
					<div class="table-responsive">
						<table class="table table-hover" id="allGamesTable">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">Game</th>
									<th scope="col">IGN Rating</th>
									<th scope="col">Premium</th>
									<th scope="col">Booking Date</th>
									<th scope="col">Return Date</th>
									<th scope="col">Total Amount</th>
									<th scope="col">Paid?</th>
									<th scope="col">Return</th>
									<security:authorize access="hasRole('ADMIN')">
										<th scope="col">Delete</th>
									</security:authorize>
								</tr>
							</thead>
							<c:forEach var="booking" items="${myBookings}" varStatus="status">
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
										<td><c:if test="${booking.paid == 'No'}">
												<form method="post" action="${payPalConfig.posturl}">
													<input type="hidden" name="upload" value="1" /> <input
														type="hidden" name="return"
														value="${payPalConfig.returnurl}" /> <input type="hidden"
														name="cmd" value="_cart" /> <input type="hidden"
														name="business" value="${payPalConfig.business}" />
													<c:set var="count" value="0" scope="page" />

													<c:set var="count" value="${count + 1}" scope="page" />
													<input type="hidden" name="item_name_${count}"
														value="${booking.game.title}" /> <input type="hidden"
														name="amount_${count}" value="${booking.bookingAmount}" />
													<input type="image"
														src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif" />
												</form>
											</c:if> <c:if test="${booking.paid == 'Yes'}">Paid</c:if></td>
										<td><c:if test="${booking.paid == 'No'}">
												<a class="isDisabled"
													href="${pageContext.request.contextPath}/booking/myBookings?userId=${user.id}">Return</a>
											</c:if> <c:if
												test="${booking.returned == 'No' && booking.paid == 'Yes'}">
												<a
													href="${pageContext.request.contextPath}/booking/returnBooking?gameId=${booking.game.gameId}&bookingId=${booking.bookingId}&userId=${user.id}">
													Return</a>
											</c:if> <c:if
												test="${booking.returned == 'Returned' && booking.paid == 'Yes'}">
												<c:out value="${booking.returned}" />
											</c:if></td>

										<td><security:authorize access="hasRole('ADMIN')">
												<a
													href="${pageContext.request.contextPath}/booking/deleteBooking?gameId=${booking.game.gameId}&bookingId=${booking.bookingId}&userId=${user.id}">
													Delete </a>
											</security:authorize></td>
									</tr>
								</tbody>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function searchGameFunc() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("searchGame");
			filter = input.value.toUpperCase();
			table = document.getElementById("allGamesTable");
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