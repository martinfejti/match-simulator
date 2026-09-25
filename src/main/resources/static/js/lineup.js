/* ==========================================================================
   KEZDŐ 11 LOGIKA - SZÉLES KÉTSOROS FORMÁTUMMAL
   ========================================================================== */

const FORMATIONS = {
    "4-3-3": [
        { pos: "GK", top: 90, left: 50 },
        { pos: "LB", top: 72, left: 14 },
        { pos: "CB", top: 76, left: 38 },
        { pos: "CB", top: 76, left: 62 },
        { pos: "RB", top: 72, left: 86 },
        { pos: "CM", top: 48, left: 24 },
        { pos: "CM", top: 52, left: 50 },
        { pos: "CM", top: 48, left: 76 },
        { pos: "LW", top: 20, left: 20 },
        { pos: "ST", top: 15, left: 50 },
        { pos: "RW", top: 20, left: 80 }
    ],
    "4-4-2": [
        { pos: "GK", top: 90, left: 50 },
        { pos: "LB", top: 72, left: 14 },
        { pos: "CB", top: 76, left: 38 },
        { pos: "CB", top: 76, left: 62 },
        { pos: "RB", top: 72, left: 86 },
        { pos: "LM", top: 45, left: 14 },
        { pos: "CM", top: 48, left: 38 },
        { pos: "CM", top: 48, left: 62 },
        { pos: "RM", top: 45, left: 86 },
        { pos: "ST", top: 18, left: 35 },
        { pos: "ST", top: 18, left: 65 }
    ],
    "3-5-2": [
        { pos: "GK", top: 90, left: 50 },
        { pos: "CB", top: 75, left: 25 },
        { pos: "CB", top: 77, left: 50 },
        { pos: "CB", top: 75, left: 75 },
        { pos: "LWB", top: 48, left: 10 },
        { pos: "CM", top: 50, left: 30 },
        { pos: "CAM", top: 42, left: 50 },
        { pos: "CM", top: 50, left: 70 },
        { pos: "RWB", top: 48, left: 90 },
        { pos: "ST", top: 18, left: 35 },
        { pos: "ST", top: 18, left: 65 }
    ],
    "4-2-3-1": [
        { pos: "GK", top: 90, left: 50 },
        { pos: "LB", top: 72, left: 14 },
        { pos: "CB", top: 76, left: 38 },
        { pos: "CB", top: 76, left: 62 },
        { pos: "RB", top: 72, left: 86 },
        { pos: "CDM", top: 56, left: 35 },
        { pos: "CDM", top: 56, left: 65 },
        { pos: "CAM", top: 35, left: 50 },
        { pos: "LM", top: 35, left: 16 },
        { pos: "RM", top: 35, left: 84 },
        { pos: "ST", top: 15, left: 50 }
    ],
    "5-3-2": [
        { pos: "GK", top: 90, left: 50 },
        { pos: "LWB", top: 68, left: 10 },
        { pos: "CB", top: 75, left: 30 },
        { pos: "CB", top: 77, left: 50 },
        { pos: "CB", top: 75, left: 70 },
        { pos: "RWB", top: 68, left: 90 },
        { pos: "CM", top: 45, left: 24 },
        { pos: "CM", top: 48, left: 50 },
        { pos: "CM", top: 45, left: 76 },
        { pos: "ST", top: 18, left: 35 },
        { pos: "ST", top: 18, left: 65 }
    ]
};

let activeSelectedPlayerId = null;
let currentLineup = {};

document.addEventListener("DOMContentLoaded", () => {
    changeFormation();
});

function changeFormation() {
    const formationKey = document.getElementById("formationSelect").value;
    const slots = FORMATIONS[formationKey];
    const pitch = document.getElementById("pitch");

    const existingZones = pitch.querySelectorAll('.drop-zone');
    existingZones.forEach(zone => zone.remove());

    slots.forEach((slot, index) => {
        const zone = document.createElement("div");
        zone.className = "drop-zone";
        zone.style.top = `${slot.top}%`;
        zone.style.left = `${slot.left}%`;
        zone.dataset.slotIndex = index;
        zone.dataset.positionRole = slot.pos;

        zone.ondragover = allowDrop;
        zone.ondragleave = clearDragOver;
        zone.ondrop = (e) => drop(e, index);
        zone.onclick = () => onSlotClick(index);

        pitch.appendChild(zone);

        if (currentLineup[index]) {
            renderPlayerInSlot(index, currentLineup[index]);
        } else {
            renderPlaceholder(zone, slot.pos);
        }
    });
}

function renderPlaceholder(zone, posRole) {
    zone.innerHTML = `<span class="drop-zone-placeholder">${posRole}</span>`;
}

function selectPlayer(playerId) {
    const row = document.getElementById(`player-row-${playerId}`);
    if (!row || row.classList.contains('row-disabled')) return;

    if (activeSelectedPlayerId === playerId) {
        row.classList.remove('selected-row');
        activeSelectedPlayerId = null;
        return;
    }

    document.querySelectorAll('.player-row').forEach(r => r.classList.remove('selected-row'));
    row.classList.add('selected-row');
    activeSelectedPlayerId = playerId;
}

