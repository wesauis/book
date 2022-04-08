<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Meus Livros</title>
        
        <link rel="stylesheet" href="<%=request.getContextPath()%>common.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <table border="1" class="border-collapse">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.id()}</td>
                        <td>${book.title()}</td>
                        <td class="text-center">
                            <i class="fa-solid fa-pen-to-square"></i>
                            <a href="<%=request.getContextPath()%>books?id=${book.id()}&__method=DELETE">
                                <i class="fa-solid fa-xmark"></i>
                            </a>
                        </td>
                    </tr> 
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
