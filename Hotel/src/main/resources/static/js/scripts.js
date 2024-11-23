document.getElementById('loadJsonButton').addEventListener('click', () => {
    fetch('/rooms', {
        headers: {
            'Accept': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const jsonResult = document.getElementById('jsonResult');
            const jsonData = document.getElementById('jsonData');

            // Display JSON in format
            jsonData.textContent = JSON.stringify(data, null, 4);
            jsonResult.style.display = 'block';
        })
        .catch(error => {
            alert('Failed to load JSON: ' + error.message);
        });
});
