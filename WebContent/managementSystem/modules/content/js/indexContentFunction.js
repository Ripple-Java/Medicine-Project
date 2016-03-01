function checkboxFunction(id) {  
    if ($('#checkbox-'+id).is(':checked')) {
        $('#checkbox-'+id+':checked + label').css({
            "background-image":"url(images/checkbox_checked.png)"
        });
    } else {
        $('#checkbox-'+id+'+ label').css({
            "background-image": "images/checkbox_unchecked.png"
        });
    }
}
function allSelect(which){//1：全选；2：取消全选
	$.each($(".list_list :checkbox "),function(times,result){
		switch (which){
		case 2:{
			$(result).removeAttr("checked");
			$('#checkbox-'+$(result).val()+'+ label').css({
	            "background-image": "images/checkbox_unchecked.png"
	        });
			break;
		}
		case 1:{			
			$(result).prop("checked", true);
			$('#checkbox-'+$(result).val()+':checked + label').css({
	            "background-image":"url(images/checkbox_checked.png)"
	        });
			
			break;
		}
		}
	});
}
function getCount(times){
	var maxPage=0;
	$.ajax({
		url:"http://localhost:8080/MedicineProject/Web/user/getCount",
		type:"GET",
		data:{
			type:times
		},
		dataType:"json",
		success:function(data){
			if(data.result.trim()=="success") {maxPage=data.count;}
		},
		async:false
	}).error(function(data){
		console.log(data);
	});	
	return maxPage;
}

function getCount_1(times){//type(类型：1-未审核企业用户数量， 2-所有需要审核条目总数，3-会议数量，4-企业数量，5-视频数量，6-反馈数量))
	var maxPage=0;
	$.ajax({
		url:"http://localhost:8080/MedicineProject/Web/admin/getCount",
		type:"GET",
		data:{
			type:times
		},
		dataType:"json",
		success:function(data){
			if(data.result.trim()=="success") {maxPage=data.count;}
		},
		async:false
	}).error(function(data){
		console.log(data);
	});	
	return maxPage;
}
function pageselectCallback(page_index, jq){
	$(".pagination a").removeClass("content_content_pageNum_hadSelect");
	$(".current").addClass("content_content_pageNum_hadSelect");
	$("span.next").removeClass("content_content_pageNum_hadSelect");
	$("span.prev").removeClass("content_content_pageNum_hadSelect"); 
    if($(jq).parent().attr('class')=="drugListPage"){
    	loadingResourceManagement_drugList(8, page_index + 1,$(".resourceManagement_drugListSelect").val());
    }else if($(jq).parent().attr('class')=="videoListPage"){
    	loadingResourceManagement_videoList(page_index + 1, 8) ;
    }else if($(jq).parent().attr('class')=="meetingListPage"){
    	loadingResourceManagement_meetingList(page_index + 1, 8) ;
    }else if($(jq).parent().attr('class')=="businessListPage"){
    	loadingBusinessManagement_businessList(page_index + 1, 8); 
    }else if($(jq).parent().attr('class')=="businessChecRrequestListPage"){
    	loadingBusinessManagement_registerCheckList(page_index + 1, 8); 
    }else if($(jq).parent().attr('class')=="userListPage"){
    	switch(parseInt($(".userFilter").val())){
    	case 1:{
    		loadingUserManagement_userList(page_index + 1, 8) ;
    		break;
    	}
    	default:{
    		userfilter(page_index + 1, 8);   		
    	}
    	}
    	
    }
    else if($(jq).parent().attr('class')=="feedbackListPage"){
    	loadingfeedBackManagement_feedbackList(page_index + 1, 8);
    }
    return false;
}
function firstPageClick(selection){
	var maxPage=0;
	switch(selection){
	case 1:{//药品
		switch($(".resourceManagement_drugListSelect").val()){
		case '1':maxPage=getCount(21);break;
		case '2':maxPage=getCount(22);break;
		case '3':maxPage=getCount(23);break;
		case '4':maxPage=getCount(24);break;
		case '5':maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);break;
		}	
		
		break;
	}
	case 2:{//会议
		maxPage=getCount_1(3);
		break;
	}
	case 3:{//视频
		maxPage=getCount_1(5);
		break;
	}
	case 4:{//用户
		switch(parseInt($(".userFilter").val())){
		case 0:maxPage=getCount(11);break;
		case 1:maxPage=getCount(11)+getCount(12);break;
		case 3:maxPage=getCount(12);break;
		}		
		break;
	}
	case 5:{//企业
		maxPage=getCount_1(4);
		break;
	}
	case 6:{//反馈
		maxPage=getCount_1(6);
		break;
	}
	case 7:{//企业注册审核
		maxPage=getCount_1(1);
		break;
	}
	default:{maxPage=5;break;}
	}
	if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
	else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
	$(" #Pagination").pagination(maxPage, {
        num_edge_entries: 0, //边缘页数
        num_display_entries: 4, //主体页数
        callback: pageselectCallback,//回调函数
        items_per_page:1, //每页显示1项
        ellipse_text: "",
        current_page:0
    });
};
function endPageClick(selection){
	var maxPage=0;
	switch(selection){
	case 1:{
		switch($(".resourceManagement_drugListSelect").val()){
		case '1':maxPage=getCount(21);break;
		case '2':maxPage=getCount(22);break;
		case '3':maxPage=getCount(23);break;
		case '4':maxPage=getCount(24);break;
		case '5':maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);break;
		}		
		break;
	}
	case 2:{//会议
		maxPage=getCount_1(3);
		break;
	}
	case 3:{//视频
		maxPage=getCount_1(5);
		break;
	}
	case 4:{//用户
		switch(parseInt($(".userFilter").val())){
		case 0:maxPage=getCount(11);break;
		case 1:maxPage=getCount(11)+getCount(12);break;
		case 3:maxPage=getCount(12);break;
		}		
		break;
	}
	case 5:{//企业
		maxPage=getCount_1(4);
		break;
	}
	case 6:{//反馈
		maxPage=getCount_1(6);
		break;
	}
	case 7:{//企业注册审核
		maxPage=getCount_1(1);
		break;
	}
	default:{maxPage=5;break;}
	}
	if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
	else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
	$("#Pagination").pagination(maxPage, {
        num_edge_entries: 0, //边缘页数
        num_display_entries: 4, //主体页数
        callback: pageselectCallback,//回调函数
        items_per_page:1, //每页显示1项
        ellipse_text: "",
        current_page:maxPage-1
    });
};

function changeAdminPassword(){
	if($(".changeNewPassword").val().trim()!=""&&$(".checkChangeNewPassword").val().trim()!=""&&$(".changeOldPassword").val().trim()!="")
	if($(".changeNewPassword").val()==$(".checkChangeNewPassword").val())
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/password/update", 
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