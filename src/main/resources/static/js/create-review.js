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
