var orderID;
function scanThisOrder() {
    console.log(orderID);
    window.location.href = "/sell/seller/order/detail?orderId="+orderID;
}

var websocket = null;
if('WebSocket' in window) {
    websocket = new WebSocket('ws://47.106.97.19:8080/sell/webSocket');
}else {
    alert('该浏览器不支持websocket!');
}

websocket.onopen = function (event) {
    console.log('建立连接');
};

websocket.onclose = function (event) {
    console.log('连接关闭');
};

websocket.onmessage = function (event) {
    var code = event.data.substr(0,3);
    orderID = event.data.substr(3,19);
    // console.log('标识:' + code + '订单号：' + orderID);
    if (code=='NEW'){
        //弹窗提醒, 播放音乐
        $('#noticeMsg').text("你有新的订单！");
        $('#myModal_notice').modal('show');
        document.getElementById('new').play();
        console.log(orderID);
        setTimeout('location.href="/sell/seller/order/detail?orderId="+orderID', 3000);
    }else if (code=='CNL') {
        //弹窗提醒, 播放音乐
        $('#noticeMsg').text("有用户申请取消订单！");
        $('#myModal_notice').modal('show');
        document.getElementById('cancel').play();
        console.log(orderID);
        setTimeout('location.href="/sell/seller/order/detail?orderId="+orderID', 3000);
    }else if(code=='URG'){
        //弹窗提醒, 播放音乐
        $('#noticeMsg').text("这个订单的客户等不及啦！快快上菜吧！");
        $('#myModal_notice').modal('show');
        document.getElementById('urge').play();
        console.log(orderID);
        setTimeout('location.href="/sell/seller/order/detail?orderId="+orderID', 3000);
    }
};

websocket.onerror = function () {
    alert('websocket通信发生错误！');
};

window.onbeforeunload = function () {
    websocket.close();
};