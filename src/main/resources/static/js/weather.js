const cards = document.querySelectorAll('.card');
const container = document.querySelector('.container');
let map, marker, searchTimeout;
let cityName= city1;

// Function to activate card and show details
function activateCard(card) {
    if (card.classList.contains('active')) {
        deactivateCards();
    } else {
        cards.forEach(c => {
            c.classList.remove('active');
            let details = c.querySelector(".card-details");
            if (details) details.style.display = "none"; // Hide details when another card is clicked
        });

        card.classList.add('active');
        container.classList.add('blur');

        let details = card.querySelector(".card-details");
        if (details) details.style.display = "block"; // Show details for active card
    }
}

// Function to reset cards and hide details
function deactivateCards() {
    cards.forEach(c => {
        c.classList.remove('active');
        let details = c.querySelector(".card-details");
        if (details) details.style.display = "none"; // Hide details when shrinking
    });

    container.classList.remove('blur');
}

// Event listeners for cards
cards.forEach(card => {
    card.addEventListener('click', (e) => {
        e.stopPropagation();
        activateCard(card);
    });
});

// Shrink on outside click
document.body.addEventListener('click', () => {
    deactivateCards();
});

// Initialize Leaflet map
function initializeMap() {
    map = L.map('map').setView([20, 0], 2);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);
}

document.addEventListener("DOMContentLoaded", initializeMap);

// Location search
//console.log(cityName)
document.addEventListener("DOMContentLoaded", () => {
    if (cityName && cityName.trim().length >= 3) {
        findLocation(cityName.trim());
    }
});

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

// Hide card details by default
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".card-details").forEach(detail => {
        detail.style.display = "none";
    });
});
