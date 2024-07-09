$(document).ready(function () {
    $('.add-to-bookCart').on('click', function (event) {
        event.preventDefault();

        const bookId = $(this).data('book-id');
        const requestData = {
            bookId: bookId,
            bookQuantity: 1
        };

        $.ajax({
            url: '/api/carts/me',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function (response, textStatus, xhr) {
                if (xhr.status === 201) {
                    alert('장바구니에 추가 되었습니다.');
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status === 409) {
                    alert('이미 장바구니에 존재하는 책입니다.');
                } else {
                    console.error('Error:', error);
                    alert('장바구니 추가 실패');
                }
            }
        });
    });
});