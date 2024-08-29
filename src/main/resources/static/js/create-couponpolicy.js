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
                url: '/api/books/search',
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
                url: '/api/categories/search',
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

// 현재 시간을 설정하는 함수
        function setCurrentTime() {
            var now = new Date();

            // 년, 월, 일, 시, 분을 추출
            var year = now.getFullYear();
            var month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
            var day = String(now.getDate()).padStart(2, '0');
            var hour = String(now.getHours()).padStart(2, '0');
            var minute = String(now.getMinutes()).padStart(2, '0');

            // 'YYYY-MM-DDTHH:MM' 형식으로 조합
            var formattedNow = `${year}-${month}-${day}T${hour}:${minute}`;

            // input 필드에 값 설정
            $('#expiredDate').val(formattedNow);
            $('#issueDate').val(formattedNow);
        }

        // 현재 시간을 설정
        setCurrentTime();

        // 모달을 보여줍니다
        $('#addCouponModal').modal('show');

        // 폼 제출 전에 issueDate를 다시 한번 현재 시간으로 설정
        $('#addCouponForm').on('submit', function (e) {
            var now = new Date();
            var year = now.getFullYear();
            var month = String(now.getMonth() + 1).padStart(2, '0');
            var day = String(now.getDate()).padStart(2, '0');
            var hour = String(now.getHours()).padStart(2, '0');
            var minute = String(now.getMinutes()).padStart(2, '0');
            var formattedNow = `${year}-${month}-${day}T${hour}:${minute}`;

            $('#issueDate').val(formattedNow);
        });

        // Close modal on clicking the close button
        $('#addCouponModal .close, #addCouponModal .btn-secondary').on('click', function () {
            $('#addCouponModal').modal('hide');
        });
    });
});


$(document).ready(function () {
    $('#editModal').on('show.bs.modal', function (event) {
        const salePriceInputEdit = $('#salePriceEdit');
        const saleRateInputEdit = $('#saleRateEdit');
        const maxSalePriceInputEdit = $('#maxSalePriceEdit');


        function updateFieldState() {
            if (salePriceInputEdit.val().trim() !== '') {
                saleRateInputEdit.val('').attr('disabled', 'disabled');
                maxSalePriceInputEdit.val('').attr('disabled', 'disabled');
                salePriceInputEdit.removeAttr('disabled');
            } else if (saleRateInputEdit.val().trim() !== '' && maxSalePriceInputEdit.val().trim() !== '') {
                salePriceInputEdit.val('').attr('disabled', 'disabled');
                saleRateInputEdit.removeAttr('disabled');
                maxSalePriceInputEdit.removeAttr('disabled');
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
        $('#minOrderPriceEdit').val(row.find('.minOrderPriceHidden').val());
        $('#salePriceEdit').val(row.find('.salePriceHidden').val());
        $('#saleRateEdit').val(row.find('.saleRateHidden').val());
        $('#maxSalePriceEdit').val(row.find('.maxSalePriceHidden').val());
        $('#typeEdit').val(row.find('.typeHidden').val());

        var isUsedValue = row.find('.isUsedHidden').val();
        // Set the radio button based on the value
        if (isUsedValue === "true") {
            $('#isUsedTrue').prop('checked', true);
        } else if (isUsedValue === "false") {
            $('#isUsedFalse').prop('checked', true);
        }
        $('#categoryNameEdit').val(row.find('.categoryNameHidden').val());
        $('#bookTitleEdit').val(row.find('.bookTitleHidden').val());


        // `th:action` URL 업데이트
        $('#editCouponForm').attr('action', '/coupons/policies/' + policyId);

        $('#editModal').modal('show'); // 수정 모달 표시


        // Close modal on clicking the close button
        $('#editModal .close, #editModal .btn-secondary').on('click', function () {
            $('#editModal').modal('hide');
        });

    });
});
