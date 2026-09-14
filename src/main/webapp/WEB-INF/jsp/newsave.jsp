<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create new save</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/newsave.css'/>">
</head>
<body>
    <div class="main-container">
        <!-- Fejléc sormenü a konténer tetején -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/mainmenu'/>" class="active">Main Menu</a></li>
                <li><a href="<c:url value='/mainmenu/go-to-load-season'/>">Load Season</a></li>
            </ul>
        </nav>

        <!-- Az oldal további tartalma -->
        <div class="content">
            <h1>Season selection</h1>
            <p>Select the season you wish to play</p>
        </div>

        <!-- Táblázat konténer a képről vett stílusban -->
<div class="table-container">
    <!-- Vékony kék sáv a táblázat tetején, sárga szöveggel -->
    <div class="table-header-bar">
        <span>Selectable seasons</span>
    </div>

    <!-- A szigorúan formázott táblázat -->
    <table class="custom-table">
        <thead>
            <tr>
                <th>Date</th>
                <th>League</th>
                <th>Country</th>
                <th class="text-end"></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="season" items="${seasonList}">
                <tr>
                    <td>${season.date()}</td>
                    <td>${season.league()}</td>
                    <td><img src="<c:url value='/flag/${season.flag().code()}.png'/>" alt="EN" class="flag-icon"></td>
                    <td class="text-end">
                        <a href="<c:url value='/mainmenu/go-to-create-new-season'/>" class="table-btn">Select</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td>2025-2026</td>
                <td>Premier League</td>
                <td><img src="<c:url value='/flag/eng.png'/>" alt="EN" class="flag-icon"></td>
                <td class="text-end">
                    <a href="<c:url value='/mainmenu/go-to-create-new-season'/>" class="table-btn">Select</a>
                </td>
            </tr>
            <tr>
                <td>2025-2026</td>
                <td>La Liga</td>
                <td><img src="<c:url value='/flag/esp.png'/>" alt="ES" class="flag-icon"></td>
                <td class="text-end">
                    <a href="<c:url value='/mainmenu/go-to-create-new-season'/>" class="table-btn">Select</a>
                </td>
            </tr>
            <tr>
                <td>2025-2026</td>
                <td>Ligue 1</td>
                <td><img src="<c:url value='/flag/fra.png'/>" alt="DE" class="flag-icon"></td>
                <td class="text-end">
                    <a href="<c:url value='/mainmenu/go-to-create-new-season'/>" class="table-btn">Select</a>
                </td>
            </tr>
        </tbody>
    </table>
</div>
    </div>
</body>
</html>