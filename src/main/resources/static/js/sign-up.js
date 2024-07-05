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

const checkEmailExistence = async () => {
    const email = document.getElementById('email').value
    console.log(email);
    const response = await fetch(`/auth/check-email?email=${encodeURI(email)}`);
    if (response.ok) {
        const emailExists = await response.json();
        if (emailExists) {
            alert('이미 사용 중인 이메일입니다.');
            document.getElementById('email').value = '';
        } else {
            alert(`${email}로 인증 번호를 전송했습니다.`);
        }
    }
}