document.addEventListener("DOMContentLoaded", () => {
    if (responseMessage)
        alert(responseMessage);

    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', function (event) {
            event.stopPropagation();
            const addressId = button.getAttribute('data-id');
            openUpdateModal(addressId);
        });
    });

    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', function (event) {
            event.stopPropagation();
            const addressId = button.getAttribute('data-id');
            deleteAddress(addressId);
        });
    });

    document.querySelectorAll('.address-row').forEach(row => {
        row.addEventListener('click', function () {
            const addressId = row.getAttribute('id');
            confirmUpdateDefaultAddress(addressId);
        });
    });
});


const execDaumPostcode = () => {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('postCode').value = data.zonecode;

            var addr = ''; // 주소 변수

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("baseAddress").value = addr;
        }
    }).open();
}

const execDaumPostcodeUpdate = (postCodeId = 'postCode', baseAddressId = 'baseAddress') => {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById(postCodeId).value = data.zonecode;

            var addr = ''; // 주소 변수

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById(baseAddressId).value = addr;
        }
    }).open();
}

const openUpdateModal = (addressId) => {
    const address = myAddresses.find(address => address.id === +addressId);

    if (address) {
        document.getElementById('updateAddressId').value = address.id;
        document.getElementById('updateAlias').value = address.alias;
        document.getElementById('updatePostCode').value = address.postCode;
        document.getElementById('updateBaseAddress').value = address.baseAddress;
        document.getElementById('updateDetailAddress').value = address.detailAddress;
        document.getElementById('updateAddressForm').action = `/addresses/${address.id}`;
        $('#updateModal').modal('show');
    }
}

const checkAddressLimit = () => {
    if (myAddresses.length >= 10) {
        alert("주소는 최대 10까지 등록할 수 있습니다.");
        return false;
    }

    return true;
}

const deleteAddress = async (addressId) => {
    console.log('delete: ', addressId);
    myAddresses = myAddresses.filter(address => address.id !== addressId);
    const row = document.getElementById(addressId);
    if (row) {
        row.remove();
    }
    const response = await fetch(`/addresses/${addressId}`, {
        method: 'DELETE'
    })

    if (response.status === 204) {
        alert("주소가 삭제되었습니다.");
    } else {
        alert("주소 삭제에 실패했습니다.");
    }
};

const confirmUpdateDefaultAddress = (addressId) => {
    if (confirm("이 주소를 기본 주소로 설정하시겠습니까?")) {
        updateDefaultAddress(addressId);
    }
}

const updateDefaultAddress = async (addressId) => {
    const response = await fetch(`/addresses/${addressId}/default`, {
        method: 'PUT'
    });

    if (response.status === 200) {
        location.reload();
        alert("기본 주소가 변경되었습니다.");
    } else {
        alert("기본 주소 변경에 실패했습니다.");
    }
}