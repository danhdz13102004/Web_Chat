<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet"
      href="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/css/dist/mdb5/standard/core.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
      crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
      integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
      crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
      integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
      crossorigin="anonymous"></script>
    <style>
      file-link {
        display: flex;
        align-items: center;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 300px;
        text-decoration: none;
        color: black;
        font-family: Arial, sans-serif;
        margin: 10px 0;
      }

      .file-icon {
        width: 40px;
        height: 40px;
        background: url('path-to-file-icon.png') no-repeat center center;
        background-size: contain;
        margin-right: 10px;
      }

      .file-details {
        display: flex;
        flex-direction: column;
      }

      .file-name {
        font-size: 16px;
        font-weight: bold;
      }

      .file-size {
        font-size: 14px;
        color: #666;
      }

      #recordingStatus {
        width: 100px;
        height: 30px;
        background-color: blue;
        color: white;
        text-align: center;
        display: none;
      }

      .recording {
        display: block;
      }

      .voice-record {
        top: 100px;
        right: 0;
        display: none;
        position: absolute;
        justify-content: space-between;
      }

      
    </style>
  </head>

  <body>
    <section>
      <div class="container py-5">

        <div class="row d-flex justify-content-center">
          <div class="col-md-10 col-lg-8 col-xl-6">

            <div class="card" id="chat2">
              <div class="card-header d-flex justify-content-between align-items-center p-3">
                <h5 class="mb-0">Chat</h5>
                <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-sm"
                  data-mdb-ripple-color="dark">Let's Chat
                  App</button>
              </div>
              <div class="card-body" data-mdb-perfect-scrollbar-init
                style="position: relative; height: 500px; overflow: auto;">

                <div class="d-flex flex-row justify-content-start">
                  <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                    alt="avatar 1" style="width: 45px; height: 100%;">
                  <div>
                    <p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">Hi</p>
                    <p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">How are you ...???
                    </p>
                    <p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">What are you doing
                      tomorrow? Can we come up a bar?</p>
                    <p class="small ms-3 mb-3 rounded-3 text-muted">23:58</p>
                  </div>
                </div>

                <div class="divider d-flex align-items-center mb-4">
                  <p class="text-center mx-3 mb-0" style="color: #a2aab7;">Today</p>
                </div>

                <div class="d-flex flex-row justify-content-end mb-4 pt-1">
                  <div>
                    <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">Hiii, I'm good.</p>
                    <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">How are you doing?</p>
                    <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">Long time no see! Tomorrow
                      office. will
                      be free on sunday.</p>
                    <p class="small me-3 mb-3 rounded-3 text-muted d-flex justify-content-end">00:06</p>
                  </div>
                  <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
                    alt="avatar 1" style="width: 45px; height: 100%;">
                </div>

                <div class="d-flex flex-row justify-content-start">
                  <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
                    alt="avatar 1" style="width: 45px; height: 100%;">
                  <div>
                    <a style="display: flex; text-decoration: none;" href="path-to-your-file/ONTAP.docx.pdf"
                      class="file-link" download>
                      <div class="file-icon">
                        <i class="fa-solid fa-file"></i>
                      </div>
                      <div class="file-details">
                        <span class="file-name">ONTAP.docx.pdf</span>
                        <span class="file-size">1.91 MB</span>
                      </div>
                    </a>
                  </div>
                </div>




              </div>
              <div style="margin-top: 20px; position: relative;"
                class="card-footer text-muted d-flex justify-content-start align-items-center p-3">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp" alt="avatar 3"
                  style="width: 40px; height: 100%;">
                <input type="text" class="form-control form-control-lg" id="exampleFormControlInput1"
                  placeholder="Type message">
                <input multiple class="selectFile" type="file" style="display: none;">
                <a onclick="openFile()" class="ms-1 text-muted" href="#!"><i class="fas fa-paperclip"></i>
                </a>
                <a onclick="showRecord()" class="ms-3 text-muted" href="#!"><i class="fa-solid fa-microphone"></i></a>
                <a onclick="sendMessage()" class="ms-3" href="#!"><i class="fas fa-paper-plane"></i></a>
                <div class="voice-record">
                  <i class="fa-solid fa-circle-xmark"></i>
	                <button id="startStopButton">Start Recording</button>
	                <div id="recordingStatus"></div>
	                <audio id="audioPlayback" controls></audio>
                  <i onclick="sendRecord()" class="fa-solid fa-paper-plane"></i>
                </div>
              </div>
            </div>

          </div>
        </div>

      </div>
    </section>
  </body>
  <image id="receivedImage" style="display: none;"></image>
  <script src="js/main.js" type="text/javascript">
      
</script>

  </html>