angular.module("doctorLogin", []).run(function () {
    resize();
}).controller('login', ['$scope', function (scope) {
    $(".loginBox_button").click(function (e) {//登录按钮
        if ($('.loginBox_user').val().trim() != "" && $('.loginBox_password').val().trim() != "")
            $.post('http://localhost:8080/MedicineProject/Web/enteruser/login',
                {
                    account:$('.loginBox_user').val(),
                    password: $('.loginBox_password').val()
                },
                function (data) {
                	console.log(data);
                    if(data.result=="success"){
                    	document.cookie = $('.loginBox_user').val();
                        location.href = "./BusinessIndex.html";
                    }else{
                        $(".loginBox .FailAlert").css('display', 'block');
                        
                    }
                },"json").error(function (data, status) {
                	console.log(data);
                	$(".loginBox .FailAlert").css('display', 'block');
                });
    });
}]).controller('forgetPassword', ['$scope', function (scope) {
    $(".forgetPasswordBox_button").click(function () {//找回密码
        if ($('.forgetPasswordBox_user').val().trim() != "")
            $.post('http://localhost:8080/MedicineProject/Web/enteruser/getBackPassword',
                    {
                        account: $('.forgetPasswordBox_user').val()
                    },
                    function (data) {
                        if (data.result.trim() == "success") {
                            location.href = "./businessLogin.html";
                        } else {
                            $(".forgetPasswordBox .FailAlert").css('display', 'block');
                        }
                    }, "json").error(function (data, status) {
                        console.log(status);
                    });
    });
}]).controller('register', ['$scope', function (scope) {
    $(".register_page2_footer_completeButton").click(function () {//完成注册
    	var check=true;//检查文本框是否非空 true：是，false：否
    	$(".register_page2_content li input").each(function(){
    	    if($(this).val().trim() == ""){
    	        check=false;
    	        return false;
    	    }
    	    
    	});   	
        if (check)
            if($('.register_page2_content_loginPassword').val().trim() == $('.register_page2_content_repeatPassword').val().trim() )
        	$.post('http://localhost:8080/MedicineProject/Web/enteruser/register',
                    {
                        email: $('.register_page2_content_loginMail').val(),
                        password: $('.register_page2_content_loginPassword').val()
                    },
                    function (data) {
                        if (data.result.trim() == "success") {
                        	location.href = "./businessLogin.html";
                        }else{
                        	if(data.errorCode!=100201)alert("注册失败，请检查填写是否正确！");
                        	else{alert("注册失败，此用户名已被使用！");}
                        }                       
                    }, "json").error(function (data, status) {
                        console.log(status);
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
    	$(".registerImg").ajaxSubmit({
	        type: "POST",  //提交方式  
	        dataType: "json", //数据类型  
	        contentType: "multipart/form-data",
	        url: "http://localhost:8080/MedicineProject/upload/image/enterCheckImg",  
	        success: function (data) { //提交成功的回调函数
	        	console.log(data);
	        	if(data.result.trim()=="success")
	        	$.post("http://localhost:8080/MedicineProject/Web/enteruser/setInfo", {
	                name: $(".register_page1_content_businessName input").val(),
	                type:parseInt($(".register_page1_content_businessStyle select").val()),
	                number:$(".register_page1_content_businessRegisterNumber input").val(),
	                imgUrl: data.imgUrl
	            }, function (setInfoData) {
	                if (setInfoData.result.trim() == "success") {
	                    $(".register_page1").css("display", "none");
	                    $(".register_page2").css("display", "block");
	                }
	            },"json");
	        },
	        error: function (data) { console.log(data); }
	    });
    } else if ($(".register_page1_content_businessName input").val() != "" && $(".register_page1_content_businessRegisterNumber input").val() != "" && $("input[type=\"file\"]").val() == "") alert("请上传执照！");
}
function registerResetButton() {//注册重置按钮
    $(".register_page1_content_businessName input").val("");
    $(".register_page1_content_businessStyle select").val("1");
    $(".register_page1_content_businessRegisterNumber input").val("");
    $(".img_uploadImg img").attr("src", "images/img_uploadImg.png");
}
function gotoAnotherPage(which) {//0:登陆页面，1：忘记密码页面，2：去注册,4:返回到注册页面1
    $(".login_content").css("display", "none");
    $(".forgetPassword_content").css("display", "none");
    $(".register_content").css("display", "none");
    switch (which) {
        case 0: {
            $(".login_content").css("display", "block");
            $(".register_page1").css("display", "block");
            $(".register_page2").css("display", "none");
            break;
        }
        case 1: $(".forgetPassword_content").css("display", "block"); break;
        case 2: {
            $(".register_content").css("display", "block");
            break;
        }
        case 4: {
        	$(".register_content").css("display", "block");
            $(".register_page1").css("display", "block");
            $(".register_page2").css("display", "none");
            break;
        }
    }
}
function registerUploadImgButtonFunction(obj) {//实时显示上传图片
    var AllowExt = ".jpg|.png|.bmp|.jpeg|"; //允许上传的文件类型 ?为无限制 每个扩展名后边要加一个"|" 小写字母表示 
    var AllowImgFileSize = 1024; //允许上传图片文件的大小 0为无限制 单位：KB 
    var FileExt = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
    var ErrMsg="";
    if (AllowExt != 0 && AllowExt.indexOf(FileExt + "|") == -1) //判断文件类型是否允许上传
    {
        ErrMsg = "该文件类型不允许上传。请上传 " + AllowExt + " 类型的文件，当前文件类型为" + FileExt;
        alert(ErrMsg);
        return false;
    } else 
    {
        
        if (AllowImgFileSize != 0 && AllowImgFileSize < Math.round(document.getElementById("file").files[0].size / 1024 * 100) / 100) {
            ErrMsg = ErrMsg + "\n图片文件大小超过限制。请上传小于" + AllowImgFileSize + "KB的文件";
            alert(ErrMsg);
        } else {
            $(".img_uploadImg img").attr("src", window.URL.createObjectURL(document.getElementById("file").files[0]));
        }
        return false;
    }
}

