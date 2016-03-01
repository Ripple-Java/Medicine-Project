function getBusinessInformation(){
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/info/get", 
        success:function (data,status) {
        	$(".businessName").empty().append(data.enterprise.name);
        	switch(data.enterprise.type){
        	case 0:$(".businessType").empty().append("外资企业");break;
        	case 1:$(".businessType").empty().append("合资企业");break;
        	case 2:$(".businessType").empty().append("内资企业");break;
        	default:break;
        	}
        	$(".aside_userMessage_userName").empty().append(data.enterprise.name);
        	if(data.enterprise.logo !=null){
        		$(".businessManagementContent_logoImg").empty().append("<img src=\"http://localhost:8080/MedicineProject/"+data.enterprise.logo+"\" class=\"uploadLogo\" />");
        		$(".aside_userMessage_userIcon").empty().append("<tr><td><img src=\"http://localhost:8080/MedicineProject/" + data.enterprise.logo + "\" class=\"uploadLogo\" style=\"width:100%;\" /></td></tr>");
        	}       	
        	$(".businessNumber").empty().append(data.enterprise.enterpriseNumber);
            $(".businessContent").empty().append(data.enterprise.content);
        	$(".businessUrl").val(data.enterprise.enterpriseUrl);
        	$(".businessPhone").val(data.enterprise.phone);
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
        });
}

function updateBusinessMessage(){
	if($(".businessContent").val()!=""&&$(".businessUrl").val()!=""&&$(".businessPhone").val()!="")
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/info/add", 
        data:{
        	content:$(".businessContent").val(),
        	enterpriseUrl:$(".businessUrl").val(),
        	phone:$(".businessPhone").val()
        },
        success:function (data,status) {
        	if(data.result.trim()=="success"){
        		alert("修改成功！");
        	}
        },
        dataType:"json",
        type:"POST"
    }).error(function (data,status) {
        });
}

function uploadBusinessLogo(){
	$(".uploadLogoForm").ajaxSubmit({
        type: "POST",  //提交方式  
        dataType: "json", //数据类型  
        contentType: "multipart/form-data",
        url: "http://localhost:8080/MedicineProject/upload/image/enterprise/logo",  
        success: function (data) { //提交成功的回调函数
        	if(data.result.trim()=="success") alert("上传成功！");
        	else alert("上传失败！");
        },
        error: function (data) { console.log(data); }
    });
}

function UploadLogoButtonFunction(obj) {//实时显示上传图片
	var AllowExt = ".jpg|.png|.bmp|.jpeg|"; //允许上传的文件类型 ?为无限制 每个扩展名后边要加一个"|" 小写字母表示 
	var AllowImgFileSize = 1024; //允许上传图片文件的大小 0为无限制 单位：KB 
	var FileExt = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
	var ErrMsg = "";
	if (AllowExt != 0 && AllowExt.indexOf(FileExt + "|") == -1) //判断文件类型是否允许上传
	{
	    ErrMsg = "该文件类型不允许上传。请上传 " + AllowExt + " 类型的文件，当前文件类型为" + FileExt;
	    alert(ErrMsg);
	    return false;
	} else {

	    if (AllowImgFileSize != 0 && AllowImgFileSize < Math.round(document.getElementById("file").files[0].size / 1024 * 100) / 100) {
	        ErrMsg = ErrMsg + "\n图片文件大小超过限制。请上传小于" + AllowImgFileSize + "KB的文件";
	        alert(ErrMsg);
	    } else {
	        $(".uploadLogo ").attr("src", window.URL.createObjectURL(document.getElementById("file").files[0]));
	        $(".aside_userMessage_userIcon .uploadLogo").attr("src", window.URL.createObjectURL(document.getElementById("file").files[0]));
	        uploadBusinessLogo();
	    }
	    return false;
	}
}

function changeBusinessPassword(){
	if($(".changeNewPassword").val().trim()!=""&&$(".checkChangeNewPassword").val().trim()!=""&&$(".changeOldPassword").val().trim()!="")
	if($(".changeNewPassword").val()==$(".checkChangeNewPassword").val())
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/upatePassword", 
    	data:{
    		"oldPassword":$(".changeOldPassword").val(),
    		"newPassword":$(".changeNewPassword").val()
    	},
        success:function (data,status) {
        	$(".changeInformation_nav li input").css("border-color" , "#e5e5e5");
        	$(".errorMessage").css("display" , "none");
        	if(data.result.trim()=="fail"){
        		$(".changeOldPassword").css("border-color" , "red");
        		$(".errorMessage").css("display" , "block");
        		$(".errorMessage").empty().append("*原始密码错误！");
        	}
        	else{alert("修改密码成功！");}
        	
        },
        dataType:"json",
        type:"POST"
    }).error(function (data,status) {
        });
	else{
		$(".checkChangeNewPassword").css("border-color" , "red");
		$(".errorMessage").css("display" , "block");
		$(".errorMessage").empty().append("*确认密码与重置密码不符！");
		}
}