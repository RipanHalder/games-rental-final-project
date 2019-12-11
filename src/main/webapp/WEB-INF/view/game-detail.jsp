<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${game.title}" /></title>
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
					href="${pageContext.request.contextPath}/booking/showAllBookings">All
						Bookings</a></li>
			</security:authorize>


			<li class="navLi"><a
				href="${pageContext.request.contextPath}/game/showAllGames">All
					Games</a></li>
			<security:authorize access="hasRole('ADMIN')">
				<li class="navLi"><a href="${pageContext.request.contextPath}/game/create">Create
				Game</a></li>
				<li class="navLi"><a href="${pageContext.request.contextPath}/category/create">Create
				Category</a></li>
				<li class="navLi"><a href="${pageContext.request.contextPath}/platform/create">Create
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
		class="mainbox col-md-7 col-md-offset-2 col-sm-8 col-sm-offset-4">

		<div class="panel panel-info">
			<div class="panel-heading"
				style="background-color: #00adb5; color: #eeeeee;">
				<div class="panel-title">
					<c:out value="${game.title}" />
				</div>
			</div>

			<div
				style="padding-top: 10px; color: #393e46; font-size: medium; background-color: #eeeeee;"
				class="panel-body">
				<c:if test="${game.quanity > 0}">
				<u>
				<a class="featured-links"
						href="${pageContext.request.contextPath}/booking/showBookingForm?gameId=${game.gameId}&userId=${user.id}">Rent
						Now!</a></u> </c:if> Total Rented - <c:out value="${game.currentRentalQuantity}" />
				<img src="${game.imageUrl}"
					style="width: 90%; margin: 10px 30px 30px 30px; height: 400px;">
				<div style="margin: 10px 30px 30px 30px;">
					<p>
						IGN Rating:
						<c:out value="${game.ignrating}" />
					</p>
					<p>Developers: <c:out value="${game.developers}" /></p>
					<p>Available On: <c:out value="${game.platform}" /> </p>
	
								<p>Category: <c:out value="${game.category}" /> </p>
				<p>Price: $ <c:out value="${game.price}" /> Per Day</p>
				<c:if test="${game.quanity < 1}">
				<p style="color:#00adb5"> OUT OF STOCK</p>
					</c:if>
				<c:if test="${game.quanity > 10}">
				<p>Quantity Available: <c:out value="${game.quanity}" />
						</p>
					</c:if>
					<c:if test="${game.quanity > 0 && game.quanity <= 10}">
				<p style="color:#00adb5">Hurry Up!! Last <c:out value="${game.quanity}" /> left in stock!!!
						</p>
					</c:if>
				
				</div>
			</div>
		</div>
	</div>

</body>
</html>