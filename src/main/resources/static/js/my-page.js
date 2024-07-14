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

document.getElementById('dormant-to-active-button').addEventListener('click', async function () {
    let email = this.getAttribute('data-email');
    await sendEmail(email);
});

const sendEmail = async (email) => {
    console.log(email);
    try {
        const response = await fetch(
            `/users/send-email/dormant-to-active?email=${encodeURI(email)}`,
            {method: 'POST'}
        );
        if (response.ok) {
            alert(`${email}로 인증 번호를 전송했습니다.`);
            showInputCertifyCode();
        } else {
            console.error('휴면 해제 인증번호 전송 응답 오류:', response.status, response.statusText);
            alert('휴면 해제 인증 번호 전송 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('휴면 해제 인증번호 전송 중 오류 발생', error);
        alert('휴면 해제 인증번호 전송; 서버 통신 중 오류 발생');
    }
}

const showInputCertifyCode = () => {
    document.getElementById('dormant-to-active-button').style.display = 'none';
    document.getElementById('code-input-container').style.display = '';
}

document.getElementById('certify-code-button').addEventListener('click', async function () {
    const email = document.getElementById('dormant-to-active-button').getAttribute('data-email');
    await certifyDormantToActive(email);
});

const certifyDormantToActive = async (email) => {
    const codeInput = document.getElementById('certify-code');
    const code = codeInput.value;

    try {
        const response = await fetch(`/users/check-email/dormant-to-active?email=${encodeURI(email)}&certifyCode=${code}`);
        if (response.ok) {
            alert('인증되었습니다.');
            location.reload();
        } else if (response.status === 401) {
            alert('인증번호가 일치하지 않습니다.');
        } else {
            console.error('휴면 해제 인증 응답 오류:', response.status, response.statusText);
            alert('휴면 해제 인증 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('휴면 해제 인증 중 오류 발생', error);
        alert('휴면 해제 인증; 서버 통신 중 오류 발생');
    }
}