$(document).ready(function() {
    const userAcc = sessionStorage.getItem('user');
    let userAccOjb = JSON.parse(userAcc)
    $("#username").val(userAccOjb.username)
    $("#email").val(userAccOjb.email)
    $("#fullName").val(userAccOjb.fullName)
    $("#avatar").val(userAccOjb.avatar)
    // $("#coverPhoto").val(userAccOjb.coverPhoto)
    $("#description").val(userAccOjb.description)
});
function back(){
    const userAcc = sessionStorage.getItem('user');
    let userAccOjb = JSON.parse(userAcc)
    // window.location.href = `time-line.html?userAccId=` + userAccOjb.id;
    window.location.href = `edit-profile-basic.html?userAccId=` + userAccOjb.id;
}

function updateUserAcc(){
    let userAccId =  JSON.parse(sessionStorage.getItem('user')).id
    let username = $("#username").val()
    let email = $("#email").val()
    let fullName = $("#fullName").val()
    let avatar = $("#avatar").val()
    // let coverPhoto = $("#coverPhoto").val()
    let description = $("#description").val()

    let userAcc = {
        username: username,
        email: email,
        fullName: fullName,
        avatar: avatar,
        // coverPhoto: coverPhoto,
        description: description
    }
    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        },
        url: "http://localhost:8080/editUserAcc/" + userAccId,
        data:JSON.stringify(userAcc),
        success: function (){
            // window.location.href = `time-line.html?userAccId=` + userAccId;
            window.location.href = `edit-profile-basic.html?userAccId=` + userAccId;
        },
        error: function (err){
            console.log("error")
        }
     })

}

