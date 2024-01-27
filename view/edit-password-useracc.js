$(document).ready(function (){
    let userAcc = sessionStorage.getItem('user');
    let userAccOjb = JSON.parse(userAcc)
    let userAccId = userAccOjb.id
    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        url: "http://localhost:8080/userAccDetail/" + userAccId,
        success: function (data) {
            $(".fullName").html(data.fullName);
           avatarUserAcc(data.avatar)

        },
        error: function (err) {
            console.log("loi")
        }
    })
})

function avatarUserAcc(avatar){
    let str = `
    								<img src="${avatar}" alt="">

    `
    $(".userAvatar1").html(str)
}
function back(){
    const userAcc = sessionStorage.getItem('user');
    let userAccOjb = JSON.parse(userAcc)
    window.location.href = `edit-profile-basic.html?userAccId=` + userAccOjb.id;
}

function changePass(){
    const userAcc = sessionStorage.getItem('user');
    let userAccOjb = JSON.parse(userAcc)
    let userAccId = userAccOjb.id
    let passwordOld = $("#passwordOld").val();
    let password = $("#password").val();
    let passwordNew = $("#passwordNew").val();
    var regex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$/;


    if ( passwordNew === "" ||  password === "" ||  passwordOld === "") {

        $("#checkNull").text("Data not null").show()
    } else {
        if (!regex.test(passwordNew)) {
            $("#regexNewPass").text("Require capital, number, special characters").show()
        } else {
            if (password !== passwordNew){
                $("#checkNewPass").text("Repassword not match").show()
            } else {
                if (passwordNew === passwordOld) {
                    $("#tb").text("Match password").show()
                } else {

                    $.ajax({
                        type: "POST",
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
                        },
                        url: "http://localhost:8080/editPassword/" + userAccId + "/" + passwordNew + "/" + passwordOld,

                        success: function () {
                            alert("Update successfully!")
                            window.location.href = `edit-profile-basic.html`
                        },
                        error: function (err) {
                            $("#tb").text("Incorect password")
                        }
                    })
                }
            }
        }
    }



}
function backhome(){
    window.location.href = `edit-profile-basic.html`
}


