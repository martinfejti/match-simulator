<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Match Preview - ${homeTeam.name()} vs ${awayTeam.name()}</title>
    <!-- Egyetlen dedikált CSS fájl -->
    <link rel="stylesheet" href="<c:url value='/css/match_preview.css'/>">
    <!-- FontAwesome az ikonokhoz -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="match-preview-wrapper">

        <!-- Top Menubar -->
        <nav class="preview-menubar">
            <ul class="preview-nav-links">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/menu/open-season'/>">Career Menu</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
            </ul>
        </nav>

        <!-- FELSŐ DOBOZ: Match Banner (Mérkőzés adatai) -->
        <div class="preview-box match-banner-box">
            <div class="box-header">
                <span class="box-header-title">Matchweek ${fixture.matchWeek()} - Fixture ${fixture.matchNumberInWeek()}</span>

                <!-- Start Match gomb a fejléc jobb oldalán: csak akkor aktív, ha mindkét felállás ki van töltve -->
                <c:choose>
                    <c:when test="${not empty fixture.homeFormation() and not empty fixture.awayFormation()}">
                        <form action="<c:url value='/career/match/simulate'/>" method="post" class="header-action-form">
                            <input type="hidden" name="fixtureId" value="${fixture.id()}" />
                            <button type="submit" class="btn-start-match">
                                <i class="fa-solid fa-play"></i> Start Match
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="btn-start-match btn-disabled" disabled title="Both home and away starting 11 must be selected">
                            <i class="fa-solid fa-play"></i> Start Match
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="box-content banner-content">
                <!-- Hazai csapat info -->
                <div class="team-profile home-profile">
                    <div class="team-details">
                        <span class="team-name">${fixture.homeTeam().name()}</span>
                    </div>
                </div>

                <!-- Középső VS doboz -->
                <div class="vs-container">
                    <div class="vs-badge">VS</div>
                </div>

                <!-- Vendég csapat info -->
                <div class="team-profile away-profile">
                    <div class="team-details align-right">
                        <span class="team-name">${fixture.awayTeam().name()}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- ALSÓ DOBOZ: Kezdőcsapatok (Starting Lineups) -->
        <div class="preview-box lineups-box">
            <div class="box-header">
                <span class="box-header-title">Starting Lineups</span>
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
                        <c:choose>
                            <c:when test="${not empty homeStartingPlayerList}">
                                <c:forEach var="player" items="${homeStartingPlayerList}">
                                    <li class="player-row-item">
                                        <div class="player-info-left">
                                            <span class="shirt-badge">${player.shirtNumber()}</span>
                                            <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-img">
                                            <span class="player-fullname">${player.firstName()} ${player.lastName()}</span>
                                        </div>
                                        <div class="player-info-right">
                                            <span class="overall-badge">${player.overall()}</span>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li class="empty-lineup-msg">
                                    <span>No home lineup selected yet.</span>
                                    <c:if test="${empty fixture.homeFormation()}">
                                        <c:url var="selectHomeLineup" value="/career/lineup/select-lineup">
                                            <c:param name="fixtureId" value="${fixture.id()}"/>
                                            <c:param name="teamId" value="${fixture.homeTeam().id()}"/>
                                            <c:param name="isHomeTeam" value="true"/>
                                        </c:url>
                                        <a href="${selectHomeLineup}" class="btn-select-lineup">
                                            <i class="fa-solid fa-user-plus"></i> Select Home Starting Lineup
                                        </a>
                                    </c:if>
                                </li>
                            </c:otherwise>
                        </c:choose>
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
                        <c:choose>
                            <c:when test="${not empty awayStartingPlayerList}">
                                <c:forEach var="player" items="${awayStartingPlayerList}">
                                    <li class="player-row-item reverse-row">
                                        <div class="player-info-left">
                                            <span class="shirt-badge">${player.shirtNumber()}</span>
                                            <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-img">
                                            <span class="player-fullname">${player.firstName()} ${player.lastName()}</span>
                                        </div>
                                        <div class="player-info-right">
                                            <span class="overall-badge">${player.overall()}</span>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li class="empty-lineup-msg">
                                    <span>No away lineup selected yet.</span>
                                    <c:if test="${empty fixture.awayFormation()}">
                                        <c:url var="selectAwayLineup" value="/career/lineup/select-lineup">
                                            <c:param name="fixtureId" value="${fixture.id()}"/>
                                            <c:param name="teamId" value="${fixture.awayTeam().id()}"/>
                                            <c:param name="isHomeTeam" value="false"/>
                                        </c:url>
                                        <a href="${selectAwayLineup}" class="btn-select-lineup">
                                            <i class="fa-solid fa-user-plus"></i> Select Away Starting Lineup
                                        </a>
                                    </c:if>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>

            </div>
        </div>

    </div>
</body>
</html>