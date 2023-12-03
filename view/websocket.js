// Tạo kết nối WebSocket
// Kết nối tới WebSocket endpoint
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function() {
    // stompClient.subscribe('/user/topic/friendRequests', function(message) {
    //     const notification = JSON.parse(message.body);
    //     alert(notification);
    // });
});
