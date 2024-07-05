var currentURL = window.location.protocol + "//" + window.location.host;
var websocket = null;
var main_chat = document.querySelector(".main-chat");
var id = document.querySelector('.id-user').value;
var index_message = 0;
const size_select = 10;
var status_load_add = 0;
var status_load_add_togr = 0;
var grId = 0;
var status_gr = false;

var listId = [];

if (id !== "") {
  websocket = new WebSocket('ws://' + window.location.host + '/chat/' + id);
  websocket.onopen = function (message) { processOpen(message); };
  websocket.onmessage = function (message) { processMessage(message); };
  websocket.onclose = function (message) { processClose(message); };
  websocket.onerror = function (message) { processError(message); };
}

var receiver = null;
var avatar = null;
var namefile = "none";

function parseToJson(message, type, grId = 0) {
  return {
    sender: id,
    message: message,
    type: type,
    receiver: receiver,
    groupId: grId,
    avatar: avatar,
    namefile: namefile,
    listId: listId
  };
}


function processOpen(message) {

}
function processMessage(message) {
  console.log(message.data);
  var item = JSON.parse(message.data);

  if (item.type === "group") {
    var html = `<li onclick="loadCoversation('${item.groupId}','gr')" class="p-2 border-bottom"><a style="text-decoration: none;" href="#!"
                        class="d-flex justify-content-between">
                          <div class="d-flex flex-row">
                            <div>
                              <img
                                src="${item.avatar}"
                                alt="avatar" class="d-flex align-self-center me-3 avatar-${item.groupId}"
                                width="60"> <span
                                class="badge bg-success badge-dot"></span>
                            </div>
                            <div class="pt-1">
                              <p class="fw-bold mb-0 name-${item.groupId}">${item.namefile}</p>
                              <p class="small text-muted message-of-${item.groupId}">${item.message}</p>
                            </div>
                          </div>
                      </a></li>`;
    console.log(html);
    document.querySelector(".list-unstyled").innerHTML = html + document.querySelector(".list-unstyled").innerHTML;
    return;
  }
  else if (item.type === "online") {
    document.querySelector(`.status-${item.sender}`).classList.add("online");
  }
  else if (item.type === "offline") {
    document.querySelector(`.status-${item.sender}`).classList.remove("online");
  }
  else if(item.type === "accept") {
    var check = document.querySelector(`.user-chat${item.id_receiver}`) === undefined;
    console.log(check);
    if(true) {
      var html = ``;
      if (item.namefile == "true") {
        html += `<li onclick="loadCoversation('${item.sender}')" class="p-2 border-bottom user-chat${item.sender}"><a style="text-decoration: none;" href="#!"
                        class="d-flex justify-content-between">
                          <div class="d-flex flex-row">
                            <div>
                              <img
                                src="${item.avatar}"
                                alt="avatar" class="d-flex align-self-center me-3 avatar-${item.sender}"
                                width="60"> <span
                                class="badge bg-success badge-dot "></span>
                            </div>
                            <div class="pt-1 small-text">
                              <p class="fw-bold mb-0 name-${item.sender}">${item.message}</p>
                              <p class="small text-muted message-of-${item.sender}">Let's start new conversation</p>
                            </div>
                            <div style="display: flex; align-items: center; ">
                              <div class="status-${item.sender} status-activity online" ></div>
                          </div>
                          </div>
                      </a></li>`;
      }
      else {
        html += `<li onclick="loadCoversation('${item.id_sender}')" class="p-2 border-bottom user-chat${item.id_sender}"><a style="text-decoration: none;" href="#!"
                        class="d-flex justify-content-between">
                          <div class="d-flex flex-row">
                            <div>
                              <img
                                src="${item.avatar}"
                                alt="avatar" class="d-flex align-self-center me-3 avatar-${item.id_sender}"
                                width="60"> <span
                                class="badge bg-success badge-dot "></span>
                            </div>
                            <div class="pt-1 small-text">
                              <p class="fw-bold mb-0 name-${item.id_sender}">${item.message}</p>
                              <p class="small text-muted message-of-${item.id}">Let's start new conversation</p>
                            </div>
                            <div style="display: flex; align-items: center; ">
                              <div class="status-${item.id_sender} status-activity online" ></div>
                          </div>
                          </div>
                      </a></li>`;
      }
      document.querySelector(".list-unstyled").innerHTML += html;
    }
  }
  else {
    var date = new Date(item.time);
    item.time = date;
    var s = date.toDateString();
    console.log("date: " + s);
    var html = "";
    if (item.sender === id) {
      var msg = document.querySelector(`.message-of-${item.receiver}`);
      if (item.type === "text") {
        html += `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">${item.message}</p>
                <p class="small me-3 mb-3 rounded-3 text-muted d-flex justify-content-end">${item.time}</p>
              </div>
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
        msg.innerText = `You: ${item.message}`;
      }
      else if (item.type === "image") {
        html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
        <div>
          <img src="${item.message}"
          alt="avatar 1" style="width: auto; height: 250px;">
        </div>
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
      </div>`;
      msg.innerText = `You sent an image `;
      }
      else if (item.type === "video") {
        html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
        <div>
          <video width="400" height="360" controls>
            <source src="${item.message}" type="video/mp4">
        </video>
        </div>
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
      </div>`;
      msg.innerText = `You sent a video`;
      }
      else if (item.type === "doc") {
        html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
        <div>
        <a style="display: flex; text-decoration: none;" href="${item.message}" class="file-link" download>
            <div class="file-icon">
              <i class="fa-solid fa-file"></i>  
            </div>
            <div class="file-details">
                <span class="file-name">${item.namefile}</span>
            </div>
          </a>
        </div>
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
      </div>`;
      msg.innerText = `You sent a file`;
      }
      else if (item.type === "audio") {
        html = `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
        <div>
          <audio controls>
  <source src="${item.message}" type="audio/wav">
  </audio>
        </div>
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
      </div>`;
      msg.innerText = `You sent an audio`;
      }
    }
    else {
      var msg = document.querySelector(`.message-of-${item.sender}`);
      if (item.type === "text") {
        html += `<div class="d-flex flex-row justify-content-start">
      <img src="${item.avatar}"
        alt="avatar 1" style="width: 45px; height: 100%;">
      <div>
        <p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">${item.message}</p>
        <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
      </div>
    </div>`;
    msg.innerText = `${item.message}`;
      }
      else if (item.type === "image") {
        html = `<div class="d-flex flex-row justify-content-start">
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
          <div>
          <img src="${item.message}" style="height:250px ; width: auto;">
          <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>  
        </div>
      </div>`;
      msg.innerText = `User sent an image`;
      }
      else if (item.type === "video") {
        html = `<div class="d-flex flex-row justify-content-start">
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
        <div>
          <video width="400" height="360" controls>
            <source src="${item.message}" type="video/mp4">
            <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
        </video>
        </div>
      </div>`;
      msg.innerText = `User sent a video`;
      }
      else if (item.type === "doc") {
        html = `<div class="d-flex flex-row justify-content-start">
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
        <div>
          <a style="display: flex; text-decoration: none;" href="${item.message}" class="file-link" download>
            <div class="file-icon">
              <i class="fa-solid fa-file"></i>  
            </div>
            <div class="file-details">
                <span class="file-name">${item.namefile}</span>
            </div>
            <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
          </a>
        </div>
      </div>`;
      msg.innerText = `User sent a file`;
      }
      else if (item.type === "audio") {
        html = `<div class="d-flex flex-row justify-content-start">
        <img src="${item.avatar}"
          alt="avatar 1" style="width: 45px; height: 100%;">
        <div>
          <audio controls>
  <source src="${item.message}" type="audio/wav">
  </audio>
        </div>
      </div>`;
      msg.innerText = `User sent an audio`;
      }

    }
    main_chat.innerHTML += html;
    main_chat.scrollTop = main_chat.scrollHeight;
  }


}
function processClose(message) {
}
function processError(message) {
  console.log("error: ");
  console.log(message);
  console.log(message.data);
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

    // document.querySelector('.main-chat').innerHTML += html;
    console.log("state", websocket.readyState);
    if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
      if (text.value.length > 0) {
        var message = parseToJson(text.value, "text",grId);
        console.log(message);
        websocket.send(JSON.stringify(message));
        // websocket.send("text message");
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
      namefile = fileInput.files[i].name;
    }
    var message = parseToJson("none", "none",grId);
    console.log(message);
    websocket.send(JSON.stringify(message));
    websocket.send(fileInput.files[i]);
    namefile = "none";
  }

  fileInput.value = "";


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
    var message = parseToJson("none", "audio",grId);
    websocket.send(JSON.stringify(message));
    websocket.send(currentRecord);
    currentRecord = null;
  }
  closeRecord();
}


