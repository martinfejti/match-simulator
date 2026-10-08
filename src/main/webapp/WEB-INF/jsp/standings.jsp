<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>League Table & Statistics</title>
    <link rel="stylesheet" href="<c:url value='/css/standings.css'/>">
</head>
<body>
    <div class="main-container">
        <!-- Menüsor -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/menu/open-season'/>">Career Menu</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
            </ul>
        </nav>

        <!-- 1. TABELLA DOBOZ: Közös fejléc, egymás melletti Felsőház és Alsóház -->
        <div class="card-box">
            <div class="card-header">
                <h2>League Table</h2>
            </div>
            <div class="card-body standings-split-container">
                
                <!-- FELSŐHÁZ (Top Half: 1 - 10) -->
                <div class="standings-half">
                    <table class="standings-table">
                        <thead>
                            <tr>
                                <th class="col-pos">#</th>
                                <th class="col-club">Club</th>
                                <th class="col-stat" title="Matches played">MP</th>
                                <th class="col-stat" title="Wins">W</th>
                                <th class="col-stat" title="Draws">D</th>
                                <th class="col-stat" title="Losses">L</th>
                                <th class="col-stat col-pts" title="Points">P</th>
                                <th class="col-gfga" title="Goals for - Goals against">GF - GA</th>
                                <th class="col-stat" title="Goal difference">GD</th>
                                <th class="col-perf" title="Last match result">LM</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${standingList}" varStatus="status" begin="0" end="${standingList.size() / 2 - 1}">
                                <tr>
                                    <td class="col-pos">${status.index + 1}</td>
                                    <td class="col-club">${item.name()}</td>
                                    <td class="col-stat">${item.matchesPlayed()}</td>
                                    <td class="col-stat">${item.wins()}</td>
                                    <td class="col-stat">${item.draws()}</td>
                                    <td class="col-stat">${item.losses()}</td>
                                    <td class="col-stat col-pts">${item.points()}</td>
                                    <td class="col-gfga">${item.goalsForAndAgainstFormatted()}</td>
                                    <td class="col-stat">${item.goalDifference()}</td>
                                    <td class="col-perf">
                                        <c:choose>
                                            <c:when test="${item.lastMatchResult() == 'W'}">
                                                <span class="status-badge status-w" title="Win">W</span>
                                            </c:when>
                                            <c:when test="${item.lastMatchResult() == 'D'}">
                                                <span class="status-badge status-d" title="Draw">D</span>
                                            </c:when>
                                            <c:when test="${item.lastMatchResult() == 'L'}">
                                                <span class="status-badge status-l" title="Loss">L</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="upcoming-text">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- ALSÓHÁZ (Bottom Half: 11 - 20) -->
                <div class="standings-half">
                    <table class="standings-table">
                        <thead>
                            <tr>
                                <th class="col-pos">#</th>
                                <th class="col-club">Club</th>
                                <th class="col-stat" title="Matches played">MP</th>
                                <th class="col-stat" title="Wins">W</th>
                                <th class="col-stat" title="Draws">D</th>
                                <th class="col-stat" title="Losses">L</th>
                                <th class="col-stat col-pts" title="Points">P</th>
                                <th class="col-gfga" title="Goals for - Goals against">GF - GA</th>
                                <th class="col-stat" title="Goal difference">GD</th>
                                <th class="col-perf" title="Last match result">LM</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${standingList}" varStatus="status" begin="${standingList.size() / 2}" end="${standingList.size() - 1}">
                                <tr>
                                    <td class="col-pos">${status.index + 1}</td>
                                    <td class="col-club">${item.name()}</td>
                                    <td class="col-stat">${item.matchesPlayed()}</td>
                                    <td class="col-stat">${item.wins()}</td>
                                    <td class="col-stat">${item.draws()}</td>
                                    <td class="col-stat">${item.losses()}</td>
                                    <td class="col-stat col-pts">${item.points()}</td>
                                    <td class="col-gfga">${item.goalsForAndAgainstFormatted()}</td>
                                    <td class="col-stat">${item.goalDifference()}</td>
                                    <td class="col-perf">
                                        <c:choose>
                                            <c:when test="${item.lastMatchResult() == 'W'}">
                                                <span class="status-badge status-w" title="Win">W</span>
                                            </c:when>
                                            <c:when test="${item.lastMatchResult() == 'D'}">
                                                <span class="status-badge status-d" title="Draw">D</span>
                                            </c:when>
                                            <c:when test="${item.lastMatchResult() == 'L'}">
                                                <span class="status-badge status-l" title="Loss">L</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="upcoming-text">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>

        <!-- 2. ALSÓ STATISZTIKAI DOBOZOK (2x2 GRID ELRENDEZÉS) -->
        <div class="stats-grid">
            
            <!-- 1. DOBOZ: Top Scorers (Góllövőlista) -->
            <div class="card-box">
                <div class="card-header">
                    <h2>Top Scorers</h2>
                </div>
                <div class="card-body compact-body">
                    <ul class="compact-stat-list">
                        <c:forEach var="player" items="${playerStatistics.goalScorerList()}" varStatus="status" end="14">
                            <li class="compact-stat-row">
                                <div class="stat-left">
                                    <span class="rank-num">${status.index + 1}</span>
                                    <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-icon" onerror="this.style.display='none'">
                                    <span class="player-name">${player.playerName()}</span>
                                    <span class="team-subname">(${player.teamName()})</span>
                                </div>
                                <div class="stat-right">
                                    <span class="stat-count">${player.numberOfGoals()}</span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <!-- 2. DOBOZ: Clean Sheets (Kapott gól nélküli meccsek) -->
            <div class="card-box">
                <div class="card-header">
                    <h2>Clean Sheets</h2>
                </div>
                <div class="card-body compact-body">
                    <ul class="compact-stat-list">
                        <c:forEach var="player" items="${playerStatistics.cleanSheetList()}" varStatus="status" end="14">
                            <li class="compact-stat-row">
                                <div class="stat-left">
                                    <span class="rank-num">${status.index + 1}</span>
                                    <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-icon" onerror="this.style.display='none'">
                                    <span class="player-name">${player.playerName()}</span>
                                    <span class="team-subname">(${player.teamName()})</span>
                                </div>
                                <div class="stat-right">
                                    <span class="stat-count">${player.numberOfCleanSheets()}</span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <!-- 3. DOBOZ: Yellow Cards (Sárga lapok) -->
            <div class="card-box">
                <div class="card-header">
                    <h2>Yellow Cards</h2>
                </div>
                <div class="card-body compact-body">
                    <ul class="compact-stat-list">
                        <c:forEach var="player" items="${playerStatistics.yellowCardList()}" varStatus="status" end="14">
                            <li class="compact-stat-row">
                                <div class="stat-left">
                                    <span class="rank-num">${status.index + 1}</span>
                                    <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-icon" onerror="this.style.display='none'">
                                    <span class="player-name">${player.playerName()}</span>
                                    <span class="team-subname">(${player.teamName()})</span>
                                </div>
                                <div class="stat-right">
                                    <span class="stat-count">${player.numberOfYellowCards()}</span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <!-- 4. DOBOZ: Red Cards (Piros lapok) -->
            <div class="card-box">
                <div class="card-header">
                    <h2>Red Cards</h2>
                </div>
                <div class="card-body compact-body">
                    <ul class="compact-stat-list">
                        <c:forEach var="player" items="${playerStatistics.redCardList()}" varStatus="status" end="14">
                            <li class="compact-stat-row">
                                <div class="stat-left">
                                    <span class="rank-num">${status.index + 1}</span>
                                    <img src="<c:url value='/flag/${player.nationality()}.png'/>" alt="${player.nationality()}" class="flag-icon" onerror="this.style.display='none'">
                                    <span class="player-name">${player.playerName()}</span>
                                    <span class="team-subname">(${player.teamName()})</span>
                                </div>
                                <div class="stat-right">
                                    <span class="stat-count">${player.numberOfRedCards()}</span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

        </div>

    </div>
</body>
</html>