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

function NextPageButtonFunction(listName,pageNum,functionSelect){
	if(pageNum==2){
		$(".ifpage1").removeClass("ifpage1");
	}else if(pageNum==1){
		$(".firstPage").addClass("ifpage1");
		$(".lastPage").addClass("ifpage1");
	}
	$(listName).empty();
	$(".content_content_pageNum ul li").removeClass("content_content_pageNum_hadSelect");
	$(".content_content_pageNum ul li:nth-child("+pageNum+")").addClass("content_content_pageNum_hadSelect");
	switch(functionSelect){
	case 1:loadingResourceManagement_drugList(8, pageNum);break;//加载药品列表
	}
}