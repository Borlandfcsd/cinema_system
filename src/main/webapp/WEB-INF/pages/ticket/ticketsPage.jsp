<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="login"/>
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Tickets</title>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css'/>
    <link rel='stylesheet' href='${contextPath}/resources/css/header.css'/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-sm navbar-custom">
        <a href="<c:url value="/"/>" class="logo navbar-brand">Cinema</a>
        <div class="navbar-collapse collapse" id="navbarCustom">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>">Home</a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link active" href="<c:url value="/admin/movies"/>">Movies</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/movieSessions"/>">Movie Sessions</a>
                    </li>
                </sec:authorize>
            </ul>
            <c:if test="${login != null}">
                <span class="ml-auto navbar-text"> ${login} | </span>
                <form id="logoutForm" method="post" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <a class="nav-link sign-out" onclick="document.forms['logoutForm'].submit()">Sign out</a>
            </c:if>
            <c:if test="${login == null}">
                <a class="nav-link" href="<c:url value="/signPage"/>">Sign in/up</a>
            </c:if>
        </div>
    </nav>
    <hr/>
    <h3 class="title">Tickets for ${session.movie.title} session at <javatime:format value="${session.beginDate}"
                                                                                     style="MS"/></h3>
    <hr/>
    <div class="tickets">
        <table class="table">
            <tr>
                <th>id</th>
                <th>Row</th>
                <th>Place</th>
                <th>Email</th>
                <th>Date</th>
                <th>Price</th>
                <th>Status</th>
                <th></th>
            </tr>
            <c:forEach items="${tickets}" var="ticket">
                <tr>
                    <td>${ticket.id}</td>
                    <td>${ticket.row}</td>
                    <td>${ticket.place}</td>
                    <td>${ticket.user.email}</td>
                    <td><javatime:format value="${ticket.movieSession.beginDate}" style="MS"/></td>
                    <td>${ticket.price}</td>
                    <td>${ticket.placeStatus}</td>
                    <c:if test="${ticket.placeStatus == 'RESERVED'}">
                        <td>
                            <a href="<c:url value='/admin/tickets/sell/${ticket.id}'/>">sell ticket</a>
                        </td>
                        <td>
                            <a href="<c:url value='/admin/tickets/cancel/${ticket.id}'/>">cancel</a>
                        </td>
                    </c:if>
                    <c:if test="${ticket.placeStatus == 'SOLD'}">
                        <td>
                            <a href="<c:url value='/admin/tickets/cancel/${ticket.id}'/>">cancel</a>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
