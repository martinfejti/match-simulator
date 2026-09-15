<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Menu</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
</head>
<body>
    <div class="main-container">
        <!-- Fejléc sormenü a konténer tetején -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/menubar'/>" class="active">Main Menu</a></li>
                <li><a href="<c:url value='/menubar/go-to-create-new-season'/>">New Season</a></li>
                <li><a href="<c:url value='/menubar/go-to-load-season'/>">Load Season</a></li>
            </ul>
        </nav>

        <!-- Az oldal további tartalma -->
        <div class="content">
            <h1>Welcome to Martínez Match Simulator!</h1>
            <p>Load a previous save or create a new season.</p>
        </div>
    </div>
</body>
</html>