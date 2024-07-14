function previewImage(input) {
    const file = input.files[0];
    const reader = new FileReader();

    reader.onload = function () {
        const previewImg = document.getElementById('previewImg');
        previewImg.src = reader.result;
        previewImg.style.display = 'block';
        const defaultText = document.querySelector('.image-preview__default-text');
        defaultText.style.display = 'none';
        const cancelImageBtn = document.getElementById('cancelImage');
        cancelImageBtn.style.display = 'inline-block';
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

const file = document.getElementById('file');
const imagePreview = document.getElementById('imagePreview');
const imagePreviewImage = imagePreview.querySelector('.image-preview__image');
const imagePreviewDefaultText = imagePreview.querySelector('.image-preview__default-text');
const cancelImageBtn = document.getElementById('cancelImage');

function resetImagePreview() {
    file.value = "";
    imagePreviewDefaultText.style.display = null;
    imagePreviewImage.style.display = "none";
    imagePreviewImage.setAttribute('src', "");
    cancelImageBtn.style.display = "none";
}

// 뒤로 가기 기능 추가
document.querySelector('.close-btn').addEventListener('click', function () {
    window.history.back();
});

const textarea = document.getElementById('reviewComment');
const wordCount = document.querySelector('.word-count span');
const submitBtn = document.getElementById('submitBtn');

textarea.addEventListener('input', function () {
    const textLength = textarea.value.length;
    wordCount.textContent = textLength;
    submitBtn.disabled = textLength === 0 || textLength > 400;
});