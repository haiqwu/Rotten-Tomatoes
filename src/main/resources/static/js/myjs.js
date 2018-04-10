$('.ui.dropdown').dropdown();

$('.ui.search')
    .search({
        // source: content,
        // searchFullText: false
        apiSettings: {
            url: "https://api.github.com/search/repositories?q={query}"
        },
        fields: {
            results: 'items',
            title: 'name',
            url: 'html_url'
        }
    });

$('.ui.sticky').sticky({
    context: '#navbar'
});

function login_fun() {
    $('#login_modal')
        .modal('show');
}

function signup_fun() {
    $('#signup_modal')
        .modal('show');
}

$(function () {
    $("#login_modal").modal({
        closable: true
    });
});

$(document).ready(function () {
    $('.slicks').slick({
        adaptiveHeight: false,
        arrows: true,
        dots: true,
        slidesToShow: 1,
        autoplay: true,
        pauseOnDotsHover: true,
        infinite: true,
        fade: true
    });
});
