const cards = document.querySelectorAll('.card');
const container = document.querySelector('.bottom-section');
let map, marker;

const cityElement = document.querySelector('.cityText');
const cityName = cityElement ? cityElement.innerText.trim() : '';

function activateCard(card) {
    if (card.classList.contains('active')) {
        deactivateCards();
    } else {
        cards.forEach(c => c.classList.remove('active'));
        card.classList.add('active');
        container.classList.add('blur');
    }
}

function deactivateCards() {
    cards.forEach(c => c.classList.remove('active'));
    container.classList.remove('blur');
}

cards.forEach(card => {
    card.addEventListener('click', (e) => {
        e.stopPropagation();
        activateCard(card);
    });
});

document.body.addEventListener('click', () => {
    deactivateCards();
});

function initializeMap() {
    map = L.map('map').setView([0, 0], 2);
    L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png', {
        maxZoom: 19,
    }).addTo(map);
}

async function findLocation(cityName) {
    try {
        let response = await fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${cityName}`);
        let data = await response.json();

        if (data.length > 0) {
            let lat = parseFloat(data[0].lat);
            let lng = parseFloat(data[0].lon);
            map.setView([lat, lng], 10);

            if (marker) map.removeLayer(marker);
            marker = L.marker([lat, lng]).addTo(map).bindPopup(data[0].display_name).openPopup();
        }
    } catch (error) {
        console.error("Error fetching location data:", error);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    initializeMap();
    if (cityName.length >= 3) {
        findLocation(cityName);
    }
});
