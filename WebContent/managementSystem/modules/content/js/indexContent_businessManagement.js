function loadingBusinessManagement_businessList(page, size) {//加载企业列表
    var Message="";
    var checkbox="";
    var Name="";
    var Type = ""; 
    var Status="";
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/getEnterprises",
    	data:{
            page:page,
            size: size
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
            	$.each(data.Enterprises, function (times, result) {
                    checkbox = "<td><li value=\""+page+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    Name = "<td>" + result.name + "</td>";
                    switch(result.type){
                    case 0:Type='内资';break;
                    case 1:Type='外资';break;
                    case 2:Type='合资';break;
                    default:Type="";break;
                    }
                    Type = "<td>" + Type +"</td>"; 
                    switch(result.status){
                    case 1:Status="<td class=\"status\" style=\"color:darkkhaki;\">未冻结</td>";break;
                    default:Status="<td class=\"status\" style=\"color:#5f9ea0;\">已冻结</td>";break;
                    }
                    Message = Message + "<tr>" + checkbox + Name + Type+ Status+"</tr>";
                });
            	$(".businessList_tbody").empty().prepend(Message);
            }   else{
            	$(".businessList_tbody").empty().prepend("<td></td><td>无相关信息！</td>");
            }
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".businessList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}

function searchBusiness(){
	var Message="";
    var checkbox="";
    var Name="";
    var Type = "";
    var Status="";
    if($(".businessManagement_search").val().trim()!="")
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/searchEnterprise",
    	data:{
            keyword:$(".businessManagement_search").val()
        }, 
        success:function (data,status) {
            if(data.result.trim()=="success"){
             	$.each(data.Enterprises, function (times, result) {
                     checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                     Name = "<td>" + result.name + "</td>";
                     switch(result.type){
                     case 0:Type='内资';break;
                     case 1:Type='外资';break;
                     case 2:Type='合资';break;
                     }
                     Type = "<td>" + Type +"</td>";   
                     switch(result.status){
                     case 1:Status="<td class=\"status\">未冻结</td>";break;
                     default:Status="<td class=\"status\">已冻结</td>";break;
                     }
                     Message = Message + "<tr>" + checkbox + Name + Type +Status + "</tr>";
                 });
             	$(".businessList_tbody").empty().prepend(Message);
             }   
            else console.log(data);
         },
         dataType:"json",
         type:"GET"
     }).error(function (data,status) {
             alert(data+"--"+status);
         });
}

function blockBusiness(value){//1：冻结，0：解冻/恢复
	var status="";
	var style="";
	$.each($(".businessList_tbody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/admin/enterprise/block",
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
		        		$(result).parent().parent().parent().find(".status").empty().append(status).css("color",style) ;
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

function loadingBusinessManagement_registerCheckList(page, size) {//加载企业列表
    var Message="";
    var checkbox="";
    var Name="";
    var Type = ""; 
    var Number="";
    var Img="";
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/admin/getEnterCheck",
    	data:{
    		pageNum:page,
    		pageSize: size
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
            	$.each(data.Enterprises, function (times, result) {
                    checkbox = "<td><li value=\""+page+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    Name = "<td><a src=\""+result.enterpriseUrl+"\">" + result.name + "</a></td>";
                    switch(result.type){
                    case 0:Type='内资';break;
                    case 1:Type='外资';break;
                    case 2:Type='合资';break;
                    default:Type="";break;
                    }
                    Type = "<td>" + Type +"</td>";
                    Number="<td>"+result.enterpriseNumber+"</td>";
                    Img="<td><a src=\""+result.checkImg+"\">查看</a></td>";
                    Message = Message + "<tr>" + checkbox + Name + Type+ Number+Img+"</tr>";
                });
            	$(".registerCheckList_tobody").empty().prepend(Message);
            }   else{
            	$(".registerCheckList_tobody").empty().prepend("<td></td><td>无相关信息！</td>");
            }
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".registerCheckList_tobody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}

function passBusinessRegisterCheck(){
	var maxPage=0;
	$.each($(".registerCheckList_tobody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/admin/passCheckData",
		    	data:{
		            id:$(result).val(),
		            type:1
		        }, 
		        success:function (data,status) {
		        	console.log(data);
		        	if(data.result.trim()=="success"){
		        		maxPage=getCount_1(1);
		        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
		        		$("#Pagination").pagination(maxPage, {
	        	        num_edge_entries: 0, //边缘页数
	        	        num_display_entries: 4, //主体页数
	        	        callback: pageselectCallback,//回调函数
	        	        items_per_page:1, //每页显示1项
	        	        ellipse_text: "",
	        	        current_page:$(result).parent().val()-1
	        	        });	
		        	}	        	
		        	else     alert("通过审核失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("通过审核失败！");
		        });
		}
	});
}

function unpassBusinessRegisterCheck(){
	var maxPage=0;
	$.each($(".registerCheckList_tobody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/admin/enterprise/unpass",
		    	data:{
		            id:$(result).val()
		        }, 
		        success:function (data,status) {
		        	if(data.result.trim()=="success"){
		        		maxPage=getCount_1(1);
		        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
		        		$("#Pagination").pagination(maxPage, {
	        	        num_edge_entries: 0, //边缘页数
	        	        num_display_entries: 4, //主体页数
	        	        callback: pageselectCallback,//回调函数
	        	        items_per_page:1, //每页显示1项
	        	        ellipse_text: "",
	        	        current_page:$(result).parent().val()-1
	        	        });	
		        	}	        	
		        	else     alert("否决审核失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("否决审核失败！");
		        });
		}
	});
}
