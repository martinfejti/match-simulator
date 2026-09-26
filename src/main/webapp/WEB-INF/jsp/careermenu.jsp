<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Career Menu</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
</head>
<body>
    <div class="main-container">
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/team/get-team'/>">Squad</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
                <li><a href="<c:url value='/career/match-preview/get-next-fixture'/>">Next Fixture</a></li>
            </ul>
        </nav>
        
        <!-- Az oldal további tartalma -->
        <div class="content">
            <!-- TODO display some important info about the current league-->
            
            <p>Number of teams in the league: ${numberOfTeams}</p>
            <p>Save name: ${save.name()}</p>

            <c:if test="${not empty fixtureList}">
                <h1>Fixtures of Match Week #${fixtureList.get(0).matchWeek()}</h1>
                <c:forEach var="fixture" items="${fixtureList}">
                    <p>${fixture.homeTeam().name()} vs ${fixture.awayTeam().name()}</p>
                </c:forEach>
            </c:if>
        </div>
    </div>
</body>
</html>