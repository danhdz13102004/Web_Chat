<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" id="roboto-subset.css-css"
			href="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/css/mdb5/fonts/roboto-subset.css?ver=3.9.0-update.5"
			type="text/css" media="all">
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
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
			integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
			crossorigin="anonymous" referrerpolicy="no-referrer" />
		<link rel="stylesheet" href="/css/main.css">
		<style>
			[data-mdb-theme="dark"] #mdb-navbar {
				background-color: var(--mdb-body-bg) !important;
			}

			#mdb-navbar {
				padding-left: 240px;
				height: 58px;
			}

			#mdb-navbar-brand-logo {
				display: none;
			}

			#sidenav-toggler {
				display: none;
			}

			[data-mdb-theme="dark"] #mdb-5-search-dropdown {
				background-color: #404040 !important;
				color: #Fafafa;

				# mdb-5-search-list li a : hover,
				#mdb-5-search-list li a : active,
				#mdb-5-search-list li a : focus {
					background-color: #fff2;
					outline: none;
				}

				.dropdown-item-pseudo-focus {
					background-color: #fff2 !important;
					box-shadow: none !important;
				}

			}

			.dropdown-item-pseudo-focus {
				background-color: rgba(0, 0, 0, 0.05) !important;
				box-shadow: none !important;
			}

			#mdb-5-search-container {
				position: relative;
				font-size: .8rem;
			}

			#mdb-5-search-dropdown {
				display: none;
				position: absolute;
				top: 36px;
				overflow-y: hidden;
				width: 100%;
				z-index: 999999;
				background-color: white;
			}

			.mdb-5-search-keywords {
				max-width: 100%;
				white-space: nowrap;
				text-overflow: ellipsis;
				overflow: hidden;
			}

			#mdb-5-search-list {
				overflow-y: scroll;
				max-height: 265px;
				position: relative;
			}

			#mdb-5-search-list li a {
				transition: background-color .3s ease-in;
			}

			#mdb-5-search-list li a:hover,
			#mdb-5-search-list li a:active,
			#mdb-5-search-list li a:focus {
				background-color: rgba(0, 0, 0, 0.05);
				outline: none;
			}

			#mdb-5-search-input {
				min-width: 230px;
			}

			#mdb-5-search-icon {
				transition: color .3s ease-out;
			}

			@media (max-width : 1440px) {
				#sidenav-toggler {
					display: inline-block;
				}
			}

			@media (max-width : 1440px) {
				#mdb-navbar {
					padding-left: 0;
				}
			}

			#chat3 .form-control {
				border-color: transparent;
			}

			#chat3 .form-control:focus {
				border-color: transparent;
				box-shadow: inset 0px 0px 0px 1px transparent;
			}

			.badge-dot {
				border-radius: 50%;
				height: 10px;
				width: 10px;
				margin-left: 2.9rem;
				margin-top: -.75rem;
			}

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
				width: 400px;
				align-items: center;
				right: 0;
				display: none;
				position: absolute;
				justify-content: space-between;
			}

			/* CSS cho toàn bộ thanh cuộn */
			div::-webkit-scrollbar {
				width: 12px;
				/* Chiều rộng của thanh cuộn dọc */
				height: 12px;
				/* Chiều cao của thanh cuộn ngang */
			}

			/* CSS cho thanh cuộn (thumb) */
			div::-webkit-scrollbar-thumb {
				background-color: #888;
				/* Màu của thanh cuộn */
				border-radius: 10px;
				/* Bo tròn các góc của thanh cuộn */
				border: 2px solid #ccc;
				/* Đường viền xung quanh thanh cuộn */
			}

			/* CSS cho track (đường chạy của thanh cuộn) */
			div::-webkit-scrollbar-track {
				background: #f1f1f1;
				/* Màu nền của đường chạy */
				border-radius: 10px;
				/* Bo tròn các góc của đường chạy */
				border: 1px solid #ccc;
				/* Đường viền xung quanh đường chạy */
			}
		</style>
	</head>

	<body>
		<input class="id-user" type="hidden" value="${sessionScope.user.id != NULL ? sessionScope.user.id : '' }">
		<section style="background-color: #CDC4F9;">
			<div class="container py-5">

				<div class="row">
					<div class="col-md-12">

						<div class="card" id="chat3" style="border-radius: 15px;">
							<div class="card-body">

								<div class="row">
									<div class="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0">

										<div class="p-3 form-chat">

											<div class="input-group rounded mb-3">
												<input type="search" class="form-control rounded inp-search" placeholder="Search"
													aria-label="Search" aria-describedby="search-addon" /> <span onclick="loadFriendToSendRequest()"
													class="input-group-text border-0" id="search-addon">
													<i class="fas fa-search"></i>
												</span> <span style="margin-left: 8px;"
													class="input-group-text border-0" id="search-addon">
													<i class="fa-solid fa-people-group add-group"></i>
												</span>
												<span onclick="showHistoryFriend()" style="margin-left: 8px;"
													class="input-group-text border-0" id="search-addon">
													<svg style="width: 20px;" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" stroke-width="3" stroke="#000000" fill="none"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="29.22" cy="16.28" r="11.14"></circle><path d="M41.32,35.69c-2.69-1.95-8.34-3.25-12.1-3.25h0A22.55,22.55,0,0,0,6.67,55h29.9"></path><circle cx="45.38" cy="46.92" r="11.94"></circle><line x1="45.98" y1="39.8" x2="45.98" y2="53.8"></line><line x1="38.98" y1="46.8" x2="52.98" y2="46.8"></line></g></svg>
												</span>
											</div>

											<div data-mdb-perfect-scrollbar-init
												style="position: relative; height: 600px; overflow: auto;">
												<ul style="overflow: auto;" class="list-unstyled mb-0">
													 <!-- <li class="p-2 border-bottom"><a href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
																	alt="avatar" class="d-flex align-self-center me-3"
																	width="60"> <span
																	class="badge bg-success badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0">Marie Horwitz</p>
																<p class="small text-muted">Hello, Are you there?</p>
															</div>
														</div>

														<div style="display: flex; align-items: center;">
															<div style="width: 20px; height: 20px; background-color: #565050; border-radius: 50%; "></div>
														</div>
												</a></li>
												<li class="p-2 border-bottom"><a href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava2-bg.webp"
																	alt="avatar" class="d-flex align-self-center me-3"
																	width="60"> <span
																	class="badge bg-warning badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0">Alexa Chung</p>
																<p class="small text-muted">Lorem ipsum dolor sit.</p>
															</div>
														</div>
														<div style="display: flex; align-items: center;">
															<div class="status-activity" style="width: 12px; height: 12px; background-color: #565050; border-radius: 50%; "></div>
														</div>
												</a></li>
												<li class="p-2 border-bottom"><a href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
																	alt="avatar" class="d-flex align-self-center me-3"
																	width="60"> <span
																	class="badge bg-success badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0">Danny McChain</p>
																<p class="small text-muted">Lorem ipsum dolor sit.</p>
															</div>
														</div>
														<div class="pt-1">
															<p class="small text-muted mb-1">Yesterday</p>
														</div>
												</a></li>
												<li class="p-2 border-bottom"><a href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
																	alt="avatar" class="d-flex align-self-center me-3"
																	width="60"> <span
																	class="badge bg-danger badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0">Ashley Olsen</p>
																<p class="small text-muted">Lorem ipsum dolor sit.</p>
															</div>
														</div>
														<div class="pt-1">
															<p class="small text-muted mb-1">Yesterday</p>
														</div>
												</a></li>
												<li class="p-2 border-bottom"><a href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava5-bg.webp"
																	alt="avatar" class="d-flex align-self-center me-3"
																	width="60"> <span
																	class="badge bg-warning badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0">Kate Moss</p>
																<p class="small text-muted">Lorem ipsum dolor sit.</p>
															</div>
														</div>
														<div class="pt-1">
															<p class="small text-muted mb-1">Yesterday</p>
														</div>
												</a></li>
												<li class="p-2"><a href="#!"
													class="d-flex justify-content-between">
														<div class="d-flex flex-row">
															<div>
																<img
																	src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
																	alt="avatar" class="d-flex align-self-center me-3"
																	width="60"> <span
																	class="badge bg-success badge-dot"></span>
															</div>
															<div class="pt-1">
																<p class="fw-bold mb-0">Ben Smith</p>
																<p class="small text-muted">Lorem ipsum dolor sit.</p>
															</div>
														</div>
														<div class="pt-1">
															<p class="small text-muted mb-1">Yesterday</p>
														</div>
												</a></li>  -->
												</ul>
											</div>

										</div>
										<div class="form-contain">
											<div class="form-add-group">
												<div class="input-group input-group-sm mb-3">
													<span class="input-group-text" id="inputGroup-sizing-sm">Name</span>
													<input class="name-group" type="text" style="padding: 0 82px; ">
												</div>
												<div class="input-group mb-3">
													<label class="input-group-text" for="inputGroupFile01">Choose
														Avatar</label> <input type="file"
														class="form-control inp-avatar" id="inputGroupFile01">
												</div>
												<ul class="list-group">
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="firstCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">First
															checkbox</label></li>
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="secondCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="secondCheckbox">Second
															checkbox</label></li>
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="thirdCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="thirdCheckbox">Third
															checkbox</label></li>
												</ul>

												<div class="div-btn">
													<button type="button" class="btn btn-danger btn-cancel">Hủy
														bỏ</button>
													<button type="button" class="btn btn-success btn-create">Tạo
														mới</button>
												</div>
											</div>
										</div>
										<div class="form-contain form-add-togr">
											<div class="form-add-group">
												<ul class="list-group list-gr-add">
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="firstCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">First
															checkbox</label></li>
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="secondCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="secondCheckbox">Second
															checkbox</label></li>
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="thirdCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="thirdCheckbox">Third
															checkbox</label></li>
												</ul>

												<div class="div-btn">
													<button type="button" class="btn btn-danger btn-cancel1">Hủy
														bỏ</button>
													<button type="button" class="btn btn-success btn-add-to-gr">Thêm mới</button>
												</div>
											</div>
										</div>
										<div class="form-contain form-add-fr">
											<div class="form-add-group">
												<ul class="list-group list-fr-add">
													<li class="list-group-item li-add-fr"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">First
															checkbox</label>
															<div style="border: 1px #000000 solid;">
																<svg style="width: 24px; padding: 4px;" viewBox="-6.4 -6.4 76.80 76.80" data-name="Layer 1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" fill="#000000" stroke="#000000" stroke-width="0.00064" transform="matrix(1, 0, 0, 1, 0, 0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="0.256"></g><g id="SVGRepo_iconCarrier"><defs><style>.cls-1{fill:#00ff33;}.cls-2{fill:#00ff33;}</style></defs><title></title><path class="cls-1" d="M28.46,42.29A2,2,0,0,1,27,41.71l-9.5-9.5a2,2,0,0,1,2.83-2.83l8.09,8.09L43.63,22.29a2,2,0,1,1,2.83,2.83L29.87,41.71A2,2,0,0,1,28.46,42.29Z"></path><path class="cls-2" d="M32,60A28,28,0,1,1,60,30.47a2,2,0,0,1-1.88,2.11A2,2,0,0,1,56,30.69,24,24,0,1,0,39.64,54.75,23.86,23.86,0,0,0,53.58,42.51a2,2,0,1,1,3.59,1.75A27.78,27.78,0,0,1,40.91,58.55,28.14,28.14,0,0,1,32,60Z"></path></g></svg>	
																<span >Sent</span>
															</div>
														</li>
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="secondCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="secondCheckbox">Second
															checkbox</label></li>
													<li class="list-group-item"><input class="form-check-input me-1"
															type="checkbox" value="" id="thirdCheckbox"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="thirdCheckbox">Third
															checkbox</label></li>
												</ul>

												<div class="div-btn">
													<button type="button" class="btn btn-danger btn-exit">Thoát</button>
												</div>
											</div>
										</div>


										<div class="form-contain form-list-fr">
											<div class="form-add-group">
												<ul class="list-group list-all-fr">
													<li class="list-group-item li-add-fr"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">First
															checkbox</label>
															<div>
																<svg style="width: 24px;" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" stroke-width="3" stroke="#22bf34" fill="none"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="22.83" cy="22.57" r="7.51"></circle><path d="M38,49.94a15.2,15.2,0,0,0-15.21-15.2h0a15.2,15.2,0,0,0-15.2,15.2Z"></path><circle cx="44.13" cy="27.22" r="6.05"></circle><path d="M42.4,49.94h14A12.24,12.24,0,0,0,44.13,37.7h0a12.21,12.21,0,0,0-5.75,1.43"></path></g></svg>
																<span >Friend</span>
															</div>
														</li>
														<li class="list-group-item li-add-fr"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">First
															checkbox</label>
															<div>
																<svg style="width: 24px;" viewBox="-46.08 -46.08 604.16 604.16" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#b32929" stroke="#b32929"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>error</title> <g id="Page-1" stroke-width="0.00512" fill="none" fill-rule="evenodd"> <g id="add" fill="#ba1717" transform="translate(42.666667, 42.666667)"> <path d="M213.333333,3.55271368e-14 C331.136,3.55271368e-14 426.666667,95.5306667 426.666667,213.333333 C426.666667,331.136 331.136,426.666667 213.333333,426.666667 C95.5306667,426.666667 3.55271368e-14,331.136 3.55271368e-14,213.333333 C3.55271368e-14,95.5306667 95.5306667,3.55271368e-14 213.333333,3.55271368e-14 Z M213.333333,42.6666667 C119.232,42.6666667 42.6666667,119.232 42.6666667,213.333333 C42.6666667,307.434667 119.232,384 213.333333,384 C307.434667,384 384,307.434667 384,213.333333 C384,119.232 307.434667,42.6666667 213.333333,42.6666667 Z M262.250667,134.250667 L292.416,164.416 L243.498667,213.333333 L292.416,262.250667 L262.250667,292.416 L213.333333,243.498667 L164.416,292.416 L134.250667,262.250667 L183.168,213.333333 L134.250667,164.416 L164.416,134.250667 L213.333333,183.168 L262.250667,134.250667 Z" id="error"> </path> </g> </g> </g></svg>
																<span>Refused</span>
															</div>
														</li>
														<li class="list-group-item li-add-fr"> <img
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;"> <label
															class="form-check-label" for="firstCheckbox">First
															checkbox</label>
															<div>
																<button type="button" class="btn btn-danger btn-cancel1">Refuse</button>
																<button type="button" class="btn btn-success btn-add-to-gr">Confirm</button>
															</div>
														</li>
												</ul>

												<div class="div-btn">
													<button type="button" class="btn btn-danger btn-exit">Thoát</button>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-6 col-lg-7 col-xl-8">
										<div class="">

											<div class="card " id="chat2">
												<div
													class="card-header d-flex justify-content-between align-items-center p-3">
													<div style="display: flex; align-items: center;">
														<img class="main-avatar"
															src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;">
														<div class="add-member"
															style="padding-left: 24px; cursor: pointer; align-items: center;">
															<i class="fa-solid fa-plus"></i>
															<span>Add Member</span>
														</div>
													</div>
													<h3 class="main-name" style="font-size: 20px; color: #0d6efd;">Ngô
														Văn Danh</h3>
												</div>
												<div class="card-body main-chat" data-mdb-perfect-scrollbar-init
													style="position: relative; height: 500px; overflow: auto;">

													<div class="d-flex flex-row justify-content-start">
														<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;">
														<div>
															<p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">Hi
															</p>
															<p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">
																How
																are you ...???</p>
															<p class="small p-2 ms-3 mb-1 rounded-3 bg-body-tertiary">
																What
																are you doing tomorrow? Can we come up a bar?</p>
															<p class="small ms-3 mb-3 rounded-3 text-muted">23:58</p>
														</div>
													</div>

													<div class="divider d-flex align-items-center mb-4">
														<p class="text-center mx-3 mb-0" style="color: #a2aab7;">Today
														</p>
													</div>

													<div class="d-flex flex-row justify-content-end mb-4 pt-1">
														<div>
															<p
																class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">
																Hiii,
																I'm good.</p>
															<p
																class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">
																How
																are you doing?</p>
															<p
																class="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">
																Long
																time no see! Tomorrow office. will be free on sunday.
															</p>
															<p
																class="small me-3 mb-3 rounded-3 text-muted d-flex justify-content-end">
																00:06</p>
														</div>
														<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava4-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;">
													</div>

													<div class="d-flex flex-row justify-content-start">
														<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3-bg.webp"
															alt="avatar 1" style="width: 45px; height: 100%;">
														<div>
															<a style="display: flex; text-decoration: none;"
																href="path-to-your-file/ONTAP.docx.pdf"
																class="file-link" download>
																<div class="file-icon">
																	<i class="fa-solid fa-file"></i>
																</div>
																<div class="file-details">
																	<span class="file-name">ONTAP.docx.pdf</span> <span
																		class="file-size">1.91 MB</span>
																</div>
															</a>
														</div>
													</div>




												</div>
												<div style="margin-top: 20px; position: relative;"
													class="card-footer text-muted d-flex justify-content-start align-items-center p-3">
													<img class="your-img"
														src="${sessionScope.user.avatar != NULL ? sessionScope.user.avatar : '' }"
														alt="avatar 3" style="width: 40px; height: 100%;"> <input
														type="text" class="form-control form-control-lg"
														id="exampleFormControlInput1" placeholder="Type message">
													<input multiple class="selectFile" type="file"
														style="display: none;"> <a onclick="openFile()"
														class="ms-1 text-muted" href="#!"><i
															class="fas fa-paperclip"></i> </a> <a onclick="showRecord()"
														class="ms-3 text-muted" href="#!"><i
															class="fa-solid fa-microphone"></i></a> <a
														onclick="sendMessage()" class="ms-3" href="#!"><i
															class="fas fa-paper-plane"></i></a>
													<div class="voice-record">
														<i onclick="closeRecord()" class="fa-solid fa-circle-xmark"></i>
														<button id="startStopButton">Start Recording</button>
														<div id="recordingStatus"></div>
														<audio id="audioPlayback" controls></audio>
														<i onclick="sendRecord()" class="fa-solid fa-paper-plane"></i>
													</div>
												</div>
											</div>

										</div>


									</div>

									<!-- 									<div
										class="text-muted d-flex justify-content-start align-items-center pe-3 pt-3 mt-2">
										<img
											src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
											alt="avatar 3" style="width: 40px; height: 100%;"> <input
											type="text" class="form-control form-control-lg"
											id="exampleFormControlInput2" placeholder="Type message">
										<a class="ms-1 text-muted" href="#!"><i
											class="fas fa-paperclip"></i></a> <a class="ms-3 text-muted"
											href="#!"><i class="fas fa-smile"></i></a> <a class="ms-3"
											href="#!"><i class="fas fa-paper-plane"></i></a>
									</div> -->

								</div>
							</div>

						</div>
					</div>

				</div>
			</div>

			</div>
		</section>



		<script src="/js/web-chat.js" type="text/javascript" defer>
		</script>
	</body>

	</html>