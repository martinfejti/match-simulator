<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<tr>
    <!-- 1. Zászló (Nemzetiség - kisbetűs ISO kód, pl. hu, gb, de, br) -->
    <td class="col-flag">
        <img src="<c:url value='/flag/${p.nationality().toLowerCase()}.png'/>" alt="${p.nationality()}" class="flag-icon" title="${p.nationality()}">
    </td>

    <!-- 2. Név -->
    <td class="col-name">${p.name()}</td>

    <!-- 3-5. Képességek: O, BC, SC -->
    <td class="col-stat col-overall">
        <span class="highlight-overall">${p.overall()}</span>
    </td>
    <td class="col-stat">${p.bigChanceFinishing()}</td>
    <td class="col-stat">${p.smallChanceFinishing()}</td>

    <!-- 6. Energy Zöld Bar -->
    <td class="col-energy">
        <div class="energy-bar-container" title="Energy: ${p.energy()}%">
            <div class="energy-bar-fill" style="width: ${p.energy()}%;"></div>
        </div>
    </td>

    <!-- 7. Sérülés: Piros kereszt a számmal, ha > 0 -->
    <td class="col-badge">
        <c:if test="${p.injuredFor() > 0}">
            <div class="injury-badge" title="Injured for ${p.injuredFor()} match(es)">
                <i class="fa-solid fa-plus"></i>
                <span>${p.injuredFor()}</span>
            </div>
        </c:if>
    </td>

    <!-- 8. Eltiltás: Piros lap a számmal, ha > 0 -->
    <td class="col-badge">
        <c:if test="${p.excludedFor() > 0}">
            <div class="red-card-badge" title="Excluded for ${p.excludedFor()} match(es)">
                <span>${p.excludedFor()}</span>
            </div>
        </c:if>
    </td>

    <!-- 9-13. Statisztikák: MP, G, CS, YC, RC -->
    <td class="col-stat">${p.matchesPlayed()}</td>
    <td class="col-stat">${p.numberOfGoals()}</td>
    <td class="col-stat">${p.cleanSheets()}</td>
    <td class="col-stat">${p.numberOfYellowCards()}</td>
    <td class="col-stat">${p.numberOfRedCards()}</td>
</tr>