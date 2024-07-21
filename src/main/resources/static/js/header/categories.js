const categoryMenuButton = document.getElementById('category-menu-button');
const parentCategoriesContainer = document.querySelector('.categories-container');

categoryMenuButton.addEventListener('click', () => {
    if (parentCategoriesContainer.classList.contains('monte-show')) {
        parentCategoriesContainer.classList.remove('monte-show');
        categoryMenuButton.classList.remove('active');
    } else {
        parentCategoriesContainer.classList.add('monte-show');
        categoryMenuButton.classList.add('active');
    }
});

document.querySelectorAll('.categories-container > div').forEach(item => {
    const categoryLink = item.querySelector('a');
    const subMenu = item.querySelector('.sub-menu');

    if (categoryLink && subMenu) {
        categoryLink.addEventListener('mouseenter', () => {
            item.classList.add('show-sub-menu');
        });

        categoryLink.addEventListener('mouseleave', () => {
            setTimeout(() => {
                if (!item.querySelector('.sub-menu:hover')) {
                    item.classList.remove('show-sub-menu');
                }
            }, 100);
        });

        subMenu.addEventListener('mouseenter', () => {
            item.classList.add('show-sub-menu');
        });

        subMenu.addEventListener('mouseleave', () => {
            item.classList.remove('show-sub-menu');
        });
    }
});
