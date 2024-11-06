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
    document.getElementById("registerForm").reset();
});

// Login and store token
document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("loginEmail").value;
    console.log("Email:", email);
    const password = document.getElementById("loginPassword").value;
    console.log("Password:", password);
    const response = await fetch(`${BASE_URL}/users/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email, password: password })
    });
    const data = await response.json();
    console.log("Data:", data);
    localStorage.setItem("token", data.token);
    document.getElementById("loginForm").reset();
});

// Create a new post
// Create a new post
document.getElementById("postForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const content = document.getElementById("postContent").value;
    const token = localStorage.getItem("token");

    await fetch(`${BASE_URL}/posts?userId=1`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content })
    });
    loadStream();
    document.getElementById("postForm").reset();
});


async function loadStream() {
    try {
        const response = await fetch(BASE_URL + "/stream");
        const posts = await response.json();

        const stream = document.getElementById("stream");
        stream.innerHTML = posts
            .map(post => `<p>${post.content} - by ${post.author.name}</p>`)
            .join('');
    } catch (error) {
        console.error("Error loading stream:", error);
    }
}

// Load stream on page load
loadStream();

