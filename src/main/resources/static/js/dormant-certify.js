const sendDormantEmail = async () => {
    const email = document.getElementById("email").value;
    try {
        const response = await fetch(
            `/users/send-email/dormant-to-active?email=${encodeURI(email)}`,
            {method: 'POST'}
        );
        if (response.ok) {
            alert(`${email}로 인증 번호를 전송했습니다.`);
        } else {
            console.error('휴면 계정 해제 인증번호 전송 응답 오류:', response.status, response.statusText);
            alert('휴면 계정 해제 인증번호 전송 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('휴면 계정 해제 인증번호 전송 중 오류 발생', error);
        alert('휴면 계정 해제 인증번호 전송; 서버 통신 중 오류 발생');
    }
}

const certifyDormantCode = async () => {
    const email = document.getElementById("email").value;
    const certifyCode = document.getElementById('certify-code').value;
    try {
        const response = await fetch(`/users/check-email/dormant-to-active?email=${encodeURI(email)}&certifyCode=${certifyCode}`);
        if (response.ok) {
            alert('인증되었습니다.');
            window.location.href = '/';
        } else if (response.status === 401) {
            alert('인증번호가 일치하지 않습니다.');
        } else {
            console.error('휴면 계정 해제 인증번호 인증 응답 오류:', response.status, response.statusText);
            alert('휴면 계정 해제 인증번호 인증 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('휴면 계정 해제 인증번호 인증 중 오류 발생', error);
        alert('휴면 계정 해제 인증번호 인증; 서버 통신 중 오류 발생');
    }
}