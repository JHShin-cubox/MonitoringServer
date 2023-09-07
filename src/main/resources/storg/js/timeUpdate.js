function updateDateTime() {
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const date = now.getDate().toString().padStart(2, '0');
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');

    const formattedTime = `${year}년 ${month}월 ${date}일 ${hours}시 ${minutes}분 ${seconds}초`;
    document.querySelector('.status_tag').innerText = formattedTime;
}

// 페이지 로딩 시 한 번 실행 후, 매 초마다 업데이트
updateDateTime();
setInterval(updateDateTime, 1000);