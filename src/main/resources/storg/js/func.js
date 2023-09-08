setInterval(function() {
    var currentTime = moment().format('YY년 MM월 DD일 HH시 mm분 ss초');
    $('.date').html(currentTime);
}, 1000);


function mqttTest(){
    console.log("123")
    let div1 = $("<div class='reading check_div'></div>");
    let hidden1 = $("<input type='hidden' id='trs_status1' value='working'/>")

    let div2 = $("<div class='content'></div>");
    let div3 = $("<div class='left_contents'></div>");
    let div3_1 = $("<div class='content_placement'></div>");
    let div3_1_1 = $("<div></div>");
    let div3_1_1_1 = $("<div class='state_info'></div>");
    let img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
    let p1 = $("<p>WORKING</p>")

    let div3_1_1_2 = $("<div class='num'></div>");
    let p2 = $("<p id='xray1' class='reg_num' value=1>000.000.0</p>")

    let div3_1_2 = $("<div class='bottom_info'></div>");
    let p3 = $("<p class='time_tag'>평균 판독시간</p>");
    let p4 = $("<p class='time'>01h 23m 01s</p>");

    let div4 = $("<div class='right_content'></div>");
    let div4C = $("<div></div>");
    let div4P = $("<div class='content_placement'></div>");
    let div4W1 = $("<div class='right_content_wrapper'></div>");
    let div4_1 = $("<div class='monitor_info'></div>");

    let p5 = $("<p class='name_tag'>통과 수하물</p>");
    let div4_1_1 = $("<div class='bar'></div>");
    let progress1 = $("<progress class='progress workingP' min='0' max='100' value='30'></progress");

    let p6 = $("<p class='num_tag'>06</p>");

    let div4_2 = $("<div class='monitor_info'></div>");
    let p7 = $("<p class='name_tag'>개봉 수하물</p>");
    let div4_2_1 = $("<div class='bar'></div>");
    let progress2 = $("<progress class='progress workingP' min='0' max='100' value='30'></progress");

    let p8 = $("<p class='num_tag'>10</p>");

    let div4W2 = $("<div class='right_content_wrapper_total'></div>");
    let div4W2_1 = $("<div class='monitor_info monitor_total_info'></div>");
    let p9 = $("<p class='name_tag'>전체 수하물</p>");
    let div4W_S = $("<div class='monitor_info monitor_total_info'></div>");
    let p10 = $("<p class='num_tag'>14</p>");

    div4W2_1.append(p9).append(div4W_S).append(p10)
    div4W2.append(div4W2_1)

    div4_2_1.append(progress2)
    div4_2.append(p7).append(div4_2_1).append(p8)

    div4_1_1.append(progress1)
    div4_1.append(p5).append(div4_1_1).append(p6)
    div4W1.append(div4_1).append(div4_2)

    div4P.append(div4W1).append(div4W2)
    div4C.append(div4P)
    div4.append(div4C)

    div3_1_2.append(p3).append(p4)

    div3_1_1_2.append(p2)
    div3_1_1_1.append(img1).append(p1)
    div3_1_1.append(div3_1_1_1).append(div3_1_1_2)
    div3_1.append(div3_1_1).append(div3_1_2)
    div3.append(div3_1)
    div2.append(div3).append(div4);
    div1.append(div2).append(hidden1)
    $('.gr_checkPc').append(div1);
}

function xrayInsertTest(){
    let hidden1 = $("<input type='hidden' id='xray_status3' value='waiting'/>")
    let div1 = $("<div class='waiting'></div>");
    let div2 = $("<div class='content'></div>");
    let div3 = $("<div class='left_contents'></div>");
    let div3_1 = $("<div class='content_placement'></div>");
    let div3_1_1 = $("<div></div>");
    let div3_1_1_1 = $("<div class='state_info'></div>");
    let img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
    let p1 = $("<p>waiting</p>")

    let div3_1_1_2 = $("<div class='num'></div>");
    let p2 = $("<p id='xray3' class='reg_num' value=3>장비명</p>")

    let div3_1_2 = $("<div class='bottom_info'></div>");
    let p3 = $("<p class='time_tag xray_none'></p>");
    let p4 = $("<p class='time xray_none'></p>");

    let div4 = $("<div class='right_content'></div>");
    let div4C = $("<div></div>");
    let div4P = $("<div class='content_placement'></div>");
    let div4W1 = $("<div class='right_content_wrapper'></div>");
    let div4_1 = $("<div class='monitor_info xray_none'></div>");
    let div4_2 = $("<div class='monitor_info xray_none'></div>");


    let div4W2 = $("<div class='right_content_wrapper_total'></div>");
    let div4W2_1 = $("<div class='monitor_info monitor_total_info'></div>");
    let p9 = $("<p class='name_tag'>수하물 처리량</p>");
    let div4W_S = $("<div class='monitor_info monitor_total_info'></div>");
    let p10 = $("<p class='num_tag'>15</p>");

    div4W2_1.append(p9).append(div4W_S).append(p10)
    div4W2.append(div4W2_1)

    div4W1.append(div4_1).append(div4_2)

    div4P.append(div4W1).append(div4W2)
    div4C.append(div4P)
    div4.append(div4C)

    div3_1_2.append(p3).append(p4)

    div3_1_1_2.append(p2)
    div3_1_1_1.append(img1).append(p1)
    div3_1_1.append(div3_1_1_1).append(div3_1_1_2)
    div3_1.append(div3_1_1).append(div3_1_2)
    div3.append(div3_1)
    div2.append(div3).append(div4);
    div1.append(div2).append(hidden1)
    $('.gr_xray').append(div1);
}
