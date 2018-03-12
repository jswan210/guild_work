<%-- 
    Document   : addUserForm
    Created on : Feb 23, 2018, 10:59:43 AM
    Author     : jswan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Superhero Sighting Location Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/location.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-10">
                    <h1>Superhero Sighting Web App</h1>
                </div>
                <div  class="col-md-2">
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <h4>
                            <a href="<c:url value="/login" />" > Login</a>
                        </h4>
                    </c:if>
                    <div>
                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <p>Hello : ${pageContext.request.userPrincipal.name}
                                | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                            </p>
                        </c:if>  
                    </div>
                </div>
            </div>
            <c:if test="${errorMsg != null}">
                <div  class="alert alert-danger">
                    <c:out value="${errorMsg}"/>
                </div>
            </c:if>
            <hr/>


            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"  ><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting">Superhero Sighting</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation" class="active">
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Admin
                            </a>
                        </li>                        
                    </sec:authorize>               
                </ul>    
            </div>
            <h1>Add User Form</h1>
            <form  class="form-horizontal" method="POST" action="addUser">
                <div class="form-group">
                    <label for="username"
                           class="col-md-1 control-label">
                        Username: 
                    </label>
                    <div class='col-md-3'>
                        <input type="text"
                               class="form-control"
                               name="username"
                               id="add-username"
                               placeholder="Username" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password"
                           class="col-md-1 control-label">
                        Password:
                    </label>
                    <div class='col-md-3'>
                        <input type="text"
                               class="form-control"
                               name="password"
                               id="add-password"
                               placeholder="Password" required/>
                    </div>
                </div>
                <div class="form-group" data-toggle="validator">
                    <div class="radio sr-radio">
                        <label class="radio-inline"><input type="radio" name="isUser" >Sidekick (User Authority)</label>
                        <label class="radio-inline"><input type="radio" name="isAdmin" >Admin (Full Authority)</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class=" col-md-1">
                        <a href="${pageContext.request.contextPath}/displayUserList" class="btn btn-default">Cancel</a>
                    </div>
                    <div id='submit-user'>
                        <div class="col-md-offset-2 col-md-1" >
                            <input id='new-user' 
                                   type="submit" 
                                   class="btn btn-primary" 
                                   value="Submit New User"/>
                        </div>
                    </div>

                </div>

            </form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>