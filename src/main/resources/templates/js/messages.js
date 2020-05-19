let message = document.getElementById('1');

message.onmousedown = function(event) {

    if (event.target !== document.getElementById("cross") && event.target !== document.getElementById("send")) {


        let shiftX = event.clientX - message.getBoundingClientRect().left;
        let shiftY = event.clientY - message.getBoundingClientRect().top;

        message.style.position = 'absolute';
        message.style.zIndex = 1000;
        document.body.append(message);

        moveAt(event.pageX, event.pageY);

        function moveAt(pageX, pageY) {
            message.style.left = pageX - shiftX + 'px';
            message.style.top = pageY - shiftY + 'px';
        }

        function onMouseMove(event) {
            moveAt(event.pageX, event.pageY);
        }

        document.addEventListener('mousemove', onMouseMove);

        message.onmouseup = function () {
            document.removeEventListener('mousemove', onMouseMove);
            message.onmouseup = null;
        };

    }

};

message.ondragstart = function() {
    return false;
};








