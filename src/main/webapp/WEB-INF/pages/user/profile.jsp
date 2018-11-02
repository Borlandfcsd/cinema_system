<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="login"/>
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Profile</title>
    <script src="${contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css'/>
    <link rel='stylesheet' href='${contextPath}/resources/css/header.css'/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-sm navbar-custom">
        <a href="<c:url value="/"/>" class="logo navbar-brand">Cinema</a>
        <div class="navbar-collapse collapse" id="navbarCustom">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="<c:url value="/"/>">Home</a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/movies"/>">Movies</a>
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
    <c:if test="${message != null}">
        <div class="alert alert-success" role="alert">
                ${message}
        </div>
    </c:if>
    <hr/>
    <h3 class="title">Profile</h3>
    <hr/>
    <div class="row">
        <c:if test="${edit == false}">
            <table>
                <tr>
                    <th>First name:</th>
                    <td>${userInfo.firstName}</td>
                </tr>
                <tr>
                    <th>Last name:</th>
                    <td>${userInfo.lastName}</td>
                </tr>
            </table>
            <a href="<c:url value="/profile/editProfile"/>">Change personal information</a>
        </c:if>

        <c:if test="${edit == true}">
            <form:form modelAttribute="userInfo" action="/profile/editProfile/save">
                <fieldset>
                    <legend>Personal Information</legend>
                    <div hidden="hidden" class="form-group">
                        <spring:bind path="id">
                            <form:label path="id">ID:</form:label>
                            <form:input path="id" type="text" readonly="readonly"/>
                        </spring:bind>
                    </div>
                    <spring:bind path="firstName">
                        <div class="form-group ">

                            <form:label path="firstName">First name:</form:label>
                            <form:input path="firstName" type="text"/>
                            <form:errors class="alert alert-danger" path="firstName"/>

                        </div>
                    </spring:bind>
                    <spring:bind path="lastName">
                        <div class="form-group ${status.error ? 'alert alert-danger' : ''}">
                            <form:label path="lastName">Last Name:</form:label>
                            <form:input path="lastName" type="text"/>
                            <form:errors class="alert alert-danger" path="lastName"/>
                        </div>
                    </spring:bind>
                </fieldset>
                <fieldset>
                    <legend>Change password</legend>
                    <div class="row">
                        <spring:bind path="password">
                            <div class="form-group col-12 ${status.error ? 'alert alert-danger' : ''}">
                                <form:label path="password">Password:</form:label>
                                <form:input class="form-control" type="password" path="password"/>
                            </div>
                            <form:errors class="alert alert-danger" path="password"/>
                        </spring:bind>
                    </div>
                    <div class="row">
                        <spring:bind path="confirmPassword">
                            <div class="form-group col-12 ${status.error ? 'alert alert-danger' : ''}">
                                <form:label path="confirmPassword">Confirm password:</form:label>
                                <form:input class="form-control" type="password" path="confirmPassword"/>

                            </div>
                            <form:errors class="alert alert-danger" path="confirmPassword"/>
                        </spring:bind>
                    </div>
                </fieldset>
                <div class="form-group">
                    <form:button type="submit">Save</form:button>
                </div>
            </form:form>
        </c:if>
    </div>

    <h3>My tickets:</h3>

    <div class="tickets">
        <table class="table">
            <tr>
                <th></th>
                <th>Title</th>
                <th>Date</th>
                <th>Row</th>
                <th>Place</th>
                <th>Status</th>
                <th>Price</th>
            </tr>
            <c:forEach items="${userInfo.tickets}" var="ticket" varStatus="i">
                <tr>
                    <td>${i.count}</td>
                    <td>${ticket.movieSession.movie.title}</td>
                    <td><javatime:format value="${ticket.movieSession.beginDate}" style="MS"/></td>
                    <td>${ticket.row}</td>
                    <td>${ticket.place}</td>
                    <td>${ticket.placeStatus}</td>
                    <td>${ticket.price}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
