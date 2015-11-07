angular.module("doctorLogin", []).run(function () {
    resize();
}).controller('login', ['$scope', function (scope) {
    $(".loginBox_button").click(function (e) {//登录按钮
        if ($('.loginBox_user').val().trim() != "" && $('.loginBox_password').val().trim() != "")
            $.post('http://localhost:8080/MedicineProject/Web/enteruser/login',
                {
                    account: $('.loginBox_user').val(),
                    password: $.md5($('.loginBox_password').val())
                },
                function (data) {
                    console.log(data);
                    if(data.result.trim()=="success"){
                    	document.cookie = "businesshadLogin";
                        location.href = "./BusinessIndex.html";
                    }
                },"json").error(function (data, status) {
                    alert("error");
                });
    });
    $(".forgetPassword").click(function () {//找回密码
        if ($('.loginBox_user').val().trim() == "") alert("请输入账户");
        else
        $.post('http://localhost:8080/MedicineProject/Web/enteruser/getBackPassword',
                {
                    account: $('.loginBox_user').val()
                },
                function (data) {
                    console.log(data );
                },"json").error(function (data, status) {
                    alert(status);
                });
    });
}]).controller('register', ['$scope', function (scope) {
    $(".register_page2_footer_completeButton").click(function () {//完成注册
        if ($('.register_page2_content_loginMail').val().trim() != "" && $('.register_page2_content_loginPassword').val().trim() != "" && $('.register_page2_content_repeatPassword').val().trim() != "")
            if($('.register_page2_content_loginPassword').val().trim() == $('.register_page2_content_repeatPassword').val().trim() )
        	$.post('http://localhost:8080/MedicineProject/Web/enteruser/register',
                    {
                        email: $('.register_page2_content_loginMail').val(),
                        password: $.md5($('.register_page2_content_loginPassword').val())
                    },
                    function (data) {
                        console.log(data);
                        if(data.result.trim()=="success"){
                        	$(".login_content").css("display", "block");
                            $(".register_content").css("display", "none");
                            $(".register_page1").css("display", "block");
                            $(".register_page2").css("display", "none");
                        }                        
                    }, "json").error(function (data, status) {
                        alert(status);
                    });
            else alert("重复密码不正确！");
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
function registerNextButton() {//注册下一步按钮
    if ($(".register_page1_content_businessName input").val() != "" && $(".register_page1_content_businessRegisterNumber input").val() != "" && $("input[type=\"file\"]").val() != "") {
        $.post("http://localhost:8080/MedicineProject/Web/enteruser/setInfo", {
            name: $(".register_page1_content_businessName input").val(),
            type:parseInt($(".register_page1_content_businessStyle select").val()),
            number:$(".register_page1_content_businessRegisterNumber input").val(),
            imgUrl: window.URL.createObjectURL(document.getElementById("file").files[0])
        }, function (data) {
            if (data.result.trim() == "success") {
                $(".register_page1").css("display", "none");
                $(".register_page2").css("display", "block");
            }
        },"json");
    } else if ($(".register_page1_content_businessName input").val() != "" && $(".register_page1_content_businessRegisterNumber input").val() != "" && $("input[type=\"file\"]").val() == "") alert("请上传执照！");
}
function registerResetButton() {//注册重置按钮
    $(".register_page1_content_businessName input").val("");
    $(".register_page1_content_businessStyle select").val("1");
    $(".register_page1_content_businessRegisterNumber input").val("");
    $(".img_uploadImg img").attr("src", "images/img_uploadImg.png");
}
function gotoRegister() {//去注册
    $(".login_content").css("display", "none");
    $(".register_content").css("display", "block");
}
function registerUploadImgButtonFunction() {//实时显示上传图片
	if (document.getElementById("file").files &&document.getElementById("file").files[0]) {
		$(".img_uploadImg img").attr("src", window.URL.createObjectURL(document.getElementById("file").files[0]));
		$(".registerImg").ajaxSubmit({
	        type: "POST",  //提交方式  
	        dataType: "json", //数据类型  
	        contentType: "multipart/form-data",
	        url: "http://localhost:8080/MedicineProject/upload/image/enterCheckImg",  
	        data:{
	        	img:"imgFile"
	        },
	        success: function (data) { //提交成功的回调函数  
	            alert(data.result);
	        },
	        error: function (data) { console.log(data); }
	    });
	}else{
		alert("上传错误");
	}
   
}
