<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="login"/>
</sec:authorize>

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
            -webkit-box-shadow:  0 0 0 0 #000;
            box-shadow:  0 0 0 0 #000;
        }
        legend {
            text-align: center;
        }

        .timeline{
            margin: 0 auto;
        }

        .timeline-row{
            margin-top: 15px;
        }
        .timeline-date{
            margin-right: 15px;
            vertical-align: middle;
        }
        .timeline-date-value{
            display: inline-block;
            font-weight: bold;
            margin: auto 0;
            vertical-align: center;
        }
        .timeline-header {
            text-align: center;
        }
        .timeline-cell{
            display: inline-block;
            border: 1px solid black;
            background-color: rgba(255, 128, 38, 0.84);
            color: white;
            text-align: center;
        }

        .timeline-cell-time{
            border-bottom: 1px solid black;
            padding: 2px 5px;
        }
        .timeline-cell-title{
            background-color: #0b93ff;
            padding: 2px 10px;
        }
        #timeline-title{
            cursor: pointer;
        }

        #timeline-title:hover{
            cursor: pointer;
            text-decoration: underline;
        }

        .timeline-cell-title.free-time{
            background-color: #9fa8a8;
        }
        #duration-hint{
            font-size: 10px;
        }
    </style>

    <div class="container" id="movieSessionApp">
        <nav class="navbar navbar-expand-sm navbar-custom">
            <a href="<c:url value="/"/>" class="logo navbar-brand">Cinema</a>
            <div class="navbar-collapse collapse" id="navbarCustom">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/"/>">Home</a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="nav-item">
                            <a class="nav-link " href="<c:url value="/admin/movies"/>">Movies</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="<c:url value="/admin/movieSessions"/>">Movie Sessions</a>
                        </li>
                    </sec:authorize>
                </ul>
                <c:if test="${login != null}">
                    <span class="ml-auto navbar-text"> ${login} | </span>
                    <form id="logoutForm" method="post" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <a  class="nav-link sign-out" onclick="document.forms['logoutForm'].submit()">Sign out</a>
                </c:if>
                <c:if test="${login == null}">
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
            <c:if test="${!empty timetable.sessionsForWeek}">
                <table>
                    <tr>
                        <th width="80">ID</th>
                        <th width="120">Movie Title</th>
                        <th width="120">Date</th>
                    </tr>
                    <c:forEach items="${timetable.sessionsForWeek}" var="movieSession">
                        <tr>
                            <td>${movieSession.id}</td>
                            <td><a href="/moviePage/${movieSession.movie.id}" target="_blank">${movieSession.movie.title}</a></td>
                            <td><javatime:format value="${movieSession.beginDate}" style="MS"/></td>
                            <td><a href="<c:url value='/admin/editSession/${movieSession.id}'/>">Edit</a></td>
                            <td><a href="<c:url value='/admin/removeSession/${movieSession.id}'/>">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>

<div class="row">
    <div class="col-5 custom-form">
        <c:url var="addAction" value="/admin/movieSession/add"/>
        <form:form action="${addAction}" modelAttribute="movieSession" class="col-lg-12">
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
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.id">
                                    <spring:message text="Movie"/>
                                </form:label>
                                <form:select id="movie-list" class="form-control" onclick="movieSessionApp.refreshDuration()" path="movie.id">
                                    <c:forEach items="${movieList}" var="film">
                                        <form:option  value="${film.id}" data-duration="${film.duration}">${film.title}</form:option>
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
                                <form:input class="form-control" id="begin-date" type="datetime-local" path="beginDate" value="${timetable.timeForNextSession}"/>
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
    <div class="col-7 timeline">
        <h5 class="timeline-header">Timeline</h5>
        <c:forEach items="${timetable.week.days.values()}" var="day">
            <div class="row timeline-row">
                <div class="timeline-date">
                    <span class="timeline-date-value">${day.date}</span>
                </div>

                <c:forEach items="${day.timeline.timeline}" var="todaySession">
                    <div class="timeline-cell">
                        <c:set value="${todaySession.beginDate.toLocalTime()}" var="begin"/>
                        <c:set value="${todaySession.endDate.toLocalTime()}" var="end"/>
                        <div class="timeline-cell-time"> ${begin} - ${end}</div>
                        <c:if test="${todaySession.movie.title.equals('free time')}" >
                            <div class="timeline-cell-title free-time"  id="timeline-title" v-on:click="setDate" data-date="${todaySession.beginDate}">${todaySession.movie.title}</div>
                        </c:if>
                        <c:if test="${!todaySession.movie.title.equals('free time')}" >
                            <div class="timeline-cell-title">${todaySession.movie.title}</div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
    </div>
    <script src="${contextPath}/resources/js/movieSession.js"></script>
</body>
</html>
