window.onload = () => {
    const params = new URLSearchParams(window.location.search);
    if (params.has('error')) {
        alert(params.get('error'));
    }
}

document.getElementById('sign-up-form').addEventListener('submit', (event) => {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('password-confirm').value;

    if (password !== confirmPassword) {
        alert('비밀번호가 일치하지 않습니다.');
        event.preventDefault();
    }
});

const sendEmail = async () => {
    const email = document.getElementById('email').value
    try {
        const response = await fetch(
            `/auth/send-email/sign-up?email=${encodeURI(email)}`,
            {method: 'POST'}
        );
        if (response.ok) {
            alert(`${email}로 인증 번호를 전송했습니다.`);
            showInputSignUpCode();
        } else if (response.status === 409) {
            alert('이미 사용 중인 이메일입니다.');
            document.getElementById('email').value = '';
        } else {
            console.error('회원가입 인증번호 전송 응답 오류:', response.status, response.statusText);
            alert('회원가입 인증번호 전송 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('회원가입 인증번호 전송 중 오류 발생', error);
        alert('회원가입 인증번호 전송; 서버 통신 중 오류 발생');
    }
}

const showInputSignUpCode = () => {
    const emailInput = document.getElementById('email');
    emailInput.disabled = true;
    const codeInputRow = document.getElementById('verify-code-row');
    codeInputRow.style.display = '';
}

const certifySignUpCode = async () => {
    const email = document.getElementById('email').value;
    const codeInput = document.getElementById('certify-code');
    const code = codeInput.value;

    try {
        const response = await fetch(`/auth/check-email/sign-up?email=${encodeURI(email)}&certifyCode=${code}`);
        if (response.ok) {
            alert('인증되었습니다.');
            codeInput.disabled = true;
        } else if (response.status === 401) {
            alert('인증번호가 일치하지 않습니다.');
        } else {
            console.error('회원가입 인증번호 인증 응답 오류:', response.status, response.statusText);
            alert('회원가입 인증번호 인증 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('회원가입 인증번호 인증 중 오류 발생', error);
        alert('회원가입 인증번호 인증; 서버 통신 중 오류 발생');
    }
}