function loadingResourceManagement_drugList(pageSize, pageNum,type) {//加载药品列表
    var drugMessage="";
    var checkbox="";
    var drugName="";
    var drugBusiness="";
    var drugFunction="";
    var drugType = "";
    var thisType=0;
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/getMedicine",
    	data:{
            pageSize: pageSize,
            pageNum: pageNum,
            type: type
        }, 
        success:function (data,status) {
            if(data.result.trim()=="success"){
            	if(data.BackGroundMedicineVOs!=null)
            	$.each(data.BackGroundMedicineVOs, function (times, result) {
                    checkbox = "<td><li value=\""+pageNum+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    if(type==5){
                    	if(result.type.firstType.trim()=="西药"){
                    		if(result.enterpriseName) thisType=4;
                    		else thisType=2;
                    	}else{
                    		if(result.enterpriseName) thisType=3;
                    		else thisType=1;
                    	}
                    }else{thisType=type;}
                    drugName = "<td>"+ result.name + "</td>";
                    if(result.enterpriseName)  {drugBusiness = "<td>" + result.enterpriseName + "</td>";drugFunction="<td></td>";}
                    else {
                    	drugBusiness = "<td></td>";
                    	drugFunction = "<td class=\"list_button resourceList\"><ul><li class=\"drugList_list_editButton iconfont\" onclick=\"updateDrug("+result.id+");\" ><a >&#xe630;</a></li></ul></td>";
                    }
                    drugType = "<td><li class=\"type-"+result.id+"\"value=\""+thisType+" \">" + result.type.firstType + "-" + result.type.secondType + "-" + result.type.thirdType +"</li></td>";                    
                    drugMessage = drugMessage + "<tr>" + checkbox + drugName + drugType + drugBusiness + drugFunction + "</tr>";
                });
            	$(".drugList").empty().prepend(drugMessage);
            }else{
            	$(".drugList").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }            
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".drugList").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}
function add_drug_ChinaOrWestaside_action(number) {//资源管理---药品列表添加选择 1：中药，2：通用西药
    $(".add_drug_ChinaOrWestaside ul li").removeClass("add_drug_ChinaOrWestaside_hadActive");
    $(".add_drug_ChinaOrWestaside ul li:nth-child(" + number + ")").addClass("add_drug_ChinaOrWestaside_hadActive");
    $(".drugTypeAllSelect").empty() ;
    if (number == 1) {
        $(".addCommonChinaDrug").css("display", "block");
        $(".addCommonWestDrug").css("display", "none");
        $(".addCommonChinaDrugType ").append("<select id=\"first\" size=\"1\" ></select> - <select id=\"second\" ></select> - <select id=\"third\"  ></select> - <select id=\"forth\"></select>");
    } else {
        $(".addCommonWestDrug").css("display", "block");
        $(".addCommonChinaDrug").css("display", "none");
        $(" .addCommonWestDrugType ").append("<select id=\"first\" size=\"1\" ></select> - <select id=\"second\" ></select> - <select id=\"third\"  ></select>");
    }
    getDrugType();
}
function addCommonChinaDrugButtonFunction() {//资源管理---药品列表---添加中药保存按钮
    var checkIsNull = 0;
    var checkTheforthIsNull="";
    $.each($(".addCommonChinaDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if($(".addCommonChinaDrug #forth").val()==null){checkTheforthIsNull=$(" .addCommonChinaDrug #third").val();}
    else checkTheforthIsNull=$(".addCommonChinaDrug #forth").val();
    if (checkIsNull == 0) {
        $.post("http://localhost:8080/MedicineProject/Web/admin/addChinMedicine", {
            name: $(".CommonChinaDrug_name").val(),
            content: $(".CommonChinaDrug_content").val(),
            efficacy: $(".CommonChinaDrug_efficacy").val(),
            annouce: $(".CommonChinaDrug_annouce").val(),
            preparations: $(".CommonChinaDrug_preparations").val(),
            manual: $(".CommonChinaDrug_manual").val(),
            store: $(".CommonChinaDrug_store").val(),
            category: $(".CommonChinaDrug_category").val(),
            price: $(".CommonChinaDrug_price").val(),
            medicineType_id: checkTheforthIsNull
        }, function (data) {
        	if(data.result.trim()=="success"){
        		alert("添加通用中药成功！");
        		$(".bg").click();
        	}else{
        		alert("添加通用中药失败！");
        	}
        }, "json");
    }
}
function addCommonWestDrugButtonFunction() {//资源管理---药品列表---添加西药保存按钮
    var checkIsNull = 0;
    $.each($(".addCommonWestDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
        $.post("http://localhost:8080/MedicineProject/Web/admin/addWestMedicine", {
            name: $(".CommonWestDrug_name").val(),
            other_name: $(".CommonWestDrug_other_name").val(),
            content: $(".CommonWestDrug_content").val(),
            current_application: $(".CommonWestDrug_current_application").val(),
            pharmacolo: $(".CommonWestDrug_pharmacolo").val(),       
            warn: $(".CommonWestDrug_warn").val(),
            ADRS: $(".CommonWestDrug_ADRS").val(),
            interaction: $(".CommonWestDrug_interaction").val(),
            dose_explain: $(".CommonWestDrug_dose_explain").val(),
            manual: $(".CommonWestDrug_manual").val(),
            preparations: $(".CommonWestDrug_preparations").val(),         
            medicineType_id:$(".addCommonWestDrug #third").val()
        }, function (data) {
        	if(data.result.trim()=="success"){
        		alert("添加通用西药成功！");
        		$(".bg").click();
        	}else{
        		alert("添加通用西药失败！");
        	}
            
        }, "json");
    }
}
function deleteDrug(){//which default:删除但是页表下药品 ，-1：删除搜索下药品
	var maxPage=0;
	var thisType="";
	$.each($(".drugList :checkbox "),function(times,result){
		if($(result).is(':checked')){
			thisType=".type-"+$(result).val();
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/admin/deleteMedicine",
		    	data:{
		            id:$(result).val(),
		            type:$(thisType).val()
		        }, 
		        success:function (data,status) {
		        	if(data.result.trim()=="success")
		        	switch(parseInt($(result).parent().val())){
		        	case -1:{
		        		$(result).parent().parent().parent().remove() ;break;
		        		
		        	}
		        	default:{
		        		switch($(".resourceManagement_drugListSelect").val()){
		        		case '1':maxPage=getCount(21);break;
		        		case '2':maxPage=getCount(22);break;
		        		case '3':maxPage=getCount(23);break;
		        		case '4':maxPage=getCount(24);break;
		        		case '5':maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);break;
		        		}	
		        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
		        		currentPage=$(result).parent().val()-1;
		        		if(maxPage==currentPage) currentPage--;
		        		$("#Pagination").pagination(maxPage, {
	        	        num_edge_entries: 0, //边缘页数
	        	        num_display_entries: 4, //主体页数
	        	        callback: pageselectCallback,//回调函数
	        	        items_per_page:1, //每页显示1项
	        	        ellipse_text: "",
	        	        current_page:currentPage
	        	    });	
	        		break;}
		        	}		        	
		        	else     alert("删除失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("删除失败！");
		        });
		}
	});
}
function searchDrug(){
	var Message="";
    var checkbox="";
    var Name="";
    var Business="";
    var Function="";
    var Type = "";
    var thisType=0;
    if($(".resourceManagement_search").val().trim()!="")
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/searchMedicine",
    	data:{
            keyword:$(".resourceManagement_search").val()
        }, 
        success:function (data,status) {
            if(data.result.trim()=="success"){
            	if(data.BackGroundMedicineVOs!=null){
            		$.each(data.BackGroundMedicineVOs, function (times, result) {
                        checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                        	if(result.type.firstType.trim()=="西药"){
                        		if(result.enterpriseName) thisType=4;
                        		else thisType=2;
                        	}else{
                        		if(result.enterpriseName) thisType=3;
                        		else thisType=1;
                        	}
                        Name = "<td>" + result.name + "</td>";
                        if(result.enterpriseName)  {
                        	Business = "<td>" + result.enterpriseName + "</td>";     
                        	Function="<td></td>";
                        }
                        else {
                        	Business = "<td></td>";
                        	Function = "<td class=\"list_button resourceList\"><ul><li class=\"drugList_list_editButton iconfont \" onclick=\"updateDrug("+result.id+");\" ><a>&#xe630;</a></li></ul></td>";
                        }
                        Type = "<td><li class=\"type-"+result.id+"\"value=\""+thisType+" \">" + result.type.firstType + "-" + result.type.secondType + "-" + result.type.thirdType+"</li></td>" ;
                        Message = Message + "<tr>" + checkbox + Name + Type + Business + Function + "</tr>";
                    });
                	$(".drugList").empty().prepend(Message);
            	}else{
            		alert("无相关数据！");
            	}
            	
            }else{
            	console.log(data);
            }       
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
            alert(data+"--"+status);
        });
}
function resourceManagement_drugListSelect(){
	//console.log($(".resourceManagement_drugListSelect").val());
	firstPageClick(1);
}

function updateDrug(isUpdate){	
	if(isUpdate==-1){
		$(".updateDrugForm").empty();
	}else{
		$(".saveID").val(isUpdate);
		if($(".type-"+isUpdate).val()=="1"){//更新中药
			$(".updateDrugForm").empty().load("modules/content/index_modules/update/updateCommonChinaDrug.html");
			
		}
		else{//更新西药
			$(".updateDrugForm").empty().load("modules/content/index_modules/update/updateCommonWestDrug.html");
		}
		
	}
	
}
function getCommonChinaDrugMessage(which){//1:中药；2：西药
	 $.ajax({
	    	url:"http://localhost:8080/MedicineProject/Web/admin/medicine/get/detail",
	    	data:{
	            id:$(".saveID").val(),
	            type: which
	        }, 
	        success:function (data,status) {
	            if(data.result.trim()=="success"){
	            	if(which==1){
	            		$(".CommonChinaDrug_name").val(data.entity.name);
		                 $(".CommonChinaDrug_content").val(data.entity.content);
		                 $(".CommonChinaDrug_efficacy").val(data.entity.efficacy);
		                 $(".CommonChinaDrug_annouce").val(data.entity.annouce);
		                 $(".CommonChinaDrug_preparations").val(data.entity.preparations);
		                 $(".CommonChinaDrug_manual").val(data.entity.manual);
		                 $(".CommonChinaDrug_store").val(data.entity.store);
		                 $(".CommonChinaDrug_category").val(data.entity.category);
		                 $(".CommonChinaDrug_price").val(data.entity.price);
	            	}else{
	            		 $(".CommonWestDrug_name").val(data.entity.name);
	                     $(".CommonWestDrug_other_name").val(data.entity.other_name);
	                     $(".CommonWestDrug_content").val(data.entity.content);
	                     $(".CommonWestDrug_current_application").val(data.entity.current_application);
	                     $(".CommonWestDrug_pharmacolo").val(data.entity.pharmacolo);       
	                     $(".CommonWestDrug_warn").val(data.entity.warn);
	                     $(".CommonWestDrug_ADRS").val(data.entity.adrs);
	                     $(".CommonWestDrug_interaction").val(data.entity.interaction);
	                     $(".CommonWestDrug_dose_explain").val(data.entity.dose_explain);
	                     $(".CommonWestDrug_manual").val(data.entity.manual);
	                     $(".CommonWestDrug_preparations").val(data.entity.preparations);
	            	}
	            	 
	            }           
	        },
	        dataType:"json",
	        type:"GET"
	    }).error(function (data,status) {
	        });
}
function updateCommonChinaDrug(){
	var checkIsNull = 0;
    var checkTheforthIsNull="";
    $.each($(".updateCommonChinaDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if($(".updateCommonChinaDrug #forth").val()==null){checkTheforthIsNull=$(" .updateCommonChinaDrug #third").val();}
    else checkTheforthIsNull=$(".updateCommonChinaDrug #forth").val();
    
    if (checkIsNull == 0) {
        $.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/admin/updateChinMedicine", 
        	data:{
            name: $(".CommonChinaDrug_name").val(),
            content: $(".CommonChinaDrug_content").val(),
            efficacy: $(".CommonChinaDrug_efficacy").val(),
            annouce: $(".CommonChinaDrug_annouce").val(),
            preparations: $(".CommonChinaDrug_preparations").val(),
            manual: $(".CommonChinaDrug_manual").val(),
            store: $(".CommonChinaDrug_store").val(),
            category: $(".CommonChinaDrug_category").val(),
            medicineType_id: checkTheforthIsNull,
            id:$(".saveID").val()
        }, 
        success:function (data,status) {
        	if(data.result.trim()=="success"){
        		console.log($(".drugList input").first().parent().val());
        		if(parseInt($(".drugList input").first().parent().val())!=-1){
        			switch($(".resourceManagement_drugListSelect").val()){
        			case '1':maxPage=getCount(21);break;
        			case '2':maxPage=getCount(22);break;
        			case '3':maxPage=getCount(23);break;
        			case '4':maxPage=getCount(24);break;
        			case '5':maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);break;
        			}
            		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
            		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
            		$("#Pagination").pagination(maxPage, {
        	        num_edge_entries: 0, //边缘页数
        	        num_display_entries: 4, //主体页数
        	        callback: pageselectCallback,//回调函数
        	        items_per_page:1, //每页显示1项
        	        ellipse_text: "",
        	        current_page:$(".drugList input").first().parent().val()-1
        	    });	
            	}else{
            		searchDrug();
            	}   
        		alert("更新通用中药成功！");
        		$(".bg").click();
        	}else{
        		alert("更新通用中药失败！");
        	}
        },
        dataType:"json",
        type:"POST"      
        }).error(function(date){alert(date);});
    }
}
function updateCommonWestDrug(){
	var checkIsNull = 0;
    $.each($(".updateCommonWestDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    
    if (checkIsNull == 0) {
        $.post("http://localhost:8080/MedicineProject/Web/admin/updateWestMedicine", {
            name: $(".CommonWestDrug_name").val(),
            other_name: $(".CommonWestDrug_other_name").val(),
            content: $(".CommonWestDrug_content").val(),
            current_application: $(".CommonWestDrug_current_application").val(),
            pharmacolo: $(".CommonWestDrug_pharmacolo").val(),       
            warn: $(".CommonWestDrug_warn").val(),
            ADRS: $(".CommonWestDrug_ADRS").val(),
            interaction: $(".CommonWestDrug_interaction").val(),
            dose_explain: $(".CommonWestDrug_dose_explain").val(),
            manual: $(".CommonWestDrug_manual").val(),
            preparations: $(".CommonWestDrug_preparations").val(),         
            medicineType_id:$(".updateCommonWestDrug #third").val(),
            id:$(".saveID").val()
        }, function (data) {
        	if(data.result.trim()=="success"){
        		if(parseInt($(".drugList input").first().parent().val())!=-1){
        			switch($(".resourceManagement_drugListSelect").val()){
        			case '1':maxPage=getCount(21);break;
        			case '2':maxPage=getCount(22);break;
        			case '3':maxPage=getCount(23);break;
        			case '4':maxPage=getCount(24);break;
        			case '5':maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);break;
        			}
            		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
            		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
            		$("#Pagination").pagination(maxPage, {
        	        num_edge_entries: 0, //边缘页数
        	        num_display_entries: 4, //主体页数
        	        callback: pageselectCallback,//回调函数
        	        items_per_page:1, //每页显示1项
        	        ellipse_text: "",
        	        current_page:$(".drugList input").first().parent().val()-1
        	    });	
            	}else{
            		searchDrug();
            	} 
        		alert("更新通用西药成功！");
        		$(".bg").click();
        	}else{
        		alert("更新通用西药失败！");
        	}
            
        }, "json");
    }
}
/*资源管理会议列表*/
function loadingResourceManagement_meetingList(page, size) {//加载药品列表
	var Message="";
    var checkbox="";
    var Name="";
    var Subject = "";
    var Times="";   
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/getMeetings",
    	data:{
            page:page,
            size: size
        }, 
        success:function (data,status) {
           if(data.result=="success"){
        	   if(data.Meetings!=null)
            	$.each(data.Meetings, function (times, result) {
                    checkbox = "<td><li value=\""+page+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    Name = "<td><a href=\""+result.pageUrl+"\">" + result.name + "</a></td>";
                    Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                    Times="<td>"+result.commitDate+"</td>";
                    Message = Message + "<tr>" + checkbox + Name + Subject+Times +  "</tr>";
                });
            	$(".meetingList_tbody").empty().prepend(Message);
            }else{
            	console.log(data);
            	$(".meetingList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }   
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	console.log(data);
    	$(".meetingList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}
function deleteMeeting(){//which default:删除但是页表下会议 ，-1：删除搜索下会议
	var maxPage=0;
	var currentPage=0;
	$.each($(".meetingList_tbody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/admin/deleteMeeting",
		    	data:{
		            id:$(result).val()
		        }, 
		        success:function (data,status) {
		        	
		        	if(data.result.trim()=="success")
		        	switch(parseInt($(result).parent().val())){
		        	case -1:{
		        		$(result).parent().parent().parent().remove() ;break;
		        		
		        	}
		        	default:{
		        		maxPage=getCount_1(3);
		        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
		        		currentPage=$(result).parent().val()-1;
		        		if(maxPage==currentPage) currentPage--;
		        		$("#Pagination").pagination(maxPage, {
	        	        num_edge_entries: 0, //边缘页数
	        	        num_display_entries: 4, //主体页数
	        	        callback: pageselectCallback,//回调函数
	        	        items_per_page:1, //每页显示1项
	        	        ellipse_text: "",
	        	        current_page:currentPage
	        	    });	
	        		break;}
		        	}		        	
		        	else     alert("删除失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("删除失败！");
		        });
		}
	});
}
function searchMeeting(){
	var Message="";
    var checkbox="";
    var Name="";
    var Subject = "";
    var Times="";
    var Function="";   
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/searchMeeting",
    	data:{
    		keyword:$(".resourceManagement_search").val()
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
        	   if(data.Meetings!=null){
        		   $.each(data.Meetings, function (times, result) {
                       checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                       Name = "<td>" + result.name + "</td>";
                       Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                       Times="<td>"+result.commitDate+"</td>";
                       Function = "<td class=\"list_button businessList_button\"><ul><li class=\"businessList_list_freezeButton iconfont icon\"><a>&#xe647;</a></li></ul></td>";
                       Message = Message + "<tr>" + checkbox + Name + Subject+Times + Function + "</tr>";
                   });
               	$(".meetingList_tbody").empty().prepend(Message);
        	   }else{
        		   alert("无相关数据！");
        	   }
            	
            }   
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
            alert(data+"--"+status);
        });
}
/*资源管理视频列表*/
function loadingResourceManagement_videoList(page, size) {//加载视频列表
    var Message="";
    var checkbox="";
    var Name="";
    var Subject = "";
    var Times="";   
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/video/get",
    	data:{
            page:page,
            pageSize: size
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
        	   if(data.Videos!=null)
            	$.each(data.Videos, function (times, result) {
                    checkbox = "<td><li value=\""+page+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    Name = "<td><a href=\""+result.pageUrl+"\">" + result.name + "</a></td>";
                    Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                    Times="<td>"+result.modifyTime+"</td>";              
                    Message = Message + "<tr>" + checkbox + Name + Subject+Times +  "</tr>";
                });
            	$(".videoList_tbody").empty().prepend(Message);
            }else{
            	$(".videoList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }   
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".videoList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}
function deleteVideo(){//which default:删除但是页表下视频，-1：删除搜索下视频
	var maxPage=0;
	var currentPage=0;
	$.each($(".videoList_tbody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/admin/video/delete",
		    	data:{
		            id:$(result).val()
		        }, 
		        success:function (data,status) {
		        	if(data.result.trim()=="success")
		        	switch(parseInt($(result).parent().val())){
		        	case -1:{
		        		$(result).parent().parent().parent().remove() ;break;
		        		
		        	}
		        	default:{
		        		maxPage=getCount_1(5);
		        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
		        		currentPage=$(result).parent().val()-1;
		        		if(maxPage==currentPage) currentPage--;
		        		$("#Pagination").pagination(maxPage, {
	        	        num_edge_entries: 0, //边缘页数
	        	        num_display_entries: 4, //主体页数
	        	        callback: pageselectCallback,//回调函数
	        	        items_per_page:1, //每页显示1项
	        	        ellipse_text: "",
	        	        current_page:currentPage
	        	    });	
	        		break;}
		        	}		        	
		        	else     alert("删除失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("删除失败！");
		        });
		}
	});
}
function getDrugType(){
	var firstOption={};
	var firstValues={};
	var secondValues={};
	var secondOption={};
	var thirdValues={};
	var forthOption={};
	var defaultvalue=0;
	var times=0;
	var firstTimes=0;
	var firstDefaultvalue=0;
	var forthTimes_1=0;
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/user/getAllMedicineType",
        success:function (data,status) {
            if(data.result.trim()=="success"){
            	$.each(data.first.superType, function (time1, superType) {
            		firstValues[superType.name]=superType.id;
            		firstOption[superType.name]={};
            		$.each(data.second,function(time2,second){
            			if(superType.name==time2){
            				$.each(second,function(time2_1,secondResult){
            					firstTimes++;
            					if(firstTimes==1){firstDefaultvalue=secondResult.id;}
                				secondValues[secondResult.name]=secondResult.id;
                				secondOption[secondResult.name]={};
                				$.each(data.third,function(time3,third){            					
                					if(time3==secondResult.name){
                						$.each(third,function(time3_1,thirdResult){
                        					thirdValues[thirdResult.name]=thirdResult.id;
                        					forthOption[thirdResult.name]={};
                                    		forthOption[thirdResult.name]["key"]=thirdResult.id;
                                    		forthOption[thirdResult.name]["values"]={};
                        					times++;
                        					if(times==1){defaultvalue=thirdResult.id;}
                        				}); 
                    					secondOption[secondResult.name]["values"]=thirdValues;
                    					secondOption[secondResult.name]["key"]=secondResult.id;
                    					secondOption[secondResult.name]["defaultvalue"]=defaultvalue;
                    					times=0;
                    					thirdValues={};
                    					return false;
                					}            					
                				});               				
                			});
            				firstOption[superType.name]["values"]=secondValues;
            				firstOption[superType.name]["key"]=superType.id;
            				firstOption[superType.name]["defaultvalue"]=firstDefaultvalue;
            				firstTimes=0;
        					secondValues={};
        					return false;
            			}           			
            		});
            	});     
                $.each(data.forth,function(times,result){//取第四级
            		
            		forthTimes_1=0;
            		if(result==null){forthOption[times]["defaultvalue"]=0;}
            		else{
            			$.each(result,function(times_1,result_1){
            				forthOption[times]["values"][result_1.name]=result_1.id;
            				if(forthTimes_1==0){forthOption[times]["defaultvalue"]=result_1.id;}
            				forthTimes_1++;
            			});
            		}           		
            	});
            	$("#first").doubleSelect('second',firstOption);
            	$("#second").doubleSelect('third',secondOption );
            	$("#third").doubleSelect('forth',forthOption);
            }            
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	
        });
}
function searchVideo(){//未完成，等后台
	var Message="";
    var checkbox="";
    var Name="";
    var Subject = "";
    var Times="";
    var Function="";   
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/video/search",
    	data:{
    		keyword:$(".resourceManagement_search").val()
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
        	   if(data.nameResult!=null){
        		   $.each(data.nameResult, function (times, result) {
                       checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                       Name = "<td>" + result.name + "</td>";
                       Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                       Times="<td>"+result.modifyTime+"</td>";
                       Function = "<td class=\"list_button businessList_button\"><ul><li class=\"businessList_list_freezeButton iconfont icon\"><a>&#xe647;</a></li></ul></td>";
                       Message = Message + "<tr>" + checkbox + Name + Subject+Times + Function + "</tr>";
                   });
               	$(".videoList_tbody").empty().prepend(Message);
        	   }else{
        		   alert("无相关数据！");
        	   }           	
            }   else{
            	$(".videoList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            } 
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".videoList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}