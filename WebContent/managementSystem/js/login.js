angular.module("doctorLogin", []).run(function () {
    resize();
}).controller('login', ['$scope', function (scope) {
    $(".loginBox_button").click(function () {
        if ($('.loginBox_user').val().trim() != "" && $('.loginBox_password').val().trim() != "")
            $.ajax({
                type: "POST",
                url: 'http://localhost:8080/MedicineProject/Web/adminuser/login',
                data:{
                    account: $('.loginBox_user').val(),
                    password: $('.loginBox_password').val()
                },
                dataType: "json",
                success: function (data) {
                	console.log(data);
                    if (data.result.trim() == "fail") $(".loginFailAlert").css('display','block');
                    else {
                        document.cookie = $('.loginBox_user').val();
                        location.href = "./index.html";
                    }
                }
            }).error(function (data) {
            	console.log(data);
                $(".loginFailAlert").css('display','block');
            });
    });
}]);
window.onresize = function () {
    resize();
};
function resize() {
    $(".login_content").css({
        "height": window.innerHeight - 75 + "px"
    });
}