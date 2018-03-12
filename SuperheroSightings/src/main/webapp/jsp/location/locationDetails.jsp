<%-- 
    Document   : locationDetails
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Location Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <div class="col-md-offset-1">

            <div class="container">
                <div class="row">
                <div class="col-md-10">
                    <h1>Location Details</h1>
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
                <h2>
                <c:out value="${location.locationName}"/>

            </h2>
            <p>
                <c:out value="${location.locationDescription}"/>
            </p>
            <p>
                <c:out value="${location.address}"/> <br>
                <c:out value="${location.city}"/>, <c:out value="${location.state}"/> <c:out value="${location.zip}"/>
            </p>

            <p>
                Coordinates: <br>
                Latitude: <c:out value="${location.latitude}"/> <br> Longitude: <c:out value="${location.longitude}"/>
            </p>

            <div class=" col-md-offset-1 col-md-1">
                <br>
                <a href="${pageContext.request.contextPath}/location/location" class="btn btn-default">Back to Locations</a>
            </div>
        </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
