function getBusinessInformation(){
	$.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/enterprise/info/get", 
        success:function (data,status) {
        	$(".businessName").empty().append(data.enterprise.name);
        	switch(data.enterprise.type){
        	case 0:$(".businessType").empty().append("外资企业");break;
        	case 1:$(".businessType").empty().append("合资企业");break;
        	case 2:$(".businessType").empty().append("内资企业");break;
        	default:break;
        	}
        	$(".aside_userMessage_userName").empty().append(data.enterprise.name);
        	$(".businessNumber").empty().append(data.enterprise.enterpriseNumber);
        	$(".businessManagementContent_logoImg").empty().append("<img src=\"http://112.74.131.194:8080/MedicineProject/"+data.enterprise.logo+"\" class=\"uploadLogo\" />");
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
    	url:"http://112.74.131.194:8080/MedicineProject/Web/enterprise/info/add", 
        data:{
        	content:$(".businessContent").val(),
        	enterpriseUrl:$(".businessUrl").val(),
        	phone:$(".businessPhone").val()
        },
        success:function (data,status) {
        	console.log(data);
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
        url: "http://112.74.131.194:8080/MedicineProject/upload/image/enterprise/logo",  
        success: function (data) { //提交成功的回调函数
        	console.log(data);
        	if(data.result.trim()=="success") alert("上传成功！");
        	else alert("上传失败！");
        },
        error: function (data) { console.log(data); }
    });
}

function UploadLogoButtonFunction() {//实时显示上传图片
	if (document.getElementById("file").files &&document.getElementById("file").files[0]) {
		$(".uploadLogo ").attr("src", window.URL.createObjectURL(document.getElementById("file").files[0]));
		uploadBusinessLogo();
	}
   
}

function changeBusinessPassword(){
	if($(".changeNewPassword").val().trim()!=""&&$(".checkChangeNewPassword").val().trim()!=""&&$(".changeOldPassword").val().trim()!="")
	if($(".changeNewPassword").val()==$(".checkChangeNewPassword").val())
	$.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/enterprise/upatePassword", 
    	data:{
    		"oldPassword":$(".changeOldPassword").val(),
    		"newPassword":$(".changeNewPassword").val()
    	},
        success:function (data,status) {
        	//console.log(data);
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