setInterval(function() {
    var currentTime = moment().format('YY년 MM월 DD일 HH시 mm분 ss초');
    $('.date').html(currentTime);
}, 1000);


