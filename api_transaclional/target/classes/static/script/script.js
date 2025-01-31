// Функція авторизації
function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (!username || !password) {
        document.getElementById("error-message").innerText = "Введіть логін та пароль!";
        return;
    }

    sessionStorage.setItem("username", username);
    sessionStorage.setItem("password", password);

    window.location.href = "transactions.html";
}

// Завантаження транзакцій
function loadTransactions() {
    const username = sessionStorage.getItem("username");
    const password = sessionStorage.getItem("password");

    if (!username || !password) {
        window.location.href = "index.html";
        return;
    }

    const startDate = document.getElementById("startDate").value || "2024-01-01";
    const endDate = document.getElementById("endDate").value || "2024-12-31";
    const type = document.getElementById("type").value;

    const url = `/api/transactions?username=${username}&password=${password}&startDate=${startDate}T00:00:00.000&endDate=${endDate}T23:59:59.999&type=${type}`;



    fetch(url)
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById("transactions-list");
            list.innerHTML = "";
            data.forEach(t => {
                const li = document.createElement("li");
                li.textContent = `${t.type} - ${t.amount} грн - ${t.date}`;
                list.appendChild(li);
            });
        })
        .catch(() => {
            alert("Помилка при завантаженні транзакцій!");
        });
}

// Виконати при завантаженні transactions.html
if (window.location.pathname.includes("transactions.html")) {
    document.addEventListener("DOMContentLoaded", loadTransactions);
    function showPopup(message) {
        let popup = document.createElement("div");
        popup.className = "popup";
        popup.innerText = message;
        document.body.appendChild(popup);

        popup.style.display = "block";

        setTimeout(() => {
            popup.style.opacity = "0";
            setTimeout(() => popup.remove(), 500);
        }, 3000);
    }

// Функція авторизації
    function login() {
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        if (!username || !password) {
            showPopup("⚠ Введіть логін та пароль!");
            return;
        }

        sessionStorage.setItem("username", username);
        sessionStorage.setItem("password", password);

        window.location.href = "transactions.html";
    }

}
