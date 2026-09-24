<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lineup Builder - ${selectedTeam.name()}</title>
    <!-- Egyedi felületi stílus (független a base.css 1000px-es korlátjától) -->
    <link rel="stylesheet" href="<c:url value='/css/lineup.css'/>">
    <!-- Flag Icons a nemzetiségi zászlókhoz -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lipis/flag-icons@6.11.0/css/flag-icons.min.css">
    <!-- FontAwesome az ikonokhoz -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="lineup-page-wrapper">
        <!-- Fejléc / Menü bar -->
        <nav class="lineup-navbar">
            <ul class="lineup-nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/menu/open-season'/>">Career Menu</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
            </ul>
        </nav>

        <!-- Rejtett mezők a mentéshez -->
        <input type="hidden" id="fixtureId" value="${fixtureId}" />
        <input type="hidden" id="teamId" value="${selectedTeam.id()}" />

        <!-- Fő széles munkaterület -->
        <div class="lineup-workspace">

            <!-- BAL OSZLOP: Kompakt Játékoslista -->
            <div class="lineup-card squad-panel">
                <div class="lineup-card-header">
                    <span>Squad - <span class="highlight-yellow">${selectedTeam.name()}</span></span>
                </div>
                <div class="lineup-card-body squad-table-scroll">
                    <table class="lineup-squad-table" id="playerTable">
                        <thead>
                            <tr>
                                <th class="col-num" title="Shirt Number">#</th>
                                <th class="col-flag"></th>
                                <th class="col-name">Name</th>
                                <th class="col-pos-code" title="Primary Position">Pos</th>
                                <th class="col-stat" title="Overall">O</th>
                                <th class="col-stat" title="Big Chance Value">BC</th>
                                <th class="col-stat" title="Small Chance Value">SC</th>
                                <th class="col-energy">Energy</th>
                                <th class="col-badge" title="Status">St</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- KAPUSOK -->
                            <tr class="section-header-row">
                                <td colspan="9">Goalkeepers</td>
                            </tr>
                            <c:forEach var="player" items="${goalkeeperList}">
                                <c:set var="p" value="${player}" scope="request" />
                                <jsp:include page="_lineup_player_row.jsp" />
                            </c:forEach>

                            <!-- VÉDŐK -->
                            <tr class="section-header-row">
                                <td colspan="9">Defenders</td>
                            </tr>
                            <c:forEach var="player" items="${defenderList}">
                                <c:set var="p" value="${player}" scope="request" />
                                <jsp:include page="_lineup_player_row.jsp" />
                            </c:forEach>

                            <!-- KÖZÉPPÁLYÁSOK -->
                            <tr class="section-header-row">
                                <td colspan="9">Midfielders</td>
                            </tr>
                            <c:forEach var="player" items="${midfielderList}">
                                <c:set var="p" value="${player}" scope="request" />
                                <jsp:include page="_lineup_player_row.jsp" />
                            </c:forEach>

                            <!-- TÁMADÓK -->
                            <tr class="section-header-row">
                                <td colspan="9">Forwards</td>
                            </tr>
                            <c:forEach var="player" items="${forwardList}">
                                <c:set var="p" value="${player}" scope="request" />
                                <jsp:include page="_lineup_player_row.jsp" />
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- JOBB OSZLOP: Taktikai Pálya (Kiszélesítve) -->
            <div class="lineup-card pitch-panel">
                <div class="lineup-card-header pitch-header-controls">
                    <div class="formation-selector-group">
                        <label for="formationSelect">Formation:</label>
                        <select id="formationSelect" onchange="changeFormation()">
                            <option value="4-3-3" selected>4-3-3</option>
                            <option value="4-4-2">4-4-2</option>
                            <option value="3-5-2">3-5-2</option>
                            <option value="4-2-3-1">4-2-3-1</option>
                            <option value="5-3-2">5-3-2</option>
                        </select>
                    </div>
                    <button type="button" class="btn-save-lineup" onclick="saveLineup()">
                        <i class="fa-solid fa-floppy-disk"></i> Save Lineup
                    </button>
                </div>

                <div class="lineup-card-body pitch-container-body">
                    <div id="pitch" class="football-pitch">
                        <!-- Pálya vonalak -->
                        <div class="pitch-line penalty-area-top"></div>
                        <div class="pitch-line goal-area-top"></div>
                        <div class="pitch-line center-circle"></div>
                        <div class="pitch-line center-line"></div>

                        <!-- A kártyák a JavaScript által dinamikusan töltődnek be ide -->
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- SCRIPT BETÖLTÉS -->
    <script>
        const SAVE_URL = "/career/lineup/save-lineup";
    </script>
    <script src="<c:url value='/js/lineup.js'/>"></script>
</body>
</html>