function onSlotClick(slotIndex) {
    if (activeSelectedPlayerId) {
        assignPlayerToSlot(activeSelectedPlayerId, slotIndex);
        if (document.getElementById(`player-row-${activeSelectedPlayerId}`)) {
            document.getElementById(`player-row-${activeSelectedPlayerId}`).classList.remove('selected-row');
        }
        activeSelectedPlayerId = null;
    }
}

function allowDrop(ev) {
    ev.preventDefault();
    ev.currentTarget.classList.add('drag-over');
}

function clearDragOver(ev) {
    ev.currentTarget.classList.remove('drag-over');
}

function drag(ev) {
    const pId = ev.currentTarget.dataset.playerId;
    ev.dataTransfer.setData("playerId", pId);
}

function drop(ev, slotIndex) {
    ev.preventDefault();
    clearDragOver(ev);
    const playerId = ev.dataTransfer.getData("playerId");
    if (playerId) {
        assignPlayerToSlot(playerId, slotIndex);
    }
}

function assignPlayerToSlot(playerId, slotIndex) {
    for (const [sIndex, pId] of Object.entries(currentLineup)) {
        if (pId === playerId) {
            delete currentLineup[sIndex];
            const oldZone = document.querySelector(`.drop-zone[data-slot-index='${sIndex}']`);
            if (oldZone) renderPlaceholder(oldZone, oldZone.dataset.positionRole);
        }
    }

    currentLineup[slotIndex] = playerId;
    renderPlayerInSlot(slotIndex, playerId);
    updateSquadTableStates();
}

function removePlayerFromSlot(slotIndex, event) {
    if (event) event.stopPropagation();
    delete currentLineup[slotIndex];

    const zone = document.querySelector(`.drop-zone[data-slot-index='${slotIndex}']`);
    if (zone) renderPlaceholder(zone, zone.dataset.positionRole);

    updateSquadTableStates();
}

// ITT HOZZUK LÉTRE A KÉRÉSNEK MEGFELELŐ 2 SOROS KÁRTYÁT A PÁLYÁN
function renderPlayerInSlot(slotIndex, playerId) {
    const zone = document.querySelector(`.drop-zone[data-slot-index='${slotIndex}']`);
    const row = document.getElementById(`player-row-${playerId}`);
    if (!zone || !row) return;

    const pData = row.dataset;

    zone.innerHTML = `
        <div class="placed-card">
            <div class="remove-player-btn" onclick="removePlayerFromSlot(${slotIndex}, event)">×</div>

            <!-- 1. SOR: Mezszám, Zászló, Név -->
            <div class="placed-card-row1">
                <span class="shirt-number">#${pData.number}</span>
                <img src="/flag/${pData.flag.toLowerCase()}.png" alt="${pData.flag}" class="flag-icon" title="${pData.flag}">
                <span class="player-name" title="${pData.name}">${pData.name}</span>
            </div>

            <!-- 2. SOR: Attribútumok (Poszt, Overall, BC, SC) -->
            <div class="placed-card-row2">
                <span style="font-weight: bold; color: #003366;">${pData.pos}</span>
                <span class="stat-tag">O: ${pData.overall}</span>
                <span class="stat-tag">BC: ${pData.bc}</span>
                <span class="stat-tag">SC: ${pData.sc}</span>
            </div>
        </div>
    `;
}

function updateSquadTableStates() {
    const assignedIds = Object.values(currentLineup);

    document.querySelectorAll('.player-row').forEach(row => {
        const pId = row.dataset.playerId;
        if (assignedIds.includes(pId)) {
            row.classList.add('assigned-row');
        } else {
            row.classList.remove('assigned-row');
        }
    });
}

function saveLineup() {
    const lineupKeys = Object.keys(currentLineup);
    if (lineupKeys.length < 11) {
        alert(`Megjegyzés: Csak ${lineupKeys.length} játékost választottál ki a 11-ből!`);
    }

    const fixtureId = document.getElementById("fixtureId").value;
    const isHomeTeam = document.getElementById("isHomeTeam").value;
    const teamId = document.getElementById("teamId").value;
    const formationSelect = document.getElementById("formationSelect").value;

    const payload = [];

    for (const [slotIndex, playerId] of Object.entries(currentLineup)) {
        const zone = document.querySelector(`.drop-zone[data-slot-index='${slotIndex}']`);
        const posRole = zone ? zone.dataset.positionRole : "SUB";

        payload.push({
            fixtureId: parseInt(fixtureId),
            teamId: parseInt(teamId),
            playerId: parseInt(playerId),
            position: posRole
        });
    }

    const createLineup = {
        fixtureId: fixtureId,
        isHomeTeam: isHomeTeam,
        formation: formationSelect,
        starterPlayerList: payload
    };

    fetch(SAVE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(createLineup)
    })
    .then(response => {
        if (response.ok) {
            alert("A kezdő 11 sikeresen elmentve!");
            return response.json();
        } else {
            alert("Hiba történt a mentés során!");
        }
    })
    .then(data => {
        window.location.href = data.redirectUrl;
    })
    .catch(error => {
        console.error("Error saving lineup:", error);
        alert("Hálózati hiba történt!");
    });
}