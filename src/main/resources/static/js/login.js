document.addEventListener('DOMContentLoaded', function () {
    let profileContainer = document.querySelector('.profile-container');
    if (localStorage.getItem('accessToken')) {
        profileContainer.innerHTML = '<button class="logout-button" id="logout">로그아웃</button>';
    } else {
        profileContainer.innerHTML = '<div>' +
            '<button class="header-login-button" id="login">로그인</button>' +
            '<span class="divider">&nbsp&nbsp|&nbsp&nbsp</span>' +
            '<button class="header-sign-up-button" id="sign-up">회원가입</button>' +
            '</div>';
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
            console.log("then() 시작")
            if (response.ok) {
                console.log("if() 시작")
                localStorage.removeItem('accessToken');
                console.log("removeItem 끝")
                window.location.href = '/';
            }
        }).catch(error => {
            console.error(error);
        });
    });

    document.getElementById('login')?.addEventListener('click', event => {
        event.preventDefault();
        window.location.href = '/auth/login';
    });

    document.getElementById('sign-up')?.addEventListener('click', event => {
        event.preventDefault();
        window.location.href = '/auth/sign-up';
    })
});