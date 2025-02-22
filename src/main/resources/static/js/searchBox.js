document.addEventListener("DOMContentLoaded", function () {
            let data = [];
            let selectedIndex = -1;
            const searchBox = document.getElementById("searchBox");
            const suggestionsBox = document.getElementById("suggestions");

        // Fetch and load data
        fetch('/textFile/Weather-City.txt')
            .then(response => response.text())
            .then(text => {
                data = text.split('\n').map(line => line.trim()).filter(line => line);
            })
            .catch(error => console.error("Error loading suggestions:", error));

        // Debounce function to limit function calls
        function debounce(func, delay) {
            let timer;
            return function (...args) {
                clearTimeout(timer);
                timer = setTimeout(() => func.apply(this, args), delay);
            };
        }

        // Show suggestions
        function showSuggestions() {
            const input = searchBox.value.trim().toLowerCase();
            suggestionsBox.innerHTML = '';
            selectedIndex = -1;

              if (!input) {
                  suggestionsBox.style.display = "none";
                  return;
              }

              const filtered = data.filter(item => item.toLowerCase().startsWith(input)).slice(0, 10);

              if (filtered.length === 0) {
                  suggestionsBox.style.display = "none";
                  return;
              }

              filtered.forEach((suggestion, index) => {

                  const div = document.createElement("div");
                  div.textContent = suggestion;
                  div.onclick = () => selectSuggestion(suggestion);
                  div.setAttribute("data-index", index);
                  suggestionsBox.appendChild(div);
              });

              suggestionsBox.style.display = "block";
          }

        // Select suggestion from list
        function selectSuggestion(value) {
            searchBox.value = value;
            suggestionsBox.style.display = "none";
        }

        // Handle keyboard navigation for suggestions
        function handleKeyUp(event) {
            const suggestions = suggestionsBox.getElementsByTagName("div");

                if (event.key === "ArrowDown" && suggestions.length > 0) {
                    selectedIndex = (selectedIndex + 1) % suggestions.length;
                } else if (event.key === "ArrowUp" && suggestions.length > 0) {
                    selectedIndex = (selectedIndex - 1 + suggestions.length) % suggestions.length;
                } else if (event.key === "Enter" && selectedIndex >= 0) {
                    selectSuggestion(suggestions[selectedIndex].textContent);
                    return;
                } else {
                    debouncedShowSuggestions();
                    return;
                }

              for (let i = 0; i < suggestions.length; i++) {
                  suggestions[i].classList.remove("active");
              }
              if (selectedIndex >= 0) {
                  suggestions[selectedIndex].classList.add("active");

              }
          }

        // Debounced version of showSuggestions
        const debouncedShowSuggestions = debounce(showSuggestions, 100);

            // Attach event listeners
            searchBox.addEventListener("keyup", handleKeyUp);
            searchBox.addEventListener("input", debouncedShowSuggestions);

        // Hide suggestions when clicking outside
        document.addEventListener("click", function (event) {
            if (!event.target.closest(".form-group")) {
                suggestionsBox.style.display = "none";
            }
 });
 });