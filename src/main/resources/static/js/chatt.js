let ws;
const mid = getId('mid');
const btnLogin = getId('btnLogin');
const btnSend = getId('btnSend');
const talk = getId('talk');
const msg = getId('msg');

// 2022.10.26[프뚜]: 전송 데이터(JSON)
const data = {};

function getId(id) {
    return document.getElementById(id);
}

btnLogin.onclick = function() {
    // 2022.10.26[프뚜]: 서버와 webSocket 연결
    ws = new WebSocket("ws://" + location.host + "/chatt");

    // 2022.10.26[프뚜]: 서버에서 받은 메세지 처리
    ws.onmessage = function(msg) {
        const data = JSON.parse(msg.data);
        let css;

        if (data.mid === mid.value) {
            css = 'class=me';
        } else {
            css = 'class=other';
        }

        const item = `<div ${css} >
		                <span><b>${data.mid}</b></span> [ ${data.date} ]<br/>
                      <span>${data.msg}</span>
						</div>`;

        talk.innerHTML += item;

        // 2022.10.26[프뚜]: 스크롤바 하단으로 이동
        talk.scrollTop=talk.scrollHeight;
    }
}

msg.onkeyup = function(ev) {
    if (ev.keyCode === 13) {
        send();
    }
}

btnSend.onclick = function() {
    send();
}

function send() {
    if (msg.value.trim() !== '') {
        data.mid = getId('mid').value;
        data.msg = msg.value;
        data.date = new Date().toLocaleString();
        const temp = JSON.stringify(data);
        ws.send(temp);
    }

    msg.value = '';
}