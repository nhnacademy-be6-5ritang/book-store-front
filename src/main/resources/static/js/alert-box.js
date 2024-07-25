// 알림 박스를 설정하는 함수입니다.
// URL 과 HTTP 메서드를 인수로 받아서 버튼 클릭 시 알림 박스에 메시지를 표시합니다.
function setupAlertBox(url, method, buttonId) {
    document.addEventListener('DOMContentLoaded', function () {
        // 버튼과 알림 박스 요소를 찾습니다.
        const button = document.getElementById(buttonId);
        const alertBox = document.getElementById('alertBox');
        const alertMessage = document.getElementById('alertMessage');

        if (button) {
            // 버튼 클릭 시 실행되는 이벤트 리스너입니다.
            button.addEventListener('click', function () {
                fetch(url, {method: method})
                    .then(response => {
                        // 네트워크 응답이 성공적인지 확인합니다.
                        if (!response.ok) {
                            throw new Error('네트워크 응답이 올바르지 않습니다.');
                        }
                        return response.text();
                    })
                    .then(message => {
                        // HTTP 메서드에 따라 표시할 메시지를 설정합니다.
                        let displayMessage = '';
                        switch (method) {
                            case 'GET':
                                displayMessage = '요청이 성공하였습니다.';
                                break;
                            case 'POST':
                                displayMessage = '새 데이터가 성공적으로 추가되었습니다.';
                                break;
                            case 'PUT':
                                displayMessage = '데이터가 성공적으로 수정되었습니다.';
                                break;
                            case 'DELETE':
                                displayMessage = '데이터가 성공적으로 삭제되었습니다.';
                                break;
                            default:
                                displayMessage = '요청이 성공하였습니다.';
                        }
                        alertMessage.innerText = displayMessage;
                        alertBox.classList.add('show');
                        // 5초 후에 알림 박스를 숨깁니다.
                        setTimeout(() => {
                            alertBox.classList.remove('show');
                        }, 5000);
                    })
                    .catch(error => {
                        console.error('오류:', error);
                        // HTTP 메서드에 따라 실패 메시지를 설정합니다.
                        let displayMessage = '';
                        switch (method) {
                            case 'GET':
                                displayMessage = '요청에 실패하였습니다.';
                                break;
                            case 'POST':
                                displayMessage = '새 데이터 추가에 실패하였습니다.';
                                break;
                            case 'PUT':
                                displayMessage = '데이터 수정에 실패하였습니다.';
                                break;
                            case 'DELETE':
                                displayMessage = '데이터 삭제에 실패하였습니다.';
                                break;
                            default:
                                displayMessage = '요청에 실패하였습니다.';
                        }
                        alertMessage.innerText = displayMessage;
                        alertBox.classList.add('show');
                        // 5초 후에 알림 박스를 숨깁니다.
                        setTimeout(() => {
                            alertBox.classList.remove('show');
                        }, 5000);
                    });
            });
        }
    });
}