angular.module("doctorLogin", []).run(function () {
    resize();
}).controller('login', ['$scope', function (scope) {
    $(".loginBox_button").click(function () {
        if ($('.loginBox_user').val().trim() != "" && $('.loginBox_password').val().trim() != "")
            $.ajax({
                type: "POST",
                url: 'http://112.74.131.194:8080/MedicineProject/Web/adminuser/login',
                data:{
                    account: $('.loginBox_user').val(),
                    password: $('.loginBox_password').val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.result.trim() == "fail") alert(data.tip);
                    else {
                        document.cookie = "adminhadLogin"
                        location.href = "./index.html";
                    }
                }
            }).error(function (data) {
                alert("error");
            });
    });
}])
window.onresize = function () {
    resize();
}
function resize() {
    $(".login_content").css({
        "height": window.innerHeight - 75 + "px"
    });
}