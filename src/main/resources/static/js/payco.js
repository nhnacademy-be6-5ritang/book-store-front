const paycoLoginRequest = async () => {
    const clientId = '3RD5T_6SsRZZqiV8oXgma4_';
    const redirectUri = 'http://local.host.com:8081/auth/payco/callback'; //TODO 컨트롤러에서 받도록 수정

    const paycoAuthUrl = `https://id.payco.com/oauth2.0/authorize?`
        + `response_type=code`
        + `&client_id=${clientId}`
        + `&redirect_uri=${encodeURI(redirectUri)}`
        + `&serviceProviderCode=FRIENDS`
        + `&userLocale=ko_KR`;

    window.location.href = paycoAuthUrl;
}