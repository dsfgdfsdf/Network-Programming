// Automatically hide the message
    window.addEventListener('DOMContentLoaded', (event) => {
    const messageBox = document.getElementById('messageBox');
    if (messageBox && messageBox.textContent.trim() !== "") {
    messageBox.style.display = 'block';
    setTimeout(() => {
    messageBox.style.display = 'none';
}, 5000);
}
});
