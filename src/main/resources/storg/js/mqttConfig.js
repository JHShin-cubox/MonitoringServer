function pcMqtt() {
    // MQTT 클라이언트 생성
    let host = window.location.hostname
    let port = 8882
    let clientId = "clientId-" + Math.random().toString(16).substr(2, 8);
    let client = new Paho.MQTT.Client(host, port, clientId)

    client.onConnectionLost = function(responseObject) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    };

    client.onMessageArrived = function(message) {

        let jsonData = JSON.parse(message.payloadString); //json 데이서 파싱
        let count = jsonData.data.length; //데이터 개수
        let value1 = 0; //reading count
        let value2 = 0; //waiting count

        //데이터 수랑 맞지 않은 태그 삭제
        $('.check_div').each(function(){
            if($(this).attr('value')>count){
                $(this).remove();
            }
        })

        for(let i=0; i<count; i++){

            let idx = jsonData.data[i].idx; // idx로 html id mapping
            let ip = jsonData.data[i].ip; // idx로 html id mapping
            let status = jsonData.data[i].pcStatus.toUpperCase(); // idx로 html id mapping
            let openLuggage = jsonData.data[i].openLuggage;
            let passLuggage = jsonData.data[i].passLuggage;
            let totalLuggage = jsonData.data[i].totalLuggage;
            let percentOpen = openLuggage/totalLuggage * 100;
            let percentPass = passLuggage/totalLuggage * 100;
            let checkTime = jsonData.data[i].checkTime;
            const hours = Math.floor(checkTime / 3600);
            const minutes = Math.floor((checkTime % 3600) / 60);
            const seconds = checkTime % 60;


            //아이디가 check+i의 텍스트와 json의 ip가 다르면 삽입
            if($('#check'+i).text()!=ip){
                let div1;
                let hidden1 = $("<input type='hidden' id='check_status"+i+"' value='"+jsonData.data[i].pcStatus+"'/>")
                if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == true) div1 = $("<div id='check_div"+i+"' class='waiting check_div'></div>");
                else if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == false) div1 = $("<div id='check_div"+i+"' class='waiting dimmed check_div'></div>");
                else if(jsonData.data[i].pcStatus == 'working' && jsonData.data[i].open == true) div1 = $("<div id='check_div"+i+"' class='reading check_div'></div>");
                else div1 = $("<div id='check_div"+i+"' class='reading dimmed check_div'></div>");
                let div2 = $("<div class='content'></div>");
                let div3 = $("<div class='left_contents'></div>");
                let div3_1 = $("<div class='content_placement'></div>");
                let div3_1_1 = $("<div></div>");
                let div3_1_1_1 = $("<div class='state_info'></div>");
                let img1;
                if(jsonData.data[i].pcStatus == 'waiting') img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
                else img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
                let p1 = $("<p>"+status+"</p>")

                let div3_1_1_2 = $("<div class='num'></div>");
                let p2 = $("<p id='check"+i+"' class='reg_num' value="+i+">"+ip+"</p>")

                let div3_1_2 = $("<div class='bottom_info'></div>");
                let p3 = $("<p class='time_tag'>평균 판독 시간</p>");
                let p4 = $("<p class='time'>"+hours+"h "+minutes+"m "+seconds+"s</p>");

                let div4 = $("<div class='right_content'></div>");
                let div4C = $("<div></div>");
                let div4P = $("<div class='content_placement'></div>");
                let div4W1 = $("<div class='right_content_wrapper'></div>");
                let div4_1 = $("<div class='monitor_info'></div>");

                let p5 = $("<p class='name_tag'>통과 수하물</p>");
                let div4_1_1 = $("<div class='bar'></div>");
                let progress1;
                if(jsonData.data[i].pcStatus == 'working') progress1 = $("<progress class='progress workingP' min='0' max='100' value="+percentPass+"></progress");
                else if(jsonData.data[i].pcStatus == 'waiting') progress1 = $("<progress class='progress waitingP' min='0' max='100' value="+percentPass+"></progress");
                else progress1 = $("<progress class='progress workingP' min='0' max='100' value="+percentPass+"></progress");
                let p6 = $("<p class='num_tag'>"+passLuggage+"</p>");

                let div4_2 = $("<div class='monitor_info'></div>");
                let p7 = $("<p class='name_tag'>개봉 수하물</p>");
                let div4_2_1 = $("<div class='bar'></div>");
                let progress2;
                if(jsonData.data[i].pcStatus == 'working') progress2 = $("<progress class='progress workingP' min='0' max='100' value="+percentOpen+"></progress");
                else if(jsonData.data[i].pcStatus == 'waiting') progress2 = $("<progress class='progress waitingP' min='0' max='100' value="+percentOpen+"></progress");
                else progress2 = $("<progress class='progress workingP' min='0' max='100' value="+percentOpen+"></progress");
                let p8 = $("<p class='num_tag'>"+openLuggage+"</p>");

                let div4W2 = $("<div class='right_content_wrapper_total'></div>");
                let div4W2_1 = $("<div class='monitor_info monitor_total_info'></div>");
                let p9 = $("<p class='name_tag'>전체 수하물</p>");
                let div4W_S = $("<div class='monitor_info monitor_total_info'></div>");
                let p10 = $("<p class='num_tag'>"+totalLuggage+"</p>");

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
            // 각 수하물 개수 업데이트
            $('#check_pass_progress'+i).attr('value',percentPass);

            $('#check_open_progress'+i).attr('value',percentOpen);
            $('#check_pass_count'+i).text(passLuggage);
            $('#check_open_count'+i).text(openLuggage);
            $('#check_total_count'+i).text(totalLuggage);
            $('#check_time'+i).text(hours+'h '+minutes+'m '+seconds+'s');

            if(jsonData.data[i].pcStatus != $('#check_status'+i).val()){
                $('#check_status'+i).val(jsonData.data[i].pcStatus);

                if(jsonData.data[i].pcStatus == 'waiting'){
                    if($('#check_div'+i).hasClass('reading')){
                        $('#check_div'+i).removeClass('reading');
                        $('#check_div'+i).addClass('waiting');
                        $('#check_state_info'+i).children().eq(0).attr('src','/storg/img/waiting_icon.png');
                        $('#check_state_info'+i).children().eq(1).text('WAITING');
                        $('#check_pass_progress'+i).attr('class',"progress waitingP");
                        $('#check_open_progress'+i).attr('class',"progress waitingP");
                    }
                } else{
                    if($('#check_div'+i).hasClass('waiting')){
                        $('#check_div'+i).removeClass('waiting');
                        $('#check_div'+i).addClass('reading');
                        $('#check_state_info'+i).children().eq(0).attr('src','/storg/img/reading_icon.png');
                        $('#check_state_info'+i).children().eq(1).text('WORKING');
                        $('#check_pass_progress'+i).attr('class',"progress workingP");
                        $('#check_open_progress'+i).attr('class',"progress workingP");
                    }
                }
            }
            //전원 값이 true(켜짐)일때 dimmed클래스가 있으면 제거
            if(jsonData.data[i].open == true) {
                if ($('#check_div' + i).hasClass('dimmed')) {
                    $('#check_div' + i).removeClass('dimmed');
                }
            }
            //전원 값이 false(꺼짐)일때 dimmed클래스가 없으면 추가
            else {
                if(!$('#check_div'+i).hasClass('dimmed')){
                    $('#check_div'+i).addClass('dimmed');
                }
            }


            if(status == 'WORKING'){
                value1++;
            } else if(status == 'WAITING'){
                value2++;
            }
        }

        let readingCheckPcPercentD = value1/count*100
        let waitingCheckPcPercentD = value2/count*100
        let readingCheckPcPercent;
        let waitingCheckPcPercent;

        if(readingCheckPcPercentD%1==0) readingCheckPcPercent= Math.floor(readingCheckPcPercentD)
        else{readingCheckPcPercent = Math.round(readingCheckPcPercentD * 100) / 100;}

        if(waitingCheckPcPercentD%1==0) waitingCheckPcPercent= Math.floor(waitingCheckPcPercentD)
        else{waitingCheckPcPercent = Math.round(waitingCheckPcPercentD * 100) / 100;}

        const RADIUS = 49;
        const CIRCUMFERENCE = 2 * Math.PI * RADIUS;
        let dashoffset
        dashoffset = CIRCUMFERENCE * (1 - (readingCheckPcPercent/100));
        $('#check_pcR').css('stroke-dashoffset',dashoffset)
        dashoffset = CIRCUMFERENCE * (1 - (waitingCheckPcPercent/100));
        $('#check_pcW').css('stroke-dashoffset',dashoffset)
        $('#waiting_percent_checkPc').text(waitingCheckPcPercent+'%')
        $('#waiting_percent_checkPc').val(waitingCheckPcPercent)
        $('#reading_percent_checkPc').text(readingCheckPcPercent+'%')
        $('#reading_percent_checkPc').val(readingCheckPcPercent)
        $('#reading_count_checkPc').val(value1);
        if(value1<10) value1 = "0"+value1
        $('#reading_count_checkPc').text(value1);
        $('#waiting_count_checkPc').val(value2);
        if(value2<10) value2 = "0"+value2
        $('#waiting_count_checkPc').text(value2);

        $('#check_size').text(count+'대');

        console.log(jsonData);
    };

    // MQTT 브로커에 연결
    client.connect({
        onSuccess: function() {
            console.log("connected");
            // cubox topic을 구독
            client.subscribe("viewer");
        },
        onFailure: function() {
            console.log("failed to connect");
        }
    });
}

