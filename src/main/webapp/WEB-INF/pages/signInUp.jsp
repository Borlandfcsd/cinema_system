<%--
  Created by IntelliJ IDEA.
  User: Borland
  Date: 26.07.2018
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Sign In\Out</title>
    <link rel='stylesheet'  id="bootstrap-css" href='${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css'/>
    <script src="${contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <link rel='stylesheet' href='${contextPath}/resources/css/authorization.css' />
    <script src="${contextPath}/webjars/jquery/3.3.1-1/jquery.js"></script>
    <script src="${contextPath}/resources/js/authorization.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-6 offset-md-3">
            <div class="panel panel-signin">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-6"> <a href="#" class="active" id="singin-form-link">sing In</a>
                        </div>
                        <div class="col-6"> <a href="#" id="singup-form-link">sing Up</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xl-12">
                            <form id="singin-form" action="${contextPath}/login" method="post" role="form" style="display: block;">
                                <div class="form-group  ${error != null ? 'has-error' : ''}">
                                    <span>${message}</span>
                                    <label >Email:</label>
                                    <input type="email" name="username" id="username" tabindex="1" class="form-control" autofocus="true" placeholder="Username"/>
                                    <label >Password:</label>
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password"/>
                                    <span>${error}</span>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6 offset-sm-3">
                                            <input type="submit" name="signIn-submit" id="singin-submit" tabindex="4"
                                                   class="form-control btn btn-signin" value="Sign In">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-xl-12">
                                            <div class="text-center"> <a href="#" tabindex="5" class="forgot-password">Forgot Password?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                            <form:form id="singup-form" modelAttribute="userForm" action="/signUp" method="post" role="form" style="display: none;">
                                <spring:bind path="firstName">
                                    <div class="form-group">
                                        <form:label path="firstName">First name:</form:label>
                                        <form:input class="form-control" path="firstName"/>
                                    </div>
                                </spring:bind>
                                <spring:bind path="lastName">
                                    <div class="form-group">
                                        <form:label path="lastName">Last name:</form:label>
                                        <form:input class="form-control" path="lastName"/>
                                    </div>
                                </spring:bind>
                                <spring:bind path="email">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:label path="email">Email:</form:label>
                                        <form:input class="form-control" type="email" path="email"/>
                                        <form:errors path="email"/>
                                    </div>
                                </spring:bind>
                                <spring:bind path="password">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <form:label path="password">Password:</form:label>
                                        <form:input class="form-control" type="password" path="password"/>
                                        <form:errors path="password"/>
                                    </div>
                                </spring:bind>
                                <spring:bind path="confirmPassword">
                                    <div class="form-group">
                                        <form:label path="confirmPassword">Confirm Password:</form:label>
                                        <form:input class="form-control" type="password" path="confirmPassword"/>
                                        <form:errors path="confirmPassword"/>
                                    </div>
                                </spring:bind>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6 offset-sm-3">
                                            <input type="submit" name="singup-submit" id="singup-submit" tabindex="4"
                                                   class="form-control btn btn-signup" value="Sign Up">
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
