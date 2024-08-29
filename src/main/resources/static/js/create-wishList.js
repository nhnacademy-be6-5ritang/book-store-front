$(document).ready(function () {
    $('.add-to-wishlist').on('click', function (event) {
        event.preventDefault();

        const bookId = $(this).data('book-id');
        const requestData = {
            bookId: bookId
        };

        // 로그인 상태 확인
        $.ajax({
            url: '/auth/has-tokens',
            type: 'GET',
            success: function (isLoggedIn) {
                if (isLoggedIn) {
                    // 로그인 상태일 경우 위시리스트 추가 요청
                    addToWishlist(requestData);
                } else {
                    // 비로그인 상태일 경우 로그인 페이지로 리다이렉트
                    window.location.href = 'https://www.5ritang.store/auth/login';
                }
            },
            error: function (xhr, status, error) {
                console.error('Error checking login status:', error);
                alert('로그인 상태 확인 중 오류가 발생했습니다.');
            }
        });
    });
});

function addToWishlist(requestData) {
    $.ajax({
        url: '/wish-lists',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(requestData),
        success: function (response, textStatus, xhr) {
            if (xhr.status === 201) {
                alert('위시리스트에 추가 되었습니다.');
            }
        },
        error: function (xhr, status, error) {
            if (xhr.status === 404) {
                alert('로그인 후 위시리스트를 이용해 주세요');
            } else if (xhr.status === 409) {
                alert('이미 위시리스트에 존재하는 책입니다.');
            } else {
                console.error('Error:', error);
                alert('위시리스트 추가 실패');
            }
        }
    });
}