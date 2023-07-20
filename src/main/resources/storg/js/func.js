function toggleMenu(){
    $(".menuContainer").toggleClass("hidden");
}
function enterKey(){
    if(window.event.keyCode==13){
        apiLogin();
    }
}

function healthCheck(){
    $('.healthCheck').toggleClass("bg-primary");
    $('.healthCheck').toggleClass("bg-dark");
    $('.healthCheck').children().toggleClass("fa-cog fa-spin");
    $('.healthCheck').children().toggleClass("fa-pause");
}

setInterval(function() {
    var currentTime = moment().format('YYYY년 MM월 DD일 HH시 mm분 ss초');
    $('.title_time').children().html(currentTime);
}, 1000);


