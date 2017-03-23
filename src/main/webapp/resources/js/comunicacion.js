
var webSocket = new WebSocket(
        'ws://localhost:8080/AisvWebSocket/despacho');

webSocket.onerror = function (event) {
    onError(event)
};

webSocket.onopen = function (event) {
    onOpen(event)
};

webSocket.onmessage = function (event) {
    onMessage(event)
};

function onMessage(event) {
    recibirNotificacion([{name: 'msg', value: event.data}]);
}

function onOpen(event) {
//    document.getElementById('messages').innerHTML = 'Now Connection established';
}

function onError(event) {
    alert(event.data);
}


