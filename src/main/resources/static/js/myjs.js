$('.ui.dropdown').dropdown();

$('.ui.search')
    .search({
        apiSettings: {
            url: '//api.github.com/search/repositories?q={query}'
        },
        fields: {
            results : 'items',
            title   : 'name',
            url     : 'html_url'
        },
        minCharacters : 2
    })
;


$('.ui.sticky').sticky({
    context: '#footer'
});

function login_fun() {
    $('#login_modal')
        .modal('show');
}

function login_fun2(){
    sessionStorage.removeItem("login_notfound");
    sessionStorage.removeItem("login_popup");
}


function signup_fun2() {
    sessionStorage.removeItem("signup_successful");
    sessionStorage.removeItem("signup_fail");
}


$(window).on('load',function(){

    if (sessionStorage.getItem("login_popup") == undefined) {
        $('#after_login_modal').modal('show');
       // $ .cookie("popup_1_2", "2");
    }

        //$('#after_login_modal').modal('show');
        // $('#after_login_modal').modal('show');
        if (  $('#after_login_modal').hasClass('in') == true ){
            sessionStorage.setItem("login_popup", "1");
        }
        
});

$(window).on('load',function(){

    if (sessionStorage.getItem("login_notfound") == undefined) {
        $('#notfound_login_modal').modal('show');
        // $ .cookie("popup_1_2", "2");
    }

    //$('#after_login_modal').modal('show');
    // $('#after_login_modal').modal('show');
    if (  $('#notfound_login_modal').hasClass('in') == true ){
        sessionStorage.setItem("login_notfound", "1");
    }

});

$(window).on('load',function(){

    if (sessionStorage.getItem("signup_successful") == undefined) {
        $('#signup_successful_modal').modal('show');
        // $ .cookie("popup_1_2", "2");
    }

    //$('#after_login_modal').modal('show');
    // $('#after_login_modal').modal('show');
    if (  $('#signup_successful_modal').hasClass('in') == true ){
        sessionStorage.setItem("signup_successful", "1");
    }

});


$(window).on('load',function(){

    if (sessionStorage.getItem("signup_fail") == undefined) {
        $('#signup_fail_modal').modal('show');
        // $ .cookie("popup_1_2", "2");
    }

    //$('#after_login_modal').modal('show');
    // $('#after_login_modal').modal('show');
    if (  $('#signup_fail_modal').hasClass('in') == true ){
        sessionStorage.setItem("signup_fail", "1");
    }

});


function logout_fun(){
    sessionStorage.removeItem("login_popup");
    sessionStorage.removeItem(login_notfound);
}

function signup_fun() {
    $('#signup_modal')
        .modal('show');
}



function peppameter_fun() {
    $('#peppameter_modal')
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

        password_signup: {
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
                type: 'match[password_signup]',
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
