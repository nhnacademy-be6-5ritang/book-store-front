$(document).ready(function () {
    $('.book-carousel').slick({
        infinite: true,
        slidesToShow: 4,
        slidesToScroll: 4,
        arrows: true,
        prevArrow: '<button type="button" class="slick-prev"><i class="fa-solid fa-caret-left"></i></button>',
        nextArrow: '<button type="button" class="slick-next"><i class="fa-solid fa-caret-right"></i></button>'
    });
});
