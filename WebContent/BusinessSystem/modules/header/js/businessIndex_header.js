function userExitFunction() {//用户退出
    $.get("http://localhost:8080/MedicineProject/Web/enterprise/loginOut",
        function (data) {
            $(".header_userFunction").css("display", "none");
            alert( "退出成功！");
            document.cookie == "";
            location.href = "./businessLogin.html";
        }).
        error(function (data) {
            alert("error");
        });
}