function xrayMqtt() {
    // MQTT 클라이언트 생성
    let host = window.location.hostname
    let port = 8882
    let clientId = "clientId-" + Math.random().toString(16).substr(2, 8);
    let client = new Paho.MQTT.Client(host, port, clientId)

    client.onConnectionLost = function(responseObject) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    };

    // 메시지가 도착했을 때 호출되는 콜백 함수 등록
    client.onMessageArrived = function(message) {
        let jsonData = JSON.parse(message.payloadString);
        let count = jsonData.data.length;
        let value1 = 0; //reading count
        let value2 = 0; //waiting count


        //데이터 수랑 맞지 않은 태그 삭제
        $('.xray_div').each(function(){
            if($(this).attr('value')>count){
                $(this).remove();
            }
        })

        for(let i=0; i<count; i++){
            let idx = jsonData.data[i].idx; // idx로 html id mapping
            let ip = jsonData.data[i].ip; // idx로 html id mapping
            let status = jsonData.data[i].pcStatus.toUpperCase(); // idx로 html id mapping
            let totalLuggage = jsonData.data[i].totalLuggage;

            //관제 상태창 상태값 수 설정
            if(status == 'WORKING') value1++;
            else if(status == 'WAITING') value2++;
            if($('#xray'+i).text()!=ip){
                let hidden1 = $("<input type='hidden' id='xray_status"+i+"' value='"+jsonData.data[i].pcStatus+"'/>")
                let div1;
                if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == true) div1 = $("<div class='waiting'></div>");
                else if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == false) div1 = $("<div class='waiting dimmed'></div>");
                else if(jsonData.data[i].pcStatus == 'working' && jsonData.data[i].open == true) div1 = $("<div class='reading'></div>");
                else div1 = $("<div class='reading dimmed'></div>");

                let div2 = $("<div class='content'></div>");
                let div3 = $("<div class='left_contents'></div>");
                let div3_1 = $("<div class='content_placement'></div>");
                let div3_1_1 = $("<div></div>");
                let div3_1_1_1 = $("<div class='state_info'></div>");
                let img1;
                if(jsonData.data[i].pcStatus == 'waiting') img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
                else img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
                let p1 = $("<p>"+status+"</p>")

                let div3_1_1_2 = $("<div class='num'></div>");
                let p2 = $("<p id='xray"+i+"' class='reg_num' value="+i+">"+ip+"</p>")

                let div3_1_2 = $("<div class='bottom_info xray_bottom'></div>");
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
                let p10 = $("<p class='num_tag'>"+totalLuggage+"</p>");

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
            $('#xray_total_count'+i).text(totalLuggage);
            if(jsonData.data[i].pcStatus != $('#xray_status'+i).val()){
                $('#xray_status'+i).val(jsonData.data[i].pcStatus);
                if(jsonData.data[i].pcStatus == 'working'){
                    if($('#xray_div'+i).hasClass('waiting')){
                        $('#xray_div'+i).removeClass('waiting')
                        $('#xray_div'+i).addClass('reading')
                        $('#xray_state_info'+i).children().eq(0).attr('src','/storg/img/reading_icon.png');
                        $('#xray_state_info'+i).children().eq(1).text('WORKING');
                    }
                } else{
                    if($('#xray_div'+i).hasClass('reading')){
                        $('#xray_div'+i).removeClass('reading')
                        $('#xray_div'+i).addClass('waiting')
                        $('#xray_state_info'+i).children().eq(0).attr('src','/storg/img/waiting_icon.png');
                        $('#xray_state_info'+i).children().eq(1).text('WAITING');
                    }
                }
            }
            //전원 값이 true(켜짐)일때 dimmed클래스가 있으면 제거
            if(jsonData.data[i].open == true) {
                if ($('#xray_div' + i).hasClass('dimmed')) {
                    $('#xray_div' + i).removeClass('dimmed');
                }
            }
            //전원 값이 false(꺼짐)일때 dimmed클래스가 없으면 추가
            else {
                if(!$('#xray_div'+i).hasClass('dimmed')){
                    $('#xray_div'+i).addClass('dimmed');
                }
            }


        }
        let readingXrayPercentD = value1/count*100
        let waitingXrayPercentD = value2/count*100
        let readingXrayPercent;
        let waitingXrayPercent;

        if(readingXrayPercentD%1==0) readingXrayPercent= Math.floor(readingXrayPercentD)
        else{readingXrayPercent = Math.round(readingXrayPercentD * 100) / 100;}

        if(waitingXrayPercentD%1==0) waitingXrayPercent= Math.floor(waitingXrayPercentD)
        else{waitingXrayPercent = Math.round(waitingXrayPercentD * 100) / 100;}
        const RADIUS = 49;
        const CIRCUMFERENCE = 2 * Math.PI * RADIUS;
        let dashoffset
        dashoffset = CIRCUMFERENCE * (1 - (readingXrayPercent/100));
        $('#xrayR').css('stroke-dashoffset',dashoffset)
        dashoffset = CIRCUMFERENCE * (1 - (waitingXrayPercent/100));
        $('#xrayW').css('stroke-dashoffset',dashoffset)
        $('#reading_percent_xray').text(readingXrayPercent+'%')
        $('#reading_percent_xray').val(readingXrayPercent)
        $('#waiting_percent_xray').text(waitingXrayPercent+'%')
        $('#waiting_percent_xray').val(waitingXrayPercent)
        $('#reading_count_xray').val(value1);
        if(value1<10) value1 = "0"+value1
        $('#reading_count_xray').text(value1);
        $('#waiting_count_xray').val(value2);
        if(value2<10) value2 = "0"+value2
        $('#waiting_count_xray').text(value2);
        $('#xray_size').text(count+'대');
        console.log(jsonData);
    };

    // MQTT 브로커에 연결
    client.connect({
        onSuccess: function() {
            console.log("connected");
            // xray_01 topic을 구독
            client.subscribe("xray");
        },
        onFailure: function() {
            console.log("failed to connect");
        }
    });
}

