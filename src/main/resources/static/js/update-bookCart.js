document.addEventListener('DOMContentLoaded', function () {
    const quantityInputs = document.querySelectorAll('.bookCart_quantity input[type="number"]');
    const changeButtons = document.querySelectorAll('.bookCart_change');
    const decrementButtons = document.querySelectorAll('.bookCart_decrement');
    const incrementButtons = document.querySelectorAll('.bookCart_increment');
    const totalPriceElement = document.querySelector('.bookCart_summary p');
    const changeForms = document.querySelectorAll('.bookCart_change form');

    // 초기화: 페이지가 로드될 때 총 금액 계산
    calculateTotalPrice();

    // 변경 버튼 클릭 시 해당 상품 금액 및 총 상품 금액 계산 및 업데이트
    changeButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const input = this.closest('.bookCart_item').querySelector('.bookCart_quantity input[type="number"]');
            const maxQuantity = parseInt(input.closest('.bookCart_item').querySelector('.bookCart_inventoryQuantity').innerText.replace('재고: ', ''));
            let quantity = parseInt(input.value);

            // 수량 검증
            if (quantity < 1) {
                alert('1권 이상의 수량을 입력해야 합니다.');
                return;
            }

            if (quantity > maxQuantity) {
                alert('재고보다 많은 수량을 입력할 수 없습니다.');
                return;
            }

            // PUT 요청 보내기
            const bookCartId = input.getAttribute('data-bookCart-id');
            const requestData = {
                bookQuantity: quantity
            };

            fetch('/api/carts/me/' + bookCartId, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            })
                .then(response => {
                    if (response.ok) {
                        alert('수량이 업데이트되었습니다.');
                        updateItemTotal(input); // 해당 상품 금액 업데이트
                        calculateTotalPrice(); // 전체 총 금액 계산 및 업데이트
                    } else {
                        alert('수량 업데이트 실패');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('수량 업데이트 중 문제가 발생했습니다.');
                });

        });
    });

    // 수량 감소 버튼 클릭 시 수량 감소 (최소 1 이상)
    decrementButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            const input = this.closest('.bookCart_quantity').querySelector('input[type="number"]');
            if (input.value > 1) {
                input.value = parseInt(input.value) - 1;
            }
        });
    });

    // 수량 증가 버튼 클릭 시 수량 증가
    incrementButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            const input = this.closest('.bookCart_quantity').querySelector('input[type="number"]');
            const maxQuantity = parseInt(input.closest('.bookCart_item').querySelector('.bookCart_inventoryQuantity').innerText.replace('재고: ', ''));
            if (input.value < maxQuantity) {
                input.value = parseInt(input.value) + 1;
            }
        });
    });

    function updateItemTotal(input) {
        const quantity = parseInt(input.value);
        if (quantity < 1) {
            input.value = 1; // 최소값 1로 설정
        }
        const pricePerItem = parseInt(input.closest('.bookCart_item').querySelector('.bookCart_price span').innerText.replace('원', '').replace(',', ''));
        const itemTotal = quantity * pricePerItem;
        input.closest('.bookCart_item').querySelector('.bookCart_total span').innerText = itemTotal.toLocaleString() + '원';
    }

    function calculateTotalPrice() {
        let totalPrice = 0;
        changeButtons.forEach(button => {
            const input = button.closest('.bookCart_item').querySelector('.bookCart_quantity input[type="number"]');
            const quantity = parseInt(input.value);
            const pricePerItem = parseInt(input.closest('.bookCart_item').querySelector('.bookCart_price span').innerText.replace('원', '').replace(',', ''));
            totalPrice += quantity * pricePerItem;
        });
        totalPriceElement.innerText = '상품 총 금액: ' + totalPrice.toLocaleString() + '원';
    }
});