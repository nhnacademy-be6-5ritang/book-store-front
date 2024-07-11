function execDaumPostcode() {
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