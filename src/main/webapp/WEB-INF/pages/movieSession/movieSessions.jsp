<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Movie Sessions</title>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css' />
    <link rel='stylesheet' href='${contextPath}/resources/css/header.css' />
    <script src="${contextPath}/webjars/jquery/3.3.1-1/jquery.js"></script>
    <script src="${contextPath}/webjars/vue/2.5.13/vue.js"></script>
</head>
<body>
    <style>
        ul{
            margin-top: 15px;
        }
        h2{
            margin: 25px 0;
        }
        h3{
            margin: 15px 0;
        }
        .custom-form {
             margin-top: 15px;
         }
        fieldset {
            border: 1px groove #ddd !important;
            padding: 0 1.4em 1.4em 1.4em !important;
            margin: 0 0 1.5em 0 !important;
            -webkit-box-shadow:  0px 0px 0px 0px #000;
            box-shadow:  0px 0px 0px 0px #000;
        }
        legend {
            text-align: center;
        }

        .timetable{
            margin: 0 auto;
        }
        .timetable-header {
            text-align: center;
        }
        .timetable-cell{
            display: inline-block;
            border: 1px solid black;
            background-color: rgba(255, 128, 38, 0.84);
            color: white;
            text-align: center;
        }

        .timetable-cell-time{
            border-bottom: 1px solid black;
            padding: 2px 5px;
        }
        .timetable-cell-title{
            background-color: #0b93ff;
            padding: 2px 10px;
        }
        .timetable-cell-title.free-time{
            background-color: #9fa8a8;
        }
        #duration-hint{
            font-size: 10px;
        }
    </style>

    <div class="container">
        <nav class="navbar navbar-expand-sm navbar-custom">
            <a href="<c:url value="/"/>" class="logo navbar-brand">Cinema</a>
            <div class="navbar-collapse collapse" id="navbarCustom">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/"/>">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/movies"/>">Movies</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="<c:url value="/movieSessions"/>">Movie Sessions</a>
                    </li>
                </ul>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <span class="ml-auto navbar-text"> ${pageContext.request.userPrincipal.name} | </span>
                    <form id="logoutForm" method="post" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <a  class="nav-link sign-out" onclick="document.forms['logoutForm'].submit()">Sign out</a>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <a  class="nav-link" href="<c:url value="/signPage"/>">Sign in/up</a>
                </c:if>
            </div>
        </nav>
        <c:if test="${message != null}">
            <div class="alert alert-success" role="alert">
                <strong>Session</strong>${message}
            </div>
        </c:if>
        <hr/>
        <h3 class="title">Sessions</h3>
        <hr/>

        <div class="row">
            <c:if test="${!empty timetable.sessions}">
                <table>
                    <tr>
                        <th width="80">ID</th>
                        <th width="120">Movie Title</th>
                        <th width="120">Date</th>
                    </tr>
                    <c:forEach items="${timetable.sessions}" var="movieSession">
                        <tr>
                            <td>${movieSession.id}</td>
                            <td><a href="/moviePage/${movieSession.movie.id}" target="_blank">${movieSession.movie.title}</a></td>
                            <td><javatime:format value="${movieSession.beginDate}" style="MS"/></td>
                            <td><a href="<c:url value='/editSession/${movieSession.id}'/>">Edit</a></td>
                            <td><a href="<c:url value='/removeSession/${movieSession.id}'/>">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <div class="row timetable">
            <div class="col-lg-12">
                <h5 class="timetable-header">Today timeline</h5>
                <c:forEach items="${timetable.todayList}" var="todaySession">
                    <div class="timetable-cell">
                        <c:set value="${todaySession.beginDate.toLocalTime()}" var="begin"/>
                        <c:set value="${todaySession.endDate.toLocalTime()}" var="end"/>
                        <div class="timetable-cell-time"> ${begin} - ${end}</div>
                        <c:if test="${todaySession.movie.title.equals('free time')}" >
                            <div class="timetable-cell-title free-time">${todaySession.movie.title}</div>
                        </c:if>
                        <c:if test="${!todaySession.movie.title.equals('free time')}" >
                            <div class="timetable-cell-title">${todaySession.movie.title}</div>
                        </c:if>

                    </div>
                </c:forEach>
            </div>
        </div>
        <div id="createSessionApp" class="custom-form row">
            <c:url var="addAction" value="/movieSession/add"/>
            <form:form action="${addAction}" modelAttribute="movieSession" class="col-lg-5">
                <div class="row">
                    <fieldset>
                        <legend>Session Add/Update</legend>
                        <div  class="col-lg-12" >
                            <div class="row">
                                <div class="col-lg-12 form-group">
                                    <c:if test="${!empty movieSession.movie}">
                                        <form:label path="id">
                                            <spring:message text="ID"/>
                                        </form:label>
                                        <form:input class="form-control" path="id" readonly="true" size="8" disabled="true"/>
                                        <form:hidden path="id"/>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 form-group" id="addSessionApp">
                                    <%--<form:label path="movie.id">
                                        <spring:message text="Movie id"/>
                                    </form:label>
                                    <form:select id="movie-list" class="form-control" path="movie.id" onclick="addSessionApp.refreshDuration()">
                                        <c:forEach items="${movieList}" var="film">
                                            <form:option value="${film.id}"  data-duration="${film.duration}">${film.title}</form:option>
                                        </c:forEach>
                                    </form:select>--%>
                                        <form:label path="movie.id">
                                           <spring:message text="Movie"/>
                                        </form:label>
                                        <form:select id="movie-list" class="form-control" path="movie.id">
                                            <c:forEach items="${movieList}" var="film">
                                                <form:option  value="${film.id}"  data-duration="${film.duration}">${film.title}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    <span id="duration-hint">{{duration}}</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 form-group">
                                    <form:label path="beginDate">
                                        <spring:message text="Begin date"/>
                                    </form:label>
                                    <form:input class="form-control" type="datetime-local" path="beginDate" value="${timetable.freeTimeForNextSession}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <date-field  v-bind:value="getDate"></date-field>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 form-group">
                                    <input type="submit"
                                           class="btn btn-primary"
                                           value="<spring:message text="Save movie session"/>"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </form:form>
        </div>

    </div>
    <script src="${contextPath}/resources/js/movieSession.js"></script>
    <script src="${contextPath}/resources/js/createSessionApp.js"></script>
</body>
</html>
