
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="true" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Movie Details</title>
    <script src="${contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css' />
    <link rel='stylesheet' href='${contextPath}/resources/css/header.css' />
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

    h5{
        margin: 15px 0;
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
                    <a class="nav-link active" href="<c:url value="/movies"/>">Movies</a>
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
    <hr/>
    <h3 class="title">${movie.title}</h3>
    <hr/>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value="/"/>">home</a></li>
            <li class="breadcrumb-item active" aria-current="page">${movie.title}</li>
        </ol>
    </nav>

    <div class="row info">
        <div class="col-lg-4">
            <img src="${pathToPoster.concat(movie.poster)}" width="300" height="450">
        </div>
        <div class="col-lg-5">
            <table class="table">
                <tr>
                    <th>Title</th>
                    <td>${movie.title}</td>
                </tr>
                <tr>
                    <th>Part</th>
                    <td>${movie.part}</td>
                </tr>
                <tr>
                    <th>Year</th>
                    <td>${movie.year}</td>
                </tr>
                <tr>
                    <th>Duration</th>
                    <td>${movie.duration}</td>
                </tr>
                <tr>
                    <th>Director</th>
                    <td>${movie.director}</td>
                </tr>
                <tr>
                    <th>Stars</th>
                    <td>${movie.stars}</td>
                </tr>
                <tr>
                    <th>Rating IMDB</th>
                    <td>${movie.rating}</td>
                </tr>
                <tr>
                    <td><a href="<c:url value='/editMovie/${movie.id}'/>">edit</a></td>
                    <td><a href="<c:url value='/removeMovie/${movie.id}'/>">remove</a></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row discription">
        <div class="col-lg-12">
            <h5>Discription: </h5>
            <p>
                ${movie.discription}
            </p>
        </div>
    </div>
</div>

</body>
</html>