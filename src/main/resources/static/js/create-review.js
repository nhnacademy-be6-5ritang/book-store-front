document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form');
    const textarea = document.querySelector('textarea');
    const wordCount = document.querySelector('.word-count span');
    const submitBtn = document.querySelector('.submit-btn');
    const imageUpload = document.getElementById('imageUpload');
    const imagePreview = document.getElementById('imagePreview');
    const imagePreviewImage = imagePreview.querySelector('.image-preview__image');
    const imagePreviewDefaultText = imagePreview.querySelector('.image-preview__default-text');
    const cancelImageBtn = document.getElementById('cancelImage');

    textarea.addEventListener('input', () => {
        const textLength = textarea.value.length;
        wordCount.textContent = textLength;
        submitBtn.disabled = textLength === 0 || textLength > 400;
    });

    imageUpload.addEventListener('change', function() {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            imagePreviewDefaultText.style.display = "none";
            imagePreviewImage.style.display = "block";
            cancelImageBtn.style.display = "inline-block";

            reader.addEventListener('load', function() {
                imagePreviewImage.setAttribute('src', this.result);
            });

            reader.readAsDataURL(file);
        } else {
            resetImagePreview();
        }
    });

    cancelImageBtn.addEventListener('click', resetImagePreview);

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(form);

        // reviewScore 값을 FormData에 추가
        const reviewScore = document.querySelector('input[name="reviewScore"]:checked').value;
        formData.append('reviewScore', reviewScore);

        fetch('/api/reviews', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                alert('리뷰가 성공적으로 등록되었습니다.');
                window.location.href = '/api/users/me/reviews/page';
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('리뷰 등록 중 오류가 발생했습니다. 다시 시도해 주세요.');
            });
    });

    function resetImagePreview() {
        imageUpload.value = "";
        imagePreviewDefaultText.style.display = null;
        imagePreviewImage.style.display = "none";
        imagePreviewImage.setAttribute('src', "");
        cancelImageBtn.style.display = "none";
    }
});