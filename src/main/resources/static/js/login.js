document.addEventListener('DOMContentLoaded', async () => {
    let profileContainer = document.querySelector('.profile-container');

    const hasTokens = await checkTokens();

    if (hasTokens) {
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

        console.log("로그아웃 버튼 클릭");

        fetch('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        }).then(response => {
            if (response.ok) {
                window.location.href = '/';
            }
        }).catch(error => {
            console.error('로그아웃 중 오류 발생: ', error);
        });
    });

    document.getElementById('login')?.addEventListener('click', event => {
        event.preventDefault();
        window.location.href = '/auth/login';
    });

    document.getElementById('sign-up')?.addEventListener('click', event => {
        event.preventDefault();
        window.location.href = '/auth/sign-up';
    });
});

const checkTokens = async () => {
    try {
        const response = await fetch('/auth/has-tokens', {
            method: 'GET'
        });

        if (response.ok) {
            return await response.json();
        } else {
            console.error('토큰 여부 확인 중 오류 발생');
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }
}