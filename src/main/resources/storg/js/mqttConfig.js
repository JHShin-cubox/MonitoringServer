function pcMqtt() {
    // MQTT 클라이언트 생성
    let host = "broker.emqx.io";
    let port = 8083
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

            //아이디가 check+i의 텍스트와 json의 ip가 다르면 삽입
            if($('#check'+i).text()!=ip){
                let div1;

                let hidden1 = $("<input type='hidden' id='check_status"+i+"' value='"+jsonData.data[i].pcStatus+"'/>")

                if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == true) div1 = $("<div id='check_div"+i+"' class='waiting check_div'></div>");
                else if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == false) div1 = $("<div id='check_div"+i+"' class='waiting dimmed check_div'></div>");
                else if(jsonData.data[i].pcStatus == 'reading' && jsonData.data[i].open == true) div1 = $("<div id='check_div"+i+"' class='reading check_div'></div>");
                else div1 = $("<div id='check_div"+i+"' class='reading dimmed check_div'></div>");

                let div2 = $("<div class='content'></div>");
                let div3 = $("<div class='content_status'></div>");
                let img1;
                if(jsonData.data[i].pcStatus == 'waiting') img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
                else img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
                let p1 = $("<p>"+status+"</p>")
                let p2 = $("<p id='check"+i+"' class='reg_num' value="+i+">"+ip+"</p>")

                div3.append(img1).append(p1)
                div2.append(div3).append(p2);
                div1.append(div2).append(hidden1)
                $('.gr_checkPc').append(div1);
            }

            if(jsonData.data[i].pcStatus != $('#check_status'+i).val()){
                $('#check_status'+i).val(jsonData.data[i].pcStatus);
                if(jsonData.data[i].pcStatus == 'waiting'){
                    if($('#check_div'+i).hasClass('reading')){
                        $('#check_div'+i).removeClass('reading');
                        $('#check_div'+i).addClass('waiting');
                        $('#check_div'+i).children().eq(1).children().children().eq(0).attr('src','/storg/img/waiting_icon.png');
                        $('#check_div'+i).children().eq(1).children().children().eq(1).text('WAITING');
                    }
                } else{
                    if($('#check_div'+i).hasClass('waiting')){
                        $('#check_div'+i).removeClass('waiting');
                        $('#check_div'+i).addClass('reading');
                        $('#check_div'+i).children().eq(1).children().children().eq(0).attr('src','/storg/img/reading_icon.png');
                        $('#check_div'+i).children().eq(1).children().children().eq(1).text('READING');
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


            if(status == 'READING'){
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
    let host = "broker.emqx.io";
    let port = 8083
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
            
            //관제 상태창 상태값 수 설정
            if(status == 'START') value1++;
            else if(status == 'END') value2++;
            
            if($('#xray'+i).text()!=ip){
                let hidden1 = $("<input type='hidden' id='xray_status"+i+"' value='"+jsonData.data[i].pcStatus+"'/>")
                let div1;
                if(jsonData.data[i].pcStatus == 'end' && jsonData.data[i].open == true) div1 = $("<div class='waiting'></div>");
                else if(jsonData.data[i].pcStatus == 'end' && jsonData.data[i].open == false) div1 = $("<div class='waiting dimmed'></div>");
                else if(jsonData.data[i].pcStatus == 'start' && jsonData.data[i].open == true) div1 = $("<div class='reading'></div>");
                else div1 = $("<div class='reading dimmed'></div>");

                let div2 = $("<div class='content'></div>");
                let div3 = $("<div class='content_status'></div>");
                let img1;
                if(jsonData.data[i].pcStatus == 'end') img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
                else img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
                let p1 = $("<p>"+status+"</p>")
                let p2 = $("<p id='xray"+i+"' class='reg_num' value="+i+">"+ip+"</p>")

                div3.append(img1).append(p1)
                div2.append(div3).append(p2);
                div1.append(div2).append(hidden1);
                $('.gr_xray').append(div1);
            }

            if(jsonData.data[i].pcStatus != $('#xray_status'+i).val()){
                $('#xray_status'+i).val(jsonData.data[i].pcStatus);
                if(jsonData.data[i].pcStatus == 'start'){
                    if($('#xray_div'+i).hasClass('waiting')){
                        $('#xray_div'+i).removeClass('waiting')
                        $('#xray_div'+i).addClass('reading')
                        $('#xray_div'+i).children().eq(1).children().children().eq(0).attr('src','/storg/img/reading_icon.png');
                        $('#xray_div'+i).children().eq(1).children().children().eq(1).text('START');
                    }
                } else{
                    if($('#xray_div'+i).hasClass('reading')){
                        $('#xray_div'+i).removeClass('reading')
                        $('#xray_div'+i).addClass('waiting')
                        $('#xray_div'+i).children().eq(1).children().children().eq(0).attr('src','/storg/img/waiting_icon.png');
                        $('#xray_div'+i).children().eq(1).children().children().eq(1).text('END');
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
    let host = "broker.emqx.io";
    let port = 8083
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
                let div3 = $("<div class='content_status'></div>");
                let img1;
                if(jsonData.data[i].pcStatus == 'end') img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>");
                else img1 = $("<img src='/storg/img/reading_icon.png' alt=''>");
                let p1 = $("<p>"+status+"</p>")
                let p2 = $("<p id='trs"+i+"' class='reg_num' value="+i+">"+ip+"</p>")

                div3.append(img1).append(p1)
                div2.append(div3).append(p2);
                div1.append(div2).append(hidden1);
                $('.gr_trs').append(div1);
            }

            if(jsonData.data[i].pcStatus != $('#trs_status'+i).val()){
                $('#trs_status'+i).val(jsonData.data[i].pcStatus);
                if(jsonData.data[i].pcStatus == 'start'){
                    if($('#trs_div'+i).hasClass('waiting')){
                        $('#trs_div'+i).removeClass('waiting')
                        $('#trs_div'+i).addClass('reading')
                        $('#trs_div'+i).children().eq(1).children().children().eq(0).attr('src','/storg/img/reading_icon.png');
                        $('#trs_div'+i).children().eq(1).children().children().eq(1).text('WAITING');

                    }
                } else{
                    if($('#trs_div'+i).hasClass('reading')){
                        $('#trs_div'+i).removeClass('reading')
                        $('#trs_div'+i).addClass('waiting')
                        $('#trs_div'+i).children().eq(1).children().children().eq(0).attr('src','/storg/img/waiting_icon.png');
                        $('#trs_div'+i).children().eq(1).children().children().eq(1).text('WORKING');
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
