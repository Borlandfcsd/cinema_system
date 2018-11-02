<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="login"/>
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Movies Page</title>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css' />
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
</style>

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
                <a  class="nav-link sign-out" onclick="document.forms['logoutForm'].submit()">Sign out</a>
            </c:if>
            <c:if test="${login == null}">
                <a  class="nav-link" href="<c:url value="/signPage"/>">Sign in/up</a>
            </c:if>
        </div>
    </nav>
    <c:if test="${message != null}">
        <div class="alert alert-success" role="alert">
            <strong>Movie </strong>${message}
        </div>
    </c:if>
    <c:if test="${exception != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Movie </strong>${exception}
        </div>
    </c:if>
    <hr/>
    <h3 class="title">Movies</h3>
    <hr/>

    <div class="row">
        <c:if test="${!empty listMovies}">
            <table>
                <tr>
                    <th width="80">ID</th>
                    <th width="120">Title</th>
                    <th width="80">Duration</th>
                    <th width="120">Poster</th>
                </tr>
                <c:forEach items="${listMovies}" var="movie">
                    <tr>
                        <td>${movie.id}</td>
                        <td><a href="/moviePage/${movie.id}" target="_blank">${movie.title}</a></td>
                        <td>${movie.duration}</td>
                        <td>${movie.poster}</td>
                        <td><a href="<c:url value='/admin/editMovie/${movie.id}'/>">Edit</a></td>
                        <td><a href="<c:url value='/admin/removeMovie/${movie.id}'/>">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>

    <div class="custom-form row">
        <c:url var="addAction" value="/admin/save?${_csrf.parameterName}=${_csrf.token}"/>

        <form:form action="${addAction}" modelAttribute="movieDto" enctype="multipart/form-data" class="col-lg-5">
            <fieldset>
                <legend>Movies Add/Update</legend>
                <div class="row">
                    <div  class="col-lg-12" >

                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <c:if test="${movie.id > 0}">
                                    <form:label path="movie.id">
                                        <spring:message text="ID:"/>
                                    </form:label>
                                    <form:input class="form-control" path="movie.id" readonly="true" size="8" disabled="true"/>
                                    <form:hidden path="movie.id"/>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <spring:bind path="movie.title">
                                <div class="col-lg-12 form-group ${status.error ? 'alert alert-danger' : ''}">
                                    <form:label path="movie.title">
                                        <spring:message text="*Title:"/>
                                    </form:label>
                                    <form:input class="form-control " path="movie.title"/>
                                </div>
                                <form:errors class="alert-danger" path="movie.title"/>
                            </spring:bind>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.part">
                                    <spring:message text="Part name:"/>
                                </form:label>
                                <form:input class="form-control" path="movie.part"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.year">
                                    <spring:message text="Year:"/>
                                </form:label>
                                <form:input class="form-control" path="movie.year"/>
                            </div>
                        </div>
                        <div class="row">
                            <spring:bind path="movie.duration">
                                <div class="col-lg-12 form-group ${status.error ? 'alert alert-danger' : ''}">
                                    <form:label path="movie.duration">
                                        <spring:message text="* Duration:"/>
                                    </form:label>
                                    <form:input class="form-control" path="movie.duration"/>
                                </div>
                                <form:errors class="alert alert-danger" path="movie.duration"/>
                            </spring:bind>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.director">
                                    <spring:message text="Director:"/>
                                </form:label>
                                <form:input class="form-control" path="movie.director"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.stars">
                                    <spring:message text="Stars:"/>
                                </form:label>
                                <form:input class="form-control" path="movie.stars"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.rating">
                                    <spring:message text="Rating imdb:"/>
                                </form:label>
                                <form:input class="form-control" path="movie.rating"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="movie.discription">
                                    <spring:message text="Discription:"/>
                                </form:label>
                                <form:input type="text" class="form-control" path="movie.discription"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <form:label path="poster">
                                    <spring:message text="Poster:"/>
                                </form:label>
                                <form:input type="file" class="form-control-file" path="poster"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 form-group">
                                <input class="btn btn-primary" type="submit"
                                       value="<spring:message text="Save movie"/>"/>
                            </div>
                        </div>

                    </div>
                </div>

            </fieldset>
        </form:form>
        <%--<form:form action="${addAction}" modelAttribute="movie" enctype="multipart/form-data" class="col-lg-5">
            <fieldset>
            <legend>Movies Add/Update</legend>
            <div class="row">
                <div  class="col-lg-12" >

                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <c:if test="${!empty movie.title}">
                                <form:label path="id">
                                    <spring:message text="ID:"/>
                                </form:label>
                                <form:input class="form-control" path="id" readonly="true" size="8" disabled="true"/>
                                <form:hidden path="id"/>
                            </c:if>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="title">
                                <spring:message text="Title:"/>
                            </form:label>
                            <form:input class="form-control" path="title"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="part">
                                <spring:message text="Part name:"/>
                            </form:label>
                            <form:input class="form-control" path="part"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="year">
                                <spring:message text="Year:"/>
                            </form:label>
                            <form:input class="form-control" path="year"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="duration">
                                <spring:message text="Duration:"/>
                            </form:label>
                            <form:input class="form-control" path="duration"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="director">
                                <spring:message text="Director:"/>
                            </form:label>
                            <form:input class="form-control" path="director"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="stars">
                                <spring:message text="Stars:"/>
                            </form:label>
                            <form:input class="form-control" path="stars"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="rating">
                                <spring:message text="Rating imdb:"/>
                            </form:label>
                            <form:input class="form-control" path="rating"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <form:label path="discription">
                                <spring:message text="Discription:"/>
                            </form:label>
                            <form:input type="text" class="form-control" path="discription"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <label>Poster:</label>
                            <input class="form-control-file" type="file" name="picture">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 form-group">
                            <input class="btn btn-primary" type="submit"
                                   value="<spring:message text="Save movie"/>"/>
                        </div>
                    </div>
                </div>
            </div>

        </fieldset>
        </form:form>--%>
    </div>

</div>
</body>
</html>