<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>League Table</title>
    <!-- Meglévő globális és az új tabella specifikus CSS -->
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
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
            </ul>
        </nav>

        <!-- Tabella Konténer -->
        <div class="standings-table-container">
            <!-- Kék gradiens sáv sárga felirattal -->
            <div class="standings-header-bar">
                <span>League Table</span>
            </div>

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
                        <th class="col-perf" title="Lats match result">LM</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- A t.index + 1 adja a helyezést a backend által már meglévő sorrend alapján -->
                    <c:forEach var="item" items="${standingList}" varStatus="status">
                        <tr>
                            <!-- Helyezés (1, 2, 3...) -->
                            <td class="col-pos">${status.index + 1}</td>

                            <!-- Csapat neve (A táblázat bal felét fedi le) -->
                            <td class="col-club">${item.name()}</td>

                            <!-- Statisztikai adatok -->
                            <td class="col-stat">${item.matchesPlayed()}</td>
                            <td class="col-stat">${item.wins()}</td>
                            <td class="col-stat">${item.draws()}</td>
                            <td class="col-stat">${item.losses()}</td>
                            <td class="col-stat col-pts">${item.points()}</td>
                            <td class="col-gfga">${item.goalsForAndAgainstFormatted()}</td>
                            <td class="col-stat">${item.goalDifference()}</td>

                            <!-- Utolsó meccs kimenetele (egyelőre fix zöld W ikon, ahogy kérted) -->
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
</body>
</html>