function trsMqtt() {
    // MQTT 클라이언트 생성
    let host = window.location.hostname
    let port = 8882
    let clientId = "clientId-" + Math.random().toString(16).substr(2, 8);
    let client = new Paho.MQTT.Client(host, port, clientId)

    client.onConnectionLost = function(responseObject) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    };

    // 메시지가 도착했을 때 호출되는 콜백 함수 등록
    client.onMessageArrived = function(message) {
        let jsonData = JSON.parse(message.payloadString);
        let count = jsonData.data.length;
        let value1 = 0; //reading count
        let value2 = 0; //waiting count

        //데이터 수랑 맞지 않은 태그 삭제
        $('.trs_div').each(function(){
            if($(this).attr('value')>count){
                $(this).remove();
            }
        })

        for(let i=0; i<count; i++){
            let idx = jsonData.data[i].idx; // idx로 html id mapping
            let ip = jsonData.data[i].ip; // idx로 html id mapping
            let status = jsonData.data[i].pcStatus.toUpperCase(); // idx로 html id mapping
            let totalLuggage = jsonData.data[i].totalLuggage;

            if(status == 'WORKING'){
                value1++;
            } else if(status == 'WAITING'){
                value2++;
            }

            if($('#trs'+i).text()!=ip){
                let div1;
                let hidden1 = $("<input type='hidden' id='trs_status"+i+"' value='"+jsonData.data[i].pcStatus+"'/>")
                if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == true) div1 = $("<div class='waiting'></div>");
                else if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == false) div1 = $("<div class='waiting dimmed'></div>");
                else if(jsonData.data[i].pcStatus == 'working' && jsonData.data[i].open == true) div1 = $("<div class='reading'></div>");
                else div1 = $("<div class='reading dimmed'></div>");

                let div2 = $("<div class='content'></div>");
                let div3 = $("<div class='left_contents'></div>");
                let div3_1 = $("<div class='content_placement'></div>");
                let div3_1_1 = $("<div></div>");
                let div3_1_1_1 = $("<div class='state_info'></div>");
                let img1;
                if(jsonData.data[i].pcStatus == 'waiting') img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
                else img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
                let p1 = $("<p>"+status+"</p>")

                let div3_1_1_2 = $("<div class='num'></div>");
                let p2 = $("<p id='xray"+i+"' class='reg_num' value="+i+">"+ip+"</p>")

                let div3_1_2 = $("<div class='bottom_info'></div>");
                let p3 = $("<p class='time_tag'></p>");
                let p4 = $("<p class='time'></p>");

                let div4 = $("<div class='right_content'></div>");
                let div4C = $("<div></div>");
                let div4P = $("<div class='content_placement'></div>");
                let div4W1 = $("<div class='right_content_wrapper'></div>");
                let div4_1 = $("<div class='monitor_info'></div>");
                let div4_2 = $("<div class='monitor_info'></div>");

                let div4W2 = $("<div class='right_content_wrapper_total'></div>");
                let div4W2_1 = $("<div class='monitor_info monitor_total_info'></div>");
                let p9 = $("<p class='name_tag'></p>");
                let div4W_S = $("<div class='monitor_info monitor_total_info'></div>");
                let p10 = $("<p class='num_tag'></p>");

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
                $('.gr_trs').append(div1);
            }
            $('#trs_total_count'+i).text(totalLuggage);
            if(jsonData.data[i].pcStatus != $('#trs_status'+i).val()){
                $('#trs_status'+i).val(jsonData.data[i].pcStatus);
                if(jsonData.data[i].pcStatus == 'working'){
                    if($('#trs_div'+i).hasClass('waiting')){
                        $('#trs_div'+i).removeClass('waiting')
                        $('#trs_div'+i).addClass('reading')
                        $('#trs_state_info'+i).children().eq(0).attr('src','/storg/img/reading_icon.png');
                        $('#trs_state_info'+i).children().eq(1).text('WORKING');

                    }
                } else{
                    if($('#trs_div'+i).hasClass('reading')){
                        $('#trs_div'+i).removeClass('reading')
                        $('#trs_div'+i).addClass('waiting')
                        $('#trs_state_info'+i).children().eq(0).attr('src','/storg/img/waiting_icon.png');
                        $('#trs_state_info'+i).children().eq(1).text('WAITING');
                    }
                }
            }
            //전원 값이 true(켜짐)일때 dimmed클래스가 있으면 제거
            if(jsonData.data[i].open == true) {
                if ($('#trs_div' + i).hasClass('dimmed')) {
                    $('#trs_div' + i).removeClass('dimmed');
                }
            }
            //전원 값이 false(꺼짐)일때 dimmed클래스가 없으면 추가
            else {
                if(!$('#trs_div'+i).hasClass('dimmed')){
                    $('#trs_div'+i).addClass('dimmed');
                }
            }

        }
        let readingTrsPercentD = value1/count*100
        let waitingTrsPercentD = value2/count*100
        let readingTrsPercent;
        let waitingTrsPercent;

        if(readingTrsPercentD%1==0) readingTrsPercent= Math.floor(readingTrsPercentD)
        else{readingTrsPercent = Math.round(readingTrsPercentD * 100) / 100;}

        if(waitingTrsPercentD%1==0) waitingTrsPercent= Math.floor(waitingTrsPercentD)
        else{waitingTrsPercent = Math.round(waitingTrsPercentD * 100) / 100;}
        const RADIUS = 49;
        const CIRCUMFERENCE = 2 * Math.PI * RADIUS;
        let dashoffset
        dashoffset = CIRCUMFERENCE * (1 - (readingTrsPercent/100));
        $('#trsR').css('stroke-dashoffset',dashoffset)
        dashoffset = CIRCUMFERENCE * (1 - (waitingTrsPercent/100));
        $('#trsW').css('stroke-dashoffset',dashoffset)
        $('#reading_percent_trs').text(readingTrsPercent+'%')
        $('#reading_percent_trs').val(readingTrsPercent)
        $('#waiting_percent_trs').text(waitingTrsPercent+'%')
        $('#waiting_percent_trs').val(waitingTrsPercent)


        $('#reading_count_trs').val(value1);
        if(value1<10) value1 = "0"+value1
        $('#reading_count_trs').text(value1);
        $('#waiting_count_trs').val(value2);
        if(value2<10) value2 = "0"+value2
        $('#waiting_count_trs').text(value2);
        $('#trs_size').text(count+'대');
        console.log(jsonData);
    };

    // MQTT 브로커에 연결
    client.connect({
        onSuccess: function() {
            console.log("connected");
            // xray_01 topic을 구독
            client.subscribe("trs");
        },
        onFailure: function() {
            console.log("failed to connect");
        }
    });
}

