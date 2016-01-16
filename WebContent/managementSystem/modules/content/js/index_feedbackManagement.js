function loadingfeedBackManagement_feedbackList(page, size) {//加载用户列表
	var checkbox="";
	var Message="";
    var Name="";
    var Phone = "";
    var QQ="";
    var LastLogin="";
    var Content=""; 
    $.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/feedback/get",
    	data:{
            page:page,
            pageSize: size
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
            	$.each(data.FeedBackMasss, function (times, result) {
            		 checkbox = "<td><li value=\""+page+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    Name = "<td>" + result.userAccount + "</td>";
                    
                    Phone = "<td>" + result.phone+"</td>";
                    QQ="<td>"+result.qqNumber+"</td>";
                    LastLogin="<td>"+result.time+"</td>";
                    Content="<td>"+result.content+"</td>";
                    Message = Message + "<tr>"+checkbox+ Name +Phone+ QQ+Content + LastLogin+ "</tr>";
                });
            	$(".feedbackList_tobody").empty().prepend(Message);
            }   else{
            	$(".feedbackList_tobody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".feedbackList_tobody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}
function reduceFeedback(){
	var maxPage=0;
	$.each($(".feedbackList_tobody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/deleteFeedBack",
		    	data:{
		            id:$(result).val()
		        }, 
		        success:function (data,status) {
		        	if(data.result.trim()=="success"){
		        		maxPage=getCount_1(6);
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
		        	else     alert("通过处理失败！");    
		        },
		        dataType:"json",
		        type:"POST"
		    }).error(function (data,status) {
		    	console.log(data);
		    	alert("通过处理失败！");
		        });
		}
	});
}
