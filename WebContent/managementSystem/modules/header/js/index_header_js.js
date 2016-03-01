function userExitFunction() {//用户退出
    $.get("http://localhost:8080/MedicineProject/Web/admin/loginOut",
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
    $(".header_userFunction").css("display", "block");
    $(".header_bg").css("display", "block");
}
function close_header_userFunction(){
	$(".header_userFunction").css("display", "none");
	$(".header_bg").css("display", "none");
}
angular.module("doctor_app_header", []).controller('header', ['$scope', function (scope) {

    $(".header_user").mouseenter(function () {
        header_user_Click();
    })
    $(".header_bg").mouseenter(function () {
        close_header_userFunction();
    })
}]);