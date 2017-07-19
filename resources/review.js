var url = $(location).attr('href').split('/');
var lastIdx = url.length - 1;
var id = url[lastIdx];

function pageInit() {
    $.ajax({
        url: '/'
    });
}