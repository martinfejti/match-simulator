<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Load saved game</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/newsave.css'/>">
</head>
<body>
    <div class="main-container">
        <!-- Fejléc sormenü a konténer tetején -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/mainmenu'/>">Main Menu</a></li>
                <li><a href="<c:url value='/mainmenu/go-to-create-new-season'/>">New Season</a></li>
                <li><a href="#" class="active">Load Season</a></li>
            </ul>
        </nav>

        <!-- Az oldal tartalma -->
        <div class="content">
            <h1>Load save</h1>
            <p>Select the save you wish to continue</p>
        </div>

        <!-- Táblázat konténer -->
        <div class="table-container">
            <!-- Vékony kék sáv a táblázat tetején, sárga szöveggel -->
            <div class="table-header-bar">
                <span>Existing saves</span>
            </div>

            <!-- A táblázat a kért oszlopsorrenddel -->
            <table class="custom-table">
                <thead>
                    <tr>
                        <th class="col-savename">Save Name</th>
                        <th class="col-season">Date</th>
                        <th class="col-flag">Country</th>
                        <th class="col-league">League</th>
                        <th class="col-action text-end"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="save" items="${saveList}">
                        <tr>
                            <td class="col-savename">
                                <strong>${save.name()}</strong>
                            </td>
                            <td class="col-season">
                                <c:out value="${save.season().date()}"/>
                            </td>
                            <td class="col-flag">
                                    <img src="<c:url value='/flag/${save.season().flag().code()}.png'/>" 
                                         alt="Flag" 
                                         class="flag-icon" />
                            </td>
                            <td class="col-league">
                                <c:out value="${save.season().league()}"/>
                            </td>
                            <td class="col-action text-end">
                                <c:url var="loadSaveUrl" value='/mainmenu/open-save'>
                                    <c:param name="saveId" value="${save.id()}"/>
                                </c:url>
                                <a href="${loadSaveUrl}" class="table-btn">Load</a>
                            </td>
                        </tr>
                    </c:forEach>

                    <!-- Ha a lista üres lenne -->
                    <c:if test="${empty saveList}">
                        <tr>
                            <td colspan="5" style="text-align: center; color: #888; padding: 15px;">
                                No saved games found.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>