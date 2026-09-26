<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Career Menu</title>
    <link rel="stylesheet" href="<c:url value='/css/career_menu.css'/>">
</head>
<body>
    <div class="main-container">
        <!-- Navigációs menüsáv -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/team/get-team'/>">Squad</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
            </ul>
        </nav>

        <!-- 1. DOBOZ: Career Information -->
        <div class="card-box">
            <div class="card-header">
                <h2>Career Information</h2>
            </div>
            <div class="card-body info-grid">
                <div class="info-column">
                    <div class="info-row">
                        <span class="info-label">League:</span>
                        <span class="info-value"><img src="<c:url value='/flag/${information.country()}.png'/>" alt="${information.country()}" class="flag-icon">${information.leagueName()}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Season:</span>
                        <span class="info-value">${information.date()}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Matches simulated:</span>
                        <span class="info-value">${numberOfFinishedFixtures}</span>
                    </div>
                </div>

                <div class="info-column">
                    <div class="info-row">
                        <span class="info-label">Save name:</span>
                        <span class="info-value">${information.saveName()}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Number of teams:</span>
                        <span class="info-value">${numberOfTeams}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Matches left:</span>
                        <span class="info-value">${numberOfNotFinishedFixtures}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 2. DOBOZ: Matchweek x - Fixtures -->
        <div class="card-box">
            <div class="card-header header-with-btn">
                <h2>Matchweek ${not empty fixtureList ? fixtureList.get(0).matchWeek() : 'X'} - Fixtures</h2>
                <c:if test="${not empty nextFixtureId}">
                    <c:url var="nextFixtureUrl" value="/career/match-preview/get-next-fixture">
                        <c:param name="fixtureId" value="${nextFixtureId}"/>
                    </c:url>
                    <a href="${nextFixtureUrl}" class="next-fixture-btn">Next Fixture</a>
                </c:if>
            </div>
            <div class="card-body">
                <c:if test="${not empty fixtureList}">
                    <div class="fixture-list">
                        <c:forEach var="fixture" items="${fixtureList}">
                            <div class="fixture-row">
                                <!-- Match number in week -->
                                <span class="match-num">${fixture.matchNumberInWeek()}</span>

                                <!-- Hazai csapat -->
                                <span class="team home-team">${fixture.homeTeam().name()}</span>

                                <!-- Eredmény / Elválasztó -->
                                <span class="score-container">
                                    <c:choose>
                                        <c:when test="${fixture.isFinished()}">
                                            ${fixture.homeScore()} - ${fixture.awayScore()}
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </span>

                                <!-- Vendég csapat -->
                                <span class="team away-team">${fixture.awayTeam().name()}</span>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>