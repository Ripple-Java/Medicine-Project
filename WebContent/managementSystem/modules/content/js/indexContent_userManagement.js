function loadingUserManagement_userList(page, size) {//加载用户列表
	var checkbox="";
    var Message="";
    var Name="";
    var Type = "";
    var RegeditTime="";
    var LastLogin="";
    var Device=""; 
    var Status="";
    $.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/getUsers",
    	data:{
            page:page,
            size: size,
            sort:$(".userSort").val()
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
        	   
            	$.each(data.Users, function (times, result) {
            		 checkbox = "<td><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></td>";
                    Name = "<td>" + result.account + "</td>";
                    switch(result.type){
                    case 0:Type='普通用户';break;
                    case 1:Type='学生';break;
                    case 2:Type='医师';break;
                    case 3:Type='企业';break;
                    case 4:Type='管理员';break;
                    }
                    Type = "<td>" + Type +"</td>";
                    switch(result.status){
                    case 1:Status="<td class=\"status\" style=\"color:darkkhaki;\">未冻结</td>";break;
                    default:Status="<td class=\"status\" style=\"color:#5f9ea0;\">已冻结</td>";break;
                    }
                    RegeditTime="<td>"+result.regeditTime+"</td>";
                    LastLogin="<td>"+result.lastLogin+"</td>";
                    Device="<td>"+result.device+"</td>";
                    Message = Message + "<tr>"+checkbox+ Name + Type + RegeditTime+LastLogin +Device +Status+"</tr>";
                });
            	$(".userList_tbody").empty().prepend(Message);
            } else{
            	$(".userList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }  
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".userList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}

function searchUser(){
	var Message="";
    var Name="";
    var Type = "";
    var RegeditTime="";
    var LastLogin="";
    var Device="";
    var Status="";
    if($(".userManagement_search").val().trim()!="")
    $.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/searchUser",
    	data:{
            keyword:$(".userManagement_search").val()
        }, 
        success:function (data,status) {
            if(data.result.trim()=="success"){
             	$.each(data.Users, function (times, result) {
                     Name = "<td>" + result.account + "</td>";
                     switch(result.type){
                     case 0:Type='普通用户';break;
                     case 1:Type='学生';break;
                     case 2:Type='医师';break;
                     case 3:Type='企业';break;
                     case 4:Type='管理员';break;
                     }
                     Type = "<td>" + Type +"</td>";
                     switch(result.status){
                     case 1:Status="<td class=\"status\" style=\"color:darkkhaki;\">未冻结</td>";break;
                     default:Status="<td class=\"status\" style=\"color:#5f9ea0;\">已冻结</td>";break;
                     }
                     RegeditTime="<td>"+result.regeditTime+"</td>";
                     LastLogin="<td>"+result.lastLogin+"</td>";
                     Device="<td>"+result.device+"</td>";
                     Message = Message + "<tr>"+ Name + Type + RegeditTime+LastLogin +Device +Status+ "</tr>";
                 });
             	$(".userList_tbody").empty().prepend(Message);
             }   
         },
         dataType:"json",
         type:"GET"
     }).error(function (data,status) {
             alert(data+"--"+status);
         });
}
function blockUser(value){//1：冻结，0：解冻/恢复
	var status="";
	var style="";
	$.each($(".userList_tbody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/user/block",
		    	data:{
		            id:$(result).val(),
		            value:value
		        }, 
		        success:function (data,functionstatus) {
		        	console.log(data);
		        	if(data.result.trim()=="success"){
		        		if(value==1){
		        			status="已冻结" ;
		        			style="#5f9ea0";
		        		}else{
		        			status="未冻结";
		        			style="darkkhaki";
		        		}	       
		        		$(result).parent().parent().find(".status").empty().append(status).css("color",style) ;
		        		$(result).click();
		        	}	        		
		        	else     alert("冻结失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("冻结失败！");
		        });
		}
	});
}
function userfilter(page, size){
	var checkbox="";
    var Message="";
    var Name="";
    var Type = "";
    var RegeditTime="";
    var LastLogin="";
    var Device=""; 
    var Status="";
    $.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/getUsersByType",
    	data:{
            page:page,
            size: size,
            type:$(".userFilter").val(),
            sort:$(".userSort").val()
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
            	$.each(data.Users, function (times, result) {
            		 checkbox = "<td><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></td>";
                    Name = "<td>" + result.account + "</td>";
                    switch(result.type){
                    case 0:Type='普通用户';break;
                    case 1:Type='学生';break;
                    case 2:Type='医师';break;
                    case 3:Type='企业';break;
                    case 4:Type='管理员';break;
                    }
                    Type = "<td>" + Type +"</td>";
                    switch(result.status){
                    case 1:Status="<td class=\"status\" style=\"color:darkkhaki;\">未冻结</td>";break;
                    default:Status="<td class=\"status\" style=\"color:#5f9ea0;\">已冻结</td>";break;
                    }
                    RegeditTime="<td>"+result.regeditTime+"</td>";
                    LastLogin="<td>"+result.lastLogin+"</td>";
                    Device="<td>"+result.device+"</td>";
                    Message = Message + "<tr>"+checkbox+ Name + Type + RegeditTime+LastLogin +Device +Status+"</tr>";
                });
            	$(".userList_tbody").empty().prepend(Message);
            } else{
            	$(".userList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }  
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".userList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}