<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%--
  Created by IntelliJ IDEA.
  User: Borland
  Date: 16.07.2018
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="login"/>
    <sec:authentication property="principal.authorities" var="role"/>
</sec:authorize>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Session Page</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="user-role" content="${role}"/>
    <script src="${contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css' />
    <script src="${contextPath}/webjars/vue/2.5.13/vue.js"></script>
    <script src="${contextPath}/webjars/jquery/3.3.1-1/jquery.js"></script>
    <link rel='stylesheet' href='${contextPath}/resources/css/header.css' />
</head>
<body>

<style>
    .nav {
        margin-bottom: 15px;
    }
    ul{
        margin-top: 15px;
    }
    h2{
        margin: 25px 0;
    }
    h3{
        margin: 15px 0;
    }

    .ticket {
        margin: 5px;
    }
    .ticket input{
        background-color: #bec7c7;
    }

    .row{
        margin-bottom: 15px;
    }

    .place {
        border-radius: 5px;
        width: 22px;
        height: 30px;
        cursor: pointer;
        display: inline-block;
        text-align: center;
    }

    .screen-handler{
        width: 450px;
    }
    .screen-img{
        width: 400px;
        height: 8px;
    }

    .screen-text{
        margin-left: 170px;
    }


    .hall-row{
        margin-top: 10px;
        cursor: default;
    }
    .row-number{
        color: #9fa8a8;
        padding-right: 10px;
    }
    a:not([href]):not([tabindex]){
         text-decoration: none;
     }
    .EMPTY {
        background: #ff9322;
        color: #fff !important;
        /*padding: 5px;*/
        margin-right: 5px;
        border: 1px solid #ff9322
    }
    .STATIC_EMPTY{
        background: #ff9322;
        color: #fff !important;
        margin-right: 5px;
        border: 1px solid #ff9322;
        cursor: default;
    }
    .EMPTY:hover {
        background: #fff;
        color: #ff9322 !important;
        border: 1px solid #ff9322;
    }
    .RESERVED{
        color: #fff !important;
        /*padding: 5px;*/
        margin-right: 5px;
        background-color: #9fa8a8;
        border: 1px solid #9fa8a8;
        cursor: default;
    }
    .CHECKED{
        color: #077bff !important;
        background-color: white;
        border: 1px solid #077bff;
    }
    .total-price{
        text-align: right;
    }
</style>

    <div id="reserveApp" class="container">
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

            <hr/>
            <h3 class="title">${movieSession.movie.title} session</h3>
            <hr/>

            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="<c:url value="/"/>">home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${movieSession.movie.title}-session</li>
                </ol>
            </nav>

        <div class="row info">
            <div class="col-lg-4">
                <img src="${pathToPoster.concat(movieSession.movie.poster)}" width="300" height="450">
            </div>
            <div class="col-lg-5">
                <table class="table">
                    <tr>
                        <th>Title</th>
                        <td>${movieSession.movie.title}</td>
                    </tr>
                    <c:if test="${movieSession.movie.part.equals('')}">
                        <tr>
                            <th>Part</th>
                            <td>${movieSession.movie.part}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>Duration</th>
                        <td>${movieSession.movie.duration} min</td>
                    </tr>
                    <tr hidden="hidden">
                        <th>Session ID</th>
                        <td id="session_id">${movieSession.id}</td>
                    </tr>
                    <tr>
                        <th>Session date</th>
                        <td><javatime:format value="${movieSession.beginDate}" style="MS"/></td>
                    </tr>
                </table>
                <div class="screen-handler">
                    <div>
                        <img class="screen-img" src="${contextPath}/resources/img/screen.png">
                    </div>
                    <div class="screen-text">
                        <span class="">SCREEN</span>
                    </div>
                </div>
                <div class="row hall">
                        <c:forEach items="${tickets.hall}" var="row" varStatus="rowNum">
                            <div class="col-12 hall-row">
                                <span class="row-number">row ${rowNum.count}</span>
                                <c:forEach items="${row}" var="ticket">
                                    <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
                                        <c:set var="ticketPrice" value="${ticket.price}"/>
                                        <c:if test="${ticket.placeStatus eq 'EMPTY'}" >
                                            <a data-row="${ticket.row}"
                                               data-place="${ticket.place}"
                                               data-price="${ticket.price}"
                                               class="place STATIC_EMPTY">
                                                    ${ticket.place}</a>
                                        </c:if>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                                        <c:set var="ticketPrice" value="${ticket.price}"/>
                                        <c:if test="${ticket.placeStatus eq 'EMPTY'}" >
                                            <a data-row="${ticket.row}"
                                               data-place="${ticket.place}"
                                               data-price="${ticket.price}"
                                               class="place ${ticket.placeStatus}"
                                               @click="selectTickets" >${ticket.place}</a>
                                        </c:if>
                                    </sec:authorize>
                                        <c:if test="${!(ticket.placeStatus eq 'EMPTY')}">
                                            <a data-row="${ticket.row}"
                                               data-place="${ticket.place}"
                                               data-price="${ticket.price}"
                                               class="place ${ticket.placeStatus}">X</a>
                                        </c:if>

                                </c:forEach>
                            </div>
                        </c:forEach>
            </div>
                <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
                    <div class="alert-warning">
                        You should <a href="${contextPath}/signPage">Sign In</a> for order a ticket
                    </div>
                </sec:authorize>

            <div class="row tickets">
                <div class="col-lg-12">
                    <form name="tickets_form" v-if="seen" action="">
                        <div class="form-group" hidden>
                            <label>Email:</label>
                            <input id="email" size="30" type="email" autocomplete="email" name="email" value="${login}">
                        </div>
                        <div class="form-group ticket" >
                            <table class="table">
                                <h4>Your tickets:</h4>
                                <tr>
                                    <th>Movie</th>
                                    <th>Date</th>
                                    <th>Row</th>
                                    <th>Place</th>
                                    <th>Price</th>
                                </tr>
                                <template v-for="ticket in tickets">
                                    <tr>
                                        <td>${movieSession.movie.title}</td>
                                        <td><javatime:format value="${movieSession.beginDate}" style="MS"/></td>
                                        <td>{{ticket.row}}</td>
                                        <td>{{ticket.place}}</td>
                                        <td>{{ticket.price}}</td>
                                    </tr>
                                </template>
                                <tr class="table-secondary">
                                    <th colspan="3">Total:</th>
                                    <th class="total-price" colspan="2">{{ price }} uah.</th>
                                </tr>
                            </table>
                        </div>
                        <div class="form-group">
                            <button class="btn-primary" type="button" onclick="reserveApp.restPost()">Reserve tickets</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </div>
<script src="${pageContext.request.contextPath}/resources/js/reserveTickets.js"></script>
</body>
</html>
