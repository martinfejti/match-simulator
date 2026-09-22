<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<tr>
    <!-- 1. Mezszám (Kékes badge) -->
    <td class="col-num">
        <span class="shirt-number">${p.shirtNumber()}</span>
    </td>

    <!-- 2. Zászló -->
    <td class="col-flag">
        <img src="<c:url value='/flag/${p.nationality()}.png'/>" alt="${p.nationality()}" class="flag-icon" title="${p.nationality()}">
    </td>

    <!-- 3. Név -->
    <td class="col-name">${p.name()}</td>

    <!-- 4. Poszt (GK, CB, ST stb.) -->
    <td class="col-pos-code">${p.primaryPosition()}</td>

    <!-- 5. Overall (Kiemelt érték) -->
    <td class="col-stat col-overall">
        <span class="highlight-overall">${p.overall()}</span>
    </td>

    <!-- 6-7. Képességek: BC, SC -->
    <td class="col-stat">${p.bigChanceFinishing()}</td>
    <td class="col-stat">${p.smallChanceFinishing()}</td>

    <!-- 8. Energy Bar (Dinamikus színekkel: Zöld / Sárga / Piros) -->
    <td class="col-energy">
        <c:choose>
            <c:when test="${p.energy() >= 60}">
                <c:set var="energyClass" value="energy-green" />
            </c:when>
            <c:when test="${p.energy() >= 30}">
                <c:set var="energyClass" value="energy-yellow" />
            </c:when>
            <c:otherwise>
                <c:set var="energyClass" value="energy-red" />
            </c:otherwise>
        </c:choose>
        <div class="energy-bar-container" title="Energy: ${p.energy()}%">
            <div class="energy-bar-fill ${energyClass}" style="width: ${p.energy()}%;"></div>
        </div>
    </td>

    <!-- 9. Sérülés: Piros kereszt a számmal, ha > 0 -->
    <td class="col-badge">
        <c:if test="${p.injuredFor() > 0}">
            <div class="injury-badge" title="Injured for ${p.injuredFor()} match(es)">
                <i class="fa-solid fa-plus"></i>
                <span>${p.injuredFor()}</span>
            </div>
        </c:if>
    </td>

    <!-- 10. Eltiltás: Piros lap a számmal, ha > 0 -->
    <td class="col-badge">
        <c:if test="${p.excludedFor() > 0}">
            <div class="red-card-badge" title="Excluded for ${p.excludedFor()} match(es)">
                <span>${p.excludedFor()}</span>
            </div>
        </c:if>
    </td>

    <!-- 11-15. Statisztikák: MP, G, CS, YC, RC -->
    <td class="col-stat">${p.matchesPlayed()}</td>
    <td class="col-stat">${p.numberOfGoals()}</td>
    <td class="col-stat">${p.cleanSheets()}</td>
    <td class="col-stat">${p.numberOfYellowCards()}</td>
    <td class="col-stat">${p.numberOfRedCards()}</td>
</tr>