<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fixtures</title>
    <!-- Meglévő globális és az új sorsolás specifikus CSS -->
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/schedule.css'/>">
    <!-- FontAwesome az ikonokhoz (H/A ikonok) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="main-container">
        <!-- Változatlan kék sormenü a konténer tetején -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/menu/open-season'/>">Career Menu</a></li>
            </ul>
        </nav>

        <!-- 1. Csapatválasztó doboz (Nagyon vékony panel) -->
        <div class="team-selector-container">
            <div class="selector-header-bar">
                <span>Team Selection</span>
            </div>
            <div class="selector-body">
                <form action="<c:url value='/career/schedule/get-schedule'/>" method="get" class="selector-form">
                    <label for="teamSelect">Select Team:</label>
                    <select name="teamId" id="teamSelect" onchange="this.form.submit()">
                        <c:forEach var="team" items="${teamList}">
                            <option value="${team.id()}" ${team.id() == selectedTeam.id() ? 'selected' : ''}>
                                ${team.name()}
                            </option>
                        </c:forEach>
                    </select>
                </form>
            </div>
        </div>

        <!-- 2. Fő Menetrend/Sorsolás táblázat konténer -->
        <div class="fixture-table-container">
            <!-- Vékony kék sáv sárga csapatnévvel -->
            <div class="fixture-header-bar">
                <span>Fixtures - <span class="highlight-team">${selectedTeam.name()}</span></span>
            </div>

            <table class="fixture-table">
                <thead>
                    <tr>
                        <th class="col-week">Week</th>
                        <th class="col-opponent">Opponent</th>
                        <th class="col-venue"></th> <!-- Névtelen H/A oszlop -->
                        <th class="col-result">Result</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="fixture" items="${fixtureList}">
                        <tr>
                            <!-- 1. Week oszlop -->
                            <td class="col-week">${fixture.matchWeek()}</td>

                            <!-- 2. Opponent oszlop (A kiválasztott csapat szempontjából az ellenfél) -->
                            <td class="col-opponent">
                                <c:choose>
                                    <c:when test="${fixture.homeTeam().id() == selectedTeam.id()}">
                                        ${fixture.awayTeam().name()}
                                    </c:when>
                                    <c:otherwise>
                                        ${fixture.homeTeam().name()}
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- 3. Venue ikon oszlop (Hazai = Kék ház, Vendég = Fehér/Lila ház vagy repülő) -->
                            <td class="col-venue">
                                <c:choose>
                                    <c:when test="${fixture.homeTeam().id() == selectedTeam.id()}">
                                        <i class="fa-solid fa-house venue-icon venue-home" title="Home"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fa-solid fa-house venue-icon venue-away" title="Away"></i>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- 4. Result oszlop -->
                            <td class="col-result">
                                <c:choose>
                                    <c:when test="${fixture.isFinished()}">
                                        <div class="result-cell">
                                            <!-- Eredmény szövege -->
                                            <span class="result-score">${fixture.homeScore()} - ${fixture.awayScore()}</span>

                                            <!-- Kimenetelek (W / D / L) kiszámítása a kiválasztott csapat szempontjából -->
                                            <c:set var="isHome" value="${fixture.homeTeam().id() == selectedTeam.id()}" />
                                            <c:set var="teamGoals" value="${isHome ? fixture.homeScore() : fixture.awayScore()}" />
                                            <c:set var="oppGoals" value="${isHome ? fixture.awayScore() : fixture.homeScore()}" />

                                            <c:choose>
                                                <c:when test="${teamGoals > oppGoals}">
                                                    <span class="status-badge status-w" title="Win">W</span>
                                                </c:when>
                                                <c:when test="${teamGoals == oppGoals}">
                                                    <span class="status-badge status-d" title="Draw">D</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-badge status-l" title="Loss">L</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="upcoming-text">- : -</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>