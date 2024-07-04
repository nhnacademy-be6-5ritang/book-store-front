document.addEventListener('DOMContentLoaded', function () {
    let profileContainer = document.querySelector('.profile-container');
    if (localStorage.getItem('accessToken')) {
        profileContainer.innerHTML = '<button class="logout-button" id="logout">로그아웃</button>';
    } else {
        profileContainer.innerHTML = '<button class="login-button" id="login">로그인</button>';
    }

    document.getElementById('logout')?.addEventListener('click', event => {
        event.preventDefault();

        fetch('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        }).then(response => {
            if (response.redirected) {
                localStorage.removeItem('accessToken');
                window.location.href = response.url;
            }
        }).catch(error => {
            console.error(error);
        });
    });

    document.getElementById('login')?.addEventListener('click', function (event) {
        event.preventDefault();
        window.location.href = '/auth/login';
    });
});