function loadAllUser() {
  var url = currentURL + `/api/friend/selectFriend?id=` + id;
  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Lỗi khi lấy dữ liệu từ URL");
      }
      return response.json();
    })
    .then(data => {
      var html = "";
      data.forEach(item => {
        console.log(item.id, id);
        if (item.id_sender !== id) {
          if (item.isonline == true) {
            html += `<li onclick="loadCoversation('${item.id_sender}')" class="p-2 border-bottom user-chat${item.id}"><a style="text-decoration: none;" href="#!"
                            class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="${item.avatar_sender}"
                                    alt="avatar" class="d-flex align-self-center me-3 avatar-${item.id_sender}"
                                    width="60"> <span
                                    class="badge bg-success badge-dot "></span>
                                </div>
                                <div class="pt-1 small-text">
                                  <p class="fw-bold mb-0 name-${item.id_sender}">${item.name_sender}</p>
                                  <p class="small text-muted message-of-${item.id_sender}">${item.lastmessage}</p>
                                </div>
                                <div style="display: flex; align-items: center; ">
															    <div class="status-${item.id} status-activity online" ></div>
														  </div>
                              </div>
                          </a></li>`;
          }
          else {
            html += `<li onclick="loadCoversation('${item.id}')" class="p-2 border-bottom user-chat${item.id}"><a style="text-decoration: none;" href="#!"
            class="d-flex justify-content-between">
              <div class="d-flex flex-row">
                <div>
                  <img
                    src="${item.avatar}"
                    alt="avatar" class="d-flex align-self-center me-3 avatar-${item.id}"
                    width="60"> <span
                    class="badge bg-success badge-dot "></span>
                </div>
                <div class="pt-1 small-text">
                  <p class="fw-bold mb-0 name-${item.id}">${item.fullname}</p>
                  <p class="small text-muted message-of-${item.id}">${item.lastmessage}</p>
                </div>
                <div style="display: flex; align-items: center; ">
                  <div class="status-${item.id} status-activity" ></div>
              </div>
              </div>
          </a></li>`;
          }
        }
      })

      document.querySelector(".list-unstyled").innerHTML += html;
    })
}

