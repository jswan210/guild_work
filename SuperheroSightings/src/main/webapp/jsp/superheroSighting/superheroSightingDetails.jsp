<%-- 
    Document   : superheroSightingDetails
    Created on : Jan 27, 2018, 2:44:25 PM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>

        <title>Organization Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>

        <div class="col-md-offset-1">
            <div class="container">
                <div class="row">
                    <div class="col-md-10">
                        <h1>Sighting Details</h1>
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
                <hr>
            </div>

        </div>
        <div class="col-md-offset-1">
            <div class="container">
                <fmt:formatDate value="${ourSight.date}" pattern="MMM dd, yyyy hh:mm a" var="formattedDate"/>
                <p><u>Date of Sighting:</u>
                    <br>
                </p>
                <p>
                    <c:out value="${formattedDate}"/>
                </p>
                <p>
                    <br>
                    <u>Superhero Sighted:</u>  
                </p>
                <p>
                    <c:out value="${ourSight.superhero.superheroName}"/>
                </p>
                <br>
                <p>
                    <u>Location of Sighting:</u>
                    <br>
                </p>
                <p>
                    <c:out value="${ourSight.location.locationName}"/><br>
                    <c:out value="${ourSight.location.address}"/>,
                    <c:out value="${ourSight.location.city}"/>,
                    <c:out value="${ourSight.location.state}"/>
                </p>

                <div class=" col-md-offset-1 col-md-1">
                    <br>
                    <a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting" class="btn btn-default">Back to Sightings</a>
                </div>
            </div>
        </div>

                    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
                    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>