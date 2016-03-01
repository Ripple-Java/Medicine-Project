function loadingResourceManagement_drugList(pageSize, pageNum) {//加载药品列表
    var drugMessage="";
    var checkbox="";
    var drugName="";
    var drugTime="";
    var drugFunction="";
    var drugType = "";
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/getMedicine",
    	data:{
            pageSize: pageSize,
            page: pageNum
        }, 
        success:function (data,status) {
            if(data.result.trim()=="success"){
            	if(data.BackGroundMedicineVOs!=null)
            	$.each(data.BackGroundMedicineVOs, function (times, result) {
                    checkbox = "<td><li value=\""+pageNum+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    drugName = "<td>" + result.name + "</td>";
                    drugType = "<td>" + result.type.firstType + "-" + result.type.secondType + "-" + result.type.thirdType ;
                    drugTime="<td>"+result.updateTime+"</td>";
                    drugFunction = "<td class=\"list_button resourceList\"><ul><li class=\"drugList_list_editButton iconfont \"  onclick=\"resoure_updateDrug("+result.id+");\"><a>&#xe630;</a></li></ul></td>";
                    drugMessage = drugMessage + "<tr>" + checkbox + drugName + drugType +drugTime+ drugFunction + "</tr>";
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
function addBusinessWestDrugButtonFunction() {//资源管理---药品列表---添加西药保存按钮
    var checkIsNull = 0;
    $.each($(".addDrug_required"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
    	$.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/enterprise/addWestMedicine",
        	data:{
        		name: $(".BusinessWestDrug_name").val(),
                other_name: $(".BusinessWestDrug_othername").val(),
                content: $(".BusinessWestDrug_content").val(),
                current_application: $(".BusinessWestDrug_current_application").val(),
                pharmacolo: $(".BusinessWestDrug_pharmacolo").val(),
                warn: $(".BusinessWestDrug_warn").val(),
                ADRS: $(".BusinessWestDrug_ADRS").val(),
                interaction: $(".BusinessWestDrug_interaction").val(),
                dose_explain: $(".BusinessWestDrug_dose_explain").val(),
                manual: $(".BusinessWestDrug_manual").val(),
                preparations: $(".BusinessWestDrug_preparations").val(),         
                price: parseInt($(".BusinessWestDrug_price").val()),
                medicineId: $("#forth").val()
            }, 
            success:function (data,status) {
            	addGetpage(1);
               alert("添加药品成功！");
               $(".bg").click();
            },
            dataType:"json",
            type:"POST"
        }).error(function (data,status) {
        	console.log(data);
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
function deleteDrug(){//which default:删除但是页表下药品 ，-1：删除搜索下药品
	$.each($(".drugList :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/enterprise/deleteMedicine",
		    	data:{
		            id:$(result).val(),
		            type: 4
		        }, 
		        success:function (data,status) {
		        	
		        	if(data.result.trim()=="success")
		        	switch(parseInt($(result).parent().val())){
		        	case -1:{
		        		$(result).parent().parent().parent().remove() ;break;
		        		
		        	}
		        	default:{
		        		addGetpage(1);	
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
function loadingResourceManagement_meetingList(page, size) {//加载会议列表
    var Message="";
    var checkbox="";
    var Name="";
    var Subject = "";
    var Times="";
    var Function="";   
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/getMeeting",
    	data:{
            page:page,
            pageSize: size
        }, 
        success:function (data,status) {
           if(data.result=="success"){
        	   if(data.Meetings!=null)
            	$.each(data.Meetings, function (times, result) {
                    checkbox = "<td><li value=\""+page+"\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                    Name = "<td><a href=\""+result.pageUrl+"\">" + result.name + "</a></td>";
                    Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                    Times="<td>"+result.commitDate+"</td>";
                    Function = "<td class=\"list_button resourceList\"><ul><li class=\"meetingList_list_editButton iconfont \" onclick=\"resoure_updateMeeting("+result.id+");\"><a>&#xe630;</a></li></ul></td>";
                    Message = Message + "<tr>" + checkbox + Name + Subject+Times + Function + "</tr>";
                });
            	$(".meetingList_tbody").empty().prepend(Message);
            }else{
            	$(".meetingList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
            }   
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	$(".meetingList_tbody").empty().prepend("<td></td><td>加载列表数据失败！</td>");
        });
}

function deleteMeeting(){//which default:删除但是页表下会议 ，-1：删除搜索下会议
	$.each($(".meetingList_tbody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/enterprise/deleteMeeting",
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
		        		addGetpage(2);
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
function loadingResourceManagement_videoList(page, size) {//加载视频列表
    var Message="";
    var checkbox="";
    var Name="";
    var Subject = "";
    var Times="";
    var Function="";   
    $.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/video/get",
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
                    Function = "<td class=\"list_button resourceList\"><ul><li class=\"meetingList_list_editButton iconfont \" onclick=\"resoure_updateVideo("+result.id+");\"><a>&#xe630;</a></li></ul></td>";
                    Message = Message + "<tr>" + checkbox + Name + Subject+Times + Function + "</tr>";
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
function deleteVideo(){//which default:删除但是页表下会议 ，-1：删除搜索下会议
	$.each($(".videoList_tbody :checkbox "),function(times,result){
		if($(result).is(':checked')){
			$.ajax({
		    	url:"http://localhost:8080/MedicineProject/Web/enterprise/video/delete",
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
		        		addGetpage(3);
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
function ResourceMangement_search(which){//1搜索药品；2搜索会议；3搜索视频
	var url="";
	var whichList_tbody="";
	var Message="";
    var checkbox="";
    var Name="";
    var Times="";
    var Function="";
    var Type = "";
    var Subject = "";
	switch(which){
	case 1:url="http://localhost:8080/MedicineProject/Web/enterprise/searchMedicine";whichList_tbody=".drugList";break;
	case 2:url="http://localhost:8080/MedicineProject/Web/enterprise/searchMeeting";whichList_tbody=".meetingList_tbody";break;
	case 3:url="http://localhost:8080/MedicineProject/Web/enterprise/video/search";whichList_tbody=".videoList_tbody";break;
	}
	$.ajax({
    	url:url,
    	data:{
            keyword:$(".content_nav_search input").val()
        }, 
        success:function (data,status) {
            if(data.result.trim()=="success"){
            	switch(which){
            	case 1:{
            		if(data.nameResult!=null)
            		$.each(data.nameResult, function (times, result) {
                        checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                        Name = "<td>" + result.name + "</td>";
                        Type = "<td>" + result.type.firstType + "-" + result.type.secondType + "-" + result.type.thirdType ;
                        Times="<td>"+result.updateTime+"</td>";
                        Function = "<td class=\"list_button resourceList\"><ul><li class=\"drugList_list_editButton iconfont \"  onclick=\"resoure_updateDrug("+result.id+");\"><a>&#xe630;</a></li></ul></td>";
                        Message = Message + "<tr>" + checkbox + Name + Type +Times+ Function + "</tr>";
                    });
            		break;
            	}
            	case 2:{
            		if(data.nameResult!=null)
            		$.each(data.nameResult, function (times, result) {
                        checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                        Name = "<td><a href=\""+result.pageUrl+"\">" + result.name + "</a></td>";
                        Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                        Times="<td>"+result.commitDate+"</td>";
                        Function = "<td class=\"list_button resourceList\"><ul><li class=\"meetingList_list_editButton iconfont \" onclick=\"resoure_updateMeeting("+result.id+");\"><a>&#xe630;</a></li></ul></td>";
                        Message = Message + "<tr>" + checkbox + Name + Subject+Times + Function + "</tr>";
                    });
            		break;
            	}
            	case 3:{
            		if(data.nameResult!=null)
            		$.each(data.nameResult, function (times, result) {
                        checkbox = "<td><li value=\"-1\"><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></li></td>";
                        Name = "<td><a href=\""+result.pageUrl+"\">" + result.name + "</a></td>";
                        Subject = "<td>" +result.subject.parent_name  +" - "+result.subject.name+"</td>";
                        Times="<td>"+result.modifyTime+"</td>";
                        Function = "<td class=\"list_button businessList_button\"><ul><li class=\"meetingList_list_editButton iconfont \" onclick=\"resoure_updateVideo("+result.id+");\"><a>&#xe630;</a></li></ul></td>";
                        Message = Message + "<tr>" + checkbox + Name + Subject+Times + Function + "</tr>";
                    });
            		break;
            	}
            	}
            	$(whichList_tbody).empty().prepend(Message);
            }else{
            	alert("没有搜索到任何信息！");
            }            
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
        });
}
function addVideo(){//添加会议
	var checkIsNull = 0;
	var selectVal="";
    $.each($(".addVideo li input"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    ($("#second").val()==null)?selectVal=$("#first").val():selectVal=$("#second").val();
    if (checkIsNull == 0) {
    	$.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/enterprise/video/add",
        	data:{
        		name: $(".BusinessVideo_name").val(),
        		speaker: $(".BusinessVideo_speaker").val(),
        		subject_id:selectVal,
        		pageUrl:$(".BusinessVideo_URL").val()
            }, 
            success:function (data,status) {
            	if(data.result.trim()=="success"){
            		addGetpage(3);	
            		alert("添加视频成功！");
                    $(".bg").click();
            	}                   
            },
            dataType:"json",
            type:"POST"
        }).error(function (data,status) {
        	alert("添加失败！");
            });
    }
	
}

function addMeeting(){//添加会议
	var checkIsNull = 0;
	var selectVal="";
    $.each($(".addMeeting li input"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    ($("#second").val()==null)?selectVal=$("#first").val():selectVal=$("#second").val();
    if (checkIsNull == 0) {
    	$.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/enterprise/addMeeting",
        	data:{
        		name: $(".BusinessMeeting_name").val(),
        		speaker: $(".BusinessMeeting_speaker").val(),
        		date:$(".BusinessMeeting_date").val(),
        		content:$(".BusinessChinaDrug_content").val(),
        		subject_id:selectVal,
        		pageUrl:$(".BusinessMeeting_URL").val()
            }, 
            success:function (data,status) {
            	if(data.result.trim()=="success"){
            		addGetpage(2);
            		alert("添加会议成功！");
                    $(".bg").click();
            	}                   
            },
            dataType:"json",
            type:"POST"
        }).error(function (data,status) {
        	alert("添加失败！");
            });
    }
	
}

function addGetpage(which_getCount){//1:drug 2:meeting 3:video
	var currentPage=0;
	var maxPage=0;
	switch(which_getCount){
	case 1:maxPage=getCount(1);currentPage=$(".drugList input").first().parent().val()-1;break;
	case 2:maxPage=getCount(2);currentPage=$(".meetingList_tbody input").first().parent().val()-1;break;
	case 3:maxPage=getCount(3);currentPage=$(".videoList_tbody input").first().parent().val()-1;break;
	}
	if(isNaN(currentPage)) currentPage=0;
	if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
	else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
	if(maxPage==currentPage) currentPage--;
	$("#Pagination").pagination(maxPage, {
    num_edge_entries: 0, //边缘页数
    num_display_entries: 4, //主体页数
    callback: pageselectCallback,//回调函数
    items_per_page:1, //每页显示1项
    ellipse_text: "",
    current_page:currentPage
    });
}

function getSubject(){
	var firstOption={};
	var i=0;
	var j=0;
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/user/getSubject",
        success:function (data,status) {
            if(data.result.trim()=="success"){
            	$.each(data.first,function(times,result){
            		firstOption[times]={};
            		firstOption[times]["key"]=i;
            		firstOption[times]["values"]={};
            		j=0;
            		$.each(result,function(times_1,result_1){
            			if(j==0){
            				firstOption[times]["defaultvalue"]=result_1.id;
            			}
            			firstOption[times]["values"][result_1.name]=result_1.id;
            			j++;
            		});
            		i++;
            	});            	
            	$("#first").doubleSelect('second',firstOption);
            }            
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	
        });
}
function getDrugType(){
	var firstOption={};
	var firstValues={};
	var secondValues={};
	var secondOption={};
	var thirdValues={};
	var defaultvalue=0;
	var times=0;
	var firstTimes=0;
	var firstDefaultvalue=0;
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
            	$("#first").doubleSelect('second',firstOption);
            	$("#second").doubleSelect('third',secondOption );
            	//console.log(firstOption);
            	getDrug();
            }            
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
    	
        });
}
function getDrug(){
	var option="";
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/user/medicine/name/get",
    	data:{
    		catagoryId: $("#third").val()
        }, 
        success:function (data,status) {
        	if(data.result.trim()=="success"){
        		$.each(data.medicines,function(times,result){
        			if(times==1){
        				option=option+"<option value=\""+result.id+"\" selected>"+result.name+"</option>";
        			}else{
        				option=option+"<option value=\""+result.id+"\" >"+result.name+"</option>";
        			}
        		});
        		$("#forth").empty().append(option);
        	}  
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {   	
    	console.log(data);
        });
}
function resoure_updateDrug(which){//修改企业西药_1
	$(".saveID").val(which);
	$(".content_content_function_add").click();
	$(".updateBusinessWestDrugButtonFunction").empty().append("<button onclick=\"updateBusinessWestDrugButtonFunction();\" >保存</button>");
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/EnterMedicine/get", 
    	data:{
    		id:which,
    		type:4
    	},
        success:function (data,status) {
        	$(".BusinessWestDrug_name").val(data.entity.name);
            $(".BusinessWestDrug_othername").val(data.entity.other_name);
            $(".BusinessWestDrug_content").val(data.entity.content);
            $(".BusinessWestDrug_current_application").val(data.entity.current_application);
            $(".BusinessWestDrug_pharmacolo").val(data.entity.pharmacolo);
            $(".BusinessWestDrug_warn").val(data.entity.warn);
            $(".BusinessWestDrug_ADRS").val(data.entity.ADRS);
            $(".BusinessWestDrug_interaction").val(data.entity.interaction);
            $(".BusinessWestDrug_dose_explain").val(data.entity.dose_explain);
            $(".BusinessWestDrug_manual").val(data.entity.manual);
            $(".BusinessWestDrug_preparations").val(data.entity.preparations) ;       
            $(".BusinessWestDrug_price").val(data.entity.price);
            $(".updateBusinessWestDrugButtonFunction").empty().append("<button onclick=\"updateBusinessWestDrugButtonFunction();\" >保存</button>");
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
        });
}
function updateBusinessWestDrugButtonFunction(){//修改企业西药_2
	var checkIsNull = 0;
    $.each($(".addDrug_required"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
    	$.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/enterprise/WestMedicine/update",
        	data:{
        		name: $(".BusinessWestDrug_name").val(),
                other_name: $(".BusinessWestDrug_othername").val(),
                content: $(".BusinessWestDrug_content").val(),
                current_application: $(".BusinessWestDrug_current_application").val(),
                pharmacolo: $(".BusinessWestDrug_pharmacolo").val(),
                warn: $(".BusinessWestDrug_warn").val(),
                ADRS: $(".BusinessWestDrug_ADRS").val(),
                interaction: $(".BusinessWestDrug_interaction").val(),
                dose_explain: $(".BusinessWestDrug_dose_explain").val(),
                manual: $(".BusinessWestDrug_manual").val(),
                preparations: $(".BusinessWestDrug_preparations").val(),         
                price: parseInt($(".BusinessWestDrug_price").val()),
                medicineId: $("#forth").val(),
                id:$(".saveID").val()
            }, 
            success:function (data,status) {
            	if(data.result.trim()=="success"){
            		if(parseInt($(".drugList input").first().parent().val())!=-1){
                		maxPage=getCount(1);
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
                		ResourceMangement_search(1);
                	}           	
                   alert("修改药品成功！");
            	}else{
            		alert("修改药品失败！");
            	}
            	
               $(".bg").click();
            },
            dataType:"json",
            type:"POST"
        }).error(function (data,status) {
        	console.log(data);
            });
    }
}
function resoure_updateMeeting(which){//修改会议_1
	$(".saveID").val(which);
	$(".content_content_function_add").click();
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/getMeeting/detail", 
    	data:{
    		id:which
    	},
        success:function (data,status) {
        	$(".BusinessMeeting_name").val(data.Meeting.name);
    		$(".BusinessMeeting_speaker").val(data.Meeting.speaker);
    		$(".BusinessMeeting_date").val(data.Meeting.date);
    		$(".BusinessChinaDrug_content").val(data.Meeting.content);
    		$(".BusinessMeeting_URL").val(data.Meeting.pageUrl);
    		$(".updateMeeting").empty().append("<button onclick=\"updateBusinessWestMeetingButtonFunction();\" >保存</button>");
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
        });
}
function updateBusinessWestMeetingButtonFunction(){//修改会议_2
	var checkIsNull = 0;
	var selectVal="";
    $.each($(".addMeeting li input"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    ($("#second").val()==null)?selectVal=$("#first").val():selectVal=$("#second").val();
    if (checkIsNull == 0) {
    	$.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/enterprise/updateMeeting",
        	data:{
        		name: $(".BusinessMeeting_name").val(),
        		speaker: $(".BusinessMeeting_speaker").val(),
        		date:$(".BusinessMeeting_date").val(),
        		content:$(".BusinessChinaDrug_content").val(),
        		subject_id:selectVal,
        		pageUrl:$(".BusinessMeeting_URL").val(),
        		meetingId:$(".saveID").val()
            }, 
            success:function (data,status) {
            	if(data.result.trim()=="success"){
            		if(parseInt($(".meetingList_tbody input").first().parent().val())!=-1){
            			maxPage=getCount(2);
    	        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
    	        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
    	        		currentPage=$(".meetingList_tbody input").first().parent().val()-1;
    	        		if(maxPage==currentPage) currentPage--;
    	        		$("#Pagination").pagination(maxPage, {
            	        num_edge_entries: 0, //边缘页数
            	        num_display_entries: 4, //主体页数
            	        callback: pageselectCallback,//回调函数
            	        items_per_page:1, //每页显示1项
            	        ellipse_text: "",
            	        current_page:currentPage
            	    });	
            		}else{
            			ResourceMangement_search(2);
            		}
            		
            		alert("修改会议成功！");
                    $(".bg").click();
            	}                   
            },
            dataType:"json",
            type:"POST"
        }).error(function (data,status) {
        	alert("修改失败！");
            });
    }
}
function resoure_updateVideo(which){//修改视频_1
	$(".saveID").val(which);
	$(".content_content_function_add").click();
	$.ajax({
    	url:"http://localhost:8080/MedicineProject/Web/enterprise/video/get/detail", 
    	data:{
    		id:which
    	},
        success:function (data,status) {
        	$(".BusinessVideo_name").val(data.Video.name);
    		$(".BusinessVideo_speaker").val(data.Video.speaker);
    		$(".BusinessVideo_URL").val(data.Video.pageUrl);
    		$(".updateVideo").empty().append("<button onclick=\"updateBusinessWestVideoButtonFunction();\" >保存</button>");
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
        });
}
function updateBusinessWestVideoButtonFunction(){//修改视频_2
	var checkIsNull = 0;
	var selectVal="";
    $.each($(".addVideo li input"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    ($("#second").val()==null)?selectVal=$("#first").val():selectVal=$("#second").val();
    if (checkIsNull == 0) {
    	$.ajax({
        	url:"http://localhost:8080/MedicineProject/Web/enterprise/video/update",
        	data:{
        		name: $(".BusinessVideo_name").val(),
        		speaker: $(".BusinessVideo_speaker").val(),
        		subject_id:selectVal,
        		pageUrl:$(".BusinessVideo_URL").val(),
        		videoId:$(".saveID").val()
            }, 
            success:function (data,status) {
            	if(data.result.trim()=="success"){
            		if(parseInt($(".videoList_tbody input").first().parent().val())!=-1){
            			maxPage=getCount(3);
		        		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		        		else (maxPage==0)?maxPage=1:maxPage=maxPage/8;
		        		currentPage=$(".videoList_tbody input").first().parent().val()-1;
		        		if(maxPage==currentPage) currentPage--;
		        		$("#Pagination").pagination(maxPage, {
	        	        num_edge_entries: 0, //边缘页数
	        	        num_display_entries: 4, //主体页数
	        	        callback: pageselectCallback,//回调函数
	        	        items_per_page:1, //每页显示1项
	        	        ellipse_text: "",
	        	        current_page:currentPage
	        	    });		            		
            		}else{
            			ResourceMangement_search(3);
            		}
            		alert("更新视频成功！");
                    $(".bg").click();	
            	}                   
            },
            dataType:"json",
            type:"POST"
        }).error(function (data,status) {
        	alert("更新失败！");
            });
    }
}