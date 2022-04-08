<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<c:set var="novo" value="${book == null || book.id() == null}"></c:set>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:if test="${novo}">
            <title>Novo Livro</title>
        </c:if>
        <c:if test="${!novo}">
            <title>Editar Livro #${book.id()}</title>
        </c:if>
        
        <link rel="stylesheet" href="<%=request.getContextPath()%>common.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <form action="<%=request.getContextPath()%>books" method="POST">
            
        </form>
    </body>
</html>
