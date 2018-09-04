<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="${contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css' />
    <link rel='stylesheet' href='${contextPath}/resources/css/header.css' />
    <link rel='stylesheet' href='${contextPath}/resources/css/index.css' />
    <script src="${contextPath}/webjars/vue/2.5.13/vue.js"></script>
</head>
<body>
<style>
    .container{
        padding: 20px;
        -webkit-box-shadow: 0 2px 3px 0 rgba(0,0,0,0.2);
        -moz-box-shadow: 0 2px 3px 0 rgba(0,0,0,0.2);
        box-shadow: 0 2px 3px 0 rgba(0,0,0,0.2);
    }

    h3{
          margin: 15px 0;
    }

    ul{
        margin-top: 15px;
    }
    h2{
        margin: 25px 0;
    }

    .day-header{
        margin: 20px 0;
    }
    .movies{
        margin-top: 10px;
    }
    .movie {
        height: 260px;
    }

    .movie-poster-img {
        padding: 0 auto;
        width: 170px;
        height: 250px;
    }
    .movie-sessions{
        margin: 10px 0;
    }
    .movie-discription{
        margin-top: 5px;
        font-size: 12px;
        width: 290px;
        height: 170px;
        overflow: hidden;
    }


    .session_time_item{
        background: #ff9322;
        color: #fff;
        padding: 5px;
        margin-right: 5px;
    }
    .session_time_item:hover{
        background: #fff;
        color: #ff9322;
        border: 1px solid #ff9322;
    }
</style>


<div class="container">
    <nav class="navbar navbar-expand-sm navbar-custom">
        <a href="<c:url value="/"/>" class="logo navbar-brand">Cinema</a>
        <div class="navbar-collapse collapse" id="navbarCustom">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="<c:url value="/"/>">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/movies"/>">Movies</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/movieSessions"/>">Movie Sessions</a>
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
                ${message}
        </div>
    </c:if>
    <hr/>
    <h3 class="title">Timetable</h3>
    <hr/>

    <div class="row days">
        <c:forEach items="${timetable.week.days}" var="day">
            <div class="col-12 day">
                <h3 class="day-header"> ${day.key}</h3>
                <div class="row movies" >
                    <c:forEach items="${day.value.movieSessionsForMovie}" var="movie">
                        <div class="col-6 movie">
                            <div class="row movie-body">
                                <div class="col-5 movie-poster">
                                    <img class="movie-poster-img" src="${pathToPoster.concat(movie.key.poster)}">
                                </div>
                                <div class="col-7 movie-info">
                                    <div class="movie-title">
                                        <h4>
                                            <a href="/moviePage/${movie.key.id}">${movie.key.title}</a>
                                        </h4>
                                    </div>
                                    <div class="movie-sessions">
                                        <c:forEach items="${movie.value}" var="session">
                                            <a class="session_time_item" href="/sessionPage/${session.id}">
                                                <javatime:format  value="${session.beginDate}" style="-S" />
                                            </a>
                                        </c:forEach>
                                    </div>
                                    <div class="movie-discription">
                                        <table class="table">
                                            <tr>
                                                <th>Duration</th>
                                                <td>${movie.key.duration} min.</td>
                                            </tr>
                                            <tr>
                                                <th>Director</th>
                                                <td>${movie.key.director}</td>
                                            </tr>
                                            <tr>
                                                <th>Rating IMDB</th>
                                                <td>${movie.key.rating}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>

</div>
    <script src="${contextPath}/webjars/jquery/3.3.1-1/jquery.js"></script>
    <script src="${contextPath}/resources/js/index.js"></script>

</body>
</html>