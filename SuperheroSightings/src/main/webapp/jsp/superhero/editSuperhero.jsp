<%-- 
    Document   : editSuperhero
    Created on : Jan 19, 2018, 8:37:03 AM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Superhero</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    </head>
    <body>
        <div class="container" id="createOrganization">

            <div class="row">
                <div class="col-md-10">
                    <h1>Edit Superhero: <c:out value ="${superhero.superheroName}"/> </h1>
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
                          modelAttribute="superhero" 
                          action="editSuperhero" 
                          method="POST" 
                          id="add-superhero">
                    <div id="super-edit" class="col-md-6">
                        <div class="form-group">
                            <label for="add-super-name" 
                                   class="col-md-5 control-label">
                                Superhero Name: 
                            </label>
                            <div class='col-md-6'>
                                <sf:input type="text"
                                          class="form-control"
                                          id="add-super-name"
                                          path="superheroName"
                                          placeholder="Enter Supehero Name"
                                          required="true"/>
                                <sf:errors path="superheroName" cssclass="error"> </sf:errors>

                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-super-description" 
                                       class="col-md-5 control-label">
                                    Superhero Description: 
                                </label>
                                <div class='col-md-6'>
                                <sf:input type="text"
                                          class="form-control"
                                          id="add-super-description"
                                          path="superheroDescription"
                                          placeholder="Enter Superhero Description"
                                          required="true"/>
                                <sf:errors path="superheroDescription" cssclass="error"> </sf:errors>

                                </div>
                                </div>
                                <div class="form-group">
                                    <label for="add-superpower" 
                                           class="col-md-5 control-label">
                                        Superpower: 
                                    </label>
                                    <div class='col-md-6'>
                                    <sf:input type="text"
                                              class="form-control"
                                              id="add-superpower"
                                              path="superpower"
                                              placeholder="Enter Organization Contact"
                                              required="true"/>
                                    <sf:errors path="superpower" cssclass="error"> </sf:errors>
                                    <sf:hidden path="superheroId"/>
                                </div>
                            </div>    


                                <div class="form-group">
                                    <div class=" col-md-offset-2 col-md-2">
                                        <a href="${pageContext.request.contextPath}/superhero/superhero" class="btn btn-default">Cancel</a>
                                    </div>

                            <div class="form-group">
                                <div class=" col-md-offset-2 col-md-2">
                                    <input class="btn btn-primary" 
                                           id="update-superhero" 
                                           type="submit"  
                                           value="Update Superhero"/>
                                </div>
                            </div>
                        </div>

                    </sf:form>
                </div>



            </div>

        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>

    </body>
</html>
