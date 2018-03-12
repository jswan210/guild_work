<%-- 
    Document   : deleteSuperheroError
    Created on : Feb 7, 2018, 9:57:52 AM
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
        <title>Superhero Sighting Superheros</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap core CSS -->
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
            <hr/>
            <div class="alert alert-danger">
                <p id="error-msg"> <c:out value="${errorMsg}"/></p>
            </div>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting">Superhero Sighting</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Admin
                            </a>
                        </li>                        
                    </sec:authorize> 
                </ul>    
            </div>
            <div class="container">
                <div class="col-md-6">
                    <h2>Superheroes</h2>
                </div>
                <div class="col-md-4 form-group btn btn-group">
                    <a href="${pageContext.request.contextPath}/superhero/displayCreateSuperheroForm" class="btn btn-default">Create New Superhero</a>
                </div>
            </div>
            <div>
                <table class='table table-striped' id="superheroTable">
                    <thead style='background-color: lightgray'>
                        <tr>
                            <th width ="5%"> </th>
                            <th width="20%">Superhero Name</th>
                            <th width="15%">Superpower</th>
                            <th width="20%">Description</th>

                            <th width="10%"></th>
                            <th width="10%"></th>
                        </tr>
                    <tbody id='contentRows'>

                        <c:forEach var="currentSuperhero" items="${superList}">
                            <tr>
                                <td></td>
                                <td>
                                    <a href="displaySuperheroDetails?superheroId=${currentSuperhero.superheroId}">
                                        <c:out value="${currentSuperhero.superheroName}"/>
                                    </a> 
                                </td>
                                <td>
                                    <c:out value="${currentSuperhero.superpower}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSuperhero.superheroDescription}"/>
                                </td>
                                <td>
                                    <sec:authorize access="isAuthenticated()">
                                        <a href="displayEditSuperhero?superheroId=${currentSuperhero.superheroId}">
                                            Edit
                                        </a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteSuperhero?superheroId=${currentSuperhero.superheroId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/superhero.js"></script>
    </body>
</html>
