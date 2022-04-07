<%-- 
    Document   : index.jsp
    Created on : 25/03/2022, 21:52:32
    Author     : wesley.isotton
--%>

<%@page import="io.github.wesauis.config.GlobalConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>J2DO</title>

        <%if (GlobalConfig.PRODUCTION) {%>
        <script src="https://unpkg.com/vue@3.2.31/dist/vue.global.prod.js"></script>
        <%} else {%>
        <script src="https://unpkg.com/vue@3"></script>
        <%}%>
    </head>
    <body>
        <h1>J2DO</h1>
    </body>
</html>
