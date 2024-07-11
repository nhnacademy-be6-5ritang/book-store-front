
// rate , price 둘중에 한개만 적을 수 있게금 조치
document.addEventListener('DOMContentLoaded', function () {
    const salePriceInput = document.getElementById('salePrice');
    const saleRateInput = document.getElementById('saleRate');
    const maxSalePriceInput = document.getElementById('maxSalePrice');

    salePriceInput.addEventListener('input', function () {
        if (salePriceInput.value.trim() !== '') {
            saleRateInput.value = '';
            maxSalePriceInput.value = '';
            saleRateInput.setAttribute('disabled', 'disabled');
            maxSalePriceInput.setAttribute('disabled', 'disabled');
        } else {
            saleRateInput.removeAttribute('disabled');
            maxSalePriceInput.removeAttribute('disabled');
        }
    });

    saleRateInput.addEventListener('input', function () {
        if (saleRateInput.value.trim() !== '' || maxSalePriceInput.value.trim() !== '') {
            salePriceInput.value = '';
            salePriceInput.setAttribute('disabled', 'disabled');
        } else {
            salePriceInput.removeAttribute('disabled');
        }
    });

    maxSalePriceInput.addEventListener('input', function () {
        if (saleRateInput.value.trim() !== '' || maxSalePriceInput.value.trim() !== '') {
            salePriceInput.value = '';
            salePriceInput.setAttribute('disabled', 'disabled');
        } else {
            salePriceInput.removeAttribute('disabled');
        }
    });
});

// Function to show additional fields based on selected type
function showFields(selectedType) {
    $('#bookFields').hide();
    $('#categoryFields').hide();

    if (selectedType === 'book') {
        $('#bookFields').show();
    } else if (selectedType === 'category') {
        $('#categoryFields').show();
    }
}

// Function to show additional fields based on selected type
function showFields(selectedType) {
    $('#bookFields').hide();
    $('#categoryFields').hide();

    if (selectedType === 'book') {
        $('#bookFields').show();
    } else if (selectedType === 'category') {
        $('#categoryFields').show();
    }
}

$(document).ready(function () {
    // 도서 검색 기능
    $('#bookSearch').on('input', function () {
        var query = $(this).val();
        if (query.length > 1) { // 3자 이상 입력 시 검색
            $.ajax({
                url: '/api/books/search/test',
                type: 'GET',
                data: {key: query},
                success: function (data) {
                    $('#bookSearchResults').html('');
                    data.forEach(function (book) {
                        $('#bookSearchResults').append('<div class="search-result book-result" data-id="' + book.bookId + '" data-title="' + book.bookTitle + '">' + book.bookTitle + '</div>');
                    });
                    // 결과 클릭 시 처리
                    $('.book-result').on('click', function () {
                        var bookId = $(this).data('id');
                        var bookTitle = $(this).data('title');
                        $('#bookId').val(bookId);
                        $('#bookTitle').val(bookTitle);
                        $('#bookSearch').val(bookTitle);
                        $('#bookSearchResults').html('');
                    });
                }
            });
        } else {
            $('#bookSearchResults').html('');
        }
    });

    // 카테고리 검색 기능
    $('#categorySearch').on('input', function () {
        var query = $(this).val();
        if (query.length > 1) { // 3자 이상 입력 시 검색
            $.ajax({
                url: '/api/categories/search/test',
                type: 'GET',
                data: {key: query},
                success: function (data) {
                    $('#categorySearchResults').html('');
                    data.forEach(function (category) {
                        $('#categorySearchResults').append('<div class="search-result category-result" data-id="' + category.categoryId + '" data-name="' + category.categoryName + '">' + category.categoryName + '</div>');
                    });
                    // 결과 클릭 시 처리
                    $('.category-result').on('click', function () {
                        var categoryId = $(this).data('id');
                        var categoryName = $(this).data('name');
                        $('#categoryId').val(categoryId);
                        $('#categoryName').val(categoryName);
                        $('#categorySearch').val(categoryName);
                        $('#categorySearchResults').html('');
                    });
                }
            });
        } else {
            $('#categorySearchResults').html('');
        }
    });
});

$(document).ready(function () {
    $('#couponTable').on('click', '.addBtn', function (e) {
        e.preventDefault();

        var policyId = $(this).data('policy-id');
        $('#couponPolicyId').val(policyId);
        $('#expiredDate').val(formatDateTime(new Date())); // 현재 날짜와 시간을 적절한 형식으로 설정
        $('#issueDate').val(formatDateTime(new Date())); // 현재 날짜와 시간을 적절한 형식으로 설정

        $('#addCouponModal').modal('show');
    });
});


// 정책 수정시 정률쿠폰 정액쿠폰 동시에 input 활성화 방지
$(document).ready(function () {
    $('#editModal').on('show.bs.modal', function (event) {
        const salePriceInputEdit = $('#salePriceEdit');
        const saleRateInputEdit = $('#saleRateEdit');
        const maxSalePriceInputEdit = $('#maxSalePriceEdit');

        function updateFieldState() {
            if (salePriceInputEdit.val().trim() !== '') {
                saleRateInputEdit.val('').attr('disabled', 'disabled');
                maxSalePriceInputEdit.val('').attr('disabled', 'disabled');
            } else {
                saleRateInputEdit.removeAttr('disabled');
                maxSalePriceInputEdit.removeAttr('disabled');
            }
            if (saleRateInputEdit.val().trim() !== '' || maxSalePriceInputEdit.val().trim() !== '') {
                salePriceInputEdit.val('').attr('disabled', 'disabled');
            } else {
                salePriceInputEdit.removeAttr('disabled');
            }
        }

        salePriceInputEdit.on('input', updateFieldState);
        saleRateInputEdit.on('input', updateFieldState);
        maxSalePriceInputEdit.on('input', updateFieldState);

        updateFieldState();
    });



    $('#couponTable').on('click', '.editBtn', function (e) {
        e.preventDefault();
        var policyId = $(this).data('policy-id');

        var isUsed = $(this).data('is-used');

        if (!isUsed) {
            alert('폐기된 정책은 다시 사용할 수 없습니다.');
            return; // 경고창을 띄운 후 함수 종료
        }



        var row = $(this).closest('tr');


        // 폼 데이터 채우기
        $('#policyIdEdit').val(row.find('.couponPolicyId').val());
        $('#minOrderPriceEdit').val(row.find('.minOrderPrice').val());
        $('#salePriceEdit').val(row.find('.salePrice').val());
        $('#saleRateEdit').val(row.find('.saleRate').val());
        $('#maxSalePriceEdit').val(row.find('.maxSalePrice').val());
        $('#typeEdit').val(row.find('.type').val());

        var isUsedValue = row.find('.isUsed').val();
        // Set the radio button based on the value
        if (isUsedValue === "true") {
            $('#isUsedTrue').prop('checked', true);
        } else if (isUsedValue === "false") {
            $('#isUsedFalse').prop('checked', true);
        }
        $('#categoryNameEdit').val(row.find('.categoryName').val());
        $('#bookTitleEdit').val(row.find('.bookTitle').val());

        // `th:action` URL 업데이트
        $('#editCouponForm').attr('action', '/coupons/policies/' + policyId);

        $('#editModal').modal('show'); // 수정 모달 표시



        // Close modal on clicking the close button
        $('#editModal .close, #editModal .btn-secondary').on('click', function() {
            $('#editModal').modal('hide');
        });

    });
});
