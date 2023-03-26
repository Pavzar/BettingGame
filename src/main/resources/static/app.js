var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#results").html("");
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/betting', function (results) {
            showResults(JSON.parse(results.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendBet() {
    stompClient.send("/app/bet", {}, JSON.stringify(
        {'betAmount': $("#bet").val(), 'number': $("#number").val()}
    ));
}

function showResults(result) {
    $("#results").append("<tr><td>" + "Win = " + result.win + ", Payout: " + result.winAmount + ", Generated number: "
        + result.generatedNumber + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendBet();
    });
});
