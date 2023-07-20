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
                let div1 = $("<div class='viewer'></div>");
                let div2;
                if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == true){
                    div2 = $("<div class='content_info waiting_status'></div>")
                }else if(jsonData.data[i].pcStatus == 'waiting' && jsonData.data[i].open == false){
                    div2 = $("<div class='content_info waiting_status dimmed'></div>")
                }else if(jsonData.data[i].pcStatus == 'reading' && jsonData.data[i].open == true){
                    div2 = $("<div class='content_info reading_status'></div>")
                }else{
                    div2 = $("<div class='content_info reading_status dimmed'></div>")
                }
                let div3 = $("<div class='status'></div>")
                let div3_1 = $("<div class='status_wrapper'></div>")
                let p1 = $("<p class='status_tittle'>"+status+"</p>")
                let p2 = $("<p class='status_num'>"+ip+"</p>")

                let div4 = $("<div class='status_icon'></div>")
                let img1;
                if(jsonData.data[i].pcStatus == 'waiting'){
                    img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>")
                } else {
                    img1 = $("<img src='/storg/img/reading_icon.png' alt=''>")
                }

                div4.append(img1)
                div3_1.append(p1).append(p2)
                div3.append(div3_1);
                div2.append(div3).append(div4)
                div1.append(div2);
                li.append(div1);
                $('.gr-viewer').append(li);
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


            let idx = jsonData.data[i].idx; // idx로 html id mapping
            let ip = jsonData.data[i].ip; // idx로 html id mapping
            let status = jsonData.data[i].pcStatus; // idx로 html id mapping
            let li = $("<li class='d-inline-block'></li>");
            let div1 = $("<div class='viewer'></div>");
            let div2;
            if(jsonData.data[i].pcStatus == 'end' && jsonData.data[i].open == true){
                div2 = $("<div class='content_info waiting_status'></div>")
            }else if(jsonData.data[i].pcStatus == 'end' && jsonData.data[i].open == false){
                div2 = $("<div class='content_info waiting_status dimmed'></div>")
            }else if(jsonData.data[i].pcStatus == 'start' && jsonData.data[i].open == true){
                div2 = $("<div class='content_info reading_status'></div>")
            }else{
                div2 = $("<div class='content_info reading_status dimmed'></div>")
            }
            let div3 = $("<div class='status'></div>")
            let div3_1 = $("<div class='status_wrapper'></div>")
            let p1 = $("<p class='status_tittle'>"+status+"</p>")
            let p2 = $("<p class='status_num'>"+ip+"</p>")

            let div4 = $("<div class='status_icon'></div>")
            let img1;
            if(jsonData.data[i].pcStatus == 'end'){
                img1 = $("<img src='/storg/img/waiting_icon.png' alt=''>")
            } else {
                img1 = $("<img src='/storg/img/reading_icon.png' alt=''>")
            }

            div4.append(img1)
            div3_1.append(p1).append(p2)
            div3.append(div3_1);
            div2.append(div3).append(div4)
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
