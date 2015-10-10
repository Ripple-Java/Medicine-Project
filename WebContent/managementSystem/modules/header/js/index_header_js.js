function userExitFunction() {//用户退出
    $.get("http://112.74.131.194:8080/MedicineProject/Web/admin/loginOut",
        function (data) {
            $(".header_userFunction").css("display", "none");
            alert( "退出成功！");
            document.cookie == "";
            location.href = "./backgroundLogin.html";
        }).
        error(function (data) {
            alert("error");
        });
}
function header_user_Click() {//header用户单击效果
    if ($(".header_userFunction").css("display") == "block") {
        $(".header_userFunction").css("display", "none");
    } else {
        $(".header_userFunction").css("display", "block");
    }
}