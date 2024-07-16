document.getElementById('category-menu-button').addEventListener('click', function () {
    const menu = document.querySelector('.category-menu');
    if (menu.style.display === 'none' || menu.style.display === '') {
        menu.style.display = 'block';
    } else {
        menu.style.display = 'none';
    }
});