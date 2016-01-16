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
function getCount(times){
	var maxPage=0;
	$.ajax({
		url:"http://112.74.131.194:8080/MedicineProject/Web/user/getCount",
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
    if($(jq).parent().attr('class').trim()=="drugListPage"){
    	loadingResourceManagement_drugList(8, page_index + 1);
    }else if($(jq).parent().attr('class').trim()=="businessListPage"){
    	loadingBusinessManagement_businessList(page_index + 1, 8);
    }
    return false;
}
function firstPageClick(selection){
	var maxPage=0;
	switch(selection){
	case 1:{
		maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);
		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		else maxPage=maxPage/8;
		break;
	}
	default:maxPage=5;
	}
	
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
		maxPage=getCount(21)+getCount(22)+getCount(23)+getCount(24);
		if(maxPage%8!=0){maxPage=parseInt(maxPage/8)+1;}
		else maxPage=maxPage/8;
		break;
	}
	default:maxPage=5;
	}
	$("#Pagination").pagination(maxPage, {
        num_edge_entries: 0, //边缘页数
        num_display_entries: 4, //主体页数
        callback: pageselectCallback,//回调函数
        items_per_page:1, //每页显示1项
        ellipse_text: "",
        current_page:maxPage-1
    });
};