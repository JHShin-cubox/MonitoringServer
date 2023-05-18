function pcMqtt() {
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

// 추가되기 전에 이미 생성된 li 요소들을 모두 삭제합니다.
        let jsonData = JSON.parse(message.payloadString);
        let count = jsonData.data.length;
        $('.gr-viewer').empty();
        if(count == 0){

        }else{
            for(let i=0; i<count; i++){
                let idx = jsonData.data[i].idx; // idx로 html id mapping
                let ip = jsonData.data[i].ip; // idx로 html id mapping
                let status = jsonData.data[i].pcStatus; // idx로 html id mapping
                let li = $("<li class='d-inline-block'></li>");
                let div1
                if(jsonData.data[i].open == true){
                    div1 = $("<div class='viewer px-1'></div>");
                } else { div1 = $("<div class='viewer px-1 nonActive'></div>"); }
                let div2 = $("<div class='dashItem' onclick='moveRecord("+idx+")'></div>")
                let div3 = $("<div class='statusImg'></div>")
                let i1;
                if(jsonData.data[i].pcStatus == 'waiting'){
                    i1 = $("<i class='fa-solid fa-pause'></i>")
                } else{
                    i1 = $("<i class='fa-solid fa-cog fa-spin'></i>")
                }
                let div4 = $("<div class='dashDesc'></div>")
                let p1 = $("<p>"+ip+"</p>")
                let p2 = $("<p>"+status+"</p>")
                div4.append(p1).append(p2);
                div3.append(i1);
                div2.append(div3).append(div4);
                div1.append(div2);
                li.append(div1);
                $('.gr-viewer').append(li);

                //  $('#status' + idx).text(jsonData.data[i].pcStatus); //pcStuts 상태값 입력
                //  $('#pcStat' + idx).val(jsonData.data[i].open); //pcStuts 상태값 입력
                //  $('#dashOpenStatus' + idx).val(jsonData.data[i].pcStatus); //해당 input태그 value에 open 상태값 입력
                //  //컨베이어 벨트 이미지 변경 기능
                // if($('#dashOpenStatus' + idx).val() == 'reading'){ // pcStatus 컬럼의 상태값이 'reading'일때
                //     $('#dashOpenStatus' + idx).next().children().attr('class','fa-solid fa-cog fa-spin')
                // } else{
                //     $('#dashOpenStatus' + idx).next().children().attr('class','fa-solid fa-pause')
                // }
                //  // Health Check 상태 표시 기능
                //  if($('#pcStat' + idx).val() == 'true'){
                //      $('#xrayline' + idx).removeClass('nonActive');
                //  } else{
                //      $('#xrayline' + idx).addClass('nonActive');
                //  }
            }
        }
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
        $('.gr-xray').empty();
        for(let i=0; i<count; i++){
            // let idx = jsonData.data[i].idx; // idx로 html id mapping
            // $('#status' + idx).text(jsonData.data[i].pcStatus); //pcStuts 상태값 입력
            // $('#pcStat' + idx).val(jsonData.data[i].open); //pcStuts 상태값 입력
            // $('#dashOpenStatus' + idx).val(jsonData.data[i].pcStatus); //해당 input태그 value에 open 상태값 입력
            // //컨베이어 벨트 이미지 변경 기능
            // if($('#dashOpenStatus' + idx).val() == 'end'){ // pcStatus 컬럼의 상태값이 'reading'일때
            //     $('#dashOpenStatus' + idx).next().children().attr('src','/static/img/conveyorBelt_no.png')
            // } else{
            //     $('#dashOpenStatus' + idx).next().children().attr('src','/static/img/conveyorBelt.png')
            // }
            // // Health Check 상태 표시 기능
            // if($('#pcStat' + idx).val() == 'true'){
            //     $('#xrayline' + idx).removeClass('nonActive');
            // } else{
            //     $('#xrayline' + idx).addClass('nonActive');
            // }

            let idx = jsonData.data[i].idx; // idx로 html id mapping
            let ip = jsonData.data[i].ip; // idx로 html id mapping
            let status = jsonData.data[i].pcStatus; // idx로 html id mapping
            let li = $("<li class='d-inline-block'></li>");
            let div1
            if(jsonData.data[i].open == true){
                div1 = $("<div class='viewer px-1'></div>");
            } else { div1 = $("<div class='viewer px-1 nonActive'></div>"); }
            let div2 = $("<div class='dashItem' onclick='moveRecord("+idx+")'></div>")
            let div3 = $("<div class='statusImg'></div>")
            let img1;
            if(jsonData.data[i].pcStatus == 'end'){
                img1 = $("<img src='/static/img/conveyorBelt_no.png'>")
            } else{
                img1 = $("<img src='/static/img/conveyorBelt.png'>")
            }
            let div4 = $("<div class='dashDesc'></div>")
            let p1 = $("<p>"+ip+"</p>")
            let p2 = $("<p>"+status+"</p>")
            div4.append(p1).append(p2);
            div3.append(img1);
            div2.append(div3).append(div4);
            div1.append(div2);
            li.append(div1);
            $('.gr-xray').append(li);
        }

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
