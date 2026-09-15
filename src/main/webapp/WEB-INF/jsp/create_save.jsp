<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Save</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/season_selector.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/create_save.css'/>">
</head>
<body>
    <div class="main-container">
        <!-- Fejléc sormenü -->
        <nav class="navbar">
            <ul class="nav-menu">
                <li><a href="<c:url value='/menubar'/>">Main Menu</a></li>
                <li><a href="<c:url value='/menubar/go-to-create-new-season'/>" class="active">New Season</a></li>
                <li><a href="<c:url value='/menubar/go-to-load-season'/>">Load Season</a></li>
            </ul>
        </nav>

        <!-- Központi szöveges blokk -->
        <div class="content">
            <h1>Create New Save</h1>
            <p>Enter a unique name for your save file to start the season</p>
        </div>

        <!-- Mentés létrehozása és szezon részletei konténer -->
        <div class="table-container">
            <div class="table-header-bar">
                <span>Selected Season Details</span>
            </div>

            <div class="save-form-padding">
                <!-- 1. Form a mentés nevének megadásához és elküldéséhez -->
                <form action="<c:url value='/selector/save/create-new-save'/>" method="post" class="save-input-row">
                    <!-- Rejtett mezőben átadjuk a kiválasztott szezon ID-ját -->
                    <input type="hidden" name="seasonId" value="${selectedSeason.id()}" />

                    <!-- Szöveges input mező -->
                    <input type="text" 
                           name="saveName" 
                           class="save-name-input" 
                           placeholder="Enter save name (e.g. My Career 2026)..." 
                           required 
                           autocomplete="off" />

                    <!-- Kék gomb sárga hoverrel -->
                    <button type="submit" class="table-btn save-btn">Start Career</button>
                </form>

                <!-- 2. A kiválasztott szezon részletei -->
                <div class="season-details-card">
                    <div class="detail-item">
                        <span class="detail-label">Flag:</span>
                        <c:if test="${not empty selectedSeason.flag()}">
                            <img src="<c:url value='/flag/${selectedSeason.flag()}.png'/>"
                                    alt="${selectedSeason.flag()}" 
                                    class="flag-icon" />
                        </c:if>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">League:</span>
                        <span class="detail-value"><c:out value="${selectedSeason.league()}"/></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Date / Season:</span>
                        <span class="detail-value"><c:out value="${selectedSeason.date()}"/></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>