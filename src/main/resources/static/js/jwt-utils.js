const addTokensToServerSession = () => {
    const accessToken = localStorage.getItem('accessToken');

    const refreshToken = getRefreshTokenFromCookie();

    if (!accessToken || !refreshToken) {
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