function loadAllFriend() {
  var url = currentURL + "/api/friend/selectFriend?id=" + id;
  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Lỗi khi lấy dữ liệu từ URL");
      }
      return response.json();
    })
    .then(data => {
      var html = "";
      data.forEach(item => {
        console.log(item.id, id);
        var check = document.querySelector(`.user-chat${item.id}`) == null;
        console.log(check);
        if (item.id !== id && check) {
          if (item.isonline == true) {
            html += `<li onclick="loadCoversation('${item.id}')" class="p-2 border-bottom user-chat${item.id}"><a style="text-decoration: none;" href="#!"
                            class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                    src="${item.avatar}"
                                    alt="avatar" class="d-flex align-self-center me-3 avatar-${item.id}"
                                    width="60"> <span
                                    class="badge bg-success badge-dot "></span>
                                </div>
                                <div class="pt-1 small-text">
                                  <p class="fw-bold mb-0 name-${item.id}">${item.fullname}</p>
                                  <p class="small text-muted message-of-${item.id}">${item.lastmessage}</p>
                                </div>
                                <div style="display: flex; align-items: center; ">
															    <div class="status-${item.id} status-activity online" ></div>
														  </div>
                              </div>
                          </a></li>`;
          }
          else {
            html += `<li onclick="loadCoversation('${item.id}')" class="p-2 border-bottom user-chat${item.id}"><a style="text-decoration: none;" href="#!"
            class="d-flex justify-content-between">
              <div class="d-flex flex-row">
                <div>
                  <img
                    src="${item.avatar}"
                    alt="avatar" class="d-flex align-self-center me-3 avatar-${item.id}"
                    width="60"> <span
                    class="badge bg-success badge-dot "></span>
                </div>
                <div class="pt-1 small-text">
                  <p class="fw-bold mb-0 name-${item.id}">${item.fullname}</p>
                  <p class="small text-muted message-of-${item.id}">${item.lastmessage}</p>
                </div>
                <div style="display: flex; align-items: center; ">
                  <div class="status-${item.id} status-activity" ></div>
              </div>
              </div>
          </a></li>`;
          }
        }
      })

      document.querySelector(".list-unstyled").innerHTML += html;
    })
}


