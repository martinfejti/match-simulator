<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Temp Menu</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
</head>
<body>
    <div class="main-container">
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="#" class="active">Career Menu</a></li>
            </ul>
        </nav>
        
        <!-- Az oldal további tartalma -->
        <div class="content">
            <!-- TODO display some important info about the current league-->
            
            <p>Number of teams in the league: ${numberOfTeams}</p>
        </div>
    </div>
</body>
</html>