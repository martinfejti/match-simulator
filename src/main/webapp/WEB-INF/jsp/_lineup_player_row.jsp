<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="isUnavailable" value="${p.injuredFor() > 0 || p.excludedFor() > 0}" />

<tr id="player-row-${p.id()}"
    class="player-row ${isUnavailable ? 'row-disabled' : ''}"
    draggable="${!isUnavailable}"
    ondragstart="drag(event)"
    onclick="selectPlayer('${p.id()}')"
    data-player-id="${p.id()}"
    data-name="${p.name()}"
    data-number="${p.shirtNumber()}"
    data-pos="${p.primaryPosition()}"
    data-overall="${p.overall()}"
    data-flag="${p.nationality()}">

    <!-- Mezszám -->
    <td class="col-num">
        <span class="shirt-number">${p.shirtNumber()}</span>
    </td>

    <!-- Zászló -->
    <td class="col-flag">
        <span class="fi fi-${p.nationality().toLowerCase()} flag-icon"></span>
    </td>

    <!-- Név -->
    <td class="col-name text-truncate" title="${p.name()}">
        ${p.name()}
    </td>

    <!-- Pozíció -->
    <td class="col-pos-code">
        ${p.primaryPosition()}
    </td>

    <!-- Overall -->
    <td class="col-stat col-overall">
        <span class="highlight-overall">${p.overall()}</span>
    </td>

    <!-- Energy bar -->
    <td class="col-energy">
        <div class="energy-bar-container" title="Energy: ${p.energy()}%">
            <div class="energy-bar-fill ${p.energy() >= 60 ? 'energy-green' : (p.energy() >= 30 ? 'energy-yellow' : 'energy-red')}"
                 style="width: ${p.energy()}%;"></div>
        </div>
    </td>

    <!-- Sérülés / Eltiltás jelzés -->
    <td class="col-badge">
        <c:choose>
            <c:when test="${p.injuredFor() > 0}">
                <span class="injury-badge" title="Injured for ${p.injuredFor()} match(es)"><i class="fa-solid fa-plus"></i>${p.injuredFor()}</span>
            </c:when>
            <c:when test="${p.excludedFor() > 0}">
                <span class="red-card-badge" title="Suspended for ${p.excludedFor()} match(es)">${p.excludedFor()}</span>
            </c:when>
            <c:otherwise>-</c:otherwise>
        </c:choose>
    </td>
</tr>