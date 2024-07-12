document.getElementById('withdraw-button').addEventListener('click', async (event) => {
    event.preventDefault();

    fetch('/users/withdraw', {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include'
    }).then(response => {
        if (response.ok) {
            alert('탈퇴되었습니다.');
            localStorage.removeItem('accessToken');
            window.location.href = '/';
        } else {
            console.error('탈퇴 응답 오류: ', response.status, response.statusText);
            alert('탈퇴 중 오류가 발생했습니다.');
        }
    }).catch(error => {
        console.error('탈퇴 요청 중 오류 발생', error);
        alert('탈퇴 중 서버와 통신하는 데 오류가 발생했습니다.');
    });
});

const displayUserGrades = () => {
    const userGradeData = document.getElementById('user-grades-data');
    userGradeData.classList.add('user-grades-on-display');
    userGradeData.classList.remove('user-grades-hidden');
}

const hideUserGrades = () => {
    const userGradeData = document.getElementById('user-grades-data');
    userGradeData.classList.add('user-grades-hidden');
    userGradeData.classList.remove('user-grades-on-display');
}