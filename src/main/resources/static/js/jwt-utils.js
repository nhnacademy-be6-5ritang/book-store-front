const addTokensToServerSession = () => {
    const accessToken = localStorage.getItem('accessToken');
    console.log('accessToken: ', accessToken);

    const refreshToken = getRefreshTokenFromCookie();
    console.log('refreshToken: ', refreshToken);

    if (!accessToken || !refreshToken) {
        console.error('accessToken 또는 refreshToken이 없습니다.');
        return null;
    }
    
    fetch('/auth/set-tokens', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `accessToken=${encodeURIComponent(accessToken)}&refreshToken=${encodeURIComponent(refreshToken)}`
    })
        .then(response => {
            if (response.ok) {
                console.log('Tokens stored successfully');
                // 필요한 경우, 추가 작업 수행
            } else {
                console.error('Error storing tokens');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const getRefreshTokenFromCookie = () => {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith('Refresh-Token=')) {
            return cookie.substring('Refresh-Token='.length);
        }
    }

    return null;
}

document.addEventListener('DOMContentLoaded', addTokensToServerSession);