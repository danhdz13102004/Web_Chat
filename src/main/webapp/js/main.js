
var websocket = null;

var id = document.querySelector('.id-user');
if(id !== "") {
  websocket = new WebSocket("ws://localhost:8080/server");
  websocket.onopen = function (message) { processOpen(message); };
  websocket.onmessage = function (message) { processMessage(message); };
  websocket.onclose = function (message) { processClose(message); };
  websocket.onerror = function (message) { processError(message); };
}



function processOpen(message) {

}
function processMessage(message) {
  console.log(message.data);
  if (typeof message.data === "string") {
    var html;
    if (message.data.startsWith("yrself")) {
      const startIndex = message.data.indexOf("\\Web_chat");

      // Lấy phần của chuỗi từ startIndex đến hết
      const result = message.data.substring(startIndex);
      html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <img src="${result}"
                alt="avatar 1" style="width: auto; height: 250px;">
              </div>
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
    }
    else if (message.data.startsWith("video_sent ")) {
      const startIndex = message.data.indexOf("\\Web_chat");
      const result = message.data.substring(startIndex);
      html = `<div class="d-flex flex-row justify-content-start">
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <video width="400" height="360" controls>
                  <source src="${result}" type="video/mp4">
              </video>
              </div>
            </div>`;
    }
    else if (message.data.startsWith("yrvideo ")) {
      const startIndex = message.data.indexOf("\\Web_chat");
      const result = message.data.substring(startIndex);
      html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <video width="400" height="360" controls>
                  <source src="${result}" type="video/mp4">
              </video>
              </div>
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
    }
    else if (message.data.startsWith("yrdoc ")) {
      const startIndex = message.data.indexOf("\\Web_chat");
      const result = message.data.substring(startIndex);
      const realName = result.replace("\\Web_chat\\image\\", "");
      html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
              <a style="display: flex; text-decoration: none;" href="${result}" class="file-link" download>
                  <div class="file-icon">
                    <i class="fa-solid fa-file"></i>  
                  </div>
                  <div class="file-details">
                      <span class="file-name">${realName}</span>
                  </div>
                </a>
              </div>
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;

    }
    else if (message.data.startsWith("docsent ")) {
      const startIndex = message.data.indexOf("\\Web_chat");
      const result = message.data.substring(startIndex);
      const realName = result.replace("\\Web_chat\\image\\", "");
      html = `<div class="d-flex flex-row justify-content-start">
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <a style="display: flex; text-decoration: none;" href="${result}" class="file-link" download>
                  <div class="file-icon">
                    <i class="fa-solid fa-file"></i>  
                  </div>
                  <div class="file-details">
                      <span class="file-name">${realName}</span>
                      <span class="file-size">1.91 MB</span>
                  </div>
                </a>
              </div>
            </div>`;
    }
    else if (message.data.startsWith("audiosent ")) {
      const startIndex = message.data.indexOf("\\Web_chat");
      const result = message.data.substring(startIndex);
      html = `<div class="d-flex flex-row justify-content-start">
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <audio controls>
        <source src="${result}" type="audio/wav">
    </audio>
              </div>
            </div>`;
    }
    else if (message.data.startsWith("yraudio ")) {
      const startIndex = message.data.indexOf("\\Web_chat");
      const result = message.data.substring(startIndex);
      html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <audio controls>
        <source src="${result}" type="audio/wav">
    </audio>
              </div>
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
    }
    else {
      var html = `<div class="d-flex flex-row justify-content-start">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
            alt="avatar 1" style="width: 45px; height: 100%;">
          <div>
            <p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">` + message.data + `</p>
            <p class="small ms-3 mb-3 rounded-3 text-muted">23:58</p>
          </div>
        </div>`;
    }
    document.querySelector('.main-chat').innerHTML += html;
  }
  else {
    var blob = new Blob([message.data], { type: "image/jpeg" });
    var url = URL.createObjectURL(blob);
    var html = `<div class="d-flex flex-row justify-content-start">
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <img src="${url}" style="height:250px ; width: auto;">
              </div>
            </div>`;
    document.querySelector('.main-chat').innerHTML += html;
  }
}
function processClose(message) {
}
function processError(message) {
}
function sendMessage() {
  var text = document.querySelector('#exampleFormControlInput1');
  if (text.value.length > 0) {
    var html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
                  <div>
                    <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">${text.value}</p>
                  </div>
                  <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                    alt="avatar 1" style="width: 45px; height: 100%;">
                </div>`;

    document.querySelector('.main-chat').innerHTML += html;
    if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
      console.log(text.value);
      console.log(text.value.length);
      if (text.value.length > 0) {
        websocket.send(text.value);
        text.value = "";
      }
    }
  }

  var fileInput = document.querySelector('.selectFile');
  // var file = fileInput.files[0];
  // console.log(fileInput);
  // console.log(file);
  for (var i = 0; i < fileInput.files.length; i++) {
    console.log(fileInput.files[i].name);
    console.log(fileInput.files[i].type);
    if (fileInput.files[i].type === "application/pdf" || fileInput.files[i].type === "application/vnd.openxmlformats-officedocument.wordprocessingml.document" || fileInput.files[i].type === "text/plain") {
      websocket.send("nameoffile " + fileInput.files[i].name);
    }
    websocket.send(fileInput.files[i]);
  }


}

function openFile() {
  document.querySelector('.selectFile').click();
}

const startStopButton = document.getElementById('startStopButton');
const recordingStatus = document.getElementById('recordingStatus');
const audioPlayback = document.getElementById('audioPlayback');
let mediaRecorder;
let audioChunks = [];
let currentRecord = null;

startStopButton.addEventListener('click', () => {
  if (mediaRecorder && mediaRecorder.state === 'recording') {
    mediaRecorder.stop();
    startStopButton.textContent = 'Start Recording';
    recordingStatus.classList.remove('recording');
  } else {
    navigator.mediaDevices.getUserMedia({ audio: true })
      .then(stream => {
        mediaRecorder = new MediaRecorder(stream);
        mediaRecorder.start();
        startStopButton.textContent = 'Stop Recording';
        recordingStatus.classList.add('recording');
        recordingStatus.textContent = 'Recording...';

        mediaRecorder.ondataavailable = event => {
          audioChunks.push(event.data);
        };

        mediaRecorder.onstop = () => {
          const audioBlob = new Blob(audioChunks, { type: 'audio/wav' });
          currentRecord = audioBlob;
          const audioUrl = URL.createObjectURL(audioBlob);
          audioPlayback.src = audioUrl;
          audioChunks = [];
        };
      })
      .catch(error => {
        console.error('Error accessing microphone:', error);
      });
  }
});

function showRecord() {
  document.querySelector('.voice-record').style.display = 'flex';
}
function closeRecord() {
  document.querySelector('.voice-record').style.display = 'none';
}


function sendRecord() {
  if (currentRecord != null) {
    websocket.send(currentRecord);
    currentRecord = null;
  }
  closeRecord();
}