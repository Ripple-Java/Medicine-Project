function userExitFunction() {//�û��˳�
    $.get("http://localhost:8080/MedicineProject/Web/enterprise/loginOut",
        function (data) {
            $(".header_userFunction").css("display", "none");
            alert( "�˳��ɹ���");
            document.cookie == "";
            location.href = "./businessLogin.html";
        }).
        error(function (data) {
            alert("error");
        });
}