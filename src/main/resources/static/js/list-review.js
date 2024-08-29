function submitReviewForm() {
    var selectedOrderListId = document.getElementById('orderListId').value;
    if (selectedOrderListId) {
        var url = '/reviews/create/' + selectedOrderListId;
        window.location.href = url;
    } else {
        alert('도서를 선택해주세요.');
    }
}