function submitReviewForm() {
    var selectedBookId = document.getElementById('bookId').value;
    if (selectedBookId) {
        var url = '/api/reviews/create/' + selectedBookId;
        window.location.href = url;
    } else {
        alert('도서를 선택해주세요.');
    }
}