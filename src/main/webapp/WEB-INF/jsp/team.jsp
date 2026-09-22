<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Team Squad - ${selectedTeam.name()}</title>
    <!-- Meglévő globális és a csapatspecifikus CSS -->
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/squad.css'/>">
    <!-- Flag Icons a nemzetiségi zászlókhoz -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lipis/flag-icons@6.11.0/css/flag-icons.min.css">
    <!-- FontAwesome a piros kereszt ikonhoz -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="main-container">
        <!-- Változatlan kék sormenü -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/career/menu/go-back-to-main-menu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/career/menu/open-season'/>">Career Menu</a></li>
                <li><a href="<c:url value='/career/standings'/>">Standings</a></li>
                <li><a href="<c:url value='/career/schedule/get-schedule'/>">Schedule</a></li>
            </ul>
        </nav>

        <!-- 1. Csapatválasztó doboz -->
        <div class="team-selector-container">
            <div class="selector-header-bar">
                <span>Team Selection</span>
            </div>
            <div class="selector-body">
                <form action="<c:url value='/career/team/get-team'/>" method="get" class="selector-form">
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

        <!-- 2. Keret / Játékosok táblázat konténer -->
        <div class="squad-table-container">
            <!-- Vékony kék sáv sárga csapatnévvel -->
            <div class="squad-header-bar">
                <span>Squad - <span class="highlight-team">${selectedTeam.name()}</span></span>
            </div>

            <table class="squad-table">
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
                        <th class="col-badge" title="Injured For (matches)">Inj.</th>
                        <th class="col-badge" title="Excluded For (matches)">Exc.</th>
                        <th class="col-stat" title="Matches Played">MP</th>
                        <th class="col-stat" title="Goals">G</th>
                        <th class="col-stat" title="Clean Sheets">CS</th>
                        <th class="col-stat" title="Yellow Cards">YC</th>
                        <th class="col-stat" title="Red Cards">RC</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- KAPUSOK (Goalkeepers) -->
                    <tr class="section-header-row">
                        <td colspan="15">Goalkeepers</td>
                    </tr>
                    <c:forEach var="player" items="${goalkeeperList}">
                        <c:set var="p" value="${player}" scope="request" />
                        <jsp:include page="_player_row.jsp" />
                    </c:forEach>

                    <!-- VÉDŐK (Defenders) -->
                    <tr class="section-header-row">
                        <td colspan="15">Defenders</td>
                    </tr>
                    <c:forEach var="player" items="${defenderList}">
                        <c:set var="p" value="${player}" scope="request" />
                        <jsp:include page="_player_row.jsp" />
                    </c:forEach>

                    <!-- KÖZÉPPÁLYÁSOK (Midfielders) -->
                    <tr class="section-header-row">
                        <td colspan="15">Midfielders</td>
                    </tr>
                    <c:forEach var="player" items="${midfielderList}">
                        <c:set var="p" value="${player}" scope="request" />
                        <jsp:include page="_player_row.jsp" />
                    </c:forEach>

                    <!-- TÁMADÓK (Forwards) -->
                    <tr class="section-header-row">
                        <td colspan="15">Forwards</td>
                    </tr>
                    <c:forEach var="player" items="${forwardList}">
                        <c:set var="p" value="${player}" scope="request" />
                        <jsp:include page="_player_row.jsp" />
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>