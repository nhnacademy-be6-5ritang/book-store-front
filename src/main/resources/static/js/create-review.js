document.addEventListener("DOMContentLoaded", function() {
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

    function resetImagePreview() {
        imageUpload.value = "";
        imagePreviewDefaultText.style.display = null;
        imagePreviewImage.style.display = "none";
        imagePreviewImage.setAttribute('src', "");
        cancelImageBtn.style.display = "none";
    }
});

document.getElementById('imageUpload').addEventListener('change', function() {
    const file = this.files[0];
    if (file) {
        console.log('File selected:', file);
        const formData = new FormData();
        formData.append('file', file);

        fetch('/api/upload-image', {
            method: 'POST',
            body: formData,
        })
            .then(response => {
                console.log('Response received:', response);
                return response.json();
            })
            .then(data => {
                console.log('Data received:', data);
                if (data.success) {
                    const imageUrl = data.imageUrl;
                    document.querySelector('.image-preview__image').src = imageUrl;
                    document.querySelector('.image-preview__image').style.display = 'block';
                    document.querySelector('.image-preview__default-text').style.display = 'none';
                    document.getElementById('cancelImage').style.display = 'block';
                } else {
                    alert('이미지 업로드 실패: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } else {
        console.log('No file selected');
    }
});