function adexMqtt() {
    // MQTT 클라이언트 생성
    let host = window.location.hostname
    // let host = "http://xraysite.kr:";
    let port = 8882
    let clientId = "clientId-" + Math.random().toString(16).substr(2, 8);
    let client = new Paho.MQTT.Client(host, port, clientId)

    client.onConnectionLost = function(responseObject) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    };

    client.onMessageArrived = function(message) {
        let now_value = $('#now_value').val();
        $.ajax({
            url: 'down',
            type: 'get',
            success:function (data){
                $.ajax({
                    url: 'status',
                    type: 'get',
                    success:function (data){
                        $('#luggage_count').text(data.luggageCount)
                        $('#detection_count').text(data.detectionCount)
                    }
                    
                });
                $.ajax({
                    url: 'lid',
                    type: 'get',
                    success:function (data){
                        $('#mLid').text(data)
                    }
                });
                $.ajax({
                    url: 'topTen',
                    type: 'get',
                    success:function (data){
                        let count = 1;
                        data.forEach(function (item) {
                            if($('#label_name'+count).length==0){

                                let div1 = $('<div class="item_list_container list_active"></li>');

                                let labelName = $('<p class="item_name"></p>');
                                labelName.attr('id','label_name'+count);
                                labelName.text(item.labelName)
                                let div1_1 = $('<div class="item_bar"></li>');

                                let div1_1_1 = $('<div class="item_bar"></li>');

                                let progress = $('<progress class="progress workingP" min="0" max="100"></progress>');
                                progress.attr('value',item.labelRatio);
                                progress.attr('id','prg'+count);

                                let labelCount = $('<p></p>');
                                labelCount.attr('value',item.labelCount);
                                labelCount.attr('id','label_count'+count)
                                labelCount.text(item.labelCount);

                                $('.item_list_wrapper').append(div1)
                                div1.append(labelName).append(div1_1).append(labelCount);
                                div1_1.append(div1_1_1);
                                div1_1_1.append(progress);

                            }
                            $('#label_name'+count).text(item.labelName);
                            $('#label_count'+count).text(item.labelCount+'개');
                            $('#prg'+count).val(item.labelRatio);

                            count++;
                        });
                    }
                });
                $.ajax({
                    url: 'subImage',
                    type: 'get',
                    success:function (data){
                        let count = 1;
                        const bfCount = $('.thumbnail_img').length;
                        console.log(bfCount)
                        if(bfCount != data.length){
                            if(bfCount > data.length){
                                console.log("보내는게 적을때")
                                for(let i=bfCount; i>bfCount - (bfCount-data.length);i--){
                                    $('#sub'+i).parent().remove();
                                    // $('#main'+i).remove();
                                }
                                data.forEach(function (item) {
                                    $('#main'+count).attr({
                                        'src': '/adex_image/' + item.name
                                    });

                                    // 서브 이미지 태그 생성
                                    if ($("#funOn").is(":checked")) {
                                        $('#sub'+count).attr({
                                            'src': '/adex_image/bbox/' + item.name
                                        });
                                    }
                                    else{
                                        $('#sub'+count).attr({
                                            'src': '/adex_image/' + item.name
                                        });
                                    }
                                    if(count ==1){
                                        if ($("#funOn").is(":checked")) {
                                            $('#main_image').attr('src','/adex_image/'+item.name);
                                            $('#sub_image').attr('src','/adex_image/bbox/'+item.name);
                                        } else{
                                            $('#main_image').attr('src','/adex_image/'+item.name);
                                            $('#sub_image').attr('src','/adex_image/'+item.name);
                                        }
                                    }
                                    count++;
                                });
                            }
                            if(bfCount < data.length){
                                console.log("보내는게 많을때")
                                for(let i=bfCount+1;i<=data.length;i++){

                                    let div1 = $("<div class='thumbnail_img'/></div>")
                                    let subImg = $("<img id='sub"+i+"' value="+i+" />");
                                    let mainImg = $("<img id='main"+i+"' value="+i+" style='display:none' />");
                                    $('.thumbnail_container').append(div1);
                                    div1.append(subImg).append(mainImg);
                                }
                                data.forEach(function (item) {
                                    $('#main'+count).attr({
                                        'src': '/adex_image/' + item.name
                                    });

                                    if ($("#funOn").is(":checked")) {
                                        $('#sub'+count).attr({
                                            'src': '/adex_image/bbox/' + item.name
                                        });
                                    }else{
                                        $('#sub'+count).attr({
                                            'src': '/adex_image/' + item.name
                                        });

                                    }
                                    if(count ==1){
                                        if ($("#funOn").is(":checked")) {
                                            $('#main_image').attr('src','/adex_image/'+item.name);
                                            $('#sub_image').attr('src','/adex_image/bbox/'+item.name);
                                        } else{
                                            $('#main_image').attr('src','/adex_image/'+item.name);
                                            $('#sub_image').attr('src','/adex_image/'+item.name);
                                        }
                                    }
                                    // 서브 이미지 태그 생성
                                    count++;
                                });
                            }
                        } else{
                            console.log("보내는게 같을때")
                            data.forEach(function (item) {
                                $('#main'+count).attr({
                                    'src': '/adex_image/' + item.name
                                });

                                // 서브 이미지 태그 생성
                                if ($("#funOn").is(":checked")) {
                                    $('#sub'+count).attr({
                                        'src': '/adex_image/bbox/' + item.name
                                    });
                                } else{
                                    $('#sub'+count).attr({
                                        'src': '/adex_image/' + item.name
                                    });
                                }
                                if ($("#funOn").is(":checked")) {
                                    if(count ==now_value){
                                        $('#main_image').attr('src','/adex_image/'+item.name);
                                        $('#sub_image').attr('src','/adex_image/bbox/'+item.name);
                                    }
                                }
                                else{
                                    if(count ==now_value){
                                        $('#main_image').attr('src','/adex_image/'+item.name);
                                        $('#sub_image').attr('src','/adex_image/'+item.name);
                                    }
                                }
                                count++;
                            });
                        }
                    }
                })
                let jsonData = JSON.parse(message.payloadString);
                console.log("전체 json : "+jsonData.lid)
            }
        });
    };

    // MQTT 브로커에 연결
    client.connect({
        onSuccess: function() {
            console.log("connected");
            // cubox topic을 구독
            client.subscribe("adex");
        },
        onFailure: function() {
            console.log("failed to connect");
        }
    });
}


function publishMqttMessage() {
    // MQTT 클라이언트 생성
    let host = window.location.hostname
    let port = 8882
    let clientId = "clientId-" + Math.random().toString(16).substr(2, 8);
    let topic = "adex"; // 원하는 MQTT 주제를 지정하세요.
    let message = { lid: "123123qwe" }; // 전송할 메시지를 지정하세요.

    let client = new Paho.MQTT.Client(host, port, clientId);

    // 연결 시도
    client.connect({
        onSuccess: function() {
            console.log("Connected to MQTT broker");

            // 메시지 게시
            let mqttMessage = new Paho.MQTT.Message(JSON.stringify(message));
            mqttMessage.destinationName = topic;

            client.send(mqttMessage);
            console.log("Message published to topic: " + topic);
        },
        onFailure: function() {
            console.log("Failed to connect to MQTT broker");
        }
    });
}