//
// function login() { // login() {
//     let username = $("#username").val()
//     let password = $("#password").val()
//     let user = {
//         username: username,
//         password: password,
//     }
//     $.ajax({
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json'
//         },
//         type: "POST",
//         url: "http://localhost:8080/api/login",
//         data: JSON.stringify(user),
//         success: function (data) {
//             // sessionStorage.setItem("token", data.accessToken)
//             // window.location.href = "index.html"
//             sessionStorage.setItem("token", data.accessToken);
//             window.location.href = data.redirectUrl;
//         }
//     })
//     event.preventDefault();
// }
//
