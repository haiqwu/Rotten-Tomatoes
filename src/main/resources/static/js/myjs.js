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
    context: '#footer'
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

$('form').form({
    on: 'blur',
    fields: {
        username: {
            rules: [{
                type: 'empty',
                prompt: 'Please enter a username'
            },
                {
                    type: 'minLength[4]',
                    prompt: 'Username must longer than 6 characters'
                }
            ]
        },
        password: {
            rules: [{
                type: 'empty',
                prompt: 'Please select a password'
            },
                {
                    type: 'minLength[4]',
                    prompt: 'Password must longer than 6 characters'
                }
            ]
        },

        email: {
            rules:[{
                type: 'email',
                prompt: 'Invalid Email Address'
            }]
        },
        re_password:{
            rules:[{
                type: 'match[password]',
                prompt: 'Repeated Password Is Different'
            }]
        }
    }
});

$('.ui.rating')
    .rating({
        initialRating: 3,
        maxRating: 5,
        onRate: function (rating) {
            // alert(rating)

            // var rate2= rating;
            $('#rate_num').html(rating);
            // document.getElementById("rate_num").innerText(rate);
        }
    });

$('.rating')
    .rating('setting', 'clearable', true);
