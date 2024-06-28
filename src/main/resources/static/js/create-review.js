document.addEventListener("DOMContentLoaded", function() {
    const textarea = document.querySelector('textarea');
    const wordCount = document.querySelector('.word-count span');
    const submitBtn = document.querySelector('.submit-btn');

    textarea.addEventListener('input', () => {
        const textLength = textarea.value.length;
        wordCount.textContent = textLength;
        submitBtn.disabled = textLength === 0 || textLength > 400;
    });
});