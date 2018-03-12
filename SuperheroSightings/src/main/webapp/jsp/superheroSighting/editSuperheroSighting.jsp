<%-- 
    Document   : editSuperheroSighting
    Created on : Feb 2, 2018, 2:10:56 PM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Superhero Sighting</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    </head>
    <body>
        <div class="container" id="edit-sighitng">
            <div class="row">
                <div class="col-md-10">
                    <h1>Edit Sighting</h1>
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
                <div>

                <sf:form  class="form-horizontal" 
                          role="form" 
                          modelAttribute="ourSighting" 
                          action="editSuperheroSighting" 
                          method="POST" 
                          id="add-superhero-sighting">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="col-md-3">
                            </div>
                            <div class="col-md-4">
                                <p><b>Original Values</b></p>
                            </div>
                            <div id="super-sighitng-edit" class="col-md-12">
                                <div class="form-group ">
                                    <sf:input name="hiddenDate" path="date" type="hidden" value="${ourSighting.date}"/>
                                    <label for="datetime-input" 
                                           class="col-md-4 control-label">Date & Time: 
                                    </label>
                                    <div class="col-md-5">
                                        <fmt:formatDate value="${ourSighting.date}" pattern="MM/dd/yyyy hh:mma" var="formattedDate"/>
                                        <sf:input type="text" 
                                                  class="form-control"                                                   
                                                  value="${formattedDate}"
                                                  path="date"
                                                  id="datetime-input"
                                                  readonly = "true"/>
                                        <sf:hidden value="${ourSighting.date}" path="date"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label id="given-superhero" 
                                           for="given-superhero"
                                           class="col-md-4 control-label">
                                        Superhero:
                                    </label>
                                    <div class="col-md-6">
                                        <sf:input class="form-control" 
                                                  type="text"
                                                  path="superhero.superheroName"                                                      
                                                  id="given-superhero" 
                                                  readonly = "true"/>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label id="add-current-location" 
                                           for="given-location"
                                           class="col-md-4 control-label">
                                        Location:
                                    </label>
                                    <div class="col-md-6">
                                        <sf:input class="form-control" 
                                                  type="text"
                                                  path="location.locationName"
                                                  id="given-location" 
                                                  readonly = "true"/>
                                        <sf:errors path="location.locationName"/>
                                        <sf:hidden path="sightId"/>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--  changes to the Sighting-->
                        <div class="col-md-6">
                            <div class="col-md-3">
                            </div>
                            <div class="col-md-6">
                                <p><b>Make Changes Here</b></p>
                            </div>
                            <div class="form-group ">
                                <label for="datetime-input-change" 
                                       class="col-md-4 control-label">Date & Time: 
                                </label>
                                <div class="col-md-5">
                                    <input class="form-control" 
                                           type="datetime-local"
                                           name="change-date"
                                           id="datetime-input-change" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label id="add-superhero"  
                                       class="col-md-4 control-label">
                                    Select Superhero:
                                </label>
                                <div class="col-md-6">
                                    <select class="btn btn-default" name="change-superhero-by-name" id="superhero-dropdown">
                                        <option value="">---Choose New Superhero---</option> 
                                        <c:forEach var="currentSuperhero" items="${superList}">
                                            <option  value="<c:out value="${currentSuperhero.superheroId}"/>"><c:out value="${currentSuperhero.superheroName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label id="add-current-location" 
                                       class="col-md-4 control-label">
                                    Select Location:
                                </label>
                                <div class="col-md-6">
                                    <select class="btn btn-default" name="change-location-by-name" id="location-dropdown" >
                                        <option value="">---Change Location---</option>
                                        <c:forEach var="currentLocation" items="${locList}">
                                            <option  value="<c:out value="${currentLocation.locationId}"/>"><c:out value="${currentLocation.locationName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                                        <div class="form-group">
                <div class="row">
                    <div class=" col-md-offset-2 col-md-2">
                        <a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting" class="btn btn-default">Cancel</a>
                    </div>

                            <div class="form-group">
                                <div class="  col-md-2">
                                    <input class="btn btn-primary" 
                                           id="update-superhero" 
                                           type="submit"  
                                           value="Update Sighting"/>
                                </div>
                            </div>
                    </div>
                </div>
                
        </sf:form>
</div>
</div>

</body>
</html>
