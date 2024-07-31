const paycoLoginRequest = async () => {
    const clientId = '3RD5T_6SsRZZqiV8oXgma4_';
    const redirectUri = 'http://local.host.com:8081/auth/payco/callback'; //TODO 컨트롤러에서 받도록 수정
    // const clientId = '3RD3uCdBFL1ANlmFIuoqa5l';
    // const redirectUri = 'https://5ritang.store/auth/payco/callback';

    const paycoAuthUrl = `https://id.payco.com/oauth2.0/authorize?`
        + `response_type=code`
        + `&client_id=${clientId}`
        + `&redirect_uri=${encodeURI(redirectUri)}`
        + `&serviceProviderCode=FRIENDS`
        + `&userLocale=ko_KR`;

    window.location.href = paycoAuthUrl;
}

const paycoConnectRequest = async => {
    const clientId = '3RD5T_6SsRZZqiV8oXgma4_';
    const redirectUri = 'http://local.host.com:8081/auth/payco/connect'; //TODO 컨트롤러에서 받도록 수정
    // const clientId = '3RD3uCdBFL1ANlmFIuoqa5l';
    // const redirectUri = 'https://5ritang.store/auth/payco/connect';

    const paycoAuthUrl = `https://id.payco.com/oauth2.0/authorize?`
        + `response_type=code`
        + `&client_id=${clientId}`
        + `&redirect_uri=${encodeURI(redirectUri)}`
        + `&serviceProviderCode=FRIENDS`
        + `&userLocale=ko_KR`;

    window.location.href = paycoAuthUrl;
}