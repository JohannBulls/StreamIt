const BASE_URL = 'http://localhost:8080';

// Register a new user
document.getElementById("registerForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const name = document.getElementById("registerName").value;
    const email = document.getElementById("registerEmail").value;
    const password = document.getElementById("registerPassword").value;

    await fetch(`${BASE_URL}/users`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password })
    });
});

// Login and store token
document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;
    const response = await fetch(`${BASE_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });
    const data = await response.json();
    localStorage.setItem("token", data.token);
});

// Create a new post
document.getElementById("postForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const content = document.getElementById("postContent").value;
    const token = localStorage.getItem("token");

    await fetch(`${BASE_URL}/posts?userId=1`, { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ content })
    });
    loadStream();
});

// Load posts stream
async function loadStream() {
    const response = await fetch(`${BASE_URL}/stream`, {
        headers: { 'Authorization': `Bearer ${localStorage.getItem("token")}` }
    });
    const posts = await response.json();

    const stream = document.getElementById("stream");
    stream.innerHTML = posts.map(post => `<p>${post.content} - by ${post.author.name}</p>`).join('');
}

// Load stream on page load
loadStream();