function loadGroup() {
  var url = currentURL + `/api/user/selectGroupOfUser?id=${id}`;
  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Lỗi khi lấy dữ liệu từ URL");
      }
      return response.json();
    })
    .then(data => {
      var html = "";
      data.forEach(item => {
        console.log(item.id, id);
        if (item.id !== id) {
          html += `<li onclick="loadCoversation('${item.id}','gr')" class="p-2 border-bottom"><a style="text-decoration: none;" href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="${item.avatar}"
																	alt="avatar" class="d-flex align-self-center me-3 avatar-${item.id}"
																	width="60"> <span
																	class="badge bg-success badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0 name-${item.id}">${item.name}</p>
																<p class="small text-muted message-of-${item.id}">A user have just created this group</p>
															</div>
														</div>
												</a></li>`;
        }
      })

      document.querySelector(".list-unstyled").innerHTML += html;

    })
}

// loadAllUser();
loadAllFriend();
loadGroup();

function loadCoversation(id1, text = "none") {
  var ava = document.querySelector(".main-avatar");
  ava.src = document.querySelector(`.avatar-${id1}`).src;
  document.querySelector(".main-name").innerText = document.querySelector(`.name-${id1}`).innerText;
  if (text === "none" || text === "gr") {
    index_message = 0;
    grId = 0;
  }

  status_gr = false;
  receiver = id1;
  avatar = document.querySelector('.your-img').src;
  var url = currentURL + `/api/user/selectConversation?id1=${id}&id2=${id1}&page=${index_message}&size=${size_select}`;
  if (text === "gr" || text === "gr_other") {
    status_gr = true;
    url = currentURL + `/api/user/selectGroupChatCoversation?id=${id1}&page=${index_message}&size=${size_select}`;
    grId = id1;
  }
  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Lỗi khi lấy dữ liệu từ URL");
      }
      return response.json();
    })
    .then(data => {
      var html = '';
      data.forEach(item => {

        if (item.sender === id) {

          if (item.type === "text") {
            html += `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
                    <div>
                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">${item.content}</p>
                      <p class="small me-3 mb-3 rounded-3 text-muted d-flex justify-content-end">${item.time}</p>
                    </div>
                    <img src="${item.avatar}"
                      alt="avatar 1" style="width: 45px; height: 100%;">
                  </div>`;
          }
          else if (item.type === "image") {
            html += `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <img src="${item.content}"
                alt="avatar 1" style="width: auto; height: 250px;">
              </div>
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
          }
          else if (item.type === "video") {
            html += `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <video width="400" height="360" controls>
                  <source src="${item.content}" type="video/mp4">
              </video>
              </div>
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
          }
          else if (item.type === "doc") {
            html += `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
              <a style="display: flex; text-decoration: none;" href="${item.content}" class="file-link" download>
                  <div class="file-icon">
                    <i class="fa-solid fa-file"></i>  
                  </div>
                  <div class="file-details">
                      <span class="file-name">${item.name}</span>
                  </div>
                </a>
              </div>
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
          }
          else if (item.type === "audio") {
            html += `<div class="d-flex flex-row justify-content-end mb-4 pt-1">
              <div>
                <audio controls>
        <source src="${item.content}" type="audio/wav">
    </audio>
              </div>
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
            </div>`;
          }
        }
        else {

          if (item.type === "text") {
            html += `<div class="d-flex flex-row justify-content-start">
            <img src="${item.avatar}"
              alt="avatar 1" style="width: 45px; height: 100%;">
            <div>
              <p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">${item.content}</p>
              <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
            </div>
          </div>`;
          }
          else if (item.type === "image") {
            html += `<div class="d-flex flex-row justify-content-start">
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
                <div>
                <img src="${item.content}" style="height:250px ; width: auto;">
                <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
              </div>
            </div>`;
          }
          else if (item.type === "video") {
            html += `<div class="d-flex flex-row justify-content-start">
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <video width="400" height="360" controls>
                  <source src="${item.content}" type="video/mp4">
                  </video>
                  <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
              </div>
            </div>`;
          }
          else if (item.type === "doc") {
            html += `<div class="d-flex flex-row justify-content-start">
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <a style="display: flex; text-decoration: none;" href="${item.content}" class="file-link" download>
                  <div class="file-icon">
                    <i class="fa-solid fa-file"></i>  
                  </div>
                  <div class="file-details">
                      <span class="file-name">${item.name}</span>
                  </div>
                  </a>
                  <p class="small ms-3 mb-3 rounded-3 text-muted">${item.time}</p>
                  </div>
            </div>`;
          }
          else if (item.type === "audio") {
            html += `<div class="d-flex flex-row justify-content-start">
              <img src="${item.avatar}"
                alt="avatar 1" style="width: 45px; height: 100%;">
              <div>
                <audio controls>
        <source src="${item.content}" type="audio/wav">
    </audio>
              </div>
            </div>`;
          }

        }
      })
      if (html !== "") {
        if (index_message > 0) {
          var old = main_chat.scrollHeight;
          main_chat.innerHTML = html + main_chat.innerHTML;
          var newsize = main_chat.scrollHeight;
          main_chat.scrollTop = newsize - old;
          console.log(newsize, old);
        }
        else {
          main_chat.innerHTML = html;
          main_chat.scrollTop = main_chat.scrollHeight;
        }
        index_message++;
      }
      if(html === "" && index_message === 0) {
        main_chat.innerHTML = html;
      }

    })

  if (status_gr) {
    document.querySelector('.add-member').style.display = 'flex';
  }
  else {
    document.querySelector('.add-member').style.display = 'none';
  }
}





main_chat.addEventListener('scroll', function () {
  if (main_chat.scrollTop === 0) {
    console.log("full rồi");
    if (status_gr) {
      loadCoversation(grId, "gr_other");
    }
    else {
      loadCoversation(receiver, "other");
    }
  }
});

document.querySelector(".add-group").addEventListener('click', () => {
  document.querySelector('.form-chat').style.display = 'none';
  document.querySelector('.form-contain').style.display = 'block';
  if (status_load_add === 0) {
    loadAllUserToAdd();
    status_load_add = 1;
  }
});



document.querySelector(".btn-cancel").addEventListener('click', () => {
  document.querySelector('.form-contain').style.display = 'none';
  document.querySelector('.form-chat').style.display = 'block';

});

document.querySelector(".btn-cancel1").addEventListener('click', () => {
  document.querySelector('.form-contain').style.display = 'none';
  document.querySelector('.form-add-togr').style.display = 'none';
  document.querySelector('.form-chat').style.display = 'block';
});


document.querySelector(".btn-create").addEventListener('click', () => {
  console.log("create group");
  grId = -1;
  var message = parseToJson(document.querySelector('.name-group').value, "group", -1);
  websocket.send(JSON.stringify(message));
  var file = document.querySelector(".inp-avatar").files[0];
  console.log(file);
  if (file !== undefined) {
    websocket.send(file);
  }
  document.querySelector('.form-contain').style.display = 'none';
  document.querySelector('.form-chat').style.display = 'block';
  listId = [];
});

document.querySelector(".btn-add-to-gr").addEventListener('click', () => {
  var message = parseToJson("none", "add", grId);
  websocket.send(JSON.stringify(message));
  document.querySelector(".btn-cancel1").click();
  listId = [];
});





function loadAllUserToAdd() {
  var url = currentURL + "/api/user/selectAll";
  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Lỗi khi lấy dữ liệu từ URL");
      }
      return response.json();
    })
    .then(data => {
      var html = "";
      data.forEach(item => {
        console.log(item.id, id);
        if (item.id !== id) {
          html += `<li  class="list-group-item"><input onchange="addId(this,'${item.id}')"
													class="form-check-input me-1" type="checkbox" value=""
													id="firstCheckbox"> <img
													src="${item.avatar}"
													alt="avatar 1" style="width: 45px; height: 100%;"> <label
													class="form-check-label" for="firstCheckbox">${item.fullname}</label></li>`;
        }
      })

      document.querySelector(".list-group").innerHTML = html;
    })
}

function addId(element, id) {
  console.log(element);
  if (element.checked) {
    console.log("đã check");
    listId.push(id);
    // this.checked = true;
  }
  else {
    console.log("chưa check");
    listId = listId.filter(item => item !== id);
    // this.checked = false;
  }

  console.log(element.checked);
  console.log(listId);
}

document.querySelector(".add-member").addEventListener('click', () => {
  document.querySelector('.form-contain').style.display = 'none';
  document.querySelector('.form-chat').style.display = 'none';
  if (grId !== 0 && status_load_add_togr === 0) {
    status_load_add_togr = 1;
    var url = currentURL + `/api/user/selectUserToAdd?id=${grId}`;
    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error("Lỗi khi lấy dữ liệu từ URL");
        }
        return response.json();
      })
      .then(data => {
        var html = "";
        data.forEach(item => {
          html += `<li  class="list-group-item"><input onchange="addId(this,'${item.id}')"
													class="form-check-input me-1" type="checkbox" value=""
													id="firstCheckbox"> <img
													src="${item.avatar}"
													alt="avatar 1" style="width: 45px; height: 100%;"> <label
													class="form-check-label" for="firstCheckbox">${item.fullname}</label></li>`;
        })
        document.querySelector('.list-gr-add').innerHTML = html;

      })
  }
  document.querySelector('.form-add-togr').style.display = 'block';
})

function loadFriendToSendRequest() {
    var name = document.querySelector('.inp-search').value;
    var url = currentURL + `/api/user/selectUserToSendRequest?id=${id}&name=${encodeURIComponent(name)}`;
    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error("Lỗi khi lấy dữ liệu từ URL");
        }
        return response.json();
      })
      .then(data => {
        var html = "";
        data.forEach(item => {
          html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.fullname}</label>
															<div onclick="sendRequestFriend(this,'${item.id}')" style="border: 1px #000000 solid; cursor: pointer;">
																<svg style="width: 24px; padding: 4px;" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M11.5003 12H5.41872M5.24634 12.7972L4.24158 15.7986C3.69128 17.4424 3.41613 18.2643 3.61359 18.7704C3.78506 19.21 4.15335 19.5432 4.6078 19.6701C5.13111 19.8161 5.92151 19.4604 7.50231 18.7491L17.6367 14.1886C19.1797 13.4942 19.9512 13.1471 20.1896 12.6648C20.3968 12.2458 20.3968 11.7541 20.1896 11.3351C19.9512 10.8529 19.1797 10.5057 17.6367 9.81135L7.48483 5.24303C5.90879 4.53382 5.12078 4.17921 4.59799 4.32468C4.14397 4.45101 3.77572 4.78336 3.60365 5.22209C3.40551 5.72728 3.67772 6.54741 4.22215 8.18767L5.24829 11.2793C5.34179 11.561 5.38855 11.7019 5.407 11.8459C5.42338 11.9738 5.42321 12.1032 5.40651 12.231C5.38768 12.375 5.34057 12.5157 5.24634 12.7972Z" stroke="#000000" stroke-width="1.176" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
																<span>Send request</span>
															</div>
														</li>`;
          
        })

        document.querySelector('.list-fr-add').innerHTML = html;
        document.querySelector(`.form-add-fr`).style.display = 'block';
        document.querySelector('.form-chat').style.display = 'none';
      })
}


document.querySelector(".btn-exit").addEventListener("click",() => {
  document.querySelector(`.form-add-fr`).style.display = 'none';
        document.querySelector('.form-chat').style.display = 'block';
        loadAllFriend();
})

function sendRequestFriend(element,id_to) {
  var url  = currentURL + `/api/friend/sendNewRequest?id1=${id}&id2=${id_to}`;
  fetch(url);
  element.innerHTML = `<svg style="width: 24px; padding: 4px;" viewBox="-6.4 -6.4 76.80 76.80" data-name="Layer 1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" fill="#000000" stroke="#000000" stroke-width="0.00064" transform="matrix(1, 0, 0, 1, 0, 0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="0.256"></g><g id="SVGRepo_iconCarrier"><defs><style>.cls-1{fill:#00ff33;}.cls-2{fill:#00ff33;}</style></defs><title></title><path class="cls-1" d="M28.46,42.29A2,2,0,0,1,27,41.71l-9.5-9.5a2,2,0,0,1,2.83-2.83l8.09,8.09L43.63,22.29a2,2,0,1,1,2.83,2.83L29.87,41.71A2,2,0,0,1,28.46,42.29Z"></path><path class="cls-2" d="M32,60A28,28,0,1,1,60,30.47a2,2,0,0,1-1.88,2.11A2,2,0,0,1,56,30.69,24,24,0,1,0,39.64,54.75,23.86,23.86,0,0,0,53.58,42.51a2,2,0,1,1,3.59,1.75A27.78,27.78,0,0,1,40.91,58.55,28.14,28.14,0,0,1,32,60Z"></path></g></svg>	
																<span >Sent</span>`;
}

function showHistoryFriend() {
  var url = currentURL + `/api/friend/selectHistoryFriend?id=${id}`;
  fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error("Lỗi khi lấy dữ liệu từ URL");
        }
        return response.json();
      })
      .then(data => {
        var html = "";
        data.forEach(item => {
          if(item.id_sender !== id) {
            if(item.status == "Đã xác nhận") {
              html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar_sender}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.name_sender}</label>
															<div>
																<svg style="width: 24px;" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" stroke-width="3" stroke="#22bf34" fill="none"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="22.83" cy="22.57" r="7.51"></circle><path d="M38,49.94a15.2,15.2,0,0,0-15.21-15.2h0a15.2,15.2,0,0,0-15.2,15.2Z"></path><circle cx="44.13" cy="27.22" r="6.05"></circle><path d="M42.4,49.94h14A12.24,12.24,0,0,0,44.13,37.7h0a12.21,12.21,0,0,0-5.75,1.43"></path></g></svg>
																<span >Friend</span>
															</div>
														</li>`;
            }
            else if(item.status === "Đã hủy") {
              html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar_sender}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.name_sender}</label>
															<div>
																<svg style="width: 24px;" viewBox="-46.08 -46.08 604.16 604.16" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#b32929" stroke="#b32929"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>error</title> <g id="Page-1" stroke-width="0.00512" fill="none" fill-rule="evenodd"> <g id="add" fill="#ba1717" transform="translate(42.666667, 42.666667)"> <path d="M213.333333,3.55271368e-14 C331.136,3.55271368e-14 426.666667,95.5306667 426.666667,213.333333 C426.666667,331.136 331.136,426.666667 213.333333,426.666667 C95.5306667,426.666667 3.55271368e-14,331.136 3.55271368e-14,213.333333 C3.55271368e-14,95.5306667 95.5306667,3.55271368e-14 213.333333,3.55271368e-14 Z M213.333333,42.6666667 C119.232,42.6666667 42.6666667,119.232 42.6666667,213.333333 C42.6666667,307.434667 119.232,384 213.333333,384 C307.434667,384 384,307.434667 384,213.333333 C384,119.232 307.434667,42.6666667 213.333333,42.6666667 Z M262.250667,134.250667 L292.416,164.416 L243.498667,213.333333 L292.416,262.250667 L262.250667,292.416 L213.333333,243.498667 L164.416,292.416 L134.250667,262.250667 L183.168,213.333333 L134.250667,164.416 L164.416,134.250667 L213.333333,183.168 L262.250667,134.250667 Z" id="error"> </path> </g> </g> </g></svg>
																<span>Refused</span>
															</div>
														</li>`;
            }
            else {
              html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar_sender}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.name_sender}</label>
															<div class="div-req-${item.id_sender}">
																<button onclick="changeRequest('ref','${item.id_sender}')" type="button" class="btn btn-danger btn-cancel1">Refuse</button>
																<button onclick="changeRequest('cof','${item.id_sender}')" type="button" class="btn btn-success btn-add-to-gr">Confirm</button>
															</div>
														</li>`;
            }
          }
          else {
            if(item.status == "Đã xác nhận") {
              html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar_receiver}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.name_receiver}</label>
															<div>
																<svg style="width: 24px;" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" stroke-width="3" stroke="#22bf34" fill="none"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="22.83" cy="22.57" r="7.51"></circle><path d="M38,49.94a15.2,15.2,0,0,0-15.21-15.2h0a15.2,15.2,0,0,0-15.2,15.2Z"></path><circle cx="44.13" cy="27.22" r="6.05"></circle><path d="M42.4,49.94h14A12.24,12.24,0,0,0,44.13,37.7h0a12.21,12.21,0,0,0-5.75,1.43"></path></g></svg>
																<span >Friend</span>
															</div>
														</li>`;
            }
            else if(item.status === "Đã hủy") {
              html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar_receiver}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.name_receiver}</label>
															<div>
																<svg style="width: 24px;" viewBox="-46.08 -46.08 604.16 604.16" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#b32929" stroke="#b32929"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>error</title> <g id="Page-1" stroke-width="0.00512" fill="none" fill-rule="evenodd"> <g id="add" fill="#ba1717" transform="translate(42.666667, 42.666667)"> <path d="M213.333333,3.55271368e-14 C331.136,3.55271368e-14 426.666667,95.5306667 426.666667,213.333333 C426.666667,331.136 331.136,426.666667 213.333333,426.666667 C95.5306667,426.666667 3.55271368e-14,331.136 3.55271368e-14,213.333333 C3.55271368e-14,95.5306667 95.5306667,3.55271368e-14 213.333333,3.55271368e-14 Z M213.333333,42.6666667 C119.232,42.6666667 42.6666667,119.232 42.6666667,213.333333 C42.6666667,307.434667 119.232,384 213.333333,384 C307.434667,384 384,307.434667 384,213.333333 C384,119.232 307.434667,42.6666667 213.333333,42.6666667 Z M262.250667,134.250667 L292.416,164.416 L243.498667,213.333333 L292.416,262.250667 L262.250667,292.416 L213.333333,243.498667 L164.416,292.416 L134.250667,262.250667 L183.168,213.333333 L134.250667,164.416 L164.416,134.250667 L213.333333,183.168 L262.250667,134.250667 Z" id="error"> </path> </g> </g> </g></svg>
																<span>Refused</span>
															</div>
														</li>`;
            }
            else {
              html += `<li class="list-group-item li-add-fr"> <img
															src="${item.avatar_receiver}"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">${item.name_receiver}</label>
															<div>
																<svg style="width: 24px; padding: 4px;" viewBox="-6.4 -6.4 76.80 76.80" data-name="Layer 1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" fill="#000000" stroke="#000000" stroke-width="0.00064" transform="matrix(1, 0, 0, 1, 0, 0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="0.256"></g><g id="SVGRepo_iconCarrier"><defs><style>.cls-1{fill:#00ff33;}.cls-2{fill:#00ff33;}</style></defs><title></title><path class="cls-1" d="M28.46,42.29A2,2,0,0,1,27,41.71l-9.5-9.5a2,2,0,0,1,2.83-2.83l8.09,8.09L43.63,22.29a2,2,0,1,1,2.83,2.83L29.87,41.71A2,2,0,0,1,28.46,42.29Z"></path><path class="cls-2" d="M32,60A28,28,0,1,1,60,30.47a2,2,0,0,1-1.88,2.11A2,2,0,0,1,56,30.69,24,24,0,1,0,39.64,54.75,23.86,23.86,0,0,0,53.58,42.51a2,2,0,1,1,3.59,1.75A27.78,27.78,0,0,1,40.91,58.55,28.14,28.14,0,0,1,32,60Z"></path></g></svg>	
																<span >Sent</span>
															</div>
														</li>`;
            }
          }
        })
        document.querySelector(".list-fr-add").innerHTML = html;
      })

      document.querySelector('.form-chat').style.display = 'none';
      document.querySelector(".form-add-fr").style.display = 'block';
}

function changeRequest(type,id2) {
  var url ;
  if(type == "ref") {
      url = currentURL + `/api/friend/updateStatusFriend?id1=${id}&id2=${id2}&status=refuse`;
      document.querySelector(`.div-req-${id2}`).innerHTML = `<svg style="width: 24px;" viewBox="-46.08 -46.08 604.16 604.16" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#b32929" stroke="#b32929"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>error</title> <g id="Page-1" stroke-width="0.00512" fill="none" fill-rule="evenodd"> <g id="add" fill="#ba1717" transform="translate(42.666667, 42.666667)"> <path d="M213.333333,3.55271368e-14 C331.136,3.55271368e-14 426.666667,95.5306667 426.666667,213.333333 C426.666667,331.136 331.136,426.666667 213.333333,426.666667 C95.5306667,426.666667 3.55271368e-14,331.136 3.55271368e-14,213.333333 C3.55271368e-14,95.5306667 95.5306667,3.55271368e-14 213.333333,3.55271368e-14 Z M213.333333,42.6666667 C119.232,42.6666667 42.6666667,119.232 42.6666667,213.333333 C42.6666667,307.434667 119.232,384 213.333333,384 C307.434667,384 384,307.434667 384,213.333333 C384,119.232 307.434667,42.6666667 213.333333,42.6666667 Z M262.250667,134.250667 L292.416,164.416 L243.498667,213.333333 L292.416,262.250667 L262.250667,292.416 L213.333333,243.498667 L164.416,292.416 L134.250667,262.250667 L183.168,213.333333 L134.250667,164.416 L164.416,134.250667 L213.333333,183.168 L262.250667,134.250667 Z" id="error"> </path> </g> </g> </g></svg>
																<span>Refused</span>`;
  }
  else {
    url = currentURL + `/api/friend/updateStatusFriend?id1=${id}&id2=${id2}&status=confirm`;
    document.querySelector(`.div-req-${id2}`).innerHTML = `<svg style="width: 24px;" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" stroke-width="3" stroke="#22bf34" fill="none"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="22.83" cy="22.57" r="7.51"></circle><path d="M38,49.94a15.2,15.2,0,0,0-15.21-15.2h0a15.2,15.2,0,0,0-15.2,15.2Z"></path><circle cx="44.13" cy="27.22" r="6.05"></circle><path d="M42.4,49.94h14A12.24,12.24,0,0,0,44.13,37.7h0a12.21,12.21,0,0,0-5.75,1.43"></path></g></svg>
																<span >Friend</span>`;
    receiver = id2;

    var message = parseToJson("none","accept");
    websocket.send(JSON.stringify(message));
  }
  fetch(url);

}