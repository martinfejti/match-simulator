<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Match Results - ${fixture.homeTeam().name()} vs ${fixture.awayTeam().name()}</title>
    <!-- Dedikált CSS fájl a Match Results oldalhoz -->
    <link rel="stylesheet" href="<c:url value='/css/match_result.css'/>">
    <!-- FontAwesome az ikonokhoz -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="match-results-wrapper">

        <!-- Top Menubar -->
        <nav class="preview-menubar">
            <ul class="preview-nav-links">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/menu/open-season'/>">Career Menu</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
            </ul>
        </nav>

        <!-- FELSŐ DOBOZ: Match Banner (Eredmény Kijelző) -->
        <div class="preview-box match-banner-box">
            <div class="box-header">
                <span class="box-header-title">Matchweek ${fixture.matchWeek()} - Fixture ${fixture.matchNumberInWeek()} (Full Time)</span>
                <span class="match-date-badge">${fixture.matchDate()}</span>
            </div>

            <div class="box-content banner-content">
                <!-- Hazai csapat info -->
                <div class="team-profile home-profile">
                    <div class="team-details">
                        <span class="team-name">${fixture.homeTeam().name()}</span>
                    </div>
                </div>

                <!-- Középső SCORE / Eredmény doboz -->
                <div class="score-container">
                    <div class="score-badge">
                        <span class="score-text">${fixture.homeScore()} - ${fixture.awayScore()}</span>
                    </div>
                </div>

                <!-- Vendég csapat info -->
                <div class="team-profile away-profile">
                    <div class="team-details align-right">
                        <span class="team-name">${fixture.awayTeam().name()}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- ÚJ DOBOZ: Key Match Events (Események Doboz) -->
        <div class="preview-box events-box">
            <div class="box-header events-header">
                <span class="box-header-title">${fixture.homeTeam().name()}</span>
                <span class="box-header-title center-title">Match Events</span>
                <span class="box-header-title align-right">${fixture.awayTeam().name()}</span>
            </div>

            <div class="box-content events-content">
                <c:choose>
                    <c:when test="${not empty matchEventList}">
                        <ul class="events-list">
                            <c:forEach var="event" items="${matchEventList}">
                                <li class="event-row">
                                    <!-- HAZAI OLDAL -->
                                    <div class="event-side home-event-side">
                                        <c:if test="${event.isHomeTeam()}">
                                            <span class="event-player-name">${event.playerName()}</span>

                                            <!-- Ikon megjelenítés esemény típusa alapján -->
                                            <c:choose>
                                                <c:when test="${event.eventType() == 'Goal'}">
                                                    <i class="fa-solid fa-futbol match-icon ball-icon" title="Goal"></i>
                                                </c:when>
                                                <c:when test="${event.eventType() == 'Yellow Card'}">
                                                    <span class="card-icon yellow-card" title="Yellow Card"></span>
                                                </c:when>
                                                <c:when test="${event.eventType() == 'Red Card'}">
                                                    <span class="card-icon red-card" title="Red Card (${event.exclusionLength()} match ban)">
                                                        <c:if test="${not empty event.exclusionLength() && event.exclusionLength() > 0}">${event.exclusionLength()}</c:if>
                                                    </span>
                                                </c:when>
                                                <c:when test="${event.eventType() == 'Injury'}">
                                                    <span class="injury-badge" title="Injured (${event.injuryLength()} matches out)">
                                                        <i class="fa-solid fa-plus"></i>
                                                        <c:if test="${not empty event.injuryLength() && event.injuryLength() > 0}">
                                                            <span class="injury-count">${event.injuryLength()}</span>
                                                        </c:if>
                                                    </span>
                                                </c:when>
                                            </c:choose>
                                        </c:if>
                                    </div>

                                    <!-- KÖZÉPSŐ ESEMÉNY TÍPUS -->
                                    <div class="event-type-badge">
                                        <span>${event.eventType()}</span>
                                    </div>

                                    <!-- VENDÉG OLDAL -->
                                    <div class="event-side away-event-side">
                                        <c:if test="${!event.isHomeTeam()}">
                                            <!-- Ikon megjelenítés esemény típusa alapján -->
                                            <c:choose>
                                                <c:when test="${event.eventType() == 'Goal'}">
                                                    <i class="fa-solid fa-futbol match-icon ball-icon" title="Goal"></i>
                                                </c:when>
                                                <c:when test="${event.eventType() == 'Yellow Card'}">
                                                    <span class="card-icon yellow-card" title="Yellow Card"></span>
                                                </c:when>
                                                <c:when test="${event.eventType() == 'Red Card'}">
                                                    <span class="card-icon red-card" title="Red Card (${event.exclusionLength()} match ban)">
                                                        <c:if test="${not empty event.exclusionLength() && event.exclusionLength() > 0}">${event.exclusionLength()}</c:if>
                                                    </span>
                                                </c:when>
                                                <c:when test="${event.eventType() == 'Injury'}">
                                                    <span class="injury-badge" title="Injured (${event.injuryLength()} matches out)">
                                                        <i class="fa-solid fa-plus"></i>
                                                        <c:if test="${not empty event.injuryLength() && event.injuryLength() > 0}">
                                                            <span class="injury-count">${event.injuryLength()}</span>
                                                        </c:if>
                                                    </span>
                                                </c:when>
                                            </c:choose>

                                            <span class="event-player-name">${event.playerName()}</span>
                                        </c:if>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <div class="no-events-msg">No major match events recorded.</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- KÖZÉPSŐ DOBOZ: Kezdőcsapatok és Meccs-események -->
        <div class="preview-box lineups-box">
            <div class="box-header">
                <span class="box-header-title">Match Lineups</span>
            </div>

            <div class="box-content lineups-content">
                <!-- HAZAI OLDAL -->
                <div class="lineup-side home-side">
                    <div class="team-lineup-header">
                        <span class="team-title-text">${fixture.homeTeam().name()}</span>
                        <c:if test="${not empty fixture.homeFormation()}">
                            <span class="formation-text">(${fixture.homeFormation()})</span>
                        </c:if>
                    </div>

                    <ul class="player-list">
                        <c:forEach var="player" items="${homeTeamPlayerList}">
                            <li class="player-row-item">
                                <div class="player-info-left">
                                    <span class="position-badge">${player.position()}</span>
                                    <span class="shirt-badge">${player.shirtNumber()}</span>
                                    <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-img">
                                    <span class="player-fullname">${player.name()}</span>
                                </div>
                                <div class="player-info-right">
                                    <!-- ESEMÉNY IKONOK -->
                                    <div class="player-events-container">
                                        <!-- Gólok (Fekete-fehér labda annyiszor, ahány gól) -->
                                        <c:if test="${player.numberOfGoals() > 0}">
                                            <span class="event-goals">
                                                <c:forEach begin="1" end="${player.numberOfGoals()}">
                                                    <i class="fa-solid fa-futbol match-icon ball-icon" title="Goal"></i>
                                                </c:forEach>
                                            </span>
                                        </c:if>

                                        <!-- Sárga lap -->
                                        <c:if test="${player.hasYellowCard()}">
                                            <span class="card-icon yellow-card" title="Yellow Card"></span>
                                        </c:if>

                                        <!-- Piros lap + Eltiltási meccsek száma -->
                                        <c:if test="${player.excludedFor() > 0}">
                                            <span class="card-icon red-card" title="Red Card (${player.excludedFor()} match ban)">
                                                ${player.excludedFor()}
                                            </span>
                                        </c:if>

                                        <!-- Sérülés piros kereszt + Kihagyott meccsek száma -->
                                        <c:if test="${player.injuredFor() > 0}">
                                            <span class="injury-badge" title="Injured (${player.injuredFor()} matches out)">
                                                <i class="fa-solid fa-plus"></i>
                                                <span class="injury-count">${player.injuredFor()}</span>
                                            </span>
                                        </c:if>
                                    </div>

                                    <span class="overall-badge">${player.overall()}</span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="lineup-divider"></div>

                <!-- VENDÉG OLDAL -->
                <div class="lineup-side away-side">
                    <div class="team-lineup-header align-right">
                        <c:if test="${not empty fixture.awayFormation()}">
                            <span class="formation-text">(${fixture.awayFormation()})</span>
                        </c:if>
                        <span class="team-title-text">${fixture.awayTeam().name()}</span>
                    </div>

                    <ul class="player-list">
                        <c:forEach var="player" items="${awayTeamPlayerList}">
                            <li class="player-row-item reverse-row">
                                <div class="player-info-left">
                                    <span class="position-badge">${player.position()}</span>
                                    <span class="shirt-badge">${player.shirtNumber()}</span>
                                    <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-img">
                                    <span class="player-fullname">${player.name()}</span>
                                </div>
                                <div class="player-info-right">
                                    <!-- ESEMÉNY IKONOK -->
                                    <div class="player-events-container">
                                        <!-- Gólok -->
                                        <c:if test="${player.numberOfGoals() > 0}">
                                            <span class="event-goals">
                                                <c:forEach begin="1" end="${player.numberOfGoals()}">
                                                    <i class="fa-solid fa-futbol match-icon ball-icon" title="Goal"></i>
                                                </c:forEach>
                                            </span>
                                        </c:if>

                                        <!-- Sárga lap -->
                                        <c:if test="${player.hasYellowCard()}">
                                            <span class="card-icon yellow-card" title="Yellow Card"></span>
                                        </c:if>

                                        <!-- Piros lap + Eltiltás -->
                                        <c:if test="${player.excludedFor() > 0}">
                                            <span class="card-icon red-card" title="Red Card (${player.excludedFor()} match ban)">
                                                ${player.excludedFor()}
                                            </span>
                                        </c:if>

                                        <!-- Sérülés -->
                                        <c:if test="${player.injuredFor() > 0}">
                                            <span class="injury-badge" title="Injured (${player.injuredFor()} matches out)">
                                                <i class="fa-solid fa-plus"></i>
                                                <span class="injury-count">${player.injuredFor()}</span>
                                            </span>
                                        </c:if>
                                    </div>

                                    <span class="overall-badge">${player.overall()}</span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <!-- ALSÓ DOBOZ: Detailed match stats (Mérkőzés Részletes Statisztikái) -->
        <div class="preview-box stats-box">
            <div class="box-header stats-header">
                <span class="box-header-title">Detailed match stats</span>
            </div>

            <div class="box-content stats-content">
                <table class="stats-table">
                    <tbody>
                        <!-- Team -->
                        <tr>
                            <td class="stat-value home-val">${fixture.homeTeam().name()}</td>
                            <td class="stat-label">Team</td>
                            <td class="stat-value away-val">${fixture.awayTeam().name()}</td>
                        </tr>
                        <!-- Recent performance -->
                        <tr>
                            <td class="stat-value home-val">
                                <c:forEach var="form" items="${recentFormContainer.homeFormList()}">
                                    <span class="perf-badge ${form.outcome()}" title="${form.details()}">
                                        ${form.outcome()}
                                    </span>
                                </c:forEach>
                            </td>
                            <td class="stat-label">Recent performance</td>
                            <td class="stat-value away-val">
                                <c:forEach var="form" items="${recentFormContainer.awayFormList()}">
                                    <span class="perf-badge ${form.outcome()}" title="${form.details()}">
                                        ${form.outcome()}
                                    </span>
                                </c:forEach>
                            </td>
                        </tr>
                        <!-- Formation -->
                        <tr>
                            <td class="stat-value home-val">${fixture.homeFormation()}</td>
                            <td class="stat-label">Formation</td>
                            <td class="stat-value away-val">${fixture.awayFormation()}</td>
                        </tr>
                        <!-- Average -->
                        <tr>
                            <td class="stat-value home-val"><fmt:formatNumber value="${averageContainer.homeTeamAverage()}" pattern="#.##" /></td>
                            <td class="stat-label">Average</td>
                            <td class="stat-value away-val"><fmt:formatNumber value="${averageContainer.awayTeamAverage()}" pattern="#.##" /></td>
                        </tr>
                        <!-- Big chances -->
                        <tr>
                            <td class="stat-value home-val">${fixture.homeBigChances()}</td>
                            <td class="stat-label">Big chances</td>
                            <td class="stat-value away-val">${fixture.awayBigChances()}</td>
                        </tr>
                        <!-- Small chances -->
                        <tr>
                            <td class="stat-value home-val">${fixture.homeSmallChances()}</td>
                            <td class="stat-label">Small chances</td>
                            <td class="stat-value away-val">${fixture.awaySmallChances()}</td>
                        </tr>
                        <!-- Yellow cards -->
                        <tr>
                            <td class="stat-value home-val">
                                ${fixture.homeYellowCards()} <span class="card-icon yellow-card inline-card"></span>
                            </td>
                            <td class="stat-label">Yellow cards</td>
                            <td class="stat-value away-val">
                                <span class="card-icon yellow-card inline-card"></span> ${fixture.awayYellowCards()}
                            </td>
                        </tr>
                        <!-- Red cards -->
                        <tr>
                            <td class="stat-value home-val">
                                ${fixture.homeRedCards()} <span class="card-icon red-card inline-card"></span>
                            </td>
                            <td class="stat-label">Red cards</td>
                            <td class="stat-value away-val">
                                <span class="card-icon red-card inline-card"></span> ${fixture.awayRedCards()}
                            </td>
                        </tr>
                        <!-- Injuries -->
                        <tr>
                            <td class="stat-value home-val">${homeInjuriesCount}</td>
                            <td class="stat-label">Injuries</td>
                            <td class="stat-value away-val">${awayInjuriesCount}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</body